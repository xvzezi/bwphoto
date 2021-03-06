package service.imple;

import DAO.RecDataDao;
import DAO.UserDao;
import model.db.User;
import service.RegisterService;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Date;

public class RegisterServiceImpl implements RegisterService {
	private UserDao dao;
	public void setDao(UserDao dao) {
		this.dao = dao;
	}
	private RecDataDao dataDao;
	public void setDataDao(RecDataDao dataDao) { this.dataDao = dataDao; }

	@Override
	public String regUser(User user, HttpSession session) {
		String name_check=user.getName();
		if(dao.FindUserByName(name_check)==null){
			// basic info. of the user
			session.setAttribute("status", "Success");
			session.setAttribute("name",user.getName() );
			session.setAttribute("password",user.getPwd());
			session.setAttribute("email",user.getEmail() );
			session.setAttribute("age",user.getAge());
			user.setTime(new Timestamp(new Date().getTime()));
			user.setAmount(0);
			user.setRole(0);
			dao.saveObject(user);

			// give out the rec data space
			dataDao.createRecData(user.getName());

	        return "SUCCESS";
		}
		else {
			session.setAttribute("status", "name occupied");
			return "name occupied";
		}
	}

}
