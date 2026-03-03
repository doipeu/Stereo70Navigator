import math
from pyproj import Transformer

STEREO70_LAT0 = 46.0
STEREO70_LON0 = 25.0
STEREO70_K0 = 0.99975
STEREO70_X0 = 500000.0
STEREO70_Y0 = 500000.0
KRASOVSKY_A = 6378245.0
KRASOVSKY_E2 = 0.006693421622966
WGS84_A = 6378137.0
WGS84_E2 = 0.00669437999014

def java_helmert(x, y):
    xRel = x - STEREO70_X0
    yRel = y - STEREO70_Y0
    lat0Rad = math.radians(STEREO70_LAT0)
    lon0Rad = math.radians(STEREO70_LON0)
    rho = math.sqrt(xRel * xRel + yRel * yRel)
    if rho == 0:
        return 0, 0
    c = 2.0 * math.atan2(rho, 2.0 * KRASOVSKY_A * STEREO70_K0)
    sinC = math.sin(c)
    cosC = math.cos(c)
    sinLat0 = math.sin(lat0Rad)
    cosLat0 = math.cos(lat0Rad)
    numerator = cosC * sinLat0 + (yRel * sinC * cosLat0 / rho)
    latKras = math.asin(numerator)
    numerator = xRel * sinC
    denominator = rho * cosLat0 * cosC - yRel * sinLat0 * sinC
    lonKras = lon0Rad + math.atan2(numerator, denominator)
    
    dx = 2.3287
    dy = -147.0425
    dz = -92.0802
    sec_to_rad = math.pi / (180.0 * 3600.0)
    rx = 0.3092483 * sec_to_rad
    ry = -0.32482185 * sec_to_rad
    rz = -0.49729934 * sec_to_rad
    s = 5.68906266 / 1000000.0
    
    N = KRASOVSKY_A / math.sqrt(1 - KRASOVSKY_E2 * math.sin(latKras) * math.sin(latKras))
    xCart = N * math.cos(latKras) * math.cos(lonKras)
    yCart = N * math.cos(latKras) * math.sin(lonKras)
    zCart = N * (1 - KRASOVSKY_E2) * math.sin(latKras)
    
    xWgs = xCart + dx - rz * yCart + ry * zCart + s * xCart
    yWgs = yCart + dy + rz * xCart - rx * zCart + s * yCart
    zWgs = zCart + dz - ry * xCart + rx * yCart + s * zCart
    
    lon = math.atan2(yWgs, xWgs)
    p = math.sqrt(xWgs * xWgs + yWgs * yWgs)
    lat = math.atan2(zWgs, p * (1 - WGS84_E2))
    for i in range(5):
        sinLat = math.sin(lat)
        N_WGS = WGS84_A / math.sqrt(1 - WGS84_E2 * sinLat * sinLat)
        lat = math.atan2(zWgs + WGS84_E2 * N_WGS * sinLat, p)
    return math.degrees(lat), math.degrees(lon)

usr_x = 445681.963
usr_y = 731125.412 

# What if we pass Northing as X, Easting as Y literally to the Java formula?
j_lat, j_lon = java_helmert(usr_x, usr_y)

transformer = Transformer.from_crs('EPSG:3844', 'EPSG:4326')
e_lat, e_lon = transformer.transform(usr_x, usr_y)

diff_m = math.sqrt(((e_lat-j_lat)*111320)**2 + ((e_lon-j_lon)*111320*math.cos(math.radians(e_lat)))**2)
print(f"Difference in meters (Reversed passing): {diff_m:.2f} m")
