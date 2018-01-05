package org.itp1.yamtcli.config;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import lombok.Data;

import java.util.List;

@Data
public class Args {

    public final JCommander thisProgram;
    @Parameter(description = "Music Files/directories", required = true)
    private List<String> musicSource;
    @Parameter(names = {"-o", "--output-dir"}, description = "Specify a folder to which the music files are moved/copied to. Default: the current folder")
    private String outputDirectory = ".";
    @Parameter(names = {"-f", "--format"}, description = "Define a format string to rename music files.")
    private String format = "";
    @Parameter(names = {"-r", "--recursive"}, description = "Search folders recursively for music files.")
    private boolean searchFilesRecursive = false;
    @Parameter(names = {"--move"}, description = "Move files to new destination.")
    private boolean moveFiles = false;
    @Parameter(names = {"--copy"}, description = "Copy files to new destination.")
    private boolean copyFiles = true;
    @Parameter(names = {"--meta"}, validateValueWith = StrategyValidator.class, description = "Choose whether YAMT tries to fetch music metadata every time, only missing or never. Default: never, Possible values: never, missing, always")
    private String strategy = "never";
    @Parameter(names = {"--help", "-h"}, help = true)
    private boolean help;

    public static Args generate(String... argv) {
        Args args = new Args(JCommander.newBuilder().programName("yamt").build());
        args.thisProgram.addObject(args);
        args.thisProgram.parse(argv);
        return args;
    }

}
