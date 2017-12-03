package org.itp1.yamtlib.music;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class TestMeta {

    //lossy formats
    protected static final String TEST_MP3 = "test.mp3";
    protected static final String TEST_OGG = "test.ogg";
    protected static final String TEST_AAC = "test.aac";
    //lossless
    protected static final String TEST_WAV = "test.wav";
    protected static final String TEST_FLAC = "test.flac";
    protected static final String TEST_FLAC2 = "test2.flac";

    protected static final String[] fileNames = {TEST_MP3, TEST_OGG, TEST_WAV, TEST_FLAC, TEST_FLAC2};
    protected static final File[] testFiles = new File[5];


    @Test
    @Before
    // NOTE: no matther what you do dont remove "/" +
    public void buildFileAr() {
        for(int i = 0; i < fileNames.length; i++) {
            try {
                testFiles[i] = new File(getClass().getResource("/"+ fileNames[i]).getFile());
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

}
