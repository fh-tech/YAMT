package org.itp1.yamtlib.metadata;

import org.itp1.yamtlib.music.WantedKey;

import java.util.EnumSet;
import java.util.Map;

public class MetaTemplate {

    private Map<WantedKey, String> apiMeta;

    MetaTemplate() {
        for(WantedKey key : WantedKey.values()) {
            apiMeta.put(key, "");
        }
    }

    void setValue(WantedKey key, String newValue) {
        apiMeta.put(key, newValue);
    }

    String getValue(WantedKey key) {
        return apiMeta.get(key);
    }

}
