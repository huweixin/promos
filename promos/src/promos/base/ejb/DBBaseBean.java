package promos.base.ejb;

import java.rmi.RemoteException;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import java.sql.Date;
import java.sql.Timestamp;
import java.sql.Clob;
import java.sql.Blob;

import java.util.ArrayList;
import java.math.BigDecimal;

import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.CreateException;
import javax.sql.DataSource;

import promos.base.objects.BaseContext;
import promos.base.objects.BaseDataType;
import promos.base.objects.BaseException;
import promos.base.objects.BaseObject;
import promos.base.objects.BasePrint;
import promos.base.objects.BaseConfig;
import promos.base.objects.FieldError;
import promos.base.objects.FormatTools;
import promos.base.objects.PromosDate;



/**
 *
 * <p>标题: Bean DBBaseBean </p>
 * <p>描述: 基类Bean </p>
 * <p>版权: Copyright (c) 2003-2004 </p>
 * <p>公司: 广州升域传媒有限公司 </p>
 * 保留所有权利。
 *
 * @author 胡维新
 * @version 1.0 04/22/2003
 *
 */
@Stateless

public class DBBaseBean implements DBBase {

  SessionContext sessionContext;
  
  private static String dataSourceName="";
  private final static String DB_USER = BaseConfig.getSettingByName("db_user"); /*系统数据库用户*/

  /**
   * Bean容器自己根据需要创建Bean实例
   *
   * @throws CreateException
   */
  static {
	    setDataJndi();
  }
  private static void setDataJndi() {
	    if (dataSourceName.equals(""))
	    {
	      dataSourceName= BaseConfig.getJNDIByName("DSPromos");
		  //BasePrint.println("dataSourceName:"+dataSourceName);
	    }
  }
  public void ejbCreate() throws CreateException {

  }

  /**
   * <p>Bean容器自己根据需要删除Bean实例 </p>
   *
   */

  public void ejbRemove() {

  }

  /**
   * 重新得到被串行化的Bean实例
   *
   */

  public void ejbActivate() {

  }

  /**
   * 串行化Bean实例，释放资源
   *
   */
  public void ejbPassivate() {

  }

  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
}

  /**
   * 得到数据源
   *
   * @param  dataSourceName 数据源JNDI命名
   * @return 数据源
   * @throws RemoteException
   */
  public DataSource getDataSource() throws RemoteException {

    DataSource ds = null;

    try {
      ds = (DataSource)(BaseContext.getInitialContext().lookup(dataSourceName));
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return ds;
  }

  /**
   * 得到连接
   *
   * @param dataSourceName 数据源JNDI命名
   * @return 连接
   * @throws RemoteException
   */
  public Connection getConnection() throws RemoteException {

    Connection con = null;

    try {
      DataSource ds = getDataSource();
      con = ds.getConnection();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return con;
  }

  /**
   * 得到指定表或视图的结构，仅包含数字型和字符串型
   *
   *
   * @param strDataSource 数据源的JNDI名称
   * @param strTableName 表或视图的名称
   * @return 表或视图的机构
   * @throws RemoteException
   */
  public BaseObject getSimpleFieldByName(String strTableName) throws
      RemoteException {
    return getTableFieldByName(strTableName, true);
  }

  /**
   * 得到指定表或视图的结构
   *
   *
   * @param strDataSource 数据源的JNDI名称
   * @param strTableName 表或视图的名称
   * @return 表或视图的机构
   * @throws RemoteException
   */
  public BaseObject getTableByName(String strTableName) throws
      RemoteException {
    return getTableFieldByName(strTableName, false);
  }

  public FieldError checkField(BaseObject baseObj) throws RemoteException {
    BaseObject meta = getTableFieldByName(baseObj.getTableName(), false);
    ArrayList aliasList = baseObj.getFieldAlias();
    ArrayList typeList = baseObj.getFieldType();

    for (int i = 0; i < aliasList.size(); i++) {
      String alias = (String)aliasList.get(i);
      BaseDataType type = (BaseDataType)typeList.get(i);
      if (type.getDataType() == BaseDataType.STRING) {
        String strValue = baseObj.toString(alias);

        int maxLenth = meta.getFieldLength(alias);
        if (strValue.getBytes().length > maxLenth) {
          FieldError field = new FieldError();
          field.alias = alias;
          field.dataType = BaseDataType.STRING;
          field.len = maxLenth;
          field.errorCode = FieldError.VALUE_TOO_LARGER;
          return field;
        }
      }
    }

    return null;
  }

  //得到指定表或视图的结构
  private BaseObject getTableFieldByName(String strTableName, boolean isSimple) throws
      RemoteException {

    BaseObject rs = new BaseObject();

    if (strTableName == null || strTableName.compareTo("") == 0) {
      return rs;
    }

    //得到指定表类
    Connection con = null;
    Statement stmt = null;
    ResultSet sqlrs = null;

    //表的字段数量
    String sqlString = "  select * from " + strTableName + " where rownum=1 ";

    //显示SQL语句
    BasePrint.print("getTableFieldByName : ", sqlString, true);

    try {

      //得到连接
      con = this.getConnection();
      stmt = con.createStatement();

      //查询指定的数据
      sqlrs = stmt.executeQuery(sqlString);

      //得到表或视图的结构
      rs = this.getTableFieldByResultSet(sqlrs, isSimple);

      //添加表或视图名称
      rs.setTableName(strTableName);

    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {

      if (sqlrs != null) {
        try {
          sqlrs.close();
        }
        catch (Exception ignore) {
        }
      }

      if (stmt != null) {
        try {
          stmt.close();
        }
        catch (Exception ignore) {
        }
      }

      if (con != null) {
        try {
          con.close();
        }
        catch (Exception ignore) {
        }
      }

    }

    return rs;

  }

  /**
   * 得到表的字段名称、查询时的字段别名、字段类型和字段数量
   *
   * @param rs 查询数据集
   * @return 基本数据类
   * @throws RemoteException
   */
  public BaseObject getTableByResultSet(ResultSet rs) throws RemoteException {

    //基本数据类
    BaseObject obj = getTableFieldByResultSet(rs, false);

    return obj;
  }

  //得到字段结构
  private BaseObject getTableFieldByResultSet(ResultSet rs, boolean isSimple) throws
      RemoteException {

    //基本数据类
    BaseObject obj = new BaseObject();

    try {

      //得到表结构
      ResultSetMetaData rsmd = rs.getMetaData();

      //首先得到字段数
      int fieldCount = rsmd.getColumnCount();

      //得到字段名称
      int i = 1;
      String fieldName = "";
      String fieldTypeName = "";
      //int fieldTypeMark = 0;
      int fieldLength = 0;
      int fieldPrecision = 0;
      String fieldAlias = "";

      BaseDataType fieldType = new BaseDataType();
      Object fieldValue = new Object();

      for (i = 1; i <= fieldCount; i++) {

        //得到字段名称
        fieldName = rsmd.getColumnName(i);

        //得到字段类型名称
        fieldTypeName = rsmd.getColumnTypeName(i).toUpperCase().trim();

        //得到字段类型是否存在小数位
        //fieldTypeMark = rsmd.getScale(i);

        fieldLength = rsmd.getColumnDisplaySize(i);

        //fieldPrecision = rsmd.getPrecision(i);

        //根据字段类型名称得到基本数据类型
        //BasePrint.println(fieldName + "   " + fieldTypeName + "  i" + i);
        fieldType = BaseDataType.getBaseDataType(fieldTypeName);

        //得到字段别名
        fieldAlias = rsmd.getColumnLabel(i).toUpperCase().trim();

        //得到默认的字段值
        fieldValue = BaseDataType.getDefaultValue(fieldType.getDataType());

        //如果仅查找基本数据
        if (isSimple) {
          int intFieldType = fieldType.getDataType();
          if (intFieldType == BaseDataType.INTEGER ||
              intFieldType == BaseDataType.NUMBER ||
              intFieldType == BaseDataType.STRING) {
            //添加
            obj.addField(fieldName, fieldAlias, fieldType, fieldValue,
                         fieldLength, fieldPrecision);

          }

        }
        else {

          //添加
          obj.addField(fieldName, fieldAlias, fieldType, fieldValue,
                       fieldLength, fieldPrecision);
        }

        //更新
        fieldName = new String("");
        fieldTypeName = new String("");
        fieldAlias = new String();
        fieldValue = new Object();

      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return obj;
  }

  /**
   * 查询记录
   *
   * <p>本方法使用限制如下：</p>
   * <p>1：仅限于单表或单视图查询。</p>
   * <p>2：仅限于简单条件判断查询。</p>
   * <p>3：列表中的每项条件之间是 AND 关系。</p>
   * <p>4：列表中的每一项多个条件之间是 OR 关系。</p>
   * <p>5：额外判定限定条件与列表中的每项条件之间是 AND 关系。</p>
   * <p>6：如果返回记录的项数为-1，将返回查询到的所有数据。</p>
   * <p>7：如果返回记录的项数大于0，将返回查询到的分页数据。
   * 起止索引是：( selectIndex+1 , selectIndex+selectNumber )</p>
   * <p>8：如果查询标记为0，查询记录，如果为1，查询记录的数量。
   * 数量名称是SELECT_ALL_NUMBER。</p>
   *
   * @param dataSourceName DataSource的名称
   * @param tableName 表名或视图的名称
   * @param al 查询SQL条件
   * @param strStatus 查询SQL额外的限定条件
   * @param selectIndex 返回记录的首项
   * @param selectNumber 返回记录的项数
   * @param status 查询记录还是查询数量标志
   * @return 查询记录列表
   * @throws RemoteException
   */
  public ArrayList selectSQL(String tableName,
                             ArrayList al, String strStatus, int selectIndex,
                             int selectNumber, int status) throws
      RemoteException {

    return this.selectSQL(tableName, " * ", al, strStatus,
                          selectIndex, selectNumber, status);

  }

  /**
   * 查询记录
   *
   * <p>本方法使用限制如下：</p>
   * <p>1：仅限于单表或单视图查询。</p>
   * <p>2：仅限于简单条件判断查询。</p>
   * <p>3：列表中的每项条件之间是 AND 关系。</p>
   * <p>4：列表中的每一项多个条件之间是 OR 关系。</p>
   * <p>5：额外判定限定条件与列表中的每项条件之间是 AND 关系。</p>
   * <p>6：如果返回记录的项数为-1，将返回查询到的所有数据。</p>
   * <p>7：如果返回记录的项数大于0，将返回查询到的分页数据。
   * 分页数据的起止索引是：( selectIndex+1 , selectIndex+selectNumber )</p>
   * <p>8：如果查询标记为0，查询记录，如果为1，查询记录的数量。
   * 记录的数量名称是SELECT_ALL_NUMBER。</p>
   *
   * @param dataSourceName DataSource的名称
   * @param tableName 表名或视图的名称
   * @param strFields 指定查询的字段
   * @param al 查询SQL条件
   * @param strStatus 查询SQL额外的限定条件
   * @param selectIndex 返回记录的首项
   * @param selectNumber 返回记录的项数
   * @param status 查询记录还是查询数量标志
   * @return 查询记录列表
   * @throws RemoteException
   */
  public ArrayList selectSQL(String tableName,
                             String strFields, ArrayList al, String strStatus,
                             int selectIndex, int selectNumber, int status) throws
      RemoteException {

    StringBuffer sqlString = new StringBuffer("");

    if (status == 1) {
      //查询记录数量
      sqlString.append(" select count(1) as SELECT_ALL_NUMBER from ");
      sqlString.append(tableName);
    }
    else if (selectNumber == -1) {
      //查询全部记录
      sqlString.append(" select ");
      sqlString.append(strFields);
      sqlString.append(" from ");
      sqlString.append(tableName);
    }
    else {
      //查询分页指定记录
      sqlString.append(" select ");
      sqlString.append(strFields);
      sqlString.append(" from ");
      sqlString.append(
          " ( select ROWNUM as tempRowNumIndex , tempTableName.*  from ");
      sqlString.append(tableName);
      sqlString.append(" tempTableName ");
    }

    //判断是否存在限定条件
    int checkAllSize = 0;
    int addSize = 0;

    if (al != null) {
      checkAllSize = al.size();
      for (int i1 = 0; i1 < checkAllSize; i1++) {
        addSize += ((BaseObject)(al.get(i1))).getFieldCount();
      }
    }

    if (strStatus.compareTo("") != 0 || addSize > 0 ||
        (status != 1 && selectNumber != -1)) {
      //存在查询条件时
      sqlString.append(" where ");

    }

    //循环判断是否存在限定条件
    //在限定条件列表中，规则：列表值BaseObject取 OR ，列表值之间取 AND
    //即采用( ... or ... ) and ( ... or ... ) and ( ... or ... )

    if (checkAllSize > 0) {

      for (int i = 0; i < checkAllSize; i++) {

        //取出列表值BaseObject
        BaseObject obj = (BaseObject)(al.get(i));
        int objSize = obj.getFieldCount();

        //如果设定条件
        if (objSize > 0) {

          //限定列表项之间采用 AND 模式
          if (i > 0) {
            sqlString.append(" and ");
          }

          sqlString.append(" ( ");

          //循环设定列表值，取 OR
          ArrayList nameAL = obj.getFieldName();
          ArrayList typeAL = obj.getFieldType();
          ArrayList valueAL = obj.getFieldValue();
          int typeIndex = 0;

          for (int j = 0; j < objSize; j++) {

            if (j > 0) {
              sqlString.append(" or ");
            }

            //判断类型
            typeIndex = ((BaseDataType)(typeAL.get(j))).getDataType();
            switch (typeIndex) {
              case BaseDataType.INVALID:

                //忽略无效数据
                break;
              case BaseDataType.INTEGER:
                sqlString.append((String)(nameAL.get(j)));
                sqlString.append(" = ");
                sqlString.append(((Integer)(valueAL.get(j))).intValue());
                break;
              case BaseDataType.NUMBER:
                sqlString.append((String)(nameAL.get(j)));
                sqlString.append(" = ");
                sqlString.append(((BigDecimal)(valueAL.get(j))).toString());
                break;
              case BaseDataType.STRING:
                sqlString.append((String)(nameAL.get(j)));
                sqlString.append(" = '");
                sqlString.append((String)(valueAL.get(j)));
                sqlString.append("' ");
                break;
              case BaseDataType.DATE:

                //格式是TO_DATE('2003-04-22','yyyy-MM-DD')
                Date tempDate = (Date)(valueAL.get(j));
                sqlString.append("TO_DATE(");
                sqlString.append((String)(nameAL.get(j)));
                sqlString.append(",'");
                sqlString.append(PromosDate.SHORTFORMAT);
                sqlString.append("')");

                sqlString.append(" = ");
                sqlString.append("TO_DATE('");
                sqlString.append(PromosDate.toShortFormatString(tempDate));
                sqlString.append("','");
                sqlString.append(PromosDate.SHORTFORMAT);
                sqlString.append("')");

                break;

            }

          }

          sqlString.append(" ) ");
        }

      }
    }

    //查找首项索引
    int searchFirstIndex = selectIndex * selectNumber;

    //查找末项索引
    int searchLastIndex = searchFirstIndex + selectNumber;

    //查询分页指定记录
    if (status != 1 && selectNumber > -1) {

      //检测是否存在限定条件
      if (addSize > 0) {
        sqlString.append(" and ROWNUM <= ");
      }
      else {
        sqlString.append(" ROWNUM <= ");
      }

      sqlString.append(searchLastIndex);

      //最后加上额外限定条件
      if (strStatus.compareTo("") != 0) {

        //如果没有查询条件
        sqlString.append(" and ( ");
        sqlString.append(strStatus);
        sqlString.append(" ) ");

      }

      sqlString.append(" ) where tempRowNumIndex > ");
      sqlString.append(searchFirstIndex);

    }
    else {

      //最后加上额外限定条件
      if (strStatus.compareTo("") != 0) {

        //如果没有查询条件
        if (addSize > 0) {
          sqlString.append(" and ( ");
          sqlString.append(strStatus);
          sqlString.append(" ) ");
        }
        else {
          sqlString.append(strStatus);
        }

      }
    }

    //显示查询语句
    String sql = sqlString.toString();
    BasePrint.println(" selectSQL : "+sql);

    return this.selectSQL(tableName, sqlString.toString());

  }

  /**
   * 查询记录
   *
   * <p>本方法使用限制如下：</p>
   * <p>1：仅限于单表或单视图查询。</p>
   * <p>2：仅限于简单条件判断查询。</p>
   * <p>3：列表中的每项条件之间是 AND 关系。</p>
   * <p>5：额外判定限定条件与列表中的每项条件之间是 AND 关系。</p>
   * <p>6：如果返回记录的项数为-1，将返回查询到的所有数据。</p>
   * <p>7：如果返回记录的项数大于0，将返回查询到的分页数据。
   * 起止索引是：( selectIndex+1 , selectIndex+selectNumber )</p>
   * <p>8：如果查询标记为0，查询记录，如果为1，查询记录的数量。
   * 数量名称是SELECT_ALL_NUMBER。</p>
   *
   * @param dataSourceName DataSource的名称
   * @param tableName 表名或视图的名称
   * @param al 查询SQL条件
   * @param strStatus 查询SQL额外的限定条件
   * @param strSort 查询SQL的排序条件
   * @param selectIndex 返回记录的首项
   * @param selectNumber 返回记录的项数
   * @param status 查询记录还是查询数量标志
   * @return 查询记录列表
   * @throws RemoteException
   */
  public ArrayList selectSQLEX(String tableName,
                               ArrayList al, String strStatus, String strSort,
                               int selectIndex, int selectNumber, int status) throws
      RemoteException {

    return this.selectSQLEX(tableName, " * ", al, strStatus,
                            strSort, selectIndex, selectNumber, status);

  }

  /**
   * 查询记录
   *
   * <p>本方法使用限制如下：</p>
   * <p>1：仅限于单表或单视图查询。</p>
   * <p>2：仅限于简单条件判断查询。</p>
   * <p>3：列表中的每项条件之间是 AND 关系。</p>
   * <p>5：额外判定限定条件与列表中的每项条件之间是 AND 关系。</p>
   * <p>6：如果返回记录的项数为-1，将返回查询到的所有数据。</p>
   * <p>7：如果返回记录的项数大于0，将返回查询到的分页数据。
   * 分页数据的起止索引是：( selectIndex+1 , selectIndex+selectNumber )</p>
   * <p>8：如果查询标记为0，查询记录，如果为1，查询记录的数量。
   * 记录的数量名称是SELECT_ALL_NUMBER。</p>
   *
   * @param dataSourceName DataSource的名称
   * @param tableName 表名或视图的名称
   * @param strFields 指定查询的字段
   * @param al 查询SQL条件
   * @param strStatus 查询SQL额外的限定条件
   * @param strSort 查询SQL的排序条件
   * @param selectIndex 返回记录的首项
   * @param selectNumber 返回记录的项数
   * @param status 查询记录还是查询数量标志
   * @return 查询记录列表
   * @throws RemoteException
   */
  public ArrayList selectSQLEX(String tableName,
                               String strFields, ArrayList al, String strStatus,
                               String strSort, int selectIndex,
                               int selectNumber, int status) throws
      RemoteException {

    StringBuffer sqlString = new StringBuffer("");

    if (status == 1) {
      //查询记录数量
      sqlString.append(" select count(1) as SELECT_ALL_NUMBER from ");
      sqlString.append(tableName);
    }
    else if (selectNumber == -1) {
      //查询全部记录
      sqlString.append(" select ");
      sqlString.append(strFields);
      sqlString.append(" from ");
      sqlString.append(tableName);
    }
    else {
      //查询分页指定记录
      sqlString.append(" select ");
      sqlString.append(strFields);
      sqlString.append(" from ");
      sqlString.append(
          " ( select ROWNUM as tempRowNumIndex , tempTableName.*  from ");
      sqlString.append(tableName);
      sqlString.append(" tempTableName ");
    }

    //判断是否存在限定条件
    int checkAllSize = 0;
    int addSize = 0;

    if (al != null) {
      checkAllSize = al.size();
      for (int i1 = 0; i1 < checkAllSize; i1++) {
        addSize += ((BaseObject)(al.get(i1))).getFieldCount();
      }
    }

    if (strStatus.compareTo("") != 0 || addSize > 0 ||
        (status != 1 && selectNumber != -1)) {
      //存在查询条件时
      sqlString.append(" where ");

    }

    //循环判断是否存在限定条件
    //在限定条件列表中，规则：列表值BaseObject取 BaseObject＋1的值 ，列表值之间取 AND
    //即采用( ... (=/>/</>=/<=/<>/like) (or/and)  ... ) and ( ... )

    if (checkAllSize > 0) {

      for (int i = 0; i < checkAllSize; i++) {

        //取出列表值BaseObject
        BaseObject obj = (BaseObject)(al.get(i));
        int objSize = obj.getFieldCount();

        //如果设定条件
        if (objSize > 0) {

          //限定列表项之间采用 AND 模式
          if (i > 0) {
            sqlString.append(" and ");
          }

          sqlString.append(" ( ");

          //循环设定列表值，取 OR
          ArrayList nameAL = obj.getFieldName();
          ArrayList typeAL = obj.getFieldType();
          ArrayList valueAL = obj.getFieldValue();
          int typeIndex = 0;

          //查找值和处理模式
          for (int j = 0; j < objSize; j += 2) {

            //判断类型
            typeIndex = ((BaseDataType)(typeAL.get(j))).getDataType();
            switch (typeIndex) {
              case BaseDataType.INVALID:

                //忽略无效数据
                break;
              case BaseDataType.INTEGER:

                if (j > 0) {
                  sqlString.append(" or ");
                }

                sqlString.append((String)(nameAL.get(j)));
                sqlString.append(" ");
                sqlString.append((String)(valueAL.get(j + 1)));
                sqlString.append(" ");
                sqlString.append(((Integer)(valueAL.get(j))).intValue());
                break;
              case BaseDataType.NUMBER:

                if (j > 0) {
                  sqlString.append(" or ");
                }

                sqlString.append((String)(nameAL.get(j)));
                sqlString.append(" ");
                sqlString.append((String)(valueAL.get(j + 1)));
                sqlString.append(" ");
                sqlString.append(((BigDecimal)(valueAL.get(j))).toString());
                break;
              case BaseDataType.STRING:

                if (j > 0) {
                  sqlString.append(" or ");
                }

                sqlString.append((String)(nameAL.get(j)));
                sqlString.append(" ");
                sqlString.append((String)(valueAL.get(j + 1)));
                sqlString.append(" '");
                sqlString.append((String)(valueAL.get(j)));
                sqlString.append("' ");
                break;
              case BaseDataType.DATE:

                if (j > 0) {
                  sqlString.append(" and ");
                }

                //格式是TO_DATE('2003-04-22','yyyy-MM-DD')
                sqlString.append((String)(nameAL.get(j)));
                sqlString.append(" ");
                sqlString.append((String)(valueAL.get(j + 1)));
                Date tempDate = (Date)(valueAL.get(j));
                sqlString.append(" TO_DATE('");
                sqlString.append(PromosDate.toShortFormatString(tempDate));
                sqlString.append("','");
                sqlString.append(PromosDate.SHORTFORMAT);
                sqlString.append("')");

                break;

            }

          }

          sqlString.append(" ) ");
        }

      }
    }

    //查找首项索引
    int searchFirstIndex = selectIndex * selectNumber;

    //查找末项索引
    int searchLastIndex = searchFirstIndex + selectNumber;

    //查询分页指定记录
    if (status != 1 && selectNumber > -1) {

      //检测是否存在限定条件
      if (addSize > 0) {
        sqlString.append(" and ROWNUM <= ");
      }
      else {
        sqlString.append(" ROWNUM <= ");
      }

      sqlString.append(searchLastIndex);

      //最后加上额外限定条件
      if (strStatus.compareTo("") != 0) {

        //如果有查询条件
        sqlString.append(" and ( ");
        sqlString.append(strStatus);
        sqlString.append(" ) ");

      }

      //最后加上排序条件
      if (strSort.compareTo("") != 0) {
        sqlString.append(" order by ");
        sqlString.append(strSort);
      }

      sqlString.append(" ) where tempRowNumIndex > ");
      sqlString.append(searchFirstIndex);

    }
    else {

      //最后加上额外限定条件
      if (strStatus.compareTo("") != 0) {

        //如果没有查询条件
        if (addSize > 0) {
          sqlString.append(" and ( ");
          sqlString.append(strStatus);
          sqlString.append(" ) ");
        }
        else {
          sqlString.append(strStatus);
        }

      }

      //最后加上排序条件
      if (strSort.compareTo("") != 0) {
        sqlString.append(" order by ");
        sqlString.append(strSort);
      }

    }

    //显示查询语句
    String sql = sqlString.toString();
    BasePrint.print(" selectSQLEX : ", sql, true);

    return this.selectSQL(tableName, sqlString.toString());

  }

  /**
   * 查询记录,检测所有的数据
   *
   * <p>本方法使用限制如下：</p>
   * <p>1：仅限于单表或单视图查询。</p>
   * <p>2：仅限于简单条件判断查询。</p>
   * <p>3：列表中的每项条件之间是 AND 关系。</p>
   * <p>5：额外判定限定条件与列表中的每项条件之间是 AND 关系。</p>
   * <p>6：如果返回记录的项数为-1，将返回查询到的所有数据。</p>
   * <p>7：如果返回记录的项数大于0，将返回查询到的分页数据。
   * 起止索引是：( selectIndex+1 , selectIndex+selectNumber )</p>
   * <p>8：如果查询标记为0，查询记录，如果为1，查询记录的数量。
   * 数量名称是SELECT_ALL_NUMBER。</p>
   *
   * @param dataSourceName DataSource的名称
   * @param tableName 表名或视图的名称
   * @param al 查询SQL条件
   * @param strStatus 查询SQL额外的限定条件
   * @param strSort 查询SQL的排序条件
   * @param selectIndex 返回记录的首项
   * @param selectNumber 返回记录的项数
   * @param status 查询记录还是查询数量标志
   * @return 查询记录列表
   * @throws RemoteException
   */
  public ArrayList selectSQLEXSortAll(String tableName,
                                      ArrayList al, String strStatus,
                                      String strSort, int selectIndex,
                                      int selectNumber, int status) throws
      RemoteException {

    return this.selectSQLEXSortAll(tableName, " * ", al,
                                   strStatus, strSort, selectIndex,
                                   selectNumber, status);

  }

  /**
   * 查询记录，检测所有的数据。
   *
   * <p>本方法使用限制如下：</p>
   * <p>1：仅限于单表或单视图查询。</p>
   * <p>2：仅限于简单条件判断查询。</p>
   * <p>3：列表中的每项条件之间是 AND 关系。</p>
   * <p>5：额外判定限定条件与列表中的每项条件之间是 AND 关系。</p>
   * <p>6：如果返回记录的项数为-1，将返回查询到的所有数据。</p>
   * <p>7：如果返回记录的项数大于0，将返回查询到的分页数据。
   * 起止索引是：( selectIndex+1 , selectIndex+selectNumber )</p>
   * <p>8：如果查询标记为0，查询记录，如果为1，查询记录的数量。
   * 数量名称是SELECT_ALL_NUMBER。</p>
   *
   * @param dataSourceName DataSource的名称
   * @param tableName 表名或视图的名称
   * @param strFields 指定查询的字段
   * @param al 查询SQL条件
   * @param strStatus 查询SQL额外的限定条件
   * @param strSort 查询SQL的排序条件
   * @param selectIndex 返回记录的首项
   * @param selectNumber 返回记录的项数
   * @param status 查询记录还是查询数量标志，1查询数量，-1查询所有，0分页查询
   * @return 查询记录列表
   * @throws RemoteException 异常
   */
  public ArrayList selectSQLEXSortAll(String tableName,
                                      String strFields, ArrayList al,
                                      String strStatus, String strSort,
                                      int selectIndex, int selectNumber,
                                      int status) throws RemoteException {

    return this.selectSQLEXSortAll(tableName, strFields, al,
                                   strStatus, strSort, null, selectIndex,
                                   selectNumber, status);

  }

  /**
   * 查询记录，检测所有的数据。
   *
   * <p>本方法使用限制如下：</p>
   * <p>1：仅限于单表或单视图查询。</p>
   * <p>2：仅限于简单条件判断查询。</p>
   * <p>3：列表中的每项条件之间是 AND 关系。</p>
   * <p>5：额外判定限定条件与列表中的每项条件之间是 AND 关系。</p>
   * <p>6：如果返回记录的项数为-1，将返回查询到的所有数据。</p>
   * <p>7：如果返回记录的项数大于0，将返回查询到的分页数据。
   * 起止索引是：( selectIndex+1 , selectIndex+selectNumber )</p>
   * <p>8：如果查询标记为0，查询记录，如果为1，查询记录的数量。
   * 数量名称是SELECT_ALL_NUMBER。</p>
   *
   * @param dataSourceName DataSource的名称
   * @param tableName 表名或视图的名称
   * @param strFields 指定查询的字段
   * @param al 查询SQL条件
   * @param strStatus 查询SQL额外的限定条件
   * @param strSort 查询SQL的排序条件
   * @param strGroupBy 查询SQL的分组条件
   * @param selectIndex 返回记录的首项
   * @param selectNumber 返回记录的项数
   * @param status 查询记录还是查询数量标志，1查询数量，-1查询所有，0分页查询
   * @return 查询记录列表
   * @throws RemoteException 异常
   */
  public ArrayList selectSQLEXSortAll(String tableName,
                                      String strFields, ArrayList al,
                                      String strStatus, String strSort, String strGroupBy,
                                      int selectIndex, int selectNumber,
                                      int status) throws RemoteException {

    StringBuffer sqlString = new StringBuffer("");

    if (status == 1) {
      //查询记录数量
      sqlString.append(" select count(1) as SELECT_ALL_NUMBER from ");
      sqlString.append(tableName);
    }
    else if (selectNumber == -1) {
      //查询全部记录
      sqlString.append(" select ");
      sqlString.append(strFields);
      sqlString.append(" from ");
      sqlString.append(tableName);
    }
    else {
      //查询分页指定记录
      sqlString.append(" select ");
      sqlString.append(strFields);
      sqlString.append(" from ");
      sqlString.append(tableName);
    }

    //判断是否存在限定条件
    boolean isHaveAl = true;
    boolean isHaveStatus = true;
    if (al == null || al.size() == 0) {
      isHaveAl = false;
    }
    if (strStatus == null || strStatus.trim().equals("")) {
      isHaveStatus = false;
    }

    //如果存在条件，将添加where
    if (isHaveAl || isHaveStatus) {
      sqlString.append(" where ");
    }

    //如果存在条件
    if (isHaveStatus) {
      //sqlString.append(" ( ");
      sqlString.append(strStatus);
      //sqlString.append(" ) ");
    }

    //如果还存在列表条件，添加and
    if (isHaveStatus && isHaveAl) {
      sqlString.append(" and ");
    }

    //添加列表条件
    if (isHaveAl) {
      //sqlString.append(" ( ");

      //循环判断是否存在限定条件
      //在限定条件列表中，规则：列表值BaseObject取 BaseObject＋1的值 ，列表值之间取 AND
      //即采用( ... (=/>/</>=/<=/<>/like) (or/and)  ... ) and ( ... )

      int checkAllSize = al.size();
      for (int i = 0; i < checkAllSize; i++) {

        //取出列表值BaseObject
        BaseObject obj = (BaseObject)(al.get(i));
        int objSize = obj.getFieldCount();

        //如果设定条件
        if (objSize > 0) {

          //限定列表项之间采用 AND 模式
          if (i > 0) {
            sqlString.append(" and ");
          }

          if (checkAllSize > 1) {
            sqlString.append(" ( ");
          }

          //循环设定列表值，取 OR
          ArrayList nameAL = obj.getFieldName();
          ArrayList typeAL = obj.getFieldType();
          ArrayList valueAL = obj.getFieldValue();
          int typeIndex = 0;

          //查找值和处理模式
          for (int j = 0; j < objSize; j += 2) {

            //判断类型
            typeIndex = ((BaseDataType)(typeAL.get(j))).getDataType();
            switch (typeIndex) {
              case BaseDataType.INVALID:

                //忽略无效数据
                break;
              case BaseDataType.INTEGER:

                if (j > 0) {
                  sqlString.append(" or ");
                }

                sqlString.append((String)(nameAL.get(j)));
                sqlString.append(" ");
                sqlString.append((String)(valueAL.get(j + 1)));
                sqlString.append(" ");
                sqlString.append(((Integer)(valueAL.get(j))).intValue());
                break;
              case BaseDataType.NUMBER:

                if (j > 0) {
                  sqlString.append(" or ");
                }

                sqlString.append((String)(nameAL.get(j)));
                sqlString.append(" ");
                sqlString.append((String)(valueAL.get(j + 1)));
                sqlString.append(" ");
                sqlString.append(((BigDecimal)(valueAL.get(j))).toString());
                break;
              case BaseDataType.STRING:

                if (j > 0) {
                  sqlString.append(" or ");
                }

                sqlString.append((String)(nameAL.get(j)));
                sqlString.append(" ");
                sqlString.append((String)(valueAL.get(j + 1)));
                sqlString.append(" '");
                sqlString.append((String)(valueAL.get(j)));
                sqlString.append("' ");
                break;
              case BaseDataType.DATE:

                if (j > 0) {
                  sqlString.append(" and ");
                }

                //格式是TO_DATE('2003-04-22','yyyy-MM-DD')
                sqlString.append((String)(nameAL.get(j)));
                sqlString.append(" ");
                sqlString.append((String)(valueAL.get(j + 1)));
                Date tempDate = (Date)(valueAL.get(j));
                sqlString.append(" TO_DATE('");
                sqlString.append(PromosDate.toShortFormatString(tempDate));
                sqlString.append("','");
                sqlString.append(PromosDate.SHORTFORMAT);
                sqlString.append("')");

                break;

            }

          }

          if (checkAllSize > 1) {
            sqlString.append(" ) ");
          }
        }

      }

      //sqlString.append(" ) ");
    }

    //如果排序
    if (strGroupBy != null && !strGroupBy.equals("")) {
      sqlString.append(" group by ");
      sqlString.append(strGroupBy);
      sqlString.append(" ");
    }

    //如果排序
    if (strSort != null && !strSort.equals("")) {
      sqlString.append(" order by ");
      sqlString.append(strSort);
      sqlString.append(" ");
    }

    //显示查询语句
    String sql = sqlString.toString();
    //如果需要分页
    if (status == 0) {
      sqlString = new StringBuffer("");
      sqlString.append(
          " select * from ( select ROWNUM as TEMP_ROWNUM, TEMP_TABLE.* from (  ");
      sqlString.append(sql);
      sqlString.append(") TEMP_TABLE  where rownum < ");
      sqlString.append((selectIndex + 1) * selectNumber + 1);
      sqlString.append(") where TEMP_ROWNUM >= ");
      sqlString.append(selectIndex * selectNumber + 1);
      sql = sqlString.toString();
    }

    BasePrint.print(" selectSQLEXSortAll : ", sql, true);

    return this.selectSQL(tableName, sqlString.toString());

  }

  /**
   * 查询记录
   *
   * @param dataSourceName DataSource的名称
   * @param tableName 表名或视图的名称
   * @param sqlString 查询SQL语句
   * @return 查询记录列表
   * @throws RemoteException
   */
  public ArrayList selectSQL(String tableName,
                             String sqlString) throws RemoteException {

    //查询不到数据，返回默认
    ArrayList rAL = new ArrayList();

    //检测
    if (dataSourceName == null) {
      return rAL;
    }
    if (tableName == null) {
      return rAL;
    }

    if ((tableName.indexOf(".")<0)&&(tableName.equals("")==false)&&(tableName!=null)&&(tableName.indexOf("select")<0)&&(tableName.indexOf("SELECT")<0))
	{
    	tableName=DB_USER+"."+tableName;
	}
    
    if (sqlString == null) {
      return rAL;
    }

    //得到指定表类
    BaseObject obj = null;
    Connection con = null;
    Statement stmt = null;
    ResultSet sqlrs = null;

    //表的字段数量
    int fieldCount = 0;

    int i = 0;
    int fieldType = 0;
    String fieldAlias = "";

    //显示SQL语句
    BasePrint.println(" selectSQL : "+sqlString);

    try {

      //得到连接
      con = this.getConnection();
      stmt = con.createStatement();

      //查询指定的数据
      sqlrs = stmt.executeQuery(sqlString);

      //得到表或视图的结构
      obj = this.getTableByResultSet(sqlrs);

      //添加表或视图名称
      obj.setTableName(tableName);

      //得到表的字段数量
      fieldCount = obj.getFieldCount();
      while (sqlrs.next()) {

        //循环得到字段值
        ArrayList valueAL = new ArrayList();

       for (i = 0; i < fieldCount; i++) {

          //得到字段别名
          fieldAlias = (String)(obj.getFieldAlias().get(i));
          
 
          //得到字段类型
          fieldType = ((BaseDataType)(obj.getFieldType().get(i))).getDataType();

          //根据字段类型得到字段值
          switch (fieldType) {

            //如果是：INTEGER
            case BaseDataType.INTEGER:
              valueAL.add(new Integer(sqlrs.getInt(fieldAlias)));
              break;

              //如果是：NUMBER
            case BaseDataType.NUMBER:
              valueAL.add(sqlrs.getBigDecimal(fieldAlias));
              break;

              //如果是：STRING
            case BaseDataType.STRING:

              //如果是NULL,返回""
              String tempFRS = sqlrs.getString(fieldAlias);
             if (tempFRS == null) {
                valueAL.add(new String(""));
              }
              else {
                valueAL.add(new String(tempFRS.trim()));
              }

              break;
              //如果是：BLOB
            case BaseDataType.BLOB:
              Blob blob = sqlrs.getBlob(fieldAlias);
              java.io.InputStream tempobj1 = blob.getBinaryStream();
              java.io.BufferedInputStream is = new java.io.BufferedInputStream(tempobj1);
              try {
                byte[] byte1 = new byte[10];
                int nodata = is.read(byte1, 0, 10);
                if (nodata == -1) {
                  valueAL.add(null);
                }
                else {
                  valueAL.add("not null");
                }
              }
              catch (Exception e) {}

              break;

            case BaseDataType.LONG:

              //如果是NULL,返回""
              tempFRS = sqlrs.getString(fieldAlias);
              //BasePrint.println(tempFRS);
              /*
                    InputStream in = sqlrs.getBinaryStream(fieldAlias);
                    BufferedReader buf = new BufferedReader(new InputStreamReader(in));
                    BasePrint.println("****"+buf.readLine());
                   in.close();
               */
              if (tempFRS == null) {
                valueAL.add(new String(""));
              }
              else {
                //BasePrint.println(fieldAlias);
                valueAL.add(new String(tempFRS.trim()));
              }

              break;

              //如果是：STREAM
            case BaseDataType.STREAM:
              java.io.InputStream tempobj = sqlrs.getBinaryStream(fieldAlias);
              java.io.BufferedInputStream io = new java.io.BufferedInputStream(tempobj);
              try {
                byte[] byte1 = new byte[10];
                int nodata = io.read(byte1, 0, 10);
                if (nodata == -1) {
                  valueAL.add(null);
                }
                else {
                  valueAL.add("not null");
                }
              }
              catch (Exception e) {}

              break;
              //如果是：CLOB
            case BaseDataType.CHAR_STREAM:
            	Clob clob = sqlrs.getClob(fieldAlias);
            	if (clob!=null){
            		BufferedReader reader = null;
            		StringBuffer buffer = new StringBuffer();
            		try {
            			reader = new BufferedReader(clob.getCharacterStream());
            			String line;
            			while ((line = reader.readLine()) != null) {
            				buffer.append(line);
            			}
            		}
            		catch (Exception e) {
            			e.printStackTrace();
            		}
            		finally {
            			try {
            				if (reader != null) {
            					reader.close();
            				}
            			}
            			catch (Exception e) {
            				e.printStackTrace();
            			}
            		}
            		valueAL.add(buffer.toString());
            	}
            	else
            	{
                    valueAL.add(null);
            	}
              break;

              //如果是：DATE
            case BaseDataType.DATE:

              //读取时间
              //如果是NULL，返回默认值
              Timestamp tempTimestamp = sqlrs.getTimestamp(fieldAlias);
              Date tempDate = null;
              if (tempTimestamp != null) {
                tempDate = new Date(tempTimestamp.getTime());
              }
              valueAL.add(tempDate);
              //BasePrint.println(valueAL.size()-1);
              //valueAL.add(sqlrs.getDate(fieldAlias));
              break;

              //如果是：DATETIME
            case BaseDataType.DATETIME:

              //读取时间
              //如果是NULL，返回默认值
              Timestamp tempTime = sqlrs.getTimestamp(fieldAlias);
              Date tempDateTime = null;
              if (tempTime != null) {
            	  tempDateTime = new Date(tempTime.getTime());
              }
              valueAL.add(tempDateTime);
              //BasePrint.println(valueAL.size()-1);
              //valueAL.add(sqlrs.getDate(fieldAlias));
              break;
              
            default:
              valueAL.add(new String("")); ;

          }

          //更新
          fieldAlias = new String("");

        }

        //设定字段值
        obj.setFieldValue(valueAL);

        //清空字段值列表
        valueAL = new ArrayList();

        //更新
        fieldAlias = new String("");

        //未采用索引，直接添加字段值
        rAL.add(obj);

        //复制新的数据类
        obj = BaseObject.clone(obj);

      }

    }
    catch (Exception e) {
      if (e.getClass().isInstance(new SQLException())) {

        //BasePrint.println("数据库错误--代码："+( (SQLException) e).getErrorCode());

        e.printStackTrace();
        throw new BaseException("", ((SQLException)e).getErrorCode());
      }
      else {
        e.printStackTrace();
      }

    }
    finally {

      if (sqlrs != null) {
        try {
          sqlrs.close();
        }
        catch (SQLException ignore) {
        }
      }

      if (stmt != null) {
        try {
          stmt.close();
        }
        catch (SQLException ignore) {
        }
      }

      if (con != null) {
        try {
          con.close();
        }
        catch (SQLException ignore) {
        }
      }

    }

    return rAL;

  }

  /**
   * 添加指定序列的记录
   *
   * @param dataSourceName 数据源的名称
   * @param updateTableName 被添加记录的表的名称
   * @param obj 添加的记录
   * @param sequenceFieldName 添加指定序列值的字段名称
   * @param sequenceName 指定的序列名称
   * @return 如果添加成功，返回True，否则返回False
   * @throws RemoteException
   */
  public boolean insertSQLBySequence(
                                     String updateTableName, BaseObject obj,
                                     String sequenceFieldName,
                                     String sequenceName) throws
      RemoteException {

    //检测
    if (dataSourceName == null) {
      return false;
    }
    if (updateTableName == null) {
      return false;
    }
    if (obj == null) {
      return false;
    }
    if (sequenceFieldName == null) {
      return false;
    }
    if (sequenceName == null) {
      return false;
    }

    //添加记录
    //添加格式：insert into test (ID,NAME,PID) values (SEQ.nextval,'test',0)
    StringBuffer strSQL = new StringBuffer();
    strSQL.append(" insert into ");
    strSQL.append(updateTableName);

    int fieldCount = obj.getFieldCount();
    int lastIndex = fieldCount - 1;
    ArrayList fieldNameAL = obj.getFieldName();
    ArrayList fieldTypeAL = obj.getFieldType();
    int fieldType = 0;
    ArrayList fieldValueAL = obj.getFieldValue();

    int i = 0;

    //检查是否指定了序列
    boolean isSequence = false;
    if (sequenceFieldName.compareTo("") == 0) {
      isSequence = false;
    }
    else {
      isSequence = true;
    }
    int sequenceField = -1;
    String fieldName = "";

    //添加字段名称
    strSQL.append(" ( ");
    for (i = 0; i < fieldCount; i++) {
      fieldType = ((BaseDataType)(fieldTypeAL.get(i))).getDataType();
      //添加字段名
      fieldName = (String)(fieldNameAL.get(i));
      strSQL.append(fieldName);

      //如果指定了序列
      if (isSequence) {

        //检查当前字段是否是指定的序列字段
        if (fieldName.compareTo(sequenceFieldName) == 0) {
          sequenceField = i;
          isSequence = false;
        }
      }

      //添加分隔号
      if (i >= 0 && i < lastIndex) {
        strSQL.append(" , ");
      }
    }
    strSQL.append(" ) ");

    //添加字段值
    strSQL.append(" values ( ");
    for (i = 0; i < fieldCount; i++) {

      //如果是指定的序列字段
      if (sequenceField == i) {
        //添加序列号值
        strSQL.append(sequenceName);
        strSQL.append(".nextval");
      }
      else {

        //检查字段类型，添加字段值
        //根据字段类型得到字段值
        fieldType = ((BaseDataType)(fieldTypeAL.get(i))).getDataType();
        switch (fieldType) {

          //如果是：INVALID
          case BaseDataType.INVALID:
            break;

            //如果是：INTEGER
          case BaseDataType.INTEGER:
            if (fieldValueAL.get(i) != null) {
              strSQL.append(((Integer)(fieldValueAL.get(i))).intValue());
            }
            else {
              strSQL.append("null");
            }
            break;

            //如果是：NUMBER
          case BaseDataType.NUMBER:
            if (fieldValueAL.get(i) != null) {
              strSQL.append(((BigDecimal)(fieldValueAL.get(i))).toString());
            }
            else {
              strSQL.append("null");
            }
            break;

            //如果是：STRING
          case BaseDataType.STRING:
            strSQL.append("'");
            String strString = (String)(fieldValueAL.get(i));
            strSQL.append(FormatTools.getOracleString(strString));
            strSQL.append("'");
            break;

            //如果是：DATE
          case BaseDataType.DATE:

            //格式是TO_DATE('2003-04-22 21:30:25','yyyy-MM-DD hh24:mi:ss')
            Object object2 = fieldValueAL.get(i);
            if (object2 != null) {
              Date tempDate = (Date)(fieldValueAL.get(i));
              strSQL.append("TO_DATE('");
              strSQL.append(PromosDate.toDefaultString(tempDate));
              strSQL.append("','");
              strSQL.append(PromosDate.toDefaultFormat(tempDate));
              strSQL.append("')");
            }
            else {
              strSQL.append(" null ");
            }

            //BasePrint.println("暂时组成的sql语句是:"+strSQL);
            break;
          case BaseDataType.CHAR_STREAM:
            strSQL.append("empty_clob()");
            break;

        } //END SWITCH

      }

      //添加分隔号
      if (i >= 0 && i < lastIndex) {
        strSQL.append(" , ");
      }

    }

    strSQL.append(" ) ");

    return executeSQL(strSQL.toString());

  }

  /**
   * 添加记录
   *
   * @param dataSourceName 数据源的名称
   * @param updateTableName 被添加记录的表的名称
   * @param obj 添加的记录
   * @return 如果成功添加记录，返回True，否则返回False
   * @throws RemoteException
   */
  public boolean insertSQL(String updateTableName,
                           BaseObject obj) throws RemoteException {

    return insertSQLBySequence(updateTableName, obj, "", "");

  }

  /**
   * 添加记录
   *
   * @param dataSourceName 数据源的名称
   * @param updateTableName 被添加记录的表的名称
   * @param obj 添加的记录
   * @param str 将被添加的记录被单独获得的条件
   * @return 如果成功添加记录，返回True，否则返回False
   * @throws RemoteException
   */
  public boolean insertSQLWithClob(
                                   String updateTableName, BaseObject obj,
                                   String strWhere) throws RemoteException {

    insertSQLBySequence(updateTableName, obj, "", "");

    int fieldCount = obj.getFieldCount();
    ArrayList fieldNameAL = obj.getFieldName();
    ArrayList fieldTypeAL = obj.getFieldType();
    int fieldType = 0;
    ArrayList fieldValueAL = obj.getFieldValue();

    for (int i = 0; i < fieldCount; i++) {
      fieldType = ((BaseDataType)(fieldTypeAL.get(i))).getDataType();
      if (fieldType == BaseDataType.CHAR_STREAM) {
        String fieldName = (String)(fieldNameAL.get(i));
        String sql = "select " + fieldName + " from " + updateTableName +
            " where 1=1";
        if (strWhere != null && strWhere.trim().length() > 0) {
          sql = sql + " and " + strWhere + " for update";
        }
        try {
          updateClob(sql, (String)fieldValueAL.get(i));
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }

    }

    return true;

  }

  /**
   * 删除记录
   *
   * @param dataSourceName 数据源的名称
   * @param updateTableName 被删除记录的表的名称
   * @param sqlString 删除记录的限定条件
   * @return 如果成功删除记录，返回True，否则返回False
   * @throws RemoteException
   */
  public boolean deleteSQL( String updateTableName,
                           String sqlString) throws RemoteException {

    //检测
    if (dataSourceName == null) {
      return false;
    }
    if (updateTableName == null) {
      return false;
    }
    if (sqlString == null) {
      return false;
    }

    //删除指定的记录
    //删除格式：delete from test where ID=100
    StringBuffer strSQL = new StringBuffer();
    strSQL.append(" delete from ");
    strSQL.append(updateTableName);
    strSQL.append(" where ");
    strSQL.append(sqlString);

    return executeSQL(strSQL.toString());
  }

  /**
   * 更新记录
   *
   * @param dataSourceName 数据源的名称
   * @param updateTableName 被更新记录的表的名称
   * @param obj 需要更新的记录
   * @param sqlString 更新记录的限定条件
   * @return 如果成功更新记录，返回True，否则返回False
   * @throws RemoteException 异常
   */
  public boolean updateSQL(String updateTableName,
                           BaseObject obj, String sqlString) throws
      RemoteException {
    //检测
    if (dataSourceName == null) {
      return false;
    }
    if (updateTableName == null) {
      return false;
    }
    if (obj == null) {
      return false;
    }
    if (sqlString == null) {
      return false;
    }

    //更改指定的记录
    //更新格式：update test set ID=200,Name='mofidy' where ID=100 and PID=0
    StringBuffer strSQL = new StringBuffer();
    strSQL.append(" update ");
    strSQL.append(updateTableName);
    strSQL.append(" set ");

    int fieldCount = obj.getFieldCount();
    ArrayList fieldNameAL = obj.getFieldName();
    ArrayList fieldTypeAL = obj.getFieldType();
    int fieldType = 0;
    ArrayList fieldValueAL = obj.getFieldValue();

    int i = 0;
    //添加更改记录
    for (i = 0; i < fieldCount; i++) {

      //添加字段名
      fieldType = ((BaseDataType)(fieldTypeAL.get(i))).getDataType();

      if (fieldType != BaseDataType.CHAR_STREAM &&
          fieldType != BaseDataType.STREAM) {
        strSQL.append((String)(fieldNameAL.get(i)));
        strSQL.append(" = ");
        //检查字段类型，添加字段值
        //根据字段类型得到字段值
        switch (fieldType) {
          //如果是：INVALID
          case BaseDataType.INVALID:
            break;
            //如果是：INTEGER
          case BaseDataType.INTEGER:
            if (fieldValueAL.get(i) != null) {
              strSQL.append(((Integer)(fieldValueAL.get(i))).intValue());
            }
            else {
              strSQL.append("null");
            }
            break;
            //如果是：NUMBER
          case BaseDataType.NUMBER:
            if (fieldValueAL.get(i) != null) {
              strSQL.append(((BigDecimal)(fieldValueAL.get(i))).toString());
            }
            else {
              strSQL.append("null");
            }
            break;
            //如果是：STRING
          case BaseDataType.STRING:
            strSQL.append("'");
            String strString = (String)(fieldValueAL.get(i));
            strSQL.append(FormatTools.getOracleString(strString));
            strSQL.append("'");
            break;
            //如果是：DATE
          case BaseDataType.DATE:

            //格式是TO_DATE('2003-04-22 21:30:25','yyyy-MM-DD hh24:mi:ss')
            Date tempDate = (Date)(fieldValueAL.get(i));
            strSQL.append("TO_DATE('");
            strSQL.append(PromosDate.toDefaultString(tempDate));
            strSQL.append("','");
            strSQL.append(PromosDate.toDefaultFormat(tempDate));
            strSQL.append("')");

            break;
        }
        //添加分隔号

        boolean hasNextField = false;
        for (int j = i + 1; j < fieldCount; j++) {
          int nextFieldType = ((BaseDataType)(fieldTypeAL.get(j))).
              getDataType();
          if (nextFieldType != BaseDataType.CHAR_STREAM &&
              nextFieldType != BaseDataType.STREAM) {
            hasNextField = true;
            break;
          }
        }
        if (hasNextField) {
          //BasePrint.println(fieldType + "   " + (String) (fieldNameAL.get(i)));
          strSQL.append(" , ");
        }
      }

    }

    strSQL.append(" where ");
    strSQL.append(sqlString);

    executeSQL(strSQL.toString());

    for (i = 0; i < fieldCount; i++) {
      fieldType = ((BaseDataType)(fieldTypeAL.get(i))).getDataType();
      if (fieldType == BaseDataType.CHAR_STREAM) {
        String fieldName = (String)(fieldNameAL.get(i));
        String sql = "select " + fieldName + " from " + updateTableName +
            " where " + sqlString + " for update";
        try {
          updateClob(sql, (String)fieldValueAL.get(i));
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }

    }

    return true;
  }

  /**
   * 创建表
   *
   * @param dataSourceName 数据源的名称
   * @param updateTableName 被创建的表的名称
   * @param sqlString 被创建的表的结构
   * @return 如果成功创建表，返回True，否则返回False
   * @throws RemoteException
   */
  public boolean createSQL(String updateTableName,
                           String sqlString) throws RemoteException {

    //检测
    if (dataSourceName == null) {
      return false;
    }
    if (updateTableName == null) {
      return false;
    }
    if (sqlString == null) {
      return false;
    }

    //新建表
    //新建格式：create table test ( ID number(20), NAME varchar(50), PID number(20))
    StringBuffer strSQL = new StringBuffer();
    strSQL.append(" create table ");
    strSQL.append(updateTableName);
    strSQL.append(" (");
    strSQL.append(sqlString);
    strSQL.append(") ");

    return executeSQL(strSQL.toString());

  }

  /**
   * 删除表
   *
   * @param dataSourceName 数据源的名称
   * @param updateTableName 被删除的表的名称
   * @return 如果成功删除表，返回True，否则返回False
   * @throws RemoteException
   */
  public boolean dropSQL(String updateTableName) throws
      RemoteException {

    //检测
    if (dataSourceName == null) {
      return false;
    }
    if (updateTableName == null) {
      return false;
    }

    //删除表
    //删除格式：drop table test
    StringBuffer strSQL = new StringBuffer();
    strSQL.append(" drop table ");
    strSQL.append(updateTableName);

    return executeSQL(strSQL.toString());

  }

  /**
   * 添加、删除、更新记录
   *
   * @param dataSourceName DataSource的名称
   * @param sqlString 添加、删除、更新的SQL语句
   * @return 如果操作成功，返回True,否则返回False
   * @throws RemoteException
   */
  public boolean executeSQL(String sqlString) throws
      RemoteException {

    boolean rs = false;
    Connection con = null;
    Statement stmt = null;
    //显示SQL语句
    BasePrint.print(" executeSQL : ", sqlString, true);

    try {

      //得到连接
      con = this.getConnection();
      stmt = con.createStatement();

      //如果SQL语句执行成功，返回TRUE
      int resule=stmt.executeUpdate(sqlString);

      //System.out.println(" resule2: "+resule);
      
      if (resule>=0) {
        rs = true;
      }
      else {
        rs = false;
      }

    }
    catch (Exception e) {
      if (e.getClass().isInstance(new SQLException())) {

        //BasePrint.println("数据库错误--代码："+( (SQLException) e).getErrorCode());

        e.printStackTrace();
        throw new BaseException("", ((SQLException)e).getErrorCode());
      }
      else {
        e.printStackTrace();
      }
    }
    finally {

      if (stmt != null) {
        try {
          stmt.close();
        }
        catch (SQLException ignore) {
        }
      }

      if (con != null) {
        try {
          con.close();
        }
        catch (SQLException ignore) {
        }
      }

    }

    return rs;
  }

  /**
   * 执行Begin-end语句
   *
   * @param dataSourceName DataSource的名称
   * @param sqlString Begin-end语句
   * @return 如果操作成功，返回True,否则返回False
   * @throws RemoteException
   */
  public boolean executeBeginEnd(String sqlString) throws
      RemoteException {

    boolean rs = false;
    Connection con = null;
    Statement stmt = null;
    //显示SQL语句
    BasePrint.print(" executeBeginEnd : ", sqlString, true);

    try {

      //得到连接
      con = this.getConnection();
      stmt = con.createStatement();

      //如果SQL语句执行成功，返回TRUE
      stmt.execute(sqlString);

      return true;

    }
    catch (Exception e) {
      if (e.getClass().isInstance(new SQLException())) {

        //BasePrint.println("数据库错误--代码："+( (SQLException) e).getErrorCode());

        //e.printStackTrace();
        throw new BaseException("", ((SQLException)e).getErrorCode());
      }
      else {
        e.printStackTrace();
      }
    }
    finally {

      if (stmt != null) {
        try {
          stmt.close();
        }
        catch (SQLException ignore) {
        }
      }

      if (con != null) {
        try {
          con.close();
        }
        catch (SQLException ignore) {
        }
      }

    }

    return rs;
  }

  /**
   * 查询记录条数
   *
   * @param dataSourceName DataSource的名称
   * @param tableName 表名或视图的名称
   * @param sqlString 查询SQL语句
   * @return 记录条数
   * @throws RemoteException
   */
  public int getCount(String tableName, String sqlString) throws
      RemoteException {

    BasePrint.print(" getCount : ", sqlString, true);
    Connection con = null;
    Statement stmt = null;
    ResultSet sqlrs = null;

    try {

      //得到连接
      con = this.getConnection();
      stmt = con.createStatement();

      //查询指定的数据
      sqlrs = stmt.executeQuery(sqlString);

      if (sqlrs.next()) {
        return sqlrs.getInt(1);
      }

    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {

      if (sqlrs != null) {
        try {
          sqlrs.close();
        }
        catch (Exception ignore) {
        }
      }

      if (stmt != null) {
        try {
          stmt.close();
        }
        catch (Exception ignore) {
        }
      }

      if (con != null) {
        try {
          con.close();
        }
        catch (Exception ignore) {
        }
      }

    }

    return 0;

  }

  /**
   * 自动获取下一个序列号
   *
   * @param dataSourceName DataSource的名称
   * @param seqName 序列号名称
   * @return 下一个序列号
   * @throws RemoteException
   */
  public long getSeqNextValue(String seqName) throws
      RemoteException {

    Connection con = null;
    Statement stmt = null;
    ResultSet sqlrs = null;

    try {

      //得到连接
      con = this.getConnection();
      stmt = con.createStatement();

      //查询指定的数据
      sqlrs = stmt.executeQuery("select " + seqName + ".nextVal from dual");

      if (sqlrs.next()) {
        return sqlrs.getLong(1);
      }

    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {

      if (sqlrs != null) {
        try {
          sqlrs.close();
        }
        catch (Exception ignore) {
        }
      }

      if (stmt != null) {
        try {
          stmt.close();
        }
        catch (Exception ignore) {
        }
      }

      if (con != null) {
        try {
          con.close();
        }
        catch (Exception ignore) {
        }
      }

    }

    return -1;

  }

  /**
   * 执行一批SQL语句
   *
   * @param dataSourceName DataSource的名称
   * @param arrayList SQL语句列表
   * @return 最终执行结果
   * @throws RemoteException
   */
  public boolean executeBatch(ArrayList arrayList) throws
      RemoteException {

    boolean bol=false;

    Connection con = null;
    Statement st = null;

    try {
      //得到连接
      con = this.getConnection();
      st = con.createStatement();

      st.addBatch((String)arrayList.get(0));
      for (int i = 1; i < arrayList.size(); i++) {
        st.addBatch((String)arrayList.get(i));
      }

      st.executeBatch();
      bol=true;
    }
    catch (Exception e) {
      if (e.getClass().isInstance(new SQLException())) {

        //BasePrint.println("数据库错误--代码："+( (SQLException) e).getErrorCode());

        e.printStackTrace();
        throw new BaseException("", ((SQLException)e).getErrorCode());
      }
      else {
        e.printStackTrace();
      }

    }
    finally {
      if (st != null) {
        try {
          st.close();
        }
        catch (SQLException e) {
        }
      }
      if (con != null) {
        try {
          con.close();
        }
        catch (SQLException e) {
        }
      }

    }

    return bol;
  }

  /**
   * 更新大字符串字段
   *
   * @param dataSourceName DataSource的名称
   * @param sql
   * @param text
   * @return 最终执行结果
   * @throws RemoteException
   */
  public boolean updateClob(String sql, String text) throws
      RemoteException {
    Connection con = null;
    Statement st = null;
    ResultSet rs = null;

    boolean defaultCommit = true;
    BasePrint.print(" updateClob : ", sql, true);
    try {
      //得到连接
      con = this.getConnection();
      defaultCommit = con.getAutoCommit();
      con.setAutoCommit(false);

      st = con.createStatement();
      rs = st.executeQuery(sql);

      if (rs.next()) {
        Object obj = rs.getClob(1);
        BufferedWriter writer = null;
        try {
          if (obj instanceof oracle.sql.CLOB) {
            BasePrint.println("clob is oracle.sql.CLOB");
            oracle.sql.CLOB clob = (oracle.sql.CLOB)obj;
            writer = new BufferedWriter(clob.getCharacterOutputStream());
          }
          writer.write(text, 0, text.length());
        }
        catch (Exception e) {
          e.printStackTrace();
        }
        finally {
          writer.close();
        }
      }
      con.commit();
    }
    catch (Exception e) {
      try {
        con.rollback();
      }
      catch (Exception e1) {
      }
      ;
      e.printStackTrace();
    }
    finally {
      if (rs != null) {
        try {
          rs.close();
        }
        catch (Exception e) {
        }
      }
      if (st != null) {
        try {
          st.close();
        }
        catch (Exception e) {
        }
      }
      if (con != null) {
        try {
          con.setAutoCommit(defaultCommit);
          con.close();
        }
        catch (Exception e) {
        }
      }
    }

    return true;
  }

  /**
   * 执行SQL取得字符串
   *
   * @param dataSourceName DataSource的名称
   * @param sql
   * @return 最终执行字符串
   * @throws RemoteException
   */
  public String getValue(String sql) throws
      RemoteException {
    ArrayList list = this.selectSQL("", sql);
    

    if (list.size() == 0) {
      return null;
    }
    BaseObject baseObj = (BaseObject)list.get(0);
    if (baseObj != null) {
      ArrayList value = baseObj.getFieldValue();
      return value.get(0).toString();
    }

    return null;

  }

  /**
   * 查询记录
   *
   * @param Connection 数据库连接
   * @param tableName 表名或视图的名称
   * @param sqlString 查询SQL语句
   * @return 查询记录列表
   * @throws RemoteException
   */
  public ArrayList selectSQL(Connection con, String tableName,
                             String sqlString) throws RemoteException {

    //查询不到数据，返回默认
    ArrayList rAL = new ArrayList();

    //检测
    if (con == null) {
      return rAL;
    }
    if (tableName == null) {
      return rAL;
    }
    if (sqlString == null) {
      return rAL;
    }

    //得到指定表类
    BaseObject obj = null;
    Statement stmt = null;
    ResultSet sqlrs = null;

    //表的字段数量
    int fieldCount = 0;

    int i = 0;
    int fieldType = 0;
    String fieldAlias = "";

    //显示SQL语句
    BasePrint.println(" selectSQL : "+sqlString);

    try {

      //得到连接
      stmt = con.createStatement();

      //查询指定的数据
      sqlrs = stmt.executeQuery(sqlString);

      //得到表或视图的结构
      obj = this.getTableByResultSet(sqlrs);

      //添加表或视图名称
      obj.setTableName(tableName);

      //得到表的字段数量
      fieldCount = obj.getFieldCount();
      while (sqlrs.next()) {

        //循环得到字段值
        ArrayList valueAL = new ArrayList();

        for (i = 0; i < fieldCount; i++) {

          //得到字段别名
          fieldAlias = (String)(obj.getFieldAlias().get(i));

          //得到字段类型
          fieldType = ((BaseDataType)(obj.getFieldType().get(i))).getDataType();

          //根据字段类型得到字段值
          switch (fieldType) {

            //如果是：INTEGER
            case BaseDataType.INTEGER:
              valueAL.add(new Integer(sqlrs.getInt(fieldAlias)));
              break;

              //如果是：NUMBER
            case BaseDataType.NUMBER:
              valueAL.add(sqlrs.getBigDecimal(fieldAlias));
              break;

              //如果是：STRING
            case BaseDataType.STRING:

              //如果是NULL,返回""
              String tempFRS = sqlrs.getString(fieldAlias);
              if (tempFRS == null) {
                valueAL.add(new String(""));
              }
              else {
                valueAL.add(new String(tempFRS.trim()));
              }

              break;

              //如果是：CLOB
            case BaseDataType.CHAR_STREAM:
              Clob clob = sqlrs.getClob(fieldAlias);
              BufferedReader reader = null;
              StringBuffer buffer = new StringBuffer();
              try {
                reader = new BufferedReader(clob.getCharacterStream());
                String line;
                while ((line = reader.readLine()) != null) {
                  buffer.append(line);
                }
              }
              catch (Exception e) {
                e.printStackTrace();
              }
              finally {
                try {
                  if (reader != null) {
                    reader.close();
                  }
                }
                catch (Exception e) {
                  e.printStackTrace();
                }
              }
              valueAL.add(buffer.toString());
              break;

              //如果是：DATE
            case BaseDataType.DATE:

              //读取时间
              //如果是NULL，返回默认值
              Timestamp tempTimestamp = sqlrs.getTimestamp(fieldAlias);
              Date tempDate = null;
              if (tempTimestamp != null) {
                tempDate = new Date(tempTimestamp.getTime());
              }
              valueAL.add(tempDate);
              //BasePrint.println(valueAL.size()-1);
              //valueAL.add(sqlrs.getDate(fieldAlias));
              break;

            default:
              valueAL.add(new String("")); ;

          }

          //更新
          fieldAlias = new String("");

        }

        //设定字段值
        obj.setFieldValue(valueAL);

        //清空字段值列表
        valueAL = new ArrayList();

        //更新
        fieldAlias = new String("");

        //未采用索引，直接添加字段值
        rAL.add(obj);

        //复制新的数据类
        obj = BaseObject.clone(obj);

      }

    }
    catch (Exception e) {
      if (e.getClass().isInstance(new SQLException())) {

        //BasePrint.println("数据库错误--代码："+( (SQLException) e).getErrorCode());

        e.printStackTrace();
        throw new BaseException("", ((SQLException)e).getErrorCode());
      }
      else {
        e.printStackTrace();
      }

    }
    finally {

      if (sqlrs != null) {
        try {
          sqlrs.close();
        }
        catch (SQLException ignore) {
        }
      }

      if (stmt != null) {
        try {
          stmt.close();
        }
        catch (SQLException ignore) {
        }
      }

    }

    return rAL;

  }

  /**
   * 保存一个BaseObject对象(Lib)
   * @param obj BaseObject
   * @throws RemoteException
   * @return boolean
   */
  public boolean insertBaseObject(BaseObject obj) throws RemoteException {
    return insertSQL(obj.getTableName(), obj);
  }

  /**
   * 修改一个BaseObject对象(Lib)
   * @param obj BaseObject
   * @param sqlWhere String
   * @throws RemoteException
   * @return boolean
   */
  public boolean updateBaseObject(BaseObject obj, String sqlWhere) throws RemoteException {
    return updateSQL(obj.getTableName(), obj, sqlWhere);
  }

  /**
   * 删除一个BaseObject对象(Lib)
   * @param obj BaseObject
   * @param sqlWhere String
   * @throws RemoteException
   * @return boolean
   */
  public boolean deleteBaseObject(BaseObject obj, String sqlWhere) throws RemoteException{
    return deleteSQL(obj.getTableName(),sqlWhere);
  }
  
   
  /**
   * 得到记录for查询条件
   *
   * @param strWhere
   *            查询条件
   * @param strTab
   *            查询的表名
   * @param fldOrderName
   *            排序字段名
   * @return ArrayList 指定列表
   * @throws RemoteException
   */
  public ArrayList selectSQL(String tableName,String strWhere, 
		  String fldOrderName) throws RemoteException {
	  StringBuffer sb = new StringBuffer("");
	  sb.append("select * from ");
	  sb.append(tableName);
	  if (strWhere != null && strWhere.equals("") == false) {
		  sb.append(" where " + strWhere);
	  }
	  if (fldOrderName != null && fldOrderName.equals("") == false) {
		  sb.append(" order by " + fldOrderName);
	  }
	  //生成查询列表
	  ArrayList rs = this.selectSQL(tableName, sb.toString());
	  return rs;
  } 
  
 
  /**
   * 得到记录for查询条件
   *
   * @param strWhere
   *            查询条件
   * @param strTab
   *            查询的表名
   * @param fldOrderName
   *            排序字段名
   * @param selectIndex 返回记录的首项
   * @param selectNumber 返回记录的项数
   * @throws RemoteException
   */
  public ArrayList selectSQL(String tableName,
		  String strWhere,String fldOrderName,int selectIndex,int selectNumber) throws RemoteException {
	  StringBuffer sqlString = new StringBuffer("");
	  
	  
	  if ((selectIndex==0)||(selectNumber==0))
	  {
		  return this.selectSQL(tableName,strWhere,fldOrderName);
	  }
	  
	  
	  //查询分页指定记录
	  sqlString.append(" select * ");
	  sqlString.append(" from ");
	  sqlString.append(" ( select ROWNUM as tempRowNumIndex , tempTableName.*  from ");
	  sqlString.append(tableName);
	  sqlString.append(" tempTableName where ");
	  
	  //查找首项索引
	  int searchFirstIndex = selectIndex * selectNumber;
	  
	  //查找末项索引
	  int searchLastIndex = searchFirstIndex + selectNumber;
	  
	  sqlString.append(" ROWNUM <= ");
	  
	  sqlString.append(searchLastIndex);
	  
	  if (strWhere != null && strWhere.equals("") == false) {
		  sqlString.append(" and " + strWhere);
	  }
	  if (fldOrderName != null && fldOrderName.equals("") == false) {
		  sqlString.append(" order by " + fldOrderName);
	  }
	  
	  sqlString.append(" ) where tempRowNumIndex > ");
	  sqlString.append(searchFirstIndex);
	  
	  //生成查询列表
	  ArrayList rs = this.selectSQL(tableName, sqlString.toString());
	  return rs;
  } 
  
  
}
