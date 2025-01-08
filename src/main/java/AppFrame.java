import java.awt.GridLayout;
import java.awt.event.*;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AppFrame extends JFrame {

    private InfoPanel infoPanel;
    private JTable table;
    private DBHandler handler;

    public AppFrame() {
        handler = new DBHandler();

        setTitle("Contacts list");
        setSize(500, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new GridLayout(0, 2));

        table = new JTable(new DefaultTableModel(handler.getNicknames(), new String[]{""}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane list = new JScrollPane(table);
        add(list);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.rowAtPoint(e.getPoint());
                    int col = table.columnAtPoint(e.getPoint());

                    if (row >= 0 && col >= 0) {
                        String nickname = (String) table.getValueAt(row, col);
                        infoPanel.reset(handler.getContactByNickname(nickname));
                    }
                }
            }
        });
        table.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char keyChar = Character.toLowerCase(e.getKeyChar());
                if (keyChar == 'd') {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow >= 0) {
                        String nickname = (String) table.getValueAt(selectedRow, 0);
                        handler.deleteContactByNickname(nickname);
                        updateTable();
                        infoPanel.reset(new HashMap<>());
                    }

                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });


        infoPanel = new InfoPanel();
        add(infoPanel);

        JButton addContactButton = new JButton("AddCont");
        addContactButton.setSize(60, 60);
        addContactButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAddContactDialog();
            }
        });
        add(addContactButton);

        setVisible(true);
    }

    private void openAddContactDialog() {
        JFrame addContactFrame = new JFrame("Add Contact");
        addContactFrame.setSize(300, 200);
        addContactFrame.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(4, 2));

        JTextField nameField = new JTextField();
        JTextField surnameField = new JTextField();
        JTextField nicknameField = new JTextField();
        JTextField phoneField = new JTextField();

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Surname:"));
        panel.add(surnameField);
        panel.add(new JLabel("Nickname:"));
        panel.add(nicknameField);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String surname = surnameField.getText();
                String nickname = nicknameField.getText();
                String phone = phoneField.getText();

                handler.addContact(name, surname, nickname, phone);
                updateTable();

                addContactFrame.dispose();
            }
        });

        panel.add(saveButton);

        addContactFrame.add(panel);
        addContactFrame.setVisible(true);
    }

    private void updateTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setDataVector(handler.getNicknames(), new String[]{""});
    }
}