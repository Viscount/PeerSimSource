package field.initial;

import peersim.config.Configuration;
import peersim.core.CommonState;
import peersim.dynamics.WireGraph;
import peersim.graph.Graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TongjiSSE on 2015/8/11.
 */
public class WireKNode extends WireGraph{

    private static final String PAR_DEGREE = "k";

    private int k;

    public WireKNode(String prefix){
        super(prefix);
        k = Configuration.getInt(prefix+"."+PAR_DEGREE);
    }

    @Override
    public void wire(Graph g) {
        final int n = g.size();
        if( n < 2 ) return;
        if( n <= k ) k=n-1;
        List nodes = new ArrayList();
        int[] degree = new int[n];
        for(int i=0; i<n; i++) degree[i] = k;
        for(int i=0; i<n; i++)
        {
            if ( degree[i] == 0 ) continue;
            nodes.clear();
            for (int j=i+1; j<n; j++){
                if ( degree[j]>0 ) nodes.add(j);
            }
            if ( nodes.size()==0 ) continue;
            while( degree[i] > 0 )
            {
                if ( nodes.size()==0 ) break;
                int index = CommonState.r.nextInt(nodes.size());
                int nodeID = (int) nodes.get(index);
                g.setEdge(i, nodeID);
                g.setEdge(nodeID, i);
                degree[i]--;
                degree[nodeID]--;
                nodes.remove(index);
            }
        }
    }
}
