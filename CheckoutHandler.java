import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.List;

public class CheckoutHandler {
    public static void handleCheckout(
            List<MainPageUI.FoodItem> orderedItems,
            double subtotal,
            boolean isQrPayment,
            JPanel orderPanel,
            JFrame parentFrame
    ) {
        if (orderedItems.isEmpty()) {
            JOptionPane.showMessageDialog(
                null,
                "Your order list is empty!",
                "Order Summary",
                JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        if (isQrPayment) {
            JDialog qrDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(orderPanel), "Scan QR Code", true);
            qrDialog.setLayout(new BorderLayout());
            qrDialog.setSize(400, 500);
            qrDialog.setLocationRelativeTo(null);

            JPanel qrPanel = new JPanel(new BorderLayout());
            qrPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            qrPanel.setBackground(Color.WHITE);

            JLabel qrImageLabel = new JLabel();
            qrImageLabel.setHorizontalAlignment(SwingConstants.CENTER);

            try {
                URL qrUrl = CheckoutHandler.class.getClassLoader().getResource("icon\\qrcodeniken.png");
                if (qrUrl != null) {
                    ImageIcon qrIcon = new ImageIcon(qrUrl);
                    Image scaledQr = qrIcon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
                    qrImageLabel.setIcon(new ImageIcon(scaledQr));
                } else {
                    BufferedImage qrPlaceholder = new BufferedImage(250, 250, BufferedImage.TYPE_INT_RGB);
                    Graphics2D g2d = qrPlaceholder.createGraphics();
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(0, 0, 250, 250);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(0, 0, 249, 249);
                    g2d.drawString("QR Code Placeholder", 70, 125);
                    g2d.dispose();
                    qrImageLabel.setIcon(new ImageIcon(qrPlaceholder));
                }
            } catch (Exception ex) {
                qrImageLabel.setText("QR Code not available");
            }

            JLabel scanInstructionLabel = new JLabel("Scan this QR code to pay for your order");
            scanInstructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
            scanInstructionLabel.setFont(new Font("Arial", Font.PLAIN, 14));

            JLabel totalAmountLabel = new JLabel("Total Amount: ₱" + String.format("%.2f", subtotal + (subtotal * 0.08)));
            totalAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
            totalAmountLabel.setFont(new Font("Arial", Font.BOLD, 16));

            JPanel instructionPanel = new JPanel();
            instructionPanel.setLayout(new BoxLayout(instructionPanel, BoxLayout.Y_AXIS));
            instructionPanel.setOpaque(false);
            instructionPanel.add(scanInstructionLabel);
            instructionPanel.add(Box.createVerticalStrut(10));
            instructionPanel.add(totalAmountLabel);

            JButton paidButton = new JButton("I've Paid");
            paidButton.setBackground(new Color(76, 175, 80));
            paidButton.setForeground(Color.WHITE);
            paidButton.setFont(new Font("Arial", Font.BOLD, 16));
            paidButton.setFocusPainted(false);

            paidButton.addActionListener(event -> {
                qrDialog.dispose();
                showReceipt(orderedItems, subtotal, true, parentFrame);
            });

            JButton cancelButton = new JButton("Cancel");
            cancelButton.setBackground(new Color(255, 87, 34));
            cancelButton.setForeground(Color.WHITE);
            cancelButton.setFont(new Font("Arial", Font.BOLD, 16));
            cancelButton.setFocusPainted(false);

            cancelButton.addActionListener(event -> qrDialog.dispose());

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
            buttonPanel.setOpaque(false);
            buttonPanel.add(paidButton);
            buttonPanel.add(cancelButton);

            qrPanel.add(qrImageLabel, BorderLayout.CENTER);
            qrPanel.add(instructionPanel, BorderLayout.NORTH);
            qrPanel.add(buttonPanel, BorderLayout.SOUTH);

            qrDialog.add(qrPanel);
            qrDialog.setVisible(true);
        } else {
            showReceipt(orderedItems, subtotal, false, parentFrame);
        }
    }

    private static void showReceipt(
            List<MainPageUI.FoodItem> orderedItems,
            double subtotal,
            boolean isQrPayment,
            JFrame parentFrame
    ) {
        int orderNumber = (int) (Math.random() * 90) + 10;
        java.util.Date date = new java.util.Date();
        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime = dateFormat.format(date);

        StringBuilder receipt = new StringBuilder();
        receipt.append("=======================================\n");
        receipt.append("            SARAP LIKHA KITCHEN        \n");
        receipt.append("=======================================\n\n");
        receipt.append("Order #: ").append(orderNumber).append("\n");
        receipt.append("Date: ").append(dateTime).append("\n");
        receipt.append("Payment Method: ");
        if (isQrPayment) {
            receipt.append("QR Code Payment (PAID)\n");
        } else {
            receipt.append("Pay at Counter\n");
        }
        receipt.append("---------------------------------------\n\n");
        receipt.append("ITEMS                      QTY    PRICE\n");
        receipt.append("---------------------------------------\n");

        for (MainPageUI.FoodItem item : orderedItems) {
            String name = item.getName();
            int qty = item.getQuantity();
            double price = item.getPrice() * qty;
            if (name.length() > 20) {
                name = name.substring(0, 17) + "...";
            }
            String paddedName = String.format("%-24s", name);
            String formattedQty = String.format("%2d", qty);
            String formattedPrice = String.format("%8.2f", price);
            receipt.append(paddedName).append(formattedQty).append("  ₱").append(formattedPrice).append("\n");
        }

        receipt.append("---------------------------------------\n");
        double tax = subtotal * 0.08;
        double total = subtotal + tax;
        receipt.append(String.format("%-26s ₱%8.2f\n", "Subtotal:", subtotal));
        receipt.append(String.format("%-26s ₱%8.2f\n", "Tax (8%):", tax));
        receipt.append("---------------------------------------\n");
        receipt.append(String.format("%-26s ₱%8.2f\n", "TOTAL:", total));
        receipt.append("\n=======================================\n");
        if (isQrPayment) {
            receipt.append("          PAYMENT RECEIVED!            \n");
            receipt.append("  PROCEED TO COUNTER TO COLLECT ORDER  \n");
        } else {
            receipt.append("          THANK YOU FOR ORDERING!      \n");
            receipt.append("          PLEASE PAY AT COUNTER        \n");
        }
        receipt.append("=======================================\n");

        Font receiptFont = new Font(Font.MONOSPACED, Font.PLAIN, 12);
        JTextArea receiptArea = new JTextArea(receipt.toString());
        receiptArea.setFont(receiptFont);
        receiptArea.setEditable(false);
        receiptArea.setBackground(new Color(255, 255, 240));

        JScrollPane receiptScrollPane = new JScrollPane(receiptArea);
        receiptScrollPane.setPreferredSize(new Dimension(400, 500));

        JOptionPane.showMessageDialog(
            null,
            receiptScrollPane,
            isQrPayment ? "Payment Receipt" : "Order Receipt",
            JOptionPane.PLAIN_MESSAGE
        );

        int response = JOptionPane.showConfirmDialog(
            null,
            "Your order has been placed! Press OK to return to the main menu.",
            "Thank You",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE
        );

        if (response == JOptionPane.OK_OPTION) {
            SwingUtilities.invokeLater(() -> {
                parentFrame.dispose();
                Mainapp.main(new String[0]);
            });
        }
    }
}
