package field.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by TongjiSSE on 2015/7/14.
 */
public class CommonUtil {

    public static int MAX_MESSAGE_TTL = 7;

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
}
