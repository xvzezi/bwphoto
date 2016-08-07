package DAO.imple;

import java.util.List;

import DAO.MemoryDao;
import org.hibernate.Query;
import org.hibernate.Session;

import model.db.Memory;
import util.HibernateUtil;
import util.Log;

/**
 * MemoryDaoImpl
 * @author ZhouTQ
 * @category DAO.imple
 * @version
 * 		0 MemoryDaoImpl with CRUD
 * 	    0.8 transaction management
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
		try
		{
			session.beginTransaction();
			session.update(memory);
		}catch (Exception e)
		{
			Log.log.log("Error In MemDAO update:").log(e.getMessage()).log();
		}finally
		{
			session.getTransaction().commit();
		}
	}
	
	/**
	 * 添加回忆内容
	 * @param memory
	 * @return void
	 */
    public void saveObject(Memory memory)
    {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    try
	    {
		    session.beginTransaction();
		    session.save(memory);
	    }catch (Exception e)
	    {
		    Log.log.log("Error In MemDAO save:").log(e.getMessage()).log();
	    }finally
	    {
		    session.getTransaction().commit();
	    }
	}
    
	/**
	 * 删除回忆内容
	 * @param memory
	 * @return void
	 */
    public void deleteMemory(Memory memory)
    {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    try
	    {
		    session.beginTransaction();
		    session.delete(memory);
	    }catch (Exception e)
	    {
		    Log.log.log("Error In MemDAO deletion:").log(e.getMessage()).log();
	    }finally
	    {
		    session.getTransaction().commit();
	    }
    }
    
	/**
	 * 根据id超找Memory
	 * @param id
	 * @return Memory
	 */
    public Memory FindMemoryById(int id)
    {
    	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    try
	    {
		    session.beginTransaction();
		    String hql = "from Memory as memory where memory.id=? ";
		    Query query = session.createQuery(hql);
		    query.setInteger(0, id);
		    List memoryList = query.list();
		    if (memoryList != null && memoryList.size() >= 1)
		    {
			    return (Memory) memoryList.get(0);
		    } else
		    {
			    return null;
		    }
	    }catch (Exception e)
	    {
		    Log.log.log("Error In MemDAO findById:").log(e.getMessage()).log();
		    return null;
	    }finally
	    {
		    session.getTransaction().commit();
	    }
	}
    
	/**
	 * 根据回忆内容查找回忆(相似查找)
	 * @param content
	 * @return Memory
	 */
    public List<Memory> FindMemoryByContent(String content)
    {
    	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    try
	    {
		    session.beginTransaction();
		    String hql = "from Memory as memory where memory.content like '%'||?||'%' ";
		    Query query = session.createQuery(hql);
		    query.setString(0, content);

		    List memoryList = query.list();
		    if (memoryList != null && memoryList.size() >= 1)
		    {
			    return (List<Memory>) memoryList;
		    } else
		    {
			    return null;
		    }
	    }catch (Exception e)
	    {
		    Log.log.log("Error In MemDAO findByContent:").log(e.getMessage()).log();
		    return null;
	    }finally
	    {
		    session.getTransaction().commit();
	    }
	}

	public List<Memory> getAll()
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try
		{
			session.beginTransaction();
			String hql = "from Memory";
			Query query = session.createQuery(hql);
			List result = query.list();
			return (List<Memory>) result;
		}catch (Exception e)
		{
			Log.log.log("Error In MemDAO getAll:").log(e.getMessage()).log();
			return null;
		}finally
		{
			session.getTransaction().commit();
		}
	}
}
