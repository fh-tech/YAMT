package org.itp1.yamtcli.config;


import org.apache.commons.cli.*;
import org.itp1.yamtlib.config.ConfigGenerator;
import org.itp1.yamtlib.config.IncompleteYamtConfig;
import org.itp1.yamtlib.errors.YamtException;


/**
 * A <Code>{@link ConfigGenerator}</Code> which uses the programs arguments to parse an incomplete org.itp1.config.
 */
public class CliConfigGenerator implements ConfigGenerator {

    private static Options OPTIONS = new Options().addOption(
            Option.builder("o")
                    .longOpt("out-dir")
                    .hasArg(true)
                    .required()
                    .desc("Specify the output directory where all" +
                            " renamed music files are stored")
                    .build())
            .addOption(
                    Option.builder("m")
                            .longOpt("music")
                            .required(false)
                            .hasArg()
                            .argName("music-dir")
                            .build())
            .addOption(
                    Option.builder("f")
                            .longOpt("format")
                            .required(false)
                            .hasArg()
                            .valueSeparator('=')
                            .argName("FormatString")
                            .build()
            );

    private final String[] args;


    public CliConfigGenerator(String[] args) {
        this.args = args;
    }


    private CommandLine parseCli() throws ParseException {
        CommandLineParser p = new DefaultParser();
        return p.parse(OPTIONS, this.args, true);
    }


    /**
     * @return an <Code>{@link IncompleteYamtConfig}</Code> parsed from program arguments passed to the constructor of this class.
     * This operation may fail when required arguments are not passed to the program.
     * @throws YamtException.ConfigException when required arguments are not passed, or when arguments are in incompatible formats (e.g an int with value "abc")
     */
    @Override
    public IncompleteYamtConfig generate() throws YamtException.ConfigException {
      return null;
    }
}

