import org.locationtech.proj4j.CRSFactory;
import org.locationtech.proj4j.CoordinateReferenceSystem;
import org.locationtech.proj4j.CoordinateTransform;
import org.locationtech.proj4j.CoordinateTransformFactory;
import org.locationtech.proj4j.ProjCoordinate;

public class TestGrid {
    public static void main(String[] args) {
        try {
            CRSFactory factory = new CRSFactory();
            String customProj = "+proj=sterea +lat_0=46 +lon_0=25 +k=0.99975 +x_0=500000 +y_0=500000 +ellps=krass +nadgrids=app/src/main/assets/stereo70_etrs89A.gsb +units=m +no_defs";
            CoordinateReferenceSystem sourceCRS = factory.createFromParameters("EPSG:3844_Grid", customProj);
            CoordinateReferenceSystem targetCRS = factory.createFromName("EPSG:4326");
            
            CoordinateTransformFactory ctf = new CoordinateTransformFactory();
            CoordinateTransform transform = ctf.createTransform(sourceCRS, targetCRS);
            
            ProjCoordinate src = new ProjCoordinate(731125.412, 445681.963);
            ProjCoordinate tgt = new ProjCoordinate();
            transform.transform(src, tgt);
            System.out.println("Result: " + tgt.y + ", " + tgt.x);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
