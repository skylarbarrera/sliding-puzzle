package edu.wm.cs.cs301.slidingpuzzle;

public class SimplePuzzleState implements PuzzleState {
	
	public int[][] puzState = new int[4][4];
	public SimplePuzzleState parent;
	public SimplePuzzleState next;
	public Operation stateOperation;
	public int length = 0;
	
	
	
	
	@Override
	public void setToInitialState(int dimension, int numberOfEmptySlots) {
		int count = 1;
		for(int r = 0; r <= dimension-1; r++) {
			for (int c = 0 ; c <= dimension-1; c++) {
				puzState[r][c] = count;
				count++;
			}
				
		}
		System.out.println("Puzzle Reset");
		if (numberOfEmptySlots == 3) {
			puzState[dimension-1][dimension-1] = 0;
			puzState[dimension-1][dimension- 2] = 0;
			puzState[dimension-1][dimension - 3] = 0;
		} else if (numberOfEmptySlots == 2) {
			puzState[dimension-1][dimension] = 0;
			puzState[dimension-1][dimension - 2] = 0;
		} else if (numberOfEmptySlots == 1) {
			puzState[dimension-1][dimension-1] = 0;
	
		}
		};

	

	@Override
	public int getValue(int row, int column) {
		// TODO Auto-generated method stub
		return puzState[row][column];
	}

	@Override
	public PuzzleState getParent() {
		// Should Work, may need extra functionality 
		return parent;
	}

	@Override
	public Operation getOperation() {
		// Should Work, may need extra functionality 
		return stateOperation;
	}

	@Override
	public int getPathLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	/**
	 * Checks if it is possible to move a tile from the given position in the given direction.
	 * If it is, the method returns a new instance of PuzzleState where the parent state is set
	 * to this object, i.e. the current state before the move, the operation is set to op and 
	 * the state is set to the state that results from the move operation. The operation also
	 * sets length of the path from the initial state to the new state. Note that this length
	 * only increases with move operations regardless of the distance to the initial state.
	 * If the move is not possible, the method returns null.
	 * @param row is an index in the range 0,1,..., dimension-1
	 * @param column is an index in the range 0,1,..., dimension-1
	 * @param op gives an operation such as move left or move up.
	 * @return new PuzzleState for legal move. For illegal move operation it returns null.
	 */
	@Override
	public PuzzleState move(int row, int column, Operation op) {
		int[] movesArr = possMoves(row, column);
		int nullsend = 0;
		for (int i = 0; i<4; i++) {
			if (movesArr[i] == 0) {
				nullsend++;
			}
		}
		if (nullsend == 0) {
			return null;
		}
		
		next.puzState = puzState;
		
		if (movesArr[0] == 1) {
			System.out.println("move up");
			puzState[row-1][column] =  puzState[row-1][column] ^ puzState[row][column] ^ ( puzState[row][column] = puzState[row-1][column] );         
		}
		
		if (movesArr[1] == 1) {
			System.out.println("move down");
			puzState[row+1][column] =  puzState[row+1][column] ^ puzState[row][column] ^ ( puzState[row][column] = puzState[row+1][column] );         
		}
		
			System.out.println("check out the moms");
			System.out.println(java.util.Arrays.toString(puzState[0]));
			System.out.println(java.util.Arrays.toString(puzState[1]));
			System.out.println(java.util.Arrays.toString(puzState[2]));
			System.out.println(java.util.Arrays.toString(puzState[3]));

		
		
			
		
		// case 1 - 2 moves
		
		// case 2 - 3 moves 
		
		// case 3 - 4 moves
		
		return next;
	}
	
	public int[] possMoves(int row, int column) {
		// array to give all possible moves, {up, down, left , right}
		int[] possMovesArr = {0,0,0,0};
		
		//up
		if (row > 1) {
			if (puzState[row-1][column] == 0 ) {
			possMovesArr[0] = 1;
		}
		}
		//down
		if (row < 3) {
			if (puzState[row+1][column] == 0 ) {
				possMovesArr[1] = 1;
			}
		}
		//left
		if (column > 1) {
			if (puzState[row][column-1] == 0 ) {
				possMovesArr[2] = 1;
			}
		}
		//right
		if (column <  3) {
			if (puzState[row][column+1] == 0 ) {
				possMovesArr[3] = 1;
			}
			
		}
		System.out.println(java.util.Arrays.toString(possMovesArr));
		return possMovesArr;
	}

	@Override
	public PuzzleState drag(int startRow, int startColumn, int endRow, int endColumn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuzzleState shuffleBoard(int pathLength) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty(int row, int column) {
		if (puzState[row][column] == 0) {
			return true;
		};
		return false;
	}

	@Override
	public PuzzleState getStateWithShortestPath() {
		// TODO Auto-generated method stub
		return null;
	}

}
