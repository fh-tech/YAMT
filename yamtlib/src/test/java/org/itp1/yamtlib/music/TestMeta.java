package org.itp1.yamtlib.music;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Array;
import java.util.List;

public class TestMeta {

    private static List<YamtMusic> testMusic;

    //lossy formats
    private static final String TEST_MP3 = "test.mp3";
    private static final String TEST_AAC = "test.aac";
    //lossless
    private static final String TEST_WAV = "test.wav";
    private static final String TEST_FLAC = "test.flac";
    private static final String TEST_FLAC2 = "test2.flac";

    private static final String TEST_OGG = "test.ogg";
    private static final String[] fileNames = {TEST_MP3, TEST_OGG, TEST_WAV, TEST_FLAC, TEST_FLAC2};
    private static final File[] testFiles_real = new File[5];
    protected static File[] testFiles = new File[5];


    @Test
    @Before
    // NOTE: no matther what you do dont remove "/" +
    public void buildFileAr() {
        for(int i = 0; i < fileNames.length; i++) {
            try {
                testFiles_real[i] = new File(getClass().getResource("/"+ fileNames[i]).getFile());
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        // working with clone to not really manipulate the data and test cases make sense
        testFiles = testFiles_real.clone();
    }

}
