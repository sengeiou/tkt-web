package com.mtl.cypw.web.service.ticket;

import com.juqitech.request.GenericRequest;
import com.juqitech.request.PaginationParam;
import com.juqitech.request.QueryRequest;
import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TMultiResult;
import com.juqitech.response.TPageResult;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.api.ticket.client.CheckApiClient;
import com.mtl.cypw.common.enums.ErrorCode;
import com.mtl.cypw.common.utils.JsonUtils;
import com.mtl.cypw.domain.ticket.dto.TicketPaperDTO;
import com.mtl.cypw.domain.ticket.dto.TicketPaperResultDTO;
import com.mtl.cypw.domain.ticket.param.CheckInDeviceParam;
import com.mtl.cypw.domain.ticket.param.CheckInQueryParam;
import com.mtl.cypw.domain.ticket.param.CheckInTicketParam;
import com.mtl.cypw.web.common.Operator;
import com.mtl.cypw.web.controller.ticket.converter.CheckPaperConverter;
import com.mtl.cypw.web.controller.ticket.param.CheckConsumeParam;
import com.mtl.cypw.web.controller.ticket.vo.TicketPaperVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Johnathon.Yuan
 * @date 2019-12-02 10:22
 */
@Slf4j
@Service
public class CheckInService {

    @Autowired
    private CheckApiClient checkApiClient;

    @Autowired
    private CheckPaperConverter checkPaperConverter;

    public TSingleResult<TicketPaperVO> queryByCode(String code) {
        GenericRequest genericRequest = new GenericRequest();
        CheckInQueryParam checkInQueryParam = new CheckInQueryParam();
        checkInQueryParam.setCheckCode(code);
        checkInQueryParam.setCheckUserId(Operator.getUserId());
        checkInQueryParam.setEnterpriseId(Operator.getEnterpriseId());
        genericRequest.setParam(checkInQueryParam);
        TSingleResult<TicketPaperResultDTO> result = checkApiClient.findTicketByCode(genericRequest);
        if (!result.success()) {
            return ResultBuilder.failTSingle(result.getStatusCode(), result.getComments());
        }
        TicketPaperResultDTO paperResult = result.getData();
        if (paperResult == null || CollectionUtils.isEmpty(paperResult.getTicketPapers())) {
            return ResultBuilder.failTSingle(ErrorCode.ERROR_TICKET_CHECK_CODE_FAILED.getCode(), ErrorCode.ERROR_TICKET_CHECK_CODE_FAILED.getMsg());
        }
        TicketPaperVO vo = checkPaperConverter.convert(paperResult.getTicketPapers().get(0));
        return ResultBuilder.succTSingle(vo);
    }

    public TMultiResult<TicketPaperVO> queryByMobileNo(String mobile) {
        GenericRequest genericRequest = new GenericRequest();
        CheckInQueryParam checkInQueryParam = new CheckInQueryParam();
        checkInQueryParam.setMobileNo(mobile);
        checkInQueryParam.setCheckUserId(Operator.getUserId());
        checkInQueryParam.setEnterpriseId(Operator.getEnterpriseId());
        genericRequest.setParam(checkInQueryParam);
        TSingleResult<TicketPaperResultDTO> result = checkApiClient.findTicketByMobile(genericRequest);
        if (!result.success()) {
            return ResultBuilder.failTMulti(result.getStatusCode(), result.getComments());
        }
        TicketPaperResultDTO paperResult = result.getData();
        if (paperResult == null || CollectionUtils.isEmpty(paperResult.getTicketPapers())) {
            return ResultBuilder.failTMulti(ErrorCode.ERROR_TICKET_CHECK_CODE_FAILED.getCode(), ErrorCode.ERROR_TICKET_CHECK_CODE_FAILED.getMsg());
        }
        List<TicketPaperVO> paperVOS = checkPaperConverter.batchConvert(paperResult.getTicketPapers());
        return ResultBuilder.succTMulti(paperVOS);
    }

    public TSingleResult<TicketPaperVO> consume(CheckConsumeParam param) {
        GenericRequest genericRequest = new GenericRequest();
        CheckInTicketParam checkInTicketParam = new CheckInTicketParam();
        checkInTicketParam.setCheckChannel(param.getCheckChannel());
        checkInTicketParam.setCheckMethod(param.getCheckMethod());
        checkInTicketParam.setCheckCode(param.getCode());
        checkInTicketParam.setCheckUserId(Operator.getUserId());
        checkInTicketParam.setEnterpriseId(Operator.getEnterpriseId());
        CheckConsumeParam.CheckDeviceInfo deviceInfo = param.getDeviceInfo();
        if (deviceInfo != null) {
            CheckInDeviceParam deviceParam = new CheckInDeviceParam();
            deviceParam.setDeviceType(deviceInfo.getDeviceType());
            deviceParam.setDeviceVersion(deviceInfo.getDeviceVersion());
            deviceParam.setDeviceUniqueCode(deviceInfo.getDeviceUniqueCode());
            deviceParam.setIdCard(deviceInfo.getIdCard());
            deviceParam.setIdCardName(deviceInfo.getIdCardName());
            deviceParam.setBindingIdCard(deviceInfo.getBindingIdCard());
            deviceParam.setBindingIdCardName(deviceInfo.getBindingIdCardName());
            checkInTicketParam.setCheckInDeviceParam(deviceParam);
        }
        genericRequest.setParam(checkInTicketParam);
        log.info("check consume, params:{}", JsonUtils.toJson(genericRequest));
        TSingleResult<TicketPaperResultDTO> result = checkApiClient.consume(genericRequest);
        if (!result.success()) {
            return ResultBuilder.failTSingle(result.getStatusCode(), result.getComments());
        }
        TicketPaperResultDTO paperResult = result.getData();
        if (paperResult == null || CollectionUtils.isEmpty(paperResult.getTicketPapers())) {
            return ResultBuilder.failTSingle(ErrorCode.ERROR_TICKET_CHECK_CODE_FAILED.getCode(), ErrorCode.ERROR_TICKET_CHECK_CODE_FAILED.getMsg());
        }
        TicketPaperVO vo = checkPaperConverter.convert(paperResult.getTicketPapers().get(0));
        return ResultBuilder.succTSingle(vo);
    }

    public TPageResult<TicketPaperVO> pageQuery(Integer pageNo, Integer pageSize, Date checkDate) {
        QueryRequest request = QueryRequest.build();
        PaginationParam pagination = new PaginationParam();
        pagination.setOffset(pageNo);
        pagination.setLength(pageSize);
        request.setPagination(pagination);

        CheckInQueryParam checkInQueryParam = new CheckInQueryParam();
        checkInQueryParam.setCheckDate(checkDate);
        checkInQueryParam.setCheckUserId(Operator.getUserId());
        checkInQueryParam.setEnterpriseId(Operator.getEnterpriseId());
        request.setParam(checkInQueryParam);

        log.info("check record pageQuery, params:{}", JsonUtils.toJson(request));
        TPageResult<TicketPaperDTO> result = checkApiClient.recordQuery(request);
        if (CollectionUtils.isEmpty(result.getData())) {
            return ResultBuilder.succTPage(Collections.emptyList(), result.getPagination());
        }
        result.getPagination().setOffset(pageNo);
        result.getPagination().setLength(pageSize);
        return ResultBuilder.succTPage(checkPaperConverter.batchConvert(result.getData()), result.getPagination());
    }

    public TSingleResult<Integer> countQuery(Date checkDate) {
        QueryRequest request = QueryRequest.build();
        CheckInQueryParam checkInQueryParam = new CheckInQueryParam();
        checkInQueryParam.setCheckDate(checkDate);
        checkInQueryParam.setCheckUserId(Operator.getUserId());
        checkInQueryParam.setEnterpriseId(Operator.getEnterpriseId());
        request.setParam(checkInQueryParam);
        log.info("check record count, params:{}", JsonUtils.toJson(request));
        return checkApiClient.countQuery(request);

    }

}
