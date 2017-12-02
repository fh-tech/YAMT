package org.itp1.yamtlib.music;

import org.itp1.yamtlib.music.wantedKeys;
import org.jaudiotagger.tag.FieldKey;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

public class TestWantedKeys {

    private static HashSet<String> availableKeys;
    @Before
    public void makeHashSet() {
        availableKeys = new HashSet<String>();
        for (FieldKey fk : FieldKey.values()) {
            availableKeys.add(fk.name());
        }
    }

    @Test
    public void allFieldKeys() {
        for(FieldKey fkey : FieldKey.values()) {
            System.out.println(fkey.name());
        }
    }

    @Test
    public void testWantedKeys() {
        for(wantedKeys key : wantedKeys.values()) {
            Assert.assertTrue(availableKeys.contains(key.name()));
        }
    }

}
