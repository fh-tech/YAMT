package org.itp1.yamtlib.errors;

public class YamtException extends Exception {

    public YamtException() {
    }

    public YamtException(String message) {
        super(message);
    }

    public YamtException(String message, Throwable cause) {
        super(message, cause);
    }

    public YamtException(Throwable cause) {
        super(cause);
    }

    public static class ConfigException extends YamtException {
        public ConfigException() {
        }

        public ConfigException(String message) {
            super(message);
        }

        public ConfigException(String message, Throwable cause) {
            super(message, cause);
        }

        public ConfigException(Throwable cause) {
            super(cause);
        }
    }
}
