package DAO.imple;

import java.util.List;


import DAO.UserDao;
import model.SimpleUser;
import model.db.User;
import util.HibernateUtil;

import org.hibernate.Query;
import org.hibernate.Session; 
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import util.Log;

/**
 * DAO of User
 * @author Zhou TQ
 * @since 2016/7/5
 * @version
 *      0   basic impl.
 *      0.8 transaction management
 */
public class UserDaoImpl implements UserDao{
	/**
	 * 验证用户
	 * @param user
	 * @return User
	 */
	 public User UserValid(SimpleUser user){
		 return FindUser(user.getUsername(),user.getPwd());
	 };
	
	/**
	 * 根据user的name和passwd查找用户
	 * @param name
	 * @param password
	 * @return User
	 */
    public User FindUser(String name,String password)
    {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    try
	    {
		    session.beginTransaction();
		    String hql = "from User as user where user.name=? and user.pwd=?";
		    Query query = session.createQuery(hql);
		    query.setString(0, name).setString(1, password);
		    User user = (User) query.uniqueResult();
		    return user;
	    }catch (Exception e)
	    {
		    Log.log.log("Error In UserDAO find:").log(e.getMessage()).log();
		    return null;
	    }finally
	    {
		    session.getTransaction().commit();
	    }
	};
    
	/**
	 * 修改用户信息
	 * @param user
	 * @return void
	 */
    public void updateUser(User user)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try
		{
			session.beginTransaction();
			session.update(user);
		}catch (Exception e)
		{
			Log.log.log("Error In UserDAO udpate:").log(e.getMessage()).log();
		}finally
		{
			session.getTransaction().commit();
		}
	}
    
	/**
	 * 添加用户
	 * @param user
	 * @return void
	 */
    public void saveObject(User user)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try
		{
			session.beginTransaction();
			session.save(user);
		}catch (Exception e)
		{
			Log.log.log("Error In UserDAO save:").log(e.getMessage()).log();
		}finally
		{
			session.getTransaction().commit();
		}
	}
    
	/**
	 * 根据user的name查找user
	 * @param name
	 * @return User
	 */
	@Override
	public User FindUserByName(String name)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try
		{
			session.beginTransaction();
			String hql = "from User as user where user.name=? ";
			Query query = session.createQuery(hql);
			query.setString(0, name);
			List userList = query.list();
			if (userList != null && userList.size() >= 1)
			{
				return (User) userList.get(0);
			} else
			{
				return null;
			}
		}catch (Exception e)
		{
			Log.log.log("Error In UserDAO findByName:").log(e.getMessage()).log();
			return null;
		}finally
		{
			session.getTransaction().commit();
		}
	}
	/**
	 * 查找所有管理员
	 * @return List<User>
	 */
	public List<User> FindAdmin()
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try
		{
			session.beginTransaction();
			String hql = "from User as user where role=? ";
			Query query = session.createQuery(hql);
			query.setInteger(0, 1);
			List userList = query.list();
			if (userList != null && userList.size() >= 1)
			{
				return (List<User>) userList;
			} else
			{
				return null;
			}
		}catch (Exception e)
		{
			Log.log.log("Error In UserDAO findAdmin:").log(e.getMessage()).log();
			return null;
		}finally
		{
			session.getTransaction().commit();
		}
	}

	/**
	 * 查找所有用户
	 * @return List《User>
	 */
	public List<User> FindAll()
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try
		{
			session.beginTransaction();
			String hql = "select user from User as user ";
			Query query = session.createQuery(hql);
			List userList = query.list();
			if (userList != null && userList.size() >= 1)
			{
				return (List<User>) userList;
			} else
			{
				return null;
			}
		}catch (Exception e)
		{
			Log.log.log("Error In UserDAO findALl:").log(e.getMessage()).log();
			return null;
		}finally
		{
			session.getTransaction().commit();
		}
	}

	/**
	 * 删除用户
	 * @param user
	 * @return void
	 */
	@Override
	public void deleteUser(User user)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try
		{
			session.beginTransaction();
			session.delete(user);
		}catch (Exception e)
		{
			Log.log.log("Error In UserDAO deletion:").log(e.getMessage()).log();
		}finally
		{
			session.getTransaction().commit();
		}

	}

} 
 