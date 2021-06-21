package darth.encryption;

import org.jasypt.util.text.AES256TextEncryptor;

public class Encrypt {
    String key;
    public String Encrypt (String object)
    {
        AES256TextEncryptor aesEncryptor = new AES256TextEncryptor();
        aesEncryptor.setPassword(key);
        String Encrypted = aesEncryptor.encrypt(object);
        return Encrypted;
    }

    public String Decrypt(String object)
    {
        AES256TextEncryptor aesEncryptor = new AES256TextEncryptor();
        aesEncryptor.setPassword(key);
        String decrypted = aesEncryptor.decrypt(object);
        return decrypted;
    }
    public void setKey(String key) {
        this.key=key;
    }
}
