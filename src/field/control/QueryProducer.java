package field.control;

import field.entity.InterestNode;
import field.entity.message.QueryMessage;
import field.support.InterestDb;
import field.util.CommonUtil;
import field.util.GlobalListener;
import field.util.JsonUtil;
import field.util.QueryListener;
import peersim.config.Configuration;
import peersim.config.FastConfig;
import peersim.core.CommonState;
import peersim.core.Control;
import peersim.core.Network;
import peersim.core.Node;
import peersim.transport.Transport;

import java.util.List;

/**
 * Created by TongjiSSE on 2015/8/17.
 */
public class QueryProducer implements Control{

    private static final String PAR_PROT_FBP = "field_protocol";
    private static final String PAR_START_TIME = "start_time";
    private static final String PAR_QUERY_PER_CYCLE = "query_per_cycle";

    private static int pid_fbp;
    private static long start_time;
    private static int query_per_cycle;

    public QueryProducer(String prefix){
        pid_fbp = Configuration.getPid(prefix+"."+PAR_PROT_FBP);
        start_time = Configuration.getLong(prefix + "." + PAR_START_TIME);
        query_per_cycle = Configuration.getInt(prefix + "." + PAR_QUERY_PER_CYCLE);
    }

    @Override
    public boolean execute() {
        if ( CommonState.getTime() < start_time ) return false;
        for (int i=0; i<query_per_cycle; i++){
            QueryMessage queryMessage = new QueryMessage();
            queryMessage.setQueryID(GlobalListener.queryIDCounter);
            queryMessage.setTTL(CommonUtil.MAX_MESSAGE_TTL);
            // select query content
            long interestTypeID = InterestDb.randomSelectOneInterest();
            InterestNode interestNode = InterestDb.getInterestById(interestTypeID);
            List queryKeywords = CommonUtil.randomPickFromArray(interestNode.getKeywords(),CommonUtil.MAX_NUM_QUERY_KEYWORDS);
            queryMessage.setInterestType(interestTypeID);
            queryMessage.setQueryKeywords(queryKeywords);
            // select source
            int nodeId = CommonState.r.nextInt(Network.size());
            Node queryNode = Network.get(nodeId);
            queryMessage.setRequester(nodeId);
            String json = JsonUtil.toJson(queryMessage);
            ((Transport) queryNode.getProtocol(FastConfig.getTransport(pid_fbp))).
                    send(queryNode, queryNode, json, pid_fbp);
            // add listener
            QueryListener queryListener = new QueryListener(CommonState.getTime());
            queryListener = CommonUtil.findFullSet(queryListener, queryMessage);
            GlobalListener.addNewListener(GlobalListener.queryIDCounter, queryListener);
            GlobalListener.queryIDCounter++;
        }
        return false;
    }
}
