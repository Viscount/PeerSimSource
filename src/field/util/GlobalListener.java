package field.util;

import field.entity.QueryResult;
import peersim.util.IncrementalStats;

import java.util.List;
import java.util.Map;

/**
 * Created by TongjiSSE on 2015/7/13.
 */
public class GlobalListener {

    private static int QUERY_WAITING_WINDOW = 1000;

    private static IncrementalStats first_hit_time;
    private static IncrementalStats first_full_hit_time;
    private static IncrementalStats num_of_results;

    private static Map<Integer,QueryListener> queryListenerMap;

    public static void receiveQueryResult(long queryID, List<QueryResult> queryResultList){
        if ( queryListenerMap.containsKey(queryID) ){
            QueryListener queryListener = queryListenerMap.get(queryID);
            queryListener.receiveResult(queryResultList);
        }
    }

}
