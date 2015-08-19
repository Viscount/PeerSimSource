package field.entity.message;

/**
 * Created by TongjiSSE on 2015/8/18.
 */
public class ClusterQueryMessage extends QueryMessage implements Cloneable{
    public ClusterQueryMessage(){
        this.set_class(ClusterQueryMessage.class.getName());
    }

    public ClusterQueryMessage clone() throws CloneNotSupportedException{
        return (ClusterQueryMessage)super.clone();
    }

}
