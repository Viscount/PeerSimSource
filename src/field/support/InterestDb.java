package field.support;

import field.entity.InterestNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TongjiSSE on 2015/8/12.
 */
public class InterestDb {

    private static List interestDb;

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
}
