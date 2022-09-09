package com.github.klima7.common.domain.operation;

public enum OperationDirection {
    CLOCKWISE,
    COUNTERCLOCKWISE;

    public OperationDirection reverse() {
        if(this == CLOCKWISE) {
            return COUNTERCLOCKWISE;
        } else {
            return CLOCKWISE;
        }
    }
}
