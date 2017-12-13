package org.itp1.yamtlib.config;

import org.itp1.yamtlib.errors.YamtException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TestConfig {

    @Test
    public void testIncompleteMerge() {
        IncompleteYamtConfig config1 = new IncompleteYamtConfig();
        IncompleteYamtConfig config2 = new IncompleteYamtConfig();

        config1.format("hello");
        config1.outputDirectory(new File("."));

        config2.format("world");
        config2.musicSource(Collections.singletonList(new File("..")));

        IncompleteYamtConfig config3 = config1.merge(config2);

        Assert.assertEquals(config3.outputDirectory(), new File("."));
        Assert.assertEquals(config3.musicSource(), Collections.singletonList(new File("..")));
        Assert.assertEquals(config3.format(), "hello");


    }

    @Test
    public void testIncompleteVerify() {
        IncompleteYamtConfig config = new IncompleteYamtConfig();
        List<File> musicPath = Collections.singletonList(new File("."));
        File outPath = new File("..");
        String format = "author-val";

        config.format(format);
        config.outputDirectory(outPath);
        config.musicSource(musicPath);
        config.moveOnRename(true);
        config.strategy(MetaDataStrategy.FETCH_NEVER);
        config.searchFilesRecursive(true);


        try {
            YamtConfig realConfig = config.verify();

            Assert.assertEquals(realConfig.getMusicSource(), musicPath);
            Assert.assertEquals(realConfig.getOutputDirectory(), outPath);

            Assert.assertEquals(realConfig.getFormat(), Optional.of(format));

        } catch (YamtException e) {
            Assert.fail("Should fail, not complete Config");
        }
    }

    @Test
    public void testIncompleteVerifyFail() {
        IncompleteYamtConfig config = new IncompleteYamtConfig();
        config.format("author-val");
        try {
            YamtConfig realConfig = config.verify();
            Assert.fail("Should fail, not complete Config");
        } catch (YamtException e) {
            Assert.assertEquals(true, true);
        }
    }

    @Test
    public void testConfigFactoryNotThrowsWhenAllOk() throws YamtException {

        IncompleteYamtConfig inc1 = new IncompleteYamtConfig();
        inc1.format("Hello");
        inc1.searchFilesRecursive(true);

        IncompleteYamtConfig inc2 = new IncompleteYamtConfig();
        inc2.musicSource(Collections.singletonList(new File(".")));
        inc2.strategy(MetaDataStrategy.FETCH_NEVER);

        IncompleteYamtConfig inc3 = new IncompleteYamtConfig();
        inc3.outputDirectory(new File(".."));
        inc3.moveOnRename(true);

        ConfigGenerator cfgGen1 = Mockito.mock(ConfigGenerator.class);
        Mockito.when(cfgGen1.generate()).thenReturn(inc1);

        ConfigGenerator cfgGen2 = Mockito.mock(ConfigGenerator.class);
        Mockito.when(cfgGen2.generate()).thenReturn(inc2);

        ConfigGenerator cfgGen3 = Mockito.mock(ConfigGenerator.class);
        Mockito.when(cfgGen3.generate()).thenReturn(inc3);


        YamtConfig ycfg = ConfigFactory.generate(cfgGen1, cfgGen2, cfgGen3);

    }

    @Test
    public void testConfigFactoryAbortWhenException() throws YamtException {
        IncompleteYamtConfig inc1 = new IncompleteYamtConfig();
        inc1.format("Hello");

        IncompleteYamtConfig inc2 = new IncompleteYamtConfig();
        inc2.musicSource(Collections.singletonList(new File(".")));

        ConfigGenerator cfgGen1 = Mockito.mock(ConfigGenerator.class);
        Mockito.when(cfgGen1.generate()).thenReturn(inc1);

        ConfigGenerator cfgGen2 = Mockito.mock(ConfigGenerator.class);
        Mockito.when(cfgGen2.generate()).thenReturn(inc2);

        ConfigGenerator cfgGen3 = Mockito.mock(ConfigGenerator.class);
        Mockito.when(cfgGen3.generate()).thenThrow(new YamtException.ConfigException("Test Error"));
        try {
            YamtConfig ycfg =ConfigFactory.generate(cfgGen1, cfgGen2, cfgGen3);
            Assert.fail("Did not throw exception");
        } catch (YamtException e) {
            Assert.assertEquals(true, true);
            Assert.assertEquals("Test Error", e.getCause().getCause().getMessage());
        }
    }


}
