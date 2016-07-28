package DAO.imple;

import DAO.FriendRequestDao;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

import java.util.List;

/**
 * Xu Zezi
 * @author Xu Zezi
 * @since 2016/7/13.
 * @version
 *      0 Basic Api implemented
 */
public class FriendRequestDaoImpl implements FriendRequestDao
{
	@Override
	public List<model.db.FriendRequest> FindRequestByApplyer(String applyer)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("from FriendRequest where applyer=?").setString(0, applyer);
		List<model.db.FriendRequest> frs = query.list();
		session.getTransaction().commit();
		return frs;
	}

	@Override
	public List<model.db.FriendRequest> FindRequestByApplyee(String applyee)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("from FriendRequest where applyee=?").setString(0, applyee);
		List<model.db.FriendRequest> frs = query.list();
		session.getTransaction().commit();
		return frs;
	}

	@Override
	public boolean createNewRequest(String applyer, String applyee)
	{
		try
		{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			model.db.FriendRequest fr = new model.db.FriendRequest();
			fr.setApplyer(applyer);
			fr.setApplyee(applyee);
			fr.setStatus('-');
			session.save(fr);
			session.getTransaction().commit();
			return true;
		}catch (Exception e)
		{
			return false;
		}
	}

	@Override
	public char updateStatus(String applyer, String applyee, char newStatus)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("from FriendRequest where applyer=? and applyee=?")
				.setString(0, applyer).setString(1, applyee);
		model.db.FriendRequest fr = (model.db.FriendRequest)query.uniqueResult();
		if(fr == null)
		{
			session.getTransaction().commit();
			return 0;
		}
		char oldStatus = fr.getStatus();
		fr.setStatus(newStatus);
		session.update(fr);
		session.getTransaction().commit();
		return oldStatus;
	}

	@Override
	public void deleteRequest(List<model.db.FriendRequest> tars)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete(tars);
		session.getTransaction().commit();
	}
}
