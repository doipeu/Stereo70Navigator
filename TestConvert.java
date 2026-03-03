import com.stereo70.navigator.Stereo70Converter;

public class TestConvert {
    public static void main(String[] args) {
        // Let's test two different coordinates
        Stereo70Converter.GPSCoordinate c1 = Stereo70Converter.stereo70ToGPS(587000, 322000);
        Stereo70Converter.GPSCoordinate c2 = Stereo70Converter.stereo70ToGPS(400000, 400000);
        System.out.println("C1: Lat " + c1.latitude + ", Lon " + c1.longitude);
        System.out.println("C2: Lat " + c2.latitude + ", Lon " + c2.longitude);
    }
}
