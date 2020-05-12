package com.mtl.cypw.web.service.member;

import com.alibaba.fastjson.JSONObject;
import com.juqitech.request.GenericRequest;
import com.juqitech.request.IdRequest;
import com.juqitech.request.QueryRequest;
import com.juqitech.response.TMultiResult;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.api.member.client.MemberAddressApiClient;
import com.mtl.cypw.api.mpm.client.CityApiClient;
import com.mtl.cypw.api.mpm.client.DistrictApiClient;
import com.mtl.cypw.api.mpm.client.ProvinceApiClient;
import com.mtl.cypw.domain.member.dto.MemberAddressDTO;
import com.mtl.cypw.domain.member.param.MemberAddressParam;
import com.mtl.cypw.domain.member.param.MemberQueryParam;
import com.mtl.cypw.domain.mpm.dto.CityDTO;
import com.mtl.cypw.domain.mpm.dto.DistrictDTO;
import com.mtl.cypw.domain.mpm.dto.ProvinceDTO;
import com.mtl.cypw.web.controller.member.converter.MemberAddressConverter;
import com.mtl.cypw.web.controller.member.vo.MemberAddressVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tang.
 * @date 2019/11/22.
 */
@Service
@Slf4j
public class MemberAddressService {

    @Autowired
    MemberAddressApiClient memberAddressApiClient;

    @Autowired
    MemberAddressConverter memberAddressConverter;

    @Autowired
    ProvinceApiClient provinceApiClient;

    @Autowired
    CityApiClient cityApiClient;

    @Autowired
    DistrictApiClient districtApiClient;

    public TMultiResult<MemberAddressVO> searchMemberAddressList(QueryRequest<MemberQueryParam> request) {
        log.debug("查询会员地址列表，request：{}", JSONObject.toJSONString(request));
        TMultiResult<MemberAddressDTO> pageDto = memberAddressApiClient.searchMemberAddressList(request);
        log.debug("查询结果，response：{}", JSONObject.toJSONString(pageDto));
        TMultiResult<MemberAddressVO> result = memberAddressConverter.toVo(pageDto);
        return result;
    }

    public TSingleResult<Boolean> createMemberAddress(GenericRequest<MemberAddressParam> request) {
        log.debug("添加会员地址，request：{}", JSONObject.toJSONString(request));
        addAddressName(request.getParam());
        return memberAddressApiClient.createMemberAddress(request);
    }

    public TSingleResult<Boolean> updateMemberAddress(GenericRequest<MemberAddressParam> request) {
        addAddressName(request.getParam());
        log.debug("修改会员地址，request：{}", JSONObject.toJSONString(request));
        return memberAddressApiClient.updateMemberAddress(request);
    }

    public TSingleResult<Boolean> deleteMemberAddress(IdRequest request) {
        log.debug("删除会员地址，request：{}", JSONObject.toJSONString(request));
        return memberAddressApiClient.deleteMemberAddress(request);
    }

    public TSingleResult<MemberAddressVO> getMemberAddress(IdRequest request) {
        log.debug("查询会员地址，request：{}", JSONObject.toJSONString(request));
        TSingleResult<MemberAddressVO> result = memberAddressConverter.toVo(memberAddressApiClient.getMemberAddress(request));
        return result;
    }

    public TSingleResult<Boolean> updateMemberDefaultAddress(IdRequest request) {
        log.debug("设置会员默认地址，request：{}", JSONObject.toJSONString(request));
        return memberAddressApiClient.updateDefaultAddress(request);
    }

    private Map<String, String> getProvinceMap() {
        TMultiResult<ProvinceDTO> dtos = provinceApiClient.provinces();
        List<ProvinceDTO> provinces = dtos.getData();
        Map<String, String> map = new HashMap(provinces.size());
        provinces.forEach(n -> map.put(n.getProvinceCode(), n.getProvinceName()));
        return map;
    }

    private Map<String, String> getCityMap() {
        TMultiResult<CityDTO> dtos = cityApiClient.citys();
        List<CityDTO> citys = dtos.getData();
        Map<String, String> map = new HashMap(citys.size());
        citys.forEach(n -> map.put(n.getCityCode(), n.getCityName()));
        return map;
    }

    private Map<String, String> getDistrictMap() {
        TMultiResult<DistrictDTO> dtos = districtApiClient.districts();
        List<DistrictDTO> districts = dtos.getData();
        Map<String, String> map = new HashMap(districts.size());
        districts.forEach(n -> map.put(n.getDistrictCode(), n.getDistrictName()));
        return map;
    }

    private void addAddressName(MemberAddressParam param) {
        if (param == null) {
            return;
        }
        Map<String, String> provinceMap = getProvinceMap();
        Map<String, String> cityMap = getCityMap();
        Map<String, String> districtMap = getDistrictMap();
        if (param.getProvinceCode() != null) {
            param.setProvinceName(provinceMap.get(param.getProvinceCode() + ""));
        }
        if (param.getCityCode() != null) {
            param.setCityName(cityMap.get(param.getCityCode() + ""));
        }
        if (param.getDistrictCode() != null) {
            param.setDistrictName(districtMap.get(param.getDistrictCode() + ""));
        }

    }
}
