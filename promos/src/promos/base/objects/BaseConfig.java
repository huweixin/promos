package promos.base.objects;

import java.util.Properties;
import java.util.ArrayList;
import java.net.MalformedURLException;
import java.net.URL;
//import java.net.URLConnection;
import java.io.Serializable;
//import java.rmi.RemoteException;

//import javax.naming.NamingException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.io.File;

/**
 *
 * <p>����: �� BaseConfig </p>
 * <p>����: �������þ�̬�� </p>
 * <p>��Ȩ: Copyright (c) 2003-2004 </p>
 * <p>��˾: ��������ý���޹�˾ </p>
 * ��������Ȩ����
 *
 * @author ��ά��
 * @version 1.0 05/27/2003
 *
 */

public final class BaseConfig
    implements Serializable {

  //ָ��վ��Ŀ¼
  private ArrayList website;

  //ָ�������ļ�
  private ArrayList file;

  //ָ�������ļ��еĲ���
  private ArrayList config;

  //��ǰ�������Ƿ���JBOSS
  private boolean JBOSS;

  //Ψһ�Ĳ������þ�̬��ʵ��
  private static BaseConfig _instance = null;

  private static URL url = null;

  //�������ʵ��
  public static void ClearInstance() {
    _instance = null;
  }

  /**
   * ��������ֹ����̳�
   */
  private BaseConfig() {
    this.website = new ArrayList();
    this.file = new ArrayList();
    this.config = new ArrayList();

    //this.WEBSITE = new String("");
    this.JBOSS = false;
  }

  /**
   * �õ������ļ��йؼ��ֵ�Ĭ���б�
   *
   * @param strWebsite վ������
   * @param strConfigFile �����ļ�����
   * @param strName �����ļ��йؼ��ֵ�����
   * @return �ַ����б�
   */
  public static ArrayList getDefaultValueEX(String strWebsite,
      String strConfigFile, String strName) {

    Properties p = BaseConfig.getProperties(strWebsite, strConfigFile);

    if (p == null) {
      return new ArrayList();
    }

    int pSize = p.size();
    int j = 1;

    boolean b = true;
    ArrayList alDL = new ArrayList();
    alDL.add("");

    do {

      //
      String strDVL = strName + j;
      String strDVLValue = p.getProperty(strDVL);

      if (strDVLValue == null || strDVLValue.compareTo("") == 0) {
        b = false;
      }
      else {
        //����б�
        alDL.add(strDVLValue);
      }

      j++;

    }
    while (b);

    return alDL;
  }

  /**
   * �õ������ļ��йؼ��ֵ�Ĭ���б�
   *
   * @param strWebsite վ������
   * @param strConfigFile �����ļ�����
   * @param strName �����ļ��йؼ��ֵ�����
   * @return �ַ����б�
   */
  public static ArrayList getDefaultValue(String strWebsite,
      String strConfigFile, String strName) {

    Properties p = BaseConfig.getProperties(strWebsite, strConfigFile);

    if (p == null) {
      return new ArrayList();
    }

    int pSize = p.size();
    int j = 1;

    boolean b = true;
    ArrayList alDL = new ArrayList();

    do {

      //
      String strDVL = strName + j;
      String strDVLValue = p.getProperty(strDVL);

      if (strDVLValue == null || strDVLValue.compareTo("") == 0) {
        b = false;
      }
      else {
        //����б�
        alDL.add(strDVLValue);
      }

      j++;

    }
    while (b);

    return alDL;
  }

  /**
   * ����վ�����ƺ������ļ����Ƶõ��������ļ������ݡ�
   *
   * @param strWebsite վ������
   * @param strConfigFile �����ļ�����
   * @return �����ļ�����
   */
  public static Properties getProperties(String strWebsite,
      String strConfigFile) {

    BaseConfig obj = BaseConfig.getInstance();

    //�õ����õ��趨ֵ
    int i = 0;
    int website = -1;
    int file = -1;

    //��⵱ǰվ�������Ƿ����
    if (obj.website == null || obj.website.size() == 0) {
      return null;
    }
    int websiteSize = obj.website.size();

    // BasePrint.println("վ��������"+websiteSize);

    for (i = 0; i < websiteSize; ) {
      //�õ�վ������
      String strWS = (String) (obj.website.get(i));
      if (strWebsite.compareTo(strWS) == 0) {
        website = i;
        break;
      }

      i++;
    }
    //BasePrint.println("��ǰվ�㣺"+website);

    //����Ҳ���վ�����ƣ�ֱ�ӷ���
    if (website == -1) {
      return null;
    }

    //��⵱ǰ�����ļ��Ƿ����
    if (obj.file == null || obj.file.size() == 0) {
      return null;
    }
    int fileSize = obj.file.size();

    //BasePrint.println("�ļ�������"+fileSize);

    for (i = 0; i < fileSize; ) {
      //�õ������ļ�����
      String strFL = (String) (obj.file.get(i));
      if (strConfigFile.compareTo(strFL) == 0) {
        file = i;
        break;
      }

      i++;

    }

    //ȥ����һ�������ļ�
    file = file - 1;

    //BasePrint.println("��ǰ�ļ���"+file);

    //����Ҳ��������ļ����ƣ�ֱ�ӷ���
    if (file == -1) {
      return null;
    }

    //��ȡ���ʵ������ļ��б�
    ArrayList alConfig = (ArrayList) (obj.config.get(website + 1));

    //���б��ж�ȡ���ʵĲ���
    Properties np = (Properties) (alConfig.get(file));

    return np;

  }

  /**
   * ��ȡ���е������ļ��������������þ�̬���ʵ����
   *
   * @return �������þ�̬��
   */
  public static BaseConfig getInstance() {
    return _instance;
  }

  /**
   * ��ȡ���е������ļ��������������þ�̬���ʵ����
   *
   * @return �������þ�̬��
   */
  public static BaseConfig getInstance(String pa) {
    if (_instance == null) {
      _instance = new BaseConfig();
      String path = pa;
      int intIndex = pa.lastIndexOf(File.separator);
      pa = pa.substring(0, intIndex + 1);
      //��ȡ��������
      try {

        //���Ƚ��������������ļ��е���Ϣ
        Properties bp = _instance.parse(path);

        //�������������Ϣ
        //�����ļ�����
        _instance.file.add("promos");

        //����������Ϣ
        ArrayList alBC = new ArrayList();
        alBC.add(bp);
        _instance.config.add(alBC);

        //�õ���ǰ�������Ƿ���JBOSS
        String servername = (bp.getProperty("server_name"));
        if (servername == null) {
          servername = "";
        }
        servername = servername.toLowerCase().trim();
        if (servername.compareTo("jboss") == 0) {
          _instance.JBOSS = true;
        }


     }
      catch (Exception e) {
        e.printStackTrace();
      }

    }

    return _instance;

  }

  /**
   * ���������ļ�promosenv.xml�ҵ����е�������Ϣ��
   *
   * <ul>
   * <font color="#ff0000"><b>����������վ���һ�α�����ʱ���á�</b></font>
   * </ul>
   *
   * @param url �����ļ���URL��ַ
   * @return �õ�������Ϣ
   */
  public static BaseConfig getInstance(URL url) {

    //���ʵ��
    if (_instance == null) {
      _instance = new BaseConfig();
      BaseConfig.url = url;
      System.out.println(" ���� �ļ�  ���� " + BaseConfig.url);
      //��ȡ��������
      try {

        //���Ƚ��������������ļ��е���Ϣ
        String strFilePath = url.toString();

        System.out.println(" ���� �ļ�  ���� " + strFilePath);

        Properties bp = _instance.parse(strFilePath);

        //�������������Ϣ
        //�����ļ�����
        _instance.file.add("promos");

        //����������Ϣ
        ArrayList alBC = new ArrayList();
        alBC.add(bp);
        _instance.config.add(alBC);



        //�õ���ǰ�������Ƿ���JBOSS
        String servername = (bp.getProperty("server_name"));
        if (servername == null) {
          servername = "";
        }
        servername = servername.toLowerCase().trim();
        if (servername.compareTo("jboss") == 0) {
          _instance.JBOSS = true;
        }

      }
      catch (Exception e) {
        e.printStackTrace();
      }

    }

    return _instance;

  }

  /**
   * ���ݲ������Ƶõ����������趨��
   *
   * @param s ��������
   * @return ���õ��趨ֵ
   */
  public static String getSettingByName(String s) {

    //�õ�����������ʵ��
    BaseConfig obj = BaseConfig.getInstance();

    //����Ƿ���������ļ�
    if (obj.file == null || obj.file.size() == 0) {
      return "";
    }

    //�ⶨ�����Ƿ��ǻ��������ļ�����
    String strBase = (String) (obj.file.get(0));
    if (strBase.compareTo("promos") != 0) {
      return "";
    }

    //�õ��б�
    ArrayList alBase = (ArrayList) (obj.config.get(0));
    Properties bp = (Properties) (alBase.get(0));

    //�õ����õ��趨ֵ
    String rs = bp.getProperty(s);
    if (rs == null) {
      return "";
    }
    return rs;
  }

  /**
   * ���ݲ������Ƶõ����������趨��
   *
   * @param strWebsite ��ǰվ������
   * @param strFileName �����ļ�����
   * @param strParameter ��������
   * @return �������ļ���ָ���������趨ֵ
   */
  public static String getSettingByName(String strWebsite, String strFileName,
      String strParameter) {

    //�õ�����������ʵ��
    BaseConfig obj = BaseConfig.getInstance();

    //�õ����õ��趨ֵ
    int i = 0;
    int website = -1;
    int file = -1;

    //��⵱ǰվ�������Ƿ����
    if (obj.website == null || obj.website.size() == 0) {
      return "";
    }
    int websiteSize = obj.website.size();

    //BasePrint.println("վ��������"+websiteSize);

    for (i = 0; i < websiteSize; ) {
      //�õ�վ������
      String strWS = (String) (obj.website.get(i));
      if (strWebsite.compareTo(strWS) == 0) {
        website = i;
        break;
      }

      i++;
    }
    //BasePrint.println("��ǰվ�㣺"+website);

    //����Ҳ���վ�����ƣ�ֱ�ӷ���
    if (website == -1) {
      return "";
    }

    //��⵱ǰ�����ļ��Ƿ����
    if (obj.file == null || obj.file.size() == 0) {
      return "";
    }
    int fileSize = obj.file.size();

    //BasePrint.println("�ļ�������"+fileSize);

    for (i = 0; i < fileSize; ) {
      //�õ������ļ�����
      String strFL = (String) (obj.file.get(i));
      if (strFileName.compareTo(strFL) == 0) {
        file = i;
        break;
      }

      i++;

    }

    //ȥ����һ�������ļ�
    file = file - 1;

    //BasePrint.println("��ǰ�ļ���"+file);

    //����Ҳ��������ļ����ƣ�ֱ�ӷ���
    if (file == -1) {
      return "";
    }

    //��ȡ���ʵ������ļ��б�
    ArrayList alConfig = (ArrayList) (obj.config.get(website + 1));

    //���б��ж�ȡ���ʵĲ���
    Properties np = (Properties) (alConfig.get(file));

    //Ȼ���ȡ����
    String rs = np.getProperty(strParameter);
    if (rs == null) {
      return "";
    }

    //��������Ϣת����Сдģʽ������Ҫ(����)��
    //rs = rs.toLowerCase().trim();

    return rs;

  }

  /**
   * ���ݲ������Ƶõ����������趨��
   *
   * @param s ��������
   * @return ���õ��趨ֵ
   */
  public static int getIntOfSettingByName(String s) {

    //�õ��ַ����͵�ֵ
    String rs = BaseConfig.getSettingByName(s);
    if (rs == null || rs.compareTo("") == 0) {
      return 0;
    }

    //ת��������ֵ
    return Integer.valueOf(rs).intValue();

  }

  /**
   * ���ݲ������Ƶõ����������趨��
   *
   * @param strWebsite վ��Ŀ¼����
   * @param strFileName �����ļ�����
   * @param strParameter ��������
   * @return �������ļ���ָ���������趨ֵ
   *
   */
  public static int getIntOfSettingByName(String strWebsite, String strFileName,
      String strParameter) {

    //�õ��ַ����͵�ֵ
    String rs = BaseConfig.getSettingByName(strWebsite, strFileName,
        strParameter);
    if (rs == null || rs.compareTo("") == 0) {
      return 0;
    }

    //ת��������ֵ
    return Integer.valueOf(rs).intValue();

  }

  /**
   * �õ�ָ��������Դ��EJB��JNDI����
   *
   * @param s ����Դ��EJB������
   * @return NDI����
   */
  public static String getJNDIByName(String s) {

    //�õ�����������ʵ��
    BaseConfig obj = BaseConfig.getInstance();

    //�õ�ָ�����ҵ�����Դ��EJB��JNDI����
    String rs = BaseConfig.getSettingByName(s);
    if (rs == null) {
      rs = "";
    }

    //����Ƿ�����JBoss�в�������Դ(��DS����)��
    if (obj.JBOSS) {
      if (s.startsWith("DS")) {
        rs = "java:/" + rs;
      }

    }

    return rs;
  }

  //���������ļ�
  private Properties parse(String fileName) throws Exception {

    //��ʼ��
    ConfigParser configCP = new ConfigParser();
    SAXParserFactory configSAXPF = SAXParserFactory.newInstance();
    configSAXPF.setNamespaceAware(false);
    configSAXPF.setValidating(false);
    SAXParser configSAXP = configSAXPF.newSAXParser();

    try {

      //��ʼ����
      configSAXP.parse(fileName, configCP);

      //�õ��������
      return configCP.getConfigPropperties();

    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      configCP = null;
      configSAXPF = null;
      configSAXP = null;

    }

    //���ؿյ�������Ϣ
    return new Properties();

  }

  /**
   * ����
   */
  public static void destory(){
    _instance=null;
  }

}
