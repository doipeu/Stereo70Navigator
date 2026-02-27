package com.stereo70.navigator;

import java.io.Serializable;

public class Coordinate implements Serializable {
    private long id;
    private String name;
    private double stereoX;
    private double stereoY;
    private boolean isFavorite;
    private long timestamp;

    public Coordinate(String name, double stereoX, double stereoY) {
        this.name = name;
        this.stereoX = stereoX;
        this.stereoY = stereoY;
        this.isFavorite = false;
        this.timestamp = System.currentTimeMillis();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getStereoX() {
        return stereoX;
    }

    public void setStereoX(double stereoX) {
        this.stereoX = stereoX;
    }

    public double getStereoY() {
        return stereoY;
    }

    public void setStereoY(double stereoY) {
        this.stereoY = stereoY;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return name + " (X: " + stereoX + ", Y: " + stereoY + ")";
    }
}
