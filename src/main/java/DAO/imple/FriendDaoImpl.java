package DAO.imple;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import util.HibernateUtil;

import DAO.FriendDao;
import model.db.Friend;

/**
 * FriendDaoImpl
 * @author ZhouTQ
 * @category DAO.imple
 * @version
 * 		0 FriendDaoImpl with Create,Retrieve,Delete
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
		session.beginTransaction();
		Friend friend=new Friend(myName,frName);
		session.save(friend);
		session.getTransaction().commit();
	};
    
	/**
	 * 查看好友列表
	 * @param myName 当前用户用户名
	 * @return List<Friend> Friend类型列表（包含当前用户信息）
	 */
	public List<Friend> FindFriend(String myName)
	{
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String hql = "from Friend as friend where friend.myName=? ";  
		Query query=session.createQuery(hql);
		query.setString(0, myName);
		List friendList = query.list();
		session.getTransaction().commit();
		if (friendList != null && friendList.size() >= 1) 
		{
			return (List<Friend>) friendList;
		}
		else 
		{
			return null;
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
		session.beginTransaction();
		String hql = "from Friend as friend where friend.myName=? and friend.frName=? ";  
		Query query=session.createQuery(hql);
		query.setString(0, myName).setString(1, frName);
		List friendList = query.list(); 

		if (friendList != null && friendList.size() >= 1) 
		{
			session.delete(friendList.get(0));	
		}
		else 
		{
			return;
		}
		session.getTransaction().commit();
		
	};
}

	