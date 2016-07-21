package service;


import java.util.List;

import javax.servlet.http.HttpSession;


import model.db.User;


public interface UserService {
    public List<User> getAdmin();
	public List<User> getGeneral();
	public String updateUser(User user, HttpSession session);
    public String deleteUser(User user, HttpSession session) ;
	 
}
