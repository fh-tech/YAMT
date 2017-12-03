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

    public static class MusicException extends YamtException {
        public MusicException() {
        }

        public MusicException(String message) {
            super(message);
        }

        public MusicException(String message, Throwable cause) {
            super(message, cause);
        }

        public MusicException(Throwable cause) {
            super(cause);
        }
    }

    public static class FormatException extends YamtException {
        public FormatException() {
        }

        public FormatException(String message) {
            super(message);
        }

        public FormatException(String message, Throwable cause) {
            super(message, cause);
        }

        public FormatException(Throwable cause) {
            super(cause);
        }
    }

}
