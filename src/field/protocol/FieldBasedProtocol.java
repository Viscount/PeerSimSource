package field.protocol;

import field.entity.FieldList;
import field.entity.ResourceRepo;
import field.entity.message.Message;
import field.handler.Handler;
import field.handler.HandlerFactory;
import field.util.JsonUtil;
import peersim.config.Configuration;
import peersim.core.Node;
import peersim.edsim.EDProtocol;
import peersim.vector.SingleValueHolder;

/**
 * Created by TongjiSSE on 2015/7/12.
 */
public class FieldBasedProtocol extends SingleValueHolder implements EDProtocol{

    private static final String PAR_FORWARD_NUM = "forward_num";

    public static int forward_num;

    public FieldList field;
    public ResourceRepo resource;

    public FieldBasedProtocol(String prefix) {
        super(prefix);
        forward_num = Configuration.getInt(prefix+"."+PAR_FORWARD_NUM);
    }

    @Override
    public void processEvent(Node node, int pid, Object event) {
        Message message = new Message();
        message = JsonUtil.toObject((String)event,Message.class);

        Handler handler = HandlerFactory.createHandler(message.getType());
        if ( handler!=null ) handler.handleMessage(node,pid,message);

    }
}
