package field.protocol;

import field.entity.message.Message;
import field.handler.Handler;
import field.handler.HandlerFactory;
import field.util.JsonUtil;
import peersim.config.Configuration;
import peersim.core.Node;
import peersim.edsim.EDProtocol;
import peersim.vector.SingleValueHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by TongjiSSE on 2015/7/16.
 */
public class InterestClusterProtocol extends SingleValueHolder implements EDProtocol {

    private static final String PAR_PROT_FBP = "field_protocol";

    public static int field_pid;

    private Map<Long,Double> corePotential;
    private Map<Long, Node> upstreamNode;
    private Map<Long, Node> ringNeighborNode;


    public InterestClusterProtocol(String prefix) {
        super(prefix);
        field_pid = Configuration.getPid(prefix+"."+PAR_PROT_FBP);
    }

    @Override
    public void processEvent(Node node, int pid, Object event) {
        try {
            String className = JsonUtil.getClassName((String) event);
            Class clazz = Class.forName(className);
            Object message = JsonUtil.toObject((String) event, clazz);

            Handler handler = HandlerFactory.createHandler(className);
            if (handler != null) handler.handleMessage(node, pid, message);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void init(){
        this.corePotential = new HashMap();
        this.upstreamNode = new HashMap();
        this.ringNeighborNode = new HashMap();
    }

    public boolean containsCorePotential(long interestTypeID){
        return corePotential.containsKey(interestTypeID);
    }

    public double getCorePotential(long interestTypeID){
        return corePotential.get(interestTypeID);
    }

    public void setCorePotential(long interestTypeID, double corePotential){
        this.corePotential.put(interestTypeID,corePotential);
    }

    public Node getUpstreamNode(long interestTypeID) {
        return upstreamNode.get(interestTypeID);
    }

    public void setUpstreamNode(long interestTypeID, Node upstreamNode) {
        this.upstreamNode.put(interestTypeID, upstreamNode);
    }

    public Node getRingNeighborNode(long interestTypeID) {
        return ringNeighborNode.get(interestTypeID);
    }

    public void setRingNeighborNode(long interestTypeID, Node ringNeighborNode) {
        this.ringNeighborNode.put(interestTypeID,ringNeighborNode);
    }
}
