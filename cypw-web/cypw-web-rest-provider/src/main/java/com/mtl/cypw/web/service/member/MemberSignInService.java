package com.mtl.cypw.web.service.member;

import com.alibaba.fastjson.JSONObject;
import com.juqitech.request.GenericRequest;
import com.juqitech.request.QueryRequest;
import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TMultiResult;
import com.juqitech.response.TSingleResult;
import com.juqitech.service.utils.DateUtils;
import com.mtl.cypw.api.member.client.MemberSignInApiClient;
import com.mtl.cypw.api.mpm.client.TemplateApiClient;
import com.mtl.cypw.api.mpm.client.TheatreApiClient;
import com.mtl.cypw.api.mpm.client.VenueApiClient;
import com.mtl.cypw.api.show.client.EventApiClient;
import com.mtl.cypw.common.exception.BusinessException;
import com.mtl.cypw.common.redis.template.CommonRedisTemplate;
import com.mtl.cypw.domain.member.dto.MemberSignInDTO;
import com.mtl.cypw.domain.member.param.MemberSignInParam;
import com.mtl.cypw.domain.member.param.MemberSignInQueryParam;
import com.mtl.cypw.domain.mpm.dto.TemplateDTO;
import com.mtl.cypw.domain.mpm.dto.TheatreDTO;
import com.mtl.cypw.domain.mpm.dto.VenueDTO;
import com.mtl.cypw.domain.mpm.param.TemplateQueryParam;
import com.mtl.cypw.domain.mpm.param.TheatreQueryParam;
import com.mtl.cypw.domain.mpm.param.VenueQueryParam;
import com.mtl.cypw.domain.show.dto.EventDTO;
import com.mtl.cypw.domain.show.query.EventQuery;
import com.mtl.cypw.web.common.Constants;
import com.mtl.cypw.web.common.Operator;
import com.mtl.cypw.web.controller.member.converter.MemberSignInConverter;
import com.mtl.cypw.web.controller.member.param.CreateMemberSignInParam;
import com.mtl.cypw.web.controller.member.vo.MemberSignInVO;
import com.mtl.cypw.web.controller.member.vo.SignInVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;

/**
 * @author tang.
 * @date 2020/3/3.
 */
@Slf4j
@Service
public class MemberSignInService {

    /**
     * 搜索范围（米）
     */
    private int searchScope = 500;

    @Autowired
    private CommonRedisTemplate redisTemplate;

    @Resource
    private MemberSignInApiClient memberSignInApiClient;

    @Resource
    private MemberSignInConverter memberSignInConverter;

    @Resource
    private EventApiClient eventApiClient;

    @Resource
    private TemplateApiClient templateApiClient;

    @Resource
    private VenueApiClient venueApiClient;

    @Resource
    private TheatreApiClient theatreApiClient;

    public TMultiResult<SignInVO> searchSignInTheatre(Double longitude, Double latitude) {
        List<SignInVO> list = new ArrayList<>();
        //获取范围内的剧院id
        List<String> theatreIdStr = redisTemplate.geoRadius(Constants.getTheatreGeoKey(), longitude, latitude, searchScope);
        if (CollectionUtils.isEmpty(theatreIdStr)) {
            log.debug("附近没有可签到的剧院");
            return ResultBuilder.succTMulti(null);
        }
        List<Integer> theatreIds = new ArrayList<>();
        List<Integer> templateIds = new ArrayList<>();
        List<Integer> venueIds = new ArrayList<>();
        theatreIdStr.forEach(n -> theatreIds.add(Integer.parseInt(n)));

        //查询剧院信息
        List<TheatreDTO> theatres = getTheatres(theatreIds);
        if(CollectionUtils.isEmpty(theatres)){
            log.error("没有查到剧院信息，theatreIds：{}",JSONObject.toJSONString(theatreIds));
            return ResultBuilder.succTMulti(null);
        }
        Map<Integer, TheatreDTO> theatreMap = new HashMap<>();
        theatres.forEach(n -> theatreMap.put(n.getTheatreId(), n));

        //查询商户当天所有场次
        List<EventDTO> events = getEvents();
        if(CollectionUtils.isNotEmpty(events)){
            events.forEach(n -> templateIds.add(n.getTemplateId()));
            //查询场次对应的模板
            List<TemplateDTO> templates = getTemplates(templateIds);
            if(CollectionUtils.isNotEmpty(templates)) {
                Map<Integer, TemplateDTO> templateMap = new HashMap<>();
                templates.forEach(n -> {
                    venueIds.add(n.getVenueId());
                    templateMap.put(n.getTemplateId(), n);
                });
                //查询模板对应的场馆
                List<VenueDTO> venues = getVenues(venueIds);
                if(CollectionUtils.isNotEmpty(venues)) {
                    Map<Integer, VenueDTO> venueMap = new HashMap<>();
                    venues.forEach(n -> {
                        venueMap.put(n.getVenueId(), n);
                    });
                    //比对场次对应的剧院和用户附近的剧院
                    events.forEach(n -> {
                        TemplateDTO template = templateMap.get(n.getTemplateId());
                        if (template != null) {
                            VenueDTO venue = venueMap.get(template.getVenueId());
                            if (venue != venue) {
                                TheatreDTO theatre = theatreMap.get(venue.getTheatreId());
                                if (theatre != null) {
                                    SignInVO vo = new SignInVO();
                                    vo.setProgramId(n.getProgramId());
                                    vo.setEventId(n.getEventId());
                                    vo.setEventDate(n.getEventDate());
                                    vo.setTheatreId(theatre.getTheatreId());
                                    vo.setTheatreAddress(theatre.getTheatreAddress());
                                    list.add(vo);
                                }
                            }
                        }
                    });
                }else {
                    log.debug("未查询到场馆，venueIds:{}", JSONObject.toJSONString(venueIds));
                }
            }else {
                log.debug("未查询到模板，templateIds:{}", JSONObject.toJSONString(templateIds));
            }
        }else {
            log.debug("该企业今天没有可以签到的场次");
        }
        return ResultBuilder.succTMulti(list);
    }

    public TSingleResult<Boolean> addMemberSignIn(CreateMemberSignInParam createMemberSignInParam) {
        MemberSignInParam param = memberSignInConverter.toMemberSignInParam(createMemberSignInParam);
        param.setEnterpriseId(Operator.getEnterpriseId());
        param.setMemberId(Operator.getMemberId());
        GenericRequest<MemberSignInParam> request = new GenericRequest<>();
        request.setParam(param);
        TSingleResult<Boolean> result = memberSignInApiClient.addMemberSignIn(request);
        if (result.success()) {
            return result;
        } else {
            return ResultBuilder.failTSingle(result.getStatusCode(), result.getComments());
        }
    }

    public TMultiResult<MemberSignInVO> searchMemberSignInList(Date signInTimeBegin, Date signInTimeEnd) {
        MemberSignInQueryParam param = new MemberSignInQueryParam();
        param.setEnterpriseId(Operator.getEnterpriseId());
        param.setMemberId(Operator.getMemberId());
        param.setSignInTimeBegin(signInTimeBegin);
        param.setSignInTimeEnd(signInTimeEnd);
        QueryRequest<MemberSignInQueryParam> request = QueryRequest.build();
        request.setParam(param);
        TMultiResult<MemberSignInDTO> result = memberSignInApiClient.searchMemberSignInList(request);
        if (result.success()) {
            return memberSignInConverter.toVo(result);
        } else {
            return ResultBuilder.failTMulti(result.getStatusCode(), result.getComments());
        }
    }

    private List<EventDTO> getEvents() {
        log.debug("获取企业今天所有的场次");
        EventQuery queryParam = new EventQuery();
        try {
            String[] currentDay = DateUtils.getCurrentDay();
            Date currentDateBegin = DateUtils.parseNoSecondFormat(currentDay[0]);
            Date currentDateEnd = DateUtils.parseNoSecondFormat(currentDay[1]);
            queryParam.setGreaterEventDate(currentDateBegin);
            queryParam.setLessEventDate(currentDateEnd);
        } catch (ParseException e) {
            log.error("获取当天时间失败");
        }
        queryParam.setEnterpriseId(Operator.getEnterpriseId());
        QueryRequest<EventQuery> request = QueryRequest.build();
        request.setParam(queryParam);
        TMultiResult<EventDTO> result = eventApiClient.searchEventList(request);
        if (result.success()) {
            return result.getData();
        } else {
            throw new BusinessException(result.getComments(), result.getStatusCode());
        }
    }

    private List<TemplateDTO> getTemplates(List<Integer> templateIds) {
        TemplateQueryParam queryParam = new TemplateQueryParam();
        queryParam.setTemplateIds(templateIds);
        QueryRequest<TemplateQueryParam> request = QueryRequest.build();
        request.setParam(queryParam);
        TMultiResult<TemplateDTO> result = templateApiClient.getTemplateList(request);
        if (result.success()) {
            return result.getData();
        } else {
            throw new BusinessException(result.getComments(), result.getStatusCode());
        }
    }

    private List<VenueDTO> getVenues(List<Integer> venueIds) {
        VenueQueryParam queryParam = new VenueQueryParam();
        queryParam.setVenueIds(venueIds);
        QueryRequest<VenueQueryParam> request = QueryRequest.build();
        request.setParam(queryParam);
        TMultiResult<VenueDTO> result = venueApiClient.getVenueList(request);
        if (result.success()) {
            return result.getData();
        } else {
            throw new BusinessException(result.getComments(), result.getStatusCode());
        }
    }

    private List<TheatreDTO> getTheatres(List<Integer> theatreIds) {
        TheatreQueryParam queryParam = new TheatreQueryParam();
        queryParam.setTheatreIds(theatreIds);
        QueryRequest<TheatreQueryParam> request = QueryRequest.build();
        request.setParam(queryParam);
        TMultiResult<TheatreDTO> result = theatreApiClient.getTheatreList(request);
        if (result.success()) {
            return result.getData();
        } else {
            throw new BusinessException(result.getComments(), result.getStatusCode());
        }
    }

}
