package pc_1;

import java.util.Vector;

class Var {
	int var;
	Var(){
		var = 0;
	}
	public void inc(){
		var++;
	}
	public void dec(){
		var--;
	}
	
	public int show() {
		return var;
	}
}

public class pc_1_2 {
	
	public static void main(String[] args) throws InterruptedException {
		int T = 5, N = 2000;
		Vector<Thread> v = new Vector<Thread>();
		Var var = new Var();
		
		System.out.println("start");
		for(int i = 0; i < N;i++) {
			Thread p;
			if(i%2==0) {
				p = new Inc(T, var);
			}
			else {
				p = new Dec(T, var);
			}
			v.add(p);
		    p.start();
		}
		boolean ok = false;
		for(int i = 0; i < N;i++) {
			v.get(i).join();
		}
		System.out.println("stop");
		System.out.println(var.show());
	}

}

class Inc extends Thread {
    long t;
    Var var;
    Inc(int t, Var var) {
      this.t = t;
      this.var = var;
    }
    public void run() {
    	var.inc();
    	System.out.println(var.show());
    }
}

class Dec extends Thread {
    long t;
    Var var;
    Dec(int t, Var var) {
      this.t = t;
      this.var = var;
    }
    public void run() {
    	var.dec();
    	System.out.println(var.show());
    }
}
