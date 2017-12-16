package org.itp1.yamtlib.metadata;

import org.itp1.yamtlib.errors.YamtException;
import org.itp1.yamtlib.music.YamtMusic;

import java.util.Map;

public interface MetaRequestor<T, J> {
    Map<YamtMusic, J> fetchMetaData(T t) throws YamtException.MetaDataException;
}

