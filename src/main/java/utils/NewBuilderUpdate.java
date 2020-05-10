package utils;

import java.util.ArrayList;

public class NewBuilderUpdate {
	private ArrayList<Integer> position;
	
	 public NewBuilderUpdate(int[] position){
		 for(int i = 0; i<2 ; i++) {
			this.position.add(position[i]);
		 }
	 }
	 
	 public  ArrayList<Integer> getPosition(){
		 return position;
	 }
}
