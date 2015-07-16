package field.entity;

import field.entity.message.Message;
import peersim.core.CommonState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TongjiSSE on 2015/7/13.
 */
public class ResourceRepo {

    private List<Resource> resourceList;

    public ResourceRepo() {
        resourceList = new ArrayList<Resource>();
    }

    public List<Resource> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<Resource> resourceList) {
        this.resourceList = resourceList;
    }

    public List<QueryResult> findResultForQuery(Message message){
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
}
