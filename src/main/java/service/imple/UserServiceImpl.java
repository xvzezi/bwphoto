package service.imple;


import java.util.List;

import javax.servlet.http.HttpSession;

import DAO.UserDao;
import model.db.User;
import service.UserService;
import util.Log;

public class UserServiceImpl implements UserService {
	
    private UserDao dao;
    public void setDao(UserDao dao)
    {
        this.dao = dao;
    }
	 public List<User> getAdmin()
	 {

	        List<User> userlist;
	        try
	        {
	            userlist = dao.FindAdmin();
	        }catch(NumberFormatException nfe)
	        {
	            Log.log.log("Integer Parse Error In Resource Service").log(nfe.getMessage()).log();
	            return null;
	        }

	  
	        return userlist;
	 }
	 
	 public List<User> getGeneral()
	 {

	        List<User> userlist;
	        try
	        {
	            userlist = dao.FindAll();
	        }catch(NumberFormatException nfe)
	        {
	            Log.log.log("Integer Parse Error In Resource Service").log(nfe.getMessage()).log();
	            return null;
	        }

	  
	        return userlist;
	 }
	 
		public String updateUser(User user, HttpSession session) {
			if(dao.FindUser(user.getName(), user.getPwd())!=null){
				//User u=dao.FindUser(user.getName(), user.getPwd());
				dao.updateUser(user);
				return"SUCCESS";
			}
			else if(dao.FindUser(user.getName(), user.getPwd())==null){
		    	   System.out.println(user.getPwd());
                   dao.saveObject(user);
		           return "SUCCESS";
				
			}
			else{
				return"Fail";
				
			}
		}


		public String deleteUser(User user, HttpSession session) {
			User u=dao.FindUser(user.getName(), user.getPwd());
		       if(u!=null){
		           dao.deleteUser(user);
		           return "SUCCESS";
		       }
		       else
		       {
		    	   return "User Not Found";
		    	   
		       }
		}
	 
	 
	 
	 
}
