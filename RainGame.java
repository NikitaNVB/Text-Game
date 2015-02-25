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
	
		
		/* Larger numbers at higher levels
		 * Fix flickering
		 * change color every level (randomize)
		 * Characters?
		 * Choosing level
		 * Difficulty (easy, mid, hard)
		 * Life counter (print failCounter)
		 * put images?
		 * start at random (x) spots
		 * sometimes it'll go from the bottom to the top
		 * Print highscores 
		 */
	
		int level = 1; //start at lv 1
		final int NUMBER_OF_FAILS = 3; //can fail this many times
		int failCounter = 0; //counts the amount of times it reaches the bottom
		boolean reachedBottom = false;
		String text = "" + (int)(Math.random()*999);
		long startTime =System.currentTimeMillis();
		long elapsed = 0L;
		
		//initialize variables (game)
		int x=0, y=Zen.getZenHeight() / 2, dx=0, dy=2*level, score=0;
		
		Zen.setFont("Helvetica-64");
		while (Zen.isRunning()) {
			if (reachedBottom) //if reached the bottom, start over with new number
				text = ""; 
				reachedBottom = false;
			if (text.length() == 0){
				x = 0;
				y = Zen.getZenHeight() / 2;
				dx = 0;
				dy = 2 * level; //increment each diff by 2 for each level
				text = "" + (int) (Math.random() * 999);
				elapsed = System.currentTimeMillis() - startTime;
				startTime = System.currentTimeMillis();
				score += 3000 / elapsed;
				
			}
			Zen.setColor(255, 0, 255);
			Zen.fillRect(0, 0, Zen.getZenWidth(), Zen.getZenHeight());

			Zen.setColor(0, 255, 0);
			Zen.drawText(text, x, y);
			
			Zen.drawText("Level: " + level,10,50); //put variables here
			Zen.drawText("Score: " + score,10,100);
			
			
			
			x += dx;
			y += dy;
			
			//if reaches bottom, start over, subtract points
			if (y==Zen.getZenHeight()){
				reachedBottom = true; //reached the bottom of the screen
				score-= 10;
				failCounter++;
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
