package promos.base.objects;

//import java.util.ArrayList;
import java.util.Date;
import java.math.BigDecimal;
import java.sql.Blob;

import java.io.Serializable;

/**
 *
 * <p>����: �� BaseDataType </p>
 * <p>����: �������������� </p>
 * <p>��Ȩ: Copyright (c) 2003-2004 </p>
 * <p>��˾: ��������ý���޹�˾ </p>
 * ��������Ȩ����
 *
 * @author ��ά��
 * @version 1.0 06/26/2003
 *
 */

public class BaseDataType
    implements Serializable {

  /**
   * ��ǰ������������Ч����������
   */
  public static final int INVALID = 0;

  /**
   * ��ǰ�����������������ͣ�Integer
   */
  public static final int INTEGER = 1;

  /**
   * ��ǰ����������Number���ͣ�����int��long��float��double
   */
  public static final int NUMBER = 2;

  /**
   * ��ǰ�����������ַ������ͣ�String
   */
  public static final int STRING = 3;

  /**
   * ��ǰ�����������������ͣ�Date
   */
  public static final int DATE = 4;

  /**
   * ��ǰ�����������������ͣ�Datetime
   */
  public static final int DATETIME = 9;

  /**
   * ��ǰ���������Ǵ��ֶ����ͣ�
   */
  public static final int STREAM = 5;

  /**
   * ��ǰ���������Ǵ��ֶ����ͣ�
   */
  public static final int CHAR_STREAM = 6;

  /**
   * ��ǰ���������Ǵ��ֶ����ͣ�
   */
  public static final int BLOB = 7;

  public static final int LONG = 8;

  //��ǰ��������
  private int dataType;

  /**
   * ����
   */
  public BaseDataType() {
    this.dataType = BaseDataType.INVALID;
  }

  /**
   * ������������������
   *
   * @param baseDataType ָ���Ļ�����������
   */
  public BaseDataType(int baseDataType) {
    this.dataType = baseDataType;
  }

  /**
   * �õ�������������
   *
   * @return ������������
   */
  public int getDataType() {
    return this.dataType;
  }

  /**
   * �趨������������
   *
   * @param i ������������
   */
  public void setDataType(int i) {
    this.dataType = i;
  }

  /**
   * �����ֶ��������Ƶõ�������������
   *
   * @param fieldTypeName �ֶ���������
   * @return ������������
   */
  public static BaseDataType getBaseDataType(String fieldTypeName) {
    BaseDataType bdt = new BaseDataType();
    
    //System.out.println("fieldTypeName:"+fieldTypeName);

    //����ֶ���������
    if (fieldTypeName == null) {
      bdt.setDataType(BaseDataType.INVALID);
      return bdt;
    }

    //ת����Сд
    String strName = (fieldTypeName.toLowerCase()).trim();

    //������ݿ��ֶ����ͣ�ת���ɺ��ʵ�����

    //����ǣ�INTEGER ��������,С������
    if (strName.compareTo("integer") == 0) {
      bdt.setDataType(BaseDataType.INTEGER);
      return bdt;
    }

    //����ǣ�NUMBER(P,S) ��������,PΪ����λ��SΪС��λ
    if (strName.compareTo("number") == 0) {
      bdt.setDataType(BaseDataType.NUMBER);
      return bdt;
    }

    //����ǣ�VARCHAR2 �ɱ䳤�ȵ��ַ���,��󳤶�4000 bytes,������������󳤶�749
    if (strName.compareTo("varchar2") == 0) {
      bdt.setDataType(BaseDataType.STRING);
      return bdt;
    }

    //����ǣ�CHAR �̶������ַ���,��󳤶�2000 bytes
    if (strName.compareTo("char") == 0) {
      bdt.setDataType(BaseDataType.STRING);
      return bdt;
    }

    //����ǣ�FLOAT ����������,NUMBER(38)��˫����
    if (strName.compareTo("float") == 0) {
      bdt.setDataType(BaseDataType.NUMBER);
      return bdt;
    }

    //����ǣ�DATE ���ڣ���-��-�꣩,DD-MM-YY��HH-MI-SS��
    if (strName.compareTo("date") == 0) {
      bdt.setDataType(BaseDataType.DATE);
      return bdt;
    }

    //����ǣ�DATETIME DD-MM-YY HH-MI
    if (strName.compareTo("datetime") == 0) {
      bdt.setDataType(BaseDataType.DATETIME);
      return bdt;
    }

    //����ǣ�NCHAR �����ַ��������Ĺ̶������ַ���,��󳤶�2000 bytes
    if (strName.compareTo("nchar") == 0) {
      bdt.setDataType(BaseDataType.STRING);
      return bdt;
    }

    //����ǣ�NVARCHAR2 �����ַ��������Ŀɱ䳤���ַ���,��󳤶�4000 bytes
    if (strName.compareTo("nvarchar2") == 0) {
      bdt.setDataType(BaseDataType.STRING);
      return bdt;
    }

    //����ǣ�DECIMAL(P,S) ��������,PΪ����λ��SΪС��λ
    if (strName.compareTo("decimal") == 0) {
      bdt.setDataType(BaseDataType.NUMBER);
      return bdt;
    }

    //����ǣ�REAL ʵ������,NUMBER(63)�����ȸ���
    if (strName.compareTo("real") == 0) {
      bdt.setDataType(BaseDataType.NUMBER);
      return bdt;
    }

    //����ǣ�LONG �����ַ���,��󳤶�2G
    if (strName.compareTo("long") == 0) {
      bdt.setDataType(BaseDataType.LONG);
      return bdt;
    }

    //����ǣ�RAW �̶����ȵĶ���������,��󳤶�2000 bytes ,��Ŷ�ý��ͼ��������
    if (strName.compareTo("raw") == 0) {
      bdt.setDataType(BaseDataType.STREAM);
      return bdt;
    }

    //����ǣ�LONG RAW �ɱ䳤�ȵĶ���������,��󳤶�2G,��Ŷ�ý��ͼ��������
    if (strName.compareTo("long raw") == 0) {
      bdt.setDataType(BaseDataType.STREAM);
      return bdt;
    }

    //����ǣ�BLOB ����������,��󳤶�4G
    if (strName.compareTo("blob") == 0) {
      bdt.setDataType(BaseDataType.BLOB);
      return bdt;
    }

    //����ǣ�CLOB �ַ�����,��󳤶�4G
    if (strName.compareTo("clob") == 0) {
      bdt.setDataType(BaseDataType.CHAR_STREAM);
      return bdt;
    }

    //����ǣ�NCLOB �����ַ����������ַ�����,��󳤶�4G
    if (strName.compareTo("nclob") == 0) {
      bdt.setDataType(BaseDataType.CHAR_STREAM);
      return bdt;
    }

    //����ǣ�BFILE ��������ݿ���Ķ���������,��󳤶�4G
    if (strName.compareTo("bfile") == 0) {
      bdt.setDataType(BaseDataType.STREAM);
      return bdt;
    }

    //����ǣ�ROWID ���ݱ��м�¼��Ψһ�к�,10 bytes ********.****.****��ʽ��*Ϊ0��1
    if (strName.compareTo("rowid") == 0) {
      bdt.setDataType(BaseDataType.STREAM);
      return bdt;
    }

    //����ǣ�NROWID ���������ݱ��м�¼��Ψһ�к�,��󳤶�4000 bytes
    if (strName.compareTo("nrowid") == 0) {
      bdt.setDataType(BaseDataType.STREAM);
      return bdt;
    }

    /*********************����ΪSybase��������************************/
    
    //����ǣ�VARCHAR �ɱ䳤�ȵ��ַ���,��󳤶�256 bytes
    if (strName.compareTo("varchar") == 0) {
      bdt.setDataType(BaseDataType.STRING);
      return bdt;
    }
    
    //����ǣ�TEXT 
    if (strName.compareTo("text") == 0) {
      bdt.setDataType(BaseDataType.STRING);
      return bdt;
    }
    
 
    //����ǣ�NUMERIC 
    if (strName.compareTo("numeric") == 0) {
      bdt.setDataType(BaseDataType.NUMBER);
      return bdt;
    }
    

    //����ǣ�CHAR 
    if (strName.compareTo("char") == 0) {
      bdt.setDataType(BaseDataType.STRING);
      return bdt;
    }
 
    //����ǣ�INT 
    if (strName.compareTo("int") == 0) {
      bdt.setDataType(BaseDataType.INTEGER);
      return bdt;
    }

    //����ǣ�DATE 
    if (strName.compareTo("date") == 0) {
      bdt.setDataType(BaseDataType.DATE);
      return bdt;
    }
    
    //����ǣ�DATETIME 
    if (strName.compareTo("datetime") == 0) {
      bdt.setDataType(BaseDataType.DATETIME);
      return bdt;
    }
   
    /*********************Sybase�������ͽ���************************/
    
    
     //�Ҳ������趨ΪSTRING
    bdt.setDataType(BaseDataType.STRING);
    return bdt;
  }

  /**
   * �õ�Ĭ�ϵ��ֶ�ֵ
   *
   * @param fieldType �ֶ�����
   * @return �ֶ�ֵ
   */
  public static Object getDefaultValue(int fieldType) {

    Object obj = new Object();

    //�����ֶ����͵õ�Ĭ�ϵ��ֶ�ֵ
    switch (fieldType) {

      //����ǣ�INVALID
      case BaseDataType.INVALID:
        break;

        //����ǣ�INTEGER
      case BaseDataType.INTEGER:
        obj = new Integer(0);
        break;

        //����ǣ�NUMBER
      case BaseDataType.NUMBER:
        obj = new BigDecimal("0");
        break;

        //����ǣ�STRING
      case BaseDataType.STRING:
        obj = new String("");
        break;

        //����ǣ�DATE
      case BaseDataType.DATE:
        obj = new Date();
        break;

        //����ǣ�DATETIME
      case BaseDataType.DATETIME:
        obj = new Date();
        break;

        //����ǣ�STREAM CHAR_STREAM
      case BaseDataType.STREAM:
      case BaseDataType.CHAR_STREAM:
        obj = new String("");
        break;

    }

    return obj;
  }

}
