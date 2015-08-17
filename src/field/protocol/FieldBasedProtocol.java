package field.protocol;

import field.entity.FieldList;
import field.entity.InterestTree;
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

    public InterestTree interestTree;
    public FieldList field;
    public ResourceRepo resource;

    public FieldBasedProtocol(String prefix) {
        super(prefix);
        forward_num = Configuration.getInt(prefix+"."+PAR_FORWARD_NUM);
    }

    @Override
    public void processEvent(Node node, int pid, Object event){
        try {
            String className = JsonUtil.getClassName((String) event);
            Class clazz = Class.forName(className);
            Object message = JsonUtil.toObject((String) event, clazz.getClass());

            Handler handler = HandlerFactory.createHandler(className);
            if (handler != null) handler.handleMessage(node, pid, message);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
