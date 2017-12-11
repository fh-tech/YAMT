package org.itp1.yamtlib.metadata;

import org.itp1.yamtlib.errors.YamtException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class MetaFetcher {

    public static final String url = "https://api.acoustid.org/v2/lookup";
    public static final String key = "RtWAwftsMAE";

    protected String[] fetchFPDur(String pathToRes) throws YamtException.MetaDataException {
        Process proc = null;
        try {
            proc = Runtime.getRuntime().exec("./src/main/resources/fpcalc " + pathToRes);
        } catch (IOException e) {
            throw new YamtException.MetaDataException(e);
        }
        InputStream is = proc.getInputStream();
        Scanner s = new Scanner(is).useDelimiter("\\A");
        String val = "";
        if (s.hasNext()) {
            val = s.next();
        }
        else {
            throw new YamtException.MetaDataException("FP and duration empty");
        }
        String[] parts = val.split("\n");
        return parts;
    }

    protected String buildAPICall(String duration, String fp) {
        return url + "?client=" + key + "&duration=" + duration + "&fingerprint=" + fp;
    }

    protected String fetchData(String apiCall) {
        return "";
    }
}
