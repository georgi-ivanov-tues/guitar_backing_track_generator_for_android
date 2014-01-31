package version1;

import java.util.Scanner;

public class Main {
	private static Scanner userInput;

	public static void main(String[] args) {
		MP3 mp3 = new MP3("sounds/Stop.mp3");
		mp3.play();
		mp3.stop();
		userInput = new Scanner(System.in);
		String input = userInput.nextLine();
		if(input.equals("stop")){
			mp3.stop();
		}
	}
}
