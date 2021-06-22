package darth.object;

import java.awt.*;
import java.io.Serializable;

abstract class SwingPadBase implements Serializable {
    Font font;
    Color bg;
    Color text;
    String data;

    public SwingPadBase(Font font, Color bg, Color text, String data) {
        this.font=font;
        this.bg=bg;
        this.text=text;
        this.data=data;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
