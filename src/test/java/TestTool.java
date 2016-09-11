/**
 * @since 2016/9/1.
 */
public class TestTool
{
	public void println(String mes)
	{
		System.out.println(mes);
	}

	public void print(String mes)
	{
		System.out.print(mes);
	}

	public void testPart(String mes)
	{
		println("***************************");
		print("Test Begin: ");
		println(mes);
	}

	public void testPartEnd(String mes)
	{
		print("Test End: ");
		println(mes);
		println("***************************");
	}

	public void slash(String mes)
	{
		print("> ");
		println(mes);
	}
}
