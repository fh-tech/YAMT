package org.itp1.yamtlib;

import org.itp1.yamtlib.music.TestMeta;
import org.junit.Test;

import java.util.Arrays;

import static org.itp1.yamtlib.util.YamtUtils.sneakyThrow;

public class TestYamt extends TestMeta{

    @Test
    public void testTmpFiles() throws Exception{

        Arrays.stream(testFiles)
                .map(sneakyThrow(Yamt::copyToTmp))
                .forEach(f -> System.out.println(f.getAbsolutePath()));
    }

}
