package field.util;

import field.entity.QueryResult;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by TongjiSSE on 2015/7/16.
 */
public class QueryListener {

    private long start_up_time;
    private long first_hit_time;
    private long first_full_hit_time;
    private Set<Long> resultSet;
    private Set<Long> fullResultSet;
    private long total_count;
    private long total_full_count;

    public QueryListener(long start_up_time) {
        this.start_up_time = start_up_time;
        this.first_hit_time = 0;
        this.first_full_hit_time = 0;
        this.resultSet = new HashSet<Long>();
        this.fullResultSet = new HashSet<Long>();
    }

    public long getStart_up_time() {
        return start_up_time;
    }

    public void setStart_up_time(long start_up_time) {
        this.start_up_time = start_up_time;
    }

    public long getFirst_hit_time() {
        return first_hit_time;
    }

    public void setFirst_hit_time(long first_hit_time) {
        this.first_hit_time = first_hit_time;
    }

    public long getFirst_full_hit_time() {
        return first_full_hit_time;
    }

    public void setFirst_full_hit_time(long first_full_hit_time) {
        this.first_full_hit_time = first_full_hit_time;
    }

    public long getTotal_count() {
        return total_count;
    }

    public void setTotal_count(long total_count) {
        this.total_count = total_count;
    }

    public long getTotal_full_count() {
        return total_full_count;
    }

    public void setTotal_full_count(long total_full_count) {
        this.total_full_count = total_full_count;
    }

    public Set<Long> getResultSet() {
        return resultSet;
    }

    public void setResultSet(Set<Long> resultSet) {
        this.resultSet = resultSet;
    }

    public Set<Long> getFullResultSet() {
        return fullResultSet;
    }

    public void setFullResultSet(Set<Long> fullResultSet) {
        this.fullResultSet = fullResultSet;
    }

    public void receiveResult(List<QueryResult> resultList){
        if ( resultList.size() > 0 ){
            for ( QueryResult queryResult : resultList ){
                if ( queryResult.getHit_num() > 0 ){
                    if ( first_hit_time == 0 ) first_hit_time = queryResult.getHit_timestamp() - start_up_time;
                    resultSet.add(queryResult.getResourceID());
                    if ( queryResult.getHit_num() == CommonUtil.MAX_NUM_QUERY_KEYWORDS ){
                        if ( first_full_hit_time == 0 ) first_full_hit_time = queryResult.getHit_timestamp()-start_up_time;
                        fullResultSet.add(queryResult.getResourceID());
                    }
                }
            }
        }
    }
}
