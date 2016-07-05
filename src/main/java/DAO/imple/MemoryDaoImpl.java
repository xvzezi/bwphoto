package DAO.imple;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import model.db.Memory;
import util.HibernateUtil;

public class MemoryDaoImpl 
{
	
	public void updateMemory(Memory memory)
	  {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.update(memory);
			session.getTransaction().commit();
	  };
    public void saveObject(Memory memory)
    {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(memory);
		session.getTransaction().commit();
    };
    public void deleteMemory(Memory memory)
    {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete(memory);
		session.getTransaction().commit();
    };
    public Memory FindMemoryById(int id)
    {
    	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String hql = "from Memory as memory where memory.id=? ";  
		Query query=session.createQuery(hql);
		query.setInteger(0, id);
		List memoryList = query.list();  
		if (memoryList != null && memoryList.size() >= 1) 
		{
			return (Memory) memoryList.get(0);
		} 
		else
		{
			return null;
		}
    };
    public Memory FindMemoryByContent(String content)
    {
    	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String hql = "from Memory as memory where memory.content like '%"+content+"%' ";  
		Query query=session.createQuery(hql);
		query.setString(0, content);
		
		List memoryList = query.list();  
		if (memoryList != null && memoryList.size() >= 1) 
		{
			return (Memory) memoryList.get(0);
		}                                    
		else 
		{
			return null;
		}
    };
}
