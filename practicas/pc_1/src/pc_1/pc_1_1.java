package pc_1;

import java.util.Vector;
import java.util.concurrent.CountDownLatch;

public class pc_1_1 {
	
	public static void main(String[] args) throws InterruptedException {
		int T = 5, N = 20;
		Vector<Thread> v = new Vector<Thread>();
		
		System.out.println("start");
		for(int i = 0; i < N;i++) {
			PrimeThread p = new PrimeThread(T);
			p.setName("Thread_" + i);
			v.add(p);
		    p.start();
		}
		boolean ok = false;
		for(int i = 0; i < N;i++) {
			v.get(i).join();
		}
		System.out.println("stop");
	}

}


class PrimeThread extends Thread {
    long t;
    PrimeThread(int t) {
      this.t = t;
    }

    public void run() {
	    try {
		      System.out.println(currentThread().getName());
			  Thread.sleep(this.t);
		      System.out.println(currentThread().getName());
	    } catch (InterruptedException e) {
	    	e.printStackTrace();
	    }
    }
}



