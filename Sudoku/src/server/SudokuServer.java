package server;

import java.io.IOException;
import java.net.ServerSocket;

import sudoku.SudokuSolver;

// Squelette hautement experimental !

public class SudokuServer implements Runnable {

	private ServerSocket ss;
	private int nbClients;
	private SudokuSolver solver;
	public static int NB_PLAYERS_MAX = 5;

	public SudokuServer(int port, SudokuSolver solver) throws IOException {
		this.ss = new ServerSocket(port);
		this.nbClients = 0;
		this.solver = solver;
	}

	@Override
	public void run() {
		try {
			while (true) {
				Thread t = new Thread(new ConnectionManager(this.ss.accept(), this));
				t.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		if (args.length != 2) {
			System.err.println("Usage : java SudokuServer port oneProblemFile");
			return;
		}
		
		int port = Integer.parseInt(args[0]);
		SudokuSolver solver = new SudokuSolver();
		solver.zero();
		solver.initFromFile(args[1]);
		solver.init();
		for(int i = 0; i < 10; i++){
			if (solver.estSolution(1, 1, i))
				break;
		}
		Thread t;
		try {
			t = new Thread(new SudokuServer(port, solver));
			t.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
