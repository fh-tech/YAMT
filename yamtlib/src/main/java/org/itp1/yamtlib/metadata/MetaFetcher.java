package org.itp1.yamtlib.metadata;

import org.itp1.yamtlib.errors.YamtException;
import org.itp1.yamtlib.metadata.musicBrainz.AccousticFingerprint;
import org.itp1.yamtlib.metadata.musicBrainz.Fingerprinter;
import org.itp1.yamtlib.metadata.musicBrainz.MBMetaExtractor;
import org.itp1.yamtlib.metadata.musicBrainz.MBMetaRequestor;
import org.itp1.yamtlib.music.YamtMusic;
import org.json.JSONObject;
import org.junit.Assert;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MetaFetcher {

    private static final ExecutorService executer = Executors.newFixedThreadPool(20);

    public Map<YamtMusic, MetaTemplate> fetch(List<YamtMusic> music) throws YamtException.MetaDataException{
        //generate a Fingerprint for each music file
        Fingerprinter fingerprinter = new Fingerprinter();
        Map<YamtMusic, AccousticFingerprint> ymFP = fingerprinter.mapFingerPrints(music);

        //fetch meta data from api
        MBMetaRequestor mbMetaRequestor = new MBMetaRequestor();
        Map<YamtMusic, JSONObject> requestResult = mbMetaRequestor.fetchMetaData(ymFP);

        //extract metadata from api responses
        Map<YamtMusic, MetaTemplate> ymTemplate = new LinkedHashMap<>();
        MBMetaExtractor mbMetaExtractor = new MBMetaExtractor();

        for (YamtMusic yamtMusic : requestResult.keySet()) {
            try {
                MetaTemplate metaTemplate = new MetaTemplate();
                metaTemplate = metaTemplate.fillTemplate(mbMetaExtractor, requestResult.get(yamtMusic));
                ymTemplate.put(yamtMusic, metaTemplate);
            }catch (Exception e){
                System.out.println("Unable to get metadata for file ["+yamtMusic.getFile().getAbsolutePath()+"]\n" + e.getMessage());
            }
        }
        return ymTemplate;
    }


}
