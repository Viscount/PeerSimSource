package field.handler;

import field.entity.message.QueryMessage;
import field.protocol.FieldBasedProtocol;
import field.util.GlobalListener;
import field.util.JsonUtil;
import field.util.TopologyUtil;
import peersim.config.FastConfig;
import peersim.core.CommonState;
import peersim.core.Node;
import peersim.transport.Transport;

import java.util.List;

/**
 * Created by TongjiSSE on 2015/8/23.
 */
public class MBFSQueryMessageHandler extends Handler{

    public static final double prob = 0.3;

    @Override
    public void handleMessage(Node node, int protocolID, Object msg) {
        QueryMessage message = (QueryMessage)msg;
        if ( message.getTTL() < 0 ) return;
        long interestType = message.getInterestType();
        FieldBasedProtocol fieldProtocol = (FieldBasedProtocol)node.getProtocol(protocolID);

        // search in local repo
        List resultList = fieldProtocol.resource.findResultForQuery(message);
        GlobalListener.receiveQueryResult(message.getQueryID(), resultList);

        // forward message with a specific probability
        try {
            List<Node> neighborList = TopologyUtil.getNeighbors(node, protocolID);
            if ( neighborList.size() > 0 ){
                QueryMessage forward_mess = (QueryMessage) message.clone();
                forward_mess.setTTL(message.getTTL() - 1);
                String json = JsonUtil.toJson(forward_mess);
                for ( Node nextNode : neighborList ){
                    if (CommonState.r.nextFloat() > prob ) {
                        ((Transport) node.getProtocol(FastConfig.getTransport(protocolID))).
                                send(node, nextNode, json, protocolID);
                        GlobalListener.messageCounter++;
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
