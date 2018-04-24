package org.itp1.yamtlib.metadata.musicBrainz;

import com.google.gson.Gson;
import org.itp1.yamtlib.errors.YamtException;
import org.itp1.yamtlib.metadata.MetaTemplate;
import org.itp1.yamtlib.music.WantedKey;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class testMetaExtractor {

    private static final String test = "{\"results\":[{\"score\":0.9999407,\"recordings\":[{\"id\":\"df46ff0a-c4d6-428e-bc13-b42fe98b0703\",\"title\":\"\"}]},{\"score\":0.999407,\"recordings\":[{\"duration\":232,\"artists\":[{\"name\":\"\",\"id\":\"6a6eea03-b435-446c-bab1-312b45580aee\"},{\"joinphrase\":\"feat.\",\"name\":\"TesterStar\",\"id\":\"6a6eea03-b435-446c-bab1-312b45580aee\"},{\"name\":\"TesterBOI\",\"id\":\"f2651b63-a44d-4d1d-ba64-d981d971ecd1\"}],\"releasegroups\":[{\"secondarytypes\":[\"Compilation\"],\"artists\":[{\"name\":\"VariousArtists\",\"id\":\"89ad4ac3-39f7-470e-963a-56509c546377\"}],\"id\":\"c6f9bb3a-8c2e-436a-9cfa-34987833b236\",\"type\":\"Album\",\"title\":\"\"},{\"secondarytypes\":[\"Compilation\"],\"artists\":[{\"name\":\"VariousArtists\",\"id\":\"89ad4ac3-39f7-470e-963a-56509c546377\"}],\"id\":\"ce279c86-d3ea-4591-8c00-e7d4b911447a\",\"type\":\"Album\",\"title\":\"Testalbum\"},{\"artists\":[{\"joinphrase\":\"feat.\",\"name\":\"Wisin\",\"id\":\"6a6eea03-b435-446c-bab1-312b45580aee\"},{\"name\":\"Ozuna\",\"id\":\"f2651b63-a44d-4d1d-ba64-d981d971ecd1\"}],\"id\":\"d7c38654-7cef-4203-bb23-76e0dabf383a\",\"type\":\"Single\",\"title\":\"Escápateconmigo\"},{\"secondarytypes\":[\"Compilation\"],\"artists\":[{\"name\":\"VariousArtists\",\"id\":\"89ad4ac3-39f7-470e-963a-56509c546377\"}],\"id\":\"84a7ef55-7cf4-4c3f-a123-18efe0c5a9b1\",\"type\":\"Album\",\"title\":\"VivaLatina2017\"},{\"artists\":[{\"name\":\"Wisin\",\"id\":\"6a6eea03-b435-446c-bab1-312b45580aee\"}],\"id\":\"20401243-8fe1-4ffc-a3c8-57b770846258\",\"type\":\"Album\",\"title\":\"Victory\"},{\"secondarytypes\":[\"Compilation\"],\"artists\":[{\"name\":\"VariousArtists\",\"id\":\"89ad4ac3-39f7-470e-963a-56509c546377\"}],\"id\":\"9dd3ca00-11ea-46d5-939f-1aa55e9319e2\",\"type\":\"Album\",\"title\":\"Testalbum!\"}],\"id\":\"df46ff0a-c4d6-428e-bc13-b42fe98b0703\",\"title\":\"Testtitle\"}],\"id\":\"113fc39e-c78d-4bf6-8629-e76e8a988cfb\"}],\"status\":\"ok\"}";
    private static final String test2 = "{\"results\":[{\"score\":0.9999407,\"recordings\":[{\"id\":\"df46ff0a-c4d6-428e-bc13-b42fe98b0703\",\"title\":\"nurTitle\"}]},],\"status\":\"ok\"}";
    private static final String test3 = "{\"results\":[{\"score\":0.9999407,\"recordings\":[{\"id\":\"df46ff0a-c4d6-428e-bc13-b42fe98b0703\",\"title\":\"besttitle\",\"artists\":[{\"name\":\"\",\"id\":\"6a6eea03-b435-446c-bab1-312b45580aee\"},{\"nam\":\"TesterStar\",\"id\":\"6a6eea03-b435-446c-bab1-312b45580aee\"},{\"name\":\"bestartist\",\"id\":\"f2651b63-a44d-4d1d-ba64-d981d971ecd1\"}],\"releasegroups\":[{\"secondarytypes\":[\"Compilation\"],\"artists\":[{\"name\":\"VariousArtists\",\"id\":\"89ad4ac3-39f7-470e-963a-56509c546377\"}],\"id\":\"c6f9bb3a-8c2e-436a-9cfa-34987833b236\",\"type\":\"Album\",\"title\":\"bestreleasegroup\"},]}]},{\"score\":0.999407,\"recordings\":[{\"duration\":232,\"artists\":[{\"name\":\"\",\"id\":\"6a6eea03-b435-446c-bab1-312b45580aee\"},{\"joinphrase\":\"feat.\",\"name\":\"TesterStar\",\"id\":\"6a6eea03-b435-446c-bab1-312b45580aee\"},{\"name\":\"TesterBOI\",\"id\":\"f2651b63-a44d-4d1d-ba64-d981d971ecd1\"}],\"releasegroups\":[{\"secondarytypes\":[\"Compilation\"],\"artists\":[{\"name\":\"VariousArtists\",\"id\":\"89ad4ac3-39f7-470e-963a-56509c546377\"}],\"id\":\"c6f9bb3a-8c2e-436a-9cfa-34987833b236\",\"type\":\"Album\",\"title\":\"\"},{\"secondarytypes\":[\"Compilation\"],\"artists\":[{\"name\":\"VariousArtists\",\"id\":\"89ad4ac3-39f7-470e-963a-56509c546377\"}],\"id\":\"ce279c86-d3ea-4591-8c00-e7d4b911447a\",\"type\":\"Album\",\"title\":\"Testalbum\"},{\"artists\":[{\"joinphrase\":\"feat.\",\"name\":\"Wisin\",\"id\":\"6a6eea03-b435-446c-bab1-312b45580aee\"},{\"name\":\"Ozuna\",\"id\":\"f2651b63-a44d-4d1d-ba64-d981d971ecd1\"}],\"id\":\"d7c38654-7cef-4203-bb23-76e0dabf383a\",\"type\":\"Single\",\"title\":\"Escápateconmigo\"},{\"secondarytypes\":[\"Compilation\"],\"artists\":[{\"name\":\"VariousArtists\",\"id\":\"89ad4ac3-39f7-470e-963a-56509c546377\"}],\"id\":\"84a7ef55-7cf4-4c3f-a123-18efe0c5a9b1\",\"type\":\"Album\",\"title\":\"VivaLatina2017\"},{\"artists\":[{\"name\":\"Wisin\",\"id\":\"6a6eea03-b435-446c-bab1-312b45580aee\"}],\"id\":\"20401243-8fe1-4ffc-a3c8-57b770846258\",\"type\":\"Album\",\"title\":\"Victory\"},{\"secondarytypes\":[\"Compilation\"],\"artists\":[{\"name\":\"VariousArtists\",\"id\":\"89ad4ac3-39f7-470e-963a-56509c546377\"}],\"id\":\"9dd3ca00-11ea-46d5-939f-1aa55e9319e2\",\"type\":\"Album\",\"title\":\"\"}],\"id\":\"df46ff0a-c4d6-428e-bc13-b42fe98b0703\",\"title\":\"Testtitle\"}],\"id\":\"113fc39e-c78d-4bf6-8629-e76e8a988cfb\"}],\"status\":\"ok\"}";
    private static final String test4 = "{\"results\":[{\"score\":0.9999407,\"recordings\":[{\"id\":\"df46ff0a-c4d6-428e-bc13-b42fe98b0703\",\"title\":\"besttitle\",\"artists\":[{\"name\":\"\",\"id\":\"6a6eea03-b435-446c-bab1-312b45580aee\"},{\"nam\":\"TesterStar\",\"id\":\"6a6eea03-b435-446c-bab1-312b45580aee\"},{\"name\":\"bestartist\",\"id\":\"f2651b63-a44d-4d1d-ba64-d981d971ecd1\"}],\"releasegroups\":[{\"secondarytypes\":[\"Compilation\"],\"artists\":[{\"name\":\"VariousArtists\",\"id\":\"89ad4ac3-39f7-470e-963a-56509c546377\"}],\"id\":\"c6f9bb3a-8c2e-436a-9cfa-34987833b236\",\"type\":\"Album\",\"title\":\"bestreleasegroup\"},]}]},{\"score\":0.999407,\"recordings\":[{\"duration\":232,\"artists\":[{\"name\":\"\",\"id\":\"6a6eea03-b435-446c-bab1-312b45580aee\"},{\"joinphrase\":\"feat.\",\"name\":\"TesterStar\",\"id\":\"6a6eea03-b435-446c-bab1-312b45580aee\"},{\"name\":\"TesterBOI\",\"id\":\"f2651b63-a44d-4d1d-ba64-d981d971ecd1\"}],\"releasegroups\":[{\"secondarytypes\":[\"Compilation\"],\"artists\":[{\"name\":\"VariousArtists\",\"id\":\"89ad4ac3-39f7-470e-963a-56509c546377\"}],\"id\":\"c6f9bb3a-8c2e-436a-9cfa-34987833b236\",\"type\":\"Album\",\"title\":\"\"},{\"secondarytypes\":[\"Compilation\"],\"artists\":[{\"name\":\"VariousArtists\",\"id\":\"89ad4ac3-39f7-470e-963a-56509c546377\"}],\"id\":\"ce279c86-d3ea-4591-8c00-e7d4b911447a\",\"type\":\"Album\",\"title\":\"Testalbum\"},{\"artists\":[{\"joinphrase\":\"feat.\",\"name\":\"Wisin\",\"id\":\"6a6eea03-b435-446c-bab1-312b45580aee\"},{\"name\":\"Ozuna\",\"id\":\"f2651b63-a44d-4d1d-ba64-d981d971ecd1\"}],\"id\":\"d7c38654-7cef-4203-bb23-76e0dabf383a\",\"type\":\"Single\",\"title\":\"Escápateconmigo\"},{\"secondarytypes\":[\"Compilation\"],\"artists\":[{\"name\":\"VariousArtists\",\"id\":\"89ad4ac3-39f7-470e-963a-56509c546377\"}],\"id\":\"84a7ef55-7cf4-4c3f-a123-18efe0c5a9b1\",\"type\":\"Album\",\"title\":\"VivaLatina2017\"},{\"artists\":[{\"name\":\"Wisin\",\"id\":\"6a6eea03-b435-446c-bab1-312b45580aee\"}],\"id\":\"20401243-8fe1-4ffc-a3c8-57b770846258\",\"type\":\"Album\",\"title\":\"Victory\"},{\"secondarytypes\":[\"Compilation\"],\"artists\":[{\"name\":\"VariousArtists\",\"id\":\"89ad4ac3-39f7-470e-963a-56509c546377\"}],\"id\":\"9dd3ca00-11ea-46d5-939f-1aa55e9319e2\",\"type\":\"Album\",\"title\":\"\"}],\"id\":\"df46ff0a-c4d6-428e-bc13-b42fe98b0703\",\"title\":\"Testtitle\"}],\"id\":\"113fc39e-c78d-4bf6-8629-e76e8a988cfb\"}],\"status\":\"error\"}";
    private static final String test5 = "{\"results\":[],\"status\":\"ok\"}";
    private static JSONObject json;

    @Test
    public void testExtraction() {
        String artist = "TesterStarfeat.TesterBOI";
        String title = "Testtitle";
        String album = "Testalbum";
        try {
            json = new JSONObject(test);
            MetaTemplate metaTemplate = new MetaTemplate();
            MBMetaExtractor mbMetaExtractor = new MBMetaExtractor();
            metaTemplate = metaTemplate.fillTemplate(mbMetaExtractor, json);

            Assert.assertTrue(artist.equals(metaTemplate.getValue(WantedKey.ARTIST)));
            Assert.assertTrue(title.equals(metaTemplate.getValue(WantedKey.TITLE)));
            Assert.assertTrue(album.equals(metaTemplate.getValue(WantedKey.ALBUM)));

            artist = "";
            title = "nurTitle";
            album = "";
            json = new JSONObject(test2);
            MetaTemplate metaTemplate2 = new MetaTemplate();
            metaTemplate2 = metaTemplate.fillTemplate(mbMetaExtractor, json);

            Assert.assertTrue(artist.equals(metaTemplate2.getValue(WantedKey.ARTIST)));
            Assert.assertTrue(title.equals(metaTemplate2.getValue(WantedKey.TITLE)));
            Assert.assertTrue(album.equals(metaTemplate2.getValue(WantedKey.ALBUM)));


            artist = "bestartist";
            title = "besttitle";
            album = "bestreleasegroup";
            json = new JSONObject(test3);
            MetaTemplate metaTemplate3 = new MetaTemplate();
            metaTemplate2 = metaTemplate3.fillTemplate(mbMetaExtractor, json);

            Assert.assertTrue(artist.equals(metaTemplate3.getValue(WantedKey.ARTIST)));
            Assert.assertTrue(title.equals(metaTemplate3.getValue(WantedKey.TITLE)));
            Assert.assertTrue(album.equals(metaTemplate3.getValue(WantedKey.ALBUM)));

        } catch (YamtException.MetaDataException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testExtractionFail() {
        try {
            json = new JSONObject(test4);
            MetaTemplate metaTemplate = new MetaTemplate();
            MBMetaExtractor mbMetaExtractor = new MBMetaExtractor();
            metaTemplate = metaTemplate.fillTemplate(mbMetaExtractor, json);
            Assert.fail("should not have gotten here");
        } catch(Exception e) {
            Assert.assertTrue(e.getMessage().equals("filling of template failed"));
            Assert.assertTrue(e.getCause().getMessage().equals("json invalid"));
        }
    }

    @Test
    public void testExtractionResultEmpty() {
        try {
            String artist = "";
            String title = "";
            String album = "";

            json = new JSONObject(test5);
            MetaTemplate metaTemplate = new MetaTemplate();
            MBMetaExtractor mbMetaExtractor = new MBMetaExtractor();
            metaTemplate = metaTemplate.fillTemplate(mbMetaExtractor, json);

            Assert.assertTrue(artist.equals(metaTemplate.getValue(WantedKey.ARTIST)));
            Assert.assertTrue(title.equals(metaTemplate.getValue(WantedKey.TITLE)));
            Assert.assertTrue(album.equals(metaTemplate.getValue(WantedKey.ALBUM)));

        } catch(Exception e) {
            Assert.fail("empty results need to return empty Strings");
        }
    }

}
