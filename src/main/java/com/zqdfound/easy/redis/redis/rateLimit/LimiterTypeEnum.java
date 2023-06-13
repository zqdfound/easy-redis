package com.zqdfound.easy.redis.redis.rateLimit;

import lombok.Getter;

/**
 * 限流类型
 * @author zhuangqingdian
 * @date 2022/8/25
 */
@Getter
public enum LimiterTypeEnum {

    /**
     * 自定义类型
     */
    CUSTOMER,
    /**
     * 根据IP地址限制
     */
    IP,
    ;
}
