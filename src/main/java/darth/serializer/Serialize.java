package darth.serializer;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Serialize {
    public Serialize(String directory, String filename, Object object) {
        try
        {
            if (object != null) {
                FileOutputStream myFileOutputStream = new FileOutputStream(directory + "\\" + filename);
                ObjectOutputStream myObjectOutputStream = new ObjectOutputStream(myFileOutputStream);
                myObjectOutputStream.writeObject(object);
                myObjectOutputStream.close();
            }
        }
        catch (NullPointerException e)
        {

        } catch (Exception e ) {

        }
    }
}
