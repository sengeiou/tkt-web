package com.mtl.cypw.web.controller.mpm.converter;

import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TMultiResult;
import com.juqitech.response.TPageResult;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.domain.mpm.dto.BuryingPointDTO;
import com.mtl.cypw.domain.mpm.enums.BuryingPointTypeEnum;
import com.mtl.cypw.domain.mpm.param.BuryingPointParam;
import com.mtl.cypw.web.controller.mpm.param.CreateBuryingPointParam;
import com.mtl.cypw.web.controller.mpm.vo.BuryingPointVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tang.
 * @date 2020/1/19.
 */
@Component
public class BuryingPointConverter {

    public BuryingPointVO toVo(BuryingPointDTO dto) {
        if (dto == null) {
            return null;
        }
        BuryingPointVO vo = new BuryingPointVO();
        vo.setId(dto.getId());
        vo.setMemberId(dto.getMemberId());
        vo.setBuryingPointType(dto.getBuryingPointType());
        vo.setBuryingPointContent(dto.getBuryingPointContent());
        vo.setEnterpriseId(dto.getEnterpriseId());
        vo.setCreateTime(dto.getCreateTime());
        return vo;
    }

    public List<BuryingPointVO> toVo(List<BuryingPointDTO> list) {
        if (list == null) {
            return null;
        }
        List<BuryingPointVO> voList = new ArrayList<>();
        list.forEach(n -> voList.add(toVo(n)));
        return voList;
    }

    public BuryingPointParam toBuryingPointParam(CreateBuryingPointParam param) {
        BuryingPointParam buryingPointParam = new BuryingPointParam();
        if (BuryingPointTypeEnum.getObject(param.getType()) != null) {
            buryingPointParam.setBuryingPointType(BuryingPointTypeEnum.getObject(param.getType()));
        } else {
            buryingPointParam.setBuryingPointType(BuryingPointTypeEnum.COMPREHENSIVE);
        }
        buryingPointParam.setBuryingPointContent(param.getContent());
        buryingPointParam.setSourcePlatform(param.getSourcePlatform());
        buryingPointParam.setSourcePage(param.getSourcePage());
        return buryingPointParam;
    }

    public TSingleResult<BuryingPointVO> toVo(TSingleResult<BuryingPointDTO> dto) {
        return ResultBuilder.succTSingle(toVo(dto.getData()));
    }

    public TMultiResult<BuryingPointVO> toVo(TMultiResult<BuryingPointDTO> pageDto) {
        return ResultBuilder.succTMulti(toVo(pageDto.getData()));
    }

    public TPageResult<BuryingPointVO> toVo(TPageResult<BuryingPointDTO> pageDto) {
        return ResultBuilder.succTPage(toVo(pageDto.getData()), pageDto.getPagination());
    }

}
