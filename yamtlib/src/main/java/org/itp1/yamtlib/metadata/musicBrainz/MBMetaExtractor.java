package org.itp1.yamtlib.metadata.musicBrainz;

import org.itp1.yamtlib.errors.YamtException;
import org.itp1.yamtlib.metadata.MetaExtractor;
import org.itp1.yamtlib.music.WantedKey;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;


// results seem to be ordered by score, so taking the first seems to be the best choice
public class MBMetaExtractor implements MetaExtractor<JSONObject> {


    /**
     * checks if json is valid (status) and then extracts metadata from it
     * @param key the key indicates which type of metadata should be extracted
     * @param j the json that should be used
     * @return String extract-result will be "" if nothing was found
     * @throws YamtException.MetaDataException
     */
    @Override
    public String extract(WantedKey key, JSONObject j) throws YamtException.MetaDataException {
        if (valid(j)) {
            switch (key) {
                case ARTIST:
                    return extractArtist(j);
                case TITLE:
                    return extractTitle(j);
                case ALBUM:
                    return extractAlbum(j);
                default:
                    return "";
            }
        } else {
            throw new YamtException.MetaDataException("json invalid");
        }
    }


    /**
     * goes through as many entries in the resultJSONArray and its corresponding recordingsArrays and artistsArrays until the artist value is not empty anymore
     * since the first result should be the most accurate one but can be empty by mistake
     * @param json the json result that should be searched
     * @return returns a String for the artist - empty string if nothing was found
     */
    private String extractArtist(JSONObject json) {
        String artist = "";
        int i = 0;
        int resultSize = getInstanceSize(json, "results");
        while("".equals(artist) && i < resultSize) {
            JSONObject result = getResult(json, i);
            int f = 0;
            int recordingSize = getInstanceSize(result, "recordings");
            while("".equals(artist) && f < recordingSize) {
                JSONObject recording = getRecording(result, f);
                int artistsSize = getInstanceSize(recording, "artists");
                JSONObject artistObj;
                if (artistsSize > 1) {
                    String joinphrase = "";
                    String feat = "";
                    for (int j = 0; j < artistsSize; j++) {
                        artistObj = getArtists(recording, j);
                        if (artistObj.has("joinphrase")) {
                            joinphrase = artistObj.get("joinphrase").toString();
                            if (artistObj.has("name")) {
                                artist = artistObj.get("name").toString();
                            }
                        } else {
                            if(artistObj.has("name")) {
                                feat = artistObj.get("name").toString();
                            }
                        }
                    }
                    artist = artist + joinphrase + feat;
                } else {
                    artistObj = getArtists(recording, 0);
                    if(artistObj != null && artistObj.has("name")) {
                        artist = artistObj.get("name").toString();
                    }
                }
                f++;
            }
            i++;
        }
        return artist;
    }

    /**
     * goes through as many entries in the resultJSONArray and its corresponding recordingsArrays and artistsArrays until the title value is not empty anymore
     * since the first result should be the most accurate one but can be empty by mistake
     * @param json the json result that should be searched
     * @return returns a String for the artist - empty string if nothing was found
     */
    private String extractTitle(JSONObject json) {
        String title = "";
        int i = 0;
        int resultSize = getInstanceSize(json, "results");
        while ("".equals(title) && i < resultSize) {
            JSONObject result = getResult(json, i);
            int f = 0;
            int recordingSize = getInstanceSize(result, "recordings");
            while ("".equals(title) && f < recordingSize) {
                JSONObject recording = getRecording(result, f);
                if (recording.has("title")) {
                    title = recording.get("title").toString();
                }
                f++;
            }
            i++;
        }

        return title;
    }

    /**
     * goes through as many entries in the resultJSONArray and its corresponding recordingsArrays and artistsArrays until the album value is not empty anymore
     * since the first result should be the most accurate one but can be empty
     * @param json the json result that should be searched
     * @return returns a String for the artist - empty string if nothing was found
     */
    private String extractAlbum(JSONObject json) {
        String album = "";
        int i = 0;
        int resultSize = getInstanceSize(json, "results");
        while ("".equals(album) && i < resultSize) {
            JSONObject result = getResult(json, i);
            int f = 0;
            int recordingSize = getInstanceSize(result, "recordings");
            while ("".equals(album) && f < recordingSize) {
                JSONObject recording = getRecording(result, f);
                int g = 0;
                int releaseGroupsSize = getInstanceSize(recording,"releasegroups");
                while ("".equals(album) && g < releaseGroupsSize) {
                    JSONObject releaseGroup = getReleaseGroups(recording, g);
                    if(releaseGroup.has("title")) {
                        album = releaseGroup.getString("title");
                    }
                    g++;
                }
                f++;
            }
            i++;
        }
        return album;
    }


    private JSONObject getResult(JSONObject json, int instance) {
        if(json.has("results")) {
            JSONArray jAr = json.getJSONArray("results");
            if (jAr.length() > instance) {
                json = jAr.getJSONObject(instance);
                return json;
            }
        }
        return null;
    }

    private JSONObject getRecording(JSONObject result, int instance) {
        if(result.has("recordings")) {
            return result.getJSONArray("recordings").getJSONObject(instance);
        }
        return null;
    }

    private JSONObject getReleaseGroups(JSONObject recording, int instance) {
        if(recording.has("releasegroups")) {
            return recording.getJSONArray("releasegroups").getJSONObject(instance);
        }
        return null;
    }


    private JSONObject getArtists(JSONObject recording, int instance) {
        if(recording.has("artists")) {
            return recording.getJSONArray("artists").getJSONObject(instance);
        }
        return null;
    }

    /**
     * to determine the amount of elements for a certain Array in a json (does not go up or down in hierarchy
     * @param json the json in which the search should take place
     * @param key the key that should be searched
     * @return size of the array or 0 if the key was not found
     */
    private int getInstanceSize(JSONObject json, String key) {
        int size = 0;
        if(json.has(key)) {
            JSONArray jAr = json.getJSONArray(key);
            size = jAr.length();
        }
        return size;
    }

    /**
     * Checks if the json status is ok
     * @param json
     * @return boolean
     */
    private boolean valid(JSONObject json) {
        if(json.has("status")) {
            if("ok".equals(json.get("status").toString())) {
                return true;
            }
        }
        return false;
    }

}



