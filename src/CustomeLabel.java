import javax.swing.*;
import java.awt.*;

public class CustomeLabel extends JLabel {


    public  CustomeLabel(int size, int x, int y, int w, int h, int fontWeight,String t){
        setFont(new Font("Default",fontWeight, size));
        setBounds(x,y,w,h);
        setText(t);

    }
    public void twoRowDisplay(String name, Object data,String unit){
        setText("<html><div>" + name + "<br>" + data + " " + unit + "</div></html>");
    }



}
