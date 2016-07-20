package DAO.imple;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import DAO.ItemDao;
import model.db.Book;
import model.db.Item;
import util.HibernateUtil;

/**
 * ItemDaoImpl
 * @author ZhouTQ
 * @category DAO.imple
 * @version
 * 		0 ItemDaoImpl with CRUD
 * @since 2016/7/5
 * @Description
 *   first version
 *   support Retrieving through id and username
 */
public class ItemDaoImpl implements ItemDao
{
	
	/**
	 * 添加一个项目对象
	 * @param item
	 * @return void
	 */
    public void saveObject(Item item)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(item);
		session.getTransaction().commit();
    }
	/**
	 * 删除一个项目对象
	 * @param item
	 * @return void
	 */
	public void deleteItem(Item item)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete(item);
		session.getTransaction().commit();
	};
	/**
	 * 根据id查找item
	 * @param id
	 * @return void
	 */
	public Item FindItemById(int id)
	{
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String hql = "from Item as item where item.id=? ";  
		Query query=session.createQuery(hql);
		query.setInteger(0, id);
		List itemList = query.list();  
		session.getTransaction().commit();
		if (itemList != null && itemList.size() >= 1) 
		{
			return (Item) itemList.get(0);
		} else 
		{
			return null;
		}
	}
	
	/**
	 * 修改项目信息
	 * @param item
	 * @return void
	 */
	public void updateItem(Item item) 
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update(item);
		session.getTransaction().commit();
		
	}
	
	
	/**
	 * 查找当前用户的item列表
	 * @param username
	 * @return List<Item>
	 */
	public List<Item> FindItemByuserName(String username)
	{
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		String hql = "from Item where userName=? ";
		Query query=session.createQuery(hql);
		query.setString(0, username);
		List itemList = query.list();
		session.getTransaction().commit();
		if (itemList != null && itemList.size() >= 1) 
		{
			return (List<Item>) itemList;
		} 
		else 
		{
			return null;
		}
	}
}
	 