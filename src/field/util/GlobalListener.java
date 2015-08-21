package field.util;

import field.entity.QueryResult;
import peersim.core.CommonState;
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

    public static long queryIDCounter;

    public static IncrementalStats first_hit_time;
    public static IncrementalStats first_full_hit_time;
    public static IncrementalStats num_of_results;

    private static Map<Long,QueryListener> queryListenerMap;

    public static void init(){
        queryIDCounter = 0;
        first_hit_time = new IncrementalStats();
        first_full_hit_time = new IncrementalStats();
        num_of_results = new IncrementalStats();
        queryListenerMap = new HashMap<Long,QueryListener>();
    }

    public static void addNewListener(long queryID){
        QueryListener queryListener = new QueryListener(CommonState.getTime());
        queryListenerMap.put(queryID,queryListener);
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
            if (queryListener.getFirst_full_hit_time()!=0) first_full_hit_time.add(queryListener.getFirst_full_hit_time());
            num_of_results.add(queryListener.getResultSet().size());
        }
    }

}
