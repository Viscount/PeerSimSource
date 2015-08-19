package field.entity.message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TongjiSSE on 2015/7/13.
 */
public class Message implements Cloneable{

    private long requester;
    private long interestType;
    private int TTL;
    private String _class;

    public Message() {
        this.set_class(Message.class.getName());
    }

    public long getRequester() {
        return requester;
    }

    public void setRequester(long requester) {
        this.requester = requester;
    }

    public long getInterestType() {
        return interestType;
    }

    public void setInterestType(long interestType) {
        this.interestType = interestType;
    }

    public int getTTL() {
        return TTL;
    }

    public void setTTL(int TTL) {
        this.TTL = TTL;
    }

    public String get_class() {
        return _class;
    }

    public void set_class(String _class) {
        this._class = _class;
    }

    @Override
    public Message clone() throws CloneNotSupportedException {
        return (Message)super.clone();
    }
}
