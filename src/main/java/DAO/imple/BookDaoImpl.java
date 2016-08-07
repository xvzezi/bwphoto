package DAO.imple;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import DAO.BookDao;
import model.db.Book;
import util.HibernateUtil;
import util.Log;

/**
 * BookDaoImpl
 * @author ZhouTQ
 * @category DAO
 * @version
 * 		0 BookDaoImpl with CRUD
 * 	    1 transaction management
 * @since 2016/7/5
 * @Description
 *   first version
 *   support Retrieving through ISBN, name of book, author of book and both name&author
 */
public class BookDaoImpl implements BookDao
{
	
	/**
	 * 添加书籍
	 * @param book 
	 * @return void
	 */
	public void saveObject(Book book)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try
		{
			session.beginTransaction();
			session.save(book);
		}catch (Exception e)
		{
			Log.log.log("Error In BookDAO creation:").log(e.getMessage()).log();
		}finally
		{
			session.getTransaction().commit();
		}
	};
	
	/**
	 * 删除书籍
	 * @param book
	 * @return void
	 */
	public void deleteBook(Book book)
	{
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try
		{
			session.beginTransaction();
			session.delete(book);
		}catch (Exception e)
		{
			Log.log.log("Error In BookDAO deletion:").log(e.getMessage()).log();
		}finally
		{
			session.getTransaction().commit();
		}
    };
    
    /**
     * 用条形码搜索书籍
     * @param ISBN（精确搜索）
     * @return Book Found
     */
	public Book FindBookByISBN(String ISBN)
	{
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try
		{
			session.beginTransaction();
			String hql = "from Book as book where book.isbn=? ";
			Query query = session.createQuery(hql);
			query.setString(0, ISBN);
			List bookList = query.list();
			if (bookList != null && bookList.size() >= 1)
			{
				return (Book) bookList.get(0);
			} else
			{
				return null;
			}
		}catch (Exception e)
		{
			Log.log.log("Error In BookDAO findByISBN:").log(e.getMessage()).log();
			return null;
		}finally
		{
			session.getTransaction().commit();
		}
	};

	/**
	 * 根据书籍名对书籍（相似搜索）
	 * @param name 书籍名
	 * @return List<Book> found
	 */
	@Override
	public List<Book> FindBookByName(String name)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try
		{
			session.beginTransaction();

			String hql = "from Book as book where book.name like '%'||?||'%' ";
			Query query = session.createQuery(hql);
			query.setString(0, name);
			List bookList = query.list();
			if (bookList != null && bookList.size() >= 1)
			{
				return (List<Book>) bookList;
			} else
			{
				return null;
			}
		}catch (Exception e)
		{
			Log.log.log("Error In BookDAO findByName:").log(e.getMessage()).log();
			return null;
		}finally
		{
			session.getTransaction().commit();
		}
	};
	  
		/**
		 * 搜索某一作家的书籍（相似搜索）
		 * @param author
		 * @return List<Book> 相关作家的的书籍列表
		 */
	  @Override
	  public List<Book> FindBookByAuthor(String author) 
	  {
	      Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		  try
		  {
			  session.beginTransaction();
			  String hql = "from Book as book where book.author like '%'||?||'%' ";
			  Query query = session.createQuery(hql);
			  query.setString(0, author);
			  List bookList = query.list();
			  if (bookList != null && bookList.size() >= 1)
			  {
				  return (List<Book>) bookList;
			  } else
			  {
				  return null;
			  }
		  }catch (Exception e)
		  {
			  Log.log.log("Error In BookDAO findByAuthor").log(e.getMessage()).log();
			  return null;
		  }finally
		  {
			  session.getTransaction().commit();
		  }
	  }
	  
		/**
		 * 根据书籍名和作家搜索（相似搜索）
		 * @param name 书籍名
		 * @param author 作者名
		 * @return List<Book> 相关书籍名及作者的书籍列表
		 */
	  @Override
	  public List<Book> FindBookByNameAndAuthor(String name, String author) 
	  {
	      Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		  try
		  {
			  session.beginTransaction();
			  String hql = "from Book as book where book.name like '%'||?||'%'and book.author like '%'||?||'%' ";
			  Query query = session.createQuery(hql);
			  query.setString(0, name).setString(1, author);
			  List bookList = query.list();
			  if (bookList != null && bookList.size() >= 1)
			  {
				  return (List<Book>) bookList;
			  } else
			  {
				  return null;
			  }
		  }catch (Exception e)
		  {
			  Log.log.log("Error In BookDAO findByNameAuthor:").log(e.getMessage()).log();
			  return null;
		  }finally
		  {
			  session.getTransaction().commit();
		  }
	  }
	  
		/**
		 * 修改book信息
		 * @param book
		 * @return void
		 */
	  @Override
	  public void updateBook(Book book) 
	  {
		  Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		  try
		  {
			  session.beginTransaction();
			  session.update(book);
		  }catch (Exception e)
		  {
			  Log.log.log("Error In BookDAO update:").log(e.getMessage()).log();
			  return;
		  }finally
		  {
			  session.getTransaction().commit();
		  }
	  };

}
