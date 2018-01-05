package org.itp1.yamtlib.metadata.musicBrainz;


import com.google.common.io.LineReader;
import com.google.gson.Gson;
import lombok.NoArgsConstructor;
import org.itp1.yamtlib.errors.YamtException;
import org.itp1.yamtlib.music.YamtMusic;


import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class Fingerprinter {

    public Map<YamtMusic, AccousticFingerprint> mapFingerPrints(List<YamtMusic> music) throws YamtException.MetaDataException {
        try {
            List<String> absPaths = fetchAbsPaths(music);
            List<AccousticFingerprint> accousticFPs = fetchFPs(absPaths);
            return mapFPtoMusic(music, accousticFPs);
        } catch(Exception e) {
            throw new YamtException.MetaDataException("mapping Fingerprints failed", e);
        }
    }

    // builds the command for FPCalc - fetches program path adds "-json" as format specifier and then adds
    // all absolute paths
    private List<String> fetchAbsPaths(List <YamtMusic> yM) throws YamtException.MetaDataException {
        try {
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
        } catch(YamtException.MetaDataException e) {
            throw new YamtException.MetaDataException("Construction of command failed", e);
        }

    }

    // calls the command with the given list and produces a List of Accoustic-FPs
    private List<AccousticFingerprint> fetchFPs(List<String> absPaths) throws YamtException.MetaDataException {
        try {
            List<AccousticFingerprint> accousticFPs = new ArrayList<>();
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec(absPaths.toArray(new String[0]));
            if (pr.getInputStream() != null) {
                Reader reader = new InputStreamReader(pr.getInputStream());
                LineReader linereader = new LineReader(reader);
                String s;
                while ((s = linereader.readLine()) != null) {
                    Gson gson = new Gson();
                    accousticFPs.add(gson.fromJson(s, AccousticFingerprint.class));
                }
            }
            return accousticFPs;
        } catch (Exception e) {
            throw new YamtException.MetaDataException("creating Fingerprints failed", e);
        }
    }

    // maps a list of YamtMusic to a list of AccousticFingerprints
    private Map<YamtMusic, AccousticFingerprint> mapFPtoMusic(List<YamtMusic> yM, List<AccousticFingerprint> aF) throws YamtException.MetaDataException {
        try {
            if(yM.size() != aF.size()) {
                throw new YamtException.MetaDataException("list sizes are not equal");
            }
            Map<YamtMusic, AccousticFingerprint> mappedMusic = new LinkedHashMap<>();
            for(int i = 0; i < yM.size(); i++) {
                mappedMusic.put(yM.get(i), aF.get(i));
            }
            return mappedMusic;
        } catch(Exception e) {
            throw new YamtException.MetaDataException("mapping the 2 lists failed", e);
        }

    }

    // gets the appropriate executable for the current operating system
    private Fpcalc getProgram() throws YamtException.MetaDataException {
        String os = getOSString();
        if (os.contains("windows")) {
            return Fpcalc.WINDOWS;
        } else if (os.contains("linux")) {
            return Fpcalc.LINUX;
        } else if (os.contains("mac")) {
            return Fpcalc.MAC;
        } else {
            throw new YamtException.MetaDataException("OS is not supported");
        }
    }

    private String getOSString() {
        return System.getProperty("os.name").toLowerCase();
    }


}
