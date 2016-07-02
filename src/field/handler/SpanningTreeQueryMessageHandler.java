package field.handler;

import field.entity.SpanningTreeNode;
import field.entity.message.QueryMessage;
import field.protocol.FieldBasedProtocol;
import field.support.SpanningTreeInfo;
import field.util.GlobalListener;
import field.util.JsonUtil;
import peersim.config.FastConfig;
import peersim.core.Network;
import peersim.core.Node;
import peersim.transport.Transport;

import java.util.List;

/**
 * Created by Jaric Liao on 2016/6/25.
 */
public class SpanningTreeQueryMessageHandler extends Handler{
    @Override
    public void handleMessage(Node node, int protocolID, Object msg) {
        QueryMessage message = (QueryMessage)msg;
        if ( message.getTTL() < 0 ) return;
        long interestType = message.getInterestType();
        FieldBasedProtocol fieldProtocol = (FieldBasedProtocol)node.getProtocol(protocolID);

        // search in local repo
        List resultList = fieldProtocol.resource.findResultForQuery(message);
        GlobalListener.receiveQueryResult(message.getQueryID(), resultList);

        //find neighbors to forword
        List<SpanningTreeNode> neighbors = SpanningTreeInfo.findChildren(message.getRequester(),node.getID());
        if (neighbors == null || neighbors.size()==0) return;
        for ( SpanningTreeNode treeNode : neighbors){
            try {
                Node nextNode = Network.get(Long.valueOf(treeNode.getNodeId()).intValue());
                QueryMessage forward_mess = (QueryMessage) message.clone();
                forward_mess.setTTL(message.getTTL() - 1);
                GlobalListener.messageCounter++;
                String json = JsonUtil.toJson(forward_mess);
                ((Transport) node.getProtocol(FastConfig.getTransport(protocolID))).
                        send(node, nextNode, json, protocolID);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
