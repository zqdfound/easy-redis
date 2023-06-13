package com.zqdfound.easy.redis.redis;

/**
 * @author huzj
 */
public class RedisKey {
    /**
     * 首页后缀
     */
    public static final String INDEX_SUFFIX = "_index_page:";
    /**
     * 货品编号
     */
    public static final String PRODUCT_NUMBER = "product_number:";
    /**
     * 资产编号
     */
    public static final String PROPERTY_NUMBER = "property_number:";
    /**
     * 统一订单编号
     */
    public static final String UNITE_NUMBER = "unite-number:";
    /**
     * 库存导出
     */
    public static final String PRODUCT_EXPORT = "product_export:";

    /**
     * 租户所有权限列表
     */
    public static final String TENANT_ALL_PERMISSIONS = "tenant_all_permission:";
    /**
     * 租户所有权限列表-失效时间 秒
     */
    public static final Long TENANT_ALL_PERMISSIONS_EXPIRE = 3600l;


    public static final String GOODS_DETAIL = "goods-detail:";
    public static final String GOODS_LIST = "goods-list:";
    /**
     * 供应商编号
     */
    public static final String SUPPLIER_NUMBER = "supplier_number";
    /**
     * 仓库编号
     */
    public static final String DEPOT_NUMBER = "depot_number";
    /**
     * 用户人脸状态
     */
    public static final String APP_USER_FACE = "app_user_face:";
    /**
     * 小程序APPID对应的配置信息
     */
    public static final String APP_ID_CONFIG_INFO = "app_id_config_info:";
    /**
     * 官网主域名对应的配置信息
     */
    public static final String WEB_CONFIG_INFO = "web_config_info:";
    /**
     *  用户绑定企业信息
     */
    public static final String LOGIN_USER_BIND_ENTERPRISE = "login-user-bind-enterprise:";

    /**
     * 联通城市列表
     */
    public static final String UNICOM_CITY_LIST = "unicom_city_list";
    /**
     * 联通登录token
     */
    public static final String UNICOM_API_TOKEN = "unicom_api_token";
    /**
     * 新企业认证过程
     */
    public static final String ENTERPRISE_CERTIFICATION_PROCESS_NEW = "enterprise_certification_process_new:";

    /**
     * 更新企业额度
     */
    public static final String ENTERPRISE_UPDATE_QUOTA = "enterprise_update_quota:";
    /**
     * 订单合同签署锁
     */
    public static final String ORDER_SIGN_LOCK = "order-sign-lock:";

    /**
     * 商户合同签署锁
     */
    public static final String MERCHANT_SIGN_LOCK = "order-sign-lock:";
}
