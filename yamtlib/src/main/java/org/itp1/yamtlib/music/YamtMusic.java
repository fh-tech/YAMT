package org.itp1.yamtlib.music;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;

@Setter
@Getter
public class YamtMusic {

    private static ArrayList<YamtMusic> wholemusic = new ArrayList<YamtMusic>();

    private String ALBUM;
    private String ALBUM_ARTIST;
    private String ALBUM_ARTIST_SORT;
    private String ARTIST;
    private String ARTIST_SORT;
    private String YEAR;
    private String COMMENT;
    private String COMPOSER;
    private String COMPOSER_SORT;
    private String GENRE;
    private String KEY;
    private String LANGUAGE;
    private String LYRICIST;
    private String LYRICS;
    private String ENGINEER;
    private String PRODUCER;
    private String TRACK;
    private String TITLE;
    private String TAGS;
    private String QUALITY;
    private String ORIGINAL_ALBUM;
    private String ORIGINAL_ARTIST;
    private String ORIGINAL_LYRICIST;
    private String ORIGINAL_YEAR;
}
