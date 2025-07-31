package com.palwy.common.controller;

import com.palwy.common.entity.ClrDictDO;
import com.palwy.common.service.ClrDictService;
import com.palwy.common.util.ResultVOUtil;
import com.palwy.common.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("dict/")
@Api(tags = "字典表接口")
@Slf4j
public class DictController {


    @Autowired
    private ClrDictService clrDictService;

    @ApiOperation(value = "查询字典")
    @GetMapping("/getByType")
    public ResultVO getVersionPlatformList(String dictType) {
        List<ClrDictDO> clrDictDOList = clrDictService.getClrDictListByDictType(dictType);
        Map<String, String> platformMap = clrDictDOList.stream()
                .collect(Collectors.toMap(
                        ClrDictDO::getDictValue,
                        ClrDictDO::getDictLabel,
                        (existing, replacement) -> existing
                ));
        return ResultVOUtil.success(platformMap);
    }

}
