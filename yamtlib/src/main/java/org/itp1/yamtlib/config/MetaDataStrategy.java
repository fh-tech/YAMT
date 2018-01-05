package org.itp1.yamtlib.config;

import org.itp1.yamtlib.errors.YamtException;

public enum MetaDataStrategy {

    FETCH_NEVER, FETCH_REQUIRED, FETCH_ALWAYS;

    public static MetaDataStrategy fromString(String s) throws YamtException.ConfigException {
        switch (s) {
            case "never":
                return FETCH_NEVER;
            case "missing":
                return FETCH_REQUIRED;
            case "always":
                return FETCH_ALWAYS;
        }
        throw new YamtException.ConfigException("Illegal String used to create metadata strategy: " + s);
    }
}
