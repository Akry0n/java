import javax.swing.*;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.image.BufferedImage;

public class MainPageUI extends JFrame {
    private double subtotal;
    private JLabel subtotalLabel;
    private JPanel orderItemsPanel;
    private JPanel foodGrid;
    private final List<FoodItem> orderedItems;
    private final Map<String, List<FoodItem>> categoryMap = FoodCategoryMap.getCategoryMap();
    private List<FoodItem> currentItems = new ArrayList<>();
    private JRadioButton counterPayment;
    private JRadioButton qrPayment;

    public MainPageUI(String restaurantName) {
        this.subtotal = 0;
        this.orderedItems = new ArrayList<>();

        setTitle(restaurantName + " SarapLikha KITCHEN");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        add(createTopBar(), BorderLayout.NORTH);
        add(createMainPanel(), BorderLayout.CENTER);
        add(createOrderPanel(), BorderLayout.EAST);

        setVisible(true);
    }
  
    private JPanel createTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        topBar.setBackground(new Color(245, 245, 245));

        JLabel logoLabel = new JLabel("SarapLikha", SwingConstants.LEFT);
        logoLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        topBar.add(logoLabel, BorderLayout.WEST);

        ImageIcon logoIcon = new ImageIcon(getClass().getClassLoader().getResource("icon//new logo.png"));
        Image scaledImage = logoIcon.getImage().getScaledInstance(50, 40, Image.SCALE_SMOOTH);
        ImageIcon scaledLogoIcon = new ImageIcon(scaledImage);

        JLabel scaledLogoLabel = new JLabel(scaledLogoIcon);
        scaledLogoLabel.setPreferredSize(new Dimension(50, 40));

        JLabel profileIcon = new JLabel("👤", SwingConstants.CENTER);
        profileIcon.setFont(new Font("SansSerif", Font.PLAIN, 22));
        profileIcon.setPreferredSize(new Dimension(100, 40));
        profileIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Add MouseListener to open the Contacts window
        profileIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                // Open the Contacts window
                SwingUtilities.invokeLater(() -> {
                    new contacts().createUI();
                });
                // Dispose of the MainPageUI window
                dispose();
            }
        });

        JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        iconPanel.setOpaque(false);
        iconPanel.add(scaledLogoLabel);
        iconPanel.add(profileIcon);

        topBar.add(iconPanel, BorderLayout.EAST);

        return topBar;
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Banner panel
        JPanel bannerPanel = new JPanel(new BorderLayout());
        bannerPanel.setPreferredSize(new Dimension(600, 220));
        bannerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel bannerImageLabel = new JLabel();
        bannerImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bannerImageLabel.setPreferredSize(new Dimension(600, 120));
        bannerPanel.add(bannerImageLabel, BorderLayout.CENTER);

        List<String> imagePaths = List.of(
 
            "rotationpanelads/Ads/ad1.png",
            "rotationpanelads/Ads/ad2.png",
            "rotationpanelads/Ads/ad3.png",
            "rotationpanelads/Ads/ad4.png",
            "rotationpanelads/Ads/ad5.png",
            "rotationpanelads/Ads/ad6.png",
            "rotationpanelads/Ads/ad7.png"
        );
        setBannerImage(bannerImageLabel, imagePaths.get(0));

        new javax.swing.Timer(8000, e -> {
            int currentIndex = imagePaths.indexOf(bannerImageLabel.getName());
            int nextIndex = (currentIndex >= 0 ? currentIndex + 1 : 1) % imagePaths.size();
            setBannerImage(bannerImageLabel, imagePaths.get(nextIndex));
        }).start();

        mainPanel.add(bannerPanel, BorderLayout.NORTH);

        // Center panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton breakfastButton = createStyledButton("Breakfast", new Color(255, 140, 0));
        JButton lunchButton = createStyledButton("Lunch", new Color(255, 204, 0));
        JButton dinnerButton = createStyledButton("Dinner", new Color(255, 87, 34));

        buttonPanel.add(breakfastButton);
        buttonPanel.add(lunchButton);
        buttonPanel.add(dinnerButton);

        // Food grid (3x3)
        foodGrid = new JPanel(new GridLayout(0, 3, 20, 20));
        foodGrid.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        foodGrid.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(foodGrid);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // smooth scroll
        scrollPane.setPreferredSize(new Dimension(0, 750)); 
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);
            
        // Populate grid
        updateFoodGrid("Dinner");
        breakfastButton.addActionListener(e -> updateFoodGrid("Breakfast"));
        lunchButton.addActionListener(e -> updateFoodGrid("Lunch"));
        dinnerButton.addActionListener(e -> updateFoodGrid("Dinner"));
        centerPanel.add(buttonPanel);
        centerPanel.add(scrollPane);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        return mainPanel;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 40));
        button.setFocusPainted(false);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void setBannerImage(JLabel label, String path) {
        URL imageUrl = getClass().getClassLoader().getResource(path);
        if (imageUrl != null) {
            ImageIcon icon = new ImageIcon(imageUrl);
            Image scaled = icon.getImage().getScaledInstance(label.getWidth() > 0 ? label.getWidth() : 1570, 150, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(scaled));
            label.setText("");
            label.setName(path);
        } else {
            label.setIcon(null);
            label.setText("[Image not found: " + path + "]");
            label.setName("");
        }
    }

    private void updateFoodGrid(String category) {
        currentItems = categoryMap.getOrDefault(category, new ArrayList<>());
        foodGrid.removeAll();
        for (FoodItem item : currentItems) {
            foodGrid.add(createRestaurantCard(item));
        }
        foodGrid.revalidate();
        foodGrid.repaint();
    }

private JPanel createRestaurantCard(FoodItem item) {
    JPanel card = new JPanel(new BorderLayout());
    card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
    card.setBackground(Color.WHITE);
    card.setPreferredSize(new Dimension(180, 400)); // smaller card size
    

    JLabel imgLabel = new JLabel("[No Image]", SwingConstants.CENTER);
    if (item.getImagePath() != null) {
        URL imageUrl = getClass().getClassLoader().getResource(item.getImagePath());
        if (imageUrl != null) {
            ImageIcon icon = new ImageIcon(imageUrl);
            Image scaled = icon.getImage().getScaledInstance(470, 420, Image.SCALE_SMOOTH); // smaller image
            imgLabel = new JLabel(new ImageIcon(scaled), SwingConstants.CENTER);
        } else {
            imgLabel.setText("[Image not found]");
        }
    }
    imgLabel.setPreferredSize(new Dimension(180, 120));
    imgLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    JLabel nameLabel = new JLabel(item.getName());
    nameLabel.setFont(new Font("SansSerif", Font.BOLD, 14)); // smaller font
    nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
    nameLabel.setBorder(BorderFactory.createEmptyBorder(2, 5, 0, 5));

    JLabel priceLabel = new JLabel("$" + item.getPrice());
    priceLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
    priceLabel.setHorizontalAlignment(SwingConstants.LEFT);
    priceLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

    JButton orderButton = new JButton("Order");
    orderButton.setFocusPainted(false);
    orderButton.setBackground(new Color(255, 140, 0));
    orderButton.setForeground(Color.WHITE);
    orderButton.setFont(new Font("SansSerif", Font.BOLD, 12));
    orderButton.setPreferredSize(new Dimension(80, 30));
    orderButton.addActionListener(e -> addToOrder(item));

    JPanel infoPanel = new JPanel(new GridLayout(2, 1));
    infoPanel.setOpaque(false);
    infoPanel.add(nameLabel);
    infoPanel.add(priceLabel);

    JPanel bottomPanel = new JPanel(new BorderLayout());
    bottomPanel.setBackground(Color.WHITE);
    bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    bottomPanel.add(infoPanel, BorderLayout.CENTER);
    bottomPanel.add(orderButton, BorderLayout.EAST);

    card.add(imgLabel, BorderLayout.CENTER);
    card.add(bottomPanel, BorderLayout.SOUTH);

    return card;
}


    private void addToOrder(FoodItem item) {
        boolean itemExists = false;

        // Check if the item already exists in the order
        for (FoodItem orderedItem : orderedItems) {
            if (orderedItem.getName().equals(item.getName())) {
                orderedItem.incrementQuantity(); // Increment quantity if item exists
                itemExists = true;
                break;
            }
        }

        // If the item does not exist, add it to the order
        if (!itemExists) {
            orderedItems.add(new FoodItem(item.getName(), item.getPrice(), item.getImagePath(), 1)); // Add with quantity 1
        }

        updateOrderPanel();
    }

    private void updateOrderPanel() {
        orderItemsPanel.removeAll();

        for (FoodItem item : orderedItems) {
            // Create a box-style panel for each order item
            JPanel itemBox = new JPanel();
            itemBox.setLayout(new BorderLayout(10, 0));
            itemBox.setBackground(Color.WHITE);
            itemBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createLineBorder(new Color(0, 100, 200), 1, true)
            ));
            
            // Left panel for item name and quantity controls
            JPanel leftPanel = new JPanel(new BorderLayout(5, 0));
            leftPanel.setOpaque(false);
            
            // Item name
            JLabel nameLabel = new JLabel(item.getName());
            nameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
            leftPanel.add(nameLabel, BorderLayout.NORTH);
            
            // Quantity control panel
            JPanel quantityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
            quantityPanel.setOpaque(false);
            
            // Quantity label with "x" prefix
            JLabel quantityLabel = new JLabel("x" + item.getQuantity());
            quantityLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
            quantityLabel.setForeground(new Color(100, 100, 100));
            
            quantityPanel.add(quantityLabel);
            leftPanel.add(quantityPanel, BorderLayout.CENTER);
            
            // Right panel for price and remove button
            JPanel rightPanel = new JPanel(new BorderLayout(5, 0));
            rightPanel.setOpaque(false);
            
            // Price label
            JLabel priceLabel = new JLabel("₱" + (item.getPrice() * item.getQuantity()));
            priceLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
            priceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            rightPanel.add(priceLabel, BorderLayout.NORTH);
            
            // Remove button
            JButton removeButton = new JButton("Remove");
            removeButton.setFocusPainted(false);
            removeButton.setBackground(new Color(255, 140, 0));
            removeButton.setForeground(Color.WHITE);
            removeButton.setFont(new Font("SansSerif", Font.BOLD, 12));
            removeButton.setPreferredSize(new Dimension(80, 25)); // Smaller, more compact size
            removeButton.setBorderPainted(false); // Remove border for cleaner look
            removeButton.addActionListener(e -> {
                orderedItems.remove(item);
                updateOrderPanel();
            });
            rightPanel.add(removeButton, BorderLayout.SOUTH);
            
            // Add panels to the item box
            itemBox.add(leftPanel, BorderLayout.WEST);
            itemBox.add(rightPanel, BorderLayout.EAST);
            
            // Add the item box to the order panel
            orderItemsPanel.add(itemBox);
            orderItemsPanel.add(Box.createVerticalStrut(5)); // Add spacing between items
        }

        updateSubtotal();
        orderItemsPanel.revalidate();
        orderItemsPanel.repaint();
    }

    private void updateSubtotal() {
        subtotal = orderedItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity()) // Multiply price by quantity
                .sum();

        subtotalLabel.setText("Sub Total: ₱" + subtotal);
    }

private JPanel createOrderPanel() {
    JPanel orderPanel = new JPanel();
    orderPanel.setLayout(new BoxLayout(orderPanel, BoxLayout.Y_AXIS));
    orderPanel.setPreferredSize(new Dimension(400, 0));
    orderPanel.setBackground(Color.WHITE);
    orderPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

    // Header row with "My Orders"
    JLabel header = new JLabel("My Orders");
    header.setFont(new Font("Arial", Font.BOLD, 20));
    header.setForeground(new Color(50, 50, 50));  // Darker header color
    header.setHorizontalAlignment(SwingConstants.LEFT); // Align text to the left
    header.setAlignmentX(Component.LEFT_ALIGNMENT); // Ensure alignment in BoxLayout
    orderPanel.add(header);

    // Order items panel with scrolling
    orderItemsPanel = new JPanel();
    orderItemsPanel.setLayout(new BoxLayout(orderItemsPanel, BoxLayout.Y_AXIS));
    orderItemsPanel.setBackground(Color.WHITE);

    JScrollPane scrollPane = new JScrollPane(orderItemsPanel);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setPreferredSize(new Dimension(360, 300)); // Adjusted width to prevent horizontal scrollbar
    scrollPane.getViewport().setBackground(Color.WHITE);
    scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
    orderPanel.add(scrollPane);

    // Panel for subtotal, clear button, and checkout button
    JPanel bottomPanel = new JPanel();
    bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
    bottomPanel.setBackground(Color.WHITE);
    bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

    // Subtotal label
    subtotalLabel = new JLabel("Sub Total: ₱" + subtotal);
    subtotalLabel.setFont(new Font("Arial", Font.BOLD, 16));
    subtotalLabel.setForeground(new Color(33, 33, 33));
    bottomPanel.add(subtotalLabel);

    // Clear All button
    JButton clearAllButton = new JButton("Clear All");
    clearAllButton.setFont(new Font("Arial", Font.BOLD, 14));
    clearAllButton.setBackground(new Color(255, 87, 34));  // Warm orange color
    clearAllButton.setForeground(Color.WHITE);
    clearAllButton.setFocusPainted(false);
    clearAllButton.setBorderPainted(false);
    clearAllButton.setPreferredSize(new Dimension(100, 40)); // Smaller than checkout button
    clearAllButton.addActionListener(e -> {
        orderedItems.clear();
        updateOrderPanel();
    });
    
    // Add some spacing between buttons
    bottomPanel.add(Box.createHorizontalGlue());  // Push the button to the right
    bottomPanel.add(clearAllButton);
    bottomPanel.add(Box.createHorizontalStrut(10)); // 10px spacing between buttons

    // Checkout button
    JButton checkoutBtn = new JButton("Check out");
    checkoutBtn.setBackground(new Color(76, 175, 80));  // Green color
    checkoutBtn.setForeground(Color.WHITE);
    checkoutBtn.setFont(new Font("Arial", Font.BOLD, 16));
    checkoutBtn.setFocusPainted(false);
    checkoutBtn.setBorderPainted(false);
    checkoutBtn.setPreferredSize(new Dimension(120, 40)); // Larger, more prominent size

    // Add ActionListener to display order summary
    checkoutBtn.addActionListener(e -> {
        CheckoutHandler.handleCheckout(
            orderedItems,
            subtotal,
            qrPayment.isSelected(),
            orderPanel,
            this
        );
    });

    bottomPanel.add(Box.createHorizontalGlue());  // Push the button to the right
    bottomPanel.add(checkoutBtn);

    orderPanel.add(bottomPanel);

    // Payment method panel
    JPanel paymentMethodPanel = new JPanel();
    paymentMethodPanel.setLayout(new BoxLayout(paymentMethodPanel, BoxLayout.Y_AXIS));
    paymentMethodPanel.setBackground(Color.WHITE);
    paymentMethodPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
    
    JLabel paymentLabel = new JLabel("Payment Method:");
    paymentLabel.setFont(new Font("Arial", Font.BOLD, 16));
    paymentLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    
    JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    radioPanel.setOpaque(false);
    
    ButtonGroup paymentGroup = new ButtonGroup();
    counterPayment = new JRadioButton("Pay at Counter");
    qrPayment = new JRadioButton("Scan QR Code");
    
    counterPayment.setSelected(true);
    counterPayment.setOpaque(false);
    qrPayment.setOpaque(false);
    
    paymentGroup.add(counterPayment);
    paymentGroup.add(qrPayment);
    
    radioPanel.add(counterPayment);
    radioPanel.add(qrPayment);
    
    paymentMethodPanel.add(paymentLabel);
    paymentMethodPanel.add(radioPanel);
    
    orderPanel.add(paymentMethodPanel);

    return orderPanel;
}

    public static class FoodItem {
        private final String name;
        private final double price;
        private final String imagePath;
        private int quantity;

        public FoodItem(String name, double price, String imagePath) {
            this.name = name;
            this.price = price;
            this.imagePath = imagePath;
            this.quantity = 1;
        }

        public FoodItem(String name, double price, String imagePath, int quantity) {
            this.name = name;
            this.price = price;
            this.imagePath = imagePath;
            this.quantity = quantity;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public String getImagePath() {
            return imagePath;
        }

        public int getQuantity() {
            return quantity;
        }

        public void incrementQuantity() {
            this.quantity++;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainPageUI("SarapLikha"));
    }
}
