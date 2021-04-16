package promos.design.ejb;

import java.io.Serializable;

import promos.base.objects.BaseEjbHome;
import promos.core.ejb.Core;

/**
 *
 * <p>����: �� DesignRemote </p>
 * <p>����: ��Design��EJB��Զ�̽ӿڴ����� </p>
 * <p>��Ȩ: Copyright (c) 2003-2004 </p>
 * <p>��˾: ��������ý���޹�˾ </p>
 * ��������Ȩ����
 *
 * @author ��ά��
 * @version 1.0 06/21/2004
 *
 */

public final class DesignRemote
    implements Serializable {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

/**
   * ��������ֹ����̳�
   */
  private DesignRemote() {
  }

  /**
   * �õ��̱�֪ͨ������bean��Զ�̽ӿ�
   *
   * @return Design
   */
  public static Design getDesign() {

    //����
    Design dm = null;
    try {
    	
    	dm = (Design) BaseEjbHome.getHome("java:global/promos/DesignBean!promos.design.ejb.Design", Design.class);

        if(dm == null){
          System.out.print("Now can not look up the JNDI name : "+Design.class.getName());
          return null;
        }


    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return dm;
  }
}