package field.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TongjiSSE on 2015/7/12.
 */
public class FieldList{

    private List<Field> fieldDetail;

    public FieldList(){
        this.fieldDetail = new ArrayList<Field>();
    }

    public FieldList(List<Field> fieldDetail) {
        this.fieldDetail = fieldDetail;
    }

    public List<Field> getFieldDetail() {
        return fieldDetail;
    }

    public void setFieldDetail(List<Field> fieldDetail) {
        this.fieldDetail = fieldDetail;
    }

    public Field get(int index){
        return fieldDetail.get(index);
    }

    public void add(Field field){
        this.fieldDetail.add(field);
        return;
    }

    public void remove(int index){
        this.fieldDetail.remove(index);
        return;
    }

    public int find(Field field){
        for (int i=0; i<fieldDetail.size(); i++){
            Field f = fieldDetail.get(i);
            if (f.equals(field)) return i;
        }
        return -1;
    }

    public List<Field> findFieldForInterest(int interestType){
        List<Field> fieldFilter = new ArrayList<Field>();
        for ( Field field : fieldDetail ){
            if ( field.getTypeID() == interestType ) fieldFilter.add(field);
        }
        return fieldFilter;
    }

    public Field findMaxFieldForInterest(int interestType){
        Field result = null;
        double maxPotential = 0;
        for ( Field field : fieldDetail ){
            if ( field.getPotential() > maxPotential ){
                maxPotential = field.getPotential();
                result = field;
            }
        }
        return result;
    }
}
