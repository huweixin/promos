package promos.base.objects;

import java.io.Serializable;

/**
 *
 * <p>标题: 类 BaseOuter </p>
 * <p>描述: 信息输出静态类 </p>
 * <p>版权: Copyright (c) 2003-2004 </p>
 * <p>公司: 广州升域传媒有限公司 </p>
 * 保留所有权利。
 *
 * @author 胡维新
 * @version 1.0 09/24/2003
 *
 */


public class BasePrint implements Serializable {

  //唯一存在的实例
  private static BasePrint _instance = null;

  //是否关闭所有的显示信息
  private static boolean isShowMessage = false;

  /**
   * 构建，禁止子类继承
   */
  private BasePrint() {
  }

  /**
   * 得到静态类的实例
   *
   * @return 信息输出静态类
   */
  public static BasePrint getInstance() {

    //检测实例
    if (_instance == null) {
      _instance = new BasePrint();

	  //System.out.println("show_message:"+(BaseConfig.getIntOfSettingByName("show_message")));
      //读取显示信息标志
      if((BaseConfig.getIntOfSettingByName("show_message")) == 1){
         isShowMessage = true;
      }

    }

    return _instance;

  }

  /**
   * 输出信息到控制台
   *
   * @param strMessage 输出信息
   */
  public static void println(String strMessage){
	  
	if (isShowMessage==false) return;

    //显示输出信息
    if(strMessage != null){
    	System.out.println(strMessage);
    }
  }
  
  
  /**
   * 输出信息到控制台
   *
   * @param strNote 附录信息
   * @param strMessage 输出信息
   * @param isBR 是否换行
   */
  public static void print(String strNote,String strMessage,boolean isBR){

    //显示附录信息
    if(strNote != null){
      System.out.print(strNote);
    }

    //显示输出信息
    if(strMessage != null){

      if(isBR){
        BasePrint.println(strMessage);
      }
      else{
        BasePrint.println(strMessage);
      }

    }
  }




}