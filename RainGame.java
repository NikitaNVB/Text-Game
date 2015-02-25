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
		 * 
		 * Known BUGS:
		 * 1) flickering issue
		 * 2) when it goes to the next level it will flicker the text color on the whole screen
		 */
		
		//constant variables that can't be changed
		final int NUMBERS_OF_TEXTS_TO_BEAT = 5; //amount of texts to beat to get to the next level
		final int NUMBER_OF_LIVES = 3; //can fail this many times
		final int NUMBER_OF_TOP_SCORES = 3; //amount of top scores it's going to print
		
		//variables to declare
		int[][] highscores = new int[3][NUMBER_OF_TOP_SCORES]; //shows highscores by Score, Level, TextsBeat
		int level = 1; //start at lv 1
		int startingSpeed = 0; //the speed you start the game at (should be used to set difficiulties 
		int amountPlayed = 0; //# of games played
		boolean stopCounting = false; //stops the level from continuously incrementing
		int failCounter = 0; //counts the amount of times it reaches the bottom
		int numberWon = 0; //counter that keeps track of how many words you finished typing
		boolean reachedBottom = false;
		String text = "" + (int)(Math.random()*999);
		long startTime =System.currentTimeMillis();
		long elapsed = 0L;
		
		int x=0;
		//initialize variables (game)
		int y=Zen.getZenHeight() / 2, dx=0, dy=startingSpeed+level, score=0;
		
		Zen.setFont("Helvetica-64");
		while (Zen.isRunning()) {
			if (reachedBottom) //if reached the bottom, start over with new number
				text = ""; 
				reachedBottom = false;
			if (text.length() == 0){
				x = (Zen.getZenWidth() / (int) (Math.random()*4+2));
				y = Zen.getZenHeight() / 2;
				dx = 0;
				dy = startingSpeed + level; //increment each diff by 2 for each level
				text = "" + (int) (Math.random() * 999);
				elapsed = System.currentTimeMillis() - startTime;
				startTime = System.currentTimeMillis();
				
				score += 10000 / elapsed; //increment the score based on how fast you typed it correctly
				
				numberWon++; //increments the number of times you got the words right
				
			}
			
			Zen.setColor((30*level)%255, (40*level)%255, (50*level)%255); //randomize color depending on level
			Zen.fillRect(0, 0, Zen.getZenWidth(), Zen.getZenHeight());

			
			Zen.setColor(0, 255, 255);
			Zen.drawText(text, x, y);
			
			Zen.setFont("Helvetica-40"); //make the fonts top smaller
			Zen.drawText("Level: " + level,10,50); //put variables here
			Zen.drawText("Streak: " + numberWon,250,50); //print how many texts user is beating
			Zen.drawText("Score: " + score,10,100); //print scores
			Zen.drawText("Health: " + (NUMBER_OF_LIVES-failCounter),250,100); //print health
			

			
			Zen.setFont("Helvetica-64"); //set back to original size
			
			
			x += dx; //decrease position x by horizontal speed
			y += dy; //decrease position y by vertical speed
			
			//every certain amount of texts beaten, increment the level
			if ((numberWon == (NUMBERS_OF_TEXTS_TO_BEAT*level)) && !stopCounting){
				level++; //go to the next level
				Zen.fillRect(0, 0, Zen.getZenWidth(), Zen.getZenHeight());
				stopCounting = true; //stop from incrementing levels on a loop
			}
			else if ((numberWon == (NUMBERS_OF_TEXTS_TO_BEAT*(level-1))+1))
				stopCounting = false; //continue to look for levels being equal
			
			//if reaches bottom, start over, subtract points
			if (y>=Zen.getZenHeight()){
				reachedBottom = true; //reached the bottom of the screen
				score-= 10;
				failCounter++;
				numberWon--; //make sure it doesn't increment a win
			}
			
			//if the player losses this many times, GAME OVER
			if (failCounter==NUMBER_OF_LIVES){
				amountPlayed++; //played a game
				break; //end the game
			}
				
			
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
		
		
		for (int i=0; i<amountPlayed; i++){
			highscores [0][i] = score; //store highest score 
			highscores [1][i] = numberWon; //store highest # of texts beaten
			highscores [2][i] = level; //store highest level
		}
		
		//game is over
		
		//erase previous input on screen
		Zen.setColor(22, 186, 44); //color of background (maybe put image here instead)
		Zen.fillRect(0, 0, Zen.getZenWidth(), Zen.getZenHeight());	
		
		Zen.setFont("Helvetica-64"); //set back to original size
		Zen.setColor(0, 0, 255); 
		Zen.drawText("Game Over",Zen.getZenWidth()/4, Zen.getZenHeight()/5); //print "Game Over"
		Zen.setColor(146, 27, 27); //color of highscores
		Zen.setFont("Helvetica-28");
		Zen.drawText("Highscore(s):",5, Zen.getZenHeight()/3); //print "Game Over"
		Zen.setFont("Helvetica-18");
		Zen.drawText(amountPlayed + ". " + "Highest Score: " + highscores[0][0] + " | " + "Highest Streak: " + highscores[1][0] + " | " + "Highest Level Reached: " + highscores[2][0],5,Zen.getZenHeight() / 2); //print "Game Over"
		
	}

}
