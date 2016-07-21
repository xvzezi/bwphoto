package DAO;

import java.util.List;

import model.SimpleUser;
import model.db.User;


public interface UserDao{
    public User UserValid(SimpleUser user);
    public User FindUser(String name, String password);
    public User FindUserByName(String name);
    public void updateUser(User user);
    public void saveObject(User user);
    public List<User> FindAll();
    public List<User> FindAdmin();
    public void deleteUser(User user);
}
