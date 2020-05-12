package com.mtl.cypw.web.controller.admin.converter;

import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TMultiResult;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.domain.admin.dto.UserDTO;
import com.mtl.cypw.web.controller.admin.vo.UserVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tang.
 * @date 2020/3/18.
 */
@Component
public class UserConverter {

    public UserVO toVo(UserDTO dto) {
        UserVO vo = new UserVO();
        vo.setUserId(dto.getUserId());
        vo.setLoginName(dto.getLoginName());
        vo.setPersonName(dto.getPersonName());
        vo.setPersonMobile(dto.getPersonMobile());
        vo.setAddDate(dto.getAddDate());
        vo.setIsAdministrator(dto.getIsAdministrator());
        vo.setEnterpriseId(dto.getEnterpriseId());
        vo.setIsPrint(dto.getIsPrint());
        return vo;
    }

    public List<UserVO> toVo(List<UserDTO> list) {
        if (list == null) {
            return null;
        }
        List<UserVO> voList = new ArrayList<>();
        list.forEach(n -> voList.add(toVo(n)));
        return voList;
    }

    public TSingleResult<UserVO> toVo(TSingleResult<UserDTO> dto) {
        return ResultBuilder.succTSingle(toVo(dto.getData()));
    }

    public TMultiResult<UserVO> toVo(TMultiResult<UserDTO> dtoList) {
        return ResultBuilder.succTMulti(toVo(dtoList.getData()));
    }
}
