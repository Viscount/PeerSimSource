package field.support;

import field.entity.SpanningTreeNode;
import field.util.TopologyUtil;
import peersim.core.Network;
import peersim.core.Node;

import java.util.*;

/**
 * Created by TongjiSSE on 2016/6/24.
 */
public class SpanningTreeInfo {

    private static Map<Long, SpanningTreeNode> spanningTrees;
    private static int protocolID;

    public static void init(int linkProtocolID){
        spanningTrees = new HashMap<Long, SpanningTreeNode>();
        protocolID = linkProtocolID;
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
            List<SpanningTreeNode> notNullResult = null;
            List<SpanningTreeNode> nextHops = root.getChildrenList();
            if( nextHops == null ) return null;
            for ( SpanningTreeNode nextNode : nextHops){
                List<SpanningTreeNode> result = find(nextNode, targetID);
                if (  result != null ) notNullResult = result;
            }
            return notNullResult;
        }
    }

    private static SpanningTreeNode buildTree(Long rootID){
        List<SpanningTreeNode> treeNodeList = new ArrayList<SpanningTreeNode>();
        for (long i=0; i<Network.size(); i++) treeNodeList.add(new SpanningTreeNode(i));
        Set nodeInTreeSet = new HashSet<Long>();
        // 根节点加入树
        nodeInTreeSet.add(rootID);
        List<Long> queue = new ArrayList<Long>();
        queue.add(rootID);
        while (queue.size() > 0){
            Long currentID = queue.get(0);
            SpanningTreeNode currentTreeNode = treeNodeList.get(currentID.intValue());
            queue.remove(0);
            List<Node> neighborList = TopologyUtil.getNeighbors(Network.get(currentID.intValue()),protocolID);
            for ( Node node : neighborList){
                if ( !nodeInTreeSet.contains(node.getID())){
                    queue.add(node.getID());
                    nodeInTreeSet.add(node.getID());
                    currentTreeNode.addChild(treeNodeList.get(Long.valueOf(node.getID()).intValue()));
                }
            }
        }
        return treeNodeList.get(rootID.intValue());
    }
}
