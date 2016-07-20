package service.imple;

import DAO.ItemDao;
import model.BasicInfo;
import model.db.Item;
import model.db.User;
import service.ResourceService;
import util.Log;

import java.sql.Timestamp;
import java.util.ArrayList;
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
            List<Item> lis = dao.FindItemByuserName(name);
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
            if(item.getStatus() != 'y')
                return null;
        }
        List<Item> lis = new LinkedList<Item>();
        lis.add(item);
        return lis;
    }

    /**
     * 创建一个空资源
     * @param name
     * @param bi
     * @return item
     */
    @Override
    public Item createResource(String name, BasicInfo bi)
    {

        Item item = new Item();
        item.setUserName(name);

        dao.saveObject(item);
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
        return null;
    }

    /**
     * 改变资源的可见度
     * @param name
     * @param resource_id
     * @return old status
     *      拥有者可以更改自己的资源的权限，以确认展示与否
     */
    public char changeToPublic(String name, int resource_id)
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
        char old = item.getStatus();
        item.setStatus('y');
        dao.updateItem(item);
        return old;
    }
    public char changeToPrivate(String name, int resource_id)
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
        char old = item.getStatus();
        item.setStatus('n');
        dao.updateItem(item);
        return old;
    }
}
