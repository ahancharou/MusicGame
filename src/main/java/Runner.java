import game.Boy;
import game.Chair;

import java.util.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CyclicBarrier;

public class Runner {

    private static ConcurrentLinkedQueue<Chair> chairs;
    private static List<Boy> boys;

    public static void main(String [] args) {

        System.out.print("Enter players count: ");
        Scanner sc = new Scanner (System.in);
        int numOfBoys = sc.nextInt();
        if (numOfBoys>1){
            boys = new ArrayList<Boy>();
            for (int i = 1; i<numOfBoys+1; i++){
                Boy boy = new Boy(i);
                boys.add(boy);
                System.out.println("Created " + i + " boy");
            }
            CyclicBarrier barrier;
            int i = 1;
            Random random = new Random();
            while (boys.size() > 1){
                System.out.println("Round "+i++);
                barrier = new CyclicBarrier(boys.size()+1);
                initChairs(boys.size()-1);
                for (Boy b:boys){
                    b.setBarrier(barrier);
                    b.setChairs(chairs);
                    new Thread(b).start();
                }
                try {
                    System.out.println("Music started");
                    Thread.sleep(6000+random.nextInt(2000));
                    System.out.println("Music stopped!");
                    barrier.await();
                    System.out.println("Boys grabbing chairs!");
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                findLoser();
            }

        }
        System.out.println("And the winner is Boy #" + boys.get(0).number);
    }

    private static void initChairs (int size){

        if (chairs != null) {
            chairs.clear();
        } else
            chairs = new ConcurrentLinkedQueue<Chair>();
        for (int b = 0; b < size; b++){
            chairs.add(new Chair(b));
        }
    }

    private static void findLoser(){
        for (Iterator<Boy> iterator = boys.iterator(); iterator.hasNext();){
            Boy boy = iterator.next();
            if (!boy.isPlaying){
                iterator.remove();
                System.out.println("Loser found!");
            }
        }
    }
}
