package field.entity.message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TongjiSSE on 2015/7/13.
 */
public class Message implements Cloneable{

    private long requester;
    private String type;
    private int interestType;
    private long queryID;
    private int TTL;
    private List queryKeywords;

    public Message() {
        queryKeywords = new ArrayList();
    }

    public long getRequester() {
        return requester;
    }

    public void setRequester(long requester) {
        this.requester = requester;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getInterestType() {
        return interestType;
    }

    public void setInterestType(int interestType) {
        this.interestType = interestType;
    }

    public long getQueryID() {
        return queryID;
    }

    public void setQueryID(long queryID) {
        this.queryID = queryID;
    }

    public int getTTL() {
        return TTL;
    }

    public void setTTL(int TTL) {
        this.TTL = TTL;
    }

    public List getQueryKeywords() {
        return queryKeywords;
    }

    public void setQueryKeywords(List queryKeywords) {
        this.queryKeywords = queryKeywords;
    }

    @Override
    public Message clone() throws CloneNotSupportedException {
        Message cloned = (Message) super.clone();
        cloned.queryKeywords.addAll(queryKeywords);
        return cloned;
    }
}
