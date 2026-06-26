package src;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;


public class MainMenuFrame extends JFrame {

    private Player  currentPlayer;
    private JLabel  lblWelcome;
    private JButton btnStartGame;
    private JButton btnStatistics;
    private JButton btnTopScorers;
    private JButton btnExit;

    public MainMenuFrame(Player player) {
        this.currentPlayer = player;
        initComponents();
        setupLayout();
        setupEventHandlers();
    }

   
    private void initComponents() {
        setTitle("Tic-Tac-Toe - Menu Utama");
        setSize(380, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        lblWelcome    = new JLabel("Halo, " + currentPlayer.getUsername() + "!", SwingConstants.CENTER);
        btnStartGame  = new JButton("▶  Mulai Game");
        btnStatistics = new JButton("📊  Statistik Saya");
        btnTopScorers = new JButton("🏆  Top 5 Pemain");
        btnExit       = new JButton("❌  Keluar");
    }

    
    private void setupLayout() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

       
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel lblTitle    = new JLabel("TIC-TAC-TOE", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setForeground(new Color(30, 100, 200));
        lblWelcome.setFont(new Font("Arial", Font.PLAIN, 14));
        lblWelcome.setForeground(new Color(80, 80, 80));
        headerPanel.add(lblTitle, BorderLayout.NORTH);
        headerPanel.add(lblWelcome, BorderLayout.SOUTH);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

       
        JPanel btnPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        styleMenuButton(btnStartGame,  new Color(34, 139, 34));   // hijau
        styleMenuButton(btnStatistics, new Color(70, 130, 180));  // biru
        styleMenuButton(btnTopScorers, new Color(218, 165, 32));  // emas
        styleMenuButton(btnExit,       new Color(178, 34, 34));   // merah

        btnPanel.add(btnStartGame);
        btnPanel.add(btnStatistics);
        btnPanel.add(btnTopScorers);
        btnPanel.add(btnExit);
        mainPanel.add(btnPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    
    private void styleMenuButton(JButton btn, Color color) {
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(280, 45));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    
    private void setupEventHandlers() {
        
        btnStartGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GameFrame gameFrame = new GameFrame(currentPlayer);
                gameFrame.setVisible(true);
                MainMenuFrame.this.dispose();
            }
        });

        
        btnStatistics.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StatisticsFrame statsFrame = new StatisticsFrame(currentPlayer);
                statsFrame.setVisible(true);
            }
        });

        
        btnTopScorers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TopScorersFrame topFrame = new TopScorersFrame();
                topFrame.setVisible(true);
            }
        });

        
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                    MainMenuFrame.this,
                    "Apakah kamu yakin ingin keluar?",
                    "Konfirmasi Keluar",
                    JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    
    public void updateWelcomeLabel() {
        lblWelcome.setText("Halo, " + currentPlayer.getUsername() + "!");
    }
}