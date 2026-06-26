package src;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class TopScorersFrame extends JFrame {

    private PlayerService     playerService;
    private JTable            table;
    private DefaultTableModel tableModel;
    private JButton           btnClose;
    private JButton           btnRefresh;

    public TopScorersFrame() {
        playerService = new PlayerService();
        initComponents();
        setupLayout();
        setupEventHandlers();
        loadTopScorers(); 
    }

    private void initComponents() {
        setTitle("🏆 Top 5 Pemain");
        setSize(500, 380);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        
        String[] columns = {"#", "Username", "Menang", "Kalah", "Seri", "Skor"};
        tableModel = new DefaultTableModel(columns, 0) {
            
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(30, 100, 200));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(200, 220, 255));
        table.setGridColor(new Color(200, 200, 200));
        table.setShowGrid(true);

        // Atur lebar kolom
        table.getColumnModel().getColumn(0).setPreferredWidth(30);  // #
        table.getColumnModel().getColumn(1).setPreferredWidth(150); // Username
        table.getColumnModel().getColumn(2).setPreferredWidth(70);  // Menang
        table.getColumnModel().getColumn(3).setPreferredWidth(60);  // Kalah
        table.getColumnModel().getColumn(4).setPreferredWidth(50);  // Seri
        table.getColumnModel().getColumn(5).setPreferredWidth(70);  // Skor

        // Rata tengah semua kolom
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        btnClose   = new JButton("Tutup");
        btnRefresh = new JButton("🔄 Refresh");
    }

    private void setupLayout() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Header
        JLabel lblTitle = new JLabel("🏆  Top 5 Pemain Terbaik", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(new Color(218, 165, 32));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        // Tabel
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(30, 100, 200), 2));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Tombol bawah
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        styleButton(btnRefresh, new Color(34, 139, 34));
        styleButton(btnClose,   new Color(70, 130, 180));
        btnRefresh.setPreferredSize(new Dimension(120, 35));
        btnClose.setPreferredSize(new Dimension(100, 35));
        btnPanel.add(btnRefresh);
        btnPanel.add(btnClose);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

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
        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        btnRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadTopScorers();
            }
        });
    }

    /**
     * Mengambil data Top 5 dari database dan mengisi tabel.
     */
    private void loadTopScorers() {
        // Kosongkan baris tabel dulu
        tableModel.setRowCount(0);

        ArrayList<Player> topList = playerService.getTopFiveScorers();

        if (topList.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Belum ada data pemain di database.",
                "Info",
                JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        // Tambahkan setiap pemain ke tabel
        int rank = 1;
        for (Player p : topList) {
            Object[] row = {
                rank,
                p.getUsername(),
                p.getWins(),
                p.getLosses(),
                p.getDraws(),
                p.getScore()
            };
            tableModel.addRow(row);
            rank++;
        }
    }
}