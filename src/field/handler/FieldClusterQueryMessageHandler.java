package field.handler;

import field.entity.message.ClusterQueryMessage;
import field.protocol.FieldBasedProtocol;
import field.protocol.InterestClusterProtocol;
import field.util.GlobalListener;
import field.util.JsonUtil;
import peersim.config.FastConfig;
import peersim.core.Node;
import peersim.transport.Transport;

import java.util.List;

/**
 * Created by Jaric Liao on 2015/9/7.
 */
public class FieldClusterQueryMessageHandler extends Handler{
    @Override
    public void handleMessage(Node node, int protocolID, Object msg) {
        ClusterQueryMessage message = (ClusterQueryMessage)msg;
        if ( message.getTTL() < 0 ) return;
        InterestClusterProtocol clusterProtocol = (InterestClusterProtocol)node.getProtocol(protocolID);
        FieldBasedProtocol localFieldProtocol = (FieldBasedProtocol)node.getProtocol(InterestClusterProtocol.field_pid);

        // search in local repo
        List resultList = localFieldProtocol.resource.findResultForQuery(message);
        GlobalListener.receiveQueryResult(message.getQueryID(), resultList);
        // TODO result process

        // forward in Interest Cluster Layer
        long interestTypeID = message.getInterestType();
        if ( clusterProtocol.getRingNeighborNode(interestTypeID)!= null ){
            try {
                Node nextHop = clusterProtocol.getRingNeighborNode(interestTypeID);
                ClusterQueryMessage forward_mess = (ClusterQueryMessage)message.clone();
                forward_mess.setTTL(message.getTTL()-1);
                String json = JsonUtil.toJson(forward_mess);
                ((Transport) node.getProtocol(FastConfig.getTransport(protocolID))).
                        send(node, nextHop, json, protocolID);
                GlobalListener.messageCounter++;
            }catch( Exception e ){
                e.printStackTrace();
            }
        }
        if ( clusterProtocol.getUpstreamNode(interestTypeID) != null ){
            try {
                Node nextHop = clusterProtocol.getUpstreamNode(interestTypeID);
                ClusterQueryMessage forward_mess = (ClusterQueryMessage)message.clone();
                forward_mess.setTTL(message.getTTL()-1);
                String json = JsonUtil.toJson(forward_mess);
                ((Transport) node.getProtocol(FastConfig.getTransport(protocolID))).
                        send(node, nextHop, json, protocolID);
                GlobalListener.messageCounter++;
            }catch( Exception e ){
                e.printStackTrace();
            }
        }
    }
}
