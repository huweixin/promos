package promos.filter;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;
import java.io.IOException;

/**
 *
 * <p>����: �� SetCharsetGBKFilter </p>
 * <p>����: ���ñ�������� </p>
 * <p>��Ȩ: Copyright (c) 2003-2004 </p>
 * <p>��˾: ��������ý���޹�˾ </p>
 * ��������Ȩ����
 *
 * @author ��ά��
 * @version 1.0 11/20/2003
 *
 */

public class SetCharsetGBKFilter
    implements Filter {

  //���������ö���
  private FilterConfig config = null;

  /**
   * �ڹ�����ִ��serviceǰ�����ã����ù��������ö���
   *
   * @param parm1 ���������ö���
   * @throws javax.servlet.ServletException
   */
  public void init(FilterConfig parm1) throws javax.servlet.ServletException {
    this.config = parm1;
  }

  /**
   * ִ�й��ˣ����ñ���ΪGBK��
   *
   * @param parm1 ����
   * @param parm2 ��Ӧ
   * @param parm3 �ṩ��̹�����������Ϣ
   * @throws java.io.IOException
   * @throws javax.servlet.ServletException
   */
  public void doFilter(ServletRequest parm1, ServletResponse parm2,
                       FilterChain parm3) throws java.io.IOException,
      javax.servlet.ServletException {

    //����
    parm1.setCharacterEncoding("GBK");

    //��������
    parm3.doFilter(parm1, parm2);

  }

  /**
   * �ڹ�����ִ��service�󱻵���
   *
   */
  public void destroy() {
    this.config = null;
  }
}