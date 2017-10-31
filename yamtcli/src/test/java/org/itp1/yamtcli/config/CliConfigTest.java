package org.itp1.yamtcli.config;

import org.itp1.errors.YamtException;
import org.itp1.yamtlib.config.IncompleteYamtConfig;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.Optional;

public class CliConfigTest {

    @Test
    public void testCliConfig() {

        String[] args = {"-f", "author-album-single", "-o", "./out"};

        CliConfigGenerator ccg = new CliConfigGenerator(args);

        try {
            IncompleteYamtConfig config = ccg.generate();


            Assert.assertEquals(config.getFormat(), Optional.of("author-album-single"));
            Assert.assertEquals(config.getMusicDir(), Optional.empty());
            Assert.assertEquals(config.getOutDir(), Optional.of(Paths.get("./out")));

        } catch (YamtException.ConfigException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testFailRequiredCliConfig() {

        String[] args = {"-f", "author-album-single", "-m", "./music"};
        CliConfigGenerator ccg = new CliConfigGenerator(args);

        try {
            IncompleteYamtConfig config = ccg.generate();

            Assert.fail("Should fail because -o is a required option");
        } catch (Exception e) {
            Assert.assertEquals(true, true);
        }
    }

}
