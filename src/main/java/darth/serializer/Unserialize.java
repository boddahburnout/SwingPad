package darth.serializer;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Unserialize {
    public Object Unserialize(String directory, String filename) {
        Object object = null;
        try
        {
            FileInputStream myFileInputStream = new FileInputStream(directory +"\\"+filename);
            ObjectInputStream myObjectInputStream = new ObjectInputStream(myFileInputStream);
            object = myObjectInputStream.readObject();
            myObjectInputStream.close();
        } catch (Exception e) {
            new Serialize(directory, filename, null);
            System.out.println("Failed to read serialized data");
        }
        return object;
    }
}
