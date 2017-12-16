package org.itp1.yamtlib.metadata.musicBrainz;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.NoArgsConstructor;
import org.itp1.yamtlib.errors.YamtException;
import org.itp1.yamtlib.metadata.MetaRequestor;
import org.itp1.yamtlib.music.YamtMusic;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

@NoArgsConstructor
public class MBMetaRequestor implements MetaRequestor<Map<YamtMusic, AccousticFingerprint>, JSONObject> {

    private static final String url = "https://api.acoustid.org/v2/lookup";
    private static final String key = "MHdydDIrB7";
    private static final String allMeta = "recordings+recordingids+releases+releaseids+releasegroups+releasegroupids+tracks+compress+usermeta+sources";
    private static final String normalMeta = "recordings+releasegroups+compress";
    private static final String minimalMeta = "recordings";

    //TODO: investigate why you get float but need to pass int
    @Override
    public Map<YamtMusic, JSONObject> fetchMetaData(Map<YamtMusic, AccousticFingerprint> yamtFP) throws YamtException.MetaDataException {
        Map<YamtMusic, JSONObject> yamtJSON = new LinkedHashMap<>();
        HttpResponse<JsonNode> response = null;
        try {
            for(YamtMusic music : yamtFP.keySet()) {
                String request = url + "?client="
                        + key + "&meta="
                        + normalMeta
                        + "&duration=" + (int)yamtFP.get(music).getDuration()
                        + "&fingerprint=" + yamtFP.get(music).getFingerprint();
                response = Unirest.get(request)
                        .asJson();
                yamtJSON.put(music, response.getBody().getObject());
            }
            return yamtJSON;
        } catch (UnirestException | NullPointerException e) {
            throw new YamtException.MetaDataException(e);
        }
    }


    /*public JSONObject fetchMetaDataPost(Map<YamtMusic, AccousticFingerprint> mBMusic) throws YamtException.MetaDataException {
        HttpResponse<JsonNode> response = null;
        try {
            for(YamtMusic music : mBMusic.keySet()) {
                *//*System.out.println(mBMusic.get(music).getDuration());*//*
                response = Unirest.post(url)
                        .header("accept", "application/json")
                        .field("client", key)
                        .field("meta", allMeta)
                        .field("duration", (int)mBMusic.get(music).getDuration() )
                        .field("fingerprint", mBMusic.get(music).getFingerprint())
                        .asJson();
            }
            return response.getBody().getObject();
        } catch (UnirestException e) {
            throw new YamtException.MetaDataException(e);
        }
    }*/







}
