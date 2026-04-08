package com.mgr.api.controller;

import com.mgr.api.constant.MgrConstant;
import com.mgr.api.dto.ApiMessageDto;
import com.mgr.api.dto.ErrorCode;
import com.mgr.api.dto.ResponseListDto;
import com.mgr.api.dto.product.ProductDto;
import com.mgr.api.exception.NotFoundException;
import com.mgr.api.form.product.CreateProductForm;
import com.mgr.api.form.product.UpdateProductForm;
import com.mgr.api.mapper.ProductMapper;
import com.mgr.api.model.Category;
import com.mgr.api.model.Product;
import com.mgr.api.model.Seller;
import com.mgr.api.model.criteria.ProductCriteria;
import com.mgr.api.repository.CategoryRepository;
import com.mgr.api.repository.ProductRepository;
import com.mgr.api.repository.seller.SellerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/product")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class ProductController extends ABasicController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private ProductMapper productMapper;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PRD_C')")
    @Transactional
    public ApiMessageDto<String> create(@Valid @RequestBody CreateProductForm createProductForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return makeResponse(false, null, "Invalid form", ErrorCode.ERROR_INVALID_FORM);
        }
        Long sellerId = getCurrentUser(); // Get seller from token
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new NotFoundException("Seller profile not found", ErrorCode.USER_ERROR_NOT_FOUND));

        Category category = categoryRepository.findById(createProductForm.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found", ErrorCode.CATEGORY_ERROR_NOT_FOUND));

        Product product = productMapper.fromCreateFormToEntity(createProductForm);
        product.setSeller(seller);
        product.setCategory(category);
        product.setStatus(MgrConstant.STATUS_ACTIVE);

        productRepository.save(product);
        return makeSuccessResponse(null, "Create Product success");
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PRD_U')")
    @Transactional
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateProductForm updateProductForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return makeResponse(false, null, "Invalid form", ErrorCode.ERROR_INVALID_FORM);
        }
        Product product = productRepository.findById(updateProductForm.getId())
                .orElseThrow(() -> new NotFoundException("Product not found", ErrorCode.PRODUCT_ERROR_NOT_FOUND));

        // Security check: Only seller can update their own product
        Long currentSellerId = getCurrentUser();
        if (!product.getSeller().getId().equals(currentSellerId) && !isSuperAdmin()) {
            throw new NotFoundException("Product not found", ErrorCode.PRODUCT_ERROR_NOT_FOUND);
        }

        productMapper.mappingUpdateFormToEntity(updateProductForm, product);
        if (updateProductForm.getStatus() != null) {
            product.setStatus(updateProductForm.getStatus());
        }
        
        if (updateProductForm.getCategoryId() != null) {
            Category category = categoryRepository.findById(updateProductForm.getCategoryId())
                    .orElseThrow(() -> new NotFoundException("Category not found", ErrorCode.CATEGORY_ERROR_NOT_FOUND));
            product.setCategory(category);
        }

        productRepository.save(product);
        return makeSuccessResponse(null, "Update Product success");
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ProductDto> get(@PathVariable("id") Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found", ErrorCode.PRODUCT_ERROR_NOT_FOUND));
        return makeSuccessResponse(productMapper.fromEntityToDto(product), "Get Product success");
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<ProductDto>> list(ProductCriteria productCriteria, Pageable pageable) {
        Page<Product> page = productRepository.findAll(productCriteria.getSpecification(), pageable);
        ResponseListDto<ProductDto> listDto = new ResponseListDto<ProductDto>(
                productMapper.fromEntityListToDtoList(page.getContent()),
                Long.valueOf(page.getTotalElements()),
                Integer.valueOf(page.getTotalPages())
        );
        return makeSuccessResponse(listDto, "Get list product success");
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PRD_D')")
    @Transactional
    public ApiMessageDto<String> delete(@PathVariable("id") Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found", ErrorCode.PRODUCT_ERROR_NOT_FOUND));
        
        Long currentSellerId = getCurrentUser();
        if (!product.getSeller().getId().equals(currentSellerId) && !isSuperAdmin()) {
            throw new NotFoundException("Product not found", ErrorCode.PRODUCT_ERROR_NOT_FOUND);
        }

        product.setStatus(MgrConstant.STATUS_DELETE);
        productRepository.save(product);
        return makeSuccessResponse(null, "Delete Product success");
    }
}
