package org.leesia.util.regex;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @ClassName: RegexUtilTest
 * @Description: TODO
 * @author: leesia
 * @date: 2019/12/18 17:21
 */
public class RegexUtilTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testIsChineseChar() throws Exception {
        Assert.assertFalse(RegexUtil.isChineseChar(""));
        Assert.assertFalse(RegexUtil.isChineseChar("1"));
        Assert.assertFalse(RegexUtil.isChineseChar("a"));
        Assert.assertFalse(RegexUtil.isChineseChar("#"));
//        Assert.assertTrue(RegexUtil.isChineseChar("中"));
        Assert.assertTrue(RegexUtil.isChineseChar("中国"));
    }
}
