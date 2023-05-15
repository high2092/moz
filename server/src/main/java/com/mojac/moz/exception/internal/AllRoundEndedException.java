package com.mojac.moz.exception.internal;

public class AllRoundEndedException extends InternalException {

    public AllRoundEndedException() {
        super(Error.ALL_ROUND_ENDED.getMessage());
    }
}
