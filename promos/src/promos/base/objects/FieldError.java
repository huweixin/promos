package promos.base.objects;

import java.io.Serializable;

public class FieldError implements Serializable{
	public static final int TYPE_ERROR = 0;
	public static final int VALUE_TOO_LARGER = 1;

	public String alias;
	public int len;
	//public String cname;
	public int dataType;			//BaseDataType
	public int errorCode;

}