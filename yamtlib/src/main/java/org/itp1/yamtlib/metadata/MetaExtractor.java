package org.itp1.yamtlib.metadata;

import org.itp1.yamtlib.errors.YamtException;
import org.itp1.yamtlib.music.WantedKey;

public interface MetaExtractor<J> {

    String extract(WantedKey key, J j) throws YamtException.MetaDataException;

}
