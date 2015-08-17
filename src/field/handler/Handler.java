package field.handler;

import field.entity.message.Message;
import peersim.core.Node;

/**
 * Created by TongjiSSE on 2015/7/13.
 */
public abstract class Handler {

    public abstract void handleMessage(Node node,int protocolID, Object message);
}
