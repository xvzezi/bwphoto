package DAO;

import java.util.List;

import model.db.Music;

public interface MusicDao{
    public List<Music> FindMusicByName(String name);
    public List<Music> FindMusicByAuthor(String author);
    public List<Music> FindMusicByNameAndAuthor(String name, String author);
    public void deleteMusic(Music music);
    public void saveObject(Music music);
    public void updateMusic(Music music) ;
    
}
