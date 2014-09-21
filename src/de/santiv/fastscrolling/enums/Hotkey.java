package de.santiv.fastscrolling.enums;


public enum Hotkey {
    SHIFT("Shift", 16),
    CTRL("Ctrl", 17),
    ALT("Alt", 18),
    CTRL_ALT("Ctrl + Alt", 17, 18),
    CTRL_SHIFT("Ctrl + Shift", 16, 17);

    private String name;
    private int[] keyCodes;

    Hotkey(String name, int... keyCodes) {
        this.name = name;
        this.keyCodes = keyCodes;
    }

    public String getName() {
        return name;
    }

    public int[] getKeyCodes() {
        return keyCodes;
    }

    @Override
    public String toString() {
        return "[" + getName() + "]";
    }
}
