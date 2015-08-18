package field.handler;

import field.entity.Field;
import field.entity.message.PushMessage;
import field.inital.FieldConstructor;
import field.protocol.FieldBasedProtocol;
import field.util.JsonUtil;
import field.util.TopologyUtil;
import peersim.config.FastConfig;
import peersim.core.Node;
import peersim.transport.Transport;

import java.util.List;

/**
 * Created by TongjiSSE on 2015/8/18.
 */
public class PushMessageHandler extends Handler{
    @Override
    public void handleMessage(Node node, int protocolID, Object msg) {
        PushMessage message = (PushMessage)msg;
        FieldBasedProtocol fieldProtocol = (FieldBasedProtocol)node.getProtocol(protocolID);
        Field msgField = message.getField();
        boolean isUpdate = false;
        int isExist = fieldProtocol.field.find(msgField);
        if ( isExist == -1 ){
            fieldProtocol.field.add(message.getField());
            isUpdate = true;
        }
        else{
            Field currentField = fieldProtocol.field.get(isExist);
            if ( currentField.getPotential() < msgField.getPotential() ){
                fieldProtocol.field.remove(isExist);
                fieldProtocol.field.add(msgField);
                isUpdate = true;
            }
        }

        try {
            if ( isUpdate ) {
                Field newField = msgField.clone();
                newField.setPotential(msgField.getPotential()*msgField.getDecayRate());
                if ( newField.getPotential() < FieldConstructor.potential_bounder ) return;
                PushMessage newPushMessage = message.clone();
                newPushMessage.setField(newField);
                String json = JsonUtil.toJson(newPushMessage);
                List<Node> neighbors = TopologyUtil.getNeighbors(node, protocolID);
                for ( Node nextNode : neighbors ){
                    ((Transport)node.getProtocol(FastConfig.getTransport(protocolID))).
                            send(node, nextNode, json, protocolID);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
