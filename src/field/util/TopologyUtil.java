package field.util;

import peersim.config.FastConfig;
import peersim.core.Linkable;
import peersim.core.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TongjiSSE on 2015/7/14.
 */
public class TopologyUtil {

    // get the neighbors of a node in specific protocol
    public static List<Node> getNeighbors(Node node, int protocolID){
        List<Node> neighbors = new ArrayList<Node>();
        int linkableID = FastConfig.getLinkable(protocolID);
        Linkable linkable = (Linkable) node.getProtocol(linkableID);
        if ( linkable.degree() > 0 ){
            for (int i=0; i<linkable.degree(); i++){
                Node neighborNode = linkable.getNeighbor(i);
                neighbors.add(neighborNode);
            }
        }
        return neighbors;
    }
}
