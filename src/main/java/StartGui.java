import darth.encryption.Encrypt;
import darth.frames.PadFrame;

public class StartGui {
    public static void main(String[] args) {
        Encrypt encrypt = new Encrypt();
        encrypt.setKey(args[0]);
        new PadFrame().initGui(encrypt);
    }
}
