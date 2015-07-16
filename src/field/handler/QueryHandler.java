package field.handler;

import field.entity.Field;
import field.entity.message.Message;
import field.protocol.FieldBasedProtocol;
import field.util.CommonUtil;
import field.util.JsonUtil;
import field.util.TopologyUtil;
import peersim.config.FastConfig;
import peersim.core.Network;
import peersim.core.Node;
import peersim.transport.Transport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TongjiSSE on 2015/7/14.
 */
public class QueryHandler extends Handler{

    @Override
    public void handleMessage(Node node, int protocolID, Message message) {
        int interestType = message.getInterestType();
        FieldBasedProtocol fieldProtocol = (FieldBasedProtocol)node.getProtocol(protocolID);

        // search in local repo
        List resultList = fieldProtocol.resource.findResultForQuery(message);
        // TODO result process


        // find the highest potential in neighbors
        List<Node> neighbors = TopologyUtil.getNeighbors(node,protocolID);
        List<Field> allCandidateField = new ArrayList<Field>();
        for ( Node neighbor : neighbors ){
            FieldBasedProtocol neighborProtocol = (FieldBasedProtocol)neighbor.getProtocol(protocolID);
            Field maxField = neighborProtocol.field.findMaxFieldForInterest(interestType);
            allCandidateField.add(maxField);
        }
        Field maxCandidate = CommonUtil.findMax(allCandidateField);

        // find the highest potential in local
        List<Field> localCandidateField = fieldProtocol.field.findFieldForInterest(interestType);
        Field localMaxField = CommonUtil.findMax(localCandidateField);

        if ( localMaxField.compareTo(maxCandidate) > 0 ){
            // reach a publisher
            // TODO switch message type
        }
        else {
            // find the highest top n potential in neighbors
            try {
                List<Field> candidateNextHop = CommonUtil.findTopN(allCandidateField, FieldBasedProtocol.forward_num);
                Message forward_mess = message.clone();
                forward_mess.setTTL(message.getTTL() - 1);
                String json = JsonUtil.toJson(forward_mess);
                for (Field field : candidateNextHop) {
                    long nodeID = field.getSourceID();
                    Node nexthop = Network.get(Integer.parseInt(Long.toString(nodeID)));
                    ((Transport) node.getProtocol(FastConfig.getTransport(protocolID))).
                            send(node, nexthop, json, protocolID);
                }
            } catch ( Exception e){
                e.printStackTrace();
            }
        }

    }
}
