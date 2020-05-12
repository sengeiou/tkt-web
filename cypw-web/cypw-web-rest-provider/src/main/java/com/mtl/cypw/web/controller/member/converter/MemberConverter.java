package com.mtl.cypw.web.controller.member.converter;

import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TMultiResult;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.domain.member.dto.MemberDTO;
import com.mtl.cypw.web.controller.member.vo.MemberVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tang.
 * @date 2019/11/22.
 */
@Component
public class MemberConverter {

    private MemberVO toVo(MemberDTO dto) {
        if (dto == null) {
            return null;
        }
        MemberVO vo = new MemberVO();
        vo.setMemberId(dto.getMemberId());
        vo.setMemberName(dto.getMemberName());
        vo.setNickName(dto.getNickName());
        vo.setMemberMobile(dto.getMemberMobile());
        vo.setEnterpriseId(dto.getEnterpriseId());
        vo.setMemberImage(dto.getMemberImage());
        vo.setDefaultAddressId(dto.getDefaultAddressId());
        vo.setGuessKeys(new ArrayList() {{
            add("演唱会");
            add("周杰伦");
        }});
        return vo;
    }

    private List<MemberVO> toVo(List<MemberDTO> list) {
        if (list == null) {
            return null;
        }
        List<MemberVO> dtoList = new ArrayList<>();
        list.forEach(n -> dtoList.add(toVo(n)));
        return dtoList;
    }

    public TSingleResult<MemberVO> toVo(TSingleResult<MemberDTO> dto) {
        return ResultBuilder.succTSingle(toVo(dto.getData()));
    }

    public TMultiResult<MemberVO> toVo(TMultiResult<MemberDTO> pageDto) {
        return ResultBuilder.succTMulti(toVo(pageDto.getData()));
    }
}
