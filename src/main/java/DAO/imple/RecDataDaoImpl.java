package DAO.imple;

import DAO.RecDataDao;
import model.db.RecData;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;
import util.Log;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * DAO of RecDate
 * @author Xu Zezi
 * @since 2016/8/6
 * @version
 *      0   Basic Impl.
 */
public class RecDataDaoImpl implements RecDataDao
{

	/**
	 * 获取recdata
	 *
	 * @param username
	 * @return 具体数据
	 * 通过用户名获取用户数据
	 */
	@Override
	public RecData getRecData(String username)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try
		{
			session.beginTransaction();
			Query query = session.createQuery("from RecData where name=?");
			RecData recData = (RecData)query.setString(0, username).uniqueResult();
			return recData;
		}catch (Exception e)
		{
			Log.log.log("Error In RecData DAO:").log(e.getMessage()).log();
			return null;
		}finally
		{
			session.getTransaction().commit();
		}
	}

	/**
	 * 获取一系列数据
	 *
	 * @param names
	 * @return 具体数据
	 * 会在用好友搞得时候用到
	 */
	@Override
	public List<RecData> getRecDataOfNames(List<String> names)
	{
		if(names == null)
		{
			return null;
		}

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try
		{
			session.beginTransaction();
			Query query = session.createQuery("from RecData where name in (:nameList)")
					.setParameterList("nameList", names);
			List<RecData> recDatas = query.list();
			return recDatas;
		}catch (Exception e)
		{
			Log.log.log("Error In RecDataDAO getRecDataOfNames:").log(e.getMessage()).log();
			return null;
		}finally
		{
			session.getTransaction().commit();
		}
	}

	/**
	 * 获取相关的名字
	 *
	 * @param username
	 * @return 一系列名字
	 * 通过某个用户的名字获取数据值匹配的用户
	 */
	@Override
	public List<String> getRelateNames(String username)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try
		{
			session.beginTransaction();
			// 首先，获取username对应的指标
			Query query = session.createQuery("from RecData where name=?");
			RecData recData = (RecData)query.setString(0, username).uniqueResult();
			double mood = recData.getEmotion() * 0.9 + recData.getF_emotion() * 0.1;
			double type = recData.getF_type() * 0.9 + recData.getF_type() * 0.1;
			// 通过指标来查询一模一样的人，以最后timestamp活跃时间排序
			query = session.createQuery("select name from RecData where emotion=? and type>? order by checktime desc")
					.setDouble(0, mood).setDouble(1, type).setFetchSize(10);
			List<String> names = query.list();
			return names;
		}catch (Exception e)
		{
			Log.log.log("Error In RecDataDAO getRelateNames:").log(e.getMessage()).log();
			return null;
		}finally
		{
			session.getTransaction().commit();
		}
	}

	/**
	 * 传统的增删改查
	 *
	 * @param username 根据username为唯一识别标识
	 */
	@Override
	public void createRecData(String username)
	{
		RecData recData = new RecData();
		recData.setName(username);
		recData.setEmotion(0);
		recData.setType(0);
		recData.setF_emotion(0);
		recData.setF_type(0);
		recData.setChecktime(new Timestamp(new Date().getTime()));
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try
		{
			session.beginTransaction();
			session.save(recData);
			return;
		}catch (Exception e)
		{
			Log.log.log("Error In RecDate creation:").log(e.getMessage()).log();
			return;
		}finally
		{
			session.getTransaction().commit();
		}
	}

	@Override
	public void deleteRecData(String username)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try
		{
			session.beginTransaction();
			Query query = session.createQuery("delete from RecData where name=?").setString(0, username);
			query.executeUpdate();
			return;
		}catch (Exception e)
		{
			Log.log.log("Error In RecDataDAO deletion:").log(e.getMessage()).log();
			return;
		}finally
		{
			session.getTransaction().commit();
		}
	}

	@Override
	public void updateRecData(RecData recData)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try
		{
			session.beginTransaction();
			session.update(recData);
			return;
		}catch (Exception e)
		{
			Log.log.log("Error In RecDataDAO update:").log(e.getMessage()).log();
			return;
		}finally
		{
			session.getTransaction().commit();
		}

	}
}
