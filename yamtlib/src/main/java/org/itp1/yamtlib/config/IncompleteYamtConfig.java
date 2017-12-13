package org.itp1.yamtlib.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;
import org.itp1.yamtlib.errors.YamtException;

import java.io.File;
import java.util.List;

/**
 * Specialized Builder for <code>{@link YamtConfig}</code>.
 * Using <code>merge</code> two {@link IncompleteYamtConfig} are merge into the caller. Fields in the caller are only overwritten if they are <code>null</code>.
 */
@Accessors(fluent = true, chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncompleteYamtConfig {

    private List<File> musicSource;

    private File outputDirectory;

    private String format;

    private Boolean searchFilesRecursive;

    private Boolean moveOnRename;

    private MetaDataStrategy strategy;

    /**
     * Merges this instance of {@link IncompleteYamtConfig} with another one, prioritizing the caller.
     * Fields of the caller are replaced with the ones from <code>other</code> only if they are <code>null</code>
     *
     * @param other The other {@link IncompleteYamtConfig} with which this on is merged. Has less priority
     */
    @NonNull
    IncompleteYamtConfig merge(@NonNull IncompleteYamtConfig other) {
        return new IncompleteYamtConfig(
                merge(this.musicSource, other.musicSource),
                merge(this.outputDirectory, other.outputDirectory),
                merge(this.format, other.format),
                merge(this.searchFilesRecursive, other.searchFilesRecursive),
                merge(this.moveOnRename, other.moveOnRename),
                merge(this.strategy, other.strategy)
        );
    }

    /**
     * Verifies if the current State of this Object is a valid Configuration and if it is returns a {@link YamtConfig}.
     * Usually this Object is merged with other {@link IncompleteYamtConfig} and then verified.
     *
     * @return a valid YamtConfig
     * @throws YamtException.ConfigException when a required field is missing
     */
    @NonNull
    public YamtConfig verify() throws YamtException.ConfigException {

        if (musicSource != null
                && outputDirectory != null
                && searchFilesRecursive != null
                && moveOnRename != null
                && strategy != null) {

            return new YamtConfig(musicSource,
                                  outputDirectory,
                                  format,
                                  searchFilesRecursive,
                                  moveOnRename,
                                  strategy);
        } else {
            String error = "";
            if (musicSource ==  null) error+="musicSource, ";
            if (outputDirectory ==  null) error+="outputDirectory, ";
            if (searchFilesRecursive ==  null) error+="searchFilesRecursive, ";
            if (moveOnRename ==  null) error+="moveOnRename, ";
            if (strategy ==  null) error+="strategy, ";
            throw new YamtException.ConfigException("Unable to create complete Yamtconfig. Missing fields: ["+error.substring(0, error.length()-2)+"]");
        }
    }

    private <T> T merge(T t1, T t2) {
        return t1 != null ? t1 : t2;
    }
}
