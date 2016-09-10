package DAO.imple;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import DAO.ItemDao;
import model.db.Book;
import model.db.Item;
import util.HibernateUtil;
import util.Log;

/**
 * ItemDaoImpl
 * @author ZhouTQ
 * @category DAO.imple
 * @version
 * 		0 ItemDaoImpl with CRUD
 * 	    1 CRUD promoted 2016/7/28 by Xu
 * @since 2016/7/5
 * @Description
 *   first version
 *   support Retrieving through id and username
 */
public class ItemDaoImpl implements ItemDao
{
	/**
	 * 获取最近的资源——所有人的
	 * @param timestamp
	 * @param amount
	 * @return resource
	 *      返回权限 —— public
	 */
	public List<Item> getItems(Timestamp timestamp, int amount)
	{
		String hql = "";
		if(timestamp == null)
		{
			hql = "from Item where status='1' order by time ";
		}
		else
		{
			hql = "from Item where time < ? and status='1' order by time";
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(hql);
		if(timestamp != null)
		{
			query.setTimestamp(0, timestamp);
		}
		if(amount >= 0)
		{
			query.setFetchSize(amount);
		}
		List<Item> items = null;
		try
		{
			items = query.list();
		}catch (Exception e)
		{
			Log.log.log("Error In ItemDao getItems:").log(e.getMessage()).log();
		}finally
		{
			session.getTransaction().commit();
		}
		return items;
	}
	
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
	 * @deprecated 这是一个下面一个函数的初版， 不够完善，将考虑删去
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

	/**
	 * 通过用户名获取全部资源
	 * @param name
	 * @return 全部资源
	 *         if Timestamp is null -- give for a certain amount name's resource
	 *         if amount is no more than 0 -- return all back
	 *         if auth is false -- only give public things
	 */
	public List<Item> FindItemByUsername(String name, Timestamp timestamp, int amount, boolean auth)
	{
		// check valid
		if(name == null)
		{
			return null;
		}

		// choose and form the hql sentence
		String hql = "";
		Query query = null;

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		if(timestamp != null)
		{
			hql = "from Item where userName=? and time < ? ";

		}
		else
		{
			hql = "from Item where userName=? ";
		}
		//auth
		if(!auth)
		{
			hql += "and status='1' ";
		}
		hql += "order by time";
		query = session.createQuery(hql);

		// make it up
		query.setString(0, name);
		if(timestamp != null)
		{
			query.setTimestamp(1, timestamp);
		}
		if(amount > 0)
		{
			query.setFetchSize(amount);
		}
		List<Item> items = (List<Item>)query.list();
		session.getTransaction().commit();
		return items;
	}

	/**
	 * 通过用户名获取朋友的最新资源
	 * @param names
	 * @param timestamp
	 * @param amount
	 * @return 全部资源
	 *         if Timestamp is null -- give for a certain amount name's resource
	 *         if amount is no more than 0 -- return all back
	 */
	public List<Item> findItemOfUserFriend(List<String> names, Timestamp timestamp, int amount)
	{
		// check if valid, most of the case, it is
		if(names == null)
		{
			return null;
		}

		// forge the statement
		String hql = "from Item where userName in (:nameList) ";
		if(timestamp != null)
		{
			hql += "and time < :timestamp";
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(hql)
				.setParameterList("nameList", names);
		if(timestamp!=null)
		{
			query.setTimestamp("timestamp", timestamp);
		}
		if(amount > 0)
		{
			query.setFetchSize(amount);
		}
		List<Item> items = query.list();
		session.getTransaction().commit();
		return items;
	}

	/**
	 * amount
	 */
	public int getAmount()
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try
		{
			session.beginTransaction();
			Query query = session.createQuery("select count(*) from Item");
			Integer i = (Integer) query.uniqueResult();
			return i;
		}catch (Exception e)
		{
			Log.log.log("Error In ItemDAO getAmount:").log(e.getMessage()).log();
			return 0;
		}finally
		{
			session.getTransaction().commit();
		}
	}
}
	 