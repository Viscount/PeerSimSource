package field.entity;

/**
 * Created by TongjiSSE on 2015/7/12.
 */
public class Field implements Comparable,Cloneable{

    private long fieldID; // ID of field
    private long sourceID; // ID of source node
    private long typeID; // ID of field interest type
    private double decayRate; // Decay rate
    private double potential; // Potential in this node

    public Field() {
    }

    public long getFieldID() {
        return fieldID;
    }

    public void setFieldID(long fieldID) {
        this.fieldID = fieldID;
    }

    public long getSourceID() {
        return sourceID;
    }

    public void setSourceID(long sourceID) {
        this.sourceID = sourceID;
    }

    public long getTypeID() {
        return typeID;
    }

    public void setTypeID(long typeID) {
        this.typeID = typeID;
    }

    public double getDecayRate() {
        return decayRate;
    }

    public void setDecayRate(double decayRate) {
        this.decayRate = decayRate;
    }

    public double getPotential() {
        return potential;
    }

    public void setPotential(double potential) {
        this.potential = potential;
    }

    @Override
    public int compareTo(Object o) {
        if ( this.potential > ((Field)o).getPotential() ) return 1;
        else if ( this.potential < ((Field)o).getPotential() ) return -1;
        return 0;
    }

    @Override
    public Field clone() throws CloneNotSupportedException {
        return (Field)super.clone();
    }
}
