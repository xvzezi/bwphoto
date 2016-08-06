package service.imple;

import DAO.BookDao;
import model.db.Book;
import service.BookService;
import util.Log;

/**
 * Service Impl of Book
 * @author Xu Zezi
 * @since 2016/8/6.
 */
public class BookServiceImpl implements BookService
{
	@Override
	/**
	 * create or update an book
	 * @param ISBN
	 * @param url
	 * @return
	 */
	public String updateIsbnUrl(String ISBN, String url)
	{
		Book book = bookDao.FindBookByISBN(ISBN);
		try
		{
			if (book == null)
			{
				book = new Book();
				book.setIsbn(ISBN);
				book.setIntro(url);
				bookDao.saveObject(book);
			} else
			{
				book.setIntro(url);
				bookDao.updateBook(book);
			}
		}catch (Exception e)
		{
			Log.log.log("Error in Book Service: ").log(e.getMessage()).log();
			return "Wrong";
		}
		return "success";
	}

	private BookDao bookDao;
	public void setBookDao(BookDao bookDao) { this.bookDao = bookDao; }
}
