package org.itp1.yamtlib.metadata.musicBrainz;
import com.google.gson.Gson;
import lombok.NoArgsConstructor;
import org.itp1.yamtlib.errors.YamtException;
import org.itp1.yamtlib.metadata.musicBrainz.AccousticFingerprint;
import org.itp1.yamtlib.metadata.musicBrainz.Fpcalc;
import org.itp1.yamtlib.music.YamtMusic;
import org.testifyproject.guava.common.io.LineReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;

@NoArgsConstructor
public class Fingerprinter {

    public Map<YamtMusic, AccousticFingerprint> mapFingerPrints(List<YamtMusic> music) throws YamtException.MetaDataException {
        List<String> absPaths = fetchAbsPaths(music);
        List<AccousticFingerprint> accousticFPs = fetchFPs(absPaths);
        return mapFPtoMusic(music, accousticFPs);
    }

    // collects all the absPaths from the music
    private List<String> fetchAbsPaths(List <YamtMusic> yM) throws YamtException.MetaDataException {
        List<String> absPaths = new ArrayList<>();
        Fpcalc program = getProgram();
        //pass the path to program
        absPaths.add(program.getProgramFile().getAbsolutePath());
        //pass -json for output formatting
        absPaths.add("-json");
        yM.stream()
                .map((m) -> m.getFile().getAbsolutePath())
                .forEach(absPaths::add);
        return absPaths;
    }

    // calls a command with the given list
    private List<AccousticFingerprint> fetchFPs(List<String> absPaths) throws YamtException.MetaDataException {
        List<AccousticFingerprint> accousticFPs = new ArrayList<>();

        Runtime rt = Runtime.getRuntime();
        try {
            Process pr = rt.exec(absPaths.toArray(new String[0]));
            if (pr.getInputStream() != null) {
                Reader reader = new InputStreamReader(pr.getInputStream());
                LineReader linereader = new LineReader(reader);
                String s;
                while ((s = linereader.readLine()) != null) {
                    Gson gson= new Gson();
                    accousticFPs.add(gson.fromJson(s, AccousticFingerprint.class));
                }
            }
        } catch (IOException e) {
            throw new YamtException.MetaDataException(e);
        }
        return accousticFPs;
    }

    // maps a list of YamtMusic to a list of AccousticFingerprints
    private Map<YamtMusic, AccousticFingerprint> mapFPtoMusic(List<YamtMusic> yM, List<AccousticFingerprint> aF)  {
        Map<YamtMusic, AccousticFingerprint> mappedMusic = new LinkedHashMap<>();
        for(int i = 0; i < yM.size(); i++) {
            mappedMusic.put(yM.get(i), aF.get(i));
        }
        return mappedMusic;
    }

    // gets the appropriate executable for the current operating system
    private Fpcalc getProgram() throws YamtException.MetaDataException {
        String os = System.getProperty("os.name").toLowerCase();
        if(os.contains("windows")) {
            return Fpcalc.WINDOWS;
        } else if(os.contains("linux")) {
            return Fpcalc.LINUX;
        } else if(os.contains("mac")) {
            return Fpcalc.MAC;
        } else {
            throw new YamtException.MetaDataException("OS is not supported");
        }
    }


}
