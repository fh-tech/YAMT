package org.itp1.yamtlib.music;

import lombok.Getter;
import org.itp1.yamtlib.errors.YamtException;
import org.jaudiotagger.tag.FieldKey;

import java.util.Arrays;

@Getter
public enum WantedKey {
    ALBUM(FieldKey.ALBUM, true, "album"),
    ALBUM_ARTIST(FieldKey.ALBUM_ARTIST, false, "album-artist"),
    ALBUM_ARTIST_SORT(FieldKey.ALBUM_ARTIST_SORT, false, "album-artist-sort"),
    ARTIST(FieldKey.ARTIST, true,"artist"),
    ARTIST_SORT(FieldKey.ARTIST_SORT, false, "artist-sort"),
    YEAR(FieldKey.YEAR, false, "year"),
    COMMENT(FieldKey.COMMENT, false, "comment"),
    GENRE(FieldKey.GENRE, false, "genre"),
    LANGUAGE(FieldKey.LANGUAGE, false, "language"),
    LYRICIST(FieldKey.LYRICIST, false, "lyricist"),
    LYRICS(FieldKey.LYRICS, false, "lyrics"),
    TRACK(FieldKey.TRACK, false, "track"),
    TITLE(FieldKey.TITLE, true, "title"),
    TAGS(FieldKey.TAGS, false, "tags"),
    QUALITY(FieldKey.QUALITY, false, "quality"),
    ORIGINAL_ALBUM(FieldKey.ORIGINAL_ALBUM, false, "original-album"),
    ORIGINAL_ARTIST(FieldKey.ORIGINAL_ARTIST, false, "original-artist"),
    ORIGINAL_LYRICIST(FieldKey.ORIGINAL_LYRICIST, false, "original-lyricist"),
    ORIGINAL_YEAR(FieldKey.ORIGINAL_YEAR, false, "original-year");

    private FieldKey fk;
    private String template;
    private boolean fetchable;

    private WantedKey(FieldKey fk, boolean fetchable, String template) {
        this.fk = fk;
        this.fetchable = fetchable;
        this.template = template;
    }

    public static WantedKey fromTemplateString(String s) throws YamtException.MusicException{
        for(WantedKey key : WantedKey.values()){
            if(key.template.equals(s))
                return key;
        }
        throw new YamtException.MusicException("No metadata for template " + s);
    }

}
