package field.util;

import field.entity.Resource;
import field.entity.message.QueryMessage;
import field.support.ResourceDb;
import peersim.core.CommonState;

import java.util.*;

/**
 * Created by TongjiSSE on 2015/7/14.
 */
public class CommonUtil {

    public static int MAX_MESSAGE_TTL = 7;
    public static int MAX_NUM_QUERY_KEYWORDS = 3;

    public static <T extends Comparable< ? super T>> T findMax( List<T> list ){
        T result = null;
        for ( T element : list ){
            if (( result == null ) || ( element.compareTo(result) > 0 )) result = element;
        }
        return result;
    }

    public static <T extends Comparable< ? super T>> List<T> findTopN( List<T> list, int n ){
        List<T> result = new ArrayList<T>();
        for ( int times = 0; times < n; times++ ){
            T currentMax = null;
            int position = -1;
            for ( int i=0; i<list.size(); i++ ){
                T currentElement = list.get(i);
                if (( currentMax == null ) || ( currentElement.compareTo(currentMax) > 0 )) {
                    currentMax = currentElement;
                    position = i;
                }
            }
            if (currentMax!=null){
                result.add(currentMax);
                list.remove(position);
            }
        }
        return result;
    }

    public static <T> List<T> randomPickFromArray(List<T> list, long count) {
        List<T> cloneArrayList = new ArrayList<T>(list);
        if (list.size() <= count) {
            return cloneArrayList;
        }
        List<T> resultList = new ArrayList<T>();
        while ((count > 0)&&(cloneArrayList.size()>0)) {
            long randomIndex = CommonState.r.nextInt(cloneArrayList.size());
            T obj = cloneArrayList.get((int)randomIndex);
            resultList.add(obj);
            cloneArrayList.remove(obj);
            count--;
        }

        return resultList;
    }

    public static String convert2SimpleName(String classFullName){
        StringBuffer stringBuffer = new StringBuffer(classFullName);
        int pos = stringBuffer.lastIndexOf(".");
        return stringBuffer.substring(pos+1);
    }

    public static QueryListener findFullSet(QueryListener queryListener, QueryMessage message){
        //TODO 找全集
        int hit_count = 0;
        int full_hit_count = 0;
        List queryKeywords = message.getQueryKeywords();
        for ( Resource resource : ResourceDb.getExistingRepo()) {
            int hit_num = resource.matchKeywords(queryKeywords);
            if (hit_num > 0) {
                hit_count++;
                if ( hit_num == CommonUtil.MAX_NUM_QUERY_KEYWORDS ) full_hit_count++;
            }
        }
        queryListener.setTotal_count(hit_count);
        queryListener.setTotal_full_count(full_hit_count);
        return queryListener;
    }
}
