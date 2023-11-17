package app.drive.model.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TripRequestStatus {
    UNCONFIRMED,
    ACCEPTED,
    REJECTED,
    COMPLETED
}
