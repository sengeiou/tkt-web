package com.mtl.cypw.web.controller.ucloud;

import com.alibaba.fastjson.JSONObject;
import com.juqitech.response.ResultBuilder;
import com.juqitech.response.TSingleResult;
import com.mtl.cypw.common.enums.ErrorCode;
import com.mtl.cypw.common.enums.FileDirectoryEnum;
import com.mtl.cypw.common.utils.FileUploadTemplate;
import com.mtl.cypw.common.utils.FileUtil;
import com.mtl.cypw.web.controller.WebGenericController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;

/**
 * @author tang.
 * @date 2019/12/11.
 */
@RestController
@Slf4j
@Api(tags = {"票星球-文件上传"})
@CrossOrigin
public class FileController extends WebGenericController {

    @Autowired
    private FileUploadTemplate fileUploadTemplate;

    @RequestMapping(value = "/pub/v1/file/upload", method = RequestMethod.POST)
    @ApiOperation(value = "上传文件", httpMethod = "POST", response = String.class, notes = "上传文件")
    public TSingleResult<String> upload(@ApiParam(required = true, name = "base64String", value = "图片编码") @RequestBody JSONObject base64String) {
        try {
            File file = FileUtil.base64StringToImage(base64String.getString("base64String"));
            return ResultBuilder.succTSingle(fileUploadTemplate.fileUpload(file, FileDirectoryEnum.member));
        } catch (Exception e) {
            log.error("文件上传异常:{}", e.getMessage());
        }
        return ResultBuilder.failTSingle(ErrorCode.ERROR_COMMON_UPLOAD_FILE.getCode(), ErrorCode.ERROR_COMMON_UPLOAD_FILE.getMsg());
    }
}
