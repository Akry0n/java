
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.io.File;
import java.util.ArrayList;

public class contacts {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new contacts().createUI());
    }

    public void createUI() {
        JFrame frame = new JFrame("Developer Contacts");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize( 2000, 1080);

        Color fontColor = Color.WHITE;
        Color scrollbarThumb = new Color(50, 50, 50);   // Dark gray
        Color scrollbarTrack = new Color(80, 60, 40);   // Brownish track

        // Main panel with background image
        ImagePanel mainPanel = new ImagePanel("D://NEW VERSION KIOSK//java//icon//background.jpg");
        mainPanel.setLayout(new BorderLayout());

        // Create Back button
        JButton backButton = new JButton("Back");
        backButton.setFocusPainted(false);
        backButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        backButton.setBackground(new Color(80, 60, 40));
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Back button functionality
        backButton.addActionListener(e -> {
            // Open the MainPageUI window
            SwingUtilities.invokeLater(() -> new MainPageUI(null));

            // Dispose of the Contacts window
            ((JFrame) SwingUtilities.getWindowAncestor(backButton)).dispose();
        });

        // Title label
        JLabel titleLabel = new JLabel("Developer Contacts");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(fontColor);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Top panel to hold Back button and Title
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Contact panel
        JPanel contactPanel = new JPanel();
        contactPanel.setLayout(new BoxLayout(contactPanel, BoxLayout.Y_AXIS));
        contactPanel.setOpaque(false);

        // Developer info: name, position, email, GitHub, and image path
        String[][] devs = {
            {"john Kenneth Reside", "Software Engineer", "alice@example.com", "https://github.com/alicej", "images/dev1.png"},
            {"Angelo Orate", "Frontend Developer", "bob@example.com", "https://github.com/bobsmith", "images/dev2.png"},
            {"Jerome Lee Serapon", "Backend Developer", "charlie@example.com", "https://github.com/charlieb", "images/dev3.png"},
            {"Gavriel Tamondong", "Full Stack Developer", "diana@example.com", "https://github.com/dianalee", "images/dev4.png"},
            //{"Raiell Jared", "Data Scientist", "ethan@example.com", "https://github.com/ethanz", "images/dev5.png"},
            {"Ralph jimrie Columbres", "UI/UX Designer", "fiona@example.com", "https://github.com/fionad", "images/dev6.png"},
            //{"Titiana Annika dellonte Abarabar", "DevOps Engineer", "george@example.com", "https://github.com/georgewhite", "images/dev7.png"},
            {"Timothy Dela Cruz", "Product Manager", "hannah@example.com", "https://github.com/hannahb", "images/dev8.png"},
            {"Zedick Medrano", "Quality Assurance", "ivan@example.com", "https://github.com/ivanm", "images/dev9.png"}
        };

        for (String[] dev : devs) {
            JPanel devPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            devPanel.setOpaque(false);

            // Image
            JLabel imgLabel;
            if (new File(dev[4]).exists()) {
                ImageIcon icon = new ImageIcon(dev[4]);
                Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                imgLabel = new JLabel(new ImageIcon(img));

            } else {
                imgLabel = new JLabel("[No Image]");
                imgLabel.setForeground(fontColor);
            }

            // Info panel
            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.setOpaque(false);

            JLabel nameLabel = new JLabel("Name: " + dev[0]);
            nameLabel.setForeground(fontColor);

            JLabel positionLabel = new JLabel("Position: " + dev[1]);
            positionLabel.setForeground(fontColor);

            JLabel emailLabel = new JLabel("Email: " + dev[2]);
            emailLabel.setForeground(fontColor);

            JLabel githubLabel = new JLabel("<html>GitHub: <a href='" + dev[3] + "'>" + dev[3] + "</a></html>");
            githubLabel.setForeground(Color.WHITE);
            githubLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            githubLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        Desktop.getDesktop().browse(new URI(dev[3]));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            infoPanel.add(nameLabel);
            infoPanel.add(positionLabel);
            infoPanel.add(emailLabel);
            infoPanel.add(githubLabel);

            devPanel.add(imgLabel);
            devPanel.add(Box.createHorizontalStrut(250));
            devPanel.add(infoPanel);

            contactPanel.add(devPanel);
            contactPanel.add(Box.createVerticalStrut(250));
        }

        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(contactPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setPreferredSize(new Dimension(8, Integer.MAX_VALUE));
        verticalScrollBar.setUnitIncrement(20);

        verticalScrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = scrollbarThumb;
                this.trackColor = scrollbarTrack;
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }
        });

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    // Custom panel for background image
    class ImagePanel extends JPanel {
        private Image backgroundImage;

        public ImagePanel(String imagePath) {
            try {
                backgroundImage = new ImageIcon(imagePath).getImage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Ensure the background image always fills the entire panel
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}
