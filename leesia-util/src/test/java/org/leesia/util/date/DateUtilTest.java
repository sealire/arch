package org.leesia.util.date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.time.LocalDateTime;

/**
 * @ClassName: LDateUtilTest
 * @Description: LDateUtil Tester
 * @author: leesia
 * @date: 2019/11/23 18:01
 */
public class DateUtilTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: format(Date date, String pattern)
     */
    @Test
    public void testFormat() throws Exception {
        LocalDateTime date = LocalDateTime.of(2019, 11, 23, 18, 01, 37);
        String format = DateUtil.format(date, "");
        Assert.assertEquals("2019-11-23 18:01:37", format);

        format = DateUtil.format(date, "yyyy-MM-dd");
        Assert.assertEquals("2019-11-23", format);
    }
} 
