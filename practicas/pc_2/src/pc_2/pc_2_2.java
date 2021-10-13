package pc_2;

import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

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

class LockTicket extends Lock {
	int[] turn;
	AtomicInteger number = new AtomicInteger(1);
	int next = 1;

	LockTicket(int m) {
		super(m);
		turn = new int[n + 1];
	}

	int fa(AtomicInteger var) {

		return var.getAndAdd(1);
	}

	void takeLock(int i) {
		turn[i] = fa(number);
		while (turn[i] != next) {
		}

	}

	void releaseLock(int i) {
		next++;
	}
}

class LockBakery extends Lock{
	int[] turn;
	LockBakery(int m) {
		super(m);
		turn = new int[n + 1];
	}
	
	boolean cond(int a, int b, int c, int d) {
		return (a>c || a == c && b>d);
	}
	
	void takeLock(int i) {
		turn[i] = 1; turn[i] = max(turn) + 1; 
		for(int j = 1; j < n && i!=j; j++) {
			while (turn[j] != 0 && cond(turn[i], i, turn[j], j)) {
			}
		}
	}

	void releaseLock(int i) {
		turn[i] = 0;
	}

	static int max(int[] t) {
		int maximum = t[0]; // start with the first value
		for (int i = 1; i < t.length; i++) {
			if (t[i] > maximum) {
				maximum = t[i]; // new maximum
			}
		}
		return maximum;
	}// end method max

}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////


public class pc_2_2 {

	public static void main(String[] args) throws InterruptedException {
		int M = 12, N = 58;
		Vector<Thread> v = new Vector<Thread>();
//		Lock lockType = new LockRompeEmpate(M*2);
//		Lock lockType = new LockTicket(M*2);
		Lock lockType = new LockBakery(M*2);
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
