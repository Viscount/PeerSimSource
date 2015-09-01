package field.control;

import field.initial.Initializer;
import field.protocol.InterestClusterProtocol;
import field.util.TopologyUtil;
import peersim.config.Configuration;
import peersim.core.Control;
import peersim.core.Network;
import peersim.core.Node;

import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by TongjiSSE on 2015/8/27.
 */
public class ClusterObserver implements Control{

    private static final String PAR_PROT_ICP = "cluster_protocol";
    private static final String PAR_FILE_NAME = "log_file_name";

    private static int pid_icp;
    private static String filename;

    public ClusterObserver(String prefix){
        pid_icp = Configuration.getPid(prefix + "." + PAR_PROT_ICP);
        filename = Configuration.getString(prefix+"."+PAR_FILE_NAME);
    }

    @Override
    public boolean execute() {
        try{
            FileWriter fwriter = new FileWriter(filename);
            Map<Long,Double> minPotential = new HashMap<Long,Double>();
            Map<Long,Node> minNode = new HashMap<Long,Node>();
            for (int i = 0; i < Network.size(); i++) {
                Node currentNode = Network.get(i);
                InterestClusterProtocol currentICP = (InterestClusterProtocol)currentNode.getProtocol(pid_icp);
                for ( long interest = 0; interest< Initializer.interest_num; interest++){
                    if ( currentICP.containsCorePotential(interest) ){
                        if ( minPotential.containsKey(interest) ){
                            if ( currentICP.getCorePotential(interest) < minPotential.get(interest)){
                                minPotential.put(interest,currentICP.getCorePotential(interest));
                                minNode.put(interest,currentNode);
                            }
                        }
                        else {
                            minPotential.put(interest,currentICP.getCorePotential(interest));
                            minNode.put(interest,currentNode);
                        }
                    }
                }
            }

            for ( long interest = 0; interest< Initializer.interest_num; interest++){
                if ( minNode.containsKey(interest)){
                    Node rootNode = minNode.get(interest);
                    InterestClusterProtocol rootICP = (InterestClusterProtocol)rootNode.getProtocol(pid_icp);

                }
            }

        } catch ( Exception e ){
            e.printStackTrace();
        }
        return false;
    }
}
