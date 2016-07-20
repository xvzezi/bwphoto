package DAO.imple;

import java.util.List;

import DAO.MemoryDao;
import org.hibernate.Query;
import org.hibernate.Session;

import model.db.Memory;
import util.HibernateUtil;

/**
 * MemoryDaoImpl
 * @author ZhouTQ
 * @category DAO.imple
 * @version
 * 		0 MemoryDaoImpl with CRUD
 * @since 2016/7/5
 * @Description
 *   first version
 *   support Retrieving through id, content
 */
public class MemoryDaoImpl implements MemoryDao
{
	/**
	 * 修改回忆内容
	 * @param memory
	 * @return void
	 */
	public void updateMemory(Memory memory)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update(memory);
		session.getTransaction().commit();
	}
	
	/**
	 * 添加回忆内容
	 * @param memory
	 * @return void
	 */
    public void saveObject(Memory memory)
    {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(memory);
		session.getTransaction().commit();
    }
    
	/**
	 * 删除回忆内容
	 * @param memory
	 * @return void
	 */
    public void deleteMemory(Memory memory)
    {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete(memory);
		session.getTransaction().commit();
    }
    
	/**
	 * 根据id超找Memory
	 * @param memory
	 * @return Memory
	 */
    public Memory FindMemoryById(int id)
    {
    	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String hql = "from Memory as memory where memory.id=? ";  
		Query query=session.createQuery(hql);
		query.setInteger(0, id);
		List memoryList = query.list(); 
		session.getTransaction().commit();
		if (memoryList != null && memoryList.size() >= 1) 
		{
			return (Memory) memoryList.get(0);
		} 
		else
		{
			return null;
		}
    }
    
	/**
	 * 根据回忆内容查找回忆(相似查找)
	 * @param memory
	 * @return Memory
	 */
    public List<Memory> FindMemoryByContent(String content)
    {
    	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String hql = "from Memory as memory where memory.content like '%'||?||'%' ";  
		Query query=session.createQuery(hql);
		query.setString(0, content);
		
		List memoryList = query.list();  
		if (memoryList != null && memoryList.size() >= 1) 
		{
			return (List<Memory>) memoryList;
		}                                    
		else 
		{
			return null;
		}
    }

	public List<Memory> getAll()
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String hql = "from Memory";
		Query query = session.createQuery(hql);
		List result = query.list();
		return (List<Memory>)result;
	}
}
