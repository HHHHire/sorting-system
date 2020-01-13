package cn.edu.jxust.sort.controller.api;


import cn.edu.jxust.sort.entity.po.Category;
import cn.edu.jxust.sort.entity.vo.CategoryVO;
import cn.edu.jxust.sort.service.CategoryService;
import cn.edu.jxust.sort.util.ResponseUtil;
import cn.edu.jxust.sort.util.TokenUtil;
import cn.edu.jxust.sort.util.annotations.RequiredPermission;
import cn.edu.jxust.sort.util.common.Const;
import cn.edu.jxust.sort.util.enums.ResponseStatus;
import cn.edu.jxust.sort.util.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

/**
 * @author: ddh
 * @data: 2020/1/8 15:58
 * @description
 **/
@RestController
@RequestMapping("/api/category")
public class CategoryController extends BaseController {
    private final TokenUtil tokenUtil;
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(TokenUtil tokenUtil, CategoryService categoryService) {
        this.tokenUtil = tokenUtil;
        this.categoryService = categoryService;
    }

    /**
     * 分页获取分类
     *
     * @param token    用户 token
     * @param deviceId 设备 id
     * @param page     页数
     * @param size     页面大小
     * @return Response
     */
    @RequiredPermission
    @GetMapping
    public Response getCategoryByDeviceId(@RequestHeader("token") String token,
                                          @RequestParam(value = "deviceId", required = false) String deviceId,
                                          @RequestParam(value = "page", defaultValue = Const.DEFAULT_PAGE_NUMBER, required = false) Integer page,
                                          @RequestParam(value = "size", defaultValue = Const.DEFAULT_PAGE_SIZE, required = false) Integer size) {
        String enterpriseId = tokenUtil.getClaim(token, "enterpriseId").asString();
        Page<Category> categories = categoryService.getCategory(enterpriseId, deviceId, PageRequest.of(page, size));
        if (categories != null) {
            return ResponseUtil.responseWithData(ResponseStatus.SUCCESS,
                    categories.map(c -> CategoryVO.builder()
                            .categoryId(c.getCategoryId())
                            .categoryName(c.getCategoryName())
                            .length(c.getCLength())
                            .lengthTolerance(c.getLengthTolerance())
                            .weight(c.getWeight())
                            .weightTolerance(c.getWeightTolerance()).build()));
        } else {
            return ResponseUtil.responseWithoutData(ResponseStatus.NOT_FOUND);
        }
    }

    /**
     * 通过分类编号获取分类信息
     *
     * @param token      用户 token
     * @param categoryId 分类编号
     * @return Response
     */
    @GetMapping("/{categoryId}")
    public Response getCategoryById(@RequestHeader("token") String token,
                                    @PathVariable String categoryId) {
        String enterpriseId = tokenUtil.getClaim(token, "enterpriseId").asString();
        Category category = categoryService.getCategoryById(enterpriseId, categoryId);
        if (category != null) {
            return ResponseUtil.responseWithData(ResponseStatus.SUCCESS, categoryId);
        } else {
            return ResponseUtil.responseWithoutData(ResponseStatus.NOT_FOUND);
        }
    }
}
