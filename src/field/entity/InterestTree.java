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

    public List<InterestNode> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<InterestNode> nodeList) {
        this.nodeList = nodeList;
    }
}
