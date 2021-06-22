package darth.serializer;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Serialize {
    public Serialize(String directory, String filename, Object object) {
        System.out.println(directory);
        try
        {
            if (object != null) {
                FileOutputStream myFileOutputStream = new FileOutputStream(directory + "\\" + filename);
                ObjectOutputStream myObjectOutputStream = new ObjectOutputStream(myFileOutputStream);
                myObjectOutputStream.writeObject(object);
                myObjectOutputStream.close();
            } else {
                System.out.println("1");
            }
        }
        catch (NullPointerException e)
        {

        } catch (Exception e ) {

        }
    }
}
