package field.entity.message;

/**
 * Created by TongjiSSE on 2015/8/18.
 */
public class ClusterQueryMessage extends QueryMessage implements Cloneable{
    public ClusterQueryMessage(){
        this.set_class(ClusterQueryMessage.class.getName());
    }

    public ClusterQueryMessage(QueryMessage queryMessage){
        this.set_class(ClusterQueryMessage.class.getName());
        this.setRequester(queryMessage.getRequester());
        this.setQueryID(queryMessage.getQueryID());
        this.setInterestType(queryMessage.getInterestType());
        this.setTTL(queryMessage.getTTL());
        this.setQueryKeywords(queryMessage.getQueryKeywords());
    }

    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }

}
