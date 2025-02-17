package com.aurora.controller;

import com.aurora.annotation.OptLog;
import com.aurora.dto.AboutDTO;
import com.aurora.dto.AuroraAdminInfoDTO;
import com.aurora.dto.AuroraHomeInfoDTO;
import com.aurora.dto.WebsiteConfigDTO;
import com.aurora.enums.FilePathEnum;
import com.aurora.service.AuroraInfoService;
import com.aurora.strategy.context.UploadStrategyContext;
import com.aurora.vo.AboutVO;
import com.aurora.vo.Result;
import com.aurora.vo.WebsiteConfigVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static com.aurora.constant.OptTypeConst.UPDATE;

@Api(tags = "aurora信息")
@RestController
public class AuroraInfoController {

    @Autowired
    private AuroraInfoService auroraInfoService;

    @Autowired
    private UploadStrategyContext uploadStrategyContext;


    @PostMapping("/report")
    public Result<?> report() {
        auroraInfoService.report();
        return Result.ok();
    }

    @ApiOperation(value = "获取系统信息")
    @GetMapping("/")
    public Result<AuroraHomeInfoDTO> getBlogHomeInfo() {
        return Result.ok(auroraInfoService.getAuroraHomeInfo());
    }

    @ApiOperation(value = "获取系统后台信息")
    @GetMapping("/admin")
    public Result<AuroraAdminInfoDTO> getBlogBackInfo() {
        return Result.ok(auroraInfoService.getAuroraAdminInfo());
    }

    @OptLog(optType = UPDATE)
    @ApiOperation(value = "更新网站配置")
    @PutMapping("/admin/website/config")
    public Result<?> updateWebsiteConfig(@Valid @RequestBody WebsiteConfigVO websiteConfigVO) {
        auroraInfoService.updateWebsiteConfig(websiteConfigVO);
        return Result.ok();
    }

    @ApiOperation(value = "获取网站配置")
    @GetMapping("/admin/website/config")
    public Result<WebsiteConfigDTO> getWebsiteConfig() {
        return Result.ok(auroraInfoService.getWebsiteConfig());
    }

    @ApiOperation(value = "查看关于我信息")
    @GetMapping("/about")
    public Result<AboutDTO> getAbout() {
        return Result.ok(auroraInfoService.getAbout());
    }

    @OptLog(optType = UPDATE)
    @ApiOperation(value = "修改关于我信息")
    @PutMapping("/admin/about")
    public Result<?> updateAbout(@Valid @RequestBody AboutVO aboutVO) {
        auroraInfoService.updateAbout(aboutVO);
        return Result.ok();
    }

    @ApiOperation(value = "上传博客配置图片")
    @ApiImplicitParam(name = "file", value = "图片", required = true, dataType = "MultipartFile")
    @PostMapping("/admin/config/images")
    public Result<String> savePhotoAlbumCover(MultipartFile file) {
        System.out.println("wokao");
        return Result.ok(uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.CONFIG.getPath()));
    }

}
