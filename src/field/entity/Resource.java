package field.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TongjiSSE on 2015/7/13.
 */
public class Resource {

    private long resourceID;
    private long interestType;
    private List keywords;

    public Resource() {
        keywords = new ArrayList<>();
    }

    public long getResourceID() {
        return resourceID;
    }

    public void setResourceID(long resourceID) {
        this.resourceID = resourceID;
    }

    public long getInterestType() {
        return interestType;
    }

    public void setInterestType(long interestType) {
        this.interestType = interestType;
    }

    public List getKeywords() {
        return keywords;
    }

    public void setKeywords(List keywords) {
        keywords = keywords;
    }

    public int matchKeywords(List<Long> queryList){
        int count = 0;
        for ( long keyword : queryList){
            if ( this.keywords.contains(keyword) ) count++;
        }
        return count;
    }
}
