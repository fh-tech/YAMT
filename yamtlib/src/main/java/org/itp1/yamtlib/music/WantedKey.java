package org.itp1.yamtlib.music;

import lombok.Getter;
import org.jaudiotagger.tag.FieldKey;

@Getter
public enum WantedKey {
    ALBUM(FieldKey.ALBUM),
    ALBUM_ARTIST(FieldKey.ALBUM_ARTIST),
    ALBUM_ARTIST_SORT(FieldKey.ALBUM_ARTIST_SORT),
    ARTIST(FieldKey.ARTIST),
    ARTIST_SORT(FieldKey.ARTIST_SORT),
    YEAR(FieldKey.YEAR),
    COMMENT(FieldKey.COMMENT),
    GENRE(FieldKey.GENRE),
    LANGUAGE(FieldKey.LANGUAGE),
    LYRICIST(FieldKey.LYRICIST),
    LYRICS(FieldKey.LYRICS),
    TRACK(FieldKey.TRACK),
    TITLE(FieldKey.TITLE),
    TAGS(FieldKey.TAGS),
    QUALITY(FieldKey.QUALITY),
    ORIGINAL_ALBUM(FieldKey.ORIGINAL_ALBUM),
    ORIGINAL_ARTIST(FieldKey.ORIGINAL_ARTIST),
    ORIGINAL_LYRICIST(FieldKey.ORIGINAL_LYRICIST),
    ORIGINAL_YEAR(FieldKey.ORIGINAL_YEAR);

    private FieldKey fk;

    private WantedKey(FieldKey fk) {
        this.fk = fk;
    }


}
