package org.itp1.yamtlib;

import org.itp1.yamtlib.errors.YamtException;
import org.itp1.yamtlib.format.MusicFormatter;
import org.itp1.yamtlib.music.WantedKey;
import org.itp1.yamtlib.music.YamtMusic;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.mockito.Mockito.when;


public class TestFormat {

    private static YamtMusic wellTagged;
    private static YamtMusic throwsException;
    private static YamtMusic empty;

    @BeforeClass
    public static void setup(){
        wellTagged = Mockito.mock(YamtMusic.class);
        throwsException = Mockito.mock(YamtMusic.class);
        try {
            when(wellTagged.getTag(WantedKey.ALBUM))
                    .thenReturn("aLBum");

            when(wellTagged.getTag(WantedKey.ARTIST))
                    .thenReturn("ArtIsT");

            when(wellTagged.getTag(WantedKey.TITLE))
                    .thenReturn("tiTle");

            when(throwsException.getTag(Mockito.any()))
                    .thenThrow(new YamtException.MusicException("Mock Exception"));

        }catch (Exception e){}
    }



    @Test
    public void testFormatting() throws Exception {
        MusicFormatter mf = new MusicFormatter("{artist}/{title}");
        String newName = mf.format(wellTagged);
        Assert.assertEquals("ArtIsT/tiTle", newName);
    }

    @Test
    public void testEscapeBracket() throws Exception{
        MusicFormatter mf = new MusicFormatter("\\{\\}abc{album}/{title}");
        String newName = mf.format(wellTagged);
        Assert.assertEquals("{}abcaLBum/tiTle", newName);
    }

    @Test
    public void testFormatStringWithoutTemplateShouldReturnEmptyString() throws Exception{
        String testString  = "hello/wor\\{ld/ab\\}c";
        MusicFormatter mf = new MusicFormatter(testString);
        String newName = mf.format(Mockito.mock(YamtMusic.class));
        Assert.assertEquals("", newName);
    }

    @Test(expected = YamtException.FormatException.class)
    public void shouldRethrow() throws YamtException {
        MusicFormatter mf = new MusicFormatter("{artist}/{title}");
        mf.format(throwsException);
    }

    @Test(expected = YamtException.FormatException.class)
    public void shouldThrowExceptionWhenMetaTagIsNotSet() throws YamtException{
        MusicFormatter mf = new MusicFormatter("{hello}/world");
        mf.format(empty);
    }
}
