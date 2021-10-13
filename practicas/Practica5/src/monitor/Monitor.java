package monitor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {
	private int data[];
	private int ini, fin;
	private int cP, cC;
	private final ReentrantLock mutex;
	private final Condition condP, condC;

	public Monitor(int n) {
		mutex = new ReentrantLock();
		condP = mutex.newCondition();
		condC = mutex.newCondition();
		ini = 0;
		fin = 0;
		cP = 0;
		cC = 0;
		data = new int[n];
		for (int i = 0; i < n; i++) {
			data[i] = 0;
		}
	}

	public void pick(int num) {
		mutex.lock();
		cC++;

		while (((fin >= ini) ? fin - ini : data.length - ini + fin) < num) {
			try {
				condC.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		cC--;

		// Pick whatever
		System.out.println("He sacado " + num + " datos. ");
		showTotal();
		for (int i = ini; i < num + ini; i++) {
			data[i % data.length] = 0;
			System.out.println(data[i % data.length]);
		}

		ini = (ini + num) % data.length;

		resume();

		mutex.unlock();
	}

	public void insert(int num) {
		mutex.lock();

		cP++;
		while (((fin >= ini) ? fin - ini : data.length - ini + fin) + num > data.length) {
			try {
				condP.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		cP--;

		// Put whatever

		System.out.println("He metido " + num + " datos. ");
		showTotal();
		for (int i = ini; i < num + ini; i++) {
			data[i % data.length] = (i % data.length) + 1;
			System.out.println(data[i % data.length]);
		}

		fin = (fin + num) % data.length;

		resume();

		mutex.unlock();
	}

	public void resume() {
		mutex.lock();
		if (cC > 0) {
			condC.signal();
		} else if (cP > 0) {
			condP.signal();
		}
		mutex.unlock();
	}

	public void showTotal() {
		System.out.print("[");
		for (int i = 0; i < data.length; i++) {
			System.out.print(" " + data[i]);
		}
		System.out.print("]\n");
	}
}