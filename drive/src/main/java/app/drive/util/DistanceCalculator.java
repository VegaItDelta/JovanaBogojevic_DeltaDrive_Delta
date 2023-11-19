package app.drive.util;

import app.drive.model.dto.LocationDto;

public class DistanceCalculator {

    private static final double EARTH_RADIUS = 6371.0;

    public static double calculateHaversineDistance(LocationDto location1, double latitude, double longitude) {
        var lat1 = Math.toRadians(location1.getLatitude());
        var lon1 = Math.toRadians(location1.getLongitude());
        var lat2 = Math.toRadians(latitude);
        var lon2 = Math.toRadians(longitude);

        var dLat = lat2 - lat1;
        var dLon = lon2 - lon1;

        var a = Math.pow(Math.sin(dLat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dLon / 2), 2);
        var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }
}
