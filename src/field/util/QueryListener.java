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

    public QueryListener(long start_up_time) {
        this.start_up_time = start_up_time;
        this.first_hit_time = 0;
        this.first_full_hit_time = 0;
        this.resultSet = new HashSet<Long>();
    }

    public void receiveResult(List<QueryResult> resultList){
        if ( resultList.size() > 0 ){
            for ( QueryResult queryResult : resultList ){
                if ( queryResult.getHit_num() > 0 ){
                    if ( first_hit_time == 0 ) first_hit_time = queryResult.getHit_timestamp();
                    resultSet.add(queryResult.getResourceID());
                    if ( queryResult.getHit_num() == CommonUtil.MAX_NUM_QUERY_KEYWORDS ){
                        if ( first_full_hit_time == 0 ) first_full_hit_time = queryResult.getHit_timestamp();
                    }
                }
            }
        }
    }
}
