package com.mtl.cypw.web.controller.mpm.converter;

import com.mtl.cypw.domain.mpm.dto.EnterpriseSearchDTO;
import com.mtl.cypw.web.controller.mpm.vo.EnterpriseSearchVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tang.
 * @date 2020/1/19.
 */
@Component
public class EnterpriseSearchConverter {

    public EnterpriseSearchVO toVo(EnterpriseSearchDTO dto) {
        if (dto == null) {
            return null;
        }
        EnterpriseSearchVO vo = new EnterpriseSearchVO();
        vo.setSearchKey(dto.getSearchKey());
        vo.setGuessKey(dto.getGuessKey());
        return vo;
    }

    public List<EnterpriseSearchVO> toVo(List<EnterpriseSearchDTO> list) {
        if (list == null) {
            return null;
        }
        List<EnterpriseSearchVO> dtoList = new ArrayList<>();
        list.forEach(n -> dtoList.add(toVo(n)));
        return dtoList;
    }
}
