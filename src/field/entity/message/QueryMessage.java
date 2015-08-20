package field.entity.message;

import java.util.List;

/**
 * Created by TongjiSSE on 2015/8/17.
 */
public class QueryMessage extends Message implements Cloneable{
    private List<Long> queryKeywords;
    private long queryID;

    public QueryMessage(){
        this.set_class(QueryMessage.class.getName());
    }

    public List getQueryKeywords() {
        return queryKeywords;
    }

    public void setQueryKeywords(List queryKeywords) {
        this.queryKeywords = queryKeywords;
    }

    public long getQueryID() {
        return queryID;
    }

    public void setQueryID(long queryID) {
        this.queryID = queryID;
    }

    public QueryMessage clone() throws CloneNotSupportedException{
        QueryMessage cloned = (QueryMessage)super.clone();
        cloned.queryKeywords.addAll(queryKeywords);
        return (cloned);
    }
}
