package promos.base.objects;

import java.text.*;
import java.lang.*;

public class FormatTools{

	public static void main(String[] args){
		/*
		float flt = Float.parseFloat(args[0]);
		int dec = Integer.parseInt(args[1]);
		BasePrint.println(formatFloat(flt, dec));

		int sec = Integer.parseInt(args[2]);
		BasePrint.println(formatSec(sec));
		*/
		String time = args[0];
		//BasePrint.println(toSecond(time));

	}

	public static String formatSec(int sec){
		NumberFormat nf = new DecimalFormat("00");

		String second = nf.format(sec%60);

		int min = sec/60;
		String minute = nf.format(min%60);

		String hour = nf.format(min/60);
		return hour+":"+minute+":"+second;
	}

	public static int toSecond(String time){
		int duration;

		time = time.trim();
		int hour = Integer.parseInt(time.substring(0,2));
		duration = hour * 60 * 60;
		int min = Integer.parseInt(time.substring(3,5));
		duration = duration + min*60;
		int second = Integer.parseInt(time.substring(6,8));
		duration = duration + second;

		return duration;
	}

	public static String formatFloat(double flt){
		NumberFormat nf = new DecimalFormat("0.00");
		return nf.format(flt);
	}

	public static String formatFloat(double flt, int dec){
		String pattern = "0";
		if (dec>0){
			pattern="0.";
			for (int i=0; i<dec; i++){
				pattern = pattern + "0";
			}
		}
		NumberFormat nf = new DecimalFormat(pattern);
		return nf.format(flt);
	}

///////////////////////////////////////new
	public static float format(double flt){
		String tmp = formatFloat(flt);
		return Float.parseFloat(tmp);

	}

	public static float format(double flt, int dec){
		String tmp = formatFloat(flt,dec);
		return Float.parseFloat(tmp);
	}



	//double format
	public static String DoubleToString(double dbl){
		NumberFormat nf = new DecimalFormat("0.00");
		return nf.format(dbl);
	}

	public static String DoubleToString(double dbl, int dec){
		String pattern = "0";
		if (dec>0){
			pattern="0.";
			for (int i=0; i<dec; i++){
				pattern = pattern + "0";
			}
		}
		NumberFormat nf = new DecimalFormat(pattern);
		return nf.format(dbl);
	}

	public static double formatDouble(double dbl){
		String tmp = DoubleToString(dbl);
		return Double.parseDouble(tmp);

	}

	public static double formatDouble(double dbl, int dec){
		String tmp = DoubleToString(dbl,dec);
		return Double.parseDouble(tmp);
	}

        /**
         * 转UTF-8字符串<br/>
         * return ex: %ab%a1%66
         * @param arg0 a <code>String</code>
         * @return a String
         * */
        public static String getStrByUTF8(String arg0){
                if (arg0 == null) {
                  return null;
                }
                byte[] str = arg0.getBytes();
                StringBuffer sb = new StringBuffer();
                for (int i=0; i<str.length; i++){
                        sb.append("%"+Integer.toHexString(str[i] & 0xFF));
                }
                return sb.toString();
        }


        //得到在Oracle数据库中正常的字符
        public static String getOracleString(String strSourc) {
          if (isNull(strSourc)) {
            strSourc = "";
          }
          else {
            strSourc = strSourc.replaceAll("'", "''");
            //strSourc = strSourc.replaceAll("~", "#");
          }

          return strSourc;
        }

        /**
         * 判断是否为空
         * @param str String
         * @return boolean
         */
        public static boolean isNull(String str) {
          return !isNotNull(str);
        }

        /**
         * 判断是否为空
         * @param str String
         * @return boolean
         */
        public static boolean isNotNull(String str) {
          if (str != null && str.length()>0) {
            return true;
          }
          return false;
        }


	/**
     * 将代表数字的字符转换成每隔3位用逗号隔开的long型数据。
     * 比如将10000000，转换成 10,000,000   不带RMB符号
     *
     * @param val
     * @return
     */
    public static String getCurrencyValueFromDouble(double val){
        //NumberFormat  formatter = NumberFormat.getCurrencyInstance();
        NumberFormat  formatter = NumberFormat.getNumberInstance();
        String retun_val = formatter.format(val);
        BasePrint.println("转换以后的值为："+retun_val);
        return retun_val;
    }

    /**
     * 将每隔3位用一个逗号隔开的字符串转换成实际的数值。
     *
     * @param str
     * @return
     */
    public static double getDoubleValueFromCurrency(String str){
        NumberFormat  formatter = NumberFormat.getNumberInstance();
        int str_index_pot = str.indexOf(".");
        Number num = null;
        double d_val = 0;
        try{
            if (str_index_pot != -1) {
                String str_start = str.substring(0, str_index_pot);
                String str_end = str.substring(str_index_pot);
                str_end = "0" + str_end;
                num = formatter.parse(str_start);
                d_val = Double.parseDouble(str_end) + num.doubleValue();

            }
            else {
                num = formatter.parse(str);
                d_val = num.doubleValue() ;

            }
        }catch(Exception e){

        }
        BasePrint.println("转换之前字符串为:" + str);
        BasePrint.println("转换之后结果为:" + d_val);
        return d_val;
    }

};
