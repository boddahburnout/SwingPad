package darth.object;

import java.awt.*;
import java.io.Serializable;

public class SwingPadProtected extends SwingPad implements Serializable {
    Object pass;
    String data;
    Font font;
    Color bg;
    Color text;

    public SwingPadProtected(Font font, Color bg, Color text, Object pass, String data) {
        super(font, bg, text);
        this.font=font;
        this.bg=bg;
        this.text=text;
        this.pass=pass;
        this.data=data;
    }

    public Object getPass() {
        return pass;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
