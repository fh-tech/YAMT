package org.itp1.yamtlib.music;

import org.itp1.yamtlib.errors.YamtException;
import org.jaudiotagger.tag.FieldKey;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestMetaReading extends TestMeta {

    // supports flac, ogg vorbis, mp3, wav(limited)
    @Test
    public void testSupportedFormats() {
        String[] expected = {"MPEG-1 Layer 3", "Ogg Vorbis v1","WAV-RIFF 16 bits","FLAC 24 bits","FLAC 24 bits"};
        try {
            String[] actual = new String[5];
            for(int i = 0; i < testFiles.length; i++) {
                YamtMusic yamtMusic = new YamtMusic(testFiles[i]);
                actual[i] = yamtMusic.getFormat();
            }
            Assert.assertArrayEquals(expected, actual);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    //sets and gets a testObject
    @Test
    public void testGetterSetter() {
        YamtMusic yamtMusic;
        try {
            yamtMusic = new YamtMusic(testFiles[1]);
            WantedKey wantedKey = WantedKey.ALBUM_ARTIST_SORT;
            String newValue = "albumTest";
            yamtMusic.setTag(wantedKey, newValue);
            Assert.assertTrue(newValue.equals(yamtMusic.getTag(wantedKey)));

            wantedKey = WantedKey.ARTIST_SORT;
            newValue = "artistTest";
            yamtMusic.setTag(wantedKey, newValue);
            Assert.assertFalse("different".equals(yamtMusic.getTag(wantedKey)));
        } catch (YamtException.MusicException e) {
            e.printStackTrace();
        }
    }


    // only to look up all possible Fields
    @Ignore
    @Test
    public void printAllAvailableFields() {
        YamtMusic yamtMusic;
        try {
            yamtMusic = new YamtMusic(getClass().getResource("/test.mp3").getPath());
            for(FieldKey fkey : FieldKey.values()) {
                System.out.print(fkey + ": ");
                System.out.println(yamtMusic.getTags().getFirst(fkey));
            }
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }




}
