package com.stereo70.navigator;

import android.content.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class NTv2Grid {

    private boolean isLoaded = false;
    private float[] latShifts;
    private float[] lonShifts;

    private double slat, nlat, elon, wlon, latinc, loninc;
    private int rows, cols;

    public void load(Context context, String gridFileName) {
        if (isLoaded) return;
        try {
            File gridFile = new File(context.getFilesDir(), gridFileName);
            if (!gridFile.exists()) {
                InputStream is = context.getAssets().open(gridFileName);
                java.io.FileOutputStream os = new java.io.FileOutputStream(gridFile);
                byte[] buffer = new byte[8192];
                int read;
                while ((read = is.read(buffer)) != -1) {
                    os.write(buffer, 0, read);
                }
                is.close();
                os.flush();
                os.close();
            }

            FileInputStream fis = new FileInputStream(gridFile);
            byte[] fileData = new byte[(int) gridFile.length()];
            fis.read(fileData);
            fis.close();

            ByteBuffer buffer = ByteBuffer.wrap(fileData);
            buffer.order(ByteOrder.LITTLE_ENDIAN);

            // Read header boundaries
            slat = buffer.getDouble(176 + 4 * 16 + 8) / 3600.0;
            nlat = buffer.getDouble(176 + 5 * 16 + 8) / 3600.0;
            elon = -buffer.getDouble(176 + 6 * 16 + 8) / 3600.0; // Positive East longitude
            wlon = -buffer.getDouble(176 + 7 * 16 + 8) / 3600.0; 
            latinc = buffer.getDouble(176 + 8 * 16 + 8) / 3600.0;
            loninc = buffer.getDouble(176 + 9 * 16 + 8) / 3600.0;

            int gsCount = buffer.getInt(176 + 10 * 16 + 8);

            cols = (int) Math.round((buffer.getDouble(176 + 7 * 16 + 8) - buffer.getDouble(176 + 6 * 16 + 8)) / buffer.getDouble(176 + 9 * 16 + 8)) + 1;
            rows = gsCount / cols;

            latShifts = new float[gsCount];
            lonShifts = new float[gsCount];

            int offset = 352;
            for (int i = 0; i < gsCount; i++) {
                latShifts[i] = buffer.getFloat(offset);
                lonShifts[i] = buffer.getFloat(offset + 4);
                offset += 16;
            }

            isLoaded = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double[] getShift(double lat, double lon) {
        if (!isLoaded) return new double[]{0.0, 0.0};

        double row = (lat - slat) / latinc;
        int rowNum = (int) Math.floor(row);
        double rowFract = row - rowNum;

        // Note: 'elon' is positive east, 'wlon' is positive east (wlon < elon theoretically, but in NTv2 E_LONG > W_LONG numerically for positive West)
        double col = (elon - lon) / loninc;
        int colNum = (int) Math.floor(col);
        double colFract = col - colNum;

        float lat00 = getNode(rowNum, colNum, true);
        float lon00 = getNode(rowNum, colNum, false);
        
        float lat10 = getNode(rowNum, colNum + 1, true);
        float lon10 = getNode(rowNum, colNum + 1, false);
        
        float lat01 = getNode(rowNum + 1, colNum, true);
        float lon01 = getNode(rowNum + 1, colNum, false);
        
        float lat11 = getNode(rowNum + 1, colNum + 1, true);
        float lon11 = getNode(rowNum + 1, colNum + 1, false);

        double shiftLat = lat00 * (1 - colFract) * (1 - rowFract)
                + lat10 * colFract * (1 - rowFract)
                + lat01 * (1 - colFract) * rowFract
                + lat11 * colFract * rowFract;

        double shiftLon = lon00 * (1 - colFract) * (1 - rowFract)
                + lon10 * colFract * (1 - rowFract)
                + lon01 * (1 - colFract) * rowFract
                + lon11 * colFract * rowFract;

        return new double[]{shiftLat / 3600.0, -shiftLon / 3600.0};
    }

    private float getNode(int r, int c, boolean isLat) {
        if (r < 0) r = 0;
        if (r >= rows) r = rows - 1;
        if (c < 0) c = 0;
        if (c >= cols) c = cols - 1;
        int idx = r * cols + c;
        if (idx < 0 || idx >= latShifts.length) return 0f;
        return isLat ? latShifts[idx] : lonShifts[idx];
    }
}
