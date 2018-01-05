package org.itp1.yamtlib;

import org.hamcrest.CoreMatchers;
import org.itp1.yamtlib.config.YamtConfig;
import org.itp1.yamtlib.errors.YamtException;
import org.itp1.yamtlib.files.FileHandler;
import org.itp1.yamtlib.format.FormatResult;
import org.itp1.yamtlib.format.MusicFormatter;
import org.itp1.yamtlib.metadata.MetaFetcher;
import org.itp1.yamtlib.metadata.MetaRequestor;
import org.itp1.yamtlib.metadata.MetaTemplate;
import org.itp1.yamtlib.metadata.musicBrainz.MBMetaRequestor;
import org.itp1.yamtlib.music.YamtMusic;
import org.itp1.yamtlib.util.YamtUtils;
import org.jaudiotagger.audio.exceptions.CannotWriteException;

import java.io.File;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.itp1.yamtlib.util.YamtUtils.handleException;
import static org.itp1.yamtlib.util.YamtUtils.sneakyThrow;

public class Yamt {

    public void runYamt(YamtConfig cfg) throws YamtException {
        Stream<File> files = new FileHandler().collectViaConfig(cfg).stream();

        if (!cfg.getMoveOnRename()) {
            files = files.map(sneakyThrow(Yamt::moveToTmp));
        }

        Stream<YamtMusic> musicStream = files.map(
                handleException(
                        f -> new YamtMusic(f),
                        e -> System.out.println(e.getMessage())));

        List<YamtMusic> musicList = handleMetaData(musicStream.collect(Collectors.toList()), cfg);

        if(cfg.getFormat().isPresent()) {

            Map<YamtMusic, String> fromattedMusic = formatMusic(musicList, cfg.getFormat().get()).getSuccess();

            copyFiles();

        }else{
            for(YamtMusic m: musicList){
                try {
                    m.getAudioFile().commit();
                } catch (CannotWriteException e) {
                    System.out.println(e.getMessage());
                }
            }
            System.out.println("Finished!");
        }


    }

    private List<YamtMusic> handleMetaData(List<YamtMusic> music, YamtConfig config) throws YamtException {
        switch(config.getStrategy()){
            case FETCH_NEVER: return music;
            case FETCH_ALWAYS: return fetchMetadata(music);
            case FETCH_REQUIRED: {
                FormatResult result = formatMusic(music, config.getFormat().get());
                List<YamtMusic> successful = new ArrayList<>(result.getSuccess().keySet());
                if(result.getFailed().isEmpty()){
                    return successful;
                }else{
                    List<YamtMusic> missing = fetchMetadata(result.getFailed());
                    missing.addAll(successful);
                    return missing;
                }
            }
            default: throw new RuntimeException("Not reachable!");
        }
    }

    private FormatResult formatMusic(List<YamtMusic> music, String format) throws YamtException {
        MusicFormatter mf = new MusicFormatter(format);
        Map<YamtMusic, String> resultMap = new HashMap<>();
        List<YamtMusic> failed = new ArrayList<>();
        for (YamtMusic m : music){
            try{

                mf.format(m);

            }catch (YamtException.MissingMetaDataException e){
                failed.add(m);
                System.out.println(e.getMessage());
            }
        }

        return new FormatResult(failed, resultMap);
    }


    private Function<YamtMusic, String> getFormatter(YamtConfig cfg) {
        Function<YamtMusic, String> formatter;
        if (cfg.getFormat().isPresent()) {
            MusicFormatter mf = new MusicFormatter(cfg.getFormat().get());
            formatter = handleException(
                    m -> mf.format(m),
                    e -> System.out.println(e.getMessage())
            );
        } else {
            formatter = (m) -> m.getFile().getName();
        }
        return formatter;
    }

    private List<YamtMusic> fetchMetadata(List<YamtMusic> musicList) throws YamtException{

        MetaFetcher mf = new MetaFetcher();
        Map<YamtMusic, MetaTemplate> metaDataMap = mf.fetch(musicList);

        List<YamtMusic> returnList = new ArrayList<>(metaDataMap.size());
        for(YamtMusic music : metaDataMap.keySet()){
            //TODO: update music with metadata

            returnList.add(music);
        }
        return returnList;
    }

    public static File moveToTmp(File fileToMove) throws Exception {
        File tmpDir = new File(System.getProperty("java.io.tmpdir"));
        return Files.copy(fileToMove.toPath(), tmpDir.toPath().resolve(fileToMove.getName()),
                StandardCopyOption.COPY_ATTRIBUTES,
                StandardCopyOption.REPLACE_EXISTING)
                .toFile();
    }



}
