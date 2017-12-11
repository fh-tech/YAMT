package org.itp1.yamtlib.metadata;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.itp1.yamtlib.errors.YamtException;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    protected List<NameValuePair> buildUrlParameters(Map<String, String> pairs) {
        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : pairs.entrySet() ) {
            String key = entry.getKey();
            String value = entry.getValue();
            urlParameters.add(new BasicNameValuePair(key, value));
        }
        return urlParameters;
    }

    protected String getAllMeta() {
        return "recordings+recordingids+releases+releaseids+releasegroups+releasegroupids+tracks+compress+usermeta+sources";
    }

    protected String fetchData(String duration, String fp, List<NameValuePair> params) throws YamtException.MetaDataException {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        // add header
        //post.setHeader("User-Agent", USER_AGENT);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("sn", "C02G8416DRJM"));
        urlParameters.add(new BasicNameValuePair("cn", ""));
        urlParameters.add(new BasicNameValuePair("locale", ""));
        urlParameters.add(new BasicNameValuePair("caller", ""));
        urlParameters.add(new BasicNameValuePair("num", "12345"));

       /* List<NameValuePair> urlParameters = buildUrlParameters();*/

        try {
            post.setEntity(new UrlEncodedFormEntity(urlParameters));
        } catch (UnsupportedEncodingException e) {
            throw new YamtException.MetaDataException("Encoding Parameters failed", e);
        }

        HttpResponse response = null;
        try {
            response = client.execute(post);
        } catch (IOException e) {
            throw new YamtException.MetaDataException("Executing POST request failed", e);
        }
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + post.getEntity());
        System.out.println("Response Code : " +
                response.getStatusLine().getStatusCode());

        BufferedReader rd = null;
        try {
            rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));
        } catch (IOException e) {
            throw new YamtException.MetaDataException("Reading InputStream failed", e);
        }

        StringBuffer result = new StringBuffer();
        String line = "";
        try {
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            throw new YamtException.MetaDataException("Building Response failed", e);
        }
        System.out.println(result.toString());
        return result.toString();
    }

}
