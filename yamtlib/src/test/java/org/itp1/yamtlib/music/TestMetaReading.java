package org.itp1.yamtlib.music;

import org.jaudiotagger.tag.FieldKey;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class TestMetaReading extends TestMeta {

    @Test
    public void testMetaDataConstructorURL() {
        MetaData metaData = new MetaData(getClass().getResource("/test.mp3").getPath());
        Assert.assertNotNull(metaData);
        Assert.assertNotNull(metaData.getTags());
        Assert.assertNotNull(metaData.getAudioFile());
        Assert.assertNotNull(metaData.getAudioHeader());
    }

    @Test
    public void testMetaDataConstructorFile() {
        File f = new File(getClass().getResource("/test.mp3").getFile());
        MetaData metaData = new MetaData(f);
        Assert.assertNotNull(metaData);
        Assert.assertNotNull(metaData.getTags());
        Assert.assertNotNull(metaData.getAudioFile());
        Assert.assertNotNull(metaData.getAudioHeader());
    }

    // supports flac, ogg vorbis, mp3, wav(limited)
    @Test
    public void testSupportedFormats() {
        String[] expected = {"MPEG-1 Layer 3", "Ogg Vorbis v1","WAV-RIFF 16 bits","FLAC 24 bits","FLAC 24 bits"};
        try {
            String[] actual = new String[5];
            for(int i = 0; i < testFiles.length; i++) {
                MetaData metaData = new MetaData(testFiles[i]);
                actual[i] = metaData.getFormat();
            }
            Assert.assertArrayEquals(expected, actual);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void printAllAvailableFields() {
        MetaData metaData = new MetaData(getClass().getResource("/test.mp3").getPath());
        for(FieldKey fkey : FieldKey.values()) {
            System.out.print(fkey + ": ");
            System.out.println(metaData.getTags().getFirst(fkey));
        }
    }







}
