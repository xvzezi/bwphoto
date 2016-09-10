package DAO.imple;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import util.HibernateUtil;

import DAO.FriendDao;
import model.db.Friend;
import util.Log;

/**
 * FriendDaoImpl
 * @author ZhouTQ
 * @category DAO.imple
 * @version
 * 		0   FriendDaoImpl with Create,Retrieve,Delete
 * 	    0.8 transaction management
 * @since 2016/7/5
 * @Description
 *   first version
 */
public class FriendDaoImpl implements FriendDao
{
	
	/**
	 * 添加好友
	 * @param myName 当前用户用户名
	 * @param myName 添加用户用户名
	 * @return void
	 */
    
	public void addFriend(String myName,String frName)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try
		{
			session.beginTransaction();
			Friend friend = new Friend(myName, frName);
			session.save(friend);
		}catch (Exception e)
		{
			Log.log.log("Error In FriendDAO add:").log(e.getMessage()).log();
		}finally
		{
			session.getTransaction().commit();
		}
	};
	/**
	 * 获取目前所有的好友数量
	 * @param name
	 * @return amount
	 */
	public int getFriendAmount(String name)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try
		{
			session.beginTransaction();
			Query query = session.createQuery("select count(*) from Friend where myName=?");
			query.setString(0, name);
			int amount = (Integer)query.uniqueResult();
			return amount;
		}catch (Exception e)
		{
			Log.log.log("Error In FriendDAO getAmount:").log(e.getMessage()).log();
			return 0;
		}finally
		{
			session.getTransaction().commit();
		}
	}

	/**
	 * 查看好友列表
	 * @param myName 当前用户用户名
	 * @return List<Friend> Friend类型列表（包含当前用户信息）
	 */
	public List<Friend> FindFriend(String myName)
	{
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try
		{
			session.beginTransaction();
			String hql = "from Friend as friend where friend.myName=? ";
			Query query = session.createQuery(hql);
			query.setString(0, myName);
			List friendList = query.list();
			if (friendList != null && friendList.size() >= 1)
			{
				return (List<Friend>) friendList;
			} else
			{
				return null;
			}
		}catch (Exception e)
		{
			Log.log.log("Error In FriendDAO find:").log(e.getMessage()).log();
			return null;
		}finally
		{
			session.getTransaction().commit();
		}
	};
	
    /**
     * 删除好友
     * @param myName 当前用户名
     * @param frName 好友名
     * @return void
     */
	@Override
	public void deleteFriend(String myName,String frName)
	{
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try
		{
			session.beginTransaction();
			String hql = "from Friend as friend where friend.myName=? and friend.frName=? ";
			Query query = session.createQuery(hql);
			query.setString(0, myName).setString(1, frName);
			List friendList = query.list();

			if (friendList != null && friendList.size() >= 1)
			{
				session.delete(friendList.get(0));
			}
		}catch (Exception e)
		{
			Log.log.log("Error In FriendDAO deletion:").log(e.getMessage()).log();
		}finally
		{
			session.getTransaction().commit();
		}
	};


	/**
	 * 找到所有的朋友
	 * @param name
	 * @return 所有的朋友
	 *      由于上一个有冗余，所以有一个仅由名字构成的版本
	 */
	public List<String> findFriendRetNames(String name)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try
		{
			session.beginTransaction();
			String hql = "select frName from Friend where myName=?";
			Query query = session.createQuery(hql).setString(0, name);
			List<String> names = query.list();
			return names;
		}catch (Exception e)
		{
			Log.log.log("Error In FriendDAO findReturnNames:").log(e.getMessage()).log();
			return null;
		}finally
		{
			session.getTransaction().commit();
		}
	}

}

	