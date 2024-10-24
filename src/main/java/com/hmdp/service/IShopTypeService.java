package com.hmdp.service;

import com.hmdp.model.dto.Result;
import com.hmdp.model.entity.ShopType;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
public interface IShopTypeService extends IService<ShopType> {

    /**
     * 查询店铺的类型
     * @return
     */
    Result queryTypeList();

}
