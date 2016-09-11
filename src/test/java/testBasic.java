import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by hasee on 2016/9/8.
 */
public class testBasic
{
	static void print(String mes)
	{
		System.out.println(mes);
	}


	public static void main(String[] args)
	{
		List<String> a = new ArrayList<>();
		a.add("a");
		a.add("B");
		ObjectMapper om = new ObjectMapper();
		try
		{
			String json = om.writeValueAsString(a);
			System.out.print(json);
		}
		catch (Exception e)
		{}
	}
}
