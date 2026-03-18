package com.pao.laboratory03.bonus.enums;

public enum Status {
    TODO{
        @Override public boolean canTranslateTo(Status next){}
    },
    IN_PROGRESS,
    DONE,
    CANCELLED;

    public abstract boolean canTranslateTo(Status next);
}
