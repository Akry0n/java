


import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.AlphaComposite;


public class RestaurantOrderGUI {
    public RestaurantOrderGUI() {
        JFrame frame = new JFrame("SARAP LIKHA KIOSK");
        frame.setTitle("SARAP LIKHA KIOSK");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1366, 768);
        frame.setLocationRelativeTo(null);
        // MESSAGE POP UP WHEN THE WINDOW IS CLOSING
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        
        BackgroundPanel background = new BackgroundPanel("D://NEW VERSION KIOSK//java//icon//background.jpg");
        background.setLayout(new BorderLayout());

        // Create a panel for the main content
        JPanel contentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        contentPanel.setOpaque(false);

        // Create containers with glass effect and blur
        GlassPanel dineInPanel = new GlassPanel();
        GlassPanel takeoutPanel = new GlassPanel();

        dineInPanel.setPreferredSize(new Dimension(450, 600));
        takeoutPanel.setPreferredSize(new Dimension(450, 600));
        dineInPanel.setLayout(new BorderLayout());
        takeoutPanel.setLayout(new BorderLayout());

        dineInPanel.add(createContent("DINE IN", "D://NEW VERSION KIOSK//java//icon//restaurant-cutlery-circular-symbol-of-a-spoon-and-a-fork-in-a-circle.png"));
        takeoutPanel.add(createContent("TAKEOUT", "D://NEW VERSION KIOSK//java//icon//take-away.png"));

        contentPanel.add(dineInPanel);
        contentPanel.add(takeoutPanel);
        
        // Create back button panel
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        backButtonPanel.setOpaque(false);
        
        // Create back button
        RoundedButton backButton = new RoundedButton("BACK");
        backButton.setPreferredSize(new Dimension(100, 40));
        backButton.setBackground(new Color(139, 69, 19));
        backButton.setForeground(new Color(255, 215, 0));
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        
        // Add action listener to back button
        backButton.addActionListener(e -> {
            frame.dispose();
            new Mainapp();
        });
        
        backButtonPanel.add(backButton);
        
        // Add panels to background
        background.add(backButtonPanel, BorderLayout.NORTH);
        background.add(contentPanel, BorderLayout.CENTER);

        frame.setContentPane(background);
        
        ImageIcon icon = new ImageIcon("D://NEW VERSION KIOSK//java//icon//n" + "ew logo.png");
        Image scaledIcon = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledIcon);
        frame.setIconImage(icon.getImage());
        
        frame.setVisible(true);
    }

    private static JPanel createContent(String buttonText, String iconPath) {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);

        ImageIcon originalIcon = new ImageIcon(iconPath);
        Image scaledImage = originalIcon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);

        JLabel iconLabel = new JLabel(resizedIcon, JLabel.CENTER);
        iconLabel.setVerticalAlignment(SwingConstants.TOP);
        iconLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 30, 0));
        iconLabel.setPreferredSize(new Dimension(250, 350));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);

        RoundedButton button = new RoundedButton(buttonText);
        button.setPreferredSize(new Dimension(300, 100));
        button.setBackground(new Color(139, 69, 19));
        button.setForeground(new Color(255, 215, 0));
        button.setFont(new Font("Times New Roman", Font.BOLD, 32));

        button.addActionListener(e -> { // Action when button is clicked
            int choice = JOptionPane.showConfirmDialog(
                contentPanel,
                "You selected " + buttonText + "\nDo you want to continue?", // Message to display
                "Confirm Selection", // Title of the dialog
                JOptionPane.YES_NO_OPTION, // Option type
                JOptionPane.QUESTION_MESSAGE // Message type
            );

            if (choice == JOptionPane.YES_OPTION) {
                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(contentPanel);
                currentFrame.dispose();
                
                MainPageUI mainPage = new MainPageUI(buttonText);
                mainPage.setVisible(true);
            }
        });

        buttonPanel.add(button);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));

        contentPanel.add(iconLabel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        return contentPanel;
    }
}

class GlassPanel extends JPanel {
    private static final int BLUR_RADIUS = 10;
    private BufferedImage blurBuffer;

    public GlassPanel() {
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Create blur effect
        int width = getWidth();
        int height = getHeight();

        // Create or update blur buffer
        if (blurBuffer == null || blurBuffer.getWidth() != width || blurBuffer.getHeight() != height) {
            blurBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        }

        // Paint background into blur buffer
        Graphics2D bg = blurBuffer.createGraphics();
        super.paintComponent(bg);
        bg.dispose();

        // Apply blur effect using BLUR_RADIUS
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
        
        // Draw semi-transparent panel with blur radius influence
        g2.setColor(new Color(245, 222, 179, 180 - BLUR_RADIUS));
        g2.fillRoundRect(0, 0, width, height, 25, 25);

        // Add glass effect
        GradientPaint gp = new GradientPaint(
            0, 0, new Color(255, 255, 255, 50),
            0, height, new Color(255, 255, 255, 0)
        );
        g2.setPaint(gp);
        g2.fillRoundRect(0, 0, width, height, 25, 25);

        // Draw border
        g2.setColor(new Color(255, 255, 255, 90));
        g2.drawRoundRect(0, 0, width - 1, height - 1, 25, 25);

        g2.dispose();
    }
}

class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        backgroundImage = icon.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}

class RoundedButton extends JButton {
    private Timer fadeTimer;
    private float shadowOpacity = 0.3f;
    private boolean isHovered = false;
    private final int SHADOW_SIZE = 5;
    
    public RoundedButton(String text) {
        super(text);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setBorderPainted(false);
        
        fadeTimer = new Timer(50, e -> {
            if (isHovered && shadowOpacity < 0.8f) {
                shadowOpacity += 0.1f;
            } else if (!isHovered && shadowOpacity > 0.3f) {
                shadowOpacity -= 0.1f;
            } else {
                fadeTimer.stop();
            }
            repaint();
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                fadeTimer.start();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                fadeTimer.start();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(new Color(0, 0, 0, (int)(shadowOpacity * 255)));
        g2.fillRoundRect(SHADOW_SIZE, SHADOW_SIZE, getWidth() - SHADOW_SIZE, getHeight() - SHADOW_SIZE, 60, 60);
        
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - SHADOW_SIZE, getHeight() - SHADOW_SIZE, 60, 60);
        
        FontMetrics fm = g2.getFontMetrics();
        Rectangle2D rect = fm.getStringBounds(getText(), g2);
        
        int textX = (getWidth() - SHADOW_SIZE - (int) rect.getWidth()) / 2;
        int textY = (getHeight() - SHADOW_SIZE - (int) rect.getHeight()) / 2 + fm.getAscent();
        
        g2.setColor(getForeground());
        g2.drawString(getText(), textX, textY);
        
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getForeground());
        g2.drawRoundRect(0, 0, getWidth() - SHADOW_SIZE - 1, getHeight() - SHADOW_SIZE - 1, 60, 60);
        g2.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension size = super.getPreferredSize();
        size.width += SHADOW_SIZE;
        size.height += SHADOW_SIZE;
        return size;
    }
}
