package org.itp1.yamtlib.metadata;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import org.apache.http.NameValuePair;
import org.itp1.yamtlib.errors.YamtException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

import static org.itp1.yamtlib.metadata.MetaFetcher.url;

public class testAPI {

    @Test
    public void testFetchFPDur() {
        MetaFetcher metaFetcher = new MetaFetcher();
    }

    @Test
    public void test() {
        System.out.println(System.getProperty("os.name"));
    }

    @Test
    public void test2() {
        MetaFetcher metaFetcher = new MetaFetcher();
        try {
            Fpcalc program = metaFetcher.getProgram();
            System.out.println(program.getProgramFile().getAbsolutePath());
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
