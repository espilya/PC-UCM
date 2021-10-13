package servidor;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class Locks {
	boolean lock;
	int n;

	Locks(int m) {
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

 class LockRompeEmpate extends Locks {
	int[] in;
	int[] last;

	LockRompeEmpate(int m) {
		super(m);
		in = new int[n + 1];
		last = new int[n + 1];
	}

	void takeLock(int i) {
		for (int j = 1; j < n; j++) {
			last[j] = i;
			in[i] = j;
			for (int k = 1; k < n && i != k; k++) {
				while (in[k] >= in[i] && last[j] == i) {
				}
			}
		}
	}

	void releaseLock(int i) {
		in[i] = 0;
	}
}

 class LockTicket extends Locks {
	int[] turn;
	AtomicInteger number = new AtomicInteger(1);;
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

 class LockBakery extends Locks {
	int[] turn;

	LockBakery(int m) {
		super(m);
		turn = new int[n + 1];
	}

	boolean cond(int a, int b, int c, int d) {
		return (a > c || a == c && b > d);
	}

	void takeLock(int i) {
		turn[i] = 1;
		turn[i] = max(turn) + 1;
		for (int j = 1; j < n && i != j; j++) {
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
