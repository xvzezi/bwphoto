package util;

/**
 * Interface EdisonError
 * @since 2016/9/6.
 */
public class EdisonError extends Throwable
{
	String error;

	public EdisonError(String mes)
	{
		this.error = mes;
	}

	public String getMessage()
	{
		return this.error;
	}
}
