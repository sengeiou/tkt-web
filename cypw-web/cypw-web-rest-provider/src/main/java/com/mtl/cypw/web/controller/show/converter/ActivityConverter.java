package com.mtl.cypw.web.controller.show.converter;

import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TMultiResult;
import com.juqitech.response.TPageResult;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.domain.show.dto.ActivityDTO;
import com.mtl.cypw.web.controller.show.vo.ActivityVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tang.
 * @date 2020/1/6.
 */
@Component
public class ActivityConverter {

    public ActivityVO toVo(ActivityDTO dto) {
        if (dto == null) {
            return null;
        }
        ActivityVO vo = new ActivityVO();
        vo.setActivityId(dto.getActivityId());
        vo.setActivityName(dto.getActivityName());
        vo.setActivityBrief(dto.getActivityBrief());
        vo.setActivityImage(dto.getActivityImage());
        vo.setBeginDate(dto.getBeginDate());
        vo.setEndDate(dto.getEndDate());
        vo.setTypeId(dto.getTypeId());
        vo.setActivityUrl(dto.getActivityUrl());
        vo.setIsEnable(dto.getIsEnable());
        vo.setEnterpriseId(dto.getEnterpriseId());
        vo.setAddDate(dto.getAddDate());
        vo.setActivityContent(dto.getActivityContent());
        return vo;
    }

    public List<ActivityVO> toVo(List<ActivityDTO> list) {
        if (list == null) {
            return null;
        }
        List<ActivityVO> dtoList = new ArrayList<>();
        list.forEach(n -> dtoList.add(toVo(n)));
        return dtoList;
    }

    public TSingleResult<ActivityVO> toVo(TSingleResult<ActivityDTO> dto) {
        return ResultBuilder.succTSingle(toVo(dto.getData()));
    }

    public TMultiResult<ActivityVO> toVo(TMultiResult<ActivityDTO> dto) {
        return ResultBuilder.succTMulti(toVo(dto.getData()));
    }

    public TPageResult<ActivityVO> toVo(TPageResult<ActivityDTO> pageDto) {
        return ResultBuilder.succTPage(toVo(pageDto.getData()), pageDto.getPagination());
    }
}
