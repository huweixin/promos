package promos.base.objects;

/**
 * <p>标题: CacheFactory工厂类</p>
 * <p>描述: 缓存操作类的实现工厂</p>
 * <p>版权:  Copyright (c) 2003-2004 </p>
 * <p>公司: 广州升域传媒有限公司</p>
 * 作者: 胡维新
 * 版本: 1.0
 */
public class CacheFactory {
  private CacheFactory() {
  }

  /**
   * 得到静态类的实例
   *
   * @return 信息输出静态类
   */
  public synchronized static CacheImpl getInstance(String type) {

    //ConfigCache缓存类
    if (type.equalsIgnoreCase(CacheImpl.CONFIG_CANCHE)) {
      return ConfigCache.getInstance();
    }

    //DataCache缓存类
    if (type.equalsIgnoreCase(CacheImpl.DATA_CANCHE)) {
      return DataCache.getInstance();
    }

    return null;
  }

}
