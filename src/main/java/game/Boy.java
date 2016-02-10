package game;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CyclicBarrier;

public class Boy implements Runnable{

    private CyclicBarrier barrier;

    private ConcurrentLinkedQueue<Chair> chairs;

    public final int number;

    public boolean isPlaying = true;

    public Boy (int i){
        number = i;
    }

    public void setBarrier(CyclicBarrier barrier){
        this.barrier = barrier;
    }

    public void setChairs(ConcurrentLinkedQueue<Chair> chairs){
        this.chairs = chairs;
    }

    public void run() {
        try {
            System.out.println(number +" running!");
            barrier.await();
            //grab chair
            isPlaying = (chairs.poll() != null);
            System.out.println(number +" has chair? "+isPlaying);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean equals(Object boy){
        return number == ((Boy) boy).number;
    }
}
