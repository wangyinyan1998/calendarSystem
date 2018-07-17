package SavingOperation;

import Event.Event;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
/**
 * Created by ZZY on 2018/6/16.
 */
public class SerializedSaving {
    private static File serializedLocal = new File("serializedLocal.txt");
    public static void saveBeforeClose(ArrayList<Event> list){
        saveBeforeClose(list,serializedLocal);
    }

    public static void saveBeforeClose(ArrayList<Event> list,File f){
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
            oos.writeObject(list);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void readAfterOpen(){
        ArrayList<Event> result = file2Array(serializedLocal);
        Event.setEvents(result);
    }

    public static ArrayList<Event> file2Array(File f){
        try{
            if(f.length() != 0) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
                ArrayList<Event> result = (ArrayList<Event>) ois.readObject();
                return result;
            }
            else
                return new ArrayList<Event>();
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
