package promos.base.objects;

import java.io.Serializable;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
 

/**
 *
 * <p>标题: 类 ExpressTool </p>
 * <p>描述: JAVA逻辑表达式的校验和计算 </p>
 * <p>版权: Copyright (c) 2003-2004 </p>
 * <p>公司: 广州升域传媒有限公司 </p>
 * 保留所有权利。
 *
 *
 * @author 胡维新
 * @version 1.0 05/14/2003
 *
 */

public class ExpressTool
    implements Serializable {
	

  private static final long serialVersionUID = 1L;
	
  /**
   * 构建
   */
  public ExpressTool() {
  }


  /**
   * 主要是判断逻辑表达式是否合法
   *
   * @param expression 逻辑表达式
   * @return 返回boolean值
   */
  public static boolean validateExpression(String expression){
	  
	// 统一大写
	  expression=expression.toUpperCase();
	  
	  //去空格 替换一些符号
	  expression=expression.replaceAll(" ","")
	          .replaceAll("AND","&&")
	          .replaceAll("OR","||")
	          .replaceAll("NOT","!")
	          .replaceAll("（","(")
	          .replaceAll("）",")");
	  
	  //判断几个错误的例子
	  if(expression.equals("!!")||expression.equals("?/)")||expression.equals("!/)")||expression.equals("!?")){
	      return  false;
	  }
	  
	  
	  // 必须是倒数第二步：判断小括号左右括弧是否等同，括弧位置是否合法,如果括弧全部合法，则去掉所有括弧和！
	  int num = 0;
	  char[] expChars = expression.toCharArray();
	  for (int i = 0; i < expChars.length; i++) {
	      char temp = expChars[i];
	      if (temp == '(') {
	          num++;
	      } else if (temp == ')') {
	          num--;
	      }
	      if (num < 0) {
	          return false;
	      }
	  }
	  if (num > 0) {
	      return false;
	  }
	  
	  expression = expression.replaceAll("\\(|\\)|!", "");
	  
	  //不能以?开头 或者结尾
	  if(expression.startsWith("&")||expression.endsWith("&")||expression.startsWith("|")||expression.endsWith("|")){
	      return false;
	  }	  

    return true;

  }
  
  
  public static Boolean assExpressbool(String expression) {
	  
	    if (validateExpression(expression)==false) return false;
	    
		String el = expression;
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		boolean eval = false;
		try {
			eval = (boolean) engine.eval(el);
		} catch (ScriptException e) {
			e.printStackTrace();
		}

	    //System.out.println("expression:"+expression);
	    //System.out.println("eval:"+eval);
	    
		return eval;
	}

	public static String calExpressval(String expression) {

	    if (validateExpression(expression)==false) return "ERROE:表达式错误！";
		
		ScriptEngineManager scriptEngineManager 
		= new ScriptEngineManager();
		ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("js");//nashorn
		try {
			String result = String.valueOf(scriptEngine.eval(expression));
			return result;
		} catch (ScriptException e) {
			e.printStackTrace();
			return "";
		}
	}
  

}
