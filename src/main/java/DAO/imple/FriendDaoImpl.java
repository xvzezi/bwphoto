package DAO.imple;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import util.HibernateUtil;

import DAO.FriendDao;
import model.db.Friend;


public class FriendDaoImpl implements FriendDao
{

	  public void addFriend(String myName,String frName)
	  {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			Friend friend=new Friend(myName,frName);
			session.save(friend);
			session.getTransaction().commit();
	  };

	  public List<Friend> FindFriend(String myName)
	  {
			// TODO Auto-generated method stub
		    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			String hql = "from Friend as friend where friend.myName=? ";  
			Query query=session.createQuery(hql);
			query.setString(0, myName);
			List friendList = query.list();  
			if (friendList != null && friendList.size() >= 1) 
			{
				
				return (List<Friend>) friendList;
			} else 
			{
				return null;
			}
	  };
	@Override
	public void deleteFriend(String frName) {
		// TODO Auto-generated method stub
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String hql = "from Friend as friend where friend.frName=? ";  
		Query query=session.createQuery(hql);
		query.setString(0, frName);
		List friendList = query.list();  
		if (friendList != null && friendList.size() >= 1) 
		{

			session.delete(friendList.get(0));
			session.getTransaction().commit();
			
		} else 
		{
			return;
		}
		
	};
}

	