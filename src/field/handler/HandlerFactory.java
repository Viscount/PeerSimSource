package field.handler;

import field.protocol.FieldBasedProtocol;
import field.util.CommonUtil;

/**
 * Created by TongjiSSE on 2015/7/13.
 */
public class HandlerFactory {

    public static Handler createHandler(String handlerType){
        try {
            Class<?> handler = Class.forName("field.handler." + FieldBasedProtocol.route_method + CommonUtil.convert2SimpleName(handlerType)+"Handler");
            Handler instanceHandler = (Handler)handler.newInstance();
            return instanceHandler;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
