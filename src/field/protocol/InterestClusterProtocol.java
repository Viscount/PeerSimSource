package field.protocol;

import field.entity.message.Message;
import field.handler.Handler;
import field.handler.HandlerFactory;
import field.util.JsonUtil;
import peersim.config.Configuration;
import peersim.core.Node;
import peersim.edsim.EDProtocol;
import peersim.vector.SingleValueHolder;

/**
 * Created by TongjiSSE on 2015/7/16.
 */
public class InterestClusterProtocol extends SingleValueHolder implements EDProtocol {

    private static final String PAR_FIELD_PID = "field_pid";

    public static int field_pid;

    private Node upstreamNode;
    private Node ringNeighborNode;


    public InterestClusterProtocol(String prefix) {
        super(prefix);
        field_pid = Configuration.getInt(prefix+"."+PAR_FIELD_PID);
    }

    @Override
    public void processEvent(Node node, int pid, Object event) {
        Message message = JsonUtil.toObject((String) event, Message.class);

        Handler handler = HandlerFactory.createHandler(message.getType());
        if ( handler!=null ) handler.handleMessage(node,pid,message);

    }

    public Node getUpstreamNode() {
        return upstreamNode;
    }

    public void setUpstreamNode(Node upstreamNode) {
        this.upstreamNode = upstreamNode;
    }

    public Node getRingNeighborNode() {
        return ringNeighborNode;
    }

    public void setRingNeighborNode(Node ringNeighborNode) {
        this.ringNeighborNode = ringNeighborNode;
    }
}
