package org.itp1.yamtlib.music;

import org.itp1.yamtlib.errors.YamtException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class TestMetaWriting extends TestMeta {

    // writes into metadata of all testFiles then reads the value and checks if the write was successful
    @Test
    public void testWriteFiles() {
        YamtMusic yamtMusic;
        String newValue = "testArtist";
        WantedKey wantedKey = WantedKey.ARTIST;
        for(File tf : testFiles) {
            try {
                yamtMusic = new YamtMusic(tf);
                yamtMusic.setTag(wantedKey, newValue);
                Assert.assertTrue(newValue.equals(yamtMusic.getTag(wantedKey)));
            } catch (YamtException.MusicException e) {
                Assert.fail(e.getMessage());
            }
        }
    }

    // writes into metadata of all testFiles then reads the value and checks if the write was successful
    // by comparing it with another String and expecting false
    @Test
    public void testWriteFiles2() {
        YamtMusic yamtMusic;
        String newValue = "thisWillFail";
        WantedKey wantedKey = WantedKey.ARTIST;
        for(File tf : testFiles) {
            try {
                yamtMusic = new YamtMusic(tf);
                yamtMusic.setTag(wantedKey, newValue);
                Assert.assertTrue(!"something else".equals(yamtMusic.getTag(wantedKey)));
            } catch (YamtException.MusicException e) {
                Assert.fail(e.getMessage());
            }
        }
    }


}
