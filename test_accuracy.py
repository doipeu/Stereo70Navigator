import math

STEREO70_LAT0 = 46.0
STEREO70_LON0 = 25.0
STEREO70_K0 = 0.99975
STEREO70_X0 = 500000.0
STEREO70_Y0 = 500000.0

KRASOVSKY_A = 6378245.0
KRASOVSKY_E2 = 0.006693421622966

WGS84_A = 6378137.0
WGS84_E2 = 0.00669437999014

def stereo70_to_krasovsky(x, y):
    xRel = x - STEREO70_X0
    yRel = y - STEREO70_Y0

    lat0Rad = math.radians(STEREO70_LAT0)
    lon0Rad = math.radians(STEREO70_LON0)

    rho = math.sqrt(xRel * xRel + yRel * yRel)
    c = 2.0 * math.atan2(rho, 2.0 * KRASOVSKY_A * STEREO70_K0)

    sinC = math.sin(c)
    cosC = math.cos(c)
    sinLat0 = math.sin(lat0Rad)
    cosLat0 = math.cos(lat0Rad)

    if rho == 0:
        latKras = lat0Rad
    else:
        numerator = cosC * sinLat0 + (yRel * sinC * cosLat0 / rho)
        latKras = math.asin(numerator)

    if rho == 0:
        lonKras = lon0Rad
    else:
        numerator = xRel * sinC
        denominator = rho * cosLat0 * cosC - yRel * sinLat0 * sinC
        lonKras = lon0Rad + math.atan2(numerator, denominator)
    return latKras, lonKras

def helmert_7param(latKras, lonKras):
    # exact parameters epsg:3844 towgs84=2.329,-147.042,-92.08,0.309,-0.325,-0.497,5.69
    dx = 2.3287
    dy = -147.0425
    dz = -92.0802
    # sec to radians
    sec_to_rad = math.pi / (180.0 * 3600.0)
    # arc-seconds to radians
    rx = 0.3092483 * sec_to_rad
    ry = -0.32482185 * sec_to_rad
    rz = -0.49729934 * sec_to_rad
    s = 5.68906266 / 1000000.0

    N = KRASOVSKY_A / math.sqrt(1 - KRASOVSKY_E2 * math.sin(latKras) * math.sin(latKras))
    xCart = N * math.cos(latKras) * math.cos(lonKras)
    yCart = N * math.cos(latKras) * math.sin(lonKras)
    zCart = N * (1 - KRASOVSKY_E2) * math.sin(latKras)

    # 7-parameter transformation (Helmert)
    # Target = Translation + (1 + s) * RotationMatrix * Source
    xWgs = dx + (1 + s) * (xCart - rz * yCart + ry * zCart)
    yWgs = dy + (1 + s) * (rz * xCart + yCart - rx * zCart)
    zWgs = dz + (1 + s) * (-ry * xCart + rx * yCart + zCart)

    lon = math.atan2(yWgs, xWgs)
    p = math.sqrt(xWgs * xWgs + yWgs * yWgs)
    lat = math.atan2(zWgs, p * (1 - WGS84_E2))

    for i in range(5):
        sinLat = math.sin(lat)
        N_WGS = WGS84_A / math.sqrt(1 - WGS84_E2 * sinLat * sinLat)
        lat = math.atan2(zWgs + WGS84_E2 * N_WGS * sinLat, p)

    return math.degrees(lat), math.degrees(lon)

# OLD method 3 param: dx=28, dy=-121, dz=-77
def helmert_3param(latKras, lonKras):
    dx = 28.0
    dy = -121.0
    dz = -77.0
    N = KRASOVSKY_A / math.sqrt(1 - KRASOVSKY_E2 * math.sin(latKras) * math.sin(latKras))
    xCart = N * math.cos(latKras) * math.cos(lonKras)
    yCart = N * math.cos(latKras) * math.sin(lonKras)
    zCart = N * (1 - KRASOVSKY_E2) * math.sin(latKras)

    xWgs = xCart + dx
    yWgs = yCart + dy
    zWgs = zCart + dz

    lon = math.atan2(yWgs, xWgs)
    p = math.sqrt(xWgs * xWgs + yWgs * yWgs)
    lat = math.atan2(zWgs, p * (1 - WGS84_E2))

    for i in range(5):
        sinLat = math.sin(lat)
        N_WGS = WGS84_A / math.sqrt(1 - WGS84_E2 * sinLat * sinLat)
        lat = math.atan2(zWgs + WGS84_E2 * N_WGS * sinLat, p)

    return math.degrees(lat), math.degrees(lon)

lat_k, lon_k = stereo70_to_krasovsky(587000, 322000) # x=easting, y=northing
lat7, lon7 = helmert_7param(lat_k, lon_k)
lat3, lon3 = helmert_3param(lat_k, lon_k)

print('Old 3 param Lat:', lat3, 'Lon:', lon3)
print('New 7 param Lat:', lat7, 'Lon:', lon7)

diff_m = math.sqrt(((lat7-lat3)*111320)**2 + ((lon7-lon3)*111320*math.cos(math.radians(lat7)))**2)
print('Difference in meters approx:', diff_m)
