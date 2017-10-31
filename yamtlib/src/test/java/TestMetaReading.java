import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;


public class TestMetaReading {

    private static final String TEST_MP3 = "test.mp3";

    @Test
    public void testFileRead() {

        Tika t = new Tika();
        try {
            BodyContentHandler contentHandler = new BodyContentHandler();
            Metadata metaData = new Metadata();
            InputStream fis = this.getClass().getResourceAsStream(TEST_MP3);
            ParseContext pctx = new ParseContext();
            Mp3Parser mp3 = new Mp3Parser();

            mp3.parse(fis, contentHandler, metaData, pctx);

            System.out.println(
                    Arrays.stream(metaData.names())
                            .map((s) -> s + " : " + metaData.get(s))
                            .reduce((s1, s2) -> s1 + "\n" + s2));

        } catch (IOException | SAXException | TikaException e) {
            Assert.fail(e.getMessage());
        }

    }


}
