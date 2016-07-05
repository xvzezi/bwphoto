package DAO;

import java.util.List;

import model.db.Tag;

public interface TagDao
{
	
    public List<Tag> FindTagByName(String name);
    public void updateTag(Tag tag);
    public void deleteTag(Tag tag);
    public void addTag(Tag tag);
    
}
