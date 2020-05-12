package com.mtl.cypw.web.controller.banner.converter;

import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TMultiResult;
import com.juqitech.response.TPageResult;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.domain.mpm.dto.BannerDTO;
import com.mtl.cypw.web.controller.banner.vo.BannerViewVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tang.
 * @date 2019/12/9.
 */
@Component
public class BannerConverter {

    public BannerViewVO toVo(BannerDTO dto) {
        if (dto == null) {
            return null;
        }
        BannerViewVO vo = new BannerViewVO();
        vo.setBannerId(dto.getBannerId());
        vo.setBannerName(dto.getBannerName());
        vo.setBannerImage(dto.getBannerImage());
        vo.setBannerUrl(dto.getBannerUrl());
        vo.setBannerType(dto.getBannerType());
        vo.setLinkType(dto.getLinkType());
        vo.setResourceId(dto.getResourceId());
        vo.setBannerDesc(dto.getBannerDesc());
        vo.setSortOrder(dto.getSortOrder());
        return vo;
    }

    public List<BannerViewVO> toVo(List<BannerDTO> list) {
        if (list == null) {
            return null;
        }
        List<BannerViewVO> dtoList = new ArrayList<>();
        list.forEach(n -> dtoList.add(toVo(n)));
        return dtoList;
    }

    public TSingleResult<BannerViewVO> toVo(TSingleResult<BannerDTO> dto) {
        return ResultBuilder.succTSingle(toVo(dto.getData()));
    }

    public TMultiResult<BannerViewVO> toVo(TMultiResult<BannerDTO> dto) {
        return ResultBuilder.succTMulti(toVo(dto.getData()));
    }

    public TPageResult<BannerViewVO> toVo(TPageResult<BannerDTO> pageDto) {
        return ResultBuilder.succTPage(toVo(pageDto.getData()), pageDto.getPagination());
    }
}
