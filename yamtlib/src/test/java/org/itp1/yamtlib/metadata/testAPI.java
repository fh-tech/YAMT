package org.itp1.yamtlib.metadata;

import org.apache.http.NameValuePair;
import org.itp1.yamtlib.errors.YamtException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

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
        MetaFetcher metaFetcher = new MetaFetcher();
        String duration = "333";
        String fp = "02385jflsnfl=932";
        String apiCall = metaFetcher.buildAPICall(duration, fp);
        String apiManual = "https://api.acoustid.org/v2/lookup?client=" +
                metaFetcher.key +"&duration=" +
                duration + "&fingerprint=" +
                fp;
        Assert.assertTrue(apiManual.equals(apiCall));
        Assert.assertTrue(!apiManual.equals(""));
        System.out.println(apiManual);
    }

    // which map to use?
    @Test
    public void testBuildUrlParameters() {
        MetaFetcher metaFetcher = new MetaFetcher();
        String duration = "333";
        String fp = "02385jflsnfl=932";
        Map<String, String> pairs = new HashMap<String, String>();
        // "=" added automatically
        pairs.put("client", metaFetcher.key);
        pairs.put("&duration", duration);
        pairs.put("&fingerprint", fp);
        pairs.put("&meta=", metaFetcher.getAllMeta());
        Assert.assertTrue(!pairs.isEmpty());
        List<NameValuePair> urlParameters = metaFetcher.buildUrlParameters(pairs);
        System.out.println(urlParameters);
    }






}
