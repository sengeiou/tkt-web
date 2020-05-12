package com.mtl.cypw.web.controller.mpm;

import com.juqitech.response.TMultiResult;
import com.mtl.cypw.web.controller.WebGenericController;
import com.mtl.cypw.web.controller.mpm.vo.ProvinceVO;
import com.mtl.cypw.web.service.mpm.ProvinceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author tang.
 * @date 2019/11/27.
 */
@RestController
@Slf4j
@Api(tags = {"省.市.区"})
@CrossOrigin
public class ProvinceController extends WebGenericController {

    @Resource
    ProvinceService provinceService;

    @RequestMapping(value = "/pub/v1/provinces", method = RequestMethod.GET)
    @ApiOperation(value = "查询省份信息", httpMethod = "GET", response = ProvinceVO.class, notes = "查询省份信息")
    public TMultiResult<ProvinceVO> provinces(@ApiParam(name = "searchCity", value = "是否查询城市") @RequestParam(defaultValue = "false") Boolean searchCity,
                                              @ApiParam(name = "searchDistrict", value = "是否查询区") @RequestParam(defaultValue = "false") Boolean searchDistrict) {
        return provinceService.provinces(searchCity, searchDistrict);
    }
}
