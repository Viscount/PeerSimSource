package field.entity;

import field.entity.message.Message;
import field.entity.message.QueryMessage;
import peersim.core.CommonState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by TongjiSSE on 2015/7/13.
 */
public class ResourceRepo {

    private List<Resource> resourceList;

    public ResourceRepo() {
        resourceList = new ArrayList<Resource>();
    }

    public int size(){
        return resourceList.size();
    }

    public List<Resource> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<Resource> resourceList) {
        this.resourceList = resourceList;
    }

    public List<QueryResult> findResultForQuery(QueryMessage message){
        List queryKeywords = message.getQueryKeywords();
        List<QueryResult> resultList = new ArrayList<QueryResult>();
        for ( Resource resource : resourceList ){
            int hit_num = resource.matchKeywords(queryKeywords);
            if ( hit_num > 0 ){
                QueryResult result = new QueryResult();
                result.setResourceID(resource.getResourceID());
                result.setHit_num(hit_num);
                result.setHit_timestamp(CommonState.getTime());
                result.setQueryID(message.getQueryID());
                resultList.add(result);
            }
        }
        return resultList;
    }

    public Map<Long,Integer> findAllInterestType(){
        Map<Long,Integer> result = new HashMap();
        for (Resource resource : resourceList){
            if (!result.containsKey(resource.getInterestType())){
                result.put(resource.getInterestType(),1);
            }
            else {
                int count = result.get(resource.getInterestType());
                result.put(resource.getInterestType(),count++);
            }
        }
        return result;
    }
}
