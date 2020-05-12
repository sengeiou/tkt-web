package com.mtl.cypw.web.service.mpm;

import com.alibaba.fastjson.JSONObject;
import com.juqitech.response.TMultiResult;
import com.mtl.cypw.api.mpm.client.CityApiClient;
import com.mtl.cypw.api.mpm.client.DistrictApiClient;
import com.mtl.cypw.api.mpm.client.ProvinceApiClient;
import com.mtl.cypw.web.controller.mpm.converter.CityConverter;
import com.mtl.cypw.web.controller.mpm.converter.DistrictConverter;
import com.mtl.cypw.web.controller.mpm.converter.ProvinceConverter;
import com.mtl.cypw.web.controller.mpm.vo.CityVO;
import com.mtl.cypw.web.controller.mpm.vo.DistrictVO;
import com.mtl.cypw.web.controller.mpm.vo.ProvinceVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tang.
 * @date 2019/11/27.
 */
@Service
@Slf4j
public class ProvinceService {

    @Autowired
    ProvinceApiClient provinceApiClient;

    @Autowired
    ProvinceConverter provinceConverter;

    @Autowired
    CityApiClient cityApiClient;

    @Autowired
    CityConverter cityConverter;

    @Autowired
    DistrictApiClient districtApiClient;

    @Autowired
    DistrictConverter districtConverter;

    public TMultiResult<ProvinceVO> provinces(Boolean searchCity, Boolean searchDistrict) {
        log.debug("查询所有省份");
        TMultiResult<ProvinceVO> result = provinceConverter.toVo(provinceApiClient.provinces());
        if (searchCity) {
            addCity(result, searchDistrict);
        }
        return result;
    }

    private TMultiResult<ProvinceVO> addCity(TMultiResult<ProvinceVO> provinceResult, Boolean searchDistrict) {
        log.debug("给所有的省添加对应的城市列表");
        List<ProvinceVO> provinces = provinceResult.getData();
        TMultiResult<CityVO> cityResult = cityConverter.toVo(cityApiClient.citys());
        List<CityVO> cityList = cityResult.getData();

        if(searchDistrict){
            addDistrict(cityList);
        }

        provinces.forEach(p -> {
            List<CityVO> province_citys = new ArrayList<>();
            cityList.forEach(c -> {
                if (p.getProvinceCode().equals(c.getProvinceCode())) {
                    province_citys.add(c);
                }
            });
            p.setCitys(province_citys);
        });
        return provinceResult;
    }

    private List<CityVO> addDistrict(List<CityVO> cityList) {
        log.debug("给所有的城市添加对应的区域列表");
        TMultiResult<DistrictVO> districtResult = districtConverter.toVo(districtApiClient.districts());
        List<DistrictVO> districts = districtResult.getData();
        cityList.forEach(c -> {
            List<DistrictVO> city_districts = new ArrayList<>();
            districts.forEach(d -> {
                if (c.getCityCode().equals(d.getCityCode())) {
                    city_districts.add(d);
                }
            });
            c.setDistricts(city_districts);
        });
        return cityList;
    }

}
