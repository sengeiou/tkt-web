package com.mtl.cypw.web.controller.mpm.converter;

import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TMultiResult;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.domain.mpm.dto.EnterpriseDTO;
import com.mtl.cypw.domain.payment.enums.PayTypeEnum;
import com.mtl.cypw.web.controller.mpm.vo.EnterpriseVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author tang.
 * @date 2019/11/26.
 */
@Component
public class EnterpriseConverter {

    @Resource
    ProgramTypeConverter programTypeConverter;

    @Resource
    EnterpriseSearchConverter enterpriseSearchConverter;

    public EnterpriseVO toVo(EnterpriseDTO dto) {
        if (dto == null) {
            return null;
        }
        EnterpriseVO vo = new EnterpriseVO();
        vo.setEnterpriseId(dto.getEnterpriseId());
        vo.setEnterpriseName(dto.getEnterpriseName());
        vo.setTenantId(dto.getTenantId());
        vo.setTenantLogo(dto.getLogoImage());
        vo.setServicePhone(dto.getServiceMobile());
        vo.setIndexTemplate(dto.getIndexTemplate());
        vo.setIndexBackgroundColor(dto.getIndexBackgroundColor());
        vo.setHomeTitle(dto.getHomeTitle());
        vo.setExtendedLogin(dto.getExtendedLogin());
        List<PayTypeEnum> payTypes = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(dto.getEnterprisePayTypes())) {
            dto.getEnterprisePayTypes().forEach(n -> {
                payTypes.add(PayTypeEnum.getObject(n.getPayTypeId()));
                if (PayTypeEnum.CCB_PAY.getPayCode() == n.getPayTypeId()) {
                    vo.setCcbInstallmentStr(n.getRemark());
                }
            });
        }
        vo.setPaymentTypes(payTypes);
        if (CollectionUtils.isNotEmpty(dto.getEnterpriseProgramTypes())) {
            vo.setProgramTypes(programTypeConverter.toVo(dto.getEnterpriseProgramTypes()));
        } else {
            vo.setProgramTypes(Collections.EMPTY_LIST);
        }
        if (dto.getEnterpriseSearchDTO() != null) {
            vo.setEnterpriseSearch(enterpriseSearchConverter.toVo(dto.getEnterpriseSearchDTO()));
        }
        if (dto.getEnterpriseDialogDTO() != null) {
            vo.setMessage1(dto.getEnterpriseDialogDTO().getMessage1());
            vo.setMessage2(dto.getEnterpriseDialogDTO().getMessage2());
        }
        return vo;
    }

    private List<EnterpriseVO> toVo(List<EnterpriseDTO> list) {
        if (list == null) {
            return null;
        }
        List<EnterpriseVO> dtoList = new ArrayList<>();
        list.forEach(n -> dtoList.add(toVo(n)));
        return dtoList;
    }

    public TSingleResult<EnterpriseVO> toVo(TSingleResult<EnterpriseDTO> dto) {
        return ResultBuilder.succTSingle(toVo(dto.getData()));
    }

    public TMultiResult<EnterpriseVO> toVo(TMultiResult<EnterpriseDTO> pageDto) {
        return ResultBuilder.succTMulti(toVo(pageDto.getData()));
    }
}
