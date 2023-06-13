package com.zqdfound.easy.redis.redis.rateLimit;

import cn.hutool.extra.servlet.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @Author: zhuangqingdian
 * @Date:2023/5/22
 */
@Aspect
@Component
@Slf4j
public class RedisRLimiterAspect {
    static {
        log.info("加载redission分布式限流");
    }

    private static final String REDIS_LIMIT_KEY_HEAD = "app-request-limit:";
    private RedissonClient redisson;

    public RedisRLimiterAspect(RedissonClient redissonClient) {
        this.redisson = redissonClient;
    }

    // 切入点
    @Pointcut("@annotation(com.zqdfound.easy.redis.redis.rateLimit.RedisRLimiter)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object limitCheck(ProceedingJoinPoint point) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        RedisRLimiter limit = method.getAnnotation(RedisRLimiter.class);
        String ip = "";
        // 默认采用方法名
        String key = StringUtils.upperCase(method.getName());
        switch (limit.limitType()) {
            case IP:
                // ip类型
                ip = ServletUtil.getClientIP(request, null);
                break;
            case CUSTOMER:
                // 传统类型，采用注解提供key
                key = limit.key();
                break;
        }
        // 生成redis key
        final String ofRateLimiter = REDIS_LIMIT_KEY_HEAD + key + ":" + ip;
        RRateLimiter rateLimiter = redisson.getRateLimiter(ofRateLimiter);


        // 设置访问速率，
        // RateType.OVERALL所有实例共享、RateType.CLIENT单实例端共享,count为访问数，period为单位时间，var6为时间单位
        // 创建令牌桶数据模型
        rateLimiter.trySetRate(RateType.OVERALL, limit.count(), limit.period(), RateIntervalUnit.SECONDS);
        //拿不到令牌
        if (!rateLimiter.tryAcquire(1)) {
            log.error("IP【{}】访问接口【{}】超出频率限制，限制规则为[限流模式：{}; 限流数量：{}; 限流时间间隔：{};]",
                    ip, method.getName(), limit.mode().toString(), limit.count(), limit.period());
            //RuntimeException exception = new RuntimeException();
            throw new Exception("访问太过频繁");
        }
        // permits 允许获得的许可数量 (如果获取失败，返回false) 1秒内不能获取到1个令牌，则返回，不阻塞
        // 尝试访问数据，占数据计算值var1，设置等待时间var3
        // acquire() 默认如下参数 如果超时时间为-1，则永不超时，则将线程阻塞，直至令牌补充
        // 此处采用3秒超时方式，服务降级
//        if (!rateLimiter.tryAcquire(1, 2, TimeUnit.SECONDS)) {
//            log.error("IP【{}】访问接口【{}】超出频率限制，限制规则为[限流模式：{}; 限流数量：{}; 限流时间间隔：{};]",
//                    ip, method.getName(), limit.mode().toString(), limit.count(), limit.period());
//            throw new RuntimeException("访问超出频率限制，请稍后重试");
//        }
        return point.proceed();
    }
}