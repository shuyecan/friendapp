package myfriend.com.friendapp.Been;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class Notbookbeen extends LitePalSupport {
    String data;
    String conent;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getConent() {
        return conent;
    }

    public void setConent(String conent) {
        this.conent = conent;
    }
}
