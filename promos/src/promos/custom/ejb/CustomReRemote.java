package promos.custom.ejb;

import java.io.Serializable;

import promos.base.ejb.Base;
import promos.base.objects.BaseEjbHome;
import promos.core.ejb.Core;

/**
 *
 * <p>����: �� MainRemote </p>
 * <p>����: ��Main��EJB��Զ�̽ӿڴ����� </p>
 * <p>��Ȩ: Copyright (c) 2003-2004 </p>
 * <p>��˾: ��������ý���޹�˾ </p>
 * ��������Ȩ����
 *
 * @author ��ά��
 * @version 1.0 06/21/2004
 *
 */

public final class CustomReRemote
    implements Serializable {

  /**
   * ��������ֹ����̳�
   */
  private CustomReRemote() {
  }

  /**
   * �õ��̱�֪ͨ������bean��Զ�̽ӿ�
   *
   * @return Main
   */
  public static CustomRe getCustomRe() {

    //����
	  CustomRe dm = null;
    try {
    	dm = (CustomRe) BaseEjbHome.getHome("java:global/promos/CustomReBean!promos.custom.ejb.CustomRe", CustomRe.class);

        if(dm == null){
          System.out.print("Now can not look up the JNDI name : "+CustomRe.class.getName());
          return null;
        }
    	
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return dm;
  }
}