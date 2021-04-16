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
 * <p>标题: 类 BaseConfig </p>
 * <p>描述: 参数配置静态类 </p>
 * <p>版权: Copyright (c) 2003-2004 </p>
 * <p>公司: 广州升域传媒有限公司 </p>
 * 保留所有权利。
 *
 * @author 胡维新
 * @version 1.0 05/27/2003
 *
 */

public final class BaseConfig
    implements Serializable {

  //指定站点目录
  private ArrayList website;

  //指定配置文件
  private ArrayList file;

  //指定配置文件中的参数
  private ArrayList config;

  //当前服务器是否是JBOSS
  private boolean JBOSS;

  //唯一的参数配置静态类实例
  private static BaseConfig _instance = null;

  private static URL url = null;

  //清除配置实例
  public static void ClearInstance() {
    _instance = null;
  }

  /**
   * 构建，禁止子类继承
   */
  private BaseConfig() {
    this.website = new ArrayList();
    this.file = new ArrayList();
    this.config = new ArrayList();

    //this.WEBSITE = new String("");
    this.JBOSS = false;
  }

  /**
   * 得到配置文件中关键字的默认列表
   *
   * @param strWebsite 站点名称
   * @param strConfigFile 配置文件名称
   * @param strName 配置文件中关键字的名称
   * @return 字符串列表
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
        //添加列表
        alDL.add(strDVLValue);
      }

      j++;

    }
    while (b);

    return alDL;
  }

  /**
   * 得到配置文件中关键字的默认列表
   *
   * @param strWebsite 站点名称
   * @param strConfigFile 配置文件名称
   * @param strName 配置文件中关键字的名称
   * @return 字符串列表
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
        //添加列表
        alDL.add(strDVLValue);
      }

      j++;

    }
    while (b);

    return alDL;
  }

  /**
   * 根据站点名称和配置文件名称得到该配置文件的内容。
   *
   * @param strWebsite 站点名称
   * @param strConfigFile 配置文件名称
   * @return 配置文件内容
   */
  public static Properties getProperties(String strWebsite,
      String strConfigFile) {

    BaseConfig obj = BaseConfig.getInstance();

    //得到配置的设定值
    int i = 0;
    int website = -1;
    int file = -1;

    //检测当前站点名称是否存在
    if (obj.website == null || obj.website.size() == 0) {
      return null;
    }
    int websiteSize = obj.website.size();

    // BasePrint.println("站点数量："+websiteSize);

    for (i = 0; i < websiteSize; ) {
      //得到站点索引
      String strWS = (String) (obj.website.get(i));
      if (strWebsite.compareTo(strWS) == 0) {
        website = i;
        break;
      }

      i++;
    }
    //BasePrint.println("当前站点："+website);

    //如果找不到站点名称，直接返回
    if (website == -1) {
      return null;
    }

    //检测当前配置文件是否存在
    if (obj.file == null || obj.file.size() == 0) {
      return null;
    }
    int fileSize = obj.file.size();

    //BasePrint.println("文件数量："+fileSize);

    for (i = 0; i < fileSize; ) {
      //得到配置文件索引
      String strFL = (String) (obj.file.get(i));
      if (strConfigFile.compareTo(strFL) == 0) {
        file = i;
        break;
      }

      i++;

    }

    //去除第一个基本文件
    file = file - 1;

    //BasePrint.println("当前文件："+file);

    //如果找不到配置文件名称，直接返回
    if (file == -1) {
      return null;
    }

    //读取合适的配置文件列表
    ArrayList alConfig = (ArrayList) (obj.config.get(website + 1));

    //从列表中读取合适的参数
    Properties np = (Properties) (alConfig.get(file));

    return np;

  }

  /**
   * 读取所有的配置文件，创建参数配置静态类的实例。
   *
   * @return 参数配置静态类
   */
  public static BaseConfig getInstance() {
    return _instance;
  }

  /**
   * 读取所有的配置文件，创建参数配置静态类的实例。
   *
   * @return 参数配置静态类
   */
  public static BaseConfig getInstance(String pa) {
    if (_instance == null) {
      _instance = new BaseConfig();
      String path = pa;
      int intIndex = pa.lastIndexOf(File.separator);
      pa = pa.substring(0, intIndex + 1);
      //读取参数配置
      try {

        //首先解析出基本配置文件中的信息
        Properties bp = _instance.parse(path);

        //保存基本配置信息
        //保存文件名称
        _instance.file.add("promos");

        //保存配置信息
        ArrayList alBC = new ArrayList();
        alBC.add(bp);
        _instance.config.add(alBC);

        //得到当前服务器是否是JBOSS
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
   * 根据配置文件promosenv.xml找到所有的配置信息。
   *
   * <ul>
   * <font color="#ff0000"><b>本方法仅在站点第一次被访问时调用。</b></font>
   * </ul>
   *
   * @param url 配置文件的URL地址
   * @return 得到配置信息
   */
  public static BaseConfig getInstance(URL url) {

    //检测实例
    if (_instance == null) {
      _instance = new BaseConfig();
      BaseConfig.url = url;
      System.out.println(" －－ 文件  －－ " + BaseConfig.url);
      //读取参数配置
      try {

        //首先解析出基本配置文件中的信息
        String strFilePath = url.toString();

        System.out.println(" －－ 文件  －－ " + strFilePath);

        Properties bp = _instance.parse(strFilePath);

        //保存基本配置信息
        //保存文件名称
        _instance.file.add("promos");

        //保存配置信息
        ArrayList alBC = new ArrayList();
        alBC.add(bp);
        _instance.config.add(alBC);



        //得到当前服务器是否是JBOSS
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
   * 根据参数名称得到基本配置设定。
   *
   * @param s 配置名称
   * @return 配置的设定值
   */
  public static String getSettingByName(String s) {

    //得到参数配置类实例
    BaseConfig obj = BaseConfig.getInstance();

    //检测是否存在配置文件
    if (obj.file == null || obj.file.size() == 0) {
      return "";
    }

    //测定首项是否是基本配置文件参数
    String strBase = (String) (obj.file.get(0));
    if (strBase.compareTo("promos") != 0) {
      return "";
    }

    //得到列表
    ArrayList alBase = (ArrayList) (obj.config.get(0));
    Properties bp = (Properties) (alBase.get(0));

    //得到配置的设定值
    String rs = bp.getProperty(s);
    if (rs == null) {
      return "";
    }
    return rs;
  }

  /**
   * 根据参数名称得到基本配置设定。
   *
   * @param strWebsite 当前站点名称
   * @param strFileName 配置文件名称
   * @param strParameter 参数名称
   * @return 该配置文件中指定参数的设定值
   */
  public static String getSettingByName(String strWebsite, String strFileName,
      String strParameter) {

    //得到参数配置类实例
    BaseConfig obj = BaseConfig.getInstance();

    //得到配置的设定值
    int i = 0;
    int website = -1;
    int file = -1;

    //检测当前站点名称是否存在
    if (obj.website == null || obj.website.size() == 0) {
      return "";
    }
    int websiteSize = obj.website.size();

    //BasePrint.println("站点数量："+websiteSize);

    for (i = 0; i < websiteSize; ) {
      //得到站点索引
      String strWS = (String) (obj.website.get(i));
      if (strWebsite.compareTo(strWS) == 0) {
        website = i;
        break;
      }

      i++;
    }
    //BasePrint.println("当前站点："+website);

    //如果找不到站点名称，直接返回
    if (website == -1) {
      return "";
    }

    //检测当前配置文件是否存在
    if (obj.file == null || obj.file.size() == 0) {
      return "";
    }
    int fileSize = obj.file.size();

    //BasePrint.println("文件数量："+fileSize);

    for (i = 0; i < fileSize; ) {
      //得到配置文件索引
      String strFL = (String) (obj.file.get(i));
      if (strFileName.compareTo(strFL) == 0) {
        file = i;
        break;
      }

      i++;

    }

    //去除第一个基本文件
    file = file - 1;

    //BasePrint.println("当前文件："+file);

    //如果找不到配置文件名称，直接返回
    if (file == -1) {
      return "";
    }

    //读取合适的配置文件列表
    ArrayList alConfig = (ArrayList) (obj.config.get(website + 1));

    //从列表中读取合适的参数
    Properties np = (Properties) (alConfig.get(file));

    //然后读取参数
    String rs = np.getProperty(strParameter);
    if (rs == null) {
      return "";
    }

    //将所有信息转换成小写模式，不必要(中文)！
    //rs = rs.toLowerCase().trim();

    return rs;

  }

  /**
   * 根据参数名称得到基本配置设定。
   *
   * @param s 配置名称
   * @return 配置的设定值
   */
  public static int getIntOfSettingByName(String s) {

    //得到字符串型的值
    String rs = BaseConfig.getSettingByName(s);
    if (rs == null || rs.compareTo("") == 0) {
      return 0;
    }

    //转换成整型值
    return Integer.valueOf(rs).intValue();

  }

  /**
   * 根据参数名称得到基本配置设定。
   *
   * @param strWebsite 站点目录名称
   * @param strFileName 配置文件名称
   * @param strParameter 参数名称
   * @return 该配置文件中指定参数的设定值
   *
   */
  public static int getIntOfSettingByName(String strWebsite, String strFileName,
      String strParameter) {

    //得到字符串型的值
    String rs = BaseConfig.getSettingByName(strWebsite, strFileName,
        strParameter);
    if (rs == null || rs.compareTo("") == 0) {
      return 0;
    }

    //转换成整型值
    return Integer.valueOf(rs).intValue();

  }

  /**
   * 得到指定的数据源或EJB的JNDI命名
   *
   * @param s 数据源或EJB的名称
   * @return NDI命名
   */
  public static String getJNDIByName(String s) {

    //得到参数配置类实例
    BaseConfig obj = BaseConfig.getInstance();

    //得到指定查找的数据源或EJB的JNDI命名
    String rs = BaseConfig.getSettingByName(s);
    if (rs == null) {
      rs = "";
    }

    //检测是否是在JBoss中查找数据源(以DS开首)。
    if (obj.JBOSS) {
      if (s.startsWith("DS")) {
        rs = "java:/" + rs;
      }

    }

    return rs;
  }

  //解析配置文件
  private Properties parse(String fileName) throws Exception {

    //初始化
    ConfigParser configCP = new ConfigParser();
    SAXParserFactory configSAXPF = SAXParserFactory.newInstance();
    configSAXPF.setNamespaceAware(false);
    configSAXPF.setValidating(false);
    SAXParser configSAXP = configSAXPF.newSAXParser();

    try {

      //开始解析
      configSAXP.parse(fileName, configCP);

      //得到解析结果
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

    //返回空的配置信息
    return new Properties();

  }

  /**
   * 销毁
   */
  public static void destory(){
    _instance=null;
  }

}
