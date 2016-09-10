package util;

import java.util.Date;

/**
 * 在线统计数据
 * @author Xu Zezi
 * @since 2016/7/12
 *
 */
public class StatisticUtil
{
	// 用户登录次数
	public static Integer visitorCounter = 0;
	// 当日新资源
	public static Integer newResource = 0;
	// 当日好友成对次数
	public static Integer friendsMade = 0;
	// 给定探测时间段，以及上次保留数字
	public static Integer timeSlash = 10000000;
	public static int lastNum = 0;

	// 相应的统计工具
	public static void check()
	{
		long timestamp = new Date().getTime();
		int preNum = (int) ((timestamp / timeSlash) % 10);
		if(lastNum != preNum)
		{
			visitorCounter = 0;
			newResource = 0;
			friendsMade = 0;
			lastNum = preNum;
		}
	}
	public static void visit()
	{
		check();
		visitorCounter++;
	}
	public static void resource()
	{
		check();
		newResource++;
	}
	public static void friend()
	{
		check();
		friendsMade++;
	}
}
