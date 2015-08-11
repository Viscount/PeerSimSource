package field.util;

import field.entity.QueryResult;
import peersim.util.IncrementalStats;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.*;

/**
 * Created by TongjiSSE on 2015/7/13.
 */
public class GlobalListener {

    private static int QUERY_WAITING_WINDOW = 1000;

    private static IncrementalStats first_hit_time;
    private static IncrementalStats first_full_hit_time;
    private static IncrementalStats num_of_results;

    private static Map<Long,QueryListener> queryListenerMap;

    public static void init(){
        first_hit_time = new IncrementalStats();
        first_full_hit_time = new IncrementalStats();
        num_of_results = new IncrementalStats();
        queryListenerMap = new HashMap<Long,QueryListener>();
    }

    public static void receiveQueryResult(long queryID, List<QueryResult> queryResultList){
        if ( queryListenerMap.containsKey(queryID) ){
            QueryListener queryListener = queryListenerMap.get(queryID);
            queryListener.receiveResult(queryResultList);
        }
    }

    public static void calculate(){
        for ( Iterator it = queryListenerMap.entrySet().iterator();it.hasNext();){
            Entry<Long,QueryListener> entry = (Entry)it.next();
            QueryListener queryListener = entry.getValue();
            first_hit_time.add(queryListener.getFirst_hit_time());
            first_full_hit_time.add(queryListener.getFirst_full_hit_time());
            num_of_results.add(queryListener.getResultSet().size());
        }
    }

}
