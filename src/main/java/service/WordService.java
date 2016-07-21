package service;

import model.db.Memory;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by hasee on 2016/7/10.
 */
public interface WordService
{
    public Memory getMemoryByItem(int item_id);
    public boolean createNewMemoryOnItem(int item_id, Memory memory);
    public List<Memory> getWords();
    public String updateMemory(Memory memory, HttpSession session);
    public String deleteMemory(Memory memory, HttpSession session);
}
