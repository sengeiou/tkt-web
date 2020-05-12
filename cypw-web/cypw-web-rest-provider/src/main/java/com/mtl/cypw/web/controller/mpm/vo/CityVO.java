package com.mtl.cypw.web.controller.mpm.vo;

import lombok.Data;

import java.util.List;

/**
 * @author tang.
 * @date 2019/11/27.
 */
@Data
public class CityVO {

    private String cityCode;

    private String cityName;

    private String cityEname;

    private String provinceCode;

    private List<DistrictVO> districts;
}
