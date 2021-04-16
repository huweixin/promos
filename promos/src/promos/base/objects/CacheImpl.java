package promos.base.objects;

/**
 * <p>����: CacheImpl</p>
 * <p>����: ͳһ�Ļ���ӿ�</p>
 * <p>��Ȩ:  Copyright (c) 2003-2004 </p>
 * <p>��˾: ��������ý���޹�˾</p>
 * ����: ��ά��
 * �汾: 1.0
 */
public interface CacheImpl {

  /**
   * ����������־
   */
  public final String CONFIG_CANCHE="config_cache";

  /**
   * ���ݻ������־
   */
  public final String DATA_CANCHE="data_cache";

  /**
   * �����󱣴浽������
   * @param Key String
   * @param obj Object
   */
  public void setCache(String ClassName,String Key,Object obj);

  /**
   * �õ�����Ļ������
   * @param Key String
   */
  public Object getCache(String ClassName,String Key);

  /**
   * ����
   */
  public void destroy();
}
