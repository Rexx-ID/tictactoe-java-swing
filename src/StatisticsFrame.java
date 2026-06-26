package src;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;


public class StatisticsFrame extends JFrame {

    private Player        currentPlayer;
    private PlayerService playerService;

    private JLabel lblWins;
    private JLabel lblLosses;
    private JLabel lblDraws;
    private JLabel lblScore;
    private JButton btnClose;

    public StatisticsFrame(Player player) {
        this.playerService = new PlayerService();

        // Ambil data terbaru dari database
        Player freshData = playerService.getPlayerById(player.getId());
        this.currentPlayer = (freshData != null) ? freshData : player;

        initComponents();
        setupLayout();
        setupEventHandlers();
    }

    private void initComponents() {
        setTitle("Statistik - " + currentPlayer.getUsername());
        setSize(350, 380);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        lblWins   = new JLabel(String.valueOf(currentPlayer.getWins()));
        lblLosses = new JLabel(String.valueOf(currentPlayer.getLosses()));
        lblDraws  = new JLabel(String.valueOf(currentPlayer.getDraws()));
        lblScore  = new JLabel(String.valueOf(currentPlayer.getScore()));
        btnClose  = new JButton("Tutup");
    }

    private void setupLayout() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        
        JLabel lblTitle = new JLabel("Statistik Saya", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(new Color(30, 100, 200));

        JLabel lblName = new JLabel("Pemain: " + currentPlayer.getUsername(), SwingConstants.CENTER);
        lblName.setFont(new Font("Arial", Font.PLAIN, 13));
        lblName.setForeground(new Color(100, 100, 100));

        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        headerPanel.add(lblTitle);
        headerPanel.add(lblName);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        
        JPanel statsPanel = new JPanel(new GridLayout(4, 2, 10, 15));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        addStatRow(statsPanel, "Menang :",  lblWins,   new Color(34, 139, 34));
        addStatRow(statsPanel, "Kalah  :",  lblLosses, new Color(178, 34, 34));
        addStatRow(statsPanel, "Seri   :",  lblDraws,  new Color(218, 165, 32));
        addStatRow(statsPanel, "Skor   :",  lblScore,  new Color(70, 130, 180));

        mainPanel.add(statsPanel, BorderLayout.CENTER);

        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        styleButton(btnClose, new Color(70, 130, 180));
        btnClose.setPreferredSize(new Dimension(120, 35));
        btnPanel.add(btnClose);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    
    private void addStatRow(JPanel panel, String labelText, JLabel valueLabel, Color color) {
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(lbl);

        valueLabel.setFont(new Font("Arial", Font.BOLD, 22));
        valueLabel.setForeground(color);
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(valueLabel);
    }

    private void styleButton(JButton btn, Color color) {
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void setupEventHandlers() {
        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}