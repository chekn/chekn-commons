package com.chekn.test;


import java.util.LinkedList;
import java.util.List;

public class LoggerUtils 
{
	public static String format(String msg, String... args)
	{
		//msg 中没有找到# 
		if(msg!=null && msg.length()>0 && msg.indexOf('#')>-1)
		{
			StringBuilder sb=new StringBuilder();
			boolean isArg=false;
			for(int x=0;x<msg.length();x++){
				char c=msg.charAt(x);
				if(isArg){
					isArg=false;
					if(Character.isDigit(c)){					//判断是否是数字
						int val=Character.getNumericValue(c);	//返回指定的unicode 字符表示的int 值
						if(val>=0 && val<args.length){
							sb.append(args[val]);
							continue;
						}
					}
					sb.append("#");
				}
				
				if(c=='#'){
					isArg=true;
					continue;
				}
				
				sb.append(c);
			}
			
			//最后如果 #是参数
			if(isArg){
				sb.append('#');
			}
			return sb.toString();
		}
		return msg;
	}
	
	public static String format(String msg,Object[] args){
		List<String> strArgs=new LinkedList<String>();
		for(Object arg:args){
		 strArgs.add(arg!=null?arg.toString():"(null)");	
		}
		return format(msg,strArgs.toArray(new String[strArgs.size()]));
 	}

	
//	public static void main(String[] args)
//	{
//		//有点像C 的格式化输出
//		System.out.println(LoggerUtils.format("foo #3 #1 #2 3#1", "A","B","C","CD"));
//		
//		System.out.println(LoggerUtils.format("Echo: #1 #2",new Object[]{"Rx","Tx",null}));
//	}
}
	


