package DAO.imple;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import DAO.MusicDao;
import model.db.Music;
import util.HibernateUtil;

/**
 * MusicDaoImpl
 * @Deprecated
 * @author ZhouTQ
 * @category DAO.imple
 * @version
 * 		0 MusicDaoImpl with CRUD
 * @since 2016/7/5
 * @Description
 *   first version
 *   support Retrieving through id,author,name of music,name&author
 */
public class MusicDaoImpl implements MusicDao
{
	/**
	 * 添加音乐
	 * @param music
	 * @return void
	 */
	  public void saveObject(Music music)
	  {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.save(music);
			session.getTransaction().commit();
	  }
	  
	  /**
	   * 删除音乐
	   * @param music
	   * @return void
	   */
	  public void deleteMusic(Music music)
	  {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.delete(music);
			session.getTransaction().commit();
	  }
	  
	  /**
	   * 根据音乐名查找音乐
	   * @param name 
	   * @return List<Music>
	   */
	  public List<Music> FindMusicByName(String name)
	  {
		    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			String hql = "from Music as music where music.name like '%'||?||'%' ";   
			Query query=session.createQuery(hql);
			query.setString(0, name);
			List musicList = query.list();  
			session.getTransaction().commit();
			if (musicList != null && musicList.size() >= 1) 
			{
				return (List<Music>) musicList;
			} else 
			{
				return null;
			}
	  }
	  
	  /**
	   * 修改音乐信息
	   * @param music
	   * @return void
	   */
	  public void updateMusic(Music music) 
	  {
		    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.update(music);
			session.getTransaction().commit();		
	  }

	  /**
	   * 根据作者名查找音乐
	   * @param  author
	   * @return List<Music>
	   */	
	  public List<Music> FindMusicByAuthor(String author)
	  {
		  Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		  session.beginTransaction();
		  String hql = "from Music as music where music.author like '%'||?||'%' ";   
		  Query query=session.createQuery(hql);
		  query.setString(0, author);
		  List musicList = query.list();
		  session.getTransaction().commit();
	      if (musicList != null && musicList.size() >= 1) 
		  {
			  return (List<Music>) musicList;
		  }
	      else 
		  {
			  return null;
		  }
	  }
	
	  /**
	   * 根据音乐名和作者名查找音乐
	   * @param name,author 
	   * @return List<Music>
	   */
	  public List<Music> FindMusicByNameAndAuthor(String name, String author) 
	  {
		    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			String hql = "from Music as music where music.name like '%'||?||'%'and music.author like '%'||?||'%' ";   
			Query query=session.createQuery(hql);
			query.setString(0, name).setString(1, author);
			List musicList = query.list();  
			session.getTransaction().commit();
			if (musicList != null && musicList.size() >= 1) 
		    {
				return (List<Music>) musicList;
			} 
			else 
			{
				return null;
			}
	}
}

	