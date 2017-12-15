package org.itp1.yamtlib.metadata.musicBrainz;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.itp1.yamtlib.errors.YamtException;
import org.itp1.yamtlib.music.TestMeta;
import org.itp1.yamtlib.music.YamtMusic;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class testMusicBrainz extends TestMeta {


    //lossy formats
    private static final String TEST_MP3 = "test.mp3";
    //lossless
    private static final String TEST_FLAC = "test.flac";
    private static final String TEST_FLAC2 = "test2.flac";

    private static final String[] fileNames = {TEST_MP3, TEST_FLAC, TEST_FLAC2};
    private static final File[] testFiles_real = new File[2];
    private static File[] testFiles = new File[2];

    private static List<YamtMusic> testMusic;

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

    @Before
    @Test
    public void buildTestYamtMusic() {
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
    public void printTestMusic() {
        testMusic.stream()
                .map((yM) -> yM.getFile())
                .forEach((f) -> System.out.println(f));
    }

    @Test
    public void testii() {
        MusicBrainz musicBrainz = new MusicBrainz();
        try {
            Map<YamtMusic, AccousticFingerprint> mBMusic = musicBrainz.makeMusicBrainz(testMusic);
            System.out.println(mBMusic);
        } catch (YamtException.MetaDataException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void makeMBCallPos() {
        MusicBrainz musicBrainz = new MusicBrainz();
        try {
            Map<YamtMusic, AccousticFingerprint> mBMusic = musicBrainz.makeMusicBrainz(testMusic);
            MBMetaRequestor mBrequestor = new MBMetaRequestor();
            mBrequestor.fetchMetaDataPost(mBMusic);
        } catch (YamtException.MetaDataException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void makeMBCallGet() {
        MusicBrainz musicBrainz = new MusicBrainz();
        try {
            Map<YamtMusic, AccousticFingerprint> mBMusic = musicBrainz.makeMusicBrainz(testMusic);
            MBMetaRequestor mBrequestor = new MBMetaRequestor();
            mBrequestor.fetchMetaDataGet(mBMusic);
        } catch (YamtException.MetaDataException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void makeCall() {
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.get("http://httpbin.org/get")
                    .asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        System.out.println(response.getBody());
    }
}
