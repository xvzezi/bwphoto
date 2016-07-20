package model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

/**
 * Created by hasee on 2016/7/5.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BasicInfo
{
    @JsonProperty
    private String name;
    @JsonProperty
    private Timestamp date;

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
