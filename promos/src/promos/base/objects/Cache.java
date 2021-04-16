package promos.base.objects;

import java.util.*;

/**
 * <p>����: Cache������</p>
 * <p>����: �����ݽ��л������</p>
 * <p>��Ȩ:  Copyright (c) 2003-2004 </p>
 * <p>��˾: ��������ý���޹�˾</p>
 * ����: ��ά��
 * �汾: 1.0
 */
public class Cache implements CacheImpl{

  private static Cache _instance=null;

  private static Map ConfigMap =null;

  private Cache() {}

  /**
   * �õ���̬���ʵ��
   *
   * @return ��Ϣ�����̬��
   */
  public synchronized static Cache getInstance() {
    //���ʵ��
    if (_instance == null) {
      _instance = new Cache();
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
   ConfigMap.clear();
    _instance=null;
  }
}
