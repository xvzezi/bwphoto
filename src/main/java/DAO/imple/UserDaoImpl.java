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


public class UserDaoImpl implements UserDao{
	 private SessionFactory sessionFactory;
	 
	 public UserDaoImpl()
	 {
		 this.sessionFactory = HibernateUtil.getSessionFactory();
	 }
	
	 public User UserValid(SimpleUser user){
		 return FindUser(user.getUsername(),user.getPwd());
	 };
	
    public User FindUser(String name,String password){
		String hql = "from User as user where user.name=? and user.pwd=?";
		Session session = sessionFactory.openSession();
		Query query=session.createQuery(hql);
		query.setString(0, name).setString(1, password);
		User user = (User) query.uniqueResult();
		return user;

    };
    public void updateUser(User user){
    	sessionFactory.getCurrentSession().update(user);	
    };
    public void saveObject(User user){
    	sessionFactory.getCurrentSession().save(user); 
    }
	@Override
	public User FindUserByName(String name) {
		// TODO Auto-generated method stub
    	Configuration config = new Configuration().configure();
		String hql = "from User as user where user.name=? ";  
		Session session = sessionFactory.openSession();
		Query query=session.createQuery(hql);
		List userList = query.list();  
		if (userList != null && userList.size() >= 1) {
			return (User) userList.get(0);
		} else {
			return null;
		}
	};
} 
 