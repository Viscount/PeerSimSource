package field.control;

import field.entity.Field;
import field.entity.FieldList;
import field.protocol.FieldBasedProtocol;
import field.util.JsonUtil;
import field.util.TopologyUtil;
import peersim.config.Configuration;
import peersim.core.Control;
import peersim.core.Network;
import peersim.core.Node;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

/**
 * Created by TongjiSSE on 2015/8/19.
 */
public class TopologyObserver implements Control{

    private static final String PAR_PROT_FBP = "field_protocol";
    private static final String PAR_FILE_NAME = "log_file_name";

    private static int pid_fbp;
    private static String filename;

    public TopologyObserver(String prefix){
        pid_fbp = Configuration.getPid(prefix+"."+PAR_PROT_FBP);
        filename = Configuration.getString(prefix+"."+PAR_FILE_NAME);
    }

    @Override
    public boolean execute() {
        try {
            FileWriter fwriter = new FileWriter(filename);
            for (int i = 0; i < Network.size(); i++) {
                Node currentNode = Network.get(i);
                List<Node> neighbors = TopologyUtil.getNeighbors(currentNode, pid_fbp);
                fwriter.write("Node "+i+":\r\n");
                fwriter.write("Neighbors : ");
                for ( Node node : neighbors){
                    fwriter.write(node.getID()+",");
                }
                fwriter.write("\r\n");
                FieldBasedProtocol fbp = (FieldBasedProtocol)currentNode.getProtocol(pid_fbp);
                fwriter.write("Field List : ");
                List<Field> fieldList = fbp.field.getFieldDetail();
                for ( Field field : fieldList ){
                    fwriter.write(JsonUtil.toJson(field)+"\r\n");
                }
            }
            fwriter.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
