package field.control;

import field.entity.Field;
import field.initial.FieldConstructor;
import field.initial.Initializer;
import field.protocol.FieldBasedProtocol;
import field.util.GlobalListener;
import peersim.config.Configuration;
import peersim.core.CommonState;
import peersim.core.Control;
import peersim.core.Network;
import peersim.core.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jaric Liao on 2015/9/7.
 */
public class FieldCalculator implements Control{

    private static final String PAR_PROT_FBP = "field_protocol";
    private static final String PAR_START_TIME = "start_time";
    private static final String PAR_END_TIME = "end_time";

    private static int pid_fbp;
    private static long start_time, end_time;

    public FieldCalculator(String prefix){
        pid_fbp = Configuration.getPid(prefix+"."+PAR_PROT_FBP);
        start_time = Configuration.getLong(prefix + "." + PAR_START_TIME);
        end_time = Configuration.getLong(prefix+"."+PAR_END_TIME);
    }

    @Override
    public boolean execute() {
        if (( CommonState.getTime() < start_time )||( CommonState.getTime() > end_time )) return false;
        for (int i=0; i< Network.size(); i++) {
            Node node = Network.get(i);
            FieldBasedProtocol fieldBasedProtocol = (FieldBasedProtocol) node.getProtocol(pid_fbp);
            List<Field> superFieldList = new ArrayList<Field>();
            for ( long interestID = 0; interestID < Initializer.interest_num; interestID++ ){
                List<Field> candidate = fieldBasedProtocol.field.findFieldForInterest(interestID);
                Field superField = new Field();
                if ( candidate.size() > 0){
                    superField.setFieldID(FieldConstructor.counter);
                    FieldConstructor.counter++;
                    superField.setSourceID(i);
                    superField.setTypeID(interestID);
                    superField.setPotential(0);
                    for ( Field singleField : candidate ){
                        superField.setPotential(superField.getPotential()+singleField.getPotential());
                    }
                    superFieldList.add(superField);
                }
            }
            fieldBasedProtocol.superposition.setFieldDetail(superFieldList);
        }
        return false;
    }
}
