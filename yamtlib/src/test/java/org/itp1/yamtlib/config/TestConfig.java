package org.itp1.yamtlib.config;

import org.itp1.yamtlib.errors.YamtException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.swing.text.html.Option;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class TestConfig {

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
    public void testConfigFactoryNotThrowsWhenAllOk() throws YamtException.ConfigException {

        IncompleteYamtConfig inc1 = new IncompleteYamtConfig();
        inc1.mergeFormat("Hello");

        IncompleteYamtConfig inc2 = new IncompleteYamtConfig();
        inc2.mergeMusicDir(Paths.get("."));

        IncompleteYamtConfig inc3 = new IncompleteYamtConfig();
        inc3.mergeOutDir(Paths.get(".."));

        ConfigGenerator cfgGen1 = Mockito.mock(ConfigGenerator.class);
        Mockito.when(cfgGen1.generate()).thenReturn(inc1);

        ConfigGenerator cfgGen2 = Mockito.mock(ConfigGenerator.class);
        Mockito.when(cfgGen2.generate()).thenReturn(inc2);

        ConfigGenerator cfgGen3 = Mockito.mock(ConfigGenerator.class);
        Mockito.when(cfgGen3.generate()).thenReturn(inc3);

        try {
            YamtConfig ycfg = new ConfigFactory().generate(cfgGen1, cfgGen2, cfgGen3);
        } catch (YamtException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testConfigFactoryAbortWhenException() throws YamtException {
        IncompleteYamtConfig inc1 = new IncompleteYamtConfig();
        inc1.mergeFormat("Hello");

        IncompleteYamtConfig inc2 = new IncompleteYamtConfig();
        inc2.mergeMusicDir(Paths.get("."));

        ConfigGenerator cfgGen1 = Mockito.mock(ConfigGenerator.class);
        Mockito.when(cfgGen1.generate()).thenReturn(inc1);

        ConfigGenerator cfgGen2 = Mockito.mock(ConfigGenerator.class);
        Mockito.when(cfgGen2.generate()).thenReturn(inc2);

        ConfigGenerator cfgGen3 = Mockito.mock(ConfigGenerator.class);
        Mockito.when(cfgGen3.generate()).thenThrow(new YamtException.ConfigException("Test Error"));
        try {
            YamtConfig ycfg = new ConfigFactory().generate(cfgGen1, cfgGen2, cfgGen3);
            Assert.fail("Did not throw exception");
        } catch (YamtException e) {
            Assert.assertEquals(true, true);
        }
    }


}
