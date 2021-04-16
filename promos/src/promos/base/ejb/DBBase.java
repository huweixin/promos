package promos.base.ejb;

import java.rmi.RemoteException;
import java.util.ArrayList;

import java.sql.Connection;
//import java.sql.ResultSet;

import javax.ejb.Local;
import javax.sql.DataSource;

import promos.base.objects.BaseObject;
import promos.base.objects.FieldError;

//import javax.naming.Name;

/**
 *
 * <p>标题: 接口 DBBase </p>
 * <p>描述: 基类Bean的接口 </p>
 * <p>版权: Copyright (c) 2003-2004 </p>
 * <p>公司: 广州升域传媒有限公司 </p>
 * 保留所有权利。
 *
 * @author 胡维新
 * @author 胡维新
 * @version 1.0 04/22/2003
 *
 */
@Local

public interface DBBase {

  /**
   * 得到指定表或视图的结构，仅包含数字型和字符串型
   *
   *
   * @param strDataSource 数据源的JNDI名称
   * @param strTableName 表或视图的名称
   * @return 表或视图的机构
   * @throws RemoteException
   */
  public BaseObject getSimpleFieldByName(String strTableName) throws     RemoteException;

  public FieldError checkField( BaseObject baseObj) throws RemoteException;

  /**
   * 得到指定表或视图的结构
   *
   *
   * @param strDataSource 数据源的JNDI名称
   * @param strTableName 表或视图的名称
   * @return 表或视图的机构
   * @throws RemoteException
   */
  public BaseObject getTableByName( String strTableName) throws
      RemoteException;

  /**
   * 得到数据源
   *
   * @param dataSourceName 数据源JNDI命名
   * @return 数据源
   * @throws RemoteException
   */
  public DataSource getDataSource() throws RemoteException;

  /**
   * 得到连接
   *
   * @param dataSourceName 数据源JNDI命名
   * @return 连接
   * @throws RemoteException
   */
  public Connection getConnection() throws RemoteException;

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
      RemoteException;

  /**
   * 查询记录
   *
   * @param dataSourceName DataSource的名称
   * @param tableName 表名或视图的名称
   * @param sqlString 查询SQL语句
   * @return 查询记录列表
   * @throws RemoteException
   */
  public ArrayList selectSQL( String tableName,
                             String sqlString) throws RemoteException;

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
  public ArrayList selectSQL( String tableName,
                             ArrayList al, String strStatus, int selectIndex,
                             int selectNumber, int status) throws
      RemoteException;

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
  public ArrayList selectSQLEX( String tableName,
                               ArrayList al, String strStatus, String strSort,
                               int selectIndex, int selectNumber, int status) throws
      RemoteException;

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
  public ArrayList selectSQLEXSortAll( String tableName,
                                      ArrayList al, String strStatus,
                                      String strSort, int selectIndex,
                                      int selectNumber, int status) throws
      RemoteException;

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
   * @param strFields 指定查询的字段
   * @param al 查询SQL条件
   * @param strStatus 查询SQL额外的限定条件
   * @param selectIndex 返回记录的首项
   * @param selectNumber 返回记录的项数
   * @param status 查询记录还是查询数量标志
   * @return 查询记录列表
   * @throws RemoteException
   */
  public ArrayList selectSQL( String tableName,
                             String strFields, ArrayList al, String strStatus,
                             int selectIndex, int selectNumber, int status) throws
      RemoteException;

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
  public ArrayList selectSQLEX( String tableName,
                               String strFields, ArrayList al, String strStatus,
                               String strSort, int selectIndex,
                               int selectNumber, int status) throws
      RemoteException;

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
  public ArrayList selectSQLEXSortAll( String tableName,
                                      String strFields, ArrayList al,
                                      String strStatus, String strSort,
                                      int selectIndex, int selectNumber,
                                      int status) throws RemoteException;



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
 public ArrayList selectSQLEXSortAll( String tableName,
                                     String strFields, ArrayList al,
                                     String strStatus, String strSort,String strGroupBy,
                                     int selectIndex, int selectNumber,
                                     int status) throws RemoteException ;













  /**
   * 添加记录
   *
   * @param dataSourceName 数据源的名称
   * @param updateTableName 被添加记录的表的名称
   * @param obj 添加的记录
   * @return 如果成功添加记录，返回True，否则返回False
   * @throws RemoteException
   */
  public boolean insertSQL( String updateTableName,
                           BaseObject obj) throws RemoteException;

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
                                   String strWhere) throws RemoteException;

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
                           String sqlString) throws RemoteException;

  /**
   * 更新记录
   *
   * @param dataSourceName 数据源的名称
   * @param updateTableName 被更新记录的表的名称
   * @param obj 需要更新的记录
   * @param sqlString 更新记录的限定条件
   * @return 如果成功更新记录，返回True，否则返回False
   * @throws RemoteException
   */
  public boolean updateSQL( String updateTableName,
                           BaseObject obj, String sqlString) throws
      RemoteException;

  /**
   * 创建表
   *
   * @param dataSourceName 数据源的名称
   * @param updateTableName 被创建的表的名称
   * @param sqlString 被创建的表的结构
   * @return 如果成功创建表，返回True，否则返回False
   * @throws RemoteException
   */
  public boolean createSQL( String updateTableName,
                           String sqlString) throws RemoteException;

  /**
   * 删除表
   *
   * @param dataSourceName 数据源的名称
   * @param updateTableName 被删除的表的名称
   * @return 如果成功删除表，返回True，否则返回False
   * @throws RemoteException
   */
  public boolean dropSQL( String updateTableName) throws
      RemoteException;

  /**
   * 添加、删除、更新记录
   *
   * @param dataSourceName DataSource的名称
   * @param sqlString 添加、删除、更新的SQL语句
   * @return 如果操作成功，返回True,否则返回False
   * @throws RemoteException
   */
  public boolean executeSQL( String sqlString) throws
      RemoteException;

  /**
   * 执行Begin-end语句
   *
   * @param dataSourceName DataSource的名称
   * @param sqlString Begin-end语句
   * @return 如果操作成功，返回True,否则返回False
   * @throws RemoteException
   */
  public boolean executeBeginEnd( String sqlString) throws
      RemoteException;

  /**
   * 查询记录条数
   *
   * @param dataSourceName DataSource的名称
   * @param tableName 表名或视图的名称
   * @param sqlString 查询SQL语句
   * @return 记录条数
   * @throws RemoteException
   */
  public int getCount( String tableName, String sqlString) throws
      RemoteException;

  /**
   * 自动获取下一个序列号
   *
   * @param dataSourceName DataSource的名称
   * @param seqName 序列号名称
   * @return 下一个序列号
   * @throws RemoteException
   */
  public long getSeqNextValue( String seqName) throws
      RemoteException;

  /**
   * 执行一批SQL语句
   *
   * @param dataSourceName DataSource的名称
   * @param arrayList SQL语句列表
   * @return 最终执行结果
   * @throws RemoteException
   */
  public boolean executeBatch( ArrayList arrayList) throws
      RemoteException;

  /**
   * 更新大字符串字段
   *
   * @param dataSourceName DataSource的名称
   * @param sql
   * @param text
   * @return 最终执行结果
   * @throws RemoteException
   */
  public boolean updateClob( String sql, String text) throws
      RemoteException;

  /**
   * 执行SQL取得字符串
   *
   * @param dataSourceName DataSource的名称
   * @param sql
   * @return 最终执行字符串
   * @throws RemoteException
   */
  public String getValue( String sql) throws
      RemoteException;


  /**
   * 查询记录
   *
   * @param Connection 数据库连接
   * @param tableName 表名或视图的名称
   * @param sqlString 查询SQL语句
   * @return 查询记录列表
   * @throws RemoteException
   */
  public ArrayList selectSQL(Connection con, String tableName, String sqlString) throws RemoteException;

  /**
   * 新增一个BaseObject对象(Lib)
   * @param obj BaseObject
   * @throws RemoteException
   * @return boolean
   */
  public boolean insertBaseObject(BaseObject obj) throws RemoteException ;

  /**
   * 修改一个BaseObject对象(Lib)
   * @param obj BaseObject
   * @param sqlWhere String
   * @throws RemoteException
   * @return boolean
   */
  public boolean updateBaseObject(BaseObject obj,String sqlWhere) throws RemoteException;

  /**
   * 删除一个BaseObject对象(Lib)
   * @param obj BaseObject
   * @param sqlWhere String
   * @throws RemoteException
   * @return boolean
   */
  public boolean deleteBaseObject(BaseObject obj, String sqlWhere) throws RemoteException;
  
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
		  String fldOrderName) throws RemoteException;
  
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
  public ArrayList selectSQL( String tableName,
		  String strWhere,String fldOrderName,int selectIndex,int selectNumber) throws RemoteException;  
}
