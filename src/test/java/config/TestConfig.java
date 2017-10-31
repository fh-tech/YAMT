package config;

import errors.YamtException;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class TestConfig {

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
    public void testIncompleteMerge() {
        IncompleteYamtConfig config1 = new IncompleteYamtConfig();
        IncompleteYamtConfig config2 = new IncompleteYamtConfig();

        config1.mergeFormat("hello");
        config1.mergeOutDir(Paths.get("."));

        config2.mergeFormat("world");
        config2.mergeMusicDir(Paths.get(".."));

        IncompleteYamtConfig config3 = config1.merge(config2);

        Assert.assertEquals(config3.getOutDir(), Optional.of(Paths.get(".")));
        Assert.assertEquals(config3.getMusicDir(), Optional.of(Paths.get("..")));
        Assert.assertEquals(config3.getFormat(), Optional.of("hello"));


    }

    @Test
    public void testIncompleteVerify() {
        IncompleteYamtConfig config = new IncompleteYamtConfig();
        Path musicPath = Paths.get(".");
        Path outPath = Paths.get("..");
        String format = "author-val";

        config.setFormat(Optional.of(format));
        config.setOutDir(Optional.of(outPath));
        config.setMusicDir(Optional.of(musicPath));

        try {
            YamtConfig realConfig = config.verify();

            Assert.assertEquals(realConfig.getMusicDir(), musicPath);
            Assert.assertEquals(realConfig.getOutDir(), outPath);

            Assert.assertEquals(realConfig.getFormat(), format);

        } catch (YamtException e) {
            Assert.fail("Should fail, not complete Config");
        }
    }

    @Test
    public void testIncompleteVerifyFail() {
        IncompleteYamtConfig config = new IncompleteYamtConfig();
        config.setFormat(Optional.of("author-val"));
        try {
            YamtConfig realConfig = config.verify();
            Assert.fail("Should fail, not complete Config");
        } catch (YamtException e) {
            Assert.assertEquals(true, true);
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
