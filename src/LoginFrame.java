package src;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class LoginFrame extends JFrame {

    private JTextField     txtUsername;
    private JPasswordField txtPassword;
    private JButton        btnLogin;
    private JLabel         lblTitle;
    private JLabel         lblUsername;
    private JLabel         lblPassword;
    private PlayerService  playerService;

    public LoginFrame() {
        playerService = new PlayerService();
        initComponents();
        setupLayout();
        setupEventHandlers();
    }

    
    private void initComponents() {
        setTitle("Tic-Tac-Toe - Login");
        setSize(380, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setResizable(false);

        lblTitle    = new JLabel("TIC-TAC-TOE GAME", SwingConstants.CENTER);
        lblUsername = new JLabel("Username:");
        lblPassword = new JLabel("Password:");
        txtUsername = new JTextField(20);
        txtPassword = new JPasswordField(20);
        btnLogin    = new JButton("Login");
    }

    
    private void setupLayout() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(new Color(30, 100, 200));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill   = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        formPanel.add(lblUsername, gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        formPanel.add(txtUsername, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        formPanel.add(lblPassword, gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        formPanel.add(txtPassword, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnLogin.setPreferredSize(new Dimension(120, 35));
        btnLogin.setFont(new Font("Arial", Font.BOLD, 13));
        btnLogin.setBackground(new Color(30, 100, 200));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnPanel.add(btnLogin);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    
    private void setupEventHandlers() {
       
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doLogin();
            }
        });

        
        txtPassword.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doLogin();
            }
        });
    }

   
    private void doLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

       
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(
                LoginFrame.this,
                "Username dan password tidak boleh kosong!",
                "Peringatan",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        
        Player player = playerService.login(username, password);

        if (player != null) {
            
            JOptionPane.showMessageDialog(
                LoginFrame.this,
                "Selamat datang, " + player.getUsername() + "!",
                "Login Berhasil",
                JOptionPane.INFORMATION_MESSAGE
            );
            MainMenuFrame menuFrame = new MainMenuFrame(player);
            menuFrame.setVisible(true);
            this.dispose(); 
        } else {
            
            JOptionPane.showMessageDialog(
                LoginFrame.this,
                "Username atau password salah!\nSilakan coba lagi.",
                "Login Gagal",
                JOptionPane.ERROR_MESSAGE
            );
            txtPassword.setText(""); 
            txtUsername.requestFocus();
        }
    }
}