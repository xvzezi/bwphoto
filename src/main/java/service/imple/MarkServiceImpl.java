package service.imple;

import DAO.ItemDao;
import DAO.MarkDao;
import model.db.Item;
import model.db.Mark;
import service.MarkService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MarkServiceImpl implements MarkService
{
	public List<Mark> getMarks(Integer item_id, Integer mark_id, String username, Timestamp timestamp, int pick_size)
	{
		// check the auth
		Item item = itemDao.FindItemById(item_id);
		if(item == null)
		{
			// no such target
			return null;
		}

		// not authored
		if(item.getStatus() == 'n' && !username.equals(item.getUserName()))
		{
			return null;
		}

		// have the access
		if(mark_id == null)
		{
			// pick up all the content
			if(timestamp == null)
			{
				Date date = new Date();
				timestamp = new Timestamp(date.getTime());
			}
			return markDao.FindMarkByItemId(item_id, timestamp, pick_size);
		}
		else
		{
			// pick up certain content
			Mark mark = markDao.FindMarkById(mark_id);
			if(mark == null)
				return null;
			else if(mark.getItemId().equals(item_id))
			{
				List<Mark> lms = new ArrayList<Mark>();
				lms.add(mark);
				return lms;
			}
			else
			{
				return null;
			}
		}
	}

	public int createMark(String content, Integer item_id, Integer target, String username)
	{
		int result = markDao.saveObject(content, item_id, target, username);
		return result;
	}
	public String deleteMark(Integer id, String username)
	{
		markDao.deleteMark(id, username);
		return "success";
	}

	private ItemDao itemDao;

	private MarkDao markDao;

	public MarkDao getMarkDao()
	{
		return markDao;
	}

	public void setMarkDao(MarkDao markDao)
	{
		this.markDao = markDao;
	}

	public ItemDao getItemDao()
	{
		return itemDao;
	}

	public void setItemDao(ItemDao itemDao)
	{
		this.itemDao = itemDao;
	}

}