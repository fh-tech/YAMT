package org.itp1.yamtlib.music;

import org.jaudiotagger.tag.FieldDataInvalidException;
import org.jaudiotagger.tag.FieldKey;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class TestMetaWriting extends TestMeta {

    @Test
    public void testWriteFiles() {
        MetaData metaData;
        for(File tf : testFiles) {
            metaData = new MetaData(tf);
            try {
                metaData.getTags().setField(FieldKey.ARTIST, "testArtist");
            } catch (FieldDataInvalidException e) {
                System.out.println("exception");
                e.printStackTrace();
            }
            System.out.println(metaData.getTags().getFirst(FieldKey.ARTIST));
            System.out.println(metaData.getTags().getFirst(FieldKey.ARTIST) == "testArtist");
            Assert.assertTrue(metaData.getTags().getFirst(FieldKey.ARTIST) == "testArtist");
        }
    }

}
