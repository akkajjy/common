package com.palwy.common.controller;

import com.palwy.common.req.TOSRespVO;
import com.palwy.common.util.ResultVOUtil;
import com.palwy.common.utils.TOSUpFileUtil;
import com.palwy.common.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Objects;

@RestController
@RequestMapping("/file")
@Api(tags = "文件相关接口")
@Slf4j
public class FileController {

    @Autowired
    private TOSUpFileUtil tosUpFileUtil;
    /**
     * 上传文件到tos指定目录
     * @param file
     * @return
     */
    @ApiOperation("文件上传")
    @PostMapping(value = "/upload")
    public ResultVO uploadTos(MultipartFile file){
        if(Objects.isNull(file)){
            return ResultVOUtil.fail("文件不能为空");
        }
        try(InputStream inputStream = file.getInputStream()) {
            TOSRespVO tosRespVO = tosUpFileUtil.uploadFile(TOSUpFileUtil.BusinessType.SELF_OPERATED, file.getOriginalFilename(), inputStream);
            return ResultVOUtil.success("上传成功",tosRespVO);
        } catch (Exception e) {
            log.info("上传文件至tos异常:{}", e);
        }
        return ResultVOUtil.fail("上传失败");
    }

    /**
     * 上传文件到tos指定目录
     * @param file
     * @return
     */
    @ApiOperation("文件上传自定义key")
    @PostMapping(value = "/uploadSpecifyKey")
    public ResultVO uploadSpecifyKey(MultipartFile file,String objectKey){
        if(Objects.isNull(file)){
            return ResultVOUtil.fail("文件不能为空");
        }
        try(InputStream inputStream = file.getInputStream()) {
            TOSRespVO tosRespVO = tosUpFileUtil.upload(objectKey, inputStream);
            return ResultVOUtil.success("上传成功",tosRespVO);
        } catch (Exception e) {
            log.info("上传文件自定义key至tos异常:{}", e);
        }
        return ResultVOUtil.fail("上传失败");
    }

    @ApiOperation("文件删除")
    @GetMapping(value = "/remove")
    public ResultVO removeFile(String objectKey){
        tosUpFileUtil.deleteFile(objectKey);
        return ResultVOUtil.success("删除成功");
    }
}
