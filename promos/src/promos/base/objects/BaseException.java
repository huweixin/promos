package promos.base.objects;

import java.io.Serializable;
import java.rmi.ServerException;

/**
 *
 * <p>标题: 类 BaseException </p>
 * <p>描述: 自定义异常类 </p>
 * <p>版权: Copyright (c) 2003-2004 </p>
 * <p>公司: 广州升域传媒有限公司 </p>
 * 保留所有权利。
 *
 * @author 胡维新
 * @version 1.0 04/24/2003
 *
 */

public final class BaseException
    extends ServerException {

  //异常代码
  private int errorCode = 0;

  //异常信息
  private String errorInfo = "";

  /**
   * 得到错误代码。
   *
   * @return 错误代码
   */
  public int getErrorCode() {
    return this.errorCode;
  }

  /**
   * 设定错误代码。
   *
   * @param i 错误代码
   */
  public void setErrorCode(int i) {
    this.errorCode = i;
  }

  /**
   * 得到错误信息。
   *
   * @return 错误信息
   */
  public String getErrorInfo() {
    return this.errorInfo;
  }

  /**
   * 设定错误信息。
   *
   * @param s 错误信息
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
    * 默认构建方法。
    *
     public BaseException() {
       this.errorCode = 0;
       this.errorInfo = "";
     }
     /**
     * 标准构建方法，指定错误代码。
     *
     * @param i 错误代码
     *
      public BaseException(int i) {
        this.errorCode = i;
        this.errorInfo = "";
      }
      /**
      * 标准构建方法，指定错误代码和错误信息。
      *
      * @param i 错误代码
      * @param s 错误信息
      *
       public BaseException(int i, String s) {
         this.errorCode = i;
         this.errorInfo = s;
       }
      */

}