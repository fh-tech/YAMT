package org.itp1.yamtlib.music;

import lombok.Getter;
import org.jaudiotagger.tag.FieldKey;

@Getter
public enum WantedKey {
    ALBUM(FieldKey.ALBUM, true),
    ALBUM_ARTIST(FieldKey.ALBUM_ARTIST, false),
    ALBUM_ARTIST_SORT(FieldKey.ALBUM_ARTIST_SORT, false),
    ARTIST(FieldKey.ARTIST, true),
    ARTIST_SORT(FieldKey.ARTIST_SORT, false),
    YEAR(FieldKey.YEAR, false),
    COMMENT(FieldKey.COMMENT, false),
    GENRE(FieldKey.GENRE, false),
    LANGUAGE(FieldKey.LANGUAGE, false),
    LYRICIST(FieldKey.LYRICIST, false),
    LYRICS(FieldKey.LYRICS, false),
    TRACK(FieldKey.TRACK, false),
    TITLE(FieldKey.TITLE, true),
    TAGS(FieldKey.TAGS, false),
    QUALITY(FieldKey.QUALITY, false),
    ORIGINAL_ALBUM(FieldKey.ORIGINAL_ALBUM, false),
    ORIGINAL_ARTIST(FieldKey.ORIGINAL_ARTIST, false),
    ORIGINAL_LYRICIST(FieldKey.ORIGINAL_LYRICIST, false),
    ORIGINAL_YEAR(FieldKey.ORIGINAL_YEAR, false);

    private FieldKey fk;
    private boolean fetchable;

    private WantedKey(FieldKey fk, boolean fetchable) {
        this.fk = fk;
        this.fetchable = fetchable;
    }


}
