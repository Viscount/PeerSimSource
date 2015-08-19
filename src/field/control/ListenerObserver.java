package field.control;

import field.util.GlobalListener;
import peersim.core.Control;

/**
 * Created by TongjiSSE on 2015/8/19.
 */
public class ListenerObserver implements Control{

    public ListenerObserver(String prefix) {

    }

    @Override
    public boolean execute() {
        GlobalListener.calculate();
        System.out.println("Num of Result:" + GlobalListener.num_of_results.getAverage() + " " + GlobalListener.num_of_results.getStD());
        System.out.println("First hit time:" + GlobalListener.first_hit_time.getAverage() + " " + GlobalListener.first_hit_time.getStD());
        System.out.println("First full hit time:" + GlobalListener.first_full_hit_time.getAverage() + " " + GlobalListener.first_full_hit_time.getStD());
        return false;
    }
}
