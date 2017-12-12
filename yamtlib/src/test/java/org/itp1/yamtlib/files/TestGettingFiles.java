package org.itp1.yamtlib.files;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.List;

public class TestGettingFiles {

    @Test
    public void testCollectFilesFromFolder() throws Exception{
        FileHandler fHandler = new FileHandler();
        String folderPath = "C:\\Users\\Matt\\Desktop\\01 - Studioalben";
        boolean searchRecursive = true;

        // List<File> collection = fHandler.collectFilesRecursive("C:\\Users\\Matt\\Desktop\\01 - Studioalben");
        // Assert.assertNotEquals(collection.size(), 0);
    }

    @Test
    public void testCollectFromFileList() throws Exception{
        FileHandler fHandler = new FileHandler();
        String[] existingFiles = new String[] {
                "C:\\Users\\Matt\\Desktop\\01 - Studioalben\\2012 - Auch\\01 - Ist das noch Punkrock.mp3",
                "C:\\Users\\Matt\\Desktop\\01 - Studioalben\\2012 - Auch\\02 - Bettmagnet.mp3",
                "C:\\Users\\Matt\\Desktop\\01 - Studioalben\\2007 - Jazz ist anders\\01 – Himmelblau.mp3",
        };
        String[] nonexistentFiles = new String[] {
                "C:\\Users\\Matt\\Desktop\\01 - Studioalben\\2012 - Auch\\01 - A file that doesn't exists.mp3",
                "C:\\Users\\Matt\\Desktop\\01 - Studioalben\\2007 - Jazz ist anders\\01 – Same with that one.mp3"
        };
        String[] duplicates = new String[] {
                "C:\\Users\\Matt\\Desktop\\01 - Studioalben\\2012 - Auch\\01 - Ist das noch Punkrock.mp3",
                "C:\\Users\\Matt\\Desktop\\01 - Studioalben\\2012 - Auch\\01 - Ist das noch Punkrock.mp3",
                "C:\\Users\\Matt\\Desktop\\01 - Studioalben\\2012 - Auch\\01 - Ist das noch Punkrock.mp3",
                "C:\\Users\\Matt\\Desktop\\01 - Studioalben\\2012 - Auch\\01 - Ist das noch Punkrock.mp3"
        };
        String[] wrongFormat = new String[] {
                "C:\\Users\\Matt\\Desktop\\01 - Studioalben\\2012 - Auch\\a676.jpeg"
        };

        // List<File> collection = fHandler.collectFilesNonRecursive(existingFiles);
        // Assert.assertEquals(collection.size(), 3);
    }
}
