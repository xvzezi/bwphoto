package DAO.imple;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;


import DAO.MarkDao;
import model.db.Mark;
import util.HibernateUtil;


public class MarkDaoImpl implements MarkDao
{
	  public void updateMark(Mark mark)
	  {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.update(mark);
			session.getTransaction().commit();
	  };
	  public void saveObject(Mark mark)
	  {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.save(mark);
			session.getTransaction().commit();
	  };
	  public void deleteMark(Mark mark)
	  {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.delete(mark);
			session.getTransaction().commit();
	  };
	  public Mark FindMarkById(int id)
	  {
			// TODO Auto-generated method stub
		    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			String hql = "from Mark as mark where mark.id=? ";  
			Query query=session.createQuery(hql);
			query.setInteger(0, id);
			List markList = query.list();  
			if (markList != null && markList.size() >= 1) 
			{
				return (Mark) markList.get(0);
			} else 
			{
				return null;
			}
	  };
}
