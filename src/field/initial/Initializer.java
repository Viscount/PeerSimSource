package field.initial;

import field.entity.FieldList;
import field.entity.ResourceRepo;
import field.protocol.FieldBasedProtocol;
import field.support.InterestDb;
import field.support.KeywordDb;
import field.support.ResourceDb;
import field.util.GlobalListener;
import peersim.config.Configuration;
import peersim.core.Control;
import peersim.core.Network;
import peersim.core.Node;

/**
 * Created by TongjiSSE on 2015/8/11.
 */
public class Initializer implements Control{

    private static final String PAR_PROT_FBP = "field_protocol";

    private static final String PAR_INTEREST_PER_NODE = "interest_per_node";
    private static final String PAR_RESOURCE_PER_NODE = "resource_per_node";
    private static final String PAR_INTEREST_NUM = "interest_num";
    private static final String PAR_RESOURCE_NUM = "resource_num";
    private static final String PAR_KEYWORD_PER_INTEREST = "keyword_per_interest";
    private static final String PAR_KEYWORD_PER_RESOURCE = "keyword_per_resource";

    private static int pid_fbp;
    private static int interest_per_node, resource_per_node;
    public static int interest_num, resource_num;
    private static int keyword_per_interest, keyword_per_resource;

    public Initializer(String prefix){
        pid_fbp = Configuration.getPid(prefix+"."+PAR_PROT_FBP);
        interest_per_node = Configuration.getInt(prefix + "." + PAR_INTEREST_PER_NODE);
        resource_per_node = Configuration.getInt(prefix + "." + PAR_RESOURCE_PER_NODE);
        interest_num = Configuration.getInt(prefix+"."+PAR_INTEREST_NUM);
        resource_num = Configuration.getInt(prefix+"."+PAR_RESOURCE_NUM);
        keyword_per_interest = Configuration.getInt(prefix+"."+PAR_KEYWORD_PER_INTEREST);
        keyword_per_resource = Configuration.getInt(prefix+"."+PAR_KEYWORD_PER_RESOURCE);
    }

    @Override
    public boolean execute() {
        GlobalListener.init();
        KeywordDb.init();
        KeywordDb.generate(interest_num * keyword_per_interest-1);
        InterestDb.init();
        InterestDb.generate(interest_num, keyword_per_interest);
        ResourceDb.init();
        ResourceDb.generate(resource_num,keyword_per_resource);
        for (int i=0; i< Network.size(); i++){
            Node node = Network.get(i);
            FieldBasedProtocol fieldBasedProtocol = (FieldBasedProtocol)node.getProtocol(pid_fbp);
            fieldBasedProtocol.interestTree = InterestDb.generateTreeForNode(interest_per_node);
            fieldBasedProtocol.resource = new ResourceRepo();
            fieldBasedProtocol.resource.setResourceList(ResourceDb.generateRepoForNode(fieldBasedProtocol.interestTree, resource_per_node));
            ResourceDb.addExistingResource(fieldBasedProtocol.resource.getResourceList());
            fieldBasedProtocol.field = new FieldList();
            fieldBasedProtocol.superposition = new FieldList();
        }
        return false;
    }
}
