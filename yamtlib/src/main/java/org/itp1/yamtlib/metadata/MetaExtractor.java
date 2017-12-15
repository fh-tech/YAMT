package org.itp1.yamtlib.metadata;

import org.json.JSONObject;

public interface MetaExtractor {

    String extractArtist(JSONObject json);

    String extractAlbum(JSONObject json);

    String extractTitle(JSONObject json);

}
