package pc_2;

import java.util.Vector;

class Var_1 {
	public volatile int counter = 0;
	public int M = 1000;
	public boolean lock = false;

	Var_1() {
		counter = 0;
	}

}

public class pc_2_1 {

	public static void main(String[] args) throws InterruptedException {
		int T = 5, N = 2;
		Vector<Thread> v = new Vector<Thread>();
		Var_1 var = new Var_1();

		Thread p;
		p = new Inc_1(T, var);
		v.add(p);
		p = new Dec_1(T, var);
		v.add(p);

		System.out.println("start");
		for (int i = 0; i < N; i++) {
			v.get(i).start();
		}

		for (int i = 0; i < N; i++) {
			v.get(i).join();
		}
		System.out.println("stop");
		System.out.println(var.counter);
	}
}

//Apuntes de tema 3:
//	bool lock=false;
//	process CS1
//	while (true) {
//		<await (!lock) lock=true;>
//		SC;
//		lock=false;
//		no SC;
//	}

class Inc_1 extends Thread {
	long t;
	Var_1 var;

	Inc_1(int t, Var_1 var) {
		this.t = t;
		this.var = var;
	}

	public void run() {
//		await (!lock) lock=true;>
		while (var.lock);
		var.lock = true;
//		SC;
		CS();
//		lock=false;
		var.lock = false;
//		no SC;
		System.out.println(var.counter);
	}

	void CS() {
		// <CS>
		for (int i = 0; i < var.M; i++) {
			var.counter++;
		}
		// </CS>
	}
}

class Dec_1 extends Thread {
	long t;
	Var_1 var;

	Dec_1(int t, Var_1 var) {
		this.t = t;
		this.var = var;
	}

	public void run() {
		while (var.lock);
		var.lock = true;
		CS();
		var.lock = false;
		System.out.println(var.counter);
	}

	void CS() {
		for (int i = 0; i < var.M; i++) {
			var.counter--;
		}
	}
}
