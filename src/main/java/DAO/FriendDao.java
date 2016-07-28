package DAO;

import java.util.List;

import model.db.Friend;

/**
 * DAO of Friends things
 * @since 2016/7/10
 * @version
 *      0   basic design
 *      1   add a more func. 2016/7/28
 */
public interface FriendDao 
{
	/**
     * 找到所有的朋友
     * @param name
     * @return 所有的朋友
     *      返回的是friend对象，所以数据结构拥有冗余
     */
    public List<Friend> FindFriend(String name);

	/**
     * 找到所有的朋友
     * @param name
     * @return 所有的朋友
     *      由于上一个有冗余，所以有一个仅由名字构成的版本
     */
    public List<String> findFriendRetNames(String name);

	/**
	 * 增添、删除朋友
     * @param myName
     * @param frName
     */
    public void addFriend(String myName, String frName);
    public void deleteFriend(String myName,String frName);
    
}
