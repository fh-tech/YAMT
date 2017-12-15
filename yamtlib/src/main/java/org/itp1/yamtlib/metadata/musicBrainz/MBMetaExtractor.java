package org.itp1.yamtlib.metadata.musicBrainz;

import org.itp1.yamtlib.metadata.MetaExtractor;
import org.json.JSONArray;
import org.json.JSONObject;

public class MBMetaExtractor implements MetaExtractor{

    public String extractArtist(JSONObject json) {
        String artist = "";
        JSONObject results = json.getJSONArray("results").getJSONObject(0);
        JSONObject recording = results.getJSONArray("recordings").getJSONObject(0);
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

        System.out.println("Artist: " + artist);

        return artist;
    }

    public String extractTitle(JSONObject json) {
        String title = "";
        JSONObject results = json.getJSONArray("results").getJSONObject(0);
        JSONObject recording = results.getJSONArray("recordings").getJSONObject(0);
        title = recording.get("title").toString();
        System.out.println("Title: " + title);

        return title;
    }


    public String extractAlbum(JSONObject json) {
        String album = "";
        JSONObject results = json.getJSONArray("results").getJSONObject(0);
        JSONObject recording = results.getJSONArray("recordings").getJSONObject(0);
        JSONObject releaseGroups = recording.getJSONArray("releasegroups").getJSONObject(0);
        album = releaseGroups.get("title").toString();
        System.out.println("Album: " + album);

        return album;
    }


}
