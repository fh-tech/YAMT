package org.itp1.yamtlib.files;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class TestGettingFiles {

    @Test
    public void testCollectFilesFromFolder(){
        FileHandler fHandler = new FileHandler();
        fHandler.searchRecursive = true;

        fHandler.collectFilesFromFolder("C:\\Users\\Matt\\Desktop\\01 - Studioalben");
        Assert.assertNotEquals(fHandler.collectedFiles.size(), 0);
        fHandler.printCollection();
    }

    @Test
    public void testcollectFilesFromFolderException(){
        FileHandler fHandler = new FileHandler();

        fHandler.collectFilesFromFolder("C:\\Users\\Matt\\Desktop\\NonExistingFolder");
        // no exception
    }

    @Test
    public void testCollectFilesFromList(){
        FileHandler fHandler = new FileHandler();
        String[] fileList = new String[] {
                "C:\\Users\\Matt\\Desktop\\01 - Studioalben\\2012 - Auch\\01 - Ist das noch Punkrock.mp3",
                "C:\\Users\\Matt\\Desktop\\01 - Studioalben\\2012 - Auch\\02 - Bettmagnet.mp3",
                "C:\\Users\\Matt\\Desktop\\01 - Studioalben\\2012 - Auch\\01 – Himmelblau.mp3",
                "C:\\Users\\Matt\\Desktop\\01 - Studioalben\\2007 - Jazz ist anders\\01 – Himmelblau.mp3",
                "C:\\Users\\Matt\\Desktop\\01 - Studioalben\\2012 - Auch\\a676.jpeg"
        };

        fHandler.collectFilesFromFileList(fileList);
        fHandler.removeFileFromCollection(new File(fileList[0]));

        Assert.assertEquals(fHandler.collectedFiles.size(), 2);
    }
}
