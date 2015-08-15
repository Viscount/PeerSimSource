package field.support;

import field.entity.InterestNode;
import field.entity.InterestTree;
import field.util.CommonUtil;
import peersim.core.CommonState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TongjiSSE on 2015/8/12.
 */
public class InterestDb {

    private static List<InterestNode> interestDb;

    public static void init(){
        interestDb = new ArrayList();
    }

    public static void generate(int num_int, int keyword_per_int){
        KeywordDb.init();
        KeywordDb.generate(num_int*keyword_per_int);
        long counter = 0;
        for (int i=0; i<num_int; i++){
            InterestNode interestNode = new InterestNode(i);
            List keywordList = new ArrayList();
            for (int j=0; j<keyword_per_int; j++){
                keywordList.add(counter);
                counter++;
            }
            interestNode.setKeywords(keywordList);
            interestDb.add(interestNode);
        }
    }

    public static InterestNode getInterestById(long interestId){
        return interestDb.get(Integer.parseInt(String.valueOf(interestId)));
    }

    public static InterestTree generateTreeForNode(int num_interest){
        List nodeList = CommonUtil.randomPickFromArray(interestDb,num_interest);
        InterestTree interestTree = new InterestTree(nodeList);
        return (interestTree);
    }

    public static long randomSelectOneInterest(){
        InterestNode interestNode = (InterestNode)interestDb.get(CommonState.r.nextInt(interestDb.size()));
        return interestNode.getInterestID();
    }
}
