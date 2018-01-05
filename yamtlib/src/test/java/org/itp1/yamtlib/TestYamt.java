package org.itp1.yamtlib;

import org.itp1.yamtlib.music.TestMeta;
import org.itp1.yamtlib.util.YamtUtils;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.itp1.yamtlib.Yamt.moveToTmp;
import static org.itp1.yamtlib.util.YamtUtils.sneakyThrow;

public class TestYamt extends TestMeta{

    @Test
    public void testTmpFiles() throws Exception{

        Arrays.stream(testFiles)
                .map(sneakyThrow(Yamt::moveToTmp))
                .forEach(f -> System.out.println(f.getAbsolutePath()));
    }

}
