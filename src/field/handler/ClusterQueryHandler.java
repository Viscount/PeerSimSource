package field.handler;

import field.entity.message.Message;
import field.protocol.FieldBasedProtocol;
import field.protocol.InterestClusterProtocol;
import field.util.JsonUtil;
import peersim.config.FastConfig;
import peersim.core.Node;
import peersim.transport.Transport;

import java.util.List;

/**
 * Created by TongjiSSE on 2015/7/16.
 */
public class ClusterQueryHandler extends Handler{
    @Override
    public void handleMessage(Node node, int protocolID, Message message) {
        InterestClusterProtocol clusterProtocol = (InterestClusterProtocol)node.getProtocol(protocolID);
        FieldBasedProtocol localFieldProtocol = (FieldBasedProtocol)node.getProtocol(InterestClusterProtocol.field_pid);

        // search in local repo
        List resultList = localFieldProtocol.resource.findResultForQuery(message);
        // TODO result process

        // forward in Interest Cluster Layer
        if ( clusterProtocol.getRingNeighborNode()!= null ){
            try {
                Node nextHop = clusterProtocol.getRingNeighborNode();
                Message forward_mess = message.clone();
                forward_mess.setTTL(message.getTTL()-1);
                String json = JsonUtil.toJson(forward_mess);
                ((Transport) node.getProtocol(FastConfig.getTransport(protocolID))).
                        send(node, nextHop, json, protocolID);
            }catch( Exception e ){
                e.printStackTrace();
            }
        }
        if ( clusterProtocol.getUpstreamNode() != null ){
            try {
                Node nextHop = clusterProtocol.getUpstreamNode();
                Message forward_mess = message.clone();
                forward_mess.setTTL(message.getTTL()-1);
                String json = JsonUtil.toJson(forward_mess);
                ((Transport) node.getProtocol(FastConfig.getTransport(protocolID))).
                        send(node, nextHop, json, protocolID);
            }catch( Exception e ){
                e.printStackTrace();
            }
        }
    }
}
