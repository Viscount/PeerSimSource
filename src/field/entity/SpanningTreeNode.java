package field.entity;

import java.util.List;

/**
 * Created by TongjiSSE on 2016/6/24.
 */
public class SpanningTreeNode {

    private Long nodeId;
    private List<SpanningTreeNode> childrenList;

    public SpanningTreeNode(Long id){
        this.nodeId = id;
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
