package field.entity;

import field.entity.Resource;

/**
 * Created by TongjiSSE on 2015/7/14.
 */
public class QueryResult {

    private long resourceID;
    private long hit_timestamp;
    private long queryID;
    private int hit_num;

    public long getResourceID() {
        return resourceID;
    }

    public void setResourceID(long resourceID) {
        this.resourceID = resourceID;
    }

    public long getHit_timestamp() {
        return hit_timestamp;
    }

    public void setHit_timestamp(long hit_timestamp) {
        this.hit_timestamp = hit_timestamp;
    }

    public long getQueryID() {
        return queryID;
    }

    public void setQueryID(long queryID) {
        this.queryID = queryID;
    }

    public int getHit_num() {
        return hit_num;
    }

    public void setHit_num(int hit_num) {
        this.hit_num = hit_num;
    }
}
