package com.mojac.moz.exception.api;

public class RoomIsFullException extends ApiException {

    public RoomIsFullException() {
        this.error = Error.ROOM_IS_FULL;
    }
}
