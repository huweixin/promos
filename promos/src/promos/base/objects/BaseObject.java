package promos.base.objects;

import java.util.ArrayList;
import java.sql.Date;
import java.sql.Time;
import java.math.BigDecimal;

import java.io.Serializable;

/**
 *
 * <p>����: �� BaseObject </p>
 * <p>����: ���������� </p>
 * <p>��Ȩ: Copyright (c) 2003-2004 </p>
 * <p>��˾: ��������ý���޹�˾ </p>
 * ��������Ȩ����
 *
 * @author ��ά��
 * @version 1.0 06/26/2003
 *
 */

public class BaseObject
    implements Serializable ,Cloneable{

  //����Դ
  private String dsName;

  //����ͼ��
  private String tableName;

  //��Ҫ�����µı���
  private String updateTableName;

  //�ֶ�����
  private int fieldCount;

  //�ֶ����б�
  private ArrayList fieldName;

  //�ֶα����б�
  private ArrayList fieldAlias;

  //�ֶ������б�
  private ArrayList fieldType;

  //�ֶ�ֵ�б�
  private ArrayList fieldValue;

  //�ֶεĳ���
  private ArrayList fieldLength;

  //�ֶεľ���
  private ArrayList fieldPrecision;

  /**
   * Ĭ�Ϲ���
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
   * ��������������
   *
   * @param strTableName �����ͼ������
   * @param strUpdateTableName ��Ҫ�����µı������
   * @param intFieldCount �����ֶε�����
   * @param alFieldName �����ֶε������б�
   * @param alFieldType �����ֶε������б�
   * @param alFieldValue �����ֶε�ֵ�б�
   * @param alFieldLength �����ֶεĳ����б�
   * @param alFieldPrecision �����ֶεľ����б�
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
   * ��ñ����ͼ������
   *
   * @return �����ͼ������
   */
  public String getTableName() {
    return this.tableName;
  }

  /**
   * �趨�����ͼ������
   *
   * @param s �����ͼ������
   */
  public void setTableName(String s) {
    this.tableName = s;
  }

  /**
   * �����Ҫ�����µı������
   *
   * @return ��Ҫ�����µı������
   */
  public String getUpdateTableName() {
    return this.updateTableName;
  }

  /**
   * �趨��Ҫ�����µı������
   *
   * @param s ��Ҫ�����µı������
   */
  public void setUpdateTableName(String s) {
    this.updateTableName = s;
  }

  /**
   * �õ��ֶε�����
   *
   * @return �ֶε�����
   */
  public int getFieldCount() {
    return this.fieldCount;
  }

  /**
   * �趨�ֶε�����
   *
   * @param i �ֶε�����
   */
  public void setFieldCount(int i) {
    this.fieldCount = i;
  }

  /**
   * �õ��ֶε������б�
   *
   * @return �ֶε������б�
   */
  public ArrayList getFieldName() {
    return this.fieldName;
  }

  /**
   * �趨�ֶε�����
   *
   * @param al �ֶε������б�
   */
  public void setFieldName(ArrayList al) {
    this.fieldName = al;
  }

  /**
   * �õ��ֶεı����б�
   *
   * @return �ֶεı����б�
   */
  public ArrayList getFieldAlias() {
    return this.fieldAlias;
  }

  /**
   * �趨�ֶεı���
   *
   * @param al �ֶεı����б�
   */
  public void setFieldAlias(ArrayList al) {
    this.fieldAlias = al;
  }

  /**
   * �õ��ֶε������б�
   *
   * @return �ֶε������б�
   */
  public ArrayList getFieldType() {
    return this.fieldType;
  }

  /**
   * �趨�ֶε�����
   *
   * @param al �ֶε������б�
   */
  public void setFieldType(ArrayList al) {
    this.fieldType = al;
  }

  /**
   * �õ��ֶε�ֵ�б�
   *
   * @return �ֶε�ֵ�б�
   */
  public ArrayList getFieldValue() {
    return this.fieldValue;
  }

  /**
   * �趨�ֶε�ֵ
   *
   * @param al �ֶε�ֵ�б�
   */
  public void setFieldValue(ArrayList al) {
    this.fieldValue = al;
  }

  /**
   * �õ��ֶεĳ����б�
   *
   * @return �ֶεĳ����б�
   */
  public ArrayList getFieldLength() {
    return this.fieldLength;
  }

  /**
   * �趨�ֶεĳ����б�
   *
   * @param al �ֶεĳ����б�
   */
  public void setFieldLength(ArrayList al) {
    this.fieldLength = al;
  }

  /**
   * �õ��ֶεľ����б�
   *
   * @return �ֶεľ����б�
   */
  public ArrayList getFieldPrecision() {
    return this.fieldPrecision;
  }

  /**
   * �趨�ֶεľ����б�
   *
   * @param al �ֶεľ����б�
   */
  public void setFieldPrecision(ArrayList al) {
    this.fieldPrecision = al;
  }

  /**
   * <p>���ָ���ֶε�ֵ���б�</p><br>
   *
   * <ul>
   * <li>�����ӵ��ֶ�������BaseDataType.INTERGER����װ��ֵ������Integer��</li>
   * <li>�����ӵ��ֶ�������BaseDataType.STRING����װ��ֵ������String��</li>
   * <li>�����ӵ��ֶ�������BaseDataType.NUMBER����װ��ֵ������BigDecimal��</li>
   * <li>�����ӵ��ֶ�������BaseDataType.DATE����װ��ֵ������java.sql.Date��</li>
   * </ul>
   *
   * @param strFieldName �ֶ�����
   * @param strFieldAlias �ֶα���
   * @param bdtFieldType �ֶ���������
   * @param objFieldValue �ֶε�ֵ
   */
  public void addField(String strFieldName, String strFieldAlias,
                       int bdtFieldType, Object objFieldValue) {
    addField(strFieldName, strFieldAlias, new BaseDataType(bdtFieldType),
             objFieldValue);
  }

  /**
   * <p>���ָ���ֶε�ֵ���б�</p><br>
   *
   * <ul>
   * <li>�����ӵ��ֶ�������BaseDataType.INTERGER����װ��ֵ������Integer��</li>
   * <li>�����ӵ��ֶ�������BaseDataType.STRING����װ��ֵ������String��</li>
   * <li>�����ӵ��ֶ�������BaseDataType.NUMBER����װ��ֵ������BigDecimal��</li>
   * <li>�����ӵ��ֶ�������BaseDataType.DATE����װ��ֵ������java.sql.Date��</li>
   * </ul>
   *
   * @param strFieldName �ֶ�����
   * @param strFieldAlias �ֶα���
   * @param bdtFieldType �ֶ���������
   * @param objFieldValue �ֶε�ֵ
   */
  public void addField(String strFieldName, String strFieldAlias,
                       BaseDataType bdtFieldType, Object objFieldValue) {

    //��ӵ�ǰ�ֶ�����
    this.fieldName.add(strFieldName.toUpperCase().trim());

    //��ӵ�ǰ�ֶα���
    this.fieldAlias.add(strFieldAlias.toUpperCase().trim());

    //��ӵ�ǰ�ֶ�����
    this.fieldType.add(bdtFieldType);

    //��ӵ�ǰ�ֶ�ֵ
    this.fieldValue.add(objFieldValue);

    //�����ֶ�����
    this.fieldCount++;
  }


    /**
     * ���ݱ���ɾ�����ֶΡ�
     *
     * @param strFieldAlias ����
     * @param bdtFieldType  ����
     */


    public void removeField(String strFieldAlias, int bdtFieldType) {
      //�������Ƿ���ȷ
      if (strFieldAlias == null || strFieldAlias.compareTo("") == 0) {
        return;
      }
	  BasePrint.println("strFieldAlias is :"+strFieldAlias);	
      //����Ƿ����ָ�����ֶ�ֵ
      int intFieldCount = this.getFieldCount();

      String strUpperFieldAlias = strFieldAlias.toUpperCase().trim();
	  BasePrint.println("strUpperFieldAlias is :"+strUpperFieldAlias);		
      //����
      ArrayList aliasObject = this.getFieldAlias();
      for (int i = 0; i < intFieldCount; i++) {
        //���
		BasePrint.println("�ֶ���Ϊ:"+(String) (aliasObject.get(i)));
        if ( ( (String) (aliasObject.get(i))).compareTo(strUpperFieldAlias) == 0) {
			BasePrint.println(" Ҫ���߸��ֶ��ˣ�������!");
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
   * ���ݱ���ɾ�����ֶΡ�
   *
   * @param strFieldAlias ����
   */
  public void removeField(String strFieldAlias) {
    //�������Ƿ���ȷ
    if (strFieldAlias == null || strFieldAlias.compareTo("") == 0) {
      return;
    }

    //����Ƿ����ָ�����ֶ�ֵ
    int intFieldCount = this.getFieldCount();

    String strUpperFieldAlias = strFieldAlias.toUpperCase().trim();
	BasePrint.println("strUpperFieldAlias is :"+strUpperFieldAlias);		
    //����
    ArrayList aliasObject = this.getFieldAlias();
    for (int i = 0; i < intFieldCount; i++) {
      //���
	  BasePrint.println("�ֶ���Ϊ:"+(String) (aliasObject.get(i)));
      if ( ( (String) (aliasObject.get(i))).compareTo(strUpperFieldAlias) == 0) {
		  BasePrint.println(" Ҫ���߸��ֶ��ˣ�������!");
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
   * <p>���ָ���ֶε�ֵ���б�</p><br>
   *
   * <ul>
   * <li>�����ӵ��ֶ�������BaseDataType.INTERGER����װ��ֵ������Integer��</li>
   * <li>�����ӵ��ֶ�������BaseDataType.STRING����װ��ֵ������String��</li>
   * <li>�����ӵ��ֶ�������BaseDataType.NUMBER����װ��ֵ������BigDecimal��</li>
   * <li>�����ӵ��ֶ�������BaseDataType.DATE����װ��ֵ������java.sql.Date��</li>
   * </ul>
   *
   * @param strFieldName �ֶ�����
   * @param strFieldAlias �ֶα���
   * @param bdtFieldType �ֶ���������
   * @param objFieldValue �ֶε�ֵ
   * @param intFieldLength �ֶεĳ���
   * @param intFieldPrecision �ֶεľ���
   */
  public void addField(String strFieldName, String strFieldAlias,
                       BaseDataType bdtFieldType, Object objFieldValue,
                       Integer intFieldLength, Integer intFieldPrecision) {

    //��ӵ�ǰ�ֶ�����
    this.fieldName.add(strFieldName.toUpperCase().trim());

    //��ӵ�ǰ�ֶα���
    this.fieldAlias.add(strFieldAlias.toUpperCase().trim());

    //��ӵ�ǰ�ֶ�����
    this.fieldType.add(bdtFieldType);

    //��ӵ�ǰ�ֶ�ֵ
    this.fieldValue.add(objFieldValue);

    //��ӵ�ǰ�ֶεĳ���
    this.fieldLength.add(intFieldLength);

    //��ӵ�ǰ�ֶεľ���
    this.fieldPrecision.add(intFieldPrecision);

    //�����ֶ�����
    this.fieldCount++;

  }

  /**
   * <p>���ָ���ֶε�ֵ���б�</p><br>
   *
   * <ul>
   * <li>�����ӵ��ֶ�������BaseDataType.INTERGER����װ��ֵ������Integer��</li>
   * <li>�����ӵ��ֶ�������BaseDataType.STRING����װ��ֵ������String��</li>
   * <li>�����ӵ��ֶ�������BaseDataType.NUMBER����װ��ֵ������BigDecimal��</li>
   * <li>�����ӵ��ֶ�������BaseDataType.DATE����װ��ֵ������java.sql.Date��</li>
   * </ul>
   *
   * @param strFieldName �ֶ�����
   * @param strFieldAlias �ֶα���
   * @param bdtFieldType �ֶ���������
   * @param objFieldValue �ֶε�ֵ
   * @param intFieldLength �ֶεĳ���
   * @param intFieldPrecision �ֶεľ���
   */
  public void addField(String strFieldName, String strFieldAlias,
                       BaseDataType bdtFieldType, Object objFieldValue,
                       int intFieldLength, int intFieldPrecision) {
    this.addField(strFieldName, strFieldAlias, bdtFieldType, objFieldValue,
                  new Integer(intFieldLength), new Integer(intFieldPrecision));
  }

  /**
   * <p>�滻ָ�����ֶ�ֵ�����û�У��½��ֶ�ֵ��</p><br>
   *
   * <ul>
   *   <li>�����ֶ�����strFieldName���ֶα���strFieldAlias�����Ƿ���ڸ��ֶΡ�</li>
   *   <li>��������ֶΣ�ֱ�ӽ��ֶ�ֵobjFieldValue������ֶΡ�</li>
   *   <li>��������ڸ��ֶΣ�������addField��Ӹ��ֶΡ�</li>
   * </ul>
   *
   * <ul>
   * <li>�����ӵ��ֶ�������BaseDataType.INTERGER����װ��ֵ������Integer��</li>
   * <li>�����ӵ��ֶ�������BaseDataType.STRING����װ��ֵ������String��</li>
   * <li>�����ӵ��ֶ�������BaseDataType.NUMBER����װ��ֵ������BigDecimal��</li>
   * <li>�����ӵ��ֶ�������BaseDataType.DATE����װ��ֵ������java.sql.Date��</li>
   * </ul>
   *
   *
   *
   * @param strFieldName �ֶ�����
   * @param strFieldAlias �ֶα���
   * @param bdtFieldType �ֶ�����
   * @param objFieldValue �ֶ�ֵ
   */
  public void setFieldValue(String strFieldName, String strFieldAlias,
                            int intFieldType, Object objFieldValue) {
    this.setFieldValue(strFieldName, strFieldAlias,
                       new BaseDataType(intFieldType), objFieldValue);
  }

  /**
   * <p>�滻ָ�����ֶ�ֵ�����û�У��½��ֶ�ֵ��</p><br>
   *
   * <ul>
   *   <li>�����ֶ�����strFieldName���ֶα���strFieldAlias�����Ƿ���ڸ��ֶΡ�</li>
   *   <li>��������ֶΣ�ֱ�ӽ��ֶ�ֵobjFieldValue������ֶΡ�</li>
   *   <li>��������ڸ��ֶΣ�������addField��Ӹ��ֶΡ�</li>
   * </ul>
   *
   * <ul>
   * <li>�����ӵ��ֶ�������BaseDataType.INTERGER����װ��ֵ������Integer��</li>
   * <li>�����ӵ��ֶ�������BaseDataType.STRING����װ��ֵ������String��</li>
   * <li>�����ӵ��ֶ�������BaseDataType.NUMBER����װ��ֵ������BigDecimal��</li>
   * <li>�����ӵ��ֶ�������BaseDataType.DATE����װ��ֵ������java.sql.Date��</li>
   * </ul>
   *
   *
   *
   * @param strFieldName �ֶ�����
   * @param strFieldAlias �ֶα���
   * @param bdtFieldType �ֶ�����
   * @param objFieldValue �ֶ�ֵ
   */
  public void setFieldValue(String strFieldName, String strFieldAlias,
                            BaseDataType bdtFieldType, Object objFieldValue) {

    //�������Ƿ���ȷ
    if (strFieldName == null || strFieldName.compareTo("") == 0 ||
        strFieldAlias == null || strFieldAlias.compareTo("") == 0) {
      return;
    }

    //����Ƿ����ָ�����ֶ�ֵ
    int intFieldCount = this.getFieldCount();

    String strUpperFieldName = strFieldName.toUpperCase().trim();
    String strUpperFieldAlias = strFieldAlias.toUpperCase().trim();

    //����
    ArrayList nameObject = this.getFieldName();
    ArrayList aliasObject = this.getFieldAlias();

    int i = 0;
    boolean isAdd = true;
    for (i = 0; i < intFieldCount; i++) {
      //���
      if ( ( (String) (nameObject.get(i))).compareTo(strUpperFieldName) == 0 &&
          ( (String) (aliasObject.get(i))).compareTo(strUpperFieldAlias) == 0) {
        isAdd = false;
        break;
      }
    }

    //
    if (isAdd) {
      //��������ڣ������ֶ�
      this.addField(strFieldName, strFieldAlias, bdtFieldType, objFieldValue);
    }
    else {
      //������ڣ�����
      ArrayList typeObject = this.getFieldType();
      typeObject.set(i, bdtFieldType);
      this.setFieldType(typeObject);
      ArrayList valueObject = this.getFieldValue();
      valueObject.set(i, objFieldValue);
      this.setFieldValue(valueObject);
    }

  }

  /**
   * ���ƻ���������
   *
   * @param obj ָ����Ҫ���ƵĻ���������
   * @return ���ƵĻ���������
   */
  public static BaseObject clone(BaseObject obj) {


    //�½�����������
    BaseObject createObj = new BaseObject();

    //����ָ���Ļ���������
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
   * �õ�ָ���ֶ��ַ�����ʾ
   *
   * @param strFieldAlias ����
   * @param dateFormat �����ַ�����ʽ
   * @return �ֶ��ַ�����ʾ
   */
  public String toString(String strFieldAlias, int dateFormat) {

    //���ݱ����õ��ֶ�
    //�ж��ֶ�����

    //����ֶα���
    if (strFieldAlias == null) {
      return "";
    }

    //�������������е��ֶ�����
    int fieldCount = this.getFieldCount();
    if (fieldCount == 0) {
      return "";
    }

    //ȫ��ת���ɴ�дģʽ
    String ts = strFieldAlias.toUpperCase().trim();

    //����ֶα����б�������ָ�����ֶα���
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

    //���û�ҵ�
    if (index == -1) {
      return "";
    }

    //�ж�����
    if ( ( (BaseDataType)this.getFieldType().get(index)).getDataType() ==
        BaseDataType.DATE) {
      //��������
      return PromosDate.checkErrorDateString(this.toString(strFieldAlias),
                                             dateFormat);
    }

    return this.toString(strFieldAlias);
  }

  /**
   * �õ�ָ���ֶ�ֵ���ַ�����ʾ
   *
   * @param fieldAlias ָ�����ֶα���
   * @return �ֶ�ֵ
   */
  public String toString(String fieldAlias) {
    return BaseObject.toString(this, fieldAlias);
  }

  /**
   * ����ָ���Ļ������ݣ�ת���ɿ���ʾ���ַ���
   *
   * @param obj ָ���Ļ�������
   * @param fieldAlias ָ���Ļ��������е��ֶα���
   * @return �ַ���ֵ
   */
  public static String toString(BaseObject obj, int fieldkey) {

	    //�����ַ���
	    String rs = new String("");

	    //������������
	    if (obj == null) {
	      return rs;
	    }

	    //�������������е��ֶ�����
	    int fieldCount = obj.getFieldCount();
	    if (fieldCount == 0) {
	      return rs;
	    }

	  
      //�ҵ�ָ���ֶ����ƺ��ҵ��ֶ�����
      BaseDataType bdt = (BaseDataType) ( (obj.getFieldType()).get(fieldkey));
      Object fieldValue = (obj.getFieldValue()).get(fieldkey);

      //�����ֶ����͵õ��ַ���
      switch (bdt.getDataType()) {

        //����ǣ�INVALID
        case BaseDataType.INVALID:
          break;

          //����ǣ�INTEGER
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

          //����ǣ�NUMBER
        case BaseDataType.NUMBER:
          if (fieldValue == null) {
            rs = "";
          }
          else {
            rs = ( (BigDecimal) fieldValue).toString();
          }

          break;

          //����ǣ�STRING
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

          //����ǣ�DATE
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
          
          //����ǣ�DATETIME
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
          

          //����ǣ�CHAR_STREAM
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
          //����ǣ�LONG
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


          //����ǣ�CHAR_STREAM
        case BaseDataType.BLOB:
          if (fieldValue == null) {
            rs = null;
          }
          else {
            rs = fieldValue.toString();
          }

          break;

          //����ǣ�
        case BaseDataType.STREAM:
          if (fieldValue == null) {
            rs = null;
          }
          else {
            rs = fieldValue.toString();
          }

      }
	  
	  
      //����ǰ��ȥ��ǰ��Ŀո�
      if (rs != null && rs.compareTo("") != 0) {
        rs = rs.trim();
      }

      return rs;
	  
  }  
  
  /**
   * ����ָ���Ļ������ݣ�ת���ɿ���ʾ���ַ���
   *
   * @param obj ָ���Ļ�������
   * @param fieldAlias ָ���Ļ��������е��ֶα���
   * @return �ַ���ֵ
   */
  public static String toString(BaseObject obj, String fieldAlias) {

    //�����ַ���
    String rs = new String("");

    //������������
    if (obj == null) {
      return rs;
    }

    //����ֶα���
    if (fieldAlias == null) {
      return rs;
    }

    //�������������е��ֶ�����
    int fieldCount = obj.getFieldCount();
    if (fieldCount == 0) {
      return rs;
    }

    //ȫ��ת���ɴ�дģʽ
    String ts = fieldAlias.toUpperCase().trim();

    //����ֶα����б�������ָ�����ֶα���
    ArrayList tempAL = obj.getFieldAlias();
    String tempFieldAlias = new String("");
    for (int i = 0; i < fieldCount; i++) {
      tempFieldAlias = (String) (tempAL.get(i));
      if (tempFieldAlias.compareTo(ts) == 0) {

        //�ҵ�ָ���ֶ����ƺ��ҵ��ֶ�����
        BaseDataType bdt = (BaseDataType) ( (obj.getFieldType()).get(i));
        Object fieldValue = (obj.getFieldValue()).get(i);

        //�����ֶ����͵õ��ַ���
        switch (bdt.getDataType()) {

          //����ǣ�INVALID
          case BaseDataType.INVALID:
            break;

            //����ǣ�INTEGER
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

            //����ǣ�NUMBER
          case BaseDataType.NUMBER:
            if (fieldValue == null) {
              rs = "";
            }
            else {
              rs = ( (BigDecimal) fieldValue).toString();
            }

            break;

            //����ǣ�STRING
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

            //����ǣ�DATE
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

            //����ǣ�DATETIME
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
            
            //����ǣ�CHAR_STREAM
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
            //����ǣ�LONG
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


            //����ǣ�CHAR_STREAM
          case BaseDataType.BLOB:
            if (fieldValue == null) {
              rs = null;
            }
            else {
              rs = fieldValue.toString();
            }

            break;

            //����ǣ�
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

    //����ǰ��ȥ��ǰ��Ŀո�
    if (rs != null && rs.compareTo("") != 0) {
      rs = rs.trim();
    }

    return rs;
  }

  /**
   * ����ָ���Ļ������ݣ�ת������������
   *
   * @param obj ָ���Ļ�������
   * @param fieldAlias ָ���Ļ��������е��ֶα���
   * @return ��������
   */
  public static Integer toInteger(BaseObject obj, String fieldAlias) {
    //������������
    Integer rs = new Integer(0);

    //������������
    if (obj == null) {
      return rs;
    }

    //����ֶα���
    if (fieldAlias == null) {
      return rs;
    }

    //ȫ��ת���ɴ�дģʽ
    String ts = fieldAlias.toUpperCase().trim();

    //�������������е��ֶ�����
    int fieldCount = obj.getFieldCount();
    if (fieldCount == 0) {
      return rs;
    }

    //����ֶα����б�������ָ�����ֶα���
    ArrayList tempAL = obj.getFieldAlias();
    String tempFieldAlias = new String("");
    for (int i = 0; i < fieldCount; i++) {
      tempFieldAlias = (String) (tempAL.get(i));
      if (tempFieldAlias.compareTo(ts) == 0) {

        //�ҵ�ָ���ֶ����ƺ��ҵ��ֶ�����
        BaseDataType bdt = (BaseDataType) ( (obj.getFieldType()).get(i));
        Object fieldValue = (obj.getFieldValue()).get(i);

        //�����ֶ����͵õ��ַ���
        switch (bdt.getDataType()) {

          //����ǣ�INVALID
          case BaseDataType.INVALID:
            break;

            //����ǣ�INTEGER
          case BaseDataType.INTEGER:
            if (fieldValue == null) {
              rs = new Integer(0);
            }
            else {
              rs = (Integer) fieldValue;
            }

            break;

            //����ǣ�NUMBER
          case BaseDataType.NUMBER:
            if (fieldValue == null) {
              rs = new Integer(0);
            }
            else {
              rs = new Integer( ( (BigDecimal) fieldValue).intValue());
            }

            break;

            //����ǣ�STRING
          case BaseDataType.STRING:
            if (fieldValue == null) {
              rs = new Integer(0);
            }
            else {
              rs = Integer.valueOf( (String) fieldValue);
            }

            break;

            //����ǣ�DATE
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
   * ����ָ���Ļ������ݣ�ת������������
   *
   * @param obj ָ���Ļ�������
   * @param fieldAlias ָ���Ļ��������е��ֶα���
   * @return ��������
   */
  public static Long toLong(BaseObject obj, String fieldAlias) {
    //������������
    Long rs = new Long(0);

    //������������
    if (obj == null) {
      return rs;
    }

    //����ֶα���
    if (fieldAlias == null) {
      return rs;
    }

    //ȫ��ת���ɴ�дģʽ
    String ts = fieldAlias.toUpperCase().trim();

    //�������������е��ֶ�����
    int fieldCount = obj.getFieldCount();
    if (fieldCount == 0) {
      return rs;
    }

    //����ֶα����б�������ָ�����ֶα���
    ArrayList tempAL = obj.getFieldAlias();
    String tempFieldAlias = new String("");
    for (int i = 0; i < fieldCount; i++) {
      tempFieldAlias = (String) (tempAL.get(i));
      if (tempFieldAlias.compareTo(ts) == 0) {

        //�ҵ�ָ���ֶ����ƺ��ҵ��ֶ�����
        BaseDataType bdt = (BaseDataType) ( (obj.getFieldType()).get(i));
        Object fieldValue = (obj.getFieldValue()).get(i);

        //�����ֶ����͵õ��ַ���
        switch (bdt.getDataType()) {

          //����ǣ�INVALID
          case BaseDataType.INVALID:
            break;

            //����ǣ�INTEGER
          case BaseDataType.INTEGER:
            if (fieldValue == null) {
              rs = new Long(0l);
            }
            else {
              rs = new Long( ( (Integer) fieldValue).longValue());
            }

            break;

            //����ǣ�NUMBER
          case BaseDataType.NUMBER:
            if (fieldValue == null) {
              rs = new Long(0l);
            }
            else {
              rs = new Long( ( (BigDecimal) fieldValue).longValue());
            }
            break;

            //����ǣ�STRING
          case BaseDataType.STRING:
            if (fieldValue == null) {
              rs = new Long(0l);
            }
            else {
              rs = Long.valueOf( (String) fieldValue);
            }
            break;

            //����ǣ�DATE
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
