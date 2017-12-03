package org.itp1.yamtlib;

import org.itp1.yamtlib.format.MusicFormatter;
import org.itp1.yamtlib.music.YamtMusic;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class TestFormat {


    @Test
    public void testFormatting() throws Exception {
        MusicFormatter mf = new MusicFormatter("{artist}/{title}");
        YamtMusic music = new YamtMusic(getClass().getResource("/test.mp3").getFile());
        String newName = mf.format(music);
        File f = new File(newName);

        Files.copy(Paths.get(getClass().getResource("/test.mp3").toURI()), Paths.get(newName+".mp3"));

        Assert.assertEquals("", newName);

    }

    //TODO: write more tests

}
