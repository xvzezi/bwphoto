package DAO.imple;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import DAO.BookDao;
import model.db.Book;
import util.HibernateUtil;


public class BookDaoImpl implements BookDao
{

	  public void saveObject(Book book)
	  {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.save(book);
			session.getTransaction().commit();
	  };
	  public void deleteBook(Book book)
	  {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.delete(book);
			session.getTransaction().commit();
	  };
	  public Book FindBookByISBN(String ISBN)
	  {
			// TODO Auto-generated method stub
		    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			String hql = "from Book as book where book.isbn=? ";  
			Query query=session.createQuery(hql);
			query.setString(0, ISBN);
			List bookList = query.list();  
			if (bookList != null && bookList.size() >= 1) 
			{
				return (Book) bookList.get(0);
			} else 
			{
				return null;
			}
	  };

	@Override
	public List<Book> FindBookByName(String name) 
	{
		// TODO Auto-generated method stub
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String hql = "from Book as book where book.name like '%"+name+"%' ";   
		Query query=session.createQuery(hql);
		query.setString(0, name);
		List bookList = query.list();  
		if (bookList != null && bookList.size() >= 1) 
		{
			return (List<Book>) bookList;
		} else 
		{
			return null;
		}
	};
	@Override
	public List<Book> FindBookByAuthor(String author) 
	{
		// TODO Auto-generated method stub
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String hql = "from Book as book where book.author like '%"+author+"%' ";   
		Query query=session.createQuery(hql);
		query.setString(0, author);
		List bookList = query.list();  
		if (bookList != null && bookList.size() >= 1) 
		{
			return (List<Book>) bookList;
		} else 
		{
			return null;
		}
	}
	@Override
	public List<Book> FindBookByNameAndAuthor(String name, String author) 
	{
		// TODO Auto-generated method stub
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String hql = "from Book as book where book.name like '%"+name+"%'and book.author like '%"+author+"%' ";   
		Query query=session.createQuery(hql);
		query.setString(0, name).setString(1, author);
		List bookList = query.list();  
		if (bookList != null && bookList.size() >= 1) 
		{
			return (List<Book>) bookList;
		} else 
		{
			return null;
		}
	}
	@Override
	public void updateBook(Book book) 
	{
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update(book);
		session.getTransaction().commit();
		
	};


}
