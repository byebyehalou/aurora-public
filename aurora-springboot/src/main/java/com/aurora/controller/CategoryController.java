package com.aurora.controller;

import com.aurora.annotation.OptLog;
import com.aurora.dto.CategoryAdminDTO;
import com.aurora.dto.CategoryDTO;
import com.aurora.dto.CategoryOptionDTO;
import com.aurora.service.CategoryService;
import com.aurora.vo.CategoryVO;
import com.aurora.vo.ConditionVO;
import com.aurora.vo.PageResult;
import com.aurora.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.aurora.constant.OptTypeConst.*;

@Api(tags = "分类模块")
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation("获取所有分类")
    @GetMapping("/categories/all")
    public Result<List<CategoryDTO>> listCategories() {
        return Result.ok(categoryService.listCategories());
    }

    @ApiOperation(value = "查看后台分类列表")
    @GetMapping("/admin/categories")
    public Result<PageResult<CategoryAdminDTO>> listCategoriesAdmin(ConditionVO conditionVO) {
        return Result.ok(categoryService.listCategoriesAdmin(conditionVO));
    }

    @ApiOperation(value = "搜索文章分类")
    @GetMapping("/admin/categories/search")
    public Result<List<CategoryOptionDTO>> listCategoriesAdminBySearch(ConditionVO conditionVO) {
        return Result.ok(categoryService.listCategoriesBySearch(conditionVO));
    }

    @OptLog(optType = DELETE)
    @ApiOperation(value = "删除分类")
    @DeleteMapping("/admin/categories")
    public Result<?> deleteCategories(@RequestBody List<Integer> categoryIds) {
        categoryService.deleteCategories(categoryIds);
        return Result.ok();
    }

    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "添加或修改分类")
    @PostMapping("/admin/categories")
    public Result<?> saveOrUpdateCategory(@Valid @RequestBody CategoryVO categoryVO) {
        categoryService.saveOrUpdateCategory(categoryVO);
        return Result.ok();
    }


}
