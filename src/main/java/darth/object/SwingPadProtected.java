package darth.object;

import java.awt.*;
import java.io.Serializable;

public class SwingPadProtected extends SwingPad implements Serializable {
    Object pass;
    Font font;
    Color bg;
    Color text;

    public SwingPadProtected(Font font, Color bg, Color text, Object pass, String data) {
        super(font, bg, text, data);
        this.font=font;
        this.bg=bg;
        this.text=text;
        this.pass=pass;
    }

    public Object getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
