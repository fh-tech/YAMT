package org.itp1.yamtlib.format;

import org.itp1.yamtlib.errors.YamtException;
import org.itp1.yamtlib.music.WantedKey;
import org.itp1.yamtlib.music.YamtMusic;

//TODO: Documentation

public class MusicFormatter {

    private final String formatString;

    public MusicFormatter(String formatString) {
        this.formatString = formatString;
    }

    public String format(YamtMusic music) throws YamtException.FormatException {
        try {
            return partialFormat(formatString, music);

        }catch (YamtException.FormatException fe){
            throw fe;
        }catch(YamtException e){
            throw new YamtException.FormatException("Unable to format file ["+music.getFile()+"]", e);
        }
    }

    private String partialFormat(String remainingFormat, YamtMusic music) throws YamtException {

        //get both opening and closing Brackets in remaining String
        int iOpenBracket = remainingFormat.indexOf('{');
        if (iOpenBracket < 0) return "";

        int iCloseBracket = remainingFormat.indexOf('}', iOpenBracket);
        if (iCloseBracket < 0) return "";

        //between brackets is the identifier
        String metaDataIdentifier = remainingFormat.substring(iOpenBracket+1, iCloseBracket);
        String metaData = music.getTag(WantedKey.fromTemplateString(metaDataIdentifier));

        if(metaData != null && !metaData.equals("")) {
            //this method with the string after the closing bracket
            return remainingFormat.substring(0, iOpenBracket) +
                    metaData +
                    partialFormat(remainingFormat.substring(iCloseBracket+1), music);

        } else {
            throw new YamtException.FormatException(
                    "Unable to generate new name for file ["
                            + music.getFile().getPath() +
                            "]\n Missing metadata: ["+metaDataIdentifier+"]");
        }
    }
}
