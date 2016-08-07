package DAO.imple;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import model.db.Itemtag;
import DAO.ItemtagDao;
import util.HibernateUtil;
import util.Log;

/**
 * ItemtagDaoImpl
 * @author ZhouTQ
 * @category DAO.imple
 * @version
 * 		0 ItemtagDaoImpl with find
 * 	    1 add find by item id           2016/7/12
 * 	    2 add create an association     2016/7/12
 * 	    2.8 transaction management      2016/8/7
 * @since 2016/7/5
 * @Description
 *   first version
 *   support Retrieving through id
 */
public class ItemtagDaoImpl implements ItemtagDao{

	/**
	 * 根据tag查找
	 * @param tagName
	 * @return List<Itemtag>
	 */
	public List<Itemtag> FindItemByTag(String tagName)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try
		{
			session.beginTransaction();
			String hql = "from Itemtag as itemtag where itemtag.tagName=? ";
			Query query = session.createQuery(hql);
			query.setString(0, tagName);
			List itemtagList = query.list();
			if (itemtagList != null && itemtagList.size() >= 1)
			{
				return (List<Itemtag>) itemtagList;
			} else
			{
				return null;
			}
		}catch (Exception e)
		{
			Log.log.log("Error In ItemTagDAO findByTag:").log(e.getMessage()).log();
			return null;
		}finally
		{
			session.getTransaction().commit();
		}
	}

	/**
	 * 根据item id 添加
	 * @param item_id
	 * @return
	 */
	public List<Itemtag> FindItemById(int item_id)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try
		{
			session.beginTransaction();
			String hql = "from Itemtag where itemId=?";
			Query query = session.createQuery(hql);
			query.setInteger(0, item_id);
			List itemtagList = query.list();
			if (itemtagList != null && itemtagList.size() >= 1)
			{
				return (List<Itemtag>) itemtagList;
			} else
			{
				return null;
			}
		}catch (Exception e)
		{
			Log.log.log("Error In ItemTagDAO findById:").log(e.getMessage()).log();
			return null;
		}finally
		{
			session.getTransaction().commit();
		}
	}

	/**
	 * 添加新的标签
	 * @param item_id
	 * @param tagname
	 * @return
	 */
	@Override
	public boolean saveObject(int item_id, String tagname)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try
		{
			session.beginTransaction();
			Itemtag it = new Itemtag();
			it.setItemId(item_id);
			it.setTagName(tagname);
			session.save(it);
		}catch (Exception e)
		{
			return false;
		}finally
		{
			session.getTransaction().commit();
		}
		return true;
	}

}
