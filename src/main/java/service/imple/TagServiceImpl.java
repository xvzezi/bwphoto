package service.imple;


import DAO.ItemDao;
import DAO.ItemtagDao;
import DAO.TagDao;
import model.db.Item;
import model.db.Itemtag;
import model.db.Tag;
import service.TagService;

import java.util.List;

/**
 * Tag Service Implementation
 * @author Xu Zezi
 * @since 2016/7/12
 * @version
 *      0   basic implementation
 */
public class TagServiceImpl implements TagService
{
	//auto wired
	private ItemtagDao itemtagDao;
	private ItemDao itemDao;
	private TagDao tagDao;

	/**
	 *
	 * @param item_id
	 * @param username
	 * @return
	 */
	@Override
	public List<Itemtag> getTags(String username, int item_id)
	{
		return itemtagDao.FindItemById(item_id);
	}

	/**
	 * 获取某个tag所有item
	 * @param tag
	 * @return
	 */
	@Override
	public List<Itemtag> getItems(String tag)
	{
		return itemtagDao.FindItemByTag(tag);
	}

	/**
	 * 为目标资源创建新的tag
	 * @param username
	 * @param itemtag
	 * @return
	 */
	@Override
	public boolean saveObject(String username, Itemtag itemtag)
	{
		//check if authorized
		if(isMatch(username, itemtag.getItemId()))
		{
			// check if the tag stored in the tag repo
			List<Tag> tags = tagDao.FindTagByName(itemtag.getTagName());
			if(tags == null || tags.size() == 0)
			{
				// store the new tag in the repo
				Tag tag = new Tag();
				tag.setName(itemtag.getTagName());
				tagDao.addTag(tag);
			}
			return itemtagDao.saveObject(itemtag.getItemId(), itemtag.getTagName());
		}
		else
		{
			return false;
		}
	}

	/**
	 * 检查该用户是否对此resource有修改的权限
	 * @param username
	 * @param item_id
	 * @return
	 */
	private boolean isMatch(String username, int item_id)
	{
		Item item = itemDao.FindItemById(item_id);
		if(item == null || !item.getUserName().equals(username))
		{
			return false;
		}
		return true;
	}

	/**
	 * 注入函数群
	 * @param dao
	 */
	public void setItemtagDao(ItemtagDao dao)
	{
		this.itemtagDao = dao;
	}
	public void setItemDao(ItemDao dao) { this.itemDao = dao; }
	public void setTagDao(TagDao dao) { this.tagDao = dao; }

}
