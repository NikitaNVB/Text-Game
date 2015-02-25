//UIUC CS125 FALL 2014 MP. File: RainGame.java, CS125 Project: PairProgramming, Version: 2015-02-23T22:42:52-0600.095355921
/**
 * @author nbuyev2,schirle2
 */
public class RainGame {

	public static void main(String[] args) {
		// To get points type your netids above (CORRECTLY-Please double check your partner correctly spells your netid correctly!!)
		// Your netid is the unique part of your @illinois email address
		// Do not put your name or your UIN. 
		// REMEMBER TO COMMIT this file...
	
		
		/* Larger numbers at higher levels => done
		 * Fix flickering
		 * change color every level => done (has flicker problem, trying to solve it)
		 * Characters?
		 * Choosing level
		 * Difficulty (easy, mid, hard)
		 * Life counter (print failCounter) => done
		 * put images?
		 * start at random (x) spots => done
		 * sometimes it'll go from the bottom to the top
		 * Print highscores 
		 */
		
		//constant variables that can't be changed
		final int NUMBER_OF_FAILS = 3; //can fail this many times
		final int NUMBERS_OF_TEXTS_TO_BEAT = 5; //amount of texts to beat to get to the next level
		
		//variables to declare
		int level = 1; //start at lv 1
		int tempLevel = 1; //initialize temp value
		boolean stopCounting = false; //stops the level from continuously incrementing
		int failCounter = 0; //counts the amount of times it reaches the bottom
		int numberWon = 0; //counter that keeps track of how many words you finished typing
		boolean reachedBottom = false;
		String text = "" + (int)(Math.random()*999);
		long startTime =System.currentTimeMillis();
		long elapsed = 0L;
		
		int x=0;
		//initialize variables (game)
		int y=Zen.getZenHeight() / 2, dx=0, dy=2*level, score=0;
		
		Zen.setFont("Helvetica-64");
		while (Zen.isRunning()) {
			if (reachedBottom) //if reached the bottom, start over with new number
				text = ""; 
				reachedBottom = false;
			if (text.length() == 0){
				x = (Zen.getZenWidth() / (int) (Math.random()*4+2));
				y = Zen.getZenHeight() / 2;
				dx = 0;
				dy = 1 + level; //increment each diff by 2 for each level
				text = "" + (int) (Math.random() * 999);
				elapsed = System.currentTimeMillis() - startTime;
				startTime = System.currentTimeMillis();
				
				score += 10000 / elapsed; //increment the score based on how fast you typed it correctly
				
				numberWon++; //increments the number of times you got the words right
				
			}
			
//			if (tempLevel < 3){
//				Zen.setColor(0, 255, 255);
//				Zen.drawText(text, x, y);
//				Zen.drawText("worked",100,300);
//			}
			
			Zen.setColor((30*level)%255, (40*level)%255, (50*level)%255); //randomize color depending on level
			Zen.fillRect(0, 0, Zen.getZenWidth(), Zen.getZenHeight());


			Zen.setColor(0, 255, 255);
			Zen.drawText(text, x, y);
			
			Zen.setFont("Helvetica-40"); //make the fonts top smaller
			Zen.drawText("Level: " + level,10,50); //put variables here
			Zen.drawText("Score: " + score,10,100);
			Zen.drawText("Health: " + (3-failCounter),200,100);
			
			Zen.setFont("Helvetica-64"); //set back to original size
			
			
			x += dx;
			y += dy;
			
//			tempLevel = level; //used to briefly hold onto the value of temp
			
			//every certain amount of texts beaten, increment the level
			if ((numberWon == (NUMBERS_OF_TEXTS_TO_BEAT*level)) && !stopCounting){
				level++; //go to the next level
				Zen.fillRect(0, 0, Zen.getZenWidth(), Zen.getZenHeight());
				stopCounting = true; //stop from incrementing levels on a loop
			}
			else if ((numberWon == (NUMBERS_OF_TEXTS_TO_BEAT*(level-1))+1))
				stopCounting = false; //continue to look for levels being equal
			
			//if reaches bottom, start over, subtract points
			if (y==Zen.getZenHeight()){
				reachedBottom = true; //reached the bottom of the screen
				score-= 10;
				failCounter++;
				numberWon--; //make sure it doesn't increment a win
			}
			
			//if the player losses this many times, GAME OVER
			if (failCounter==NUMBER_OF_FAILS)
				break; //end the game
			
			// Find out what keys the user has been pressing.
			String user = Zen.getEditText();
			// Reset the keyboard input to an empty string
			// So next iteration we will only get the most recently pressed keys.
			Zen.setEditText("");
			
			for(int i=0;i < user.length();i++) {
				char c = user.charAt(i);
				if(c == text.charAt(0))
					text = text.substring(1,text.length()); // all except first character
			}

			
			Zen.sleep(90-level);// sleep for 90 milliseconds

		}
		
		//game is over
		Zen.drawText("Game Over",Zen.getZenWidth()/4,Zen.getZenHeight() / 2); //print "Game Over"
	}

}
