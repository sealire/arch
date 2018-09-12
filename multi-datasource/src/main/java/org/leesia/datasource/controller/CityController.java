package org.leesia.datasource.controller;

import org.leesia.datasource.entity.City;
import org.leesia.datasource.service.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: leesia
 * @Date: 2018/8/8 13:56
 * @Description:
 */
@RestController
@RequestMapping(value = "city")
public class CityController {

    private static final Logger logger = LoggerFactory.getLogger(CityController.class);

    @Autowired
    private CityService cityService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(HttpServletRequest request) {
        logger.info("查询全部城市");
        return cityService.get(new HashMap<>());
    }

    @RequestMapping(value = "provinceCities", method = RequestMethod.GET)
    @ResponseBody
    public Object provinceCities(HttpServletRequest request, @RequestParam String provinceName) {
        logger.info("查询省份下的全部城市：{}", provinceName);
        Map<String, Object> map = new HashMap<>();
        map.put("provinceName", provinceName);
        return cityService.get(map);
    }

    @RequestMapping(value = "getByName", method = RequestMethod.GET)
    @ResponseBody
    public Object getByName(HttpServletRequest request, @RequestParam String cityName) {
        logger.info("查询城市：{}", cityName);
        return cityService.getByName(cityName);
    }
}
