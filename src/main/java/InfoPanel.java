import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InfoPanel extends JPanel {

    JLabel fullName = new JLabel("John Doe");
    JLabel nickname = new JLabel("nickname");
    JLabel phone = new JLabel("80000000000000000000000000000000000000 000 00 000000000000000000000000000000000000000000000000000000000" +
            "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000" +
            "0000000000000000000000000000000000000000000000000000000000000000000000000000000000");

    public InfoPanel() {
        this.setLayout(new GridLayout(2, 0));
//        setBackground(Color.WHITE);

        JPanel general = new JPanel(new GridLayout(0, 1 ));
        setBackground(Color.WHITE);
        this.add(general);

        JLabel avatar = new JLabel(new ImageIcon(
            new ImageIcon(getClass().getResource("contact.png"))
                .getImage()
                .getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
//        general.add(avatar);

        JPanel generalInfo = new JPanel(new GridLayout(3, 0));
        setBackground(Color.WHITE);
        general.add(generalInfo);



        generalInfo.add(fullName);
        generalInfo.add(nickname);
        generalInfo.add(phone);

        JPanel additional = new JPanel(new GridLayout());
        setBackground(Color.WHITE);
        this.add(additional);
    }

    public void reset(HashMap<String,   String> contact) {
        this.fullName.setText(contact.get("name") + " " + contact.get("surname"));
        this.nickname.setText(contact.get("nickname"));
        this.phone.setText(contact.get("phone"));
    }


}
