package field.entity;

import java.util.List;

/**
 * Created by TongjiSSE on 2015/7/13.
 */
public class InterestTree {

    private List<InterestNode> nodeList;

    public InterestTree(List<InterestNode> nodeList) {
        this.nodeList = nodeList;
    }

    public void setInterestWeight(long interestTypeID, double weight){
        for ( InterestNode interestNode : nodeList ){
            if ( interestNode.getInterestID() == interestTypeID ){
                interestNode.setWeight(weight);
            }
        }
    }

    public List<InterestNode> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<InterestNode> nodeList) {
        this.nodeList = nodeList;
    }
}
