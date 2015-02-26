import java.awt.Image;

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
		 * Fix flickering => (has something to do with Zen.flipBuffering() but I'm having problems with it)
		 * change color every level => done 
		 * Characters? => work on this next
		 * Choosing level => don't think we need to bother with this one
		 * Difficulty (easy, hard) => done
		 * Life counter (print failCounter) => done
		 * put images? => can't be done
		 * start at random (x) spots => done
		 * sometimes it'll go from the bottom to the top
		 * Print highscores (done)
		 * 
		 * Known BUGS:
		 * 1) flickering issue
		 * 
		 */
		
		//constant variables that can't be changed
		final int NUMBERS_OF_TEXTS_TO_BEAT = 5; //amount of texts to beat to get to the next level
		final int NUMBER_OF_LIVES = 3; //can fail this many times
		final int NUMBER_OF_TOP_SCORES = 3; //amount of top scores it's going to print
		
		//variables to declare
		int[][] highscores = new int[3][NUMBER_OF_TOP_SCORES]; //shows highscores by Score, Level, TextsBeat
		int level = 1; //starts levels at lv 1
		String name = ""; //store user's name
		int startingSpeed = 0; //the speed you start the game at (should be used to set difficiulties 
		int amountPlayed = 0; //# of games played
		boolean stopCounting = false; //stops the level from continuously incrementing
		int failCounter = 0; //counts the amount of times it reaches the bottom
		int numberWon = 0; //counter that keeps track of how many words you finished typing
		boolean reachedBottom = false;
		String text = "" + (int)(Math.random()*999);
		long startTime =System.currentTimeMillis();
		long elapsed = 0L;
		
		int mouseClickX; //get X coordinate of mouse click
		int mouseClickY; //get Y coordinate of mouse click
	
		
//Starting screen => input name
		
		while (Zen.isRunning()) {
			int width = 50;
			int height = 50;
			
			//background color of main screen
			Zen.setColor(38, 104, 128);
			Zen.fillRect(0, 0, Zen.getZenWidth(), Zen.getZenHeight());
			
			mouseClickX = Zen.getMouseClickX(); //get X coordinate
			mouseClickY = Zen.getMouseClickY(); //get Y coordinate

			
			//title of the game
			Zen.setFont("Helvetica-54");
			Zen.setColor(255,0,0);
			Zen.drawText("Text",185, 60); 
			Zen.setColor(0,0,255);
			Zen.drawText("Game", 310, 60);
			
			//creators of the game
			Zen.setFont("Helvetica-25");
			Zen.setColor(255,0,255);
			Zen.drawText("A game by Nikita Buyevich and Matthew Schirle",55, 100);
			
			//draw black line
			Zen.setColor(255,255,255);
			Zen.drawLine(0, 115, Zen.getZenWidth(),115);
			
			Zen.setColor(255,160,30);
			Zen.setFont("Helvetica-34");
			Zen.drawText("Hello! What's your name?",120, 200); //prompt user to enter name
			
			Zen.setFont("Helvetica-44");
			Zen.setColor(255,0,0); //change color of inputed name
			Zen.drawText(":",130, 250); //prompt user to enter name
			name = Zen.getEditText(); //get users name
			Zen.drawText(name, 140, 250);

			Zen.setFont("Helvetica-25");
			Zen.setColor(255,255,255);
			Zen.drawText("Click inside the box when you're done =>",40, 310); //prompt user to enter name
			
			Zen.setColor(96, 202, 89);
			Zen.fillRect(515, 280, width, height);
			

			
			if (((mouseClickX >= 515) && (mouseClickX <= 515+width)) && ((mouseClickY >= 280) && (mouseClickY <= 280+height))){
				break; //break out of first screen
			}
			
			
			
		}
		
//Second screen => choose difficulty
		
		while (Zen.isRunning()) {
			//background color of main screen
			Zen.setColor(38, 104, 128);
			Zen.fillRect(0, 0, Zen.getZenWidth(), Zen.getZenHeight());
			
			mouseClickX = Zen.getMouseClickX(); //get X coordinate
			mouseClickY = Zen.getMouseClickY(); //get Y coordinate

			
			//title of the game
			Zen.setFont("Helvetica-44");
			Zen.setColor(0,0,255);
			Zen.drawText("Hello", 200, 60); 
			Zen.setColor(0,255,0);
			Zen.drawText(name + "!",315, 60); 
			Zen.setColor(198,70,70);
			Zen.drawText("Rules of the Game", 145, 110); 
		
			
			//rules of the game
			Zen.setFont("Helvetica-20");
			Zen.drawText("1) Enter the text that appears on screen before it gets to the bottom.", 5, 150); 
			Zen.drawText("2) Every " + NUMBERS_OF_TEXTS_TO_BEAT + " texts beaten, you move on to the next level.", 5, 190); 
			Zen.drawText("3) You have " + NUMBER_OF_LIVES + " lives. Meaning if you let it get to the bottom ", 5, 230);
			Zen.drawText(NUMBER_OF_LIVES + " times, it will be game over.", 29, 250);
			Zen.drawText("4) There are two difficulties, normal and hard. The hard difficulty ", 5, 290);
			Zen.drawText("introduces characters.", 29, 310);
			 
			//choose difficulty
			
			Zen.setColor(0,255,0);
			Zen.drawText("Easy =>", 110, 400);
			Zen.fillRect(190, 370, 50, 50);
			
			//user chose easy difficulty
			if (((mouseClickX >= 190) && (mouseClickX <= 190+50)) && ((mouseClickY >= 370) && (mouseClickY <= 370+50))){
				level = 1; //increments speed by 1
				break; //break out of first screen
			}
			
			Zen.setColor(255,0,0);
			Zen.drawText("Hard =>", 330, 400);
			Zen.fillRect(410, 370, 50, 50);
			
			//user chose hard difficulty
			if (((mouseClickX >= 410) && (mouseClickX <= 410+50)) && ((mouseClickY >= 370) && (mouseClickY <= 370+50))){
				startingSpeed = 2; //start out at a faster speed
				break; //break out of first screen
			}
			
			Zen.drawText("X: " + mouseClickX + " Y: " + mouseClickY,0, 440); //coordinates for the location in X and Y
			
		}
		
		//Zen.drawText("X: " + mouseClickX + " Y: " + mouseClickY,430, 440); //coordinates for the location in X and Y
		
		//initialize variables (game)
		int y=Zen.getZenHeight() / 2, dx=0, dy=startingSpeed+level, score=0, x=0;
		
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
				Zen.setColor((30*level)%255, (40*level)%255, (50*level)%255); //randomize color depending on level
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
			
			
			Zen.sleep(90-level);// sleep for 90 milliseconds and get less and less with every level

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
		Zen.drawText(name + ", here are your highscores!",5, Zen.getZenHeight()/3); //print "Game Over"
		Zen.setFont("Helvetica-18");
		Zen.drawText(amountPlayed + ". " + "Highest Score: " + highscores[0][0] + " | " + "Highest Streak: " + highscores[1][0] + " | " + "Highest Level Reached: " + highscores[2][0],5,Zen.getZenHeight() / 2); //print "Game Over"
		

	}

}
