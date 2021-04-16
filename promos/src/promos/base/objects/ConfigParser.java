package promos.base.objects;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;

import java.util.Properties;
import java.io.Serializable;

/**
 *
 * <p>标题: 类 ConfigParser </p>
 * <p>描述: 配置解析类 </p>
 * <p>版权: Copyright (c) 2003-2004 </p>
 * <p>公司: 广州升域传媒有限公司 </p>
 * 保留所有权利。
 *
 * @author 胡维新
 * @version 1.0 05/27/2003
 *
 */

public class ConfigParser
    extends DefaultHandler implements Serializable{

  private Properties configPropperties;
  private String currentName;
  private StringBuffer currentValue = new StringBuffer();

  /**
   * 构建
   */
  public ConfigParser() {
    this.configPropperties = new Properties();
  }

  /**
   * 得到配置参数
   *
   * @return 配置参数
   */
  public Properties getConfigPropperties() {
    return this.configPropperties;
  }

  /**
   * 读取<XXX>中的XXX名称
   *
   * @param uri
   * @param lName
   * @param qName
   * @param attributes
   * @throws SAXException
   */
  public void startElement(String uri, String lName, String qName,
                           Attributes attributes) throws SAXException {

    currentValue.delete(0, currentValue.length());
    this.currentName = qName;

  }

  /**
   * 得到<XXX></XXX>之间的参数值
   *
   * @param ch
   * @param start
   * @param length
   * @throws SAXException
   */
  public void characters(char[] ch, int start, int length) throws SAXException {

    currentValue.append(ch, start, length);

  }

  /**
   * 保存配置的名称和参数
   *
   * @param ui
   * @param lName
   * @param qName
   * @throws SAXException
   */
  public void endElement(String ui, String lName, String qName) throws
      SAXException {

    configPropperties.put(qName, currentValue.toString().trim());

  }

}
