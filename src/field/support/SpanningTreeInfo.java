package field.support;

import field.entity.SpanningTreeNode;
import field.util.TopologyUtil;
import peersim.core.Network;

import java.util.*;

/**
 * Created by TongjiSSE on 2016/6/24.
 */
public class SpanningTreeInfo {

    private static Map<Long, SpanningTreeNode> spanningTrees;

    public SpanningTreeInfo(){
        spanningTrees = new HashMap<Long, SpanningTreeNode>();
        for ( long i=0; i< Network.size(); i++){
            SpanningTreeNode rootNode = buildTree(i);
            spanningTrees.put(i, rootNode);
        }
    }

    public static List<SpanningTreeNode> findChildren(Long nodeID, long targetID){
        SpanningTreeNode rootNode = spanningTrees.get(nodeID);
        return find(rootNode,targetID);
    }

    // private method
    private static List<SpanningTreeNode> find(SpanningTreeNode root, Long targetID){
        if ( root.getNodeId() == targetID ) return root.getChildrenList();
        else{
            List<SpanningTreeNode> nextHops = root.getChildrenList();
            return find(root, targetID);
        }
    }

    private static SpanningTreeNode buildTree(Long rootID){
        SpanningTreeNode rootNode = new SpanningTreeNode(rootID);
        Set nodeInTreeSet = new HashSet<Long>();
        Set nodeAvailableSet = new HashSet<Long>();
        nodeInTreeSet.add(rootID);
        while (nodeInTreeSet.size() < Network.size()){

        }
        return rootNode;
    }
}
