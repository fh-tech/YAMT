package org.itp1.yamtlib;

import org.junit.Assert;
import org.junit.Test;

public class testEscape {
    @Test
    public void testEscape(){
        String t1 = "a|a";
        Assert.assertEquals("aa", t1.replaceAll("[\"|*?<>]", ""));
        t1 = "a\"a";
        Assert.assertEquals("aa", t1.replaceAll("[\"|*?<>]", ""));
        t1 = "a<a";
        Assert.assertEquals("aa", t1.replaceAll("[\"|*?<>]", ""));
        t1 = "a>a";
        Assert.assertEquals("aa", t1.replaceAll("[\"|*?<>]", ""));
        t1 = "a*a";
        Assert.assertEquals("aa", t1.replaceAll("[\"|*?<>]", ""));
        t1 = "a?a";
        Assert.assertEquals("aa", t1.replaceAll("[\"|*?<>]", ""));
        t1 = "a|a";
        Assert.assertEquals("aa", t1.replaceAll("[\"|*?<>]", ""));

    }
}
