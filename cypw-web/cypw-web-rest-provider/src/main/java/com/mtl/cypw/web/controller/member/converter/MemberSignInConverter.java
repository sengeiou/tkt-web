package com.mtl.cypw.web.controller.member.converter;

import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TMultiResult;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.domain.member.dto.MemberSignInDTO;
import com.mtl.cypw.domain.member.param.MemberSignInParam;
import com.mtl.cypw.web.controller.member.param.CreateMemberSignInParam;
import com.mtl.cypw.web.controller.member.vo.MemberSignInVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tang.
 * @date 2020/3/3.
 */
@Component
public class MemberSignInConverter {

    public MemberSignInVO toVo(MemberSignInDTO dto) {
        if (dto == null) {
            return null;
        }
        MemberSignInVO vo = new MemberSignInVO();
        vo.setId(dto.getId());
        vo.setMemberId(dto.getMemberId());
        vo.setOrderId(dto.getOrderId());
        vo.setProgramId(dto.getProgramId());
        vo.setEventId(dto.getEventId());
        vo.setTheatreId(dto.getTheatreId());
        vo.setSignInAddress(dto.getSignInAddress());
        vo.setLongitude(dto.getLongitude());
        vo.setLatitude(dto.getLatitude());
        vo.setType(dto.getType());
        vo.setEnterpriseId(dto.getEnterpriseId());
        vo.setRemark(dto.getRemark());
        vo.setSignInTime(dto.getSignInTime());
        return vo;
    }

    public MemberSignInParam toMemberSignInParam(CreateMemberSignInParam param){
        MemberSignInParam memberSignInParam = new MemberSignInParam();
        memberSignInParam.setOrderId(param.getOrderId());
        memberSignInParam.setProgramId(param.getProgramId());
        memberSignInParam.setEventId(param.getEventId());
        memberSignInParam.setTheatreId(param.getTheatreId());
        memberSignInParam.setSignInAddress(param.getSignInAddress());
        memberSignInParam.setLongitude(param.getLongitude());
        memberSignInParam.setLatitude(param.getLatitude());
        memberSignInParam.setType(param.getType());
        memberSignInParam.setRemark(param.getRemark());
        return memberSignInParam;
    }

    public List<MemberSignInVO> toVo(List<MemberSignInDTO> list) {
        if (list == null) {
            return null;
        }
        List<MemberSignInVO> voList = new ArrayList<>();
        list.forEach(n -> voList.add(toVo(n)));
        return voList;
    }

    public TSingleResult<MemberSignInVO> toVo(TSingleResult<MemberSignInDTO> dto) {
        return ResultBuilder.succTSingle(toVo(dto.getData()));
    }

    public TMultiResult<MemberSignInVO> toVo(TMultiResult<MemberSignInDTO> dto) {
        return ResultBuilder.succTMulti(toVo(dto.getData()));
    }
}
