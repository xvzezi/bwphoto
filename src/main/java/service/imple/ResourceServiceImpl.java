package service.imple;

import DAO.FriendDao;
import DAO.ItemDao;
import DAO.UserDao;
import controller.Friend;
import model.db.Item;
import model.db.User;
import model.request.ResourceCreation;
import model.response.ImgHash;
import service.ResourceService;
import util.Log;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 资源服务
 * @author Sturmfy
 * @category Service
 * @version
 *      0   实现基本的资源获取服务，检查人与资源的一致性
 *      1   根据timestamp获取资源     2016/7/19
 *      1.1
 *      2.5 增加msuic与book部分      2016/8/1
 *      2.6 user增加数量信息，例行更改 2016/8/2
 * @since 2016/7/5
 * @Description
 *   获取资源，同时管理数据权限
 */
public class ResourceServiceImpl implements ResourceService
{
    private ItemDao dao;
    public void setDao(ItemDao id)
    {
        this.dao = id;
    }
    private FriendDao friendDao;
    public void setFriendDao(FriendDao friendDao) { this.friendDao = friendDao; }
    private UserDao userDao;
    public void setUserDao(UserDao userDao) { this.userDao = userDao; }

    /**
     * 获取资源，如果有id，则获取某个资源
     * @param name
     * @param resource_id
     * @return resources
     */
    @Override
    public List<Item> getResources(String name, int resource_id)
    {
        //get all the resources
        if(resource_id < 0)
        {
            List<Item> lis = dao.FindItemByUsername(name, null, 0, true);
            return lis;
        }

        if(name == null)
        {
            Item item = dao.FindItemById(resource_id);
            if(item == null)
                return null;
            else
            {
                List<Item> lms = new ArrayList<Item>();
                lms.add(item);
                return lms;
            }
        }
        //get certain resource
        Item item;
        item = dao.FindItemById(resource_id);

        //check the username
        if(!item.getUserName().equals(name))
        {
            if(item.getStatus() != 1)
                return null;
        }
        List<Item> lis = new LinkedList<Item>();
        lis.add(item);
        return lis;
    }

    /**
     * 创建一个空资源
     * @param name
     * @param resourceCreation
     * @return item
     */
    @Override
    public Item createResource(String name, ResourceCreation resourceCreation)
    {
        // create new one
        Item item = new Item();
        item.setUserName(name);
        Date date = new Date();
        item.setTime(new Timestamp(date.getTime()));
        item.setEmotion(resourceCreation.getEmotion());
        item.setStatus(1);
        dao.saveObject(item);

        // when nothing's wrong

        // add one to the people's account
        User user = userDao.FindUserByName(name);
        user.setAmount(user.getAmount() + 1);
        userDao.updateUser(user);

        // finished
        return item;
    }

    @Override
    public boolean deleteResource(String name, int resource_id)
    {
        Item item = dao.FindItemById(resource_id);
        if(item == null || !item.getUserName().equals(name))
        {
            return false;
        }
        dao.deleteItem(item);

        // when nothing's wrong
        // add one to the people's account
        User user = userDao.FindUserByName(name);
        user.setAmount(user.getAmount() - 1);
        userDao.updateUser(user);

        return true;
    }

    /**
     * 获取最新的资源
     * @param timestamp
     * @param amount
     * @return list
     * - timestamp为空
     *      返回最近的amount条
     * - timestamp 不为空
     *      返回timestamp之前的amount条
     */
    public List<Item> getLatestResource(Timestamp timestamp, int amount)
    {
        return dao.getItems(timestamp, amount);
    }

    /**
     * 改变资源的可见度
     * @param name
     * @param resource_id
     * @return old status
     *      拥有者可以更改自己的资源的权限，以确认展示与否
     */
    public int changeToPublic(String name, int resource_id)
    {
        Item item = dao.FindItemById(resource_id);
        if(item == null)
        {
            return 0;
        }
        // check the auth
        if(!item.getUserName().equals(name))
        {
            return 1;
        }
        // change the result
        int old = item.getStatus();
        item.setStatus(1);
        dao.updateItem(item);
        return old;
    }
    public int changeToPrivate(String name, int resource_id)
    {
        Item item = dao.FindItemById(resource_id);
        if(item == null)
        {
            return 0;
        }
        // check the auth
        if(!item.getUserName().equals(name))
        {
            return 1;
        }
        // change the result
        int old = item.getStatus();
        item.setStatus(0);
        dao.updateItem(item);
        return old;
    }

    /**
     * 查看好友的最近信息
     * @param name
     * @param timestamp
     * @param amount
     * @return 朋友最近的信息
     *          返回朋友当中最近的、以时间戳为起点的、不超过amount个数的resources
     */
    public List<Item> getFriendAndLatest(String name, Timestamp timestamp, int amount)
    {
        List<String> names = friendDao.findFriendRetNames(name);
        return dao.findItemOfUserFriend(names, timestamp, amount);
    }

    /**
     * 获取某个人的信息
     * @param tarname
     * @param username
     * @param timestamp
     * @param amount
     * @return 某个人的信息
     *      查询者为自己的情况下——获取全部信息，按照给与的timestamp去fetch
     *      查询他人的情况下——只能获取状态为public 的部分
     */
    public List<Item> getPersonalResource(String tarname, String username, Timestamp timestamp, int amount)
    {
        if(username.equals(tarname))
        {
            return dao.FindItemByUsername(tarname, timestamp, amount, true);
        }
        else
        {
            return dao.FindItemByUsername(tarname, timestamp, amount, false);
        }
    }

    /**
     * 更新一条resource的music hash
     * @param resource_id
     * @param music_hash
     * @param username
     * @return 成功信息
     *      先匹配resource的ownership信息，符合的情况下更改
     */
    public String setMusicHash(int resource_id, String music_hash, String username)
    {
        if(username == null)
        {
            return "Require Valid User Name";
        }
        Item item = dao.FindItemById(resource_id);
        if(item == null)
        {
            return "Invalid Resource Id";
        }
        if(!username.equals(item.getUserName()))
        {
            return "Require Proper Auth";
        }
        item.setMusicId(music_hash);
        try
        {
            dao.updateItem(item);
        }catch (Exception e)
        {
            Log.log.log("Error in setMusicHash: ").log(e.getMessage()).log();
            return "Something is Wrong";
        }
        return "success";
    }

    /**
     * 更新一条resource的book isbn
     * @param resource_id
     * @param ISBN
     * @param username
     * @return 成功信息
     *      先匹配resource的ownership信息，符合的情况下更改
     */
    public String setBookISBN(int resource_id, String ISBN, String username)
    {
        if(username == null)
        {
            return "Require Valid User Name";
        }
        Item item = dao.FindItemById(resource_id);
        if(item == null)
        {
            return "Invalid Resource Id";
        }
        if(!username.equals(item.getUserName()))
        {
            return "Require Proper Auth";
        }
        item.setIsbn(ISBN);
        try
        {
            dao.updateItem(item);
        }catch (Exception e)
        {
            Log.log.log("Error in setMusicHash: ").log(e.getMessage()).log();
            return "Something is Wrong";
        }
        return "success";
    }

    /**
     * 在符合条件的情况下，设置hash——image
     *
     * @param resource_id
     * @param name
     * @param hash
     * @return boolean
     */
    @Override
    public String setImgHash(int resource_id, String name, String hash)
    {
        if(name == null)
        {
            return "Require Valid User Name";
        }
        if(hash == null)
        {
            return "Invalid hash code";
        }
        Item item = dao.FindItemById(resource_id);
        if(item == null)
        {
            return "Invalid Resource Id";
        }
        if(!name.equals(item.getUserName()))
        {
            return "Require Proper Auth";
        }
        if(item.getImageId() == null)
        {
            return "Image Not Uploaded";
        }
        item.setImg_hash(hash);
        try
        {
            dao.updateItem(item);
        }catch (Exception e)
        {
            Log.log.log("Error in setImgHash: ").log(e.getMessage()).log();
            return "Something is Wrong";
        }
        return "success";
    }

    /**
     * 获取用户所有的hash, img
     *
     * @param name
     * @return list
     */
    @Override
    public List<ImgHash> getImgHashOfName(String name)
    {
        if(name == null)
        {
            return null;
        }

        return dao.getImgHash(name);
    }
}
