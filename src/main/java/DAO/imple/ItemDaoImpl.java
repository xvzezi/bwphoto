package DAO.imple;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import DAO.ItemDao;
import model.db.Book;
import model.db.Item;
import util.HibernateUtil;


public class ItemDaoImpl implements ItemDao
{

	  public void saveObject(Item item)
	  {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.save(item);
			session.getTransaction().commit();
	  };
	  public void deleteItem(Item item)
	  {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.delete(item);
			session.getTransaction().commit();
	  };
	@Override
	public Item FindItemById(int id) {
		// TODO Auto-generated method stub
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String hql = "from Item as item where item.id=? ";  
		Query query=session.createQuery(hql);
		query.setInteger(0, id);
		List itemList = query.list();  
		if (itemList != null && itemList.size() >= 1) 
		{
			return (Item) itemList.get(0);
		} else 
		{
			return null;
		}
	};
	
	public void updateItem(Item item) 
	{
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update(item);
		session.getTransaction().commit();
		
	};
}
	 