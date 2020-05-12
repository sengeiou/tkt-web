package com.mtl.cypw.web.service.show;

import com.mtl.cypw.api.show.client.ProgramDistributionApiClient;
import com.mtl.cypw.domain.show.dto.ProgramDistributionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tang.
 * @date 2019/11/20.
 */
@Slf4j
@Service
public class ProgramDistributionService {

    @Autowired
    ProgramDistributionApiClient programDistributionApiClient;

    public List<ProgramDistributionDTO> searchProgramDistributionList(Integer programId) {
        return programDistributionApiClient.searchProgramDistributionList(programId);
    }
}
