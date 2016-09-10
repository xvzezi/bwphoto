package service;

import controller.Resource;
import model.db.Item;
import model.db.User;
import model.request.ResourceCreation;

import java.util.List;

/**
 * Service of Recommend
 * @author Xu Zezi
 * @since 2016/8/10.
 * @version
 *      0   basic
 *      1   added 2016/9/8
 */
public interface RecService
{
	/**
	 * Update for New Friends Made
	 * do it after friend added
	 */
	public void addFriend(String username, String tar);

	/**
	 * Update for Deleted Friends
	 * do it after friend deleted
	 */
	public void deleteFriend(String username, String tar);

	/**
	 * Update for New Added Resource
	 * do it after resources added
	 */
	public void addResource(String name, ResourceCreation resourceCreation);

	/**
	 * Update for Deleted Friends
	 * do it after resources deleted
	 */
	public void deleteResource(Item item);

	/**
	 * Get Recommended Friends
	 */
	public List<String> getRecFriends(String username);
}
