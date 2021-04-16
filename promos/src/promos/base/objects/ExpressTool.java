package promos.base.objects;

import java.io.Serializable;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
 

/**
 *
 * <p>����: �� ExpressTool </p>
 * <p>����: JAVA�߼����ʽ��У��ͼ��� </p>
 * <p>��Ȩ: Copyright (c) 2003-2004 </p>
 * <p>��˾: ��������ý���޹�˾ </p>
 * ��������Ȩ����
 *
 *
 * @author ��ά��
 * @version 1.0 05/14/2003
 *
 */

public class ExpressTool
    implements Serializable {
	

  private static final long serialVersionUID = 1L;
	
  /**
   * ����
   */
  public ExpressTool() {
  }


  /**
   * ��Ҫ���ж��߼����ʽ�Ƿ�Ϸ�
   *
   * @param expression �߼����ʽ
   * @return ����booleanֵ
   */
  public static boolean validateExpression(String expression){
	  
	// ͳһ��д
	  expression=expression.toUpperCase();
	  
	  //ȥ�ո� �滻һЩ����
	  expression=expression.replaceAll(" ","")
	          .replaceAll("AND","&&")
	          .replaceAll("OR","||")
	          .replaceAll("NOT","!")
	          .replaceAll("��","(")
	          .replaceAll("��",")");
	  
	  //�жϼ������������
	  if(expression.equals("!!")||expression.equals("?/)")||expression.equals("!/)")||expression.equals("!?")){
	      return  false;
	  }
	  
	  
	  // �����ǵ����ڶ������ж�С�������������Ƿ��ͬ������λ���Ƿ�Ϸ�,�������ȫ���Ϸ�����ȥ�����������ͣ�
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
	  
	  //������?��ͷ ���߽�β
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

	    if (validateExpression(expression)==false) return "ERROE:���ʽ����";
		
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
