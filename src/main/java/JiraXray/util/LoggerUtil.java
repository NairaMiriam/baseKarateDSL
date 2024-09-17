package JiraXray.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by NairaMasgo on 20/09/2023.
 * Role: QE Engineer
 */
public class LoggerUtil {
    private static final Map<Class, Logger> LOGGER_MAP = (Map) new ConcurrentHashMap<>();

    public static Logger getLogger(Class clazz) {
        return LOGGER_MAP.computeIfAbsent(clazz, LoggerFactory::getLogger);
    }
}

