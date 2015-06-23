package SpeechRecognition;


import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;

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
			System.exit(0);
		}
		while(true){

			Result result = recognizer.recognize();
			if (result != null){
				String resultText = result.getBestFinalResultNoFiller();
				System.out.println(resultText);
				if(resultText.toLowerCase().equals("hello")){
					try {
						Process p;
						p = Runtime.getRuntime().exec("aplay /home/pi/sounds/hello.wav");
						p.waitFor();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if(resultText.toLowerCase().equals("how are you")){
					try {
						Process p;
						p = Runtime.getRuntime().exec("aplay /home/pi/sounds/good.wav");
						p.waitFor();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if(resultText.toLowerCase().equals("whats going on")){
					try {
						Process p;
						p = Runtime.getRuntime().exec("aplay /home/pi/sounds/nothing.wav");
						p.waitFor();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if(resultText.toLowerCase().equals("whats your name")){
					try {
						Process p;
						p = Runtime.getRuntime().exec("aplay /home/pi/ralph.wav");
						p.waitFor();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if(resultText.toLowerCase().equals("bye")){
					try {
						Process p;
						//p = Runtime.getRuntime().exec("aplay /home/pi/bye.wav");
						//p.waitFor();
						System.out.println(resultText);
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
