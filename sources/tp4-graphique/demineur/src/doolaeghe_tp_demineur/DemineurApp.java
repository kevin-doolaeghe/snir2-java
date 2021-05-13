package doolaeghe_tp_demineur;

import java.awt.GridLayout;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class DemineurApp extends JFrame {
	private static int ROWS = 10, COLUMNS = 10, BOMBS = 10;

	private Case[][] cases;
	private int[][] casesState;
	private int[][] bombs;

	public DemineurApp() {
		cases = new Case[ROWS][COLUMNS];
		casesState = new int[ROWS][COLUMNS];
		bombs = new int[BOMBS][3];

		this.setTitle("Démineur");
		this.setSize(720, 480);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(ROWS, COLUMNS, 2, 2));

		for (int row = 0; row < ROWS; row++) {
			for (int column = 0; column < COLUMNS; column++) {
				cases[row][column] = new Case();
				this.getContentPane().add(cases[row][column]);
			}
		}

		this.setVisible(true);
	}

	public void start() {
		for (int id = 0; id < BOMBS; id++) {
			bombs[id][0] = id;
			bombs[id][1] = -1;
			bombs[id][2] = -1;
		}

		for (int id = 0; id < BOMBS; id++) {
			do {
				bombs[id][1] = (int) Math.random() * (ROWS - 1);
				bombs[id][2] = (int) Math.random() * (COLUMNS - 1);
			} while (isRowTaken(bombs[id][1]) && isColumnTaken(bombs[id][2]));
		}

		for (int row = 0; row < ROWS; row++) {
			for (int column = 0; column < COLUMNS; column++) {
				if (!isBomb(row, column)) {
					int nbAdjacentBombs = 0;

					if (row == 0) {
						if (column == 0) { // Top left
							if (isBomb(row + 1, column))
								nbAdjacentBombs++;
							if (isBomb(row, column + 1))
								nbAdjacentBombs++;
							if (isBomb(row + 1, column + 1))
								nbAdjacentBombs++;
						} else if (column == COLUMNS - 1) { // Top right
							if (isBomb(row - 1, column))
								nbAdjacentBombs++;
							if (isBomb(row - 1, column + 1))
								nbAdjacentBombs++;
							if (isBomb(row, column + 1))
								nbAdjacentBombs++;
						} else { // Top
							if (isBomb(row - 1, column))
								nbAdjacentBombs++;
							if (isBomb(row + 1, column))
								nbAdjacentBombs++;
							if (isBomb(row - 1, column + 1))
								nbAdjacentBombs++;
							if (isBomb(row, column + 1))
								nbAdjacentBombs++;
							if (isBomb(row + 1, column + 1))
								nbAdjacentBombs++;
						}
					} else if (row == ROWS - 1) {
						if (column == 0) { // Bottom left
							if (isBomb(row + 1, column - 1))
								nbAdjacentBombs++;
							if (isBomb(row + 1, column))
								nbAdjacentBombs++;
							if (isBomb(row, column + 1))
								nbAdjacentBombs++;
						} else if (column == COLUMNS - 1) { // Bottom right
							if (isBomb(row - 1, column - 1))
								nbAdjacentBombs++;
							if (isBomb(row, column - 1))
								nbAdjacentBombs++;
							if (isBomb(row - 1, column))
								nbAdjacentBombs++;
						} else { // Bottom
							if (isBomb(row - 1, column - 1))
								nbAdjacentBombs++;
							if (isBomb(row, column - 1))
								nbAdjacentBombs++;
							if (isBomb(row + 1, column - 1))
								nbAdjacentBombs++;
							if (isBomb(row - 1, column))
								nbAdjacentBombs++;
							if (isBomb(row + 1, column))
								nbAdjacentBombs++;
						}
					} else {
						if (column == 0) { // Left
							if (isBomb(row, column - 1))
								nbAdjacentBombs++;
							if (isBomb(row + 1, column - 1))
								nbAdjacentBombs++;
							if (isBomb(row + 1, column))
								nbAdjacentBombs++;
							if (isBomb(row, column + 1))
								nbAdjacentBombs++;
							if (isBomb(row + 1, column + 1))
								nbAdjacentBombs++;
						} else if (column == COLUMNS - 1) { // Right
							if (isBomb(row - 1, column - 1))
								nbAdjacentBombs++;
							if (isBomb(row, column - 1))
								nbAdjacentBombs++;
							if (isBomb(row - 1, column))
								nbAdjacentBombs++;
							if (isBomb(row - 1, column + 1))
								nbAdjacentBombs++;
							if (isBomb(row, column + 1))
								nbAdjacentBombs++;
						} else { // Middle
							if (isBomb(row - 1, column - 1))
								nbAdjacentBombs++;
							if (isBomb(row, column - 1))
								nbAdjacentBombs++;
							if (isBomb(row + 1, column - 1))
								nbAdjacentBombs++;
							if (isBomb(row - 1, column))
								nbAdjacentBombs++;
							if (isBomb(row + 1, column))
								nbAdjacentBombs++;
							if (isBomb(row - 1, column + 1))
								nbAdjacentBombs++;
							if (isBomb(row, column + 1))
								nbAdjacentBombs++;
							if (isBomb(row + 1, column + 1))
								nbAdjacentBombs++;
						}
					}

					casesState[row][column] = nbAdjacentBombs;
					cases[row][column].setState(nbAdjacentBombs);
				} else {
					casesState[row][column] = -1;
					cases[row][column].setState(-1);
				}
			}
		}
	}

	private boolean isRowTaken(int row) {
		boolean isRowTaken = false;

		for (int id = 0; id < BOMBS; id++) {
			if (bombs[id][1] == row)
				isRowTaken = true;
		}

		return isRowTaken;
	}

	private boolean isColumnTaken(int column) {
		boolean isColumnTaken = false;

		for (int id = 0; id < BOMBS; id++) {
			if (bombs[id][2] == column)
				isColumnTaken = true;
		}

		return isColumnTaken;
	}

	private boolean isBomb(int row, int column) {
		boolean isBomb = false;

		for (int id = 0; id < BOMBS; id++) {
			if (bombs[id][1] == row && bombs[id][2] == column)
				isBomb = true;
		}

		return isBomb;
	}
}
