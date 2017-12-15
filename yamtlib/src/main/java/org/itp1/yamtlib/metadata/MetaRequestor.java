package org.itp1.yamtlib.metadata;

import org.itp1.yamtlib.errors.YamtException;
import org.itp1.yamtlib.metadata.musicBrainz.AccousticFingerprint;
import org.itp1.yamtlib.music.YamtMusic;
import org.json.JSONObject;

import java.util.Collection;
import java.util.Map;

public interface MetaRequestor<T> {

    //TODO: find out how to make parameter generic
    JSONObject fetchMetaDataPost(T t) throws YamtException.MetaDataException;

    JSONObject fetchMetaDataGet(T t) throws YamtException.MetaDataException;
}

