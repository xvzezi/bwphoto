package DAO.imple;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import DAO.MusicDao;
import model.db.Music;
import util.HibernateUtil;


public class MusicDaoImpl implements MusicDao
{

	  public void saveObject(Music music)
	  {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.save(music);
			session.getTransaction().commit();
	  }
	  public void deleteMusic(Music music)
	  {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.delete(music);
			session.getTransaction().commit();
	  }
	  public List<Music> FindMusicByName(String name)
	  {
		// TODO Auto-generated method stub
		    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			String hql = "from Music as music where music.name like '%"+name+"%' ";   
			Query query=session.createQuery(hql);
			query.setString(0, name);
			List musicList = query.list();  
			if (musicList != null && musicList.size() >= 1) 
			{
				return (List<Music>) musicList;
			} else 
			{
				return null;
			}
	  }
	  
		public void updateMusic(Music music) 
		{
			// TODO Auto-generated method stub
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.update(music);
			session.getTransaction().commit();
			
		}

		
		public List<Music> FindMusicByAuthor(String author) {
			// TODO Auto-generated method stub
		    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			String hql = "from Music as music where music.author like '%"+author+"%' ";   
			Query query=session.createQuery(hql);
			query.setString(0, author);
			List musicList = query.list();  
			if (musicList != null && musicList.size() >= 1) 
			{
				return (List<Music>) musicList;
			} else 
			{
				return null;
			}
		}
	
		
		public List<Music> FindMusicByNameAndAuthor(String name, String author) {
			// TODO Auto-generated method stub
		    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			String hql = "from Music as music where music.name like '%"+name+"%'and music.author like '%"+author+"%' ";   
			Query query=session.createQuery(hql);
			query.setString(0, name).setString(1, author);
			List musicList = query.list();  
			if (musicList != null && musicList.size() >= 1) 
			{
				return (List<Music>) musicList;
			} else 
			{
				return null;
			}
		}



}

	