package promos.base.objects;

/**
 * <p>标题: CacheImpl</p>
 * <p>描述: 统一的缓存接口</p>
 * <p>版权:  Copyright (c) 2003-2004 </p>
 * <p>公司: 广州升域传媒有限公司</p>
 * 作者: 胡维新
 * 版本: 1.0
 */
public interface CacheImpl {

  /**
   * 配置项缓存类标志
   */
  public final String CONFIG_CANCHE="config_cache";

  /**
   * 数据缓存类标志
   */
  public final String DATA_CANCHE="data_cache";

  /**
   * 将对象保存到缓存中
   * @param Key String
   * @param obj Object
   */
  public void setCache(String ClassName,String Key,Object obj);

  /**
   * 得到保存的缓存对象
   * @param Key String
   */
  public Object getCache(String ClassName,String Key);

  /**
   * 销毁
   */
  public void destroy();
}
