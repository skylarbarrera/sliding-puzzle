package edu.wm.cs.cs301.slidingpuzzle;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.wm.cs.cs301.slidingpuzzle.PuzzleState.Operation;

/**
 * Junit test cases for SimplePuzzleState implementation of PuzzleState interface.
 * Class contains a number of black box tests.
 * Tests are very basic in that they rely on as little extra functionality
 * as possible aside from the method under test. 
 * This implies that tests are not particularly elaborate and may serve
 * as a set of initial smoke tests to see if at least some basic functionality is there.
 */ 
public class SimplePuzzleStateBasicsTest {
	// tests show up in the order generated by Eclipse

	/**
	 * Test method for {@link edu.wm.cs.cs301.slidingpuzzle.PuzzleState#setToInitialState(int, int)}.
	 * The initial state for a given dimension and number of empty slots should list
	 * tiles with numbers in increasing order and have empty slots at the very end.
	 * Empty slots are encoded with zero values.
	 * We test if this works for 1 empty slot.
	 * Test requires setToInitialState() and getValue() being properly implemented. 
	 */
	@Test
	public final void testSetToInitialStateIntIntOneSlot() {
		// create state with initial state
		PuzzleState ps1 = new SimplePuzzleState();
		int empties = 1;
		int dim = 4;
		ps1.setToInitialState(dim, empties); // 4x4 board with 1 empty slot
		checkInitialState(ps1, dim, empties);
	}
	/**
	 * Test method for {@link edu.wm.cs.cs301.slidingpuzzle.PuzzleState#setToInitialState(int, int)}.
	 * The initial state for a given dimension and number of empty slots should list
	 * tiles with numbers in increasing order and have empty slots at the very end.
	 * Empty slots are encoded with zero values.
	 * We test if this works for several empty slots. 
	 * Test requires setToInitialState() and getValue() being properly implemented.
	 */
	@Test
	public final void testSetToInitialStateIntIntSeveralSlots() {
		// create state with initial state
		PuzzleState ps1 = new SimplePuzzleState();
		for (int dim = 1; dim < 10; dim++) {
			for (int empties = 1; empties < 4 && empties < dim; empties++) {
				ps1.setToInitialState(dim, empties); 
				checkInitialState(ps1, dim, empties);
			}
		}
	}
	/**
	 * Helper method to see if given state is the initial state.
	 * @param ps1 state to analyze
	 * @param dim dimension of board is (dim x dim)
	 * @param empties the number of empty slots on the board
	 */
	private void checkInitialState(PuzzleState ps1, int dim, int empties) {
		int len = dim * dim - empties;
		for (int r = 0; r < dim; r++) {
			for (int c = 0; c < dim; c++) {
				if (r * dim + c < len) {
					assertEquals(r * dim + c + 1, ps1.getValue(r, c));
				} else {
					// empties at end positions
					assertEquals(0, ps1.getValue(r, c));
				}
			}
		}
	}
	/**
	 * Test method for {@link edu.wm.cs.cs301.slidingpuzzle.SimplePuzzleState#hashCode()}.
	 * The SimplePuzzleState class must implement (override) the equals method.
	 * If one does so, it is recommended good practice to also override the 
	 * hashCode method to have a consistent class design.
	 * We check if equal states receive the same hashCode.
	 * Test requires setToInitialState() and hashCode() being properly implemented.
	 */
	@Test
	public final void testHashCode() {
		PuzzleState ps1 = new SimplePuzzleState();
		int empties = 2;
		int dim = 4;
		ps1.setToInitialState(dim, empties);
		// calling hashCode twice should give same result
		// hashCode of same object should be the same
		assertEquals(ps1.hashCode(),ps1.hashCode());
		
		PuzzleState ps2 = new SimplePuzzleState();
		ps2.setToInitialState(dim, empties);
		// both objects should have the same initial state
		// so their hashCodes should be the same
		assertEquals(ps1.hashCode(),ps2.hashCode());
	}

	/**
	 * Test method for {@link edu.wm.cs.cs301.slidingpuzzle.SimplePuzzleState#equals(java.lang.Object)}.
	 * The SimplePuzzleState class must implement (override) the equals method.
	 * Test requires setToInitialState() and equals() being properly implemented.
	 */
	@Test
	public final void testEqualsObject() {
		PuzzleState ps1 = new SimplePuzzleState();
		int empties = 2;
		int dim = 4;
		ps1.setToInitialState(dim, empties);
		
		// requirements for equals
		// existing object is not equal to null
		assertFalse(ps1.equals(null));
		// PuzzleState can not be equal to a class like
		// object that does not implement PuzzleState
		assertFalse(ps1.equals(new Object()));
		// each object is equal to itself
		assertTrue(ps1.equals(ps1));
		// if 2 puzzle states have the same settings, 
		// they should be equal
		PuzzleState ps2 = new SimplePuzzleState();
		ps2.setToInitialState(dim, empties);
		assertTrue(ps1.equals(ps2));
		// if 2 puzzle states have different settings
		// they can not be equal
		ps2.setToInitialState(dim, empties-1);
		assertFalse(ps1.equals(ps2));
		ps2.setToInitialState(dim, empties+1);
		assertFalse(ps1.equals(ps2));
		ps2.setToInitialState(dim-1, empties);
		assertFalse(ps1.equals(ps2));
		ps2.setToInitialState(dim+1, empties);
		assertFalse(ps1.equals(ps2));
	}
	/**
	 * Test method for {@link edu.wm.cs.cs301.slidingpuzzle.PuzzleState#getOperation()}.
	 * Trivial test to see if initial state returns null for the operation.
	 * Test requires getOperation() being properly implemented.
	 */
	@Test
	public final void testGetOperation() {
		PuzzleState ps1 = new SimplePuzzleState();
		ps1.setToInitialState(4, 2);
		// the initial state does not have a parent state
		// there is no move operation get from a non-existing state
		// to this state
		assertNull(ps1.getOperation());
	}
	/**
	 * Test method for {@link edu.wm.cs.cs301.slidingpuzzle.PuzzleState#getOperation()}.
	 * Test to see if direct successor state returns correct operation.
	 * Test requires getOperation() and move() being properly implemented.
	 */
	@Test
	public final void testGetOperation1() {
		PuzzleState ps1 = new SimplePuzzleState();
		ps1.setToInitialState(4, 2);
		assertNull(ps1.getOperation());
		PuzzleState ps2;
		// on a 4x4 board, the lower right corner at (3,3) is empty
		// we move the tile above downwards on that spot
		// so the successor state should exist and 
		// the operation to get to it should be movedown
		ps2 = ps1.move(2, 3, Operation.MOVEDOWN);
		assertNotNull(ps2);
		assertEquals(Operation.MOVEDOWN,ps2.getOperation());
	}
	/**
	 * Test method for {@link edu.wm.cs.cs301.slidingpuzzle.PuzzleState#getParent()}.
	 * Trivial test to see if initial state returns null for the parent node.
	 * Test requires getParent() being properly implemented.
	 */
	@Test
	public final void testGetParent() {
		PuzzleState ps1 = new SimplePuzzleState();
		ps1.setToInitialState(4, 2);
		// the initial state does not have a parent state
		assertNull(ps1.getParent());
	}
	/**
	 * Test method for {@link edu.wm.cs.cs301.slidingpuzzle.PuzzleState#getParent()}.
	 * Test to see if direct successor state returns the initial state as its parent.
	 * Test requires getParent(), equals() and move() being properly implemented.
	 */
	@Test
	public final void testGetParent1() {
		PuzzleState ps1 = new SimplePuzzleState();
		ps1.setToInitialState(4, 2);
		assertNull(ps1.getParent());
		PuzzleState ps2;
		// on a 4x4 board, the lower right corner at (3,3) is empty
		// we move the tile above downwards on that spot
		// so the successor state should exist and 
		// the parent of that successor state is the initial state		
		ps2 = ps1.move(2, 3, Operation.MOVEDOWN);
		assertNotNull(ps2);
		assertEquals(ps1,ps2.getParent());
	}
	/**
	 * Test method for {@link edu.wm.cs.cs301.slidingpuzzle.PuzzleState#getPathLength()}.
	 * Test to see if the path length for the initial state is 0.
	 * Test requires getPathLength() being properly implemented.
	 */
	@Test
	public final void testGetPathLength() {
		PuzzleState ps1 = new SimplePuzzleState();
		ps1.setToInitialState(4, 2);
		// state does not result from a move operation
		// so the path length must be zero
		assertEquals(0,ps1.getPathLength());
	}
	/**
	 * Test method for {@link edu.wm.cs.cs301.slidingpuzzle.PuzzleState#getPathLength()}.
	 * Test to see if the path length for a successor state of the initial state is 1.
	 * Test requires getPathLength() and move() being properly implemented.
	 */
	@Test
	public final void testGetPathLength1() {
		PuzzleState ps1 = new SimplePuzzleState();
		ps1.setToInitialState(4, 2);
		// state does not result from a move operation
		// so the path length must be zero
		assertEquals(0,ps1.getPathLength());
		PuzzleState ps2;
		// on a 4x4 board, the lower right corner at (3,3) is empty
		// we move the tile above downwards on that spot
		// so the successor state should exist and 
		// the parent of that successor state is the initial state
		// the path length is 1 because it is just one move operation
		ps2 = ps1.move(2, 3, Operation.MOVEDOWN);
		assertEquals(1,ps2.getPathLength());
	}
	/**
	 * Test method for {@link edu.wm.cs.cs301.slidingpuzzle.PuzzleState#shuffleBoard(int)}.
	 * Trivial test to see if shuffleBoard returns an equal state for length 0 and
	 * different states for length greater 0.
	 * Test requires setToInitialState(), equals(), and shuffleBoard() being properly implemented.
	 */
	@Test
	public final void testShuffleBoard() {
		PuzzleState ps1 = new SimplePuzzleState();
		ps1.setToInitialState(4, 2);
		// create 2nd state and compare
		PuzzleState ps2 = new SimplePuzzleState();
		ps2.setToInitialState(4, 2);	
		assertTrue(ps1.equals(ps2));
		// use shuffle operation, length 0 should give same state
		ps2 = ps1.shuffleBoard(0);
		assertTrue(ps1.equals(ps2));
		// use shuffle operation, length 1 should give different state
		ps2 = ps1.shuffleBoard(1);
		
		
		assertFalse(ps1.equals(ps2));
		// 2nd try with length 2, see if we can do more than 1 step
		ps2 = ps1.shuffleBoard(2);
		assertFalse(ps1.equals(ps2));
	}
	/**
	 * Test method for {@link edu.wm.cs.cs301.slidingpuzzle.PuzzleState#getValue(int,int)}.
	 * Trivial test to see if shuffleBoard returns states where the sum of values across all
	 * tiles stays the same. Test uses getValue() extensively but only checks correctness
	 * with the help of an aggregate (sum).
	 * Test requires setToInitialState(), shuffleBoard() and getValue() being properly implemented.
	 */
	@Test
	public final void testGetValue() {
		PuzzleState ps1 = new SimplePuzzleState();
		int dim = 4; 
		int empties = 2;
		ps1.setToInitialState(dim, empties);
		// calculate sum of values for comparison
		int s1 = sum(ps1, dim);
		// shuffle operation should give state
		// that is different but sum of values is the same
		PuzzleState ps2;
		ps2 = ps1.shuffleBoard(1);
		assertEquals(s1,sum(ps2, dim));
		// try the same property but for longer sequences
		ps2 = ps1.shuffleBoard(2);
		System.out.println("SHUFFLE SHUFFLE SHUFFLE");
		assertEquals(s1,sum(ps2, dim));
		System.out.println("SHUFFLE SHUFFLE SHUFFLE");
		ps2.printArr();
		ps2 = ps1.shuffleBoard(3);
		System.out.println("SHUFFLE SHUFFLE SHUFFLE");
		assertEquals(s1,sum(ps2, dim));
		ps2 = ps1.shuffleBoard(4);
		assertEquals(s1,sum(ps1, dim));
	}
	/**
	 * Helper method to give the sum of all tiles.
	 * @param ps1 the state to analyze
	 * @param dim the dimension of the board (dim x dim)
	 * @return the sum of all values across all tiles on the board
	 */
	private int sum(PuzzleState ps1, int dim) {
		int result = 0;
		int val;
		for (int r = 0; r < dim; r++) {
			for (int c = 0; c < dim; c++) {
				val = ps1.getValue(r, c);
				// check range, values can't be negative
				// or larger than the number of slots on the board
				assertTrue(0 <= val);
				assertTrue(val < dim*dim);
				// accumulate values
				result += val;
			}
		}
		return result;
	}
	/**
	 * Test method for {@link edu.wm.cs.cs301.slidingpuzzle.PuzzleState#move(int,int,Operation)}.
	 * Test to see if a move operation works. We try two different directions for the same
	 * empty slot. We also check that the starting state remains intact.
	 * Test requires setToInitialState(), isEmpty(), equals() and move() being properly implemented.
	 */
	@Test
	public final void testMove() {
		PuzzleState ps1 = new SimplePuzzleState();
		PuzzleState ps2;
		PuzzleState ps3  = new SimplePuzzleState();
		ps1.setToInitialState(3,1);
		ps3.setToInitialState(3,1);
		// ps1 and ps3 should be the same, check this upfront and later again
		assertEquals(ps1,ps3);
		// last position: lower right is empty, (2,2)
		assertFalse(ps1.isEmpty(1, 2));
		assertTrue(ps1.isEmpty(2, 2));
		// we can move the tile from above (1,2) on the empty slot
		ps2 = ps1.move(1,2,Operation.MOVEDOWN);
		// check if slot above is empty, target slot is filled now
		assertTrue(ps2.isEmpty(1, 2));
		assertFalse(ps2.isEmpty(2, 2));
		// try a second move starting from ps1 again
		// note that only ps2 is the result of a move operation
		// ps1 should stay as it was, we compare it with ps3.
		System.out.println("failed comparison MoveTest");
		assertEquals(ps1,ps3);
		
		assertFalse(ps1.isEmpty(2, 1));
		assertTrue(ps1.isEmpty(2, 2));
		// last position: lower right is empty (2,2)
		// we can move the tile from the left (2,1) on the empty slot
		ps2 = ps1.move(2,1,Operation.MOVERIGHT);
		assertTrue(ps2.isEmpty(2, 1));
		assertFalse(ps2.isEmpty(2, 2));
	}
	/**
	 * Test method for {@link edu.wm.cs.cs301.slidingpuzzle.PuzzleState#drag(int,int,int,int)}.
	 * Test to see if a drag operation works. We try two different directions for the same
	 * empty slot. We also check that the starting state remains intact.
	 * Test requires setToInitialState(), isEmpty(), equals() and drag() being properly implemented.
	 */
	@Test
	public final void testDrag() {
		SimplePuzzleState ps1 = new SimplePuzzleState();
		PuzzleState ps2;
		SimplePuzzleState ps3  = new SimplePuzzleState();
		
		ps1.setToInitialState(4,2);
		ps3.setToInitialState(4,2);
		// establish that ps1 and ps3 are the same by construction
		// we will use this later to check that ps1 was not changed by the drag operation
		assertEquals(ps1,ps3);
		// last 2 positions on the lower right are empty (3,2) and (3,3)
		assertFalse(ps1.isEmpty(2, 2));
		assertFalse(ps1.isEmpty(2, 3));
		assertTrue(ps1.isEmpty(3, 2));
		assertTrue(ps1.isEmpty(3, 3));
		// move the tile from (2,2) to (3,3)
		ps2 = ps1.drag(2, 2, 3, 3);
		// check that this worked
		assertTrue(ps2.isEmpty(2, 2));
		assertFalse(ps2.isEmpty(2, 3));
		assertTrue(ps2.isEmpty(3, 2));
		
		assertFalse(ps2.isEmpty(3, 3));
		// check that ps1 did not change with the drag operation
		
		assertEquals(ps1,ps3);
		
		

		// 2nd try, different direction
		assertFalse(ps1.isEmpty(2, 2));
		assertFalse(ps1.isEmpty(2, 3));
		assertTrue(ps1.isEmpty(3, 2));
		assertTrue(ps1.isEmpty(3, 3));
		// move tile from (2,3) to (3,2)
		ps2 = ps1.drag(2, 3, 3, 2);
		assertFalse(ps2.isEmpty(2, 2));
		assertTrue(ps2.isEmpty(2, 3));
		assertFalse(ps2.isEmpty(3, 2));
		assertTrue(ps2.isEmpty(3, 3));
		// check that ps1 did not change with the drag operation
		assertEquals(ps1,ps3);
	}
	/**
	 * Test method for {@link edu.wm.cs.cs301.slidingpuzzle.PuzzleState#isEmpty(int,int)}.
	 * Test checks all positions on the initial state for being empty or not.
	 * Test requires setToInitialState() and isEmpty() being properly implemented.
	 */
	@Test
	public final void testIsEmpty() {
		int dim = 4;
		int empties = 2;
		PuzzleState ps1 = new SimplePuzzleState();
		ps1.setToInitialState(dim, empties);
		int len = dim * dim - empties;
		for (int r = 0; r < dim; r++) {
			for (int c = 0; c < dim; c++) {
				if (r * dim + c < len) {
					//assertEquals(r * dim + c + 1, ps1.getValue(r, c));
					assertFalse(ps1.isEmpty(r, c));
				} else {
					// empties at end positions
					//assertEquals(0, ps1.getValue(r, c));
					assertTrue(ps1.isEmpty(r, c));
				}
			}
		}
	}
	/**
	 * Test method for {@link edu.wm.cs.cs301.slidingpuzzle.PuzzleState#getStateWithShortestPath()}.
	 * Trivial test to see if getStateWithShortestPath() returns a state for
	 * trivial sequences of length 0 and 1.
	 * Test is not(!) rigid enough to see if an existing cycle is recognized and removed
	 * by the method.
	 * Test requires setToInitialState() and isEmpty() being properly implemented.
	 */
	@Test
	public final void testGetStateWithShortestPath() {
		PuzzleState ps1 = new SimplePuzzleState();
		ps1.setToInitialState(4,2);
		PuzzleState ps2,ps3;
		// check the initial state, there is no parent, no path at all
		// should expect a state in return that is the same
		ps2 = ps1.getStateWithShortestPath();
		assertTrue(ps1.equals(ps2));
		// make a move
		// this can no establish a loop so the successor state and one computed
		// for the shortest path should be the same
		ps3 = ps1.move(2,3,Operation.MOVEDOWN);
		
		ps2 = ps3.getStateWithShortestPath();
		assertTrue(ps3.equals(ps2));
	}

}
