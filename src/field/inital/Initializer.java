package field.inital;

import field.util.GlobalListener;
import peersim.config.Configuration;
import peersim.core.Control;

/**
 * Created by TongjiSSE on 2015/8/11.
 */
public class Initializer implements Control{

    private static final String PAR_PROT_FBP = "field_protocol";
    private static final String PAR_PROT_ICP = "interest_protocol";

    private static int pid_fbp,pid_icp;

    public Initializer(String prefix){
        pid_fbp = Configuration.getPid(prefix+","+PAR_PROT_FBP);
        pid_icp = Configuration.getPid(prefix+","+PAR_PROT_ICP);
    }

    @Override
    public boolean execute() {
        GlobalListener.init();

        return false;
    }
}
