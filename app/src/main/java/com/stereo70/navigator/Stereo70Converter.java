package com.stereo70.navigator;

/**
 * Converter for Stereo 70 (Romanian projection system) to WGS84 (GPS coordinates)
 * Based on the official Romanian projection parameters
 */
public class Stereo70Converter {

    // Stereo 70 projection parameters
    private static final double STEREO70_LAT0 = 46.0; // Central parallel (degrees)
    private static final double STEREO70_LON0 = 25.0; // Central meridian (degrees)
    private static final double STEREO70_K0 = 0.99975; // Scale factor
    private static final double STEREO70_X0 = 500000.0; // False Easting
    private static final double STEREO70_Y0 = 500000.0; // False Northing

    // Krasovsky 1940 ellipsoid parameters
    private static final double KRASOVSKY_A = 6378245.0; // Semi-major axis
    private static final double KRASOVSKY_B = 6356863.019; // Semi-minor axis
    private static final double KRASOVSKY_E2 = 0.006693421622966; // First eccentricity squared

    // WGS84 ellipsoid parameters
    private static final double WGS84_A = 6378137.0;
    private static final double WGS84_B = 6356752.314245;
    private static final double WGS84_E2 = 0.00669437999014;

    public static class GPSCoordinate {
        public double latitude;
        public double longitude;

        public GPSCoordinate(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        @Override
        public String toString() {
            return String.format("%.6f, %.6f", latitude, longitude);
        }
    }

    private static boolean isInitialized = false;
    private static org.locationtech.proj4j.CoordinateReferenceSystem sourceCRS;
    private static org.locationtech.proj4j.CoordinateReferenceSystem targetCRS;
    private static org.locationtech.proj4j.CoordinateTransform transform;
    private static NTv2Grid ntv2Grid;

    /**
     * Initialize grid and proj4j
     */
    public static void init(android.content.Context context) {
        if (isInitialized) return;
        try {
            ntv2Grid = new NTv2Grid();
            ntv2Grid.load(context, "stereo70_etrs89A.gsb");

            org.locationtech.proj4j.CRSFactory factory = new org.locationtech.proj4j.CRSFactory();
            // Fara parametrul +nadgrids care dadea eroare in org.locationtech.proj4j
            String customProj = "+proj=sterea +lat_0=46 +lon_0=25 +k=0.99975 +x_0=500000 +y_0=500000 +ellps=krass +units=m +no_defs";
            sourceCRS = factory.createFromParameters("EPSG:3844_Grid", customProj);
            targetCRS = factory.createFromName("EPSG:4326");
            
            org.locationtech.proj4j.CoordinateTransformFactory ctf = new org.locationtech.proj4j.CoordinateTransformFactory();
            transform = ctf.createTransform(sourceCRS, targetCRS);
            isInitialized = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Convert Stereo 70 coordinates to GPS (WGS84)
     * @param x Stereo 70 X coordinate (Northing usually, wait, Stereo 70 X is North, Y is East)
     *        But standard coordinate systems take Easting for X, Northing for Y.
     *        Our method is called as stereo70ToGPS(easting, northing) from MainActivity
     * @param y Stereo 70 Y coordinate
     * @return GPSCoordinate with latitude and longitude
     */
    public static GPSCoordinate stereo70ToGPS(double x, double y) {
        if (isInitialized && transform != null) {
            try {
                org.locationtech.proj4j.ProjCoordinate src = new org.locationtech.proj4j.ProjCoordinate(x, y);
                org.locationtech.proj4j.ProjCoordinate tgt = new org.locationtech.proj4j.ProjCoordinate();
                transform.transform(src, tgt);
                // Proj4J EPSG:4326 output: X=longitude, Y=latitude
                
                // Add the true precise TransDatRO NTv2 shift parsed natively
                double[] shift = ntv2Grid.getShift(tgt.y, tgt.x);
                double finalLat = tgt.y + shift[0];
                double finalLon = tgt.x + shift[1];

                return new GPSCoordinate(finalLat, finalLon);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        // Fallback: Extremely simple Spherical Stereographic + Helmert (Has large bounds of error 300m)
        double xRel = x - STEREO70_X0;
        double yRel = y - STEREO70_Y0;

        double lat0Rad = Math.toRadians(STEREO70_LAT0);
        double lon0Rad = Math.toRadians(STEREO70_LON0);

        double rho = Math.sqrt(xRel * xRel + yRel * yRel);
        double c = 2.0 * Math.atan2(rho, 2.0 * KRASOVSKY_A * STEREO70_K0);

        double sinC = Math.sin(c);
        double cosC = Math.cos(c);
        double sinLat0 = Math.sin(lat0Rad);
        double cosLat0 = Math.cos(lat0Rad);

        double latKras = (rho == 0) ? lat0Rad : Math.asin(cosC * sinLat0 + (yRel * sinC * cosLat0 / rho));
        double lonKras = (rho == 0) ? lon0Rad : lon0Rad + Math.atan2(xRel * sinC, rho * cosLat0 * cosC - yRel * sinLat0 * sinC);

        double dx = 2.3287;
        double dy = -147.0425;
        double dz = -92.0802;
        
        double secToRad = Math.PI / (180.0 * 3600.0);
        double rx = 0.3092483 * secToRad;
        double ry = -0.32482185 * secToRad;
        double rz = -0.49729934 * secToRad;
        double s = 5.68906266 / 1000000.0;

        double N = KRASOVSKY_A / Math.sqrt(1 - KRASOVSKY_E2 * Math.sin(latKras) * Math.sin(latKras));
        double xCart = N * Math.cos(latKras) * Math.cos(lonKras);
        double yCart = N * Math.cos(latKras) * Math.sin(lonKras);
        double zCart = N * (1 - KRASOVSKY_E2) * Math.sin(latKras);

        double xWgs = xCart + dx - rz * yCart + ry * zCart + s * xCart;
        double yWgs = yCart + dy + rz * xCart - rx * zCart + s * yCart;
        double zWgs = zCart + dz - ry * xCart + rx * yCart + s * zCart;

        double lon = Math.atan2(yWgs, xWgs);
        double p = Math.sqrt(xWgs * xWgs + yWgs * yWgs);
        double lat = Math.atan2(zWgs, p * (1 - WGS84_E2));

        for (int i = 0; i < 5; i++) {
            double sinLat = Math.sin(lat);
            double N_WGS = WGS84_A / Math.sqrt(1 - WGS84_E2 * sinLat * sinLat);
            lat = Math.atan2(zWgs + WGS84_E2 * N_WGS * sinLat, p);
        }

        return new GPSCoordinate(Math.toDegrees(lat), Math.toDegrees(lon));
    }

    /**
     * Validate Stereo 70 coordinates
     */
    public static boolean isValidStereo70(double x, double y) {
        return x >= 100000 && x <= 900000 && y >= 100000 && y <= 1000000;
    }
}
