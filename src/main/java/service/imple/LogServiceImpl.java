package service.imple;

import javax.servlet.http.HttpSession;

import DAO.UserDao;
import model.SimpleUser;
import model.db.User;
import service.LogService;
import util.SpringIoC;

public class LogServiceImpl implements LogService{
    private UserDao dao;
	public void setDao(UserDao dao) {
		this.dao = dao;
	}

	@Override
	public String login(SimpleUser user, HttpSession session) {
		// TODO Auto-generated method stub
	        
	    User thisuser=dao.FindUser(user.getUsername(),user.getPwd());
		if(thisuser!=null){
			session.setAttribute("name",thisuser.getName() );
			session.setAttribute("role",thisuser.getRole() );
			return "Success";
		}
		return "Fail";
	}



	@Override
	public User userDetail(String username) {
		// TODO Auto-generated method stub
		User user=dao.FindUserByName(username);
		if(user!=null)
		{
			user.setPwd(null);
			return user;
		}
		return null;
	}

}
