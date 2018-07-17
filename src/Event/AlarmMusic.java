package Event;

import java.applet.AudioClip;
import java.io.File;
import java.applet.Applet;
import java.net.URL;

/**
 * Created by ZZY on 2018/6/16.
 */
public class AlarmMusic {
    private static File f = new File("alarm.wav");
    private static AudioClip ac = null;

    public AlarmMusic() {
//        try {
//            File f = new File("alarm.wav");
//            //System.out.println(f.length());
//            //System.out.println(f.getAbsolutePath());
//            URL url = f.toURI().toURL();
//            //System.out.println(url);
//            ac = Applet.newAudioClip(url);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
    public static void musicPlay(){
        if(ac == null) {
            try {
                ac = Applet.newAudioClip(f.toURI().toURL());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ac.play();

    }
    public static void musicStop(){
        if(ac == null)
            return;
        else {
            ac.stop();
            ac = null;
        }
    }
}



