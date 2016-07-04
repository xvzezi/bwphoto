package service.imple;

import DAO.UserDao;
import model.db.User;
import service.RegisterService;
import javax.servlet.http.HttpSession;

public class RegisterServiceImpl implements RegisterService {
    private UserDao dao;
	@Override
	public String regUser(User user, HttpSession session) {
		// TODO Auto-generated method stub
		String name_check=user.getName();
		if(dao.FindUserByName(name_check)==null){
			 session.setAttribute("status", "Success");
			 session.setAttribute("name",user.getName() );
			 session.setAttribute("password",user.getPwd());
			 session.setAttribute("email",user.getEmail() );
			 session.setAttribute("age",user.getAge());
	        dao.saveObject(user);

	        return "SUCCESS";
		}
		else {
			session.setAttribute("status", "name occupied");
			return "name occupied";
		}
	}

}
