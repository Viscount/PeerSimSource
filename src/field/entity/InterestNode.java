package field.entity;

import java.util.List;

/**
 * Created by TongjiSSE on 2015/7/13.
 */
public class InterestNode {

    private long interestID;
    private double weight;
    private List<Integer> keywords;

    public InterestNode(long interestID) {
        this.interestID = interestID;
    }

    public long getInterestID() {
        return interestID;
    }

    public void setInterestID(long interestID) {
        this.interestID = interestID;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public List<Integer> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Integer> keywords) {
        this.keywords = keywords;
    }
}
