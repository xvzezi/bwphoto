import model.db.*;
import model.db.User;
import org.junit.Test;
import service.LogService;
import util.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @since 2016/9/1.
 */
public class UtilTest extends TestTool
{
	@Test
	public void testStatistic()
	{
		testPart("Statistic Utility");
		// ------------------------------
		slash("Visitor Counter");
		println("counter init: " + StatisticUtil.visitorCounter);
		println("run plus one ...");
		StatisticUtil.visit();
		println("counter now: " + StatisticUtil.visitorCounter);
		// ------------------------------
		slash("Resource Counter");
		println("counter init: " + StatisticUtil.newResource);
		println("run plus one ...");
		StatisticUtil.resource();
		println("counter now: " + StatisticUtil.newResource);
		// ------------------------------
		slash("Friend Counter");
		println("counter init: " + StatisticUtil.friendsMade);
		println("run plus one ...");
		StatisticUtil.friend();
		println("counter now: " + StatisticUtil.friendsMade);
		// ------------------------------
		testPartEnd("Statistic Utility");
	}

	@Test
	public void testSpringIoC()
	{
		testPart("Spring IoC");
		// -------------------------------
		slash("Normal generating");
		println("Get Log Service");
		println("Service @" + SpringIoC.idGetter("logService", LogService.class));
		// -------------------------------
		slash("Wrong generating");
		println("Get non-existed service");
		println("Get xxx service");
		try
		{
			Object o = SpringIoC.idGetter("xxx", LogService.class);
			println("Get a xxx Service @:" + o);
		}catch (Exception e)
		{
			println("Error Occurred as Expected: " + e.getMessage());
		}
		testPartEnd("Spring IoC");
	}

	@Test
	public void testRestUitl()
	{
		testPart("Restful Cookie Utility");
		// ------------------------------
		slash("Utility Used Inside Android End.");
		slash("Test Part Is integreted Inside.");
		slash("passed as success");
		// ------------------------------
		testPartEnd("Restful Cookie Utility");
	}

	@Test
	public void testMyLog()
	{
		testPart("Log Record");
		// ------------------------------
		slash("Acclerrated Message Adding");
		println("Not Expect Message being printed");
		Log.log.log("Should not be printed.");
		println("Now should be printed, with the former message together");
		Log.log.log("Should be printed").log();
		// ------------------------------
		testPartEnd("Log Record");
	}

	@Test
	public void testMongoUtil()
	{
		testPart("Mongo Communication Utility");
		// ------------------------------
		slash("Utility Used Inside Service End.");
		slash("Test Part Is integreted Inside.");
		slash("passed as success");
		// ------------------------------
		testPartEnd("Mongo Communication Utility");
	}

	@Test
	public void testEdison()
	{
		testPart("Edison Test");
		try{
			slash("connecting");
			Edison edison = new Edison(108, 80, "memory");
			model.db.User user = new User();
			user.setAge(10);
			user.setName("Love");
			List<User> users = new ArrayList<>();
			users.add(user);
			slash("Sending Array");
			edison.sendArray(users);
			slash("Getting Array");
			List<User> uss = edison.getArray(User.class);
			for(User tar: uss)
			{
				System.out.println(tar.getName());
			}
			slash("connection closing");
			edison.close();

		}catch (EdisonError e)
		{
			System.out.println(e.getMessage());
		}
		testPartEnd("Edison Test");
	}

	@Test
	public void testGetAge()
	{
		testPart("Date get age");
		// --------------------------------------------------
		slash("Expecting: ");
		println("Inputting: 1996.6.21");
		println("should get 20 years old");
		slash("Inputting: ");
		Date date = new Date();
		date.setYear(96);
		date.setMonth(5);
		date.setDate(21);
		println("We get" + new DateGetAge().getAge(date));
		// --------------------------------------------------
		slash("Expecting: ");
		println("Inputting: 1996.10.21");
		println("should get 19 years old");
		slash("Inputting: ");
		date.setYear(96);
		date.setMonth(9);
		date.setDate(21);
		println("We get" + new DateGetAge().getAge(date));
		// ---------------------------------------------------
		testPartEnd("Date get age");
	}
}
