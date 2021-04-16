package promos.base.objects;

import java.util.*;

/**
 * <p>����: DataCache������</p>
 * <p>����: �����ݽ��л������</p>
 * <p>��Ȩ:  Copyright (c) 2003-2004 </p>
 * <p>��˾: ��������ý���޹�˾</p>
 * ����: ��ά��
 * �汾: 1.0
 */
public class DataCache implements CacheImpl{

  private static DataCache _instance=null;

  private static Map ConfigMap =null;

  private DataCache() {}

  /**
   * �õ���̬���ʵ��
   *
   * @return ��Ϣ�����̬��
   */
  public synchronized static DataCache getInstance() {
    //���ʵ��
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
