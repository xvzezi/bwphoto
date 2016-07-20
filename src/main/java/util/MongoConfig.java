package util;

import com.mongodb.Mongo;

/**
 * Created by hasee on 2016/7/7.
 */
public class MongoConfig
{
    private String ip;
    private int port;
    public Mongo getMongoDB()
    {
        return new Mongo(ip, port);
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }
}
