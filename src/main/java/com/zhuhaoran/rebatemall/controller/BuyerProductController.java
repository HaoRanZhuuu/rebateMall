package com.zhuhaoran.rebatemall.controller;

import com.zhuhaoran.rebatemall.dataobject.ProductCategory;
import com.zhuhaoran.rebatemall.dataobject.ProductInfo;
import com.zhuhaoran.rebatemall.enums.IdentificationStatusEnum;
import com.zhuhaoran.rebatemall.enums.ProductStatusEnum;
import com.zhuhaoran.rebatemall.enums.ResultEnum;
import com.zhuhaoran.rebatemall.exception.MallException;
import com.zhuhaoran.rebatemall.form.CategoryForm;
import com.zhuhaoran.rebatemall.form.ProductForm;
import com.zhuhaoran.rebatemall.service.ProductCategoryService;
import com.zhuhaoran.rebatemall.service.ProductInfoService;
import com.zhuhaoran.rebatemall.service.UserInfoService;
import com.zhuhaoran.rebatemall.utils.*;
import com.zhuhaoran.rebatemall.viewObject.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZhuHaoran
 * @className BuyerProductController
 * @date 2019/4/8
 * @description
 */

@RestController
@RequestMapping("/buyer/product")
@Slf4j
public class BuyerProductController {
    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * @param userId
     * @param page
     * @param size
     * @return 商品列表List
     * /list根据不同用户显示不同商品信息，可以申请条数
     */

    /**获取商品列表*/
    @CrossOrigin
    @GetMapping("/list")
    public ResultVo list(String userId,@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "20") Integer size){

        ProductVo productVo = new ProductVo();
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(userInfoService.findById(userId))){
            log.error("/list方法异常，参数userid为空");
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        productVo.setPage(page);
        productVo.setSize(size);
        productVo.setUserId(userId);

        PageRequest request = PageRequest.of(page - 1 ,size);

        Page<ProductInfo> productInfoPage = productInfoService.findUpAll(request);

        List<ProductInfo> productInfoList = productInfoPage.getContent();

        productVo.setProductInfoVoList(JudgeSpecialUserUtil.ProductInfoList2ProductInfoVoList(productInfoList,userId));

        return  ResultVoUtil.success(productVo);
    }

    /**关键词模糊搜索商品*/
    @CrossOrigin
    @GetMapping("/search")
    public ResultVo search(String userId,String keyWords,@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "20") Integer size) {
        ProductVo productVo = new ProductVo();
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(userInfoService.findById(userId))){
            log.error("/list方法异常，参数userid为空或用户不存在");
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        if (StringUtils.isEmpty(keyWords)) {
            log.error("关键词为空");
            throw new MallException(ResultEnum.KEY_WORD_IS_EMPTY);
        }
        productVo.setPage(page);
        productVo.setSize(size);
        productVo.setUserId(userId);
        PageRequest request = PageRequest.of(page - 1 ,size);
        if (StringUtils.isEmpty(productInfoService.findByLikeProductName(keyWords, request))) {
            return ResultVoUtil.success("找不到相关商品");
        }
        Page<ProductInfo> productInfoPage = productInfoService.findByLikeProductName(keyWords,request);
        List<ProductInfo> productInfoList = productInfoPage.getContent();
        productVo.setProductInfoVoList(JudgeSpecialUserUtil.ProductInfoList2ProductInfoVoList(productInfoList,userId));

        return ResultVoUtil.success(productVo);
    }

    /**商品详情界面*/
    @CrossOrigin
    @GetMapping("/detail")
    public ResultVo detail(String userId,String productId) {
        if (StringUtils.isEmpty(userId)) {
            log.error("用户名为空");
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        if (StringUtils.isEmpty(userInfoService.findById(userId))) {
            log.error("用户不存在");
            throw new MallException(ResultEnum.USER_NOT_EXIST);
        }
        if (StringUtils.isEmpty(productId) || StringUtils.isEmpty(productInfoService.findById(productId))) {
            log.error("商品id错误");
            throw new MallException(ResultEnum.PRODUCT_NOT_EXIST);
        }

        ProductDetailVo productDetailVo = new ProductDetailVo();
        ProductInfo productInfo = productInfoService.findById(productId);
        BeanUtils.copyProperties(productInfo, productDetailVo);
        if (!JudgeSpecialUserUtil.judgeSpecialUser(userId)) {
            productDetailVo.setProductDiscount(null);
        }
        return ResultVoUtil.success(productDetailVo);

    }

    /**优惠商品详情*/
    @CrossOrigin
    @PostMapping("/discountPage")
    public ResultVo discountPage (String productId , String sellerId , String userId) {
        if (StringUtils.isEmpty(productId) || StringUtils.isEmpty(sellerId)) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        if (StringUtils.isEmpty(userInfoService.findById(sellerId))) {
            throw new MallException(ResultEnum.USER_NOT_EXIST);
        }
        if (StringUtils.isEmpty(productInfoService.findById(productId))) {
            throw new MallException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (userId.isEmpty()) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }

        DebateProductDetailVo productDetailVo = new DebateProductDetailVo();
        ProductInfo productInfo = productInfoService.findById(productId);
        BeanUtils.copyProperties(productInfo, productDetailVo);
        productDetailVo.setSellerId(sellerId);
        Map map = new HashMap();
        map.put("userId", userId);
        map.put("productDetail",productDetailVo);
        return ResultVoUtil.success(map);
    }

    /**按类目展示商品列表*/
    @CrossOrigin
    @GetMapping("/listByCate")
    public ResultVo listByCate(String userId,Integer categoryId,@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "20") Integer size){
        if (StringUtils.isEmpty(userId)) {
            log.error("用户名为空");
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        if (StringUtils.isEmpty(categoryId)) {
            log.error("类目为空");
            throw new MallException(ResultEnum.CATEGORY_NOT_EXIST);
        }
        if (StringUtils.isEmpty(userInfoService.findById(userId))) {
            log.error("用户不存在");
            throw new MallException(ResultEnum.USER_NOT_EXIST);
        }
        if (StringUtils.isEmpty(productCategoryService.findById(categoryId))) {
            log.error("类目不存在");
            throw new MallException(ResultEnum.CATEGORY_NOT_EXIST);
        }
        ProductVo productVo = new ProductVo();
        productVo.setPage(page);
        productVo.setSize(size);
        productVo.setUserId(userId);
        PageRequest request = PageRequest.of(page - 1 ,size);

        Page<ProductInfo> productInfoPage = productInfoService.findByCategoryId(categoryId,request);

        List<ProductInfo> productInfoList = productInfoPage.getContent();

        productVo.setProductInfoVoList(JudgeSpecialUserUtil.ProductInfoList2ProductInfoVoList(productInfoList,userId));

        return  ResultVoUtil.success(productVo);

    }

    /**搜索Key*/
    @CrossOrigin
    @GetMapping("/searchKey")
    public ResultVo searchKey(String key,String userId) {
        if (StringUtils.isEmpty(key)) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        ResultVo resultVo = new ResultVo();
        if (ProductKeyGenerate.matchKey(key)) {
            String resultKey = ProductKeyGenerate.getProductKeyFromString(key);
            Map<Object, Object> map = redisUtils.getRedisHashMapByKey(resultKey);
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("productId", map.get("productId").toString());
            params.add("sellerId",map.get("sellerId").toString());
            params.add("userId",userId);
            resultVo = SendRequestUtils.sendPostRequest("http://127.0.0.1:8080/rebatemall/buyer/product/discountPage",params);
        } else if (InviteKeyGenerate.matchKey(key)) {
            String resultKey = InviteKeyGenerate.getInviteKeyFromString(key);
            Map<Object, Object> map = redisUtils.getRedisHashMapByKey(resultKey);
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("sellerId", map.get("sellerId").toString());
            params.add("userId",userId);
            resultVo = SendRequestUtils.sendPostRequest("http://127.0.0.1:8080/rebatemall/user/inviteBySeller", params);
        }
        return resultVo;
    }

    /**创建商品分享口令*/
    @CrossOrigin
    @GetMapping("/share")
    public ResultVo share(String productId, String userId) {
        if (productId.isEmpty() || userId.isEmpty()) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        if (StringUtils.isEmpty(productInfoService.findById(productId)) || StringUtils.isEmpty(userInfoService.findById(userId))) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        if (userInfoService.findById(userId).getIsSpecial() == IdentificationStatusEnum.NOT_IDENTIFICATION.getCode()) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("productId", productId);
        map.put("sellerId", userId);
        String key = ProductKeyGenerate.getProductKey();
        redisUtils.setRedisByHashMap(key, map);
        ProductInfo productInfo = productInfoService.findById(productId);
        BigDecimal price = productInfo.getProductPrice().subtract(productInfo.getProductDiscount());
        String shareWord = productInfo.getProductName()+" 劲爆优惠 仅售"+price.toString()+"口令："+key;
        Map result = new HashMap();
        result.put("shareWord", shareWord);
        return ResultVoUtil.success(result);
    }

    @CrossOrigin
    @GetMapping("/cateList")
    public ResultVo cateList () {
        List<ProductCategory> categoryList = productCategoryService.findAll();
        List<Cate1stVo> cate1stVoList = new ArrayList<>();
        for (ProductCategory category1st : categoryList) {
            if (category1st.getCategoryGrade() == 1) {
                Cate1stVo cate1stVo = new Cate1stVo();
                BeanUtils.copyProperties(category1st, cate1stVo);
                List<Cate2edVo> cate2edVoList = new ArrayList<>();
                for (ProductCategory category2ed : categoryList) {
                    if (category2ed.getParentId() == cate1stVo.getCategoryId()&&category2ed.getCategoryGrade()==2) {
                        Cate2edVo cate2edVo = new Cate2edVo();
                        BeanUtils.copyProperties(category2ed, cate2edVo);
                        List<Cate3thVo> cate3thVoList = new ArrayList<>();
                        for (ProductCategory category3th : categoryList) {
                            if (category3th.getParentId() == cate2edVo.getCategoryId() && category3th.getCategoryGrade() == 3) {
                                Cate3thVo cate3thVo = new Cate3thVo();
                                BeanUtils.copyProperties(category3th, cate3thVo);
                                cate3thVoList.add(cate3thVo);
                            }
                        }
                        cate2edVo.setCate3thVoList(cate3thVoList);
                        cate2edVoList.add(cate2edVo);
                    }

                }
                cate1stVo.setCate2edVoList(cate2edVoList);
                cate1stVoList.add(cate1stVo);
            }
        }
        CategoryVo categoryVo = new CategoryVo();
        categoryVo.setCate1stVoList(cate1stVoList);
        return ResultVoUtil.success(categoryVo);
    }

    @CrossOrigin
    @GetMapping("/cateAdd")
    public ResultVo cateAdd(CategoryForm categoryForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        ProductCategory category = new ProductCategory();
        BeanUtils.copyProperties(categoryForm, category);
        category.setCategoryId(IdKeyGenerate.getCateId());
        productCategoryService.save(category);
        return ResultVoUtil.success();
    }

    @CrossOrigin
    @DeleteMapping("/cateDelete")
    public ResultVo cateDelete(Integer categoryId) {
        productCategoryService.deleteById(categoryId);
        return ResultVoUtil.success();
    }

    @CrossOrigin
    @PutMapping("/cateAlter")
    public ResultVo cateAlter(Integer categoryId, CategoryForm categoryForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        ProductCategory category = productCategoryService.findById(categoryId);
        BeanUtils.copyProperties(categoryForm, category);
        productCategoryService.save(category);
        return ResultVoUtil.success();
    }

    @CrossOrigin
    @PostMapping("/add")
    public ResultVo add(@Validated ProductForm productForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        ProductInfo productInfo = new ProductInfo();
        BeanUtils.copyProperties(productForm, productInfo);
        productInfo.setProductId(IdKeyGenerate.getIdKey());
        productInfoService.save(productInfo);
        return ResultVoUtil.success();
    }

    @CrossOrigin
    @PutMapping("/change")
    public ResultVo change(String productId) {
        if (StringUtils.isEmpty(productId)) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        ProductInfo productInfo = productInfoService.findById(productId);
        if (productInfo.getProductStatus() == ProductStatusEnum.UP.getCode()) {
            productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
            productInfoService.save(productInfo);
        } else {
            productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
            productInfoService.save(productInfo);
        }
        return ResultVoUtil.success();
    }

    @CrossOrigin
    @PutMapping("/alter")
    public ResultVo alter(String productId, @Validated ProductForm productForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        if (StringUtils.isEmpty(productId)) {
            throw new MallException(ResultEnum.VARIABLE_ERROR);
        }
        ProductInfo productInfo = productInfoService.findById(productId);
        BeanUtils.copyProperties(productForm, productInfo);
        productInfoService.save(productInfo);
        return ResultVoUtil.success();
    }

    @CrossOrigin
    @DeleteMapping("/delete")
    public ResultVo delete(String productId) {
        productInfoService.deleteById(productId);
        return ResultVoUtil.success();
    }
    
}
