package org.itp1.yamtlib.music;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class TestYamtMusic {

    @Test
    public void testConstructor() {
        YamtMusic yamtMusic = new YamtMusic(getClass().getResource("/test.mp3").getPath());
        Assert.assertNotNull(yamtMusic);
        Assert.assertNotNull(yamtMusic.getTags());
        Assert.assertNotNull(yamtMusic.getAudioFile());
        Assert.assertNotNull(yamtMusic.getAudioHeader());
    }

    @Test
    public void testConstructor2() {
        File f = new File(getClass().getResource("/test.mp3").getFile());
        YamtMusic yamtMusic = new YamtMusic(f);
        Assert.assertNotNull(yamtMusic);
        Assert.assertNotNull(yamtMusic.getTags());
        Assert.assertNotNull(yamtMusic.getAudioFile());
        Assert.assertNotNull(yamtMusic.getAudioHeader());
    }
}
