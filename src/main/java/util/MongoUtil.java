package util;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

import java.io.File;
import java.io.InputStream;

/**
 * Mongo Database Utility
 * @author Xu Zezi
 * @since 2016/7/7
 * @version
 *      0   images fetching and saving
 */
public class MongoUtil
{
    private static Mongo mongo = SpringIoC.idGetter("mongodb", MongoConfig.class).getMongoDB();

    /**
     * save an image through given configuration
     * @param database
     * @param bucket
     * @param filename
     * @param image
     * @return success or not
     */
    public static boolean saveImg(String database, String bucket, String filename, InputStream image)
    {
        DB db = mongo.getDB(database);
        GridFS gfs = new GridFS(db, bucket);
        try{
            gfs.remove(filename);
        }catch(Exception e){}
        try{
        GridFSInputFile gif = gfs.createFile(image);
        gif.put("filename", filename);
        gif.save();
        }catch (Exception e){
            Log.log.log("error while saving images:").log(e.getMessage()).log();
            return false;
        }
        return true;
    }

    /**
     * fetch an image has been saved in the mongodb
     * @param database
     * @param bucket
     * @param filename
     * @return file stream and null when nothing inside or something went wrong
     */
    public static InputStream getImg(String database, String bucket, String filename)
    {
        InputStream is = null;
        DB db = mongo.getDB(database);
        GridFS gfs = new GridFS(db, bucket);
        try{
            GridFSDBFile target = gfs.findOne(filename);
            is = target.getInputStream();
        }catch(Exception e)
        {
            Log.log.log("error while fetching images:").log(e.getMessage()).log();
            return null;
        }
        return is;
    }
}
