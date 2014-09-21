package de.santiv.fastscrolling.enums;

public enum FastScrollingHotkey {
    CTRL("Ctrl", 17),
    SHIFT("Shift", 18),
    CTRL_SHIFT("Ctrl+Shift", 19);

    private String name;
    private int keyCode;

    FastScrollingHotkey(String name, int keyCode) {
        this.name = name;
        this.keyCode = keyCode;
    }

    public String getName() {
        return name;
    }

    public int getKeyCode() {
        return keyCode;
    }

    @Override
    public String toString() {
        return getName();
    }
}
