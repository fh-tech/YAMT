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

    public static class MissingMetaDataException extends YamtException {
        public MissingMetaDataException() {
        }

        public MissingMetaDataException(String message) {
            super(message);
        }

        public MissingMetaDataException(String message, Throwable cause) {
            super(message, cause);
        }

        public MissingMetaDataException(Throwable cause) {
            super(cause);
        }
    }

    public static class MetaDataException extends YamtException {
        public MetaDataException() {
        }

        public MetaDataException(String message) {
            super(message);
        }

        public MetaDataException(String message, Throwable cause) {
            super(message, cause);
        }

        public MetaDataException(Throwable cause) {
            super(cause);
        }
    }

    public static class MetaDataException extends YamtException {
        public MetaDataException() {
        }

        public MetaDataException(String message) {
            super(message);
        }

        public MetaDataException(String message, Throwable cause) {
            super(message, cause);
        }

        public MetaDataException(Throwable cause) {
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
