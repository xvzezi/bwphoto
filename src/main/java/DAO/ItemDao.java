package DAO;

import model.db.Item;

public interface ItemDao{
    public Item FindItemById(int id);
    public void updateItem(Item item);
    public void saveObject(Item item);
    public void deleteItem(Item item);
    
}
