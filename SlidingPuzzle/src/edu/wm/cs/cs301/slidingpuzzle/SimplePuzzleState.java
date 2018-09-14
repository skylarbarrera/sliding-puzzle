package edu.wm.cs.cs301.slidingpuzzle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

public class SimplePuzzleState implements PuzzleState {
	
	public int[][] puzState = new int[4][4];
	public SimplePuzzleState parent;
	public SimplePuzzleState next = this;
	public Operation stateOperation;
	public int length = 0;
	public int[] notSet = {9,9};
	public int[] empty1 = {9,9};
	public int[] empty2 = {9,9};
	public int[] empty3 = {9,9};
	
	public void setEmpties() {
		for(int r = 0; r <= 3; r++) {
			for (int c = 0 ; c <= 3; c++) {
				if (puzState[r][c] == 0){
					if (Arrays.equals(empty1,notSet)) {
						empty1[0] = r;
						empty1[1] = c;
						System.out.println("empty1 set");
					}else if (Arrays.equals(empty2,notSet)) {
						empty2[0] = r;
						empty2[1] = c;
						System.out.println("empty2 set");
					} else if (Arrays.equals(empty3,notSet)) {
						empty3[0] = r;
						empty3[1] = c;
						System.out.println("empty3 set");
					} 
				}
			}
		}
	}
	
	
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
			puzState[dimension-1][dimension-1] = 0;
			puzState[dimension-1][dimension - 2] = 0;
		} else if (numberOfEmptySlots == 1) {
			puzState[dimension-1][dimension-1] = 0;
		
	
		}
		setEmpties();
		};

	

	@Override
	public int getValue(int row, int column) {
		return this.puzState[row][column];
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
		
		next.parent = this;
		
		if (movesArr[0] == 1) {
			System.out.println("move up");
			stateOperation = Operation.MOVEUP;
			next.puzState[row-1][column] =  next.puzState[row-1][column] ^ next.puzState[row][column] ^ ( next.puzState[row][column] = next.puzState[row-1][column] );
			
		}
		
		if (movesArr[1] == 1) {
			System.out.println("move down");
			stateOperation = Operation.MOVEDOWN;
			next.puzState[row+1][column] =  next.puzState[row+1][column] ^ next.puzState[row][column] ^ ( next.puzState[row][column] = next.puzState[row+1][column] );         
		}
		if (movesArr[2] == 1) {
			System.out.println("move left");
			stateOperation = Operation.MOVELEFT;
			next.puzState[row][column-1] =  next.puzState[row][column-1] ^ next.puzState[row][column] ^ ( next.puzState[row][column] = next.puzState[row][column-1] );         
		}
		if (movesArr[3] == 1) {
			System.out.println("move right");
			stateOperation = Operation.MOVERIGHT;
			next.puzState[row][column+1] =  next.puzState[row][column+1] ^ next.puzState[row][column] ^ ( next.puzState[row][column] = next.puzState[row][column+1] );         
		}
		int a = getRandomEmpty(2,1);
			/*
			System.out.println("check out the moms");
			System.out.println(java.util.Arrays.toString(next.puzState[0]));
			System.out.println(java.util.Arrays.toString(next.puzState[1]));
			System.out.println(java.util.Arrays.toString(next.puzState[2]));
			System.out.println(java.util.Arrays.toString(next.puzState[3]));
			*/
		
		
			
		
		return next;
	}
	
	public int[] possMoves(int row, int column) {
		// array to give all possible moves, {up, down, left , right}
		int[] possMovesArr = {0,0,0,0};
		
		//up
		if (row > 0) {
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
		if (column > 0) {
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
	
	
	public int[] emptyMoves(int row, int column) {
		// array to give all possible moves, {up, down, left , right}
		int[] possMovesArr = {0,0,0,0};
		
		//up
		if (row > 0) {
			if (puzState[row-1][column] != 0 ) {
			possMovesArr[0] = 1;
		}
		}
		//down
		if (row < 3) {
			if (puzState[row+1][column] != 0 ) {
				possMovesArr[1] = 1;
			}
		}
		//left
		if (column > 0) {
			if (puzState[row][column-1] != 0 ) {
				possMovesArr[2] = 1;
			}
		}
		//right
		if (column <  3) {
			if (puzState[row][column+1] != 0 ) {
				possMovesArr[3] = 1;
			}
			
		}
		System.out.println(java.util.Arrays.toString(possMovesArr));
		return possMovesArr;
	}

	
	@Override
	public PuzzleState drag(int startRow, int startColumn, int endRow, int endColumn) {
		//check if end cell is empty else return null
		if (puzState[endRow][endColumn] != 0) {
			return null;
		}
		int[] possMovesStart = possMoves(startRow, startColumn);
		boolean possMovesStartBool = IntStream.of(possMovesStart).anyMatch(x -> x == 1);
		if (!possMovesStartBool ) {
			return null;
		} 
		ArrayList<Integer> emptyLoad = new ArrayList<Integer>();
		ArrayList<Integer> moveLoad = dragHelper(startRow,startColumn,endRow,endColumn, emptyLoad);
		System.out.println("moveLoad");
		System.out.println(moveLoad);
		/*else {
			if (startRow == endRow) {
			if (startColumn > endColumn) {
				if(possMovesStart[2] == 1) {
					if(puzState[startRow][startColumn-1] == puzState[endRow][EndColumn]) {
						//only needs to move Left once
					} else if(puzState[startRow][startColumn-2] == puzState[endRow][EndColumn]){
							//moving twice
						} else if(puzState[startRow][startColumn-3] == puzState[endRow][EndColumn]) {
							//moving three times
						}
					}
				}
			} else if (startColumn < endColumn) {
				if(puzState[startRow][startColumn+1] == puzState[endRow][EndColumn]) {
					//only needs to move Right once
				} else if(puzState[startRow][startColumn+2] == puzState[endRow][EndColumn]){
						//moving twice
					} else if(puzState[startRow][startColumn+3] == puzState[endRow][EndColumn]) {
						//moving three times
					}
				}
			}
			}
		}	else if (startRow > endRow) {
				if (startColumn > endColumn) {
				
			} else if (startColumn < endColumn) {
				
			}
		} else {
			//startRow < endRow
		}
		
		*/
		
		
		return null;
	}
	
	public ArrayList<Integer> dragHelper(int sRow, int sCol, int eRow, int eCol, ArrayList<Integer> movesStack) {
		System.out.println("starting");
		if ((sRow == eRow)  && (sCol == eCol)) {
			System.out.println("at destination return");
			return movesStack;
		}else {
			int[] possMovesStart = possMoves(sRow, sCol);
			//boolean possMovesStartBool = IntStream.of(possMovesStart).anyMatch(x -> x == 1);
			
			if (sRow > eRow) {
				if (sCol > eCol) {
					// up & left
					if (possMovesStart[0] == 1) {
						movesStack.add(0);
						movesStack = dragHelper(sRow-1,sCol,eRow, eCol, movesStack);
					} 
					if (possMovesStart[2] == 1) {
						movesStack.add(2);
						movesStack = dragHelper(sRow,sCol-1,eRow, eCol, movesStack);
					}
					
				} else if (sCol < eCol) {
					//up & right
					if (possMovesStart[0] == 1) {
						movesStack.add(0);
						movesStack = dragHelper(sRow-1,sCol,eRow, eCol, movesStack);
					} 
					if (possMovesStart[3] == 1) {
						movesStack.add(3);
						movesStack = dragHelper(sRow,sCol+1,eRow, eCol, movesStack);
					}
					
				}else {
					if (possMovesStart[0] == 1) {
						movesStack.add(0);
						movesStack = dragHelper(sRow-1,sCol,eRow, eCol, movesStack);
					} 
				}
			} else if (sRow < eRow) {
				if (sCol > eCol) {
					//down & left
					if (possMovesStart[1] == 1) {
						movesStack.add(1);
						movesStack = dragHelper(sRow+1,sCol,eRow, eCol, movesStack);
					} 
					if (possMovesStart[2] == 1) {
						movesStack.add(2);
						movesStack = dragHelper(sRow,sCol-1,eRow, eCol, movesStack);
					}
					
				} else if (sCol < eCol) {
					//down & right
					if (possMovesStart[1] == 1) {
						movesStack.add(1);
						movesStack = dragHelper(sRow+1,sCol,eRow, eCol, movesStack);
					} 
					if (possMovesStart[3] == 1) {
						movesStack.add(3);
						movesStack = dragHelper(sRow,sCol+1,eRow, eCol, movesStack);
					}
					
				}else {
					//down
					if (possMovesStart[1] == 1) {
						movesStack.add(1);
						movesStack = dragHelper(sRow+1,sCol,eRow, eCol, movesStack);
					} 
				}
			} else {
				if (sCol > eCol) {
					//left
					if (possMovesStart[2] == 1) {
						movesStack.add(2);
						movesStack = dragHelper(sRow,sCol-1,eRow, eCol, movesStack);
					}
					
				} else if (sCol < eCol) {
					//right
					if (possMovesStart[3] == 1) {
						System.out.println("adding to ");
						System.out.println(movesStack);
						movesStack.add(3);
						System.out.println("next = added");
						System.out.println(movesStack);
						System.out.println("calling again");
						movesStack = dragHelper(sRow,sCol+1,eRow, eCol, movesStack);
					}
					
				}else {
					//were there already
				}
			}
		}
		System.out.println("About to return");
		System.out.println(movesStack);
		return movesStack;
	}
	
	public PuzzleState dragMover (int[] movesStack) {
		return this;
	}
	

	public int getRandomEmpty(double min, double max){
		int[] empty1moves = null;
		int[] empty2moves = null;
		int[] empty3moves = null;
		boolean empty1Poss = false;
		boolean empty2Poss = false;
		boolean empty3Poss = false;
		if ( empty1[0] != 9) {
			empty1moves = emptyMoves(empty1[0],empty1[1]);
			empty1Poss = IntStream.of(empty1moves).anyMatch(x -> x == 1);
		}
		if ( empty2[0] != 9) {
			empty2moves = emptyMoves(empty2[0],empty2[1]);
			empty2Poss = IntStream.of(empty2moves).anyMatch(x -> x == 1);
		}
		if ( empty3[0] != 9) {
			empty3moves = emptyMoves(empty3[0],empty3[1]);
			empty3Poss = IntStream.of(empty3moves).anyMatch(x -> x == 1);
		}
		
		
		System.out.println("Poss Moves - 1 2 3 " + empty1Poss + " " +empty2Poss + " " + empty3Poss);

		
		
		double x = (int)(Math.random()*((3-1)+1))+min;
		return 5;
}

	@Override
	public PuzzleState shuffleBoard(int pathLength) {
		int[][] empties = [2][3]
		if (length == pathLength) {
			return this;
		}
		else {
			for (int r = 0; r<4; r++) {
				for (int c = 0; c<4; c++) {
				
				}
			}
			int[] tMoves = possMoves()
		}
		
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
