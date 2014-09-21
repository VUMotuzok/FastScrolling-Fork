package de.santiv.fastscrolling.enums;

public enum Strings {
    COMPONENT_NAME("FastScrollingPlugin"),

    CONF__DISPLAY_NAME("Fast-Scrolling"),
    CONF__STEP("Step"),
    CONF__HOTKEY("Hotkey"),
    CONF__REOPEN_INFO("(You need to reopen your editors or restart the application.)");

    private String description;

    Strings(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static String getPropertyPath(Strings value) {
        return "de.santiv.fastscrolling." + value.name();
    }
}
