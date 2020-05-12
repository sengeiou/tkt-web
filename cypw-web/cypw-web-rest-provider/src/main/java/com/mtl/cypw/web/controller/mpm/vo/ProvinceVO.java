package com.mtl.cypw.web.controller.mpm.vo;

import lombok.Data;

import java.util.List;

/**
 * @author tang.
 * @date 2019/11/27.
 */
@Data
public class ProvinceVO {
    private String provinceCode;

    private String provinceName;

    private String provinceEname;

    private List<CityVO> citys;

}
