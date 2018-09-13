package org.leesia.datasource.controller;

import org.leesia.datasource.entity.Nation;
import org.leesia.datasource.service.NationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @Auther: leesia
 * @Date: 2018/8/10 18:43
 * @Description:
 */
@RestController
@RequestMapping(value = "nation")
public class NationController {

    private static final Logger logger = LoggerFactory.getLogger(NationController.class);

    @Autowired
    private NationService nationService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(HttpServletRequest request) {
        logger.info("查询全部民族");
        return nationService.get(new HashMap<>());
    }

    @RequestMapping(value = "getByName", method = RequestMethod.GET)
    @ResponseBody
    public Object getByName(HttpServletRequest request, @RequestParam String nationName) {
        logger.info("查询民族：{}", nationName);
        return nationService.getByName(nationName);
    }
}
