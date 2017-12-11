package org.itp1.yamtlib.metadata;

import org.itp1.yamtlib.errors.YamtException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class testAPI {

    @Test
    public void testFetchFPDur() {
        MetaFetcher metaFetcher = new MetaFetcher();
        String[] result = new String[2];
        try {
            result = metaFetcher.fetchFPDur("src/test/resources/test.mp3");
        } catch (YamtException.MetaDataException e) {
            Assert.fail();
        }
        Assert.assertNotNull(result[0]);
        Assert.assertNotNull(result[1]);
        Assert.assertTrue(result[0].startsWith("DURATION"));
        Assert.assertTrue(result[1].startsWith("FINGERPRINT"));
    }

    @Test
    public void testBuildAPICall() {
        String duration = "333";
        String fp = "02385jflsnfl=932";
        MetaFetcher metaFetcher = new MetaFetcher();
        String apiCall = metaFetcher.buildAPICall(duration, fp);
        String apiManual = "https://api.acoustid.org/v2/lookup?client=" +
                metaFetcher.key +"&duration=" +
                duration + "&fingerprint=" +
                fp;
        Assert.assertTrue(apiManual.equals(apiCall));
        Assert.assertTrue(!apiManual.equals(""));
        System.out.println(apiManual);
    }






}
