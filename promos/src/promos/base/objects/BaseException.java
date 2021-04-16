package promos.base.objects;

import java.io.Serializable;
import java.rmi.ServerException;

/**
 *
 * <p>����: �� BaseException </p>
 * <p>����: �Զ����쳣�� </p>
 * <p>��Ȩ: Copyright (c) 2003-2004 </p>
 * <p>��˾: ��������ý���޹�˾ </p>
 * ��������Ȩ����
 *
 * @author ��ά��
 * @version 1.0 04/24/2003
 *
 */

public final class BaseException
    extends ServerException {

  //�쳣����
  private int errorCode = 0;

  //�쳣��Ϣ
  private String errorInfo = "";

  /**
   * �õ�������롣
   *
   * @return �������
   */
  public int getErrorCode() {
    return this.errorCode;
  }

  /**
   * �趨������롣
   *
   * @param i �������
   */
  public void setErrorCode(int i) {
    this.errorCode = i;
  }

  /**
   * �õ�������Ϣ��
   *
   * @return ������Ϣ
   */
  public String getErrorInfo() {
    return this.errorInfo;
  }

  /**
   * �趨������Ϣ��
   *
   * @param s ������Ϣ
   */
  public void setErrorCode(String s) {
    this.errorInfo = s;
  }

  public BaseException(String s) {
    super(s);
  }

  public BaseException(String s, int i) {
    super(s);
    this.errorCode = i;
  }

  public BaseException(String s, int i, String s1) {
    super(s);
    this.errorCode = i;
    this.errorInfo = s1;
  }

  public BaseException(String s, Exception ex) {
    super(s, ex);
  }
  /*
    /**
    * Ĭ�Ϲ���������
    *
     public BaseException() {
       this.errorCode = 0;
       this.errorInfo = "";
     }
     /**
     * ��׼����������ָ��������롣
     *
     * @param i �������
     *
      public BaseException(int i) {
        this.errorCode = i;
        this.errorInfo = "";
      }
      /**
      * ��׼����������ָ���������ʹ�����Ϣ��
      *
      * @param i �������
      * @param s ������Ϣ
      *
       public BaseException(int i, String s) {
         this.errorCode = i;
         this.errorInfo = s;
       }
      */

}