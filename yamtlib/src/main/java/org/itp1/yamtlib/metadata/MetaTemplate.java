package org.itp1.yamtlib.metadata;

import org.itp1.yamtlib.errors.YamtException;
import org.itp1.yamtlib.music.WantedKey;

import java.util.LinkedHashMap;
import java.util.Map;


public class MetaTemplate<T, J> {

    private Map<WantedKey, String> apiMeta;

    /**
     * makes a new Metatemplate with all the WantedKey entries as fields initially they are ""
     */
    public MetaTemplate() {
        apiMeta = new LinkedHashMap<>();
        for(WantedKey key : WantedKey.values()) {
            this.setValue(key, "");
        }
    }

    /**
     * used to fill a MetaTemplates fields by extracting the values from a result object
     * @param extractor implementation of the interface MetaExtractor
     * @param j a Response Object that the defined Metaextractor needs to be able to handle
     * @return the MetaTemplate with its field set field that were not able to be set are ""
     */
    public MetaTemplate fillTemplate(MetaExtractor<J> extractor, J j) throws YamtException.MetaDataException {
        for (WantedKey key : this.apiMeta.keySet()) {
            try {
                this.setValue(key, extractor.extract(key, j));
            } catch (YamtException.MetaDataException e) {
                throw new YamtException.MetaDataException("filling of template failed", e);
            }
        }
        return this;
    }

    private void setValue(WantedKey key, String newValue) {
        this.apiMeta.put(key, newValue);
    }

    private String getValue(WantedKey key) {
        return this.apiMeta.get(key);
    }

}
