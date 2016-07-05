package DAO.imple;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import model.db.Itemtag;
import DAO.ItemtagDao;
import util.HibernateUtil;

public class ItemtagDaoImpl implements ItemtagDao{

	public List<Itemtag> FindItemByTag(String tagName) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String hql = "from Itemtag as itemtag where itemtag.tagName=? ";  
		Query query=session.createQuery(hql);
		query.setString(0, tagName);
		List itemtagList = query.list();  
		if (itemtagList != null && itemtagList.size() >= 1) 
		{
			return (List<Itemtag>) itemtagList;
		} else 
		{
			return null;
		}
	}

}
