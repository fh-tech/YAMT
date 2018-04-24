package org.itp1.yamtcli.config;


import org.itp1.yamtlib.config.ConfigGenerator;
import org.itp1.yamtlib.config.IncompleteYamtConfig;
import org.itp1.yamtlib.config.MetaDataStrategy;
import org.itp1.yamtlib.errors.YamtException;

import java.io.File;
import java.util.stream.Collectors;


/**
 * A <Code>{@link ConfigGenerator}</Code> which uses the programs arguments to parse an incomplete org.itp1.config.
 */
public class CliConfigGenerator implements ConfigGenerator {

    private final Args args;

    public CliConfigGenerator(Args args) {
        this.args = args;
    }

    /**
     * @return an <Code>{@link IncompleteYamtConfig}</Code> parsed from program arguments passed to the constructor of this class.
     * This operation may fail when required arguments are not passed to the program.
     * @throws YamtException.ConfigException when required arguments are not passed, or when arguments are in incompatible formats (e.g an int with value "abc")
     */
    @Override
    public IncompleteYamtConfig generate() throws YamtException.ConfigException {

        return new IncompleteYamtConfig(
                args.getMusicSource().stream().map(File::new).collect(Collectors.toList()),
                new File(args.getOutputDirectory()).getAbsoluteFile(),
                args.getFormat(),
                args.isSearchFilesRecursive(),
                args.isMoveFiles(),
                MetaDataStrategy.fromString(args.getStrategy())
        );
    }
}

