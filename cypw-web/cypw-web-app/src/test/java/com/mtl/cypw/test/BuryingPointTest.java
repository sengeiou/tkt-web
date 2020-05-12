package com.mtl.cypw.test;

import com.alibaba.fastjson.JSONObject;
import com.juqitech.request.PaginationParam;
import com.juqitech.response.TPageResult;
import com.mtl.cypw.domain.mpm.enums.BuryingPointTypeEnum;
import com.mtl.cypw.web.common.Operator;
import com.mtl.cypw.web.controller.mpm.param.CreateBuryingPointParam;
import com.mtl.cypw.web.service.mpm.BuryingPointService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author tang.
 * @date 2020/3/6.
 */
@Slf4j
public class BuryingPointTest extends BaseTest {

    @Resource
    private BuryingPointService buryingPointService;

    @Test
    public void addBuryingPoint() {
        CreateBuryingPointParam param = new CreateBuryingPointParam();
        param.setType(BuryingPointTypeEnum.MEMBER_SEARCH_LOG.getName());
        param.setContent("林俊杰");
        param.setSourcePlatform("WEB");
        Operator.setMemberId(1);
        Operator.setEnterpriseId(1);
        buryingPointService.addBuryingPointInfo(param);
    }

    @Test
    public void getSearchLog() {
        PaginationParam paginationParam = new PaginationParam();
        paginationParam.setOffset(1);
        paginationParam.setLength(10);
        Operator.setMemberId(1);
        Operator.setEnterpriseId(1);
        TPageResult<String> result = buryingPointService.findSearchLogPage(null, null, paginationParam);
        log.info("result:{}", JSONObject.toJSONString(result.getData()));
    }
}
