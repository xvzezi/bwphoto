package service;

import model.db.Itemtag;

import java.util.List;

/**
 * Tag Service
 * @author Xu Zezi
 * @since 2016/7/12
 * @version
 *      0   basic interface
 */
public interface TagService
{
	/**
	 * By item_id, get its tags
	 * @param item_id
	 * @return
	 */
	public List<Itemtag> getTags(String username, int item_id);

	/**
	 * By tag name, get items hold that
	 * @param tag
	 * @return
	 */
	public List<Itemtag> getItems(String tag);

	/**
	 * Add A New Item Tag
	 * @param itemtag
	 * @return
	 */
	public boolean saveObject(String username, Itemtag itemtag);
}
