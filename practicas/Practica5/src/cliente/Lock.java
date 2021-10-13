package cliente;


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
