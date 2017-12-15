package org.itp1.yamtlib.metadata;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.itp1.yamtlib.errors.YamtException;
import org.itp1.yamtlib.metadata.musicBrainz.Fpcalc;
import org.itp1.yamtlib.metadata.musicBrainz.MusicBrainz;
import org.itp1.yamtlib.music.TestMeta;
import org.itp1.yamtlib.music.YamtMusic;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class testAPI extends TestMeta {

    private static List<YamtMusic> testMusic;

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

   /* @Test
    public void testii() {
        MusicBrainz musicBrainz = new MusicBrainz();
        try {
            List<String> absPaths = musicBrainz.fetchFP(testMusic);
        } catch (YamtException.MetaDataException e) {
            e.printStackTrace();
        }
    }*/

    /*@Test
    public void test2() {
        MusicBrainz musicBrainz = new MusicBrainz();
        try {
            Fpcalc program = musicBrainz.getProgram();
            System.out.println(program.getProgramFile().getAbsolutePath());
        } catch (YamtException.MetaDataException e) {
            e.printStackTrace();
        }
    }*/

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
