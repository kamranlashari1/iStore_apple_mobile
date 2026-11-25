
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main {

    static final String[] CATEGORIES = { "iPhone", "Apple Watch", "AirPods" };
    static ArrayList<String[]> products = new ArrayList<>();
    static ArrayList<String> sales = new ArrayList<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            if (products.isEmpty()) {
                products.add(new String[] { "iPhone 11", "120000", "8", "iPhone" });
                products.add(new String[] { "iPhone 11 Pro Max", "150000", "5", "iPhone" });
                products.add(new String[] { "iPhone 12", "130000", "10", "iPhone" });
                products.add(new String[] { "iPhone 12 Pro", "200000", "12", "iPhone" });
                products.add(new String[] { "iPhone 13", "220000", "7", "iPhone" });
                products.add(new String[] { "iPhone 13 Pro", "300000", "5", "iPhone" });
                products.add(new String[] { "iPhone 14", "350000", "8", "iPhone" });
                products.add(new String[] { "iPhone 14 Pro", "400000", "6", "iPhone" });
                products.add(new String[] { "iPhone 15 Pro Max", "540000", "10", "iPhone" });
                products.add(new String[] { "iPhone 15", "380000", "15", "iPhone" });
                products.add(new String[] { "Apple Watch Series 10", "165000", "6", "Apple Watch" });
                products.add(new String[] { "Apple Watch SE", "95000", "8", "Apple Watch" });
                products.add(new String[] { "Apple Watch Ultra 2", "280000", "3", "Apple Watch" });
                products.add(new String[] { "AirPods Pro 2", "75000", "10", "AirPods" });
                products.add(new String[] { "AirPods 4", "45000", "12", "AirPods" });
            }
            new LoginFrame();
        });
    }

    static void addNavigation(JFrame frame) {
        JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 3));
        addNavigationButton(navigationPanel, "Home", frame, () -> new DashboardFrame());
        addNavigationButton(navigationPanel, "Add Product", frame, () -> new AddProductFrame());
        addNavigationButton(navigationPanel, "Stock", frame, () -> new ViewStockFrame());
        addNavigationButton(navigationPanel, "Billing", frame, () -> new BillingFrame());

        JButton salesButton = new JButton("Sales");
        salesButton.addActionListener(e -> showSalesReport(frame));
        navigationPanel.add(salesButton);

        JButton logoutButton = new JButton("Log Out");
        logoutButton.addActionListener(e -> {
            frame.dispose();
            new LoginFrame();
        });
        navigationPanel.add(logoutButton);

        if (frame.getLayout() instanceof BorderLayout) {
            frame.add(navigationPanel, BorderLayout.NORTH);
        } else {
            navigationPanel.setBounds(5, frame.getHeight() - 70, frame.getWidth() - 20, 40);
            frame.add(navigationPanel);
        }
    }

    static void addNavigationButton(JPanel panel, String title, JFrame frame, Runnable destination) {
        JButton button = new JButton(title);
        button.addActionListener(e -> {
            frame.dispose();
            destination.run();
        });
        panel.add(button);
    }

    static void showSalesReport(JFrame frame) {
        StringBuilder report = new StringBuilder("Total Bills: ").append(sales.size()).append("\n\n");
        for (String sale : sales)
            report.append(sale).append('\n');
        JOptionPane.showMessageDialog(frame, sales.isEmpty() ? "No Sales Yet" : report.toString(),
                "Sales Report", JOptionPane.INFORMATION_MESSAGE);
    }

    static Integer parsePositiveInteger(String value) {
        try {
            int number = Integer.parseInt(value.trim());
            return number > 0 ? number : null;
        } catch (NumberFormatException | NullPointerException exception) {
            return null;
        }
    }

    // ===== 1. LOGIN SCREEN =====
    static class LoginFrame extends JFrame {
        LoginFrame() {
            setTitle("iStore - Login");
            setSize(400, 250);
            setLayout(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            JLabel title = new JLabel("iStore - Apple Mobile Store");
            title.setBounds(80, 20, 260, 30);
            title.setFont(new Font("Arial", Font.BOLD, 16));
            add(title);

            JLabel u = new JLabel("Username:");
            u.setBounds(50, 70, 100, 25);
            add(u);
            JTextField userField = new JTextField();
            userField.setBounds(150, 70, 180, 25);
            add(userField);

            JLabel p = new JLabel("Password:");
            p.setBounds(50, 110, 100, 25);
            add(p);
            JPasswordField passField = new JPasswordField();
            passField.setBounds(150, 110, 180, 25);
            add(passField);

            JButton btn = new JButton("Login");
            btn.setBounds(150, 160, 100, 30);
            add(btn);
            btn.addActionListener(e -> {
                if (userField.getText().equals("admin") && new String(passField.getPassword()).equals("admin123")) {
                    JOptionPane.showMessageDialog(this, "Welcome Admin!");
                    new DashboardFrame();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Wrong Username / Password");
                }
            });
            setVisible(true);
        }
    }

    // ===== 2. DASHBOARD =====
    static class DashboardFrame extends JFrame {
        DashboardFrame() {
            setTitle("iStore - Dashboard");
            setSize(600, 400);
            setLayout(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            JLabel title = new JLabel("iStore Dashboard");
            title.setBounds(200, 20, 300, 40);
            title.setFont(new Font("Arial", Font.BOLD, 22));
            add(title);

            JButton addBtn = new JButton("Add Product");
            addBtn.setBounds(100, 100, 180, 50);
            add(addBtn);
            JButton stockBtn = new JButton("View Stock");
            stockBtn.setBounds(320, 100, 180, 50);
            add(stockBtn);
            JButton billBtn = new JButton("New Billing");
            billBtn.setBounds(100, 180, 180, 50);
            add(billBtn);
            JButton salesBtn = new JButton("Sales Report");
            salesBtn.setBounds(320, 180, 180, 50);
            add(salesBtn);

            addNavigation(this);
            addBtn.addActionListener(e -> {
                dispose();
                new AddProductFrame();
            });
            stockBtn.addActionListener(e -> {
                dispose();
                new ViewStockFrame();
            });
            billBtn.addActionListener(e -> {
                dispose();
                new BillingFrame();
            });
            salesBtn.addActionListener(e -> showSalesReport(this));
            setVisible(true);
        }
    }

    // ===== 3. ADD PRODUCT =====
    static class AddProductFrame extends JFrame {
        AddProductFrame() {
            setTitle("Add Product");
            setSize(400, 300);
            setLayout(null);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);
            addNavigation(this);
            JLabel l1 = new JLabel("Model:");
            l1.setBounds(30, 30, 100, 25);
            add(l1);
            JTextField m = new JTextField();
            m.setBounds(140, 30, 200, 25);
            add(m);
            JLabel categoryLabel = new JLabel("Category:");
            categoryLabel.setBounds(30, 70, 100, 25);
            add(categoryLabel);
            JComboBox<String> categoryBox = new JComboBox<>(CATEGORIES);
            categoryBox.setBounds(140, 70, 200, 25);
            add(categoryBox);
            JLabel l2 = new JLabel("Price:");
            l2.setBounds(30, 110, 100, 25);
            add(l2);
            JTextField pr = new JTextField();
            pr.setBounds(140, 110, 200, 25);
            add(pr);
            JLabel l3 = new JLabel("Stock:");
            l3.setBounds(30, 150, 100, 25);
            add(l3);
            JTextField st = new JTextField();
            st.setBounds(140, 150, 200, 25);
            add(st);
            JButton btn = new JButton("Save");
            btn.setBounds(140, 200, 100, 30);
            add(btn);
            btn.addActionListener(e -> {
                String model = m.getText().trim();
                Integer price = parsePositiveInteger(pr.getText());
                Integer stock = parsePositiveInteger(st.getText());

                if (model.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Enter a model name.");
                    return;
                }
                if (price == null) {
                    JOptionPane.showMessageDialog(this, "Enter a valid positive price.");
                    return;
                }
                if (stock == null) {
                    JOptionPane.showMessageDialog(this, "Enter a valid positive stock quantity.");
                    return;
                }
                for (String[] product : products) {
                    if (product[0].equalsIgnoreCase(model)
                            && product[3].equals(categoryBox.getSelectedItem())) {
                        JOptionPane.showMessageDialog(this, "This product model already exists.");
                        return;
                    }
                }

                products.add(new String[] { model, String.valueOf(price), String.valueOf(stock),
                        (String) categoryBox.getSelectedItem() });
                JOptionPane.showMessageDialog(this, "Product Added!");
                dispose();
                new DashboardFrame();
            });
            setVisible(true);
        }
    }

    // ===== 4. VIEW STOCK =====
    static class ViewStockFrame extends JFrame {
        ViewStockFrame() {
            setTitle("View Stock");
            setSize(500, 300);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);
            addNavigation(this);
            JTabbedPane tabs = new JTabbedPane();
            for (String category : CATEGORIES) {
                String[] cols = { "Model", "Price", "Stock" };
                ArrayList<String[]> categoryProducts = new ArrayList<>();
                for (String[] product : products) {
                    if (category.equals(product[3]))
                        categoryProducts.add(new String[] { product[0], product[1], product[2] });
                }
                String[][] data = categoryProducts.toArray(new String[0][]);
                JPanel categoryPanel = new JPanel(new BorderLayout());
                categoryPanel.add(new DeviceIllustration(category), BorderLayout.NORTH);
                categoryPanel.add(new JScrollPane(new JTable(data, cols)), BorderLayout.CENTER);
                tabs.addTab(category, categoryPanel);
            }
            add(tabs);
            setVisible(true);
        }
    }

    static class DeviceIllustration extends JPanel {
        private final String category;

        DeviceIllustration(String category) {
            this.category = category;
            setPreferredSize(new Dimension(200, 125));
            setBackground(new Color(244, 246, 248));
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            Graphics2D g = (Graphics2D) graphics.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int centerX = getWidth() / 2;

            if (category.equals("iPhone")) {
                int x = centerX - 37;
                g.setColor(new Color(38, 42, 48));
                g.fillRoundRect(x, 8, 74, 108, 16, 16);
                g.setPaint(new GradientPaint(x, 16, new Color(90, 177, 201), x + 70, 108,
                        new Color(34, 69, 108)));
                g.fillRoundRect(x + 5, 14, 64, 96, 12, 12);
                g.setColor(new Color(28, 32, 38));
                g.fillRoundRect(centerX - 12, 17, 24, 5, 5, 5);
                g.setColor(new Color(224, 243, 244, 210));
                g.fillOval(centerX + 5, 18, 4, 4);
            } else if (category.equals("Apple Watch")) {
                g.setColor(new Color(94, 112, 124));
                g.fillRoundRect(centerX - 19, 5, 38, 115, 18, 18);
                g.setColor(new Color(41, 46, 51));
                g.fillRoundRect(centerX - 38, 27, 76, 72, 22, 22);
                g.setColor(new Color(31, 39, 43));
                g.fillRoundRect(centerX - 31, 34, 62, 58, 18, 18);
                g.setColor(new Color(116, 203, 181));
                g.drawArc(centerX - 21, 43, 42, 42, 35, 285);
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, 13));
                g.drawString("10:09", centerX - 19, 67);
            } else {
                g.setColor(new Color(208, 214, 218));
                g.fillRoundRect(centerX - 69, 61, 138, 50, 20, 20);
                g.setColor(new Color(250, 251, 252));
                g.fillRoundRect(centerX - 66, 58, 132, 48, 19, 19);
                g.setColor(new Color(183, 191, 197));
                g.drawRoundRect(centerX - 66, 58, 132, 48, 19, 19);
                g.setColor(new Color(222, 227, 231));
                g.fillRoundRect(centerX - 32, 79, 64, 2, 2, 2);
                g.setColor(new Color(250, 251, 252));
                g.fillOval(centerX - 36, 16, 22, 26);
                g.fillRoundRect(centerX - 31, 31, 11, 34, 6, 6);
                g.fillOval(centerX + 14, 16, 22, 26);
                g.fillRoundRect(centerX + 20, 31, 11, 34, 6, 6);
                g.setColor(new Color(183, 191, 197));
                g.drawOval(centerX - 36, 16, 22, 26);
                g.drawOval(centerX + 14, 16, 22, 26);
            }
            g.dispose();
        }
    }

    // ===== 5. BILLING =====
    static class BillingFrame extends JFrame {
        BillingFrame() {
            setTitle("Billing");
            setSize(450, 400);
            setLayout(null);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);
            addNavigation(this);
            JLabel l1 = new JLabel("Customer:");
            l1.setBounds(30, 30, 100, 25);
            add(l1);
            JTextField name = new JTextField();
            name.setBounds(150, 30, 220, 25);
            add(name);

            JLabel categoryLabel = new JLabel("Category:");
            categoryLabel.setBounds(30, 70, 100, 25);
            add(categoryLabel);
            JComboBox<String> categoryBox = new JComboBox<>(CATEGORIES);
            categoryBox.setBounds(150, 70, 220, 25);
            add(categoryBox);

            JLabel l2 = new JLabel("Model:");
            l2.setBounds(30, 110, 100, 25);
            add(l2);
            JComboBox<String> box = new JComboBox<>();
            box.setBounds(150, 110, 220, 25);
            add(box);

            JLabel priceLabel = new JLabel("Price: ");
            priceLabel.setBounds(30, 150, 300, 25);
            add(priceLabel);
            JLabel totalLabel = new JLabel("Total: ");
            totalLabel.setBounds(30, 190, 300, 25);
            add(totalLabel);
            JLabel stockLabel = new JLabel("Available: ");
            stockLabel.setBounds(30, 230, 300, 25);
            add(stockLabel);

            JLabel quantityLabel = new JLabel("Quantity:");
            quantityLabel.setBounds(30, 270, 100, 25);
            add(quantityLabel);
            JSpinner quantity = new JSpinner(new SpinnerNumberModel(1, 1, 1, 1));
            quantity.setBounds(150, 270, 220, 25);
            add(quantity);

            Runnable updateModels = () -> {
                String selectedCategory = (String) categoryBox.getSelectedItem();
                box.removeAllItems();
                for (String[] product : products) {
                    if (selectedCategory != null && selectedCategory.equals(product[3])
                            && parsePositiveInteger(product[2]) != null
                            && Integer.parseInt(product[2]) > 0)
                        box.addItem(product[0]);
                }
            };
            categoryBox.addActionListener(e -> updateModels.run());
            box.addActionListener(e -> {
                int idx = findProductIndex((String) categoryBox.getSelectedItem(),
                        (String) box.getSelectedItem());
                if (idx >= 0) {
                    int stock = Integer.parseInt(products.get(idx)[2]);
                    int unitPrice = Integer.parseInt(products.get(idx)[1]);
                    priceLabel.setText("Price: Rs. " + unitPrice);
                    stockLabel.setText("Available: " + stock);
                    quantity.setModel(new SpinnerNumberModel(1, 1, stock, 1));
                    totalLabel.setText("Total: Rs. " + unitPrice);
                } else {
                    priceLabel.setText("Price: N/A");
                    stockLabel.setText("Available: 0");
                    quantity.setModel(new SpinnerNumberModel(1, 1, 1, 1));
                    totalLabel.setText("Total: ");
                }
            });
            quantity.addChangeListener(e -> {
                int idx = findProductIndex((String) categoryBox.getSelectedItem(),
                        (String) box.getSelectedItem());
                if (idx >= 0) {
                    int unitPrice = Integer.parseInt(products.get(idx)[1]);
                    int selectedQuantity = (Integer) quantity.getValue();
                    totalLabel.setText("Total: Rs. " + (unitPrice * selectedQuantity));
                }
            });
            updateModels.run();

            JButton btn = new JButton("Generate Bill");
            btn.setBounds(130, 310, 150, 35);
            add(btn);
            btn.addActionListener(e -> {
                String customer = name.getText().trim();
                if (customer.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Enter a customer name.");
                    return;
                }
                int idx = findProductIndex((String) categoryBox.getSelectedItem(),
                        (String) box.getSelectedItem());
                if (idx < 0) {
                    JOptionPane.showMessageDialog(this, "Select a product first");
                    return;
                }
                int selectedQuantity = (Integer) quantity.getValue();
                int available = Integer.parseInt(products.get(idx)[2]);
                if (selectedQuantity > available) {
                    JOptionPane.showMessageDialog(this, "Requested quantity exceeds available stock.");
                    return;
                }
                int unitPrice = Integer.parseInt(products.get(idx)[1]);
                int total = unitPrice * selectedQuantity;
                products.get(idx)[2] = String.valueOf(available - selectedQuantity);
                totalLabel.setText("Total: Rs. " + total);
                sales.add(customer + " - " + products.get(idx)[0] + " x " + selectedQuantity
                        + " - Rs." + total);
                JOptionPane.showMessageDialog(this,
                        "Bill Generated!\nCustomer: " + customer
                                + "\nQuantity: " + selectedQuantity + "\nTotal: Rs. " + total);
                dispose();
                new DashboardFrame();
            });
            setVisible(true);
        }
    }

    static int findProductIndex(String category, String model) {
        if (category == null || model == null)
            return -1;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i)[3].equals(category) && products.get(i)[0].equals(model))
                return i;
        }
        return -1;
    }
}