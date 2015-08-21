package field.entity.message;

import field.entity.Field;

/**
 * Created by TongjiSSE on 2015/8/17.
 */
public class PushMessage extends Message implements Cloneable{

    private Field field;

    public PushMessage(){
        this.set_class(PushMessage.class.getName());
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
