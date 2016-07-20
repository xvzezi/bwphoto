package DAO;

import java.util.List;

import model.db.Memory;

public interface MemoryDao
{
	
    public void updateMemory(Memory memory);
    public void saveObject(Memory memory);
    public void deleteMemory(Memory memory);
    public Memory FindMemoryById(int id);
    public List<Memory> FindMemoryByContent(String content);
    public List<Memory> getAll();
}
