package DAO;

import java.util.List;

import model.db.Itemtag;

public interface ItemtagDao {
	//if succeeds returns item_id;
	public List<Itemtag> FindItemByTag(String tagName);
	public List<Itemtag> FindItemById(int item_id);
	public boolean saveObject(int item_id, String tagname);
}
