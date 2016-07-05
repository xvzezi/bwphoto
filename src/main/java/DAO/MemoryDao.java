package DAO;

import model.db.Memory;

public interface MemoryDao
{
	
    public void updateMemory(Memory memory);
    public void saveObject(Memory memory);
    public void deleteMemory(Memory memory);
    public Memory FindMemoryById(int id);
    public Memory FindMemoryByContent(String content);
}
