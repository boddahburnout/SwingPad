package darth.settings;

import java.awt.*;
import java.io.Serializable;

public class UserSettings implements Serializable {
    Color bgColor;
    Color fontColor;
    Font font;
    boolean isWordWrap;

    public UserSettings(Color bgColor, Color fontColor, Font font, boolean isWordWrap) {
        this.bgColor = bgColor;
        this.fontColor = fontColor;
        this.font = font;
        this.isWordWrap = isWordWrap;
    }

    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }

    public void setWordWrap(boolean wordWrap) {
        isWordWrap = wordWrap;
    }

    public Color getBgColor() {
        return bgColor;
    }

    public Color getFontColor() {
        return fontColor;
    }

    public Font getFont() {
        return font;
    }

    public boolean getWordWrap() {
        return this.isWordWrap;
    }
}
