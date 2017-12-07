package org.itp1.yamtlib.format;

import lombok.NonNull;
import org.itp1.yamtlib.errors.YamtException;
import org.itp1.yamtlib.music.WantedKey;
import org.itp1.yamtlib.music.YamtMusic;

/***
 * This class represents a Formatter for music file names.
 * Format Strings should be in the format: abc{tagname}bnb
 * When given a specific music file in form of a {@link YamtMusic} object {tagname} is replaced by the value of
 * the metadata 'tagname', or throws an exception should the tag name be unknown or not set on the file.
 */

public class MusicFormatter {

    private final String formatString;

    public MusicFormatter(@NonNull String formatString) {
        this.formatString = formatString;
    }

    /***
     * Returns the next occurrence of char of in String s not preceded by a '\'
     */
    private static int indexOfNotEscaped(int from, char of, String s) {
        int index = s.indexOf(of, from);
        //if index is -1 return it if index is 0 it cant be escaped, if it is higher check for '/'
        if (index > 0 && s.charAt(index - 1) == '\\') {
            return indexOfNotEscaped(index + 1, of, s);
        } else {
            return index;
        }
    }

    /***
     * Generates a new file name for a given music file with the <code>formatString</code> used to construct this {@link MusicFormatter}
     * @param music A YamtMusic object for which a new file name is generated
     * @return the new filename relative to the output directory as String
     * @throws YamtException.FormatException when the given format string is invalid or if the required metadata is not present
     */
    public String format(@NonNull YamtMusic music) throws YamtException.FormatException {
        try {
            return partialFormat(formatString, music)
                    .replaceAll("\\\\\\{", "{")
                    .replaceAll("\\\\}", "}");
        } catch (YamtException.FormatException fe) {
            throw fe;
        } catch (YamtException e) {
            throw new YamtException.FormatException("Unable to format file [" + music.getFile() + "]", e);
        }
    }

    private String partialFormat(String remainingFormat, @NonNull YamtMusic music) throws YamtException {

        //get both opening and closing Brackets in remaining String
        int iOpenBracket = indexOfNotEscaped(0, '{', remainingFormat);
        if (iOpenBracket < 0) return "";

        int iCloseBracket = indexOfNotEscaped(0, '}', remainingFormat);
        if (iCloseBracket < 0) return "";

        //between brackets is the identifier
        String metaDataIdentifier = remainingFormat.substring(iOpenBracket + 1, iCloseBracket);
        String metaData = music.getTag(WantedKey.fromTemplateString(metaDataIdentifier));

        if (metaData != null && !metaData.equals("")) {
            //call this method with the remaining string after the closing bracket
            return remainingFormat.substring(0, iOpenBracket) +
                    metaData +
                    partialFormat(remainingFormat.substring(iCloseBracket + 1), music);

        } else {
            throw new YamtException.FormatException(
                    "Unable to generate new name for file ["
                            + (music.getFile() != null ? music.getFile().getPath() : null) +
                            "]\n Missing metadata: [" + metaDataIdentifier + "]");
        }
    }
}
