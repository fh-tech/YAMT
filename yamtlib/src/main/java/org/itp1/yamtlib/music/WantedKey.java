package org.itp1.yamtlib.music;

import lombok.Getter;
import org.itp1.yamtlib.errors.YamtException;
import org.jaudiotagger.tag.FieldKey;

import java.util.Arrays;

@Getter
public enum WantedKey {
    ALBUM(FieldKey.ALBUM, "album"),
    ALBUM_ARTIST(FieldKey.ALBUM_ARTIST, "album-artist"),
    ALBUM_ARTIST_SORT(FieldKey.ALBUM_ARTIST_SORT, "album-artist-sort"),
    ARTIST(FieldKey.ARTIST,"artist"),
    ARTIST_SORT(FieldKey.ARTIST_SORT, "artist-sort"),
    YEAR(FieldKey.YEAR, "year"),
    COMMENT(FieldKey.COMMENT, "comment"),
    GENRE(FieldKey.GENRE, "genre"),
    LANGUAGE(FieldKey.LANGUAGE, "language"),
    LYRICIST(FieldKey.LYRICIST, "lyricist"),
    LYRICS(FieldKey.LYRICS, "lyrics"),
    TRACK(FieldKey.TRACK, "track"),
    TITLE(FieldKey.TITLE, "title"),
    TAGS(FieldKey.TAGS, "tags"),
    QUALITY(FieldKey.QUALITY, "quality"),
    ORIGINAL_ALBUM(FieldKey.ORIGINAL_ALBUM, "original-album"),
    ORIGINAL_ARTIST(FieldKey.ORIGINAL_ARTIST, "original-artist"),
    ORIGINAL_LYRICIST(FieldKey.ORIGINAL_LYRICIST, "original-lyricist"),
    ORIGINAL_YEAR(FieldKey.ORIGINAL_YEAR, "original-year");

    private FieldKey fk;
    private String template;

    WantedKey(FieldKey fk, String template) {
        this.fk = fk;
        this.template = template;
    }

    public static WantedKey fromTemplateString(String templateString) throws YamtException.MusicException{
        return Arrays.stream(values())
                .filter(key -> key.template.equals(templateString))
                .findFirst()
                .orElseThrow(() -> new YamtException.
                                MusicException("Metadata tempalate string not supported: ["+ templateString +"]"));
    }


}
