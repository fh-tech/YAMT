package org.itp1.yamtlib.metadata;

import org.itp1.yamtlib.errors.YamtException;
import org.itp1.yamtlib.music.WantedKey;
import org.itp1.yamtlib.music.YamtMusic;

import java.util.Map;



public class MetaFiller {
    /**
     * uses all WantedKeys to get the values from the MetaTemplate and sets the MetaTemplates values as the YamtMusic MetaTags - does not commit it!
     * @param ymTemp Map<YamtMusic, MetaTemplate>
     */
    public void fillYamtMusic(Map<YamtMusic, MetaTemplate> ymTemp) {
        for(YamtMusic ym : ymTemp.keySet()) {
            MetaTemplate template = ymTemp.get(ym);
            for(WantedKey key: WantedKey.values()) {
                try {
                    ym.setTag(key, template.getValue(key));
                } catch (YamtException.MusicException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void commitMetaData(Map<YamtMusic, MetaTemplate> ymTemp) {
        for (YamtMusic ym : ymTemp.keySet()) {
            //commit its metadata
        }
    }

}
