import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.locationtech.proj4j.CRSFactory;
import org.locationtech.proj4j.CoordinateReferenceSystem;
import org.locationtech.proj4j.CoordinateTransform;
import org.locationtech.proj4j.CoordinateTransformFactory;
import org.locationtech.proj4j.ProjCoordinate;

public class test_exact_java {
    public static void main(String[] args) throws Exception {
        // Grid Logic
        byte[] fileData = Files.readAllBytes(Paths.get("app/src/main/assets/stereo70_etrs89A.gsb"));
        ByteBuffer buffer = ByteBuffer.wrap(fileData);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        double slat = buffer.getDouble(176 + 4 * 16 + 8) / 3600.0;
        double elon = -buffer.getDouble(176 + 6 * 16 + 8) / 3600.0; 
        double latinc = buffer.getDouble(176 + 8 * 16 + 8) / 3600.0;
        double loninc = buffer.getDouble(176 + 9 * 16 + 8) / 3600.0;
        int gsCount = buffer.getInt(176 + 10 * 16 + 8);
        int cols = (int) Math.round((buffer.getDouble(176 + 7 * 16 + 8) - buffer.getDouble(176 + 6 * 16 + 8)) / buffer.getDouble(176 + 9 * 16 + 8)) + 1;
        float[] latShifts = new float[gsCount];
        float[] lonShifts = new float[gsCount];
        int offset = 352;
        for (int i = 0; i < gsCount; i++) {
            latShifts[i] = buffer.getFloat(offset);
            lonShifts[i] = buffer.getFloat(offset + 4);
            offset += 16;
        }

        // Proj4J Logic
        CRSFactory factory = new CRSFactory();
        String customProj = "+proj=sterea +lat_0=46 +lon_0=25 +k=0.99975 +x_0=500000 +y_0=500000 +ellps=krass +units=m +no_defs";
        CoordinateReferenceSystem sourceCRS = factory.createFromParameters("EPSG:3844_Grid", customProj);
        CoordinateReferenceSystem targetCRS = factory.createFromName("EPSG:4326");
        CoordinateTransformFactory ctf = new CoordinateTransformFactory();
        CoordinateTransform transform = ctf.createTransform(sourceCRS, targetCRS);

        // Test Point user: X = 445681.963, Y = 731125.412
        // Our app does: stereo70ToGPS(Y, X) -> Easting(731125), Northing(445681)
        double easting = 731125.412;
        double northing = 445681.963;

        ProjCoordinate src = new ProjCoordinate(easting, northing);
        ProjCoordinate tgt = new ProjCoordinate();
        transform.transform(src, tgt);

        System.out.println("Proj4J result: Lat=" + tgt.y + " Lon=" + tgt.x);

        double row = (tgt.y - slat) / latinc;
        int rowNum = (int) Math.floor(row);
        double rowFract = row - rowNum;
        double col = (elon - tgt.x) / loninc;
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

        double finalLat = tgt.y + (shiftLat / 3600.0);
        double finalLon = tgt.x - (shiftLon / 3600.0);

        System.out.println("Final Result with NTv2: Lat=" + finalLat + " Lon=" + finalLon);
    }
}
