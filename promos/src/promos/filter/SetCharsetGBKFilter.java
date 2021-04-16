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
 * <p>标题: 类 SetCharsetGBKFilter </p>
 * <p>描述: 设置编码过滤类 </p>
 * <p>版权: Copyright (c) 2003-2004 </p>
 * <p>公司: 广州升域传媒有限公司 </p>
 * 保留所有权利。
 *
 * @author 胡维新
 * @version 1.0 11/20/2003
 *
 */

public class SetCharsetGBKFilter
    implements Filter {

  //过滤器配置对象
  private FilterConfig config = null;

  /**
   * 在过滤器执行service前被调用，设置过滤器配置对象
   *
   * @param parm1 过滤器配置对象
   * @throws javax.servlet.ServletException
   */
  public void init(FilterConfig parm1) throws javax.servlet.ServletException {
    this.config = parm1;
  }

  /**
   * 执行过滤，设置编码为GBK。
   *
   * @param parm1 请求
   * @param parm2 响应
   * @param parm3 提供后继过滤器调用信息
   * @throws java.io.IOException
   * @throws javax.servlet.ServletException
   */
  public void doFilter(ServletRequest parm1, ServletResponse parm2,
                       FilterChain parm3) throws java.io.IOException,
      javax.servlet.ServletException {

    //设置
    parm1.setCharacterEncoding("GBK");

    //传递请求
    parm3.doFilter(parm1, parm2);

  }

  /**
   * 在过滤器执行service后被调用
   *
   */
  public void destroy() {
    this.config = null;
  }
}