package service;

/**
 * Service of Book
 * @author Xu Zezi
 * @since  2016/8/6.
 */
public interface BookService
{
	/**
	 * create or update an book
	 * @param ISBN
	 * @param url
	 * @return
	 */
	public String updateIsbnUrl(String ISBN, String url);
}
