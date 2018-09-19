package edu.wm.cs.cs301.slidingpuzzle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

public class SimplePuzzleState implements PuzzleState {
	
	public int[][] puzState;
	public SimplePuzzleState parent;
	public SimplePuzzleState next;
	public Operation stateOperation;
	public int dimen = 0;
	public int length = 0;
	public int empties = 5;
	public int[] notSet = {9,9};
	public int[] empty1 = {9,9};
	public int[] empty2 = {9,9};
	public int[] empty3 = {9,9};
	
	
	public void printArr() {
		System.out.println(" After FaIL State");
		System.out.println(" Operation " + stateOperation);
	
		System.out.println(java.util.Arrays.toString(puzState[0]));
		System.out.println(java.util.Arrays.toString(puzState[1]));
		System.out.println(java.util.Arrays.toString(puzState[2]));
		System.out.println(java.util.Arrays.toString(puzState[3]));
	}
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
		if ((obj instanceof SimplePuzzleState)) {
			System.out.println("Objects not the same instance");
			return true;
		}
		
		SimplePuzzleState state = (SimplePuzzleState)obj;
		if (this.puzState != null) {
			this.setEmpties(4);
		}
		if (state.puzState != null) {
			state.setEmpties(4);
		}
		
		
		if (java.util.Arrays.deepEquals(state.puzState,this.puzState)) {
			System.out.println("Puzstates are the same");
			return true;
		}
		if (this.hashCode() == obj.hashCode()) {
			System.out.println("same hash");
			return true;
		}
		
		
		System.out.println("NOT same hash");

		System.out.println("not equal");
		return false;
		
	}
	
	@Override
	public int hashCode() {
		int hasher = 31;
		hasher =  12 * hasher  + empty1[0] + empty1[1] * empty2[0];
		
		
		//System.out.println(hasher);
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

	public int[][] arrayCopy(int[][] oldStateArray){
		
		int[][] newStateArray = new int[dimen][dimen];
		
		for (int r = 0; r < dimen; r++) {
			for (int c = 0; c < dimen; c++) {
				newStateArray[r][c] = oldStateArray[r][c];
			}
		}
		return newStateArray;
	}
	
	
	public void newLength() {
		
		length = parent.length + 1;
		
		if (parent.parent != null) {
			if (this.puzState == parent.parent.puzState) {
				//length = length -2;
			}
		}
		
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
		//System.out.println("possMoves of " + row + " " + column);
		
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
		SimplePuzzleState nextState = new SimplePuzzleState();
		nextState.parent = this;
		nextState.puzState = arrayCopy(this.puzState);
		nextState.dimen = this.dimen;
		this.next = nextState;
		
		if (op == Operation.MOVEUP) {
			//System.out.println("move up");
			//System.out.println(stateOperation);
			nextState.stateOperation = Operation.MOVEUP;
			//System.out.println("AFTER " + stateOperation);
		
			nextState.puzState[row-1][column] =  nextState.puzState[row-1][column] ^ nextState.puzState[row][column] ^ ( nextState.puzState[row][column] = nextState.puzState[row-1][column] );
			nextState.newLength();
		}
		
		if (op == Operation.MOVEDOWN) {
			//System.out.println("move down");
			nextState.stateOperation = Operation.MOVEDOWN;
			nextState.puzState[row+1][column] =  nextState.puzState[row+1][column] ^ nextState.puzState[row][column] ^ ( nextState.puzState[row][column] = nextState.puzState[row+1][column] );         
			nextState.newLength();
		}
		if (op == Operation.MOVELEFT) {
			//System.out.println("move left");
			nextState.stateOperation = Operation.MOVELEFT;
			nextState.puzState[row][column-1] =  nextState.puzState[row][column-1] ^ nextState.puzState[row][column] ^ ( nextState.puzState[row][column] = nextState.puzState[row][column-1] );         
			nextState.newLength();
		}
		if (op == Operation.MOVERIGHT) {
			
			nextState.stateOperation = Operation.MOVERIGHT;
			nextState.puzState[row][column+1] =  nextState.puzState[row][column+1] ^ nextState.puzState[row][column] ^ ( nextState.puzState[row][column] = nextState.puzState[row][column+1] );         
			nextState.newLength();
		}
		
			/*
			System.out.println("C");
			System.out.println(java.util.Arrays.toString(next.puzState[0]));
			System.out.println(java.util.Arrays.toString(next.puzState[1]));
			System.out.println(java.util.Arrays.toString(next.puzState[2]));
			System.out.println(java.util.Arrays.toString(next.puzState[3]));
			*/
		
		
		//refresh empty locations 
		//System.out.println("lengths P C" + length + " "+ next.length);
		setEmpties(dimen);
		return nextState;
	}
	
	public int[] possMoves(int row, int column) {
		// array to give all possible moves, {up, down, left , right}
		int[] possMovesArr = {0,0,0,0};
		if (row > 3 || column >3) {
			//System.out.println("ERROR ERROR");
		}
//		System.out.println("row col" + row + " " + column);

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
		//System.out.println(java.util.Arrays.toString(possMovesArr));
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
		//System.out.println("printing Empty Poss");
		//System.out.println(java.util.Arrays.toString(possMovesArr));
		return possMovesArr;
	}

	
	@Override
	public PuzzleState drag(int startRow, int startColumn, int endRow, int endColumn) {
		//check if end cell is empty else return null
		if (puzState[endRow][endColumn] != 0) {
			//return null;
		}
		int[] possMovesStart = possMoves(startRow, startColumn);
		boolean possMovesStartBool = IntStream.of(possMovesStart).anyMatch(x -> x == 1);
		 
		ArrayList<Integer> emptyLoad = new ArrayList<Integer>();
		ArrayList<Integer> moveLoad = dragHelper(startRow,startColumn,endRow,endColumn, emptyLoad);
		//System.out.println("moveLoad");
		//System.out.println(moveLoad);
		SimplePuzzleState nextState = new SimplePuzzleState();
		nextState = (SimplePuzzleState)dragMoveCaller(moveLoad);
		
		
		setEmpties(dimen);
		return nextState;
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
					} else
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
					} else
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
					} else
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
					} else
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
		//System.out.println("About to return");
		System.out.println(movesStack);
		return movesStack;
	}
	
	public PuzzleState dragMoveCaller (ArrayList<Integer> movesStack) {
		//System.out.println("movesStack BEFORE CUT");
		//System.out.println(movesStack);
		int curMove = movesStack.get(0);
		movesStack.remove(0);
		int nextR = movesStack.get(0);
		movesStack.remove(0);
		int nextC = movesStack.get(0);
		movesStack.remove(0);
		//System.out.println("curMove = " + curMove + "  next R, next C " + nextR + ", " + nextC);
		//System.out.println("movesStack");
		//System.out.println(movesStack);
		SimplePuzzleState nextState = new SimplePuzzleState();
		switch (curMove) {
		case 0: 
			//System.out.println("Load - up");
			nextState = (SimplePuzzleState)dragMover(nextR,nextC,Operation.MOVEUP);
			break;
		case 1: 
			//System.out.println("Load - down");
			nextState = (SimplePuzzleState)dragMover(nextR,nextC,Operation.MOVEDOWN);
			break;
		case 2: 
			//System.out.println("Load - left");
			nextState = (SimplePuzzleState)dragMover(nextR,nextC,Operation.MOVELEFT);
			break;
		case 3: 
			//System.out.println("Load - right");
			nextState = (SimplePuzzleState)dragMover(nextR,nextC,Operation.MOVERIGHT);
			break;
				
		}
		if (!movesStack.isEmpty()) {
			nextState = (SimplePuzzleState)nextState.dragMoveCaller(movesStack);
		}
		
		
		return nextState;
	}
	
	public PuzzleState dragMover(int row, int column, Operation op) {
		//SAME AS MOVING FUNCTION
		
		//may not be giving the right parent
		SimplePuzzleState nextState = new SimplePuzzleState();
		nextState.parent = this;
		nextState.puzState = arrayCopy(this.puzState);
		nextState.dimen = this.dimen;
		this.next = nextState;
		
		if (op == Operation.MOVEUP) {
			System.out.println("move up drag");
			//System.out.println(stateOperation);
			nextState.stateOperation = Operation.MOVEUP;
			//System.out.println("AFTERDRAG" + stateOperation);
		
			nextState.puzState[row-1][column] =  nextState.puzState[row-1][column] ^ nextState.puzState[row][column] ^ ( nextState.puzState[row][column] = nextState.puzState[row-1][column] );
			nextState.newLength();
		}
		
		if (op == Operation.MOVEDOWN) {
			System.out.println("move down drag");
			nextState.stateOperation = Operation.MOVEDOWN;
			nextState.puzState[row+1][column] =  nextState.puzState[row+1][column] ^ nextState.puzState[row][column] ^ ( nextState.puzState[row][column] = nextState.puzState[row+1][column] );         
			//System.out.println("move DOWN drag");
			//nextState.printArr();
			nextState.newLength();
		}
		if (op == Operation.MOVELEFT) {
			System.out.println("move left drag");
			nextState.stateOperation = Operation.MOVELEFT;
			nextState.puzState[row][column-1] =  nextState.puzState[row][column-1] ^ nextState.puzState[row][column] ^ ( nextState.puzState[row][column] = nextState.puzState[row][column-1] );         
			nextState.newLength();
		}
		if (op == Operation.MOVERIGHT) {
			System.out.println("move right drag");
			nextState.stateOperation = Operation.MOVERIGHT;
			//nextState.printArr();
			nextState.puzState[row][column+1] =  nextState.puzState[row][column+1] ^ nextState.puzState[row][column] ^ ( nextState.puzState[row][column] = nextState.puzState[row][column+1] );         
			//System.out.println("moveed r drag");

			//nextState.printArr();
			nextState.newLength();
		}
		
		//System.out.println("leaving");

		return nextState;
	}

	public SimplePuzzleState getRandomEmpty(){
		//printArr();
		//System.out.println("Entered RandomEmpty");
		SimplePuzzleState newState = new SimplePuzzleState();
		int[] pickedEmpty = new int[2];
		int[] pickedMoves = null;
		int[] empty1moves = null;
		int[] empty2moves = null;
		int[] empty3moves = null;
		boolean empty1Poss = false;
		boolean empty2Poss = false;
		boolean empty3Poss = false;
		boolean checkmate = false;
		//System.out.println("intiital RandomEmpty");
		setEmpties(dimen);
		if 	(empties == 1) {
			empty1moves = emptyMoves(empty1[0],empty1[1]);
			pickedEmpty[0] = empty1[0];
			pickedEmpty[1] = empty1[1];
			//System.out.println("-------------------");
			//System.out.println("LengthAss = " + this.length);
			newState = moverPicker(pickedEmpty,empty1moves);
			//System.out.println("---------------------");
			//System.out.println("RETURNEDLength = " + newState.length);
			checkmate = true;
			return newState;
			
		}
		if ( empty1[0] != 9 ) {
			//System.out.println("empy 1 being set");
			//System.out.println("empty1 location " + empty1[0] + " " +empty1[1]);
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
		
		/*System.out.println(java.util.Arrays.toString(empty1moves));
		System.out.println(java.util.Arrays.toString(empty2moves));
		System.out.println(java.util.Arrays.toString(empty3moves));
		System.out.println("Poss Moves - 1 2 3 " + empty1Poss + " " +empty2Poss + " " + empty3Poss);

		System.out.println("intstreams RandomEmpty");
		
		*/
		
		while (checkmate != true) {
			//System.out.println("While RandomEmpty");
		int x = (int)Math.round((Math.random()*((3-1)+1)) +0);
		//System.out.println("random = "+ x);
		switch (x){
		case 1:
			//System.out.println("case 1");
			if (empty1Poss == true ) {
				pickedEmpty[0] = empty1[0];
				pickedEmpty[1] = empty1[1];
				pickedMoves = empty1moves;
				checkmate = true;
			}
			break;
		case 2: 
			//System.out.println("case 2");
			if (empty2Poss == true ) {
				//System.out.println("case 2 ENTERED");
				pickedEmpty[0] = empty2[0];
				pickedEmpty[1] = empty2[1];
				pickedMoves = empty2moves;
				checkmate = true;
				}
				break;
		case 3: 
			if (empty3Poss == true ) {
				
				pickedEmpty[0] = empty3[0];
				pickedEmpty[1] = empty3[1];
				pickedMoves = empty3moves;
				checkmate = true;
				}
				break;
			
		}
		}
		//System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		//System.out.println(java.util.Arrays.toString(pickedEmpty));

		newState = moverPicker(pickedEmpty,pickedMoves);
		//System.out.println("---------------------");
		//System.out.println("rETURNEDLength = " + newState.length);
		return newState;
}
	
	public SimplePuzzleState moverPicker(int[] pickedEmpty, int[] emptyMoves) {
		int[] notEmptyMoves = new int[4];
		for (int i = 0; i<3;i++) {
			if(emptyMoves[i] == 0) {
				notEmptyMoves[i] =1;
			}else {
				notEmptyMoves[i] =0;
			}
		}
		SimplePuzzleState newState = new SimplePuzzleState();
		boolean check = false;
		
		//System.out.println("I CHOOSE YOU");
		//System.out.println(java.util.Arrays.toString(pickedEmpty));
		//System.out.println(java.util.Arrays.toString(emptyMoves));
			
		while(check != true) {
		int x = (int)Math.round((Math.random()*((3-1)+1))+0);
		switch (x) {
		case 0: 
			//MOVEDOWN so MOVEUP
			if (emptyMoves[0] == 1) {
				
				
				newState = moveRandom(pickedEmpty[0]-1,pickedEmpty[1], Operation.MOVEDOWN);
				
				check = true;
			}
			break;
		case 1: 
			//MOVEUP so MOVEDOWN
			if (emptyMoves[1] == 1) {
				//System.out.println("EmptyMoves[1] == 1");
				newState = moveRandom(pickedEmpty[0]+1,pickedEmpty[1], Operation.MOVEUP);
				
				check = true;
			}
			break;
		case 2: 
			//MOVEUP so MOVEDOWN
			if (emptyMoves[2] == 1) {
				//System.out.println("EmptyMoves[2] == 1");
				newState =  moveRandom(pickedEmpty[0],pickedEmpty[1]-1, Operation.MOVERIGHT);
				
				check = true;
			}
			break;
		case 3: 
			//MOVEUP so MOVEDOWN
			if (emptyMoves[3] == 1) {
				
				//System.out.println("EmptyMoves[3] == 1");
				newState = moveRandom(pickedEmpty[0],pickedEmpty[1]+1, Operation.MOVELEFT);
				
				check = true;
			}
			break;
		}
			
		}
		return newState;
	}

	public SimplePuzzleState moveRandom(int row, int column, Operation op) {
		//System.out.println("possMoves of " + row + " " + column);
		//System.out.println("Operation " + op);
		
		/*int temp = row;
		row = column;
		column = temp;
		 */
		//int[] movesArr = possMoves(row, column);
	
		
		//boolean possMovesStartBool = IntStream.of(movesArr).anyMatch(x -> x == 1);
	
		/*if (!possMovesStartBool) {
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
		*/
		
		
		//may not be giving the right parent
		SimplePuzzleState nextState = new SimplePuzzleState();
		nextState.parent = this;
		
		nextState.puzState = arrayCopy(this.puzState);
		nextState.dimen = this.dimen;
		this.next = nextState;
		
		if (op == Operation.MOVEUP) {
			System.out.println("move up");
			nextState.stateOperation = Operation.MOVEUP;
		
			nextState.puzState[row-1][column] =  nextState.puzState[row-1][column] ^ nextState.puzState[row][column] ^ ( nextState.puzState[row][column] = nextState.puzState[row-1][column] );
			nextState.newLength();
		}
		
		if (op == Operation.MOVEDOWN) {
			System.out.println("move down");
			nextState.stateOperation = Operation.MOVEDOWN;
			nextState.puzState[row+1][column] =  nextState.puzState[row+1][column] ^ nextState.puzState[row][column] ^ ( nextState.puzState[row][column] = nextState.puzState[row+1][column] );         
			nextState.newLength();
		}
		if (op == Operation.MOVELEFT) {
			System.out.println("move left");
			nextState.stateOperation = Operation.MOVELEFT;
			nextState.puzState[row][column-1] =  nextState.puzState[row][column-1] ^ nextState.puzState[row][column] ^ ( nextState.puzState[row][column] = nextState.puzState[row][column-1] );         
			nextState.newLength();
		}
		if (op == Operation.MOVERIGHT) {
			
			nextState.stateOperation = Operation.MOVERIGHT;
			nextState.puzState[row][column+1] =  nextState.puzState[row][column+1] ^ nextState.puzState[row][column] ^ ( nextState.puzState[row][column] = nextState.puzState[row][column+1] );         
			nextState.newLength();
		}
		
			/*
			System.out.println("C");
			System.out.println(java.util.Arrays.toString(next.puzState[0]));
			System.out.println(java.util.Arrays.toString(next.puzState[1]));
			System.out.println(java.util.Arrays.toString(next.puzState[2]));
			System.out.println(java.util.Arrays.toString(next.puzState[3]));
			*/
		
		
		//refresh empty locations 
		//System.out.println("lengths P C" + length + " "+ next.length);
		setEmpties(dimen);
		return nextState;
	}
	
	
	public SimplePuzzleState shuffleRecursive(int pathLength) {
		SimplePuzzleState newState = new SimplePuzzleState();
		if (this.length == pathLength) {
			return this;
		}
		else {
			
			newState = getRandomEmpty();
			if (newState.length == pathLength) {
				return newState;
			} else {
			newState = newState.shuffleRecursive(pathLength);
			setEmpties(dimen);
			}
		
	}
		
			
			
			
			
			setEmpties(dimen);
			return newState;
		
	}
	@Override
	public PuzzleState shuffleBoard(int pathLength) {
		SimplePuzzleState newState = new SimplePuzzleState();
		newState = shuffleRecursive(pathLength);
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
		if (this.parent == null) {
			return this;
		}
		return this;
	}

}
