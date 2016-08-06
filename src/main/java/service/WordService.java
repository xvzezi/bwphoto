package service;

import model.db.Memory;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Service Of Words
 * @author Xu Zezi
 * @since 2016/7/10.
 * @version
 *      0   Basic Impl.
 */
public interface WordService
{
	/**
     * Memory与item的互动
     * @param item_id
     * @return
     */
    public Memory getMemoryByItem(int item_id);
    public boolean createNewMemoryOnItem(int item_id, Memory memory);

	/**
	 * CRUD
     * @return
     */
    public List<Memory> getWords();
    public String updateMemory(Memory memory, HttpSession session);
    public String deleteMemory(Memory memory, HttpSession session);
}
