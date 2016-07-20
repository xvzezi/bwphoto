package service.imple;

import DAO.ItemDao;
import DAO.MemoryDao;
import model.db.Item;
import model.db.Memory;
import service.WordService;
import util.Log;

import java.util.List;

/**
 * 信息服务
 * @since 2016/7/11
 * @Description:
 *   memory的具体内容
 */
public class WordServiceImpl implements WordService
{
    //auto wired
    private ItemDao itemDao;

    private MemoryDao memoryDao;

    @Override
    public Memory getMemoryByItem(int item_id)
    {
        Item item = itemDao.FindItemById(item_id);
        if(item == null || item.getMemoryId() == null)
        {
            return null;
        }

        Memory memory = memoryDao.FindMemoryById(item.getMemoryId());
        return memory;
    }

    @Override
    public boolean createNewMemoryOnItem(int item_id, Memory memory)
    {
        Item item = itemDao.FindItemById(item_id);
        if(item == null)
        {
            return false;
        }

        try
        {
            memoryDao.saveObject(memory);
            item.setMemoryId(memory.getId());
            itemDao.updateItem(item);
        }catch (Exception e)
        {
            Log.log.log("Error in Word Service:").log(e.getMessage()).log();
            return false;
        }
        return true;
    }

    @Override
    public List<Memory> getWords()
    {
        return memoryDao.getAll();
    }



    public ItemDao getItemDao()
    {
        return itemDao;
    }

    public void setItemDao(ItemDao itemDao)
    {
        this.itemDao = itemDao;
    }

    public MemoryDao getMemoryDao()
    {
        return memoryDao;
    }

    public void setMemoryDao(MemoryDao memoryDao)
    {
        this.memoryDao = memoryDao;
    }

}
