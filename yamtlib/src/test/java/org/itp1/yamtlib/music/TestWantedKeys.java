package org.itp1.yamtlib.music;

import org.jaudiotagger.tag.FieldKey;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

public class TestWantedKeys {

    private static HashSet<FieldKey> availableKeys;
    @Before
    public void makeHashSet() {
        availableKeys = new HashSet<FieldKey>();
        for (FieldKey fk : FieldKey.values()) {
            availableKeys.add(fk);
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
        for(WantedKeys key : WantedKeys.values()) {
            Assert.assertNotNull(key.getFk());
            Assert.assertTrue(availableKeys.contains(key.getFk()));
        }
    }

}
