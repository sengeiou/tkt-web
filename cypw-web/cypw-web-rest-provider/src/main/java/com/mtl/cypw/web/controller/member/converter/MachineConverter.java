package com.mtl.cypw.web.controller.member.converter;

import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TMultiResult;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.domain.member.dto.MachineDTO;
import com.mtl.cypw.web.controller.member.vo.MachineVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tang.
 * @date 2020/3/12.
 */
@Component
public class MachineConverter {

    public MachineVO toVo(MachineDTO dto) {
        if (dto == null) {
            return null;
        }
        MachineVO vo = new MachineVO();
        vo.setMachineId(dto.getMachineId());
        vo.setEnterpriseId(dto.getEnterpriseId());
        vo.setMachineKey(dto.getMachineKey());
        vo.setMacAddress(dto.getMacAddress());
        return vo;
    }

    public List<MachineVO> toVo(List<MachineDTO> list) {
        if (list == null) {
            return null;
        }
        List<MachineVO> voList = new ArrayList<>();
        list.forEach(n -> voList.add(toVo(n)));
        return voList;
    }

    public TSingleResult<MachineVO> toVo(TSingleResult<MachineDTO> dto) {
        return ResultBuilder.succTSingle(toVo(dto.getData()));
    }

    public TMultiResult<MachineVO> toVo(TMultiResult<MachineDTO> pageDto) {
        return ResultBuilder.succTMulti(toVo(pageDto.getData()));
    }

}
