import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Paths;
public class test_ntv2 {
    public static void main(String[] args) throws Exception {
        byte[] fileData = Files.readAllBytes(Paths.get("app/src/main/assets/stereo70_etrs89A.gsb"));
        ByteBuffer buffer = ByteBuffer.wrap(fileData);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        double slat = buffer.getDouble(176 + 4 * 16 + 8) / 3600.0;
        double nlat = buffer.getDouble(176 + 5 * 16 + 8) / 3600.0;
        double elon = -buffer.getDouble(176 + 6 * 16 + 8) / 3600.0; 
        double wlon = -buffer.getDouble(176 + 7 * 16 + 8) / 3600.0; 
        double latinc = buffer.getDouble(176 + 8 * 16 + 8) / 3600.0;
        double loninc = buffer.getDouble(176 + 9 * 16 + 8) / 3600.0;
        int gsCount = buffer.getInt(176 + 10 * 16 + 8);
        int cols = (int) Math.round((buffer.getDouble(176 + 7 * 16 + 8) - buffer.getDouble(176 + 6 * 16 + 8)) / buffer.getDouble(176 + 9 * 16 + 8)) + 1;
        int rows = gsCount / cols;
        System.out.println("Cols: " + cols + " Rows: " + rows + " Total: " + gsCount);
        
        float[] latShifts = new float[gsCount];
        float[] lonShifts = new float[gsCount];
        int offset = 352;
        for (int i = 0; i < gsCount; i++) {
            latShifts[i] = buffer.getFloat(offset);
            lonShifts[i] = buffer.getFloat(offset + 4);
            offset += 16;
        }
        
        double lat = 45.474071, lon = 27.956795;
        double row = (lat - slat) / latinc;
        int rowNum = (int) Math.floor(row);
        double rowFract = row - rowNum;
        double col = (elon - lon) / loninc;
        int colNum = (int) Math.floor(col);
        double colFract = col - colNum;
        
        float lat00 = latShifts[rowNum * cols + colNum];
        float lon00 = lonShifts[rowNum * cols + colNum];
        
        float lat10 = latShifts[rowNum * cols + (colNum+1)];
        float lon10 = lonShifts[rowNum * cols + (colNum+1)];
        
        float lat01 = latShifts[(rowNum+1) * cols + colNum];
        float lon01 = lonShifts[(rowNum+1) * cols + colNum];
        
        float lat11 = latShifts[(rowNum+1) * cols + (colNum+1)];
        float lon11 = lonShifts[(rowNum+1) * cols + (colNum+1)];
        
        double shiftLat = lat00 * (1 - colFract) * (1 - rowFract)
                + lat10 * colFract * (1 - rowFract)
                + lat01 * (1 - colFract) * rowFract
                + lat11 * colFract * rowFract;
        double shiftLon = lon00 * (1 - colFract) * (1 - rowFract)
                + lon10 * colFract * (1 - rowFract)
                + lon01 * (1 - colFract) * rowFract
                + lon11 * colFract * rowFract;
        System.out.println("ShiftLat: " + (shiftLat / 3600.0) + " ShiftLon: " + (-shiftLon / 3600.0));
    }
}
