package DAO.imple;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;


import DAO.MarkDao;
import model.db.Mark;
import util.HibernateUtil;
import util.Log;

/**
 * MarkDaoImpl
 * @author ZhouTQ
 * @category DAO.imple
 * @version
 * 		0 MarkDaoImpl with CRUD
 * @since 2016/7/5
 * @Description
 *   first version
 *   support Retrieving through id,itemId
 */
public class MarkDaoImpl implements MarkDao
{
	/**
	 * 创建
	 *
	 * @param content
	 * @param item_id
	 * @param target
	 * @param username
	 */
	@Override
	public int saveObject(String content, Integer item_id, Integer target, String username)
	{
		// prepare the basic message
		Mark mark = new Mark();
		mark.setContent(content);
		mark.setItemId(item_id);
		mark.setParent(target);
		mark.setThisname(username);
		Date date = new Date();
		mark.setTime(new Timestamp(date.getTime()));

		// do the storage things
		// the constraints is auto kept by the database
		try
		{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.save(mark);
			session.getTransaction().commit();
			return mark.getId();
		}
		catch (Exception e)
		{
			Log.log.log("Error In MarkDao SaveObject:").log(e.getMessage()).log();
			return -1;
		}
	}

	/**
	 * 删除
	 *
	 * @param item_id
	 */
	@Override
	public void deleteMark(Integer item_id)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("delete from Mark where itemId=?").setInteger(0, item_id);
		query.executeUpdate();
		session.getTransaction().commit();
	}

	@Override
	public void deleteMark(Integer id, String username)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("delete from Mark where id=? and thisname=?")
				.setInteger(0, id).setString(1, username);
		query.executeUpdate();
		session.getTransaction().commit();
	}

	/**
	 * 获取
	 *
	 * @param id
	 * @return
	 */
	@Override
	public Mark FindMarkById(Integer id)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("from Mark where id=?").setInteger(0, id);
		Mark mark = (Mark)query.uniqueResult();
		session.getTransaction().commit();
		return mark;
	}

	@Override
	public List<Mark> FindMarkByItemId(Integer item_id, Timestamp timestamp, Integer limit)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("from Mark where itemId=? and time < ? order by time")
				.setInteger(0, item_id).setTimestamp(1, new Date(timestamp.getTime()))
				.setFetchSize(limit);
		List<Mark> marks = (List<Mark>)query.list();
		session.getTransaction().commit();
		return marks;
	}
}
