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

/**
 * @author Zhou TQ
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
		  session.beginTransaction();
		  String hql = "from User as user where user.name=? and user.pwd=?";
		  Query query=session.createQuery(hql);
		  query.setString(0, name).setString(1, password);
		  User user = (User) query.uniqueResult();
		  session.getTransaction().commit();
		  return user;
    };
    
	/**
	 * 修改用户信息
	 * @param user
	 * @return void
	 */
    public void updateUser(User user)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update(user);
		session.getTransaction().commit();
    }
    
	/**
	 * 添加用户
	 * @param user
	 * @return void
	 */
    public void saveObject(User user)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(user);
		session.getTransaction().commit();
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
		session.beginTransaction();
		String hql = "from User as user where user.name=? ";
		Query query=session.createQuery(hql);
		query.setString(0, name);
		List userList = query.list();
		session.getTransaction().commit();
		if (userList != null && userList.size() >= 1) {
			return (User) userList.get(0);
		} else {
			return null;
		}
	}
	/**
	 * 查找所有管理员
	 * @param null
	 * @return List<User>
	 */
	public List<User> FindAdmin() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String hql = "from User as user where role=? ";
		Query query=session.createQuery(hql);
		query.setInteger(0, 1);
		List userList = query.list();
		session.getTransaction().commit();
		if (userList != null && userList.size() >= 1) {
			return (List<User>) userList;
		} else {
			return null;
		}
	}

	/**
	 * 查找所有用户
	 * @param null
	 * @return List《User>
	 */
	public List<User> FindAll() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String hql = "select user from User as user ";
		Query query=session.createQuery(hql);
		List userList = query.list();
		session.getTransaction().commit();
		if (userList != null && userList.size() >= 1) {
			return (List<User>) userList;
		} else {
			return null;
		}
	}

	/**
	 * 删除用户
	 * @param user
	 * @return void
	 */
	@Override
	public void deleteUser(User user) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete(user);
		session.getTransaction().commit();

	}

} 
 