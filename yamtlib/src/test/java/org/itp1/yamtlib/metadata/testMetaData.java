package org.itp1.yamtlib.metadata;

import org.itp1.yamtlib.errors.YamtException;
import org.itp1.yamtlib.metadata.musicBrainz.AccousticFingerprint;
import org.itp1.yamtlib.metadata.musicBrainz.Fingerprinter;
import org.itp1.yamtlib.metadata.musicBrainz.MBMetaExtractor;
import org.itp1.yamtlib.metadata.musicBrainz.MBMetaRequestor;
import org.itp1.yamtlib.music.YamtMusic;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class testMetaData {

    //lossy formats
    private static final String TEST_MP3 = "test.mp3";
    private static final String TEST_MP3_2 = "freaky_like_me.mp3";
    private static final String TEST_MP3_3 = "like_a_g6.mp3";
    private static final String TEST_MP3_4 = "work_it_out.mp3";
    private static final String TEST_MP3_5 = "Hips_Don't_Lie_(featuring_Wyclef_Jean).mp3";
    private static final String TEST_MP3_6 = "Shakira_Don't_Bother.mp3";
    private static final String TEST_MP3_7 = "Shakira_Give_It_Up_To_Me.mp3";
    private static final String TEST_MP3_8 = "Shakira_Loca_+_Lyrics.mp3";
    private static final String TEST_MP3_9 = "Shakira_Waka_Waka_(This_Time_for_Africa)wmv.mp3";
    private static final String TEST_MP3_10 = "Shakira_Whenever,_Wherever.mp3";
    //lossless
    private static final String TEST_FLAC = "test.flac";
    private static final String TEST_FLAC2 = "test2.flac";

    private static final String[] fileNames = {TEST_MP3, TEST_MP3_2, TEST_MP3_3, TEST_MP3_4, TEST_MP3_5, TEST_MP3_6, TEST_MP3_7, TEST_MP3_8, TEST_MP3_9, TEST_MP3_10, TEST_FLAC, TEST_FLAC2};
    private static final File[] testFiles_real = new File[12];
    private static File[] testFiles = new File[12];

    private static List<YamtMusic> testMusic;

    @Test
    @Before
    // NOTE: no matther what you do dont remove "/" +
    public void buildTestyM() {
        for (int i = 0; i < fileNames.length; i++) {
            try {
                testFiles_real[i] = new File(getClass().getResource("/" + fileNames[i]).getFile());
            } catch (Exception e) {
                e.printStackTrace();
                Assert.fail();
            }
        }
        // working with clone to not really manipulate the data
        testFiles = testFiles_real.clone();
        testMusic = Arrays.stream(testFiles)
                .map((f) -> {
                    try {
                        return new YamtMusic(f);
                    } catch (YamtException.MusicException e) {
                        Assert.fail();
                    }
                    Assert.fail();
                    return null;
                })
                .collect(Collectors.toList());
    }


    @Test
    public void testWorkflow() {
        try {
            Fingerprinter fingerprinter = new Fingerprinter();
            Map<YamtMusic, AccousticFingerprint> ymFP = fingerprinter.mapFingerPrints(testMusic);

            MBMetaRequestor mbMetaRequestor = new MBMetaRequestor();
            Map<YamtMusic, JSONObject> requestResult = mbMetaRequestor.fetchMetaData(ymFP);

            Map<YamtMusic, MetaTemplate> ymTemplate = new LinkedHashMap<>();
            MBMetaExtractor mbMetaExtractor = new MBMetaExtractor();

            for (YamtMusic yamtMusic : requestResult.keySet()) {
                MetaTemplate metaTemplate = new MetaTemplate();
                metaTemplate = metaTemplate.fillTemplate(mbMetaExtractor, requestResult.get(yamtMusic));
                ymTemplate.put(yamtMusic, metaTemplate);
                System.out.println("_________");
            }
        } catch (YamtException.MetaDataException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }


    @Test
    @Ignore
    public void printTestMusic() {
        testMusic.stream()
                .map((yM) -> yM.getFile())
                .forEach((f) -> System.out.println(f));
    }
}
