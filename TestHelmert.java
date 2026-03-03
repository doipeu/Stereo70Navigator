public class TestHelmert {
    public static void main(String[] args) {
        double latKras = Math.toRadians(44.4);
        double lonKras = Math.toRadians(26.1);
        
        double a = 6378245.0;
        double e2 = 0.006693421622966;
        
        double n = a / Math.sqrt(1 - e2 * Math.sin(latKras) * Math.sin(latKras));
        double xCart = n * Math.cos(latKras) * Math.cos(lonKras);
        double yCart = n * Math.cos(latKras) * Math.sin(lonKras);
        double zCart = n * (1 - e2) * Math.sin(latKras);
        
        double dx = 2.329;
        double dy = -147.042;
        double dz = -92.08;
        
        double secToRad = Math.PI / (180.0 * 3600.0);
        double rx = 0.309 * secToRad;
        double ry = -0.325 * secToRad;
        double rz = -0.497 * secToRad;
        double s = 5.69 / 1000000.0;
        
        // Position Vector transformation (used by PROJ)
        double px = xCart + dx + s * xCart - rz * yCart + ry * zCart;
        double py = yCart + dy + rz * xCart + s * yCart - rx * zCart;
        double pz = zCart + dz - ry * xCart + rx * yCart + s * zCart;
        
        double wgsA = 6378137.0;
        double wgsE2 = 0.00669437999014;
        
        double lon = Math.atan2(py, px);
        double p = Math.sqrt(px * px + py * py);
        double lat = Math.atan2(pz, p * (1 - wgsE2));
        
        for (int i = 0; i < 5; i++) {
            double sinLat = Math.sin(lat);
            double nWgs = wgsA / Math.sqrt(1 - wgsE2 * sinLat * sinLat);
            lat = Math.atan2(pz + wgsE2 * nWgs * sinLat, p);
        }
        
        System.out.println("Result: " + Math.toDegrees(lat) + " " + Math.toDegrees(lon));
    }
}
