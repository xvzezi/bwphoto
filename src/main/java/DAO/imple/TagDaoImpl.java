package DAO.imple;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import DAO.TagDao;
import model.db.Friend;
import model.db.Tag;
import util.HibernateUtil;

public class TagDaoImpl implements TagDao {

	@Override
	public List<Tag> FindTagByName(String name) {
		// TODO Auto-generated method stub
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String hql = "from Tag as tag where tag.name=? ";  
		Query query=session.createQuery(hql);
		query.setString(0, name);
		List tagList = query.list();  
		if (tagList != null && tagList.size() >= 1) 
		{
			
			return (List<Tag>) tagList;
		} else 
		{
			return null;
		}
	}

	public void updateTag(Tag tag) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update(tag);
		session.getTransaction().commit();
	}


	public void deleteTag(Tag tag) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete(tag);
		session.getTransaction().commit();
		
	}

	
	public void addTag(Tag tag) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(tag);
		session.getTransaction().commit();
		
	}

}
