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

    /**
     * Convert Stereo 70 coordinates to GPS (WGS84)
     * @param x Stereo 70 X coordinate (Easting)
     * @param y Stereo 70 Y coordinate (Northing)
     * @return GPSCoordinate with latitude and longitude
     */
    public static GPSCoordinate stereo70ToGPS(double x, double y) {
        // Step 1: Remove false easting/northing
        double xRel = x - STEREO70_X0;
        double yRel = y - STEREO70_Y0;

        // Step 2: Convert from Stereo70 to Krasovsky lat/lon
        double lat0Rad = Math.toRadians(STEREO70_LAT0);
        double lon0Rad = Math.toRadians(STEREO70_LON0);

        // Calculate rho and c
        double rho = Math.sqrt(xRel * xRel + yRel * yRel);
        double c = 2.0 * Math.atan2(rho, 2.0 * KRASOVSKY_A * STEREO70_K0);

        double sinC = Math.sin(c);
        double cosC = Math.cos(c);
        double sinLat0 = Math.sin(lat0Rad);
        double cosLat0 = Math.cos(lat0Rad);

        // Calculate latitude (Krasovsky)
        double latKras;
        if (rho == 0) {
            latKras = lat0Rad;
        } else {
            double numerator = cosC * sinLat0 + (yRel * sinC * cosLat0 / rho);
            latKras = Math.asin(numerator);
        }

        // Calculate longitude (Krasovsky)
        double lonKras;
        if (rho == 0) {
            lonKras = lon0Rad;
        } else {
            double numerator = xRel * sinC;
            double denominator = rho * cosLat0 * cosC - yRel * sinLat0 * sinC;
            lonKras = lon0Rad + Math.atan2(numerator, denominator);
        }

        // Step 3: Convert from Krasovsky to WGS84 (datum transformation)
        // Using 7-parameter Helmert transformation parameters for Romania (EPSG:3844 to EPSG:4326)
        double dx = 2.3287;
        double dy = -147.0425;
        double dz = -92.0802;
        
        double secToRad = Math.PI / (180.0 * 3600.0);
        double rx = 0.3092483 * secToRad;
        double ry = -0.32482185 * secToRad;
        double rz = -0.49729934 * secToRad;
        double s = 5.68906266 / 1000000.0;

        // Convert to Cartesian coordinates (Krasovsky)
        double N = KRASOVSKY_A / Math.sqrt(1 - KRASOVSKY_E2 * Math.sin(latKras) * Math.sin(latKras));
        double xCart = N * Math.cos(latKras) * Math.cos(lonKras);
        double yCart = N * Math.cos(latKras) * Math.sin(lonKras);
        double zCart = N * (1 - KRASOVSKY_E2) * Math.sin(latKras);

        // Apply 7-parameter Position Vector transformation
        double xWgs = xCart + dx + s * xCart - rz * yCart + ry * zCart;
        double yWgs = yCart + dy + rz * xCart + s * yCart - rx * zCart;
        double zWgs = zCart + dz - ry * xCart + rx * yCart + s * zCart;

        // Convert back to geodetic coordinates (WGS84)
        double lon = Math.atan2(yWgs, xWgs);
        double p = Math.sqrt(xWgs * xWgs + yWgs * yWgs);
        double lat = Math.atan2(zWgs, p * (1 - WGS84_E2));

        // Iterative refinement for latitude
        for (int i = 0; i < 5; i++) {
            double sinLat = Math.sin(lat);
            double N_WGS = WGS84_A / Math.sqrt(1 - WGS84_E2 * sinLat * sinLat);
            lat = Math.atan2(zWgs + WGS84_E2 * N_WGS * sinLat, p);
        }

        // Convert to degrees
        double latDeg = Math.toDegrees(lat);
        double lonDeg = Math.toDegrees(lon);

        return new GPSCoordinate(latDeg, lonDeg);
    }

    /**
     * Validate Stereo 70 coordinates
     * Typical Romanian ranges: X: 200000-800000, Y: 200000-900000
     */
    public static boolean isValidStereo70(double x, double y) {
        return x >= 100000 && x <= 900000 && y >= 100000 && y <= 1000000;
    }
}
