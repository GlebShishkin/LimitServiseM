package ru.stepup.limitservise.enumerator;

public enum DirectionType {
    debiting(0), crediting(1);

    private int directionTypeId;
    private DirectionType(int directionTypeId) {
        this.directionTypeId = directionTypeId;
    }
    public int getDirectionTypeId() {
        return directionTypeId;
    }

    public int getDirectionType() {
        return directionTypeId;
    }

}