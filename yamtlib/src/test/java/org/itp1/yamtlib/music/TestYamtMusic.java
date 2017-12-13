package org.itp1.yamtlib.music;

import org.itp1.yamtlib.errors.YamtException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class TestYamtMusic {

    //if the given file exists no error expected and nothing should be null
    @Test
    public void testConstructor() {
        YamtMusic yamtMusic;
        try {
            yamtMusic = new YamtMusic(getClass().getResource("/test.mp3").getPath());
            Assert.assertNotNull(yamtMusic);
            Assert.assertNotNull(yamtMusic.getTags());
            Assert.assertNotNull(yamtMusic.getAudioFile());
            Assert.assertNotNull(yamtMusic.getAudioHeader());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    // if the given file at the path exists no error expected
    // and nothing should be null
    @Test
    public void testConstructor2() {
        File f = new File(getClass().getResource("/test.mp3").getFile());
        YamtMusic yamtMusic;
        try {
            yamtMusic = new YamtMusic(f);
            Assert.assertNotNull(yamtMusic);
            Assert.assertNotNull(yamtMusic.getTags());
            Assert.assertNotNull(yamtMusic.getAudioFile());
            Assert.assertNotNull(yamtMusic.getAudioHeader());
        } catch (YamtException.MusicException e) {
            Assert.fail(e.getMessage());
        }
    }

    // if empty file as parameter the construction has to fail
    // with message "Construction failed"
    @Test
    public void testConstructor3() {
        File f = new File("notHere.mp3");
        try {
            YamtMusic yamtMusic = new YamtMusic(f);
            Assert.fail("Should not have come this far");
        } catch(YamtException.MusicException e) {
            Assert.assertTrue("Construction failed".equals(e.getMessage()));
        }
    }

    // if the path is invalid "Construction failed" should occur
    // the cause shouldnt be "File is null"
    @Test
    public void testConstructor4() {
        YamtMusic yamtMusic;
        try {
            yamtMusic = new YamtMusic("notHere");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue("Construction failed".equals(e.getMessage()));
            Assert.assertTrue(!"File is null".equals(e.getCause().getMessage()));
        }
    }

    // if file is null exception message should be "Construction failed"
    // the message of the cause of the exception should be "File is null"
    @Test
    public void testConstructor5() {
        File f = null;
        try {
            YamtMusic yamtMusic = new YamtMusic(f);
            Assert.fail("Should not have come this far");
        } catch (YamtException.MusicException e) {
            Assert.assertTrue("Construction failed".equals(e.getMessage()));
            Assert.assertTrue("File is null".equals(e.getCause().getMessage()));
        }
    }
}
