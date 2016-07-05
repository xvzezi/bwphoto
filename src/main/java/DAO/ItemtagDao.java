package DAO;

import java.util.List;

import model.db.Itemtag;

public interface ItemtagDao {
	//if succeeds returns item_id;
	public List<Itemtag> FindItemByTag(String tagName);
}
