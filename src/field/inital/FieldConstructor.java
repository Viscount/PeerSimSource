package field.inital;

import field.entity.Field;
import field.entity.Resource;
import field.protocol.FieldBasedProtocol;
import peersim.config.Configuration;
import peersim.core.Control;
import peersim.core.Network;
import peersim.core.Node;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by TongjiSSE on 2015/8/17.
 */
public class FieldConstructor implements Control {

    private static final String PAR_PROT_FBP = "field_protocol";
    private static final String PAR_PROT_ICP = "interest_protocol";
    private static final String PAR_DECAY_RATE = "decay_rate";
    private static final String PAR_POTENTIAL_BOUNDER = "potential_bounder";

    private static int pid_fbp,pid_icp;
    private static double decay_rate;
    private static double potential_bounder;

    public FieldConstructor(String prefix){
        pid_fbp = Configuration.getPid(prefix + "," + PAR_PROT_FBP);
        pid_icp = Configuration.getPid(prefix+","+PAR_PROT_ICP);
        decay_rate = Configuration.getDouble(prefix + "," + PAR_DECAY_RATE);
        potential_bounder = Configuration.getDouble(prefix+","+PAR_POTENTIAL_BOUNDER);
    }

    @Override
    public boolean execute() {
        long counter = 0;
        for (int i=0; i< Network.size(); i++){
            Node currentNode = Network.get(i);
            FieldBasedProtocol currentFbp = (FieldBasedProtocol)currentNode.getProtocol(pid_fbp);
            Map<Long,Integer> currentInterestTypeList = currentFbp.resource.findAllInterestType();
            for (Iterator it = currentInterestTypeList.entrySet().iterator();it.hasNext();){
                Map.Entry entry = (Map.Entry)it.next();
                Field field = new Field();
                field.setFieldID(counter);
                field.setTypeID((long)entry.getKey());
                field.setSourceID(i);
                field.setDecayRate(decay_rate);
                field.setPotential((double) entry.getValue() / currentFbp.resource.size());
            }
        }
        return false;
    }
}
