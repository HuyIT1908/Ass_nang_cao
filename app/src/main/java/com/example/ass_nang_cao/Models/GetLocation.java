package com.example.ass_nang_cao.Models;

public class GetLocation {
    private Double latitude;
    private Double longitude;

    public GetLocation() {
    }

    public GetLocation(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "GetLocation{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
