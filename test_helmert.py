import math

latKras = math.radians(44.4)
lonKras = math.radians(26.1)

a = 6378245.0
e2 = 0.006693421622966

n = a / math.sqrt(1 - e2 * math.sin(latKras) * math.sin(latKras))
xCart = n * math.cos(latKras) * math.cos(lonKras)
yCart = n * math.cos(latKras) * math.sin(lonKras)
zCart = n * (1 - e2) * math.sin(latKras)

dx = 2.329
dy = -147.042
dz = -92.08

secToRad = math.pi / (180.0 * 3600.0)
rx = 0.309 * secToRad
ry = -0.325 * secToRad
rz = -0.497 * secToRad
s = 5.69 / 1000000.0

# Position Vector transformation (used by PROJ)
# In PROJ towgs84:
# X_out = X_in + dx + s*X_in - rz*Y_in + ry*Z_in
# Y_out = Y_in + dy + rz*X_in + s*Y_in - rx*Z_in
# Z_out = Z_in + dz - ry*X_in + rx*Y_in + s*Z_in

px = xCart + dx + s * xCart - rz * yCart + ry * zCart
py = yCart + dy + rz * xCart + s * yCart - rx * zCart
pz = zCart + dz - ry * xCart + rx * yCart + s * zCart

wgsA = 6378137.0
wgsE2 = 0.00669437999014

lon = math.atan2(py, px)
p = math.sqrt(px * px + py * py)
lat = math.atan2(pz, p * (1 - wgsE2))

for i in range(5):
    sinLat = math.sin(lat)
    nWgs = wgsA / math.sqrt(1 - wgsE2 * sinLat * sinLat)
    lat = math.atan2(pz + wgsE2 * nWgs * sinLat, p)

print('Result:', math.degrees(lat), math.degrees(lon))
