package pc_3;

import java.util.Vector;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


// clases 23, 24

class Var_2 {
	public volatile int counter = 0;
	public int N;
	public Lock state;
	

	Var_2(Lock lockType, int n) {
		state = lockType;
		counter = 0;
		this.N = n;
	}
}

abstract class Lock {
	boolean lock;
	int n;

	Lock(int m) {
		n = m;
		lock = false;
	}

	void takeLock(int i) {
		lock = true;
	}

	void releaseLock(int i) {
		lock = false;
	}
}

class LockRompeEmpate extends Lock {
	int[] in;
	int[] last;
	LockRompeEmpate(int m) {
		super(m);
		in =  new int[n+1];
		last = new int[n+1];
	}
	
	void takeLock(int i) {
		for (int j = 1; j < n; j++) {
			last[j] = i;
			in[i] = j;
			for(int k = 1; k < n && i!=k; k++) {
				while(in[k] >= in[i] && last[j] == i) {
				}
			}
		}
	}

	void releaseLock(int i) {
		in[i] = 0;
	}
}

class t extends Lock {
//	 AtomicInteger mutex;
	 Semaphore sem;
	t(int m) {
		super(m);
//		mutex = new AtomicInteger(1);;
		sem = new Semaphore(1);
	}
	
	// P
	void takeLock(int i) {
		 try {
			sem.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//<await (s>0) s=s-1>;
//		while(mutex.get() <= 0); 
//		mutex.decrementAndGet();

	}
	
	// V
	void releaseLock(int i) {
		//<s=s+1>;
//		mutex.addAndGet(1);
		 sem.release();
		
	}
}


/////////////////////////////////////////////////////////////////////////////////////////////////////////////


public class pc_3_1 {

	public static void main(String[] args) throws InterruptedException {
		int M = 6, N = 80;
		Vector<Thread> v = new Vector<Thread>();
		Lock lockType = new t(M*2);
//		Lock lockType = new LockTicket(M*2);
//		Lock lockType = new LockBakery(M*2);
		Var_2 var = new Var_2(lockType, N);
		
		System.out.println("start");
		
		for (int i = 0; i < M * 2; i++) {
			Thread p;
			if (i % 2 == 0)
				p = new Inc_2(var, i+1);
			else
				p = new Dec_2(var, i+1);

			v.add(p);
		}
		
		for (int i = 0; i < M * 2; i++) 
			v.get(i).start();
		
		for (int i = 0; i < M * 2; i++) 
			v.get(i).join();
		
		System.out.println("stop");
		System.out.println(var.counter);
	}
}

//Apuntes de tema 3:
//bool lock=false;
//process CS1
//while (true) {
//	<await (!lock) lock=true;>
//	SC;
//	lock=false;
//	no SC;
//}

class Inc_2 extends Thread {
	Var_2 var;
	int i;

	Inc_2(Var_2 var, int i) {
		this.var = var;
		this.i = i;
	}

	public void run() {
		while (var.state.lock);
		var.state.takeLock(i);
		CS();
		var.state.releaseLock(i);
		System.out.println(var.counter);
	}

	void CS() {
		for (int i = 0; i < var.N; i++) {
			var.counter++;
		}
	}
}

class Dec_2 extends Thread {
	Var_2 var;
	int i;

	Dec_2(Var_2 var, int i) {
		this.var = var;
		this.i = i;
	}

	public void run() {
		while (var.state.lock);
		var.state.takeLock(i);
		CS();
		var.state.releaseLock(i);
		System.out.println(var.counter);
	}

	void CS() {
		for (int i = 0; i < var.N; i++) {
			var.counter--;
		}
	}
}
