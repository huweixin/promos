package promos.base.objects;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;

import java.util.Properties;
import java.io.Serializable;

/**
 *
 * <p>����: �� ConfigParser </p>
 * <p>����: ���ý����� </p>
 * <p>��Ȩ: Copyright (c) 2003-2004 </p>
 * <p>��˾: ��������ý���޹�˾ </p>
 * ��������Ȩ����
 *
 * @author ��ά��
 * @version 1.0 05/27/2003
 *
 */

public class ConfigParser
    extends DefaultHandler implements Serializable{

  private Properties configPropperties;
  private String currentName;
  private StringBuffer currentValue = new StringBuffer();

  /**
   * ����
   */
  public ConfigParser() {
    this.configPropperties = new Properties();
  }

  /**
   * �õ����ò���
   *
   * @return ���ò���
   */
  public Properties getConfigPropperties() {
    return this.configPropperties;
  }

  /**
   * ��ȡ<XXX>�е�XXX����
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
   * �õ�<XXX></XXX>֮��Ĳ���ֵ
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
   * �������õ����ƺͲ���
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
