import math
import struct

def parse_ntv2_and_get_shift(grid_path, lat_deg, lon_deg):
    with open(grid_path, 'rb') as f:
        data = f.read()

    def get_double(record_idx):
        start = 176 + record_idx * 16 + 8
        return struct.unpack('<d', data[start:start+8])[0]

    slat = get_double(4) / 3600.0
    nlat = get_double(5) / 3600.0
    elon = -get_double(6) / 3600.0  # Positive East
    wlon = -get_double(7) / 3600.0  # Positive East
    latinc = get_double(8) / 3600.0
    loninc = get_double(9) / 3600.0
    gs_count = struct.unpack('<i', data[176+10*16+8:176+10*16+12])[0]

    cols = int(round((get_double(7) - get_double(6)) / get_double(9))) + 1
    
    # Calculate indices
    # Row 0 is at S_LAT (southmost)
    row = (lat_deg - slat) / latinc
    rowNum = int(math.floor(row))
    rowFract = row - rowNum

    # Col 0 is at E_LONG (easternmost)
    # Since Western coords have positive values in NTv2, 'East' is smaller.
    # col index increments towards West
    col = (elon - lon_deg) / loninc
    colNum = int(math.floor(col))
    colFract = col - colNum

    def get_node(r, c):
        idx = r * cols + c
        if idx < 0 or idx >= gs_count: return 0.0, 0.0
        offset = 352 + idx * 16
        return struct.unpack('<ff', data[offset:offset+8])

    v00 = get_node(rowNum, colNum)
    v10 = get_node(rowNum, colNum + 1)
    v01 = get_node(rowNum + 1, colNum)
    v11 = get_node(rowNum + 1, colNum + 1)

    lat_sh = v00[0]*(1-colFract)*(1-rowFract) + v10[0]*colFract*(1-rowFract) + v01[0]*(1-colFract)*rowFract + v11[0]*colFract*rowFract
    lon_sh = v00[1]*(1-colFract)*(1-rowFract) + v10[1]*colFract*(1-rowFract) + v01[1]*(1-colFract)*rowFract + v11[1]*colFract*rowFract

    return lat_sh / 3600.0, -lon_sh / 3600.0

lat_sh, lon_sh = parse_ntv2_and_get_shift('app/src/main/assets/stereo70_etrs89A.gsb', 45.474071, 27.956795)
print(lat_sh, lon_sh)

