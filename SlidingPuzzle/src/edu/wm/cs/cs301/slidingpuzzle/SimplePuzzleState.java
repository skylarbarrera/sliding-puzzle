package edu.wm.cs.cs301.slidingpuzzle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

public class SimplePuzzleState implements PuzzleState {
	
	public int[][] puzState;
	public SimplePuzzleState parent;
	public SimplePuzzleState next = this;
	public Operation stateOperation;
	public int dimen = 0;
	public int length = 0;
	public int empties;
	public int[] notSet = {9,9};
	public int[] empty1 = {9,9};
	public int[] empty2 = {9,9};
	public int[] empty3 = {9,9};
	
	public void setEmpties(int dimension) {
		//reset empty locations for relocation
		empty1[0] = 9;
		empty1[1] = 9;
		empty2[0] = 9;
		empty2[1] = 9;
		empty3[0] = 9;
		empty3[1] = 9;
		
		for(int r = 0; r <= dimension-1; r++) {
			for (int c = 0 ; c <= dimension-1; c++) {
				if (puzState[r][c] == 0){
					if (Arrays.equals(empty1,notSet)) {
						empty1[0] = r;
						empty1[1] = c;
						
					}else if (Arrays.equals(empty2,notSet)) {
						empty2[0] = r;
						empty2[1] = c;
						
					} else if (Arrays.equals(empty3,notSet)) {
						empty3[0] = r;
						empty3[1] = c;
						
					} 
				}
			}
		}
	}
	
	
	@Override
    public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof SimplePuzzleState)) {
			System.out.println("Objects not the same instance");
			return false;
		}
		
		SimplePuzzleState state = (SimplePuzzleState)obj;
		if (!java.util.Arrays.deepEquals(state.puzState,this.puzState)) {
			//System.out.println("Puzstates are not the same");
			return false;
		}
		
		if (this.hashCode() == obj.hashCode()) {
			System.out.println("same hash");
			return true;
		}
		System.out.println("not equal");
		return false;
		
	}
	
	@Override
	public int hashCode() {
		int hasher = 31;
		hasher = dimen * 12 * hasher  + empty1[0] - length ;
		System.out.println(hasher);
		return hasher;
	}
	
	
	
	@Override
	public void setToInitialState(int dimension, int numberOfEmptySlots) {
		dimen = dimension;
		empties = numberOfEmptySlots;
		puzState = new int[dimension][dimension];
		int count = 1;
		parent = null;
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
		setEmpties(dimension);
		};

	

	@Override
	public int getValue(int row, int column) {

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
		return length;
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
		System.out.println("possMoves of " + row + " " + column);
		int[] movesArr = possMoves(row, column);
	
		
		boolean possMovesStartBool = IntStream.of(movesArr).anyMatch(x -> x == 1);
	
		if (!possMovesStartBool) {
			return null;
		}
		
		if (movesArr[0] == 1) {
			op = Operation.MOVEUP;
		} else if (movesArr[1] == 1) {
			op = Operation.MOVEDOWN;
		} else if (movesArr[2] == 1) {
			op = Operation.MOVELEFT;
		} else if (movesArr[3] == 1) {
			op = Operation.MOVERIGHT;
		} 
		
		
		//may not be giving the right parent
		next.parent = this;
		
		if (op == Operation.MOVEUP) {
			System.out.println("move up");
			stateOperation = Operation.MOVEUP;
		
			next.puzState[row-1][column] =  next.puzState[row-1][column] ^ next.puzState[row][column] ^ ( next.puzState[row][column] = next.puzState[row-1][column] );
			next.length++;
		}
		
		if (op == Operation.MOVEDOWN) {
			System.out.println("move down");
			stateOperation = Operation.MOVEDOWN;
			next.puzState[row+1][column] =  next.puzState[row+1][column] ^ next.puzState[row][column] ^ ( next.puzState[row][column] = next.puzState[row+1][column] );         
			next.length++;
		}
		if (op == Operation.MOVELEFT) {
			System.out.println("move left");
			stateOperation = Operation.MOVELEFT;
			next.puzState[row][column-1] =  next.puzState[row][column-1] ^ next.puzState[row][column] ^ ( next.puzState[row][column] = next.puzState[row][column-1] );         
			next.length++;
		}
		if (op == Operation.MOVERIGHT) {
			System.out.println("move right");
			stateOperation = Operation.MOVERIGHT;
			next.puzState[row][column+1] =  next.puzState[row][column+1] ^ next.puzState[row][column] ^ ( next.puzState[row][column] = next.puzState[row][column+1] );         
			next.length++;
		}
		
			/*
			System.out.println("check out the moms");
			System.out.println(java.util.Arrays.toString(next.puzState[0]));
			System.out.println(java.util.Arrays.toString(next.puzState[1]));
			System.out.println(java.util.Arrays.toString(next.puzState[2]));
			System.out.println(java.util.Arrays.toString(next.puzState[3]));
			*/
		
		
		//refresh empty locations 	
		setEmpties(dimen);
		return next;
	}
	
	public int[] possMoves(int row, int column) {
		// array to give all possible moves, {up, down, left , right}
		int[] possMovesArr = {0,0,0,0};
		
		if (row == 9 ) {
			return possMovesArr;
		}
		//up
		if (row > 0) {
			if (puzState[row-1][column] == 0 ) {
				possMovesArr[0] = 1;
		}
		}
		//down
		if (row < dimen-1) {
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
		if (column <  dimen-1) {
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
		if (row == 9) {
			return possMovesArr;
		}
		
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
		PuzzleState dragged = dragMoveCaller(moveLoad,this);
		
		
		
		return dragged;
	}
	
	public ArrayList<Integer> dragHelper(int sRow, int sCol, int eRow, int eCol, ArrayList<Integer> movesStack) {
		if ((sRow == eRow)  && (sCol == eCol)) {
			return movesStack;
		}else {
			int[] possMovesStart = possMoves(sRow, sCol);
			//boolean possMovesStartBool = IntStream.of(possMovesStart).anyMatch(x -> x == 1);
			
			if (sRow > eRow) {
				if (sCol > eCol) {
					// up & left
					if (possMovesStart[0] == 1) {
						movesStack.add(0);
						movesStack.add(sRow);
						movesStack.add(sCol);
						movesStack = dragHelper(sRow-1,sCol,eRow, eCol, movesStack);
					} 
					if (possMovesStart[2] == 1) {
						movesStack.add(2);
						movesStack.add(sRow);
						movesStack.add(sCol);
						movesStack = dragHelper(sRow,sCol-1,eRow, eCol, movesStack);
					}
					
				} else if (sCol < eCol) {
					//up & right
					if (possMovesStart[0] == 1) {
						movesStack.add(0);
						movesStack.add(sRow);
						movesStack.add(sCol);
						movesStack = dragHelper(sRow-1,sCol,eRow, eCol, movesStack);
					} 
					if (possMovesStart[3] == 1) {
						movesStack.add(3);
						movesStack.add(sRow);
						movesStack.add(sCol);
						movesStack = dragHelper(sRow,sCol+1,eRow, eCol, movesStack);
					}
					
				}else {
					//up
					if (possMovesStart[0] == 1) {
						movesStack.add(0);
						movesStack.add(sRow);
						movesStack.add(sCol);
						movesStack = dragHelper(sRow-1,sCol,eRow, eCol, movesStack);
					} 
				}
			} else if (sRow < eRow) {
				if (sCol > eCol) {
					//down & left
					if (possMovesStart[1] == 1) {
						movesStack.add(1);
						movesStack.add(sRow);
						movesStack.add(sCol);
						movesStack = dragHelper(sRow+1,sCol,eRow, eCol, movesStack);
					} 
					if (possMovesStart[2] == 1) {
						movesStack.add(2);
						movesStack.add(sRow);
						movesStack.add(sCol);
						movesStack = dragHelper(sRow,sCol-1,eRow, eCol, movesStack);
					}
					
				} else if (sCol < eCol) {
					//down & right
					if (possMovesStart[1] == 1) {
						movesStack.add(1);
						movesStack.add(sRow);
						movesStack.add(sCol);
						movesStack = dragHelper(sRow+1,sCol,eRow, eCol, movesStack);
					} 
					if (possMovesStart[3] == 1) {
						movesStack.add(3);
						movesStack.add(sRow);
						movesStack.add(sCol);
						movesStack = dragHelper(sRow,sCol+1,eRow, eCol, movesStack);
					}
					
				}else {
					//down
					if (possMovesStart[1] == 1) {
						movesStack.add(1);
						movesStack.add(sRow);
						movesStack.add(sCol);
						movesStack = dragHelper(sRow+1,sCol,eRow, eCol, movesStack);
					} 
				}
			} else {
				if (sCol > eCol) {
					//left
					if (possMovesStart[2] == 1) {
						movesStack.add(2);
						movesStack.add(sRow);
						movesStack.add(sCol);
						movesStack = dragHelper(sRow,sCol-1,eRow, eCol, movesStack);
					}
					
				} else if (sCol < eCol) {
					//right
					if (possMovesStart[3] == 1) {
						
						movesStack.add(3);
						movesStack.add(sRow);
						movesStack.add(sCol);
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
	
	public PuzzleState dragMoveCaller (ArrayList<Integer> movesStack, PuzzleState now) {
		PuzzleState current = this;
		int curMove = movesStack.get(0);
		movesStack.remove(0);
		int nextR = movesStack.get(0);
		movesStack.remove(0);
		int nextC = movesStack.get(0);
		movesStack.remove(0);
		System.out.println("curMove = " + curMove + "  next R, next C " + nextR + ", " + nextC);
		System.out.println("movesStack");
		System.out.println(movesStack);
		switch (curMove) {
		case 0: 
			System.out.println("Load - up");
			current = dragMover(nextR,nextC,Operation.MOVEUP);
			break;
		case 1: 
			System.out.println("Load - down");
			current = dragMover(nextR,nextC,Operation.MOVEDOWN);
			break;
		case 2: 
			System.out.println("Load - left");
			current = dragMover(nextR,nextC,Operation.MOVELEFT);
			break;
		case 3: 
			System.out.println("Load - right");
			current = dragMover(nextR,nextC,Operation.MOVERIGHT);
			break;
				
		}
		if (!movesStack.isEmpty()) {
			dragMoveCaller(movesStack,now);
		}
		
		
		return current;
	}
	
	public PuzzleState dragMover(int row, int column, Operation op) {
		
		//may not be giving the right parent
		next.parent = this;
		
		if (op == Operation.MOVEUP) {
			System.out.println("move up");
			stateOperation = Operation.MOVEUP;
		
			next.puzState[row-1][column] =  next.puzState[row-1][column] ^ next.puzState[row][column] ^ ( next.puzState[row][column] = next.puzState[row-1][column] );
			length++;
		}
		
		if (op == Operation.MOVEDOWN) {
			System.out.println("move down");
			stateOperation = Operation.MOVEDOWN;
			next.puzState[row+1][column] =  next.puzState[row+1][column] ^ next.puzState[row][column] ^ ( next.puzState[row][column] = next.puzState[row+1][column] );         
			length++;
		}
		if (op == Operation.MOVELEFT) {
			System.out.println("move left");
			stateOperation = Operation.MOVELEFT;
			next.puzState[row][column-1] =  next.puzState[row][column-1] ^ next.puzState[row][column] ^ ( next.puzState[row][column] = next.puzState[row][column-1] );         
			length++;
		}
		if (op == Operation.MOVERIGHT) {
			System.out.println("move right");
			stateOperation = Operation.MOVERIGHT;
			next.puzState[row][column+1] =  next.puzState[row][column+1] ^ next.puzState[row][column] ^ ( next.puzState[row][column] = next.puzState[row][column+1] );         
			length++;
		}
		
		
		return next;
	}

	public PuzzleState getRandomEmpty(){
		int[] pickedEmpty = new int[2];
		int[] empty1moves = null;
		int[] empty2moves = null;
		int[] empty3moves = null;
		boolean empty1Poss = false;
		boolean empty2Poss = false;
		boolean empty3Poss = false;
		if ( empty1[0] != 9 ) {
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
		
		System.out.println(java.util.Arrays.toString(empty1moves));
		System.out.println(java.util.Arrays.toString(empty2moves));
		System.out.println(java.util.Arrays.toString(empty3moves));
		System.out.println("Poss Moves - 1 2 3 " + empty1Poss + " " +empty2Poss + " " + empty3Poss);

		
		
		int[] pickedMoves = null;
		boolean checkmate = false;
		while (checkmate != true) {
		int x = (int)Math.round((Math.random()*((3-0)+1)) +0);
		switch (x){
		case 1:
			if (empty1Poss == true && empty1moves != null) {
			pickedEmpty[0] = empty1[0];
			pickedEmpty[1] = empty1[1];
			pickedMoves = empty1moves;
			checkmate = true;
			}
			break;
		case 2: 
			if (empty2Poss == true && empty2moves != null) {
				pickedEmpty[0] = empty2[0];
				pickedEmpty[1] = empty2[1];
				pickedMoves = empty1moves;
				checkmate = true;
				}
				break;
		case 3: 
			if (empty1Poss == true && empty3moves != null) {
				
				pickedEmpty[0] = empty3[0];
				pickedEmpty[1] = empty3[1];
				pickedMoves = empty1moves;
				checkmate = true;
				}
				break;
			
		}
		}
		PuzzleState newState = null;
		newState = moverPicker(pickedEmpty,pickedMoves);
		return newState;
}
	
	public PuzzleState moverPicker(int[] pickedEmpty, int[] emptyMoves) {
		PuzzleState newState = null;
		boolean check = false;
		while(check != true) {
		int x = (int)Math.round((Math.random()*((3-1)+1))+1);
		switch (x) {
		case 0: 
			//MOVEDOWN so MOVEUP
			if (emptyMoves[0] == 1) {
				newState = move(pickedEmpty[0]+1,pickedEmpty[1], Operation.MOVEUP);
				check = true;
			}
			break;
		case 1: 
			//MOVEUP so MOVEDOWN
			if (emptyMoves[1] == 1) {
				newState = move(pickedEmpty[0]-1,pickedEmpty[1], Operation.MOVEDOWN);
				check = true;
			}
			break;
		case 2: 
			//MOVEUP so MOVEDOWN
			if (emptyMoves[2] == 1) {
				newState =  move(pickedEmpty[0],pickedEmpty[1]-1, Operation.MOVERIGHT);
				check = true;
			}
			break;
		case 3: 
			//MOVEUP so MOVEDOWN
			if (emptyMoves[3] == 1) {
				System.out.println("movingleft from " + pickedEmpty[0] + " " + (pickedEmpty[0] +1 ));
				newState = move(pickedEmpty[0],pickedEmpty[1]+1, Operation.MOVELEFT);
				check = true;
			}
			break;
		}
			
		}
		return newState;
	}

	@Override
	public PuzzleState shuffleBoard(int pathLength) {
		PuzzleState newState = null;
		if (length == pathLength) {
			return this;
		}
		else {
			newState = getRandomEmpty();
			newState = shuffleBoard(pathLength);
		
	}
		return newState;
	}

	@Override
	public boolean isEmpty(int row, int column) {
		if (puzState == null) {
			return false;
		}
		if (puzState[row][column] == 0) {
			return true;
		}
		return false;
	}

	@Override
	public PuzzleState getStateWithShortestPath() {
		if (getParent() == null) {
			return this;
		}
		return parent;
	}

}
