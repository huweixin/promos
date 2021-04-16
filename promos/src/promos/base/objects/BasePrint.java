package promos.base.objects;

import java.io.Serializable;

/**
 *
 * <p>����: �� BaseOuter </p>
 * <p>����: ��Ϣ�����̬�� </p>
 * <p>��Ȩ: Copyright (c) 2003-2004 </p>
 * <p>��˾: ��������ý���޹�˾ </p>
 * ��������Ȩ����
 *
 * @author ��ά��
 * @version 1.0 09/24/2003
 *
 */


public class BasePrint implements Serializable {

  //Ψһ���ڵ�ʵ��
  private static BasePrint _instance = null;

  //�Ƿ�ر����е���ʾ��Ϣ
  private static boolean isShowMessage = false;

  /**
   * ��������ֹ����̳�
   */
  private BasePrint() {
  }

  /**
   * �õ���̬���ʵ��
   *
   * @return ��Ϣ�����̬��
   */
  public static BasePrint getInstance() {

    //���ʵ��
    if (_instance == null) {
      _instance = new BasePrint();

	  //System.out.println("show_message:"+(BaseConfig.getIntOfSettingByName("show_message")));
      //��ȡ��ʾ��Ϣ��־
      if((BaseConfig.getIntOfSettingByName("show_message")) == 1){
         isShowMessage = true;
      }

    }

    return _instance;

  }

  /**
   * �����Ϣ������̨
   *
   * @param strMessage �����Ϣ
   */
  public static void println(String strMessage){
	  
	if (isShowMessage==false) return;

    //��ʾ�����Ϣ
    if(strMessage != null){
    	System.out.println(strMessage);
    }
  }
  
  
  /**
   * �����Ϣ������̨
   *
   * @param strNote ��¼��Ϣ
   * @param strMessage �����Ϣ
   * @param isBR �Ƿ���
   */
  public static void print(String strNote,String strMessage,boolean isBR){

    //��ʾ��¼��Ϣ
    if(strNote != null){
      System.out.print(strNote);
    }

    //��ʾ�����Ϣ
    if(strMessage != null){

      if(isBR){
        BasePrint.println(strMessage);
      }
      else{
        BasePrint.println(strMessage);
      }

    }
  }




}