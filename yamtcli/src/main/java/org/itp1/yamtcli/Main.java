package org.itp1.yamtcli;

import org.itp1.yamtcli.config.Args;
import org.itp1.yamtcli.config.CliConfigGenerator;
import org.itp1.yamtlib.Yamt;
import org.itp1.yamtlib.config.ConfigFactory;
import org.itp1.yamtlib.config.ConfigGenerator;
import org.itp1.yamtlib.config.YamtConfig;

public class Main {
    public static void main(String[] args) throws Exception {
        Args arg = Args.generate(args);

        if (arg.isHelp()) {
            arg.thisProgram.usage();
            System.exit(0);
        }

        ConfigGenerator cliConfiGen = new CliConfigGenerator(arg);

        YamtConfig config = ConfigFactory.generate(cliConfiGen);

        Yamt yamt = new Yamt();


        try {
            yamt.runYamt(config);
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }

}
