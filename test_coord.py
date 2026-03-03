import math

STEREO70_LAT0 = 46.0
STEREO70_LON0 = 25.0
STEREO70_K0 = 0.99975
STEREO70_X0 = 500000.0
STEREO70_Y0 = 500000.0

KRASOVSKY_A = 6378245.0
KRASOVSKY_B = 6356863.019
KRASOVSKY_E2 = 0.006693421622966

WGS84_A = 6378137.0
WGS84_B = 6356752.314245
WGS84_E2 = 0.00669437999014

def stereo70ToGPS(x, y):
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
        N = WGS84_A / math.sqrt(1 - WGS84_E2 * sinLat * sinLat)
        lat = math.atan2(zWgs + WGS84_E2 * N * sinLat, p)

    return math.degrees(lat), math.degrees(lon)

# Test Bucharest
# True GPS: approx 44.4268, 26.1025
# Stereo 70 approx (Easting/Y: 587000, Northing/X: 322000)
# Let's test providing X=Northing, Y=Easting as user's type them.
lat, lon = stereo70ToGPS(322000, 587000)
print('Passing Northing as X, Easting as Y -> Lat:', lat, 'Lon:', lon)

# Let's test passing Easting as X, Northing as Y.
lat, lon = stereo70ToGPS(587000, 322000)
print('Passing Easting as X, Northing as Y -> Lat:', lat, 'Lon:', lon)

