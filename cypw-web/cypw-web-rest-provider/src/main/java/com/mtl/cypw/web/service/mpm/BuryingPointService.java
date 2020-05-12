package com.mtl.cypw.web.service.mpm;

import com.juqitech.request.GenericRequest;
import com.juqitech.request.PaginationParam;
import com.juqitech.request.QueryRequest;
import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TMultiResult;
import com.juqitech.response.TPageResult;
import com.mtl.cypw.api.mpm.client.BuryingPointApiClient;
import com.mtl.cypw.domain.mpm.dto.BuryingPointDTO;
import com.mtl.cypw.domain.mpm.enums.BuryingPointTypeEnum;
import com.mtl.cypw.domain.mpm.param.BuryingPointParam;
import com.mtl.cypw.domain.mpm.param.BuryingPointQueryParam;
import com.mtl.cypw.web.common.Operator;
import com.mtl.cypw.web.controller.mpm.converter.BuryingPointConverter;
import com.mtl.cypw.web.controller.mpm.param.CreateBuryingPointParam;
import com.mtl.cypw.web.controller.mpm.vo.BuryingPointVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tang.
 * @date 2020/1/19.
 */
@Service
@Slf4j
public class BuryingPointService {

    @Resource
    private BuryingPointApiClient client;

    @Resource
    private BuryingPointConverter converter;

    public TPageResult<String> findSearchLogPage(Date createTimeBegin, Date createTimeEnd, PaginationParam pagination) {
        QueryRequest<BuryingPointQueryParam> request = new QueryRequest<>();
        BuryingPointQueryParam query = new BuryingPointQueryParam();
        query.setCreateTimeBegin(createTimeBegin);
        query.setCreateTimeEnd(createTimeEnd);
        query.setMemberId(Operator.getMemberId());
        query.setEnterpriseId(Operator.getEnterpriseId());
        query.setBuryingPointType(BuryingPointTypeEnum.MEMBER_SEARCH_LOG);
        request.setParam(query);
        request.setPagination(pagination);
        TPageResult<BuryingPointDTO> result = client.findBuryingPointPageList(request);
        if (result.success()) {
            List<String> searchKeys = new ArrayList<>();
            result.getData().forEach(n -> searchKeys.add(n.getBuryingPointContent()));
            List newList = searchKeys.stream().distinct().collect(Collectors.toList());
            return ResultBuilder.succTPage(newList, result.getPagination());
        } else {
            return ResultBuilder.failTPage(result.getStatusCode(), result.getComments());
        }
    }

    public TMultiResult<BuryingPointVO> findBuryingPointList(Date createTimeBegin, Date createTimeEnd) {
        QueryRequest<BuryingPointQueryParam> request = new QueryRequest<>();
        BuryingPointQueryParam query = new BuryingPointQueryParam();
        query.setCreateTimeBegin(createTimeBegin);
        query.setCreateTimeEnd(createTimeEnd);
        request.setParam(query);
        TMultiResult<BuryingPointDTO> result = client.findBuryingPointList(request);
        if (result.success()) {
            return converter.toVo(result);
        } else {
            return ResultBuilder.failTMulti(result.getStatusCode(), result.getComments());
        }
    }


    public void addBuryingPointInfo(CreateBuryingPointParam param) {
        if (param == null) {
            return;
        }
        BuryingPointParam buryingPointParam = converter.toBuryingPointParam(param);
        buryingPointParam.setMemberId(Operator.getMemberId());
        buryingPointParam.setEnterpriseId(Operator.getEnterpriseId());
        GenericRequest<BuryingPointParam> request = new GenericRequest();
        request.setParam(buryingPointParam);
        client.addBuryingPoint(request);
    }

    public void deleteBuryingPointInfo(String type) {
        if (type == null || BuryingPointTypeEnum.getObject(type) == null) {
            return;
        }
        BuryingPointParam buryingPointParam = new BuryingPointParam();
        buryingPointParam.setBuryingPointType(BuryingPointTypeEnum.getObject(type));
        buryingPointParam.setMemberId(Operator.getMemberId());
        buryingPointParam.setEnterpriseId(Operator.getEnterpriseId());
        GenericRequest<BuryingPointParam> request = new GenericRequest();
        request.setParam(buryingPointParam);
        client.deleteBuryingPoint(request);
    }
}
