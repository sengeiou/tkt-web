package com.mtl.cypw.web.service.show;

import com.juqitech.request.IdRequest;
import com.juqitech.request.PaginationParam;
import com.juqitech.request.QueryRequest;
import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TMultiResult;
import com.juqitech.response.TPageResult;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.api.show.client.ActivityApiClient;
import com.mtl.cypw.common.enums.ErrorCode;
import com.mtl.cypw.domain.show.dto.ActivityDTO;
import com.mtl.cypw.domain.show.query.ActivityQuery;
import com.mtl.cypw.web.common.Operator;
import com.mtl.cypw.web.controller.show.converter.ActivityConverter;
import com.mtl.cypw.web.controller.show.enums.TimeTypeEnum;
import com.mtl.cypw.web.controller.show.vo.ActivityVO;
import org.assertj.core.util.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author tang.
 * @date 2020/1/6.
 */
@Service
public class ActivityService {

    @Resource
    ActivityApiClient client;

    @Resource
    ActivityConverter converter;

    public TSingleResult<ActivityVO> getActivity(Integer activityId) {
        if (activityId == null) {
            return ResultBuilder.failTSingle(ErrorCode.ERROR_COMMON_PARAMETER.getCode(), ErrorCode.ERROR_COMMON_PARAMETER.getMsg());
        }
        TSingleResult<ActivityDTO> result = client.getActivity(new IdRequest(activityId.toString()));
        if (result.success()) {
            return converter.toVo(result);
        } else {
            return ResultBuilder.failTSingle(result.getStatusCode(), result.getComments());
        }
    }

    public TMultiResult<ActivityVO> searchActivity(TimeTypeEnum timeTypeEnum) {
        TMultiResult<ActivityDTO> result = client.searchActivity(buildActivityRequest(timeTypeEnum, null));
        if (result.success()) {
            return converter.toVo(result);
        } else {
            return ResultBuilder.failTMulti(result.getStatusCode(), result.getComments());
        }
    }

    public TPageResult<ActivityVO> searchActivityPage(TimeTypeEnum timeTypeEnum, PaginationParam pagination) {
        TPageResult<ActivityDTO> result = client.searchActivityPage(buildActivityRequest(timeTypeEnum, pagination));
        if (result.success()) {
            return converter.toVo(result);
        } else {
            return ResultBuilder.failTPage(result.getStatusCode(), result.getComments());
        }
    }

    private QueryRequest<ActivityQuery> buildActivityRequest(TimeTypeEnum timeTypeEnum, PaginationParam pagination) {
        QueryRequest<ActivityQuery> request = QueryRequest.build();
        ActivityQuery query = new ActivityQuery();
        query.setEnterpriseId(Operator.getEnterpriseId());
        query.setIsEnable(1);
        Date date = DateUtil.now();
        if (TimeTypeEnum.CURRENT_TIME.equals(timeTypeEnum)) {
            query.setLessBeginDate(date);
            query.setGreaterEndDate(date);
        } else if (TimeTypeEnum.BEFORE_TIME.equals(timeTypeEnum)) {
            query.setLessEndDate(date);
        } else if (TimeTypeEnum.AFTER_TIME.equals(timeTypeEnum)) {
            query.setGreaterBeginDate(date);
        }
        request.setParam(query);
        if (pagination != null) {
            request.setPagination(pagination);
        }
        return request;
    }
}
