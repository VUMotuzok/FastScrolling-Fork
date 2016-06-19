package de.santiv.fastscrolling.enums;

import com.intellij.ide.util.PropertiesComponent;

public enum Config {
    STEP(500),
    HOTKEY(Hotkey.ALT.name())
    ;

    public static final String PROPERTY_PATH_PREFIX = "de.santiv.fastscrolling.";

    private Object defaultValue;

    Config(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public String getPropertyPath() {
        return PROPERTY_PATH_PREFIX + name();
    }

    public static void saveValue(Config key, String value) {
        PropertiesComponent.getInstance().setValue(key.getPropertyPath(), value);
    }

    public static String loadValue(Config key) {
        return PropertiesComponent.getInstance().getValue(key.getPropertyPath(), String.valueOf(key.getDefaultValue()));
    }

    public static boolean loadBooleanValue(Config key) {
        return PropertiesComponent.getInstance().getBoolean(key.getPropertyPath(),
                key.getDefaultValue() instanceof Boolean ? ((Boolean) key.getDefaultValue()).booleanValue() : false);
    }

    public static void saveBooleanValue(Config key, boolean value) {
        saveValue(key, String.valueOf(value));
    }


    public static int loadIntValue(Config key) {
        return PropertiesComponent.getInstance().getOrInitInt(key.getPropertyPath(),
                key.getDefaultValue() instanceof Integer ? ((Integer) key.getDefaultValue()).intValue() : 0);
    }

    public static void saveIntValue(Config key, int value) {
        saveValue(key, String.valueOf(value));
    }

    public static <E extends Enum<E>> E loadEnumValue(Config key, Class<E> enumClass) {
        return Enum.valueOf(enumClass, PropertiesComponent.getInstance().getValue(key.getPropertyPath(),
                key.getDefaultValue().toString()));
    }
}
