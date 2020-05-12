package com.mtl.cypw.web.service.show;

import com.alibaba.fastjson.JSONObject;
import com.juqitech.request.GenericRequest;
import com.juqitech.request.IdRequest;
import com.juqitech.request.PaginationParam;
import com.juqitech.request.QueryRequest;
import com.juqitech.response.TMultiResult;
import com.juqitech.response.TPageResult;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.api.coupon.client.PromotionApiClient;
import com.mtl.cypw.api.coupon.client.PromotionCouponApiClient;
import com.mtl.cypw.api.show.client.ActorApiClient;
import com.mtl.cypw.api.show.client.EventPriceApiClient;
import com.mtl.cypw.api.show.client.ProgramApiClient;
import com.mtl.cypw.domain.coupon.dto.PromotionDTO;
import com.mtl.cypw.domain.coupon.enums.PromotionTypeEnum;
import com.mtl.cypw.domain.coupon.param.PromotionCouponQueryParam;
import com.mtl.cypw.domain.coupon.param.PromotionQueryParam;
import com.mtl.cypw.domain.show.dto.ActorDTO;
import com.mtl.cypw.domain.show.dto.EventPriceDTO;
import com.mtl.cypw.domain.show.dto.ProgramDTO;
import com.mtl.cypw.domain.show.enums.SaleStatusEnum;
import com.mtl.cypw.domain.show.query.EventQuery;
import com.mtl.cypw.domain.show.query.ProgramQuery;
import com.mtl.cypw.web.common.Operator;
import com.mtl.cypw.web.controller.coupon.converter.PromotionConverter;
import com.mtl.cypw.web.controller.coupon.vo.PromotionVO;
import com.mtl.cypw.web.controller.show.converter.ActorConverter;
import com.mtl.cypw.web.controller.show.converter.ProgramConverter;
import com.mtl.cypw.web.controller.show.converter.ProgramDetailConverter;
import com.mtl.cypw.web.controller.show.vo.ActorVO;
import com.mtl.cypw.web.controller.show.vo.ProgramDetailVO;
import com.mtl.cypw.web.controller.show.vo.ProgramVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author tang.
 * @date 2019/11/20.
 */
@Slf4j
@Service
public class ProgramService {

    @Autowired
    ProgramApiClient programApiClient;

    @Autowired
    ProgramConverter programConverter;

    @Autowired
    ProgramDetailConverter programDetailConverter;

    @Autowired
    EventPriceApiClient eventPriceApiClient;

    @Autowired
    ActorApiClient actorApiClient;

    @Autowired
    ActorConverter actorConverter;

    @Resource
    PromotionApiClient promotionApiClient;

    @Resource
    PromotionCouponApiClient promotionCouponApiClient;

    @Resource
    PromotionConverter converter;

    public TPageResult<ProgramVO> searchProgramList(PaginationParam paginationParam, Integer isRecommend) {
        return searchProgramList(paginationParam, null, null, isRecommend);
    }

    public TPageResult<ProgramVO> searchProgramList(PaginationParam paginationParam, String likeName, List<Integer> programTypes, Integer isRecommend) {
        ProgramQuery query = new ProgramQuery();
        query.setEnterpriseId(Operator.getEnterpriseId());
        query.setLikeName(likeName);
        query.setProgramTypeList(programTypes);
        query.setIsRecommend(isRecommend);
        List<Integer> saleStatusList = new ArrayList<>();
        saleStatusList.add(SaleStatusEnum.START_SALE.getCode());
        saleStatusList.add(SaleStatusEnum.ADVANCE_SALE.getCode());
        saleStatusList.add(SaleStatusEnum.APPOINTMENT_SALE.getCode());
        query.setSaleStatusList(saleStatusList);

        QueryRequest<ProgramQuery> request = QueryRequest.build();
        request.setParam(query);
        request.setPagination(paginationParam);
        TPageResult<ProgramDTO> pageDto = programApiClient.searchProgramList(request);
        log.debug("查询结果，response：{}", JSONObject.toJSONString(pageDto.getPagination()));
        return addMinPrice(programConverter.toVo(pageDto));
    }

    public TSingleResult<ProgramDetailVO> getProgramById(Integer projectId) {
        log.debug("查询演出详情，projectId：{}", JSONObject.toJSONString(projectId));
        ProgramQuery query = new ProgramQuery();
        query.setProgramId(projectId);
        QueryRequest<ProgramQuery> request = QueryRequest.build();
        request.setParam(query);

        TSingleResult<ProgramDetailVO> vo = new TSingleResult<>();
        TSingleResult<ProgramDTO> dto = programApiClient.getProgramById(request);
        if (dto.getData() != null) {
            vo = programDetailConverter.toVo(dto);
            EventPriceDTO eventPrice = searchMinEventPrice(dto.getData().getProgramId());
            if (eventPrice != null) {
                vo.getData().setMinPrice(eventPrice.getPriceValue());
            }
            List<ActorVO> actors = searchActorList(dto.getData().getProgramId());
            if (actors != null) {
                vo.getData().setActors(actors);
            }
            List<PromotionVO> promotions = searchPromotionList(dto.getData().getProgramId(), dto.getData().getProgramTypeId());
            if (promotions != null) {
                vo.getData().setPromotions(promotions);
            }
        }
        log.debug("查询结果，response：{}", JSONObject.toJSONString(dto));
        return vo;
    }

    private TPageResult<ProgramVO> addMinPrice(TPageResult<ProgramVO> pageResult) {
        List<ProgramVO> list = pageResult.getData();
        if (list == null || list.size() == 0) {
            return pageResult;
        }
        List<Integer> programIds = new ArrayList<>();
        list.forEach(n -> programIds.add(n.getId()));
        Map<Integer, BigDecimal> map = searchMinEventPriceByProgramIds(programIds);
        list.forEach(n -> n.setMinPrice(map.get(n.getId())));
        return pageResult;
    }

    private EventPriceDTO searchMinEventPrice(Integer programId) {
        log.debug("查询演出最低票价,programId:{}", programId);
        if (programId == null) {
            return null;
        }
        QueryRequest<EventQuery> eventRequest = new QueryRequest<>();
        EventQuery eventQuery = new EventQuery();
        eventQuery.setProgramId(programId);
        eventRequest.setParam(eventQuery);
        TSingleResult<EventPriceDTO> eventPriceResult = eventPriceApiClient.searchMinEventPriceByProgramId(eventRequest);
        return eventPriceResult.getData();
    }

    private Map<Integer, BigDecimal> searchMinEventPriceByProgramIds(List<Integer> programIds) {
        log.debug("查询演出最低票价,programId:{}", programIds);
        Map<Integer, BigDecimal> map = new HashMap<>();
        if (programIds == null) {
            return map;
        }
        QueryRequest<EventQuery> request = new QueryRequest<>();
        EventQuery query = new EventQuery();
        query.setProgramIds(programIds);
        request.setParam(query);
        TMultiResult<EventPriceDTO> result = eventPriceApiClient.searchMinEventPriceByProgramIds(request);
        List<EventPriceDTO> list = result.getData();
        if (list != null) {
            map = list.stream().collect(Collectors.toMap(EventPriceDTO::getProgramId, n -> n.getPriceValue()));
        }
        return map;
    }

    private List<ActorVO> searchActorList(Integer programId) {
        log.debug("演职人员列表,programId:{}", programId);
        if (programId == null) {
            return null;
        }
        IdRequest request = new IdRequest();
        request.setId(programId.toString());
        TMultiResult<ActorDTO> eventPriceResult = actorApiClient.searchActorList(request);
        return actorConverter.toVo(eventPriceResult.getData());
    }

    private List<PromotionVO> searchPromotionList(Integer programId, Integer programTypeId) {
        log.debug("项目相关优惠券列表,programId:{}", programId);
        if (programId == null) {
            return null;
        }
        PromotionQueryParam query = new PromotionQueryParam();
        query.setProgramId(programId);
        List<Integer> promotionTypeList = new ArrayList<>();
        promotionTypeList.add(PromotionTypeEnum.DISCOUNT_COUPON.getCode());
        promotionTypeList.add(PromotionTypeEnum.CASH_COUPON.getCode());
        query.setPromotionTypeList(promotionTypeList);
        query.setEnterpriseId(Operator.getEnterpriseId());
        query.setDistributionType(0);
        query.setIsReceiveTime(1);
        if (programTypeId != null) {
            List<Integer> programTypeIds = new ArrayList<>();
            programTypeIds.add(programTypeId);
            query.setProgramTypeIds(programTypeIds);
        }
        if (Operator.getMemberId() != null) {
            GenericRequest<PromotionCouponQueryParam> promotionCouponRequest = new GenericRequest<>();
            PromotionCouponQueryParam param = new PromotionCouponQueryParam();
            param.setMemberId(Operator.getMemberId());
            promotionCouponRequest.setParam(param);
            TMultiResult<Integer> promotionIdList = promotionCouponApiClient.searchPromotionIdListByMemberId(promotionCouponRequest);
            List<Integer> ids = promotionIdList.getData();
            query.setNotPromotionIds(ids);
        }
        QueryRequest<PromotionQueryParam> request = QueryRequest.build();
        request.setParam(query);
        TMultiResult<PromotionDTO> result = promotionApiClient.searchPromotionList(request);
        return converter.toVo(result.getData());
    }
}
