package SpeechRecognition;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javazoom.jl.player.Player;

public class SpeechRecognition {
	public SpeechRecognition(String[] args) {
		ConfigurationManager cm;
		if (args.length > 0) {
			cm = new ConfigurationManager(args[0]);
		} else {
			cm = new ConfigurationManager(SpeechRecognition.class.getResource("helloworld.config.xml"));
		}
		Recognizer recognizer = (Recognizer)cm.lookup("recognizer");
		recognizer.allocate();

		Microphone microphone = (Microphone)cm.lookup("microphone");
		if (!microphone.startRecording())
		{
			recognizer.deallocate();
			System.exit(1);
		}
		while(true)
		{

			Result result = recognizer.recognize();
			if (result != null){
				String resultText = result.getBestFinalResultNoFiller();
				System.out.println(resultText);
				if(resultText.toLowerCase().equals("hello")){
					try {
						File f = new File("audio\\nope.mp3");
						FileInputStream fls = new FileInputStream(f);
						BufferedInputStream bls = new BufferedInputStream(fls);
						
						Player p = new Player(bls);
						p.play();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public static void main(String[] args){
		new SpeechRecognition(args);
	}

}
