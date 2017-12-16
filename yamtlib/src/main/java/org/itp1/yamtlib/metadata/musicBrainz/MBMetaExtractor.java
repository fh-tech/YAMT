package org.itp1.yamtlib.metadata.musicBrainz;

import org.itp1.yamtlib.errors.YamtException;
import org.itp1.yamtlib.metadata.MetaExtractor;
import org.itp1.yamtlib.music.WantedKey;
import org.json.JSONArray;
import org.json.JSONObject;

public class MBMetaExtractor implements MetaExtractor<JSONObject> {


    @Override
    public String extract(WantedKey key, JSONObject j) throws YamtException.MetaDataException {
        System.out.println(j);
        if(valid(j)) {
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

    private String extractArtist(JSONObject json) {
        String artist = "";
        JSONObject recording = getRecording(json);
        if(recording != null) {
            if(recording.has("artists")) {
                JSONArray artists = recording.getJSONArray("artists");
                // if more artist entries one could be feat.
                if (artists.length() > 1) {
                    String feat = "";
                    for (int i = 0; i < artists.length(); i++) {
                        if (artists.getJSONObject(i).has("joinphrase")) {
                            feat = " feat. " + artists.getJSONObject(i).get("name").toString();
                        } else {
                            artist = artists.getJSONObject(i).get("name").toString();
                        }
                    }
                    artist = artist + feat;
                } else {
                    artist = artists.getJSONObject(0).get("name").toString();
                }
            }
        }
        System.out.println("Artist: " + artist);
        return artist;
    }

    private String extractTitle(JSONObject json) {
        String title = "";
        JSONObject recording = getRecording(json);
        if(recording != null) {
            title = recording.get("title").toString();
        }
        System.out.println("Title: " + title);
        return title;
    }

    private String extractAlbum(JSONObject json) {
        String album = "";
        JSONObject recording = getRecording(json);
        if(recording != null) {
            if(recording.has("releasegroups")) {
                JSONArray jAr = recording.getJSONArray("releasegroups");
                if(jAr.length() > 0) {
                    JSONObject releaseGroups = jAr.getJSONObject(0);
                    album = releaseGroups.get("title").toString();
                }
            }
        }
        System.out.println("Album: " + album);
        return album;
    }

    private JSONObject getRecording(JSONObject json) {
        if(json.has("results")) {
            JSONArray jAr = json.getJSONArray("results");
            if(jAr.length() > 0) {
                json = jAr.getJSONObject(0);
                if(json.has("recordings")) {
                    return json.getJSONArray("recordings").getJSONObject(0);
                }
            }
        }
        return null;
    }

    private boolean valid(JSONObject json) {
        if(json.has("status")) {
            if(json.get("status") != "error") {
                return true;
            }
        }
        return false;
    }

}



