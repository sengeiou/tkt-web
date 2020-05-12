package com.mtl.cypw.web.controller.member;

import com.juqitech.request.GenericRequest;
import com.juqitech.request.IdRequest;
import com.juqitech.request.QueryRequest;
import com.juqitech.response.TMultiResult;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.domain.member.param.MemberAddressParam;
import com.mtl.cypw.domain.member.param.MemberQueryParam;
import com.mtl.cypw.web.common.Operator;
import com.mtl.cypw.web.controller.WebGenericController;
import com.mtl.cypw.web.controller.member.vo.MemberAddressVO;
import com.mtl.cypw.web.service.member.MemberAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author tang.
 * @date 2019/11/22.
 */
@RestController
@Slf4j
@Api(value = "票星球-会员地址", tags = {"票星球-会员地址接口"})
@CrossOrigin
public class MemberAddressController extends WebGenericController {

    @Resource
    MemberAddressService memberAddressService;

    @RequestMapping(value = "/buyer/v1/member/address", method = RequestMethod.GET)
    @ApiOperation(value = "查询会员地址列表", httpMethod = "GET", response = MemberAddressVO.class, notes = "查询会员地址列表")
    public TMultiResult<MemberAddressVO> searchMemberAddressList() {
        MemberQueryParam query = new MemberQueryParam();
        query.setMemberId(Operator.getMemberId());

        QueryRequest<MemberQueryParam> request = QueryRequest.build();
        request.setParam(query);
        return memberAddressService.searchMemberAddressList(request);
    }

    @RequestMapping(value = "/buyer/v1/member/address/{addressId}", method = RequestMethod.GET)
    @ApiOperation(value = "查询会员地址", httpMethod = "GET", response = MemberAddressVO.class, notes = "查询会员地址")
    public TSingleResult<MemberAddressVO> getMemberAddress(@ApiParam(required = true, name = "param", value = "地址id") @PathVariable("addressId") Integer addressId) {
        IdRequest request = new IdRequest();
        request.setId(addressId.toString());
        return memberAddressService.getMemberAddress(request);
    }

    @RequestMapping(value = "/buyer/v1/member/address/create", method = RequestMethod.POST)
    @ApiOperation(value = "新增会员地址", httpMethod = "POST", response = Boolean.class, notes = "新增会员地址")
    public TSingleResult<Boolean> createMemberAddress(@ApiParam(required = true, name = "param", value = "会员地址信息") @RequestBody MemberAddressParam param) {
        param.setMemberId(Operator.getMemberId());
        GenericRequest<MemberAddressParam> request = new GenericRequest();
        request.setParam(param);
        return memberAddressService.createMemberAddress(request);
    }

    @RequestMapping(value = "/buyer/v1/member/address/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改会员地址", httpMethod = "POST", response = Boolean.class, notes = "修改会员地址")
    public TSingleResult<Boolean> updateMemberAddress(@ApiParam(required = true, name = "param", value = "会员地址信息") @RequestBody MemberAddressParam param) {
        GenericRequest<MemberAddressParam> request = new GenericRequest();
        request.setParam(param);
        return memberAddressService.updateMemberAddress(request);
    }

    @RequestMapping(value = "/buyer/v1/member/address/delete/{addressId}", method = RequestMethod.POST)
    @ApiOperation(value = "删除会员地址", httpMethod = "POST", response = Boolean.class, notes = "删除会员地址")
    public TSingleResult<Boolean> deleteMemberAddress(@ApiParam(required = true, name = "addressId", value = "地址id") @PathVariable("addressId") Integer addressId) {
        IdRequest request = new IdRequest();
        request.setId(addressId.toString());
        return memberAddressService.deleteMemberAddress(request);
    }


    @RequestMapping(value = "/buyer/v1/member/address/default/{addressId}", method = RequestMethod.POST)
    @ApiOperation(value = "设置默认地址", httpMethod = "POST", response = Boolean.class, notes = "设置默认地址")
    public TSingleResult<Boolean> updateMemberDefaultAddress(@ApiParam(required = true, name = "addressId", value = "地址id") @PathVariable("addressId") Integer addressId) {
        IdRequest request = new IdRequest();
        request.setId(addressId.toString());
        return memberAddressService.updateMemberDefaultAddress(request);
    }


}
