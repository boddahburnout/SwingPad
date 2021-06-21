package darth.object;

import java.awt.*;
import java.io.Serializable;

abstract class SwingPadBase implements Serializable {
    Font font;
    Color bg;
    Color text;

    public SwingPadBase(Font font, Color bg, Color text) {
        this.font=font;
        this.bg=bg;
        this.text=text;
    }

    public Font getFont() {
        return font;
    }

    public Color getBg() {
        return bg;
    }

    public Color getText() {
        return text;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public void setBg(Color bg) {
        this.bg = bg;
    }

    public void setText(Color text) {
        this.text = text;
    }
}
