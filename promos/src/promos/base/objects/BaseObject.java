package promos.base.objects;

import java.util.ArrayList;
import java.sql.Date;
import java.sql.Time;
import java.math.BigDecimal;

import java.io.Serializable;

/**
 *
 * <p>标题: 类 BaseObject </p>
 * <p>描述: 基本数据类 </p>
 * <p>版权: Copyright (c) 2003-2004 </p>
 * <p>公司: 广州升域传媒有限公司 </p>
 * 保留所有权利。
 *
 * @author 胡维新
 * @version 1.0 06/26/2003
 *
 */

public class BaseObject
    implements Serializable ,Cloneable{

  //数据源
  private String dsName;

  //表、视图名
  private String tableName;

  //需要被更新的表名
  private String updateTableName;

  //字段数量
  private int fieldCount;

  //字段名列表
  private ArrayList fieldName;

  //字段别名列表
  private ArrayList fieldAlias;

  //字段属性列表
  private ArrayList fieldType;

  //字段值列表
  private ArrayList fieldValue;

  //字段的长度
  private ArrayList fieldLength;

  //字段的精度
  private ArrayList fieldPrecision;

  /**
   * 默认构建
   */
  public BaseObject() {
    this.tableName = "";
    this.updateTableName = "";
    this.fieldCount = 0;
    this.fieldName = new ArrayList();
    this.fieldAlias = new ArrayList();
    this.fieldType = new ArrayList();
    this.fieldValue = new ArrayList();
    this.fieldLength = new ArrayList();
    this.fieldPrecision = new ArrayList();
  }

  /**
   * 创建基本数据类
   *
   * @param strTableName 表或视图的名称
   * @param strUpdateTableName 需要被更新的表的名称
   * @param intFieldCount 表中字段的数量
   * @param alFieldName 表中字段的名称列表
   * @param alFieldType 表中字段的类型列表
   * @param alFieldValue 表中字段的值列表
   * @param alFieldLength 表中字段的长度列表
   * @param alFieldPrecision 表中字段的精度列表
   */
  public BaseObject(String strTableName, String strUpdateTableName,
                    int intFieldCount, ArrayList alFieldName,
                    ArrayList alFieldAlias, ArrayList alFieldType,
                    ArrayList alFieldValue, ArrayList alFieldLength,
                    ArrayList alFieldPrecision) {

    this.tableName = strTableName;
    this.updateTableName = strUpdateTableName;
    this.fieldCount = intFieldCount;
    this.fieldName = alFieldName;
    this.fieldAlias = alFieldAlias;
    this.fieldType = alFieldType;
    this.fieldValue = alFieldValue;
    this.fieldLength = alFieldLength;
    this.fieldPrecision = alFieldPrecision;
  }

  /**
   * 获得表或视图的名称
   *
   * @return 表或视图的名称
   */
  public String getTableName() {
    return this.tableName;
  }

  /**
   * 设定表或视图的名称
   *
   * @param s 表或视图的名称
   */
  public void setTableName(String s) {
    this.tableName = s;
  }

  /**
   * 获得需要被更新的表的名称
   *
   * @return 需要被更新的表的名称
   */
  public String getUpdateTableName() {
    return this.updateTableName;
  }

  /**
   * 设定需要被更新的表的名称
   *
   * @param s 需要被更新的表的名称
   */
  public void setUpdateTableName(String s) {
    this.updateTableName = s;
  }

  /**
   * 得到字段的数量
   *
   * @return 字段的数量
   */
  public int getFieldCount() {
    return this.fieldCount;
  }

  /**
   * 设定字段的数量
   *
   * @param i 字段的数量
   */
  public void setFieldCount(int i) {
    this.fieldCount = i;
  }

  /**
   * 得到字段的名称列表
   *
   * @return 字段的名称列表
   */
  public ArrayList getFieldName() {
    return this.fieldName;
  }

  /**
   * 设定字段的名称
   *
   * @param al 字段的名称列表
   */
  public void setFieldName(ArrayList al) {
    this.fieldName = al;
  }

  /**
   * 得到字段的别名列表
   *
   * @return 字段的别名列表
   */
  public ArrayList getFieldAlias() {
    return this.fieldAlias;
  }

  /**
   * 设定字段的别名
   *
   * @param al 字段的别名列表
   */
  public void setFieldAlias(ArrayList al) {
    this.fieldAlias = al;
  }

  /**
   * 得到字段的类型列表
   *
   * @return 字段的类型列表
   */
  public ArrayList getFieldType() {
    return this.fieldType;
  }

  /**
   * 设定字段的类型
   *
   * @param al 字段的类型列表
   */
  public void setFieldType(ArrayList al) {
    this.fieldType = al;
  }

  /**
   * 得到字段的值列表
   *
   * @return 字段的值列表
   */
  public ArrayList getFieldValue() {
    return this.fieldValue;
  }

  /**
   * 设定字段的值
   *
   * @param al 字段的值列表
   */
  public void setFieldValue(ArrayList al) {
    this.fieldValue = al;
  }

  /**
   * 得到字段的长度列表
   *
   * @return 字段的长度列表
   */
  public ArrayList getFieldLength() {
    return this.fieldLength;
  }

  /**
   * 设定字段的长度列表
   *
   * @param al 字段的长度列表
   */
  public void setFieldLength(ArrayList al) {
    this.fieldLength = al;
  }

  /**
   * 得到字段的精度列表
   *
   * @return 字段的精度列表
   */
  public ArrayList getFieldPrecision() {
    return this.fieldPrecision;
  }

  /**
   * 设定字段的精度列表
   *
   * @param al 字段的精度列表
   */
  public void setFieldPrecision(ArrayList al) {
    this.fieldPrecision = al;
  }

  /**
   * <p>添加指定字段的值到列表。</p><br>
   *
   * <ul>
   * <li>如果添加的字段类型是BaseDataType.INTERGER，封装的值类型是Integer。</li>
   * <li>如果添加的字段类型是BaseDataType.STRING，封装的值类型是String。</li>
   * <li>如果添加的字段类型是BaseDataType.NUMBER，封装的值类型是BigDecimal。</li>
   * <li>如果添加的字段类型是BaseDataType.DATE，封装的值类型是java.sql.Date。</li>
   * </ul>
   *
   * @param strFieldName 字段名称
   * @param strFieldAlias 字段别名
   * @param bdtFieldType 字段数据类型
   * @param objFieldValue 字段的值
   */
  public void addField(String strFieldName, String strFieldAlias,
                       int bdtFieldType, Object objFieldValue) {
    addField(strFieldName, strFieldAlias, new BaseDataType(bdtFieldType),
             objFieldValue);
  }

  /**
   * <p>添加指定字段的值到列表。</p><br>
   *
   * <ul>
   * <li>如果添加的字段类型是BaseDataType.INTERGER，封装的值类型是Integer。</li>
   * <li>如果添加的字段类型是BaseDataType.STRING，封装的值类型是String。</li>
   * <li>如果添加的字段类型是BaseDataType.NUMBER，封装的值类型是BigDecimal。</li>
   * <li>如果添加的字段类型是BaseDataType.DATE，封装的值类型是java.sql.Date。</li>
   * </ul>
   *
   * @param strFieldName 字段名称
   * @param strFieldAlias 字段别名
   * @param bdtFieldType 字段数据类型
   * @param objFieldValue 字段的值
   */
  public void addField(String strFieldName, String strFieldAlias,
                       BaseDataType bdtFieldType, Object objFieldValue) {

    //添加当前字段名称
    this.fieldName.add(strFieldName.toUpperCase().trim());

    //添加当前字段别名
    this.fieldAlias.add(strFieldAlias.toUpperCase().trim());

    //添加当前字段类型
    this.fieldType.add(bdtFieldType);

    //添加当前字段值
    this.fieldValue.add(objFieldValue);

    //增加字段数量
    this.fieldCount++;
  }


    /**
     * 根据别名删除该字段。
     *
     * @param strFieldAlias 别名
     * @param bdtFieldType  类型
     */


    public void removeField(String strFieldAlias, int bdtFieldType) {
      //检测参数是否正确
      if (strFieldAlias == null || strFieldAlias.compareTo("") == 0) {
        return;
      }
	  BasePrint.println("strFieldAlias is :"+strFieldAlias);	
      //检测是否存在指定的字段值
      int intFieldCount = this.getFieldCount();

      String strUpperFieldAlias = strFieldAlias.toUpperCase().trim();
	  BasePrint.println("strUpperFieldAlias is :"+strUpperFieldAlias);		
      //查找
      ArrayList aliasObject = this.getFieldAlias();
      for (int i = 0; i < intFieldCount; i++) {
        //检测
		BasePrint.println("字段名为:"+(String) (aliasObject.get(i)));
        if ( ( (String) (aliasObject.get(i))).compareTo(strUpperFieldAlias) == 0) {
			BasePrint.println(" 要移走该字段了！这里了!");
          this.fieldName.remove(i);
          this.fieldAlias.remove(i);
          this.fieldType.remove(i);
          this.fieldValue.remove(i);
          if (bdtFieldType == BaseDataType.INTEGER) {
            this.fieldLength.remove(i);
            this.fieldPrecision.remove(i);
          }
          this.fieldCount--;
          break;
        }
      }

    }

  /**
   * 根据别名删除该字段。
   *
   * @param strFieldAlias 别名
   */
  public void removeField(String strFieldAlias) {
    //检测参数是否正确
    if (strFieldAlias == null || strFieldAlias.compareTo("") == 0) {
      return;
    }

    //检测是否存在指定的字段值
    int intFieldCount = this.getFieldCount();

    String strUpperFieldAlias = strFieldAlias.toUpperCase().trim();
	BasePrint.println("strUpperFieldAlias is :"+strUpperFieldAlias);		
    //查找
    ArrayList aliasObject = this.getFieldAlias();
    for (int i = 0; i < intFieldCount; i++) {
      //检测
	  BasePrint.println("字段名为:"+(String) (aliasObject.get(i)));
      if ( ( (String) (aliasObject.get(i))).compareTo(strUpperFieldAlias) == 0) {
		  BasePrint.println(" 要移走该字段了！这里了!");
        this.fieldName.remove(i);
        this.fieldAlias.remove(i);
		if(this.fieldLength.size() > 0){
			this.fieldLength.remove(i);
		}
		if(this.fieldPrecision.size() > 0){
			this.fieldPrecision.remove(i);
		}
        this.fieldType.remove(i);
        this.fieldValue.remove(i);
        this.fieldCount--;
        break;
      }
    }

  }

  /**
   * <p>添加指定字段的值到列表。</p><br>
   *
   * <ul>
   * <li>如果添加的字段类型是BaseDataType.INTERGER，封装的值类型是Integer。</li>
   * <li>如果添加的字段类型是BaseDataType.STRING，封装的值类型是String。</li>
   * <li>如果添加的字段类型是BaseDataType.NUMBER，封装的值类型是BigDecimal。</li>
   * <li>如果添加的字段类型是BaseDataType.DATE，封装的值类型是java.sql.Date。</li>
   * </ul>
   *
   * @param strFieldName 字段名称
   * @param strFieldAlias 字段别名
   * @param bdtFieldType 字段数据类型
   * @param objFieldValue 字段的值
   * @param intFieldLength 字段的长度
   * @param intFieldPrecision 字段的精度
   */
  public void addField(String strFieldName, String strFieldAlias,
                       BaseDataType bdtFieldType, Object objFieldValue,
                       Integer intFieldLength, Integer intFieldPrecision) {

    //添加当前字段名称
    this.fieldName.add(strFieldName.toUpperCase().trim());

    //添加当前字段别名
    this.fieldAlias.add(strFieldAlias.toUpperCase().trim());

    //添加当前字段类型
    this.fieldType.add(bdtFieldType);

    //添加当前字段值
    this.fieldValue.add(objFieldValue);

    //添加当前字段的长度
    this.fieldLength.add(intFieldLength);

    //添加当前字段的精度
    this.fieldPrecision.add(intFieldPrecision);

    //增加字段数量
    this.fieldCount++;

  }

  /**
   * <p>添加指定字段的值到列表。</p><br>
   *
   * <ul>
   * <li>如果添加的字段类型是BaseDataType.INTERGER，封装的值类型是Integer。</li>
   * <li>如果添加的字段类型是BaseDataType.STRING，封装的值类型是String。</li>
   * <li>如果添加的字段类型是BaseDataType.NUMBER，封装的值类型是BigDecimal。</li>
   * <li>如果添加的字段类型是BaseDataType.DATE，封装的值类型是java.sql.Date。</li>
   * </ul>
   *
   * @param strFieldName 字段名称
   * @param strFieldAlias 字段别名
   * @param bdtFieldType 字段数据类型
   * @param objFieldValue 字段的值
   * @param intFieldLength 字段的长度
   * @param intFieldPrecision 字段的精度
   */
  public void addField(String strFieldName, String strFieldAlias,
                       BaseDataType bdtFieldType, Object objFieldValue,
                       int intFieldLength, int intFieldPrecision) {
    this.addField(strFieldName, strFieldAlias, bdtFieldType, objFieldValue,
                  new Integer(intFieldLength), new Integer(intFieldPrecision));
  }

  /**
   * <p>替换指定的字段值，如果没有，新建字段值。</p><br>
   *
   * <ul>
   *   <li>根据字段名称strFieldName和字段别名strFieldAlias查找是否存在该字段。</li>
   *   <li>如果存在字段，直接将字段值objFieldValue赋予该字段。</li>
   *   <li>如果不存在该字段，将调用addField添加该字段。</li>
   * </ul>
   *
   * <ul>
   * <li>如果添加的字段类型是BaseDataType.INTERGER，封装的值类型是Integer。</li>
   * <li>如果添加的字段类型是BaseDataType.STRING，封装的值类型是String。</li>
   * <li>如果添加的字段类型是BaseDataType.NUMBER，封装的值类型是BigDecimal。</li>
   * <li>如果添加的字段类型是BaseDataType.DATE，封装的值类型是java.sql.Date。</li>
   * </ul>
   *
   *
   *
   * @param strFieldName 字段名称
   * @param strFieldAlias 字段别名
   * @param bdtFieldType 字段类型
   * @param objFieldValue 字段值
   */
  public void setFieldValue(String strFieldName, String strFieldAlias,
                            int intFieldType, Object objFieldValue) {
    this.setFieldValue(strFieldName, strFieldAlias,
                       new BaseDataType(intFieldType), objFieldValue);
  }

  /**
   * <p>替换指定的字段值，如果没有，新建字段值。</p><br>
   *
   * <ul>
   *   <li>根据字段名称strFieldName和字段别名strFieldAlias查找是否存在该字段。</li>
   *   <li>如果存在字段，直接将字段值objFieldValue赋予该字段。</li>
   *   <li>如果不存在该字段，将调用addField添加该字段。</li>
   * </ul>
   *
   * <ul>
   * <li>如果添加的字段类型是BaseDataType.INTERGER，封装的值类型是Integer。</li>
   * <li>如果添加的字段类型是BaseDataType.STRING，封装的值类型是String。</li>
   * <li>如果添加的字段类型是BaseDataType.NUMBER，封装的值类型是BigDecimal。</li>
   * <li>如果添加的字段类型是BaseDataType.DATE，封装的值类型是java.sql.Date。</li>
   * </ul>
   *
   *
   *
   * @param strFieldName 字段名称
   * @param strFieldAlias 字段别名
   * @param bdtFieldType 字段类型
   * @param objFieldValue 字段值
   */
  public void setFieldValue(String strFieldName, String strFieldAlias,
                            BaseDataType bdtFieldType, Object objFieldValue) {

    //检测参数是否正确
    if (strFieldName == null || strFieldName.compareTo("") == 0 ||
        strFieldAlias == null || strFieldAlias.compareTo("") == 0) {
      return;
    }

    //检测是否存在指定的字段值
    int intFieldCount = this.getFieldCount();

    String strUpperFieldName = strFieldName.toUpperCase().trim();
    String strUpperFieldAlias = strFieldAlias.toUpperCase().trim();

    //查找
    ArrayList nameObject = this.getFieldName();
    ArrayList aliasObject = this.getFieldAlias();

    int i = 0;
    boolean isAdd = true;
    for (i = 0; i < intFieldCount; i++) {
      //检测
      if ( ( (String) (nameObject.get(i))).compareTo(strUpperFieldName) == 0 &&
          ( (String) (aliasObject.get(i))).compareTo(strUpperFieldAlias) == 0) {
        isAdd = false;
        break;
      }
    }

    //
    if (isAdd) {
      //如果不存在，新增字段
      this.addField(strFieldName, strFieldAlias, bdtFieldType, objFieldValue);
    }
    else {
      //如果存在，更新
      ArrayList typeObject = this.getFieldType();
      typeObject.set(i, bdtFieldType);
      this.setFieldType(typeObject);
      ArrayList valueObject = this.getFieldValue();
      valueObject.set(i, objFieldValue);
      this.setFieldValue(valueObject);
    }

  }

  /**
   * 复制基本数据类
   *
   * @param obj 指定需要复制的基本数据类
   * @return 复制的基本数据类
   */
  public static BaseObject clone(BaseObject obj) {


    //新建基本数据类
    BaseObject createObj = new BaseObject();

    //复制指定的基本数据类
    createObj.setDsName(obj.getDsName());
    createObj.setTableName(obj.getTableName());
    createObj.setUpdateTableName(obj.getUpdateTableName());
    createObj.setFieldCount(obj.getFieldCount());
    createObj.setFieldName((ArrayList)obj.getFieldName().clone());
    createObj.setFieldAlias((ArrayList)obj.getFieldAlias().clone());
    createObj.setFieldType((ArrayList)obj.getFieldType().clone());
    createObj.setFieldValue((ArrayList)obj.getFieldValue().clone());
    createObj.setFieldLength((ArrayList)obj.getFieldLength().clone());
    createObj.setFieldPrecision((ArrayList)obj.getFieldPrecision().clone());
    return createObj;

  }

  /**
   * 得到指定字段字符串显示
   *
   * @param strFieldAlias 别名
   * @param dateFormat 日期字符串格式
   * @return 字段字符串显示
   */
  public String toString(String strFieldAlias, int dateFormat) {

    //根据别名得到字段
    //判断字段类型

    //检测字段别名
    if (strFieldAlias == null) {
      return "";
    }

    //检测基本数据类中的字段数量
    int fieldCount = this.getFieldCount();
    if (fieldCount == 0) {
      return "";
    }

    //全部转换成大写模式
    String ts = strFieldAlias.toUpperCase().trim();

    //检测字段别名列表中有无指定的字段别名
    int index = -1;
    ArrayList tempAL = this.getFieldAlias();
    String tempFieldAlias = new String("");
    for (int i = 0; i < fieldCount; i++) {
      tempFieldAlias = (String) (tempAL.get(i));
      if (tempFieldAlias.compareTo(ts) == 0) {
        index = i;
        break;
      }
    }

    //如果没找到
    if (index == -1) {
      return "";
    }

    //判断类型
    if ( ( (BaseDataType)this.getFieldType().get(index)).getDataType() ==
        BaseDataType.DATE) {
      //日期类型
      return PromosDate.checkErrorDateString(this.toString(strFieldAlias),
                                             dateFormat);
    }

    return this.toString(strFieldAlias);
  }

  /**
   * 得到指定字段值的字符串显示
   *
   * @param fieldAlias 指定的字段别名
   * @return 字段值
   */
  public String toString(String fieldAlias) {
    return BaseObject.toString(this, fieldAlias);
  }

  /**
   * 查找指定的基本数据，转换成可显示的字符串
   *
   * @param obj 指定的基本数据
   * @param fieldAlias 指定的基本数据中的字段别名
   * @return 字符串值
   */
  public static String toString(BaseObject obj, int fieldkey) {

	    //返回字符串
	    String rs = new String("");

	    //检测基本数据类
	    if (obj == null) {
	      return rs;
	    }

	    //检测基本数据类中的字段数量
	    int fieldCount = obj.getFieldCount();
	    if (fieldCount == 0) {
	      return rs;
	    }

	  
      //找到指定字段名称后，找到字段类型
      BaseDataType bdt = (BaseDataType) ( (obj.getFieldType()).get(fieldkey));
      Object fieldValue = (obj.getFieldValue()).get(fieldkey);

      //根据字段类型得到字符串
      switch (bdt.getDataType()) {

        //如果是：INVALID
        case BaseDataType.INVALID:
          break;

          //如果是：INTEGER
        case BaseDataType.INTEGER:
          if (fieldValue == null) {
            rs = "";
          }
          else {
            rs = String.valueOf( ( (Integer) fieldValue).intValue());
            if (rs == null || rs.compareTo("") == 0) {
              rs = "";
            }
          }

          break;

          //如果是：NUMBER
        case BaseDataType.NUMBER:
          if (fieldValue == null) {
            rs = "";
          }
          else {
            rs = ( (BigDecimal) fieldValue).toString();
          }

          break;

          //如果是：STRING
        case BaseDataType.STRING:
          if (fieldValue == null) {
            rs = "";
          }
          else {
            rs = fieldValue.toString();
			  if (rs.length() >= 10){
				String substr = rs.substring(0, 10);
				if (substr.compareTo("1970-01-01") == 0 || substr.compareTo("1899-12-30") == 0 || substr.compareTo("1969-12-31")==0){
					rs = "";
				}
			  }
            if (rs == null) {
              rs = "";
            }
          }

          break;

          //如果是：DATE
        case BaseDataType.DATE:
          if (fieldValue == null) {
            rs = "";
          }
          else {
            rs = PromosDate.toDefaultString( (Date) fieldValue);
            if (rs == null) {
              rs = "";
            }
          }

          break;
          
          //如果是：DATETIME
        case BaseDataType.DATETIME:
          if (fieldValue == null) {
            rs = "";
          }
          else {
              rs = PromosDate.toDefaultString( (Date) fieldValue);
            if (rs == null) {
              rs = "";
            }
          }

          break;
          

          //如果是：CHAR_STREAM
        case BaseDataType.CHAR_STREAM:
          if (fieldValue == null) {
            rs = "";
          }
          else {
            rs = (String) fieldValue;
            if (rs == null) {
              rs = "";
            }
          }
          //如果是：LONG
        case BaseDataType.LONG:
          if (fieldValue == null) {
            rs = "";
          }
          else {
            rs = (String) fieldValue;
            if (rs == null) {
              rs = "";
            }
          }


          //如果是：CHAR_STREAM
        case BaseDataType.BLOB:
          if (fieldValue == null) {
            rs = null;
          }
          else {
            rs = fieldValue.toString();
          }

          break;

          //如果是：
        case BaseDataType.STREAM:
          if (fieldValue == null) {
            rs = null;
          }
          else {
            rs = fieldValue.toString();
          }

      }
	  
	  
      //返回前，去掉前后的空格
      if (rs != null && rs.compareTo("") != 0) {
        rs = rs.trim();
      }

      return rs;
	  
  }  
  
  /**
   * 查找指定的基本数据，转换成可显示的字符串
   *
   * @param obj 指定的基本数据
   * @param fieldAlias 指定的基本数据中的字段别名
   * @return 字符串值
   */
  public static String toString(BaseObject obj, String fieldAlias) {

    //返回字符串
    String rs = new String("");

    //检测基本数据类
    if (obj == null) {
      return rs;
    }

    //检测字段别名
    if (fieldAlias == null) {
      return rs;
    }

    //检测基本数据类中的字段数量
    int fieldCount = obj.getFieldCount();
    if (fieldCount == 0) {
      return rs;
    }

    //全部转换成大写模式
    String ts = fieldAlias.toUpperCase().trim();

    //检测字段别名列表中有无指定的字段别名
    ArrayList tempAL = obj.getFieldAlias();
    String tempFieldAlias = new String("");
    for (int i = 0; i < fieldCount; i++) {
      tempFieldAlias = (String) (tempAL.get(i));
      if (tempFieldAlias.compareTo(ts) == 0) {

        //找到指定字段名称后，找到字段类型
        BaseDataType bdt = (BaseDataType) ( (obj.getFieldType()).get(i));
        Object fieldValue = (obj.getFieldValue()).get(i);

        //根据字段类型得到字符串
        switch (bdt.getDataType()) {

          //如果是：INVALID
          case BaseDataType.INVALID:
            break;

            //如果是：INTEGER
          case BaseDataType.INTEGER:
            if (fieldValue == null) {
              rs = "";
            }
            else {
              rs = String.valueOf( ( (Integer) fieldValue).intValue());
              if (rs == null || rs.compareTo("") == 0) {
                rs = "";
              }
            }

            break;

            //如果是：NUMBER
          case BaseDataType.NUMBER:
            if (fieldValue == null) {
              rs = "";
            }
            else {
              rs = ( (BigDecimal) fieldValue).toString();
            }

            break;

            //如果是：STRING
          case BaseDataType.STRING:
            if (fieldValue == null) {
              rs = "";
            }
            else {
              rs = fieldValue.toString();
			  if (rs.length() >= 10){
				String substr = rs.substring(0, 10);
				if (substr.compareTo("1970-01-01") == 0 || substr.compareTo("1899-12-30") == 0 || substr.compareTo("1969-12-31")==0){
					rs = "";
				}
			  }
              if (rs == null) {
                rs = "";
              }
            }

            break;

            //如果是：DATE
          case BaseDataType.DATE:
            if (fieldValue == null) {
              rs = "";
            }
            else {
              rs = PromosDate.toDefaultString( (Date) fieldValue);
              if (rs == null) {
                rs = "";
              }
            }

            break;

            //如果是：DATETIME
          case BaseDataType.DATETIME:
            if (fieldValue == null) {
              rs = "";
            }
            else {
                rs = PromosDate.toDefaultString( (Date) fieldValue);
              if (rs == null) {
                rs = "";
              }
            }

            break;
            
            //如果是：CHAR_STREAM
          case BaseDataType.CHAR_STREAM:
            if (fieldValue == null) {
              rs = "";
            }
            else {
              rs = (String) fieldValue;
              if (rs == null) {
                rs = "";
              }
            }
            //如果是：LONG
          case BaseDataType.LONG:
            if (fieldValue == null) {
              rs = "";
            }
            else {
              rs = (String) fieldValue;
              if (rs == null) {
                rs = "";
              }
            }


            //如果是：CHAR_STREAM
          case BaseDataType.BLOB:
            if (fieldValue == null) {
              rs = null;
            }
            else {
              rs = fieldValue.toString();
            }

            break;

            //如果是：
          case BaseDataType.STREAM:
            if (fieldValue == null) {
              rs = null;
            }
            else {
              rs = fieldValue.toString();
            }
            break;

        }

        break;
      }

    }

    //返回前，去掉前后的空格
    if (rs != null && rs.compareTo("") != 0) {
      rs = rs.trim();
    }

    return rs;
  }

  /**
   * 查找指定的基本数据，转换成整型类型
   *
   * @param obj 指定的基本数据
   * @param fieldAlias 指定的基本数据中的字段别名
   * @return 整型类型
   */
  public static Integer toInteger(BaseObject obj, String fieldAlias) {
    //返回整型类型
    Integer rs = new Integer(0);

    //检测基本数据类
    if (obj == null) {
      return rs;
    }

    //检测字段别名
    if (fieldAlias == null) {
      return rs;
    }

    //全部转换成大写模式
    String ts = fieldAlias.toUpperCase().trim();

    //检测基本数据类中的字段数量
    int fieldCount = obj.getFieldCount();
    if (fieldCount == 0) {
      return rs;
    }

    //检测字段别名列表中有无指定的字段别名
    ArrayList tempAL = obj.getFieldAlias();
    String tempFieldAlias = new String("");
    for (int i = 0; i < fieldCount; i++) {
      tempFieldAlias = (String) (tempAL.get(i));
      if (tempFieldAlias.compareTo(ts) == 0) {

        //找到指定字段名称后，找到字段类型
        BaseDataType bdt = (BaseDataType) ( (obj.getFieldType()).get(i));
        Object fieldValue = (obj.getFieldValue()).get(i);

        //根据字段类型得到字符串
        switch (bdt.getDataType()) {

          //如果是：INVALID
          case BaseDataType.INVALID:
            break;

            //如果是：INTEGER
          case BaseDataType.INTEGER:
            if (fieldValue == null) {
              rs = new Integer(0);
            }
            else {
              rs = (Integer) fieldValue;
            }

            break;

            //如果是：NUMBER
          case BaseDataType.NUMBER:
            if (fieldValue == null) {
              rs = new Integer(0);
            }
            else {
              rs = new Integer( ( (BigDecimal) fieldValue).intValue());
            }

            break;

            //如果是：STRING
          case BaseDataType.STRING:
            if (fieldValue == null) {
              rs = new Integer(0);
            }
            else {
              rs = Integer.valueOf( (String) fieldValue);
            }

            break;

            //如果是：DATE
          case BaseDataType.DATE:

            //rs = new Integer(((Date)fieldValue).);
            //rs = SeperpDate.toDefaultString((Date)fieldValue);
            break;

        }

        break;
      }

    }

    return rs;

  }

  /**
   * 查找指定的基本数据，转换成整型类型
   *
   * @param obj 指定的基本数据
   * @param fieldAlias 指定的基本数据中的字段别名
   * @return 整型类型
   */
  public static Long toLong(BaseObject obj, String fieldAlias) {
    //返回整型类型
    Long rs = new Long(0);

    //检测基本数据类
    if (obj == null) {
      return rs;
    }

    //检测字段别名
    if (fieldAlias == null) {
      return rs;
    }

    //全部转换成大写模式
    String ts = fieldAlias.toUpperCase().trim();

    //检测基本数据类中的字段数量
    int fieldCount = obj.getFieldCount();
    if (fieldCount == 0) {
      return rs;
    }

    //检测字段别名列表中有无指定的字段别名
    ArrayList tempAL = obj.getFieldAlias();
    String tempFieldAlias = new String("");
    for (int i = 0; i < fieldCount; i++) {
      tempFieldAlias = (String) (tempAL.get(i));
      if (tempFieldAlias.compareTo(ts) == 0) {

        //找到指定字段名称后，找到字段类型
        BaseDataType bdt = (BaseDataType) ( (obj.getFieldType()).get(i));
        Object fieldValue = (obj.getFieldValue()).get(i);

        //根据字段类型得到字符串
        switch (bdt.getDataType()) {

          //如果是：INVALID
          case BaseDataType.INVALID:
            break;

            //如果是：INTEGER
          case BaseDataType.INTEGER:
            if (fieldValue == null) {
              rs = new Long(0l);
            }
            else {
              rs = new Long( ( (Integer) fieldValue).longValue());
            }

            break;

            //如果是：NUMBER
          case BaseDataType.NUMBER:
            if (fieldValue == null) {
              rs = new Long(0l);
            }
            else {
              rs = new Long( ( (BigDecimal) fieldValue).longValue());
            }
            break;

            //如果是：STRING
          case BaseDataType.STRING:
            if (fieldValue == null) {
              rs = new Long(0l);
            }
            else {
              rs = Long.valueOf( (String) fieldValue);
            }
            break;

            //如果是：DATE
          case BaseDataType.DATE:

            //rs = new Integer(((Date)fieldValue).);
            //rs = SeperpDate.toDefaultString((Date)fieldValue);
            break;

        }

        break;
      }

    }

    return rs;

  }

  public int getFieldLength(String alias){
	  for (int i=0; i<fieldAlias.size(); i++){
		  String alias1 = (String)fieldAlias.get(i);
		  if (alias.equals(alias1)){
			 Integer intObj = (Integer)fieldLength.get(i);
			 return intObj.intValue();

		  }
	  }

	  return -1;
  }

  public int getFieldType(String alias){
	  for (int i=0; i<fieldAlias.size(); i++){
		  String alias1 = (String)fieldAlias.get(i);
		  if (alias.equals(alias1)){
			 BaseDataType typeObj = (BaseDataType)fieldType.get(i);
			 return typeObj.getDataType();

		  }
	  }

	  return -1;
  }
  public String getDsName() {
    return dsName;
  }
  public void setDsName(String dsName) {
    this.dsName = dsName;
  }

}
