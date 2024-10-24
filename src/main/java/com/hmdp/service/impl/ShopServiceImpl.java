package com.hmdp.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.mapper.ShopMapper;
import com.hmdp.model.dto.Result;
import com.hmdp.model.entity.Shop;
import com.hmdp.service.IShopService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.hmdp.constants.RedisConstants.CACHE_SHOP_KEY;
import static com.hmdp.constants.RedisConstants.CACHE_SHOP_TTL;

import com.hmdp.constants.RedisConstants;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements IShopService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 根据id查询商铺数据
     *
     * @param id
     * @return
     */
    @Override
    public Result queryById(Long id) {
        String key = CACHE_SHOP_KEY + id;
        // 1、从Redis中查询店铺数据
        String shopJson = stringRedisTemplate.opsForValue().get(key);

        Shop shop = null;
        // 2、判断缓存是否命中
        if (StrUtil.isNotBlank(shopJson)) {
            // 2.1 缓存命中，直接返回店铺数据
            shop = JSONUtil.toBean(shopJson, Shop.class);
            return Result.ok(shop);
        }
        // 2.2 缓存未命中，从数据库中查询店铺数据
        shop = this.getById(id);

        // 3、判断数据库是否存在店铺数据
        if (Objects.isNull(shop)) {
            // 3.1 数据库中不存在，返回失败信息
            return Result.fail("店铺不存在");
        }
        // 3.2 数据库中存在，写入Redis，并返回店铺数据
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(shop),CACHE_SHOP_TTL, TimeUnit.MINUTES);
        return Result.ok(shop);
    }

    @Override
    @Transactional
    public Result update(Shop shop){
        Long id = shop.getId();
        if (id == null) return Result.fail("店铺id不能为空");
        //1.更新数据库
        updateById(shop);
        //2.删除缓存
        stringRedisTemplate.delete(CACHE_SHOP_KEY + shop.getId());
        return Result.ok();
    }
}
