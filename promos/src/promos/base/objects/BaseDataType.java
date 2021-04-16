package promos.base.objects;

//import java.util.ArrayList;
import java.util.Date;
import java.math.BigDecimal;
import java.sql.Blob;

import java.io.Serializable;

/**
 *
 * <p>标题: 类 BaseDataType </p>
 * <p>描述: 基本数据类型类 </p>
 * <p>版权: Copyright (c) 2003-2004 </p>
 * <p>公司: 广州升域传媒有限公司 </p>
 * 保留所有权利。
 *
 * @author 胡维新
 * @version 1.0 06/26/2003
 *
 */

public class BaseDataType
    implements Serializable {

  /**
   * 当前数据类型是无效的数据类型
   */
  public static final int INVALID = 0;

  /**
   * 当前数据类型是整型类型：Integer
   */
  public static final int INTEGER = 1;

  /**
   * 当前数据类型是Number类型，包括int、long、float、double
   */
  public static final int NUMBER = 2;

  /**
   * 当前数据类型是字符串类型：String
   */
  public static final int STRING = 3;

  /**
   * 当前数据类型是日期类型：Date
   */
  public static final int DATE = 4;

  /**
   * 当前数据类型是日期类型：Datetime
   */
  public static final int DATETIME = 9;

  /**
   * 当前数据类型是大字段类型：
   */
  public static final int STREAM = 5;

  /**
   * 当前数据类型是大字段类型：
   */
  public static final int CHAR_STREAM = 6;

  /**
   * 当前数据类型是大字段类型：
   */
  public static final int BLOB = 7;

  public static final int LONG = 8;

  //当前数据类型
  private int dataType;

  /**
   * 构建
   */
  public BaseDataType() {
    this.dataType = BaseDataType.INVALID;
  }

  /**
   * 创建基本数据类型类
   *
   * @param baseDataType 指定的基本数据类型
   */
  public BaseDataType(int baseDataType) {
    this.dataType = baseDataType;
  }

  /**
   * 得到基本数据类型
   *
   * @return 基本数据类型
   */
  public int getDataType() {
    return this.dataType;
  }

  /**
   * 设定基本数据类型
   *
   * @param i 基本数据类型
   */
  public void setDataType(int i) {
    this.dataType = i;
  }

  /**
   * 根据字段类型名称得到基本数据类型
   *
   * @param fieldTypeName 字段类型名称
   * @return 基本数据类型
   */
  public static BaseDataType getBaseDataType(String fieldTypeName) {
    BaseDataType bdt = new BaseDataType();
    
    //System.out.println("fieldTypeName:"+fieldTypeName);

    //检测字段类型名称
    if (fieldTypeName == null) {
      bdt.setDataType(BaseDataType.INVALID);
      return bdt;
    }

    //转换成小写
    String strName = (fieldTypeName.toLowerCase()).trim();

    //检测数据库字段类型，转换成合适的类型

    //如果是：INTEGER 整数类型,小的整数
    if (strName.compareTo("integer") == 0) {
      bdt.setDataType(BaseDataType.INTEGER);
      return bdt;
    }

    //如果是：NUMBER(P,S) 数字类型,P为整数位，S为小数位
    if (strName.compareTo("number") == 0) {
      bdt.setDataType(BaseDataType.NUMBER);
      return bdt;
    }

    //如果是：VARCHAR2 可变长度的字符串,最大长度4000 bytes,可做索引的最大长度749
    if (strName.compareTo("varchar2") == 0) {
      bdt.setDataType(BaseDataType.STRING);
      return bdt;
    }

    //如果是：CHAR 固定长度字符串,最大长度2000 bytes
    if (strName.compareTo("char") == 0) {
      bdt.setDataType(BaseDataType.STRING);
      return bdt;
    }

    //如果是：FLOAT 浮点数类型,NUMBER(38)，双精度
    if (strName.compareTo("float") == 0) {
      bdt.setDataType(BaseDataType.NUMBER);
      return bdt;
    }

    //如果是：DATE 日期（日-月-年）,DD-MM-YY（HH-MI-SS）
    if (strName.compareTo("date") == 0) {
      bdt.setDataType(BaseDataType.DATE);
      return bdt;
    }

    //如果是：DATETIME DD-MM-YY HH-MI
    if (strName.compareTo("datetime") == 0) {
      bdt.setDataType(BaseDataType.DATETIME);
      return bdt;
    }

    //如果是：NCHAR 根据字符集而定的固定长度字符串,最大长度2000 bytes
    if (strName.compareTo("nchar") == 0) {
      bdt.setDataType(BaseDataType.STRING);
      return bdt;
    }

    //如果是：NVARCHAR2 根据字符集而定的可变长度字符串,最大长度4000 bytes
    if (strName.compareTo("nvarchar2") == 0) {
      bdt.setDataType(BaseDataType.STRING);
      return bdt;
    }

    //如果是：DECIMAL(P,S) 数字类型,P为整数位，S为小数位
    if (strName.compareTo("decimal") == 0) {
      bdt.setDataType(BaseDataType.NUMBER);
      return bdt;
    }

    //如果是：REAL 实数类型,NUMBER(63)，精度更高
    if (strName.compareTo("real") == 0) {
      bdt.setDataType(BaseDataType.NUMBER);
      return bdt;
    }

    //如果是：LONG 超长字符串,最大长度2G
    if (strName.compareTo("long") == 0) {
      bdt.setDataType(BaseDataType.LONG);
      return bdt;
    }

    //如果是：RAW 固定长度的二进制数据,最大长度2000 bytes ,存放多媒体图象声音等
    if (strName.compareTo("raw") == 0) {
      bdt.setDataType(BaseDataType.STREAM);
      return bdt;
    }

    //如果是：LONG RAW 可变长度的二进制数据,最大长度2G,存放多媒体图象声音等
    if (strName.compareTo("long raw") == 0) {
      bdt.setDataType(BaseDataType.STREAM);
      return bdt;
    }

    //如果是：BLOB 二进制数据,最大长度4G
    if (strName.compareTo("blob") == 0) {
      bdt.setDataType(BaseDataType.BLOB);
      return bdt;
    }

    //如果是：CLOB 字符数据,最大长度4G
    if (strName.compareTo("clob") == 0) {
      bdt.setDataType(BaseDataType.CHAR_STREAM);
      return bdt;
    }

    //如果是：NCLOB 根据字符集而定的字符数据,最大长度4G
    if (strName.compareTo("nclob") == 0) {
      bdt.setDataType(BaseDataType.CHAR_STREAM);
      return bdt;
    }

    //如果是：BFILE 存放在数据库外的二进制数据,最大长度4G
    if (strName.compareTo("bfile") == 0) {
      bdt.setDataType(BaseDataType.STREAM);
      return bdt;
    }

    //如果是：ROWID 数据表中记录的唯一行号,10 bytes ********.****.****格式，*为0或1
    if (strName.compareTo("rowid") == 0) {
      bdt.setDataType(BaseDataType.STREAM);
      return bdt;
    }

    //如果是：NROWID 二进制数据表中记录的唯一行号,最大长度4000 bytes
    if (strName.compareTo("nrowid") == 0) {
      bdt.setDataType(BaseDataType.STREAM);
      return bdt;
    }

    /*********************以下为Sybase数据类型************************/
    
    //如果是：VARCHAR 可变长度的字符串,最大长度256 bytes
    if (strName.compareTo("varchar") == 0) {
      bdt.setDataType(BaseDataType.STRING);
      return bdt;
    }
    
    //如果是：TEXT 
    if (strName.compareTo("text") == 0) {
      bdt.setDataType(BaseDataType.STRING);
      return bdt;
    }
    
 
    //如果是：NUMERIC 
    if (strName.compareTo("numeric") == 0) {
      bdt.setDataType(BaseDataType.NUMBER);
      return bdt;
    }
    

    //如果是：CHAR 
    if (strName.compareTo("char") == 0) {
      bdt.setDataType(BaseDataType.STRING);
      return bdt;
    }
 
    //如果是：INT 
    if (strName.compareTo("int") == 0) {
      bdt.setDataType(BaseDataType.INTEGER);
      return bdt;
    }

    //如果是：DATE 
    if (strName.compareTo("date") == 0) {
      bdt.setDataType(BaseDataType.DATE);
      return bdt;
    }
    
    //如果是：DATETIME 
    if (strName.compareTo("datetime") == 0) {
      bdt.setDataType(BaseDataType.DATETIME);
      return bdt;
    }
   
    /*********************Sybase数据类型结束************************/
    
    
     //找不到，设定为STRING
    bdt.setDataType(BaseDataType.STRING);
    return bdt;
  }

  /**
   * 得到默认的字段值
   *
   * @param fieldType 字段类型
   * @return 字段值
   */
  public static Object getDefaultValue(int fieldType) {

    Object obj = new Object();

    //根据字段类型得到默认的字段值
    switch (fieldType) {

      //如果是：INVALID
      case BaseDataType.INVALID:
        break;

        //如果是：INTEGER
      case BaseDataType.INTEGER:
        obj = new Integer(0);
        break;

        //如果是：NUMBER
      case BaseDataType.NUMBER:
        obj = new BigDecimal("0");
        break;

        //如果是：STRING
      case BaseDataType.STRING:
        obj = new String("");
        break;

        //如果是：DATE
      case BaseDataType.DATE:
        obj = new Date();
        break;

        //如果是：DATETIME
      case BaseDataType.DATETIME:
        obj = new Date();
        break;

        //如果是：STREAM CHAR_STREAM
      case BaseDataType.STREAM:
      case BaseDataType.CHAR_STREAM:
        obj = new String("");
        break;

    }

    return obj;
  }

}
