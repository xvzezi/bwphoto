package DAO.imple;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import DAO.ImageDao;
import model.db.Image;
import util.HibernateUtil;

/**
 * ImageDaoImpl
 * @author ZhouTQ
 * @category DAO.imple
 * @version
 * 		0 ImageDaoImpl with CRUD
 * @since 2016/7/5
 * @Description
 *   first version
 *   support Retrieving through id
 */
public class ImageDaoImpl implements ImageDao
{
	
	/**
	 * 添加图片
	 * @param image
	 * @return void
	 */
	  public void saveObject(Image image)
	  {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.save(image);
			session.getTransaction().commit();
	  };
	  
      /**
	   * 删除图片
	   * @param image 
	   * @return void
	   */
	  public void deleteImage(Image image)
	  {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.delete(image);
			session.getTransaction().commit();
	  };
	  
      /**
	   * 根据id查找图片
	   * @param  id
	   * @return void
	   */
	  public Image FindImageById(int id)
	  {
		    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			String hql = "from Image as image where image.id=? ";  
			Query query=session.createQuery(hql);
			query.setInteger(0, id);
			List imageList = query.list();  
			session.getTransaction().commit();
			if (imageList != null && imageList.size() >= 1) 
			{
				return (Image) imageList.get(0);
			} else 
			{
				return null;
			}
	  };
	  
      /**
	   * 修改图片
	   * @param image 
	   * @return void
	   */
	  public void updateImage(Image image) 
	  {
		  Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		  session.beginTransaction();
		  session.update(image);
		  session.getTransaction().commit();
	  };
}

	