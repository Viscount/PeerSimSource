package field.entity;

import java.util.*;

/**
 * Created by TongjiSSE on 2016/6/24.
 */
public class SpanningTreeNode {
    private Long nodeId;
    private Set filter;
    private List<SpanningTreeNode> childrenList;

    public SpanningTreeNode(Long id){
        this.nodeId = id;
    }

    public void addChild(SpanningTreeNode child){
        if( childrenList == null ){
            childrenList = new ArrayList<SpanningTreeNode>();
            childrenList.add(child);
        }
        else childrenList.add(child);
    }

    public void addInterest(Long interestID){
        if (filter == null){
            filter = new HashSet<Long>();
            filter.add(interestID);
        }
        else {
            filter.add(interestID);
        }
    }

    public void addInterest(Set interestSet){
        if ( filter == null ){
            filter = new HashSet<Long>();
            filter.addAll(interestSet);
        }
        else{
            filter.addAll(interestSet);
        }
    }

    public Set getFilter() {
        return filter;
    }

    public boolean checkInterest(Long interestID){
        return filter.contains(interestID);
    }

    public long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public List<SpanningTreeNode> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<SpanningTreeNode> childrenList) {
        this.childrenList = childrenList;
    }
}
