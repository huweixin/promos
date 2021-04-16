package promos.base.objects;

/**
 * <p>����: CacheFactory������</p>
 * <p>����: ����������ʵ�ֹ���</p>
 * <p>��Ȩ:  Copyright (c) 2003-2004 </p>
 * <p>��˾: ��������ý���޹�˾</p>
 * ����: ��ά��
 * �汾: 1.0
 */
public class CacheFactory {
  private CacheFactory() {
  }

  /**
   * �õ���̬���ʵ��
   *
   * @return ��Ϣ�����̬��
   */
  public synchronized static CacheImpl getInstance(String type) {

    //ConfigCache������
    if (type.equalsIgnoreCase(CacheImpl.CONFIG_CANCHE)) {
      return ConfigCache.getInstance();
    }

    //DataCache������
    if (type.equalsIgnoreCase(CacheImpl.DATA_CANCHE)) {
      return DataCache.getInstance();
    }

    return null;
  }

}
