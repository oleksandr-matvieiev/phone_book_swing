import javax.swing.SwingUtilities;

public class AppStarter {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AppFrame::new);
    }
}
