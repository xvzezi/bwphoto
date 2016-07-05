package DAO.imple;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import DAO.ImageDao;
import model.db.Image;
import util.HibernateUtil;


public class ImageDaoImpl implements ImageDao
{

	  public void saveObject(Image image)
	  {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.save(image);
			session.getTransaction().commit();
	  };
	  public void deleteImage(Image image)
	  {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.delete(image);
			session.getTransaction().commit();
	  };
	  public Image FindImageById(int id)
	  {
			// TODO Auto-generated method stub
		    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			String hql = "from Image as image where image.id=? ";  
			Query query=session.createQuery(hql);
			query.setInteger(0, id);
			List imageList = query.list();  
			if (imageList != null && imageList.size() >= 1) 
			{
				return (Image) imageList.get(0);
			} else 
			{
				return null;
			}
	  };
	  
		public void updateImage(Image image) 
		{
			// TODO Auto-generated method stub
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.update(image);
			session.getTransaction().commit();
			
		};

}

	