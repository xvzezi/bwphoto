package DAO.imple;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import DAO.TagDao;
import model.db.Friend;
import model.db.Tag;
import util.HibernateUtil;


/**
 * TagDaoImpl
 * @author ZhouTQ
 * @category DAO.imple
 * @version
 * 		0 TagDaoImpl with CRUD
 * @since 2016/7/5
 * @Description
 *   first version
 *   support Retrieving through id,author,name of music,name&author
 */
public class TagDaoImpl implements TagDao {
    
	/**
	 * 根据tag的name查找tag
	 * @param tag
	 * @return List<Tag>
	 */
	public List<Tag> FindTagByName(String name)
	{
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String hql = "from Tag as tag where tag.name=? ";  
		Query query=session.createQuery(hql);
		query.setString(0, name);
		List tagList = query.list();
		session.getTransaction().commit();
		if (tagList != null && tagList.size() >= 1) 
		{
			return (List<Tag>) tagList;
		}
		else 
		{
			return null;
		}
	}
    
	/**
	 * 修改tag
	 * @param tag
	 * @return List<Tag>
	 */
	public void updateTag(Tag tag)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update(tag);
		session.getTransaction().commit();
	}

	/**
	 * 删除tag
	 * @param tag
	 * @return void
	 */
	public void deleteTag(Tag tag)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete(tag);
		session.getTransaction().commit();
		
	}

	/**
	 * 增加tag
	 * @param tag
	 * @return void
	 */
	public void addTag(Tag tag)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(tag);
		session.getTransaction().commit();
		
	}

}
