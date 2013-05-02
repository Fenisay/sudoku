package sudoku;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SudokuSolver {
	// ATTENTION:
	// les lignes et colonnes 0 ne sont pas utilisées
	int[][] T = new int[10][10];
	int[][] TInit = new int[10][10];

	public SudokuSolver() {
	}

	public void zero() {
		int i, j;

		for (i = 1; i <= 9; i++)
			for (j = 1; j <= 9; j++) {
				T[i][j] = 0;
				TInit[i][j] = 0;
			}
	}

	// valeurs non nulles
	public void init() {
		int i, j;

		for (i = 1; i <= 9; i++)
			for (j = 1; j <= 9; j++) {
				T[i][j] = TInit[i][j];
			}
	}

	// on teste l'unicite d'une valeur dans son petit carre
	boolean estUniqueDansPetitCarre(int x, int y, int v) {
		int origineXCarre, origineYCarre;

		origineXCarre = ((x - 1) / 3) * 3 + 1;
		origineYCarre = ((y - 1) / 3) * 3 + 1;

		int i, j;

		for (i = origineXCarre; i <= origineXCarre + 2; i++)
			for (j = origineYCarre; j <= origineYCarre + 2; j++) {
				if ((i == x) && (j == y))
					continue;
				if (T[i][j] == v)
					return false;
			}

		return true;
	}

	// on teste la validite d'une valeur à une position donnee
	boolean estValide(int x, int y, int v) {
		if (T[x][y] != 0)
			return (v == T[x][y]);

		int i, j;

		for (i = 1; i <= 9; i++) {
			if (i == x)
				continue;
			if (T[i][y] == v)
				return false;
		}

		for (j = 1; j <= 9; j++) {
			if (j == y)
				continue;
			if (T[x][j] == v)
				return false;
		}

		return estUniqueDansPetitCarre(x, y, v);
	}

	public boolean estSolution(int x, int y, int v) {
		// System.out.println (x + " " + y + " " + v);

		if (!estValide(x, y, v))
			return false;

		// on essaye
		T[x][y] = v;

		// on a trouve UNE solution; on s'arrete la!
		if ((x == 9) && (y == 9))
			return true;

		// appel recursif
		int i;
		if (y == 9) {
			for (i = 1; i <= 9; i++)
				if (estSolution(x + 1, 1, i))
					return true;

			// la solution proposee ne marche pas; backtrack
			if (TInit[x][y] == 0)
				T[x][y] = 0;
			return false;
		}

		for (i = 1; i <= 9; i++)
			if (estSolution(x, y + 1, i))
				return true;

		// la solution proposee ne marche pas; backtrack
		if (TInit[x][y] == 0)
			T[x][y] = 0;
		return false;
	}

	void print() {
		int i, j;

		for (i = 1; i <= 9; i++) {
			for (j = 1; j <= 9; j++)
				System.out.print("T[" + i + "," + j + "]=" + T[i][j] + " ");

			System.out.println("");
		}
	}

	public void initFromFile(String inputFile) {
		int i, j;

		try {
			BufferedReader buff = new BufferedReader(new FileReader(inputFile));

			try {
				String line;
				// Lecture du fichier ligne par ligne.
				for (i = 1; i <= 9; i++) {
					line = buff.readLine();
					for (j = 1; j <= 9; j++)
						TInit[i][j] = Character.getNumericValue(line
								.charAt(j - 1));
				}
			} finally {
				// dans tous les cas, on ferme nos flux
				buff.close();
			}
		} catch (IOException ioe) {
			// erreur de fermeture des flux
			System.out.println("Erreur --" + ioe.toString());
		}
	}

	public static void main(String args[]) {
		if (args.length != 1) {
			System.out.println("Usage: java Sudoku oneProblemFle");
			System.exit(0);
		}

		String inputFile = args[0];

		SudokuSolver S = new SudokuSolver();

		S.zero();

		S.initFromFile(inputFile);

		// avec GUI
		// SudokuGUI SGUI = new SudokuGUI (S);

		// sans GUI

		int i;
		S.init();
		for (i = 1; i <= 9; i++) {
			System.out.println("i=" + i);
			if (S.estSolution(1, 1, i))
				S.print();
		}
	}
}
