package org.itp1.yamtlib.config;

import lombok.*;

import java.nio.file.Path;

/**
 * The Configuration of the Yamt application.
 * All user and environment configuration is in this file.
 * To directly create a YamtConfig either use the builder class nested inside,
 * or the specialized builder <Code>{@link IncompleteYamtConfig}</Code>, which can merge with others and verify if it is complete.
 * This Config is only in a complete state and cannot be constructed incomplete.
 */
@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class YamtConfig {

    @NonNull
    private final Path musicDir;

    @NonNull
    private final Path outDir;

    private final String format;
}
