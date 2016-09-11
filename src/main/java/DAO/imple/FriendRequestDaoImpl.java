package DAO.imple;

import DAO.FriendRequestDao;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;
import util.Log;

import java.util.List;

/**
 * Xu Zezi
 * @author Xu Zezi
 * @since 2016/7/13.
 * @version
 *      0   Basic Api implemented
 *      0.8 Transaction Management
 */
public class FriendRequestDaoImpl implements FriendRequestDao
{
	@Override
	public List<model.db.FriendRequest> FindRequestByApplyer(String applyer)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try
		{
			session.beginTransaction();
			Query query = session.createQuery("from FriendRequest where applyer=?").setString(0, applyer);
			List<model.db.FriendRequest> frs = query.list();
			return frs;
		}catch (Exception e)
		{
			Log.log.log("Error In FriendREDAO findByApplyer:").log(e.getMessage()).log();
			return null;
		}finally
		{
			session.getTransaction().commit();
		}
	}

	@Override
	public List<model.db.FriendRequest> FindRequestByApplyee(String applyee)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try
		{
			session.beginTransaction();
			Query query = session.createQuery("from FriendRequest where applyee=?").setString(0, applyee);
			List<model.db.FriendRequest> frs = query.list();
			return frs;
		}catch (Exception e)
		{
			Log.log.log("Error In FriendREDAO FindByApplyee:").log(e.getMessage()).log();
			return null;
		}finally
		{
			session.getTransaction().commit();
		}
	}

	@Override
	public boolean createNewRequest(String applyer, String applyee)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try
		{
			session.beginTransaction();
			model.db.FriendRequest fr = new model.db.FriendRequest();
			fr.setApplyer(applyer);
			fr.setApplyee(applyee);
			fr.setStatus(0);
			session.save(fr);
			return true;
		}catch (Exception e)
		{
			Log.log.log("Error In FriendREDAO creation:").log(e.getMessage()).log();
			return false;
		}finally
		{
			session.getTransaction().commit();
		}
	}

	@Override
	public int updateStatus(String applyer, String applyee, int newStatus)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try
		{
			session.beginTransaction();
			Query query = session.createQuery("from FriendRequest where applyer=? and applyee=?")
					.setString(0, applyer).setString(1, applyee);
			model.db.FriendRequest fr = (model.db.FriendRequest) query.uniqueResult();
			if (fr == null)
			{

				return 0;
			}
			int oldStatus = fr.getStatus();
			fr.setStatus(newStatus);
			session.update(fr);
			return oldStatus;
		}catch (Exception e)
		{
			Log.log.log("Error In FriendREDAO update:").log(e.getMessage()).log();
			return 0;
		}finally
		{
			session.getTransaction().commit();
		}
	}

	@Override
	public void deleteRequest(List<model.db.FriendRequest> tars)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try
		{
			session.beginTransaction();
			session.delete(tars);
		}catch (Exception e)
		{
			Log.log.log("Error In FriendREDAO deletion:").log(e.getMessage()).log();
		}finally
		{
			session.getTransaction().commit();
		}
	}
}
