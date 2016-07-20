package DAO;

import java.util.List;

import model.db.Item;

public interface ItemDao{
    public Item FindItemById(int id);
    public List<Item> FindItemByuserName(String name);
    public void updateItem(Item item);
    public void saveObject(Item item);
    public void deleteItem(Item item);
    
}
