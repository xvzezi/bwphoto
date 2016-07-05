package DAO;

import java.util.List;

import model.db.Friend;


public interface FriendDao
{
    public List<Friend> FindFriend(String myName);
    public void addFriend(String myName,String frName);
    public void deleteFriend(String frName);
    
}
