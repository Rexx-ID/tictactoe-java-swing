package src;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class GameFrame extends JFrame {

    private Player        currentPlayer;
    private PlayerService playerService;
    private GameLogic     gameLogic;

    private JButton[] buttons;
    private JLabel    lblStatus;
    private JButton   btnNewGame;
    private JButton   btnBackMenu;
    private boolean   gameOver;

    public GameFrame(Player player) {
        this.currentPlayer = player;
        this.playerService = new PlayerService();
        this.gameLogic     = new GameLogic();
        this.gameOver      = false;

        initComponents();
        setupLayout();
        setupEventHandlers();


    }

    private void initComponents() {
        setTitle("Tic-Tac-Toe - Game (Player Duluan)");
        setSize(400, 480);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        buttons = new JButton[9];
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton(" ");
            buttons[i].setFont(new Font("Arial", Font.BOLD, 36));
            buttons[i].setFocusPainted(false);
            buttons[i].setBackground(Color.WHITE);
            buttons[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        // ✅ Label awal: giliran player
        lblStatus = new JLabel("Giliran kamu (X) — Kamu duluan!", SwingConstants.CENTER);
        lblStatus.setFont(new Font("Arial", Font.BOLD, 15));
        lblStatus.setForeground(new Color(34, 139, 34));

        btnNewGame  = new JButton("🔄 Game Baru");
        btnBackMenu = new JButton("🏠 Menu Utama");
    }

    private void setupLayout() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel lblTitle    = new JLabel("TIC-TAC-TOE", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(new Color(30, 100, 200));
        headerPanel.add(lblTitle,  BorderLayout.NORTH);
        headerPanel.add(lblStatus, BorderLayout.SOUTH);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel boardPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        boardPanel.setBackground(new Color(30, 100, 200));
        boardPanel.setBorder(BorderFactory.createLineBorder(new Color(30, 100, 200), 3));
        for (int i = 0; i < 9; i++) boardPanel.add(buttons[i]);
        mainPanel.add(boardPanel, BorderLayout.CENTER);

        JPanel ctrlPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        ctrlPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        styleButton(btnNewGame,  new Color(34, 139, 34));
        styleButton(btnBackMenu, new Color(70, 130, 180));
        ctrlPanel.add(btnNewGame);
        ctrlPanel.add(btnBackMenu);
        mainPanel.add(ctrlPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void styleButton(JButton btn, Color color) {
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void setupEventHandlers() {
        for (int i = 0; i < buttons.length; i++) {
            final int index = i;
            buttons[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    handlePlayerMove(index);
                }
            });
        }

        btnNewGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });

        btnBackMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goToMainMenu();
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                goToMainMenu();
            }
        });
    }

    private void handlePlayerMove(int index) {
        if (gameOver) return;

        boolean moved = gameLogic.makeMove(index, 'X');
        if (!moved) return;

        buttons[index].setText("X");
        buttons[index].setForeground(new Color(30, 100, 200));
        buttons[index].setEnabled(false);

        if (gameLogic.checkWinner('X')) { finishGame("WIN");  return; }
        if (gameLogic.isDraw())         { finishGame("DRAW"); return; }

        lblStatus.setText("Komputer sedang berpikir...");
        lblStatus.setForeground(new Color(178, 34, 34));

        Timer timer = new Timer(400, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ((Timer) e.getSource()).stop();
                doComputerMove();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void doComputerMove() {
        int compIndex = gameLogic.computerMove();
        if (compIndex == -1) { finishGame("DRAW"); return; }

        gameLogic.makeMove(compIndex, 'O');
        buttons[compIndex].setText("O");
        buttons[compIndex].setForeground(new Color(178, 34, 34));
        buttons[compIndex].setEnabled(false);

        if (gameLogic.checkWinner('O')) { finishGame("LOSE"); return; }
        if (gameLogic.isDraw())         { finishGame("DRAW"); return; }

        lblStatus.setText("Giliran kamu (X)");
        lblStatus.setForeground(new Color(34, 139, 34));
    }

    private void finishGame(String result) {
        gameOver = true;
        for (JButton btn : buttons) btn.setEnabled(false);

        playerService.updateStatistics(currentPlayer, result);
        Player updatedPlayer = playerService.getPlayerById(currentPlayer.getId());
        if (updatedPlayer != null) currentPlayer = updatedPlayer;

        String pesan; String judul; int icon;
        if (result.equals("WIN")) {
            pesan = "🎉 Selamat! Kamu MENANG!\n+10 poin ditambahkan.";
            judul = "Menang!"; icon = JOptionPane.INFORMATION_MESSAGE;
            lblStatus.setText("🎉 Kamu MENANG!");
            lblStatus.setForeground(new Color(34, 139, 34));
        } else if (result.equals("LOSE")) {
            pesan = "😞 Kamu KALAH! Komputer menang.\n+0 poin.";
            judul = "Kalah"; icon = JOptionPane.ERROR_MESSAGE;
            lblStatus.setText("😞 Kamu KALAH!");
            lblStatus.setForeground(new Color(178, 34, 34));
        } else {
            pesan = "🤝 SERI! Tidak ada pemenang.\n+3 poin ditambahkan.";
            judul = "Seri"; icon = JOptionPane.WARNING_MESSAGE;
            lblStatus.setText("🤝 Permainan SERI!");
            lblStatus.setForeground(new Color(218, 165, 32));
        }
        JOptionPane.showMessageDialog(this, pesan, judul, icon);
    }

    private void resetGame() {
        gameLogic.resetBoard();
        gameOver = false;

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setText(" ");
            buttons[i].setEnabled(true);
            buttons[i].setBackground(Color.WHITE);
        }

        // ✅ Reset: player jalan duluan lagi
        lblStatus.setText("Giliran kamu (X) — Kamu duluan!");
        lblStatus.setForeground(new Color(34, 139, 34));

        // Tidak ada Timer komputer — tunggu klik player
    }

    private void goToMainMenu() {
        MainMenuFrame menuFrame = new MainMenuFrame(currentPlayer);
        menuFrame.setVisible(true);
        this.dispose();
    }
}