package org.itp1.yamtlib.metadata;
import lombok.NoArgsConstructor;
import org.itp1.yamtlib.errors.YamtException;
import org.itp1.yamtlib.music.YamtMusic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor
public class MetaFetcher {


    //TODO: use GSON to parse json

    public static final String url = "https://api.acoustid.org/v2/lookup";
    public static final String key = "RtWAwftsMAE";

    public String[] fetchFPDur(List<YamtMusic> music) throws YamtException.MetaDataException {
        Fpcalc program = getProgram();
        List<String> absPaths = new ArrayList<>();
        absPaths.add(program.getProgramFile().getAbsolutePath() + " -json");
        music.stream()
                .map((m) -> m.getFile().getAbsolutePath())
                .forEach(absPaths::add);

        Runtime rt = Runtime.getRuntime();
        try {
            Process pr = rt.exec(absPaths.toArray(new String[0]));
        } catch (IOException e) {
            throw new YamtException.MetaDataException(e);
        }

    }

    //TODO: make private after tests
    public Fpcalc getProgram() throws YamtException.MetaDataException {
        String os = System.getProperty("os.name").toLowerCase();
        if(os.indexOf("windows") != 0) {
            return Fpcalc.WINDOWS;
        } else if(os.indexOf("linux") != 0) {
            return Fpcalc.LINUX;
        } else if(os.indexOf("mac") != 0) {
            return Fpcalc.MAC;
        } else {
            throw new YamtException.MetaDataException("OS is not supported");
        }
    }


}
