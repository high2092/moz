package com.mojac.moz.exception.internal;

public class RoomStatusIsNotPlayingException extends InternalException {

    public RoomStatusIsNotPlayingException() {
        super(Error.ROOM_STATUS_IS_NOT_PLAYING.getMessage());
    }
}
