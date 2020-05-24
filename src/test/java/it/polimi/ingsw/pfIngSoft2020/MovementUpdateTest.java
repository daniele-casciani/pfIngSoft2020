package it.polimi.ingsw.pfIngSoft2020;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import utils.*;

public class MovementUpdateTest {

	int[] start = {1,1};
	int height1 = 3;
	int[] end = {2,2};
	int height2 = 1;
	String name = "player1";
	
	MoveUpdate message = new MoveUpdate(start, height1, end, height2,name);
	
	@Test
	public void testMU() {
		int[] value ={start[0],start[1],height1,end[0],end[1],height2};
		assertEquals(value,message.getMovement());
		assertEquals(name,message.getName());
	}
	
}
