package field.support;

import field.entity.InterestNode;
import field.entity.InterestTree;
import field.entity.Resource;
import field.util.CommonUtil;
import peersim.core.CommonState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TongjiSSE on 2015/8/12.
 */
public class ResourceDb {

    private static List<Resource> resourcesDb;

    public static void init(){
        resourcesDb = new ArrayList();
    }

    public static List generateRepoForNode(InterestTree interestTree,int num_resource){
        List<InterestNode> interestList = interestTree.getNodeList();
        List<Resource> candidate = new ArrayList();
        for ( InterestNode interestNode : interestList ){
            candidate.addAll(findAllResourceForInterest(interestNode.getInterestID()));
        }
        if ( candidate.size() < num_resource ) {
            // TODO
        }
        return CommonUtil.randomPickFromArray(candidate,num_resource);
    }

    public static void generate(int num, int keyword_per_resource){
        for (int i=0; i<num; i++){
            Resource resource = new Resource();
            resource.setResourceID(i);
            long interestTypeId = InterestDb.randomSelectOneInterest();
            resource.setInterestType(interestTypeId);
            InterestNode interestNode = InterestDb.getInterestById(interestTypeId);
            List keyWordList = CommonUtil.randomPickFromArray(interestNode.getKeywords(),keyword_per_resource);
            resource.setKeywords(keyWordList);
            resourcesDb.add(resource);
        }
    }

    public static List<Resource> findAllResourceForInterest(long interestType){
        List<Resource> result = new ArrayList();
        for ( Resource resource : resourcesDb){
            if ( resource.getInterestType() == interestType ) result.add(resource);
        }
        return result;
    }

}
