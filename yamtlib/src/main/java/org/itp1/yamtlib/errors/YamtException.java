package org.itp1.yamtlib.errors;

import java.io.File;

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
        public ConfigException() {}

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

    public static class FilesException extends YamtException {
        public FilesException() {}

        public FilesException(String message) { super(message); }

        public FilesException(String message, Throwable cause) {
            super(message, cause);
        }

        public FilesException(Throwable cause) {
            super(cause);
        }
    }
}
