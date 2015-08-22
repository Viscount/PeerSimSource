package field.handler;

import field.entity.message.QueryMessage;
import field.protocol.FieldBasedProtocol;
import field.util.CommonUtil;
import field.util.GlobalListener;
import field.util.JsonUtil;
import field.util.TopologyUtil;
import peersim.config.FastConfig;
import peersim.core.Node;
import peersim.transport.Transport;

import java.util.List;

/**
 * Created by TongjiSSE on 2015/8/22.
 */
public class RandomWalkQueryMessageHandler extends Handler{

    public static final int WALKERS = 3;

    @Override
    public void handleMessage(Node node, int protocolID, Object msg) {
        QueryMessage message = (QueryMessage)msg;
        if ( message.getTTL() < 0 ) return;
        long interestType = message.getInterestType();
        FieldBasedProtocol fieldProtocol = (FieldBasedProtocol)node.getProtocol(protocolID);

        // search in local repo
        List resultList = fieldProtocol.resource.findResultForQuery(message);
        GlobalListener.receiveQueryResult(message.getQueryID(), resultList);

        //find random neighbors to forword
        if ( message.getRequester() == node.getID() ){
            try {
                List<Node> neighborList = TopologyUtil.getNeighbors(node, protocolID);
                List<Node> nextHop = CommonUtil.randomPickFromArray(neighborList, WALKERS);
                if (nextHop.size() > 0) {
                    QueryMessage forward_mess = (QueryMessage) message.clone();
                    forward_mess.setTTL(message.getTTL() - 1);
                    String json = JsonUtil.toJson(forward_mess);
                    for ( Node nextNode : nextHop ){
                        ((Transport) node.getProtocol(FastConfig.getTransport(protocolID))).
                                send(node, nextNode, json, protocolID);
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }

        }
        else {
            try {
                List<Node> neighborList = TopologyUtil.getNeighbors(node, protocolID);
                List<Node> nextHop = CommonUtil.randomPickFromArray(neighborList, 1);
                if (nextHop.size() > 0) {
                    Node nextNode = nextHop.get(0);
                    QueryMessage forward_mess = (QueryMessage) message.clone();
                    forward_mess.setTTL(message.getTTL() - 1);
                    String json = JsonUtil.toJson(forward_mess);
                    ((Transport) node.getProtocol(FastConfig.getTransport(protocolID))).
                            send(node, nextNode, json, protocolID);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }


    }
}
