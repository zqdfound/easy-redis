package com.zqdfound.easy.redis.redis.rateLimit;

import org.redisson.api.RateType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Redis限流注解
 * @author zhuangqingdian
 * @date 2022/8/25
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisRLimiter {
    /**
     * 资源名称，用于描述接口功能
     */
    String name() default "";

    /**
     * 限制访问次数（单位时间内产生的令牌数）
     */
    int count();

    /**
     * 时间间隔，单位秒
     */
    int period();

    /**
     * 资源 key
     */
    String key() default "";

    /**
     * 限制类型{@link LimiterTypeEnum}
     */
    LimiterTypeEnum limitType() default LimiterTypeEnum.CUSTOMER;

    /**
     * RRateLimiter 速度类型
     * OVERALL,    // 所有客户端加总限流
     * PER_CLIENT; // 每个客户端单独计算流量
     * @return
     */
    RateType mode() default RateType.PER_CLIENT;
}
