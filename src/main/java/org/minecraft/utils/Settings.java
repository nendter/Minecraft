package org.minecraft.utils;

public class Settings {

    public enum SettingKey{
        WINDOW_NAME("Minecraft"),

        WINDOW_WIDTH(400),
        WINDOW_HEIGHT(400);

        SettingKey(Object def) {
            this.def = def;
        }

        private final Object def;

        public Object getDef() {
            return def;
        }
    }

    public static <T> T get(SettingKey key) {
        // TODO: Get Secret From Local File Or Sth
        return (T) key.getDef();
    }

}
