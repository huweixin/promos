package promos.base.objects;

import java.util.*;

/**
 * <p>标题: DataCache缓存类</p>
 * <p>描述: 对数据进行缓存操作</p>
 * <p>版权:  Copyright (c) 2003-2004 </p>
 * <p>公司: 广州升域传媒有限公司</p>
 * 作者: 胡维新
 * 版本: 1.0
 */
public class DataCache implements CacheImpl{

  private static DataCache _instance=null;

  private static Map ConfigMap =null;

  private DataCache() {}

  /**
   * 得到静态类的实例
   *
   * @return 信息输出静态类
   */
  public synchronized static DataCache getInstance() {
    //检测实例
    if (_instance == null) {
      _instance = new DataCache();
      ConfigMap=new HashMap();
    }
    return _instance;
  }


  /**
   * setCache
   *
   * @param Key String
   * @param obj Object
   */
  public void setCache(String ClassName,String key, Object obj) {
    ConfigMap.put(ClassName+key,obj);
  }

  /**
   * getCache
   *
   * @param Key String
   */
  public Object getCache(String ClassName,String key) {
    return ConfigMap.get(ClassName+key);
  }

  /**
   * destroy
   */
  public void destroy() {
    _instance=null;
  }
}
