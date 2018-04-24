package org.itp1.yamtcli.config;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import org.itp1.yamtlib.config.IncompleteYamtConfig;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CliConfigTest {

    @Test
    public void testDefaultCall() {
        Args args = Args.generate("-r", "-f", "{author}/{title}", "--meta", "never", "./hello", "./../");

        assertEquals(true, args.isSearchFilesRecursive());
        assertEquals("{author}/{title}", args.getFormat());
        assertEquals(Arrays.asList("./hello", "./../"), args.getMusicSource());
        assertEquals("never", args.getStrategy());

    }

    @Test(expected = ParameterException.class)
    public void testShouldFailOnRequiredParameters() {
        Args.generate("-r", "-f");
        fail();

    }

    @Test(expected = ParameterException.class)
    public void testShouldFailOnWrongMetaParameter() {
        Args.generate("--meta", "abc", "./Hello");
        fail();
    }

    @Test
    public void testArgsToConfigTranslation() throws Exception {
        Args args = new Args(new JCommander());

        args.setFormat("{artist}-{track}");
        args.setCopyFiles(true);
        args.setMoveFiles(false);
        args.setSearchFilesRecursive(true);
        args.setMusicSource(Collections.singletonList("."));

        IncompleteYamtConfig inyc = new CliConfigGenerator(args).generate();

        assertEquals("{artist}-{track}", inyc.format());
        assertEquals(false, inyc.moveOnRename());
        assertEquals(true, inyc.searchFilesRecursive());
        assertEquals(Collections.singletonList(new File(".")), inyc.musicSource());

    }


}
