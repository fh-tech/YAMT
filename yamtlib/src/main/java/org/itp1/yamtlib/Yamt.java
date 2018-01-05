package org.itp1.yamtlib;

import org.itp1.yamtlib.config.YamtConfig;
import org.itp1.yamtlib.errors.YamtException;
import org.itp1.yamtlib.files.FileHandler;
import org.itp1.yamtlib.format.FormatResult;
import org.itp1.yamtlib.format.MusicFormatter;
import org.itp1.yamtlib.metadata.MetaFetcher;
import org.itp1.yamtlib.metadata.MetaTemplate;
import org.itp1.yamtlib.music.YamtMusic;
import org.itp1.yamtlib.util.YamtUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.itp1.yamtlib.util.YamtUtils.handleException;
import static org.itp1.yamtlib.util.YamtUtils.sneakyThrow;

public class Yamt {

    public void runYamt(YamtConfig cfg) throws YamtException {
        Logger.getLogger("org.jaudiotagger").setLevel(Level.OFF);
        Stream<File> files = new FileHandler().collectViaConfig(cfg).stream();

        if (!cfg.getMoveOnRename()) {
            files = files.map(sneakyThrow(Yamt::copyToTmp));
        }

        Stream<YamtMusic> musicStream = files.map(
                handleException(
                        f -> new YamtMusic(f),
                        e -> System.out.println(e.getMessage())));

        List<YamtMusic> musicList = handleMetaData(musicStream.collect(Collectors.toList()), cfg);

        if (cfg.getFormat().isPresent()) {

            Map<YamtMusic, String> formattedMusic = formatMusic(musicList, cfg.getFormat().get()).getSuccess();
            formattedMusic.forEach(
                    sneakyThrow(
                            (y, s)-> moveFile(y, s, cfg)
                    ));
        }

    }


    private List<YamtMusic> handleMetaData(List<YamtMusic> music, YamtConfig config) throws YamtException {
        switch (config.getStrategy()) {
            case FETCH_NEVER:
                return music;
            case FETCH_ALWAYS:
                return fetchMetadata(music);
            case FETCH_REQUIRED: {
                FormatResult result = formatMusic(music, config.getFormat().get());
                List<YamtMusic> successful = new ArrayList<>(result.getSuccess().keySet());
                if (result.getFailed().isEmpty()) {
                    return successful;
                } else {
                    List<YamtMusic> missing = fetchMetadata(result.getFailed());
                    missing.addAll(successful);
                    return missing;
                }
            }
            default:
                throw new RuntimeException("Not reachable!");
        }
    }

    private FormatResult formatMusic(List<YamtMusic> music, String format) throws YamtException {
        MusicFormatter mf = new MusicFormatter(format);
        Map<YamtMusic, String> resultMap = new HashMap<>();
        List<YamtMusic> failed = new ArrayList<>();
        for (YamtMusic m : music) {
            try {

                String s =  mf.format(m);
                resultMap.put(m, s);
            } catch (YamtException.MissingMetaDataException e) {
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

    private List<YamtMusic> fetchMetadata(List<YamtMusic> musicList) throws YamtException {

        MetaFetcher mf = new MetaFetcher();
        Map<YamtMusic, MetaTemplate> metaDataMap = mf.fetch(musicList);

        List<YamtMusic> returnList = new ArrayList<>(metaDataMap.size());
        for (YamtMusic music : metaDataMap.keySet()) {
            music.fillMetaData(metaDataMap.get(music));
            music.commitMetaData();
            returnList.add(music);
        }
        return returnList;
    }

    public static File copyToTmp(File fileToMove) throws Exception {
        File tmpDir = new File(System.getProperty("java.io.tmpdir"));
        return Files.copy(fileToMove.toPath(), tmpDir.toPath().resolve(fileToMove.getName()),
                StandardCopyOption.COPY_ATTRIBUTES,
                StandardCopyOption.REPLACE_EXISTING)
                .toFile();
    }

    public static File moveFile(YamtMusic music, String fromatString, YamtConfig config) throws IOException {

        String extension = "";

        int i = music.getFile().getAbsolutePath().lastIndexOf('.');

        if (i > 0) {
            extension = music.getFile().getAbsolutePath().substring(i + 1);
        }
        String path = config.getOutputDirectory() +"/"+ fromatString +"."+ extension;
        path = path.replaceAll("[\"|:*?<>]", "");

        String folder = path.substring(0, path.lastIndexOf('/'));
        Files.createDirectories(Paths.get(folder));

        return Files.move(music.getFile().toPath(), Paths.get(path)).toFile();
    }


}
