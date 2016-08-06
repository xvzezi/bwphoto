package DAO;

import java.util.List;

import model.SimpleUser;
import model.db.User;

/**
 * Interface User Dao
 * @author Zhou TQ
 * @since 2016/7/1
 * @version
 *      0   basic func
 *      1   new function added
 */
public interface UserDao{
	/**
     * 检查User是否有效
     * @param user
     * @return
     */
    public User UserValid(SimpleUser user);

	/**
	 * 查找用户
     * @param name
     * @param password
     * @return
     */
    public User FindUser(String name, String password);

	/**
	 * 通过名字查找用户
     * @param name
     * @return
     */
    public User FindUserByName(String name);

	/**
	 * 更新用户
     * @param user
     */
    public void updateUser(User user);

	/**
     * 保存新用户
     * @param user
     */
    public void saveObject(User user);

	/**
	 * 找到所有的用户
     * @return
     */
    public List<User> FindAll();

	/**
     * 找到所有的管理员用户
     * @return
     */
    public List<User> FindAdmin();

	/**
	 * 杀出一个用户
     * @param user
     */
    public void deleteUser(User user);
}
