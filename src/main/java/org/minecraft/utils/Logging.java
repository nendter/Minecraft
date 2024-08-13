package org.minecraft.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Logging {

    public static <T> Logger get(Class<T> c) {
        return LogManager.getLogger(c);
    }

}
