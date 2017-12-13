package org.itp1.yamtlib.config;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * The Configuration of the Yamt application.
 * All user and environment configuration is in this file.
 * To directly create a YamtConfig either use the builder class nested inside,
 * or the specialized builder <Code>{@link IncompleteYamtConfig}</Code>, which can merge with others and verify if it is complete.
 * This Config is only in a complete state and cannot be constructed incomplete.
 */
@Value
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class YamtConfig {

    @NonNull
    private final List<File> musicSource;

    @NonNull
    private final File outputDirectory;

    private final String format;

    @NonNull
    private final Boolean searchFilesRecursive;

    @NonNull
    private final Boolean moveOnRename;

    @NonNull
    private final MetaDataStrategy strategy;

    @NonNull public Optional<String> getFormat(){
        return Optional.ofNullable(format);
    }

}
