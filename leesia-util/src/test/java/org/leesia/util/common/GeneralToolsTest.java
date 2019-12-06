package org.leesia.util.common;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.leesia.util.list.LListUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @ClassName: GeneralToolsTest
 * @Description: GeneralTools Tester
 * @author: leesia
 * @date: 2019/11/23 18:09
 */
public class GeneralToolsTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: isEmpty(Object o)
     */
    @Test
    public void testIsEmpty() throws Exception {
        Assert.assertEquals(true, GeneralTools.isEmpty(null));
        Assert.assertEquals(true, GeneralTools.isEmpty(""));
        Assert.assertEquals(true, GeneralTools.isEmpty(new String[]{}));
        Assert.assertEquals(true, GeneralTools.isEmpty(new ArrayList<String>()));
        Assert.assertEquals(true, GeneralTools.isEmpty(new HashMap<String, String>()));

        Assert.assertEquals(false, GeneralTools.isEmpty(new Integer(1)));
        Assert.assertEquals(false, GeneralTools.isEmpty("1"));
        Assert.assertEquals(false, GeneralTools.isEmpty(new String[]{"1"}));
        Assert.assertEquals(false, GeneralTools.isEmpty(LListUtil.asList(1)));
        Assert.assertEquals(false, GeneralTools.isEmpty(new HashMap<String, String>() {{
            put("name", "pumpkin");
        }}));
    }

    /**
     * Method: isNotEmpty(Object o)
     */
    @Test
    public void testIsNotEmpty() throws Exception {
        Assert.assertEquals(false, GeneralTools.isNotEmpty(null));
        Assert.assertEquals(false, GeneralTools.isNotEmpty(""));
        Assert.assertEquals(false, GeneralTools.isNotEmpty(new ArrayList<String>()));
        Assert.assertEquals(false, GeneralTools.isNotEmpty(new HashMap<String, String>()));

        Assert.assertEquals(true, GeneralTools.isNotEmpty(new Integer(1)));
        Assert.assertEquals(true, GeneralTools.isNotEmpty("1"));
        Assert.assertEquals(true, GeneralTools.isNotEmpty(LListUtil.asList(1)));
        Assert.assertEquals(true, GeneralTools.isNotEmpty(new HashMap<String, String>() {{
            put("name", "pumpkin");
        }}));
    }
}
