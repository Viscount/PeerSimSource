package field.initial;

import field.entity.Field;
import field.entity.message.PushMessage;
import field.protocol.FieldBasedProtocol;
import field.protocol.InterestClusterProtocol;
import field.util.JsonUtil;
import peersim.config.Configuration;
import peersim.config.FastConfig;
import peersim.core.Control;
import peersim.core.Network;
import peersim.core.Node;
import peersim.transport.Transport;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by TongjiSSE on 2015/8/17.
 */
public class FieldConstructor implements Control {

    private static final String PAR_PROT_FBP = "field_protocol";
    private static final String PAR_PROT_ICP = "interest_protocol";
    private static final String PAR_DECAY_RATE = "decay_rate";
    private static final String PAR_POTENTIAL_BOUNDER = "potential_bounder";
    private static final String PAR_PUSH_MESSAGE_TTL = "push_message_ttl";
    private static final String PAR_CLUSTER_LAYER_STEP = "cluster_layer_step";

    private static int pid_fbp,pid_icp;
    private static double decay_rate;
    public static double potential_bounder;
    public static int push_message_ttl;
    private static double cluster_layer_step;

    public FieldConstructor(String prefix){
        pid_fbp = Configuration.getPid(prefix + "." + PAR_PROT_FBP);
        pid_icp = Configuration.getPid(prefix+"."+PAR_PROT_ICP);
        decay_rate = Configuration.getDouble(prefix + "." + PAR_DECAY_RATE);
        potential_bounder = Configuration.getDouble(prefix+"."+PAR_POTENTIAL_BOUNDER);
        push_message_ttl = Configuration.getInt(prefix + "." + PAR_PUSH_MESSAGE_TTL);
        cluster_layer_step = Configuration.getDouble(prefix + "." + PAR_CLUSTER_LAYER_STEP);
    }

    @Override
    public boolean execute() {
        long counter = 0;
        Map<Long,Node> interestClusterEntrance = new HashMap();
        for (int i=0; i< Network.size(); i++){
            Node currentNode = Network.get(i);
            FieldBasedProtocol currentFbp = (FieldBasedProtocol)currentNode.getProtocol(pid_fbp);
            InterestClusterProtocol currentIcp = (InterestClusterProtocol)currentNode.getProtocol(pid_icp);
            currentIcp.init();
            // calculate field in every node
            Map<Long,Integer> currentInterestTypeList = currentFbp.resource.findAllInterestType();
            for (Iterator it = currentInterestTypeList.entrySet().iterator();it.hasNext();){
                Map.Entry entry = (Map.Entry)it.next();
                long interestTypeID = (long) entry.getKey();

                Field field = new Field();
                field.setFieldID(counter);
                counter++;
                field.setTypeID(interestTypeID);
                field.setSourceID(i);
                field.setDecayRate(decay_rate);
                field.setPotential( (int)entry.getValue()*1.0 / currentFbp.resource.size());

                currentFbp.interestTree.setInterestWeight(interestTypeID,field.getPotential());
                // add node to interest cluster
                currentIcp.setCorePotential(interestTypeID, field.getPotential());
                if ( interestClusterEntrance.containsKey(interestTypeID) ){
                    Node iteratorNode = interestClusterEntrance.get(interestTypeID);
                    InterestClusterProtocol iteratorIcp = (InterestClusterProtocol)iteratorNode.getProtocol(pid_icp);
                    // add node in entrance
                    if (iteratorIcp.getCorePotential(interestTypeID) > field.getPotential()+cluster_layer_step){
                        currentIcp.setUpstreamNode(interestTypeID,iteratorNode);
                        interestClusterEntrance.put(interestTypeID,currentNode);
                    }
                    else{
                        Node upstreamNode = iteratorIcp.getUpstreamNode(interestTypeID);
                        // upstream node isn't exist
                        while ( upstreamNode != null ){
                            InterestClusterProtocol upstreamIcp = (InterestClusterProtocol)upstreamNode.getProtocol(pid_icp);
                            if ( upstreamIcp.getCorePotential(interestTypeID) > field.getPotential()+cluster_layer_step ) break;
                            // next iteration
                            iteratorNode = upstreamNode;
                            iteratorIcp = (InterestClusterProtocol)iteratorNode.getProtocol(pid_icp);
                            upstreamNode = iteratorIcp.getUpstreamNode(interestTypeID);
                        }
                        if ( field.getPotential() > iteratorIcp.getCorePotential(interestTypeID)+cluster_layer_step){
                            iteratorIcp.setUpstreamNode(interestTypeID,currentNode);
                            currentIcp.setUpstreamNode(interestTypeID,upstreamNode);
                        }
                        else {
                            currentIcp.setUpstreamNode(interestTypeID,iteratorIcp.getUpstreamNode(interestTypeID));
                            currentIcp.setRingNeighborNode(interestTypeID,iteratorIcp.getRingNeighborNode(interestTypeID));
                            iteratorIcp.setRingNeighborNode(interestTypeID,currentNode);
                        }
//                        if( upstreamNode!=null ) {
//                            // add new layer
//                            if ( field.getPotential() > iteratorIcp.getCorePotential(interestTypeID)+cluster_layer_step){
//                                iteratorIcp.setUpstreamNode(interestTypeID,currentNode);
//                                currentIcp.setUpstreamNode(interestTypeID,upstreamNode);
//                            }
//                            else{
//                                //add in current layer
//                                while (iteratorIcp.getRingNeighborNode(interestTypeID)!=null){
//                                    iteratorNode = iteratorIcp.getRingNeighborNode(interestTypeID);
//                                    iteratorIcp = (InterestClusterProtocol)iteratorNode.getProtocol(pid_icp);
//                                }
//                                iteratorIcp.setRingNeighborNode(interestTypeID,currentNode);
//                                currentIcp.setUpstreamNode(interestTypeID,iteratorIcp.getUpstreamNode(interestTypeID));
//                            }
//                        }
//                        else{
//                            iteratorIcp.setUpstreamNode(interestTypeID,currentNode);
//                        }
                    }
                }
                else {
                    interestClusterEntrance.put(field.getTypeID(),currentNode);
                }

                // generate push message
                PushMessage pushMessage = new PushMessage();
                pushMessage.setRequester(i);
                pushMessage.setInterestType((long) entry.getKey());
                pushMessage.setTTL(push_message_ttl);
                pushMessage.setField(field);

                String json = JsonUtil.toJson(pushMessage);
                ((Transport) currentNode.getProtocol(FastConfig.getTransport(pid_fbp))).
                        send(currentNode, currentNode, json, pid_fbp);
            }
        }

        return false;
    }
}
