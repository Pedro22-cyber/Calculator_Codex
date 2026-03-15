import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

public class Calculator extends JFrame {

    private static final Color CANVAS_TOP = new Color(7, 17, 33);
    private static final Color CANVAS_BOTTOM = new Color(20, 54, 92);
    private static final Color CARD_TOP = new Color(10, 24, 46, 235);
    private static final Color CARD_BOTTOM = new Color(7, 16, 30, 235);
    private static final Color CARD_BORDER = new Color(67, 145, 194, 120);
    private static final Color DISPLAY_TOP = new Color(7, 28, 47);
    private static final Color DISPLAY_BOTTOM = new Color(3, 12, 22);
    private static final Color DISPLAY_BORDER = new Color(39, 244, 222, 170);
    private static final Color TEXT_PRIMARY = new Color(234, 245, 255);
    private static final Color TEXT_MUTED = new Color(131, 178, 213);
    private static final Color ACCENT = new Color(39, 244, 222);
    private static final Color ACCENT_STRONG = new Color(0, 194, 255);
    private static final Color DIGIT_BASE = new Color(17, 35, 61);
    private static final Color UTILITY_BASE = new Color(24, 58, 88);
    private static final Color OPERATOR_BASE = new Color(12, 110, 130);
    private static final Color EQUALS_BASE = new Color(11, 170, 195);
    private static final String READY_TEXT = "READY // AWAITING INPUT";

    private final CalculatorEngine engine = new CalculatorEngine();
    private final Map<JButton, String> digitButtons = new LinkedHashMap<>();

    private JButton B0;
    private JButton B1;
    private JButton B2;
    private JButton B3;
    private JButton B4;
    private JButton B5;
    private JButton B6;
    private JButton B7;
    private JButton B8;
    private JButton B9;
    private JButton C;
    private JButton CE;
    private JButton CLEAR;
    private JButton DIV;
    private JButton IGUAL;
    private JButton MAS;
    private JButton MEN;
    private JButton MULT;
    private JButton jButton25;
    private JButton jButton27;
    private JLabel displayLabel;
    private JLabel historyLabel;
    private int shortcutIndex;

    public Calculator() {
        initComponents();
        configureWindow();
        configureButtonMap();
        buildInterface();
        wireEvents();
        setupKeyboardShortcuts();
        refreshDisplay();
    }

    private void initComponents() {
        CE = createButton("CE", ButtonStyle.UTILITY);
        C = createButton("C", ButtonStyle.UTILITY);
        CLEAR = createButton("DEL", ButtonStyle.UTILITY);
        DIV = createButton("/", ButtonStyle.OPERATOR);
        B7 = createButton("7", ButtonStyle.DIGIT);
        MULT = createButton("*", ButtonStyle.OPERATOR);
        B9 = createButton("9", ButtonStyle.DIGIT);
        B8 = createButton("8", ButtonStyle.DIGIT);
        MEN = createButton("-", ButtonStyle.OPERATOR);
        B4 = createButton("4", ButtonStyle.DIGIT);
        B5 = createButton("5", ButtonStyle.DIGIT);
        B6 = createButton("6", ButtonStyle.DIGIT);
        B1 = createButton("1", ButtonStyle.DIGIT);
        B3 = createButton("3", ButtonStyle.DIGIT);
        B2 = createButton("2", ButtonStyle.DIGIT);
        MAS = createButton("+", ButtonStyle.OPERATOR);
        jButton25 = createButton("+/-", ButtonStyle.UTILITY);
        jButton27 = createButton(".", ButtonStyle.DIGIT);
        IGUAL = createButton("=", ButtonStyle.ACCENT);
        B0 = createButton("0", ButtonStyle.DIGIT);

        historyLabel = new JLabel(READY_TEXT);
        historyLabel.setForeground(TEXT_MUTED);
        historyLabel.setFont(new Font("Consolas", Font.PLAIN, 13));

        displayLabel = new JLabel("0");
        displayLabel.setForeground(new Color(248, 252, 255));
        displayLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        displayLabel.setFont(new Font("Consolas", Font.BOLD, 46));

        CE.setToolTipText("Clear current entry");
        C.setToolTipText("Clear all");
        CLEAR.setToolTipText("Delete last digit");
    }

    private void configureWindow() {
        setTitle("Calculator // Neo Interface");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(420, 690));
        setResizable(false);
        getRootPane().setBorder(new EmptyBorder(0, 0, 0, 0));
    }

    private void configureButtonMap() {
        digitButtons.put(B0, "0");
        digitButtons.put(B1, "1");
        digitButtons.put(B2, "2");
        digitButtons.put(B3, "3");
        digitButtons.put(B4, "4");
        digitButtons.put(B5, "5");
        digitButtons.put(B6, "6");
        digitButtons.put(B7, "7");
        digitButtons.put(B8, "8");
        digitButtons.put(B9, "9");
    }

    private void buildInterface() {
        FuturisticPanel rootPanel = new FuturisticPanel();
        rootPanel.setLayout(new BorderLayout());
        rootPanel.setBorder(new EmptyBorder(24, 24, 24, 24));
        setContentPane(rootPanel);

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.add(buildHeader());
        content.add(Box.createVerticalStrut(18));
        content.add(buildDisplayCard());
        content.add(Box.createVerticalStrut(18));
        content.add(buildKeypadCard());

        rootPanel.add(content, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout(12, 0));
        header.setOpaque(false);

        JPanel titleBlock = new JPanel();
        titleBlock.setOpaque(false);
        titleBlock.setLayout(new BoxLayout(titleBlock, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("NEON CALC");
        titleLabel.setForeground(TEXT_PRIMARY);
        titleLabel.setFont(new Font("Consolas", Font.BOLD, 30));

        JLabel subtitleLabel = new JLabel("FUTURE INTERFACE // PRECISION ENGINE");
        subtitleLabel.setForeground(TEXT_MUTED);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        titleBlock.add(titleLabel);
        titleBlock.add(Box.createVerticalStrut(4));
        titleBlock.add(subtitleLabel);

        JPanel badge = createBadge("SYNC");

        header.add(titleBlock, BorderLayout.WEST);
        header.add(badge, BorderLayout.EAST);
        return header;
    }

    private JPanel buildDisplayCard() {
        CardPanel displayCard = new CardPanel(CARD_TOP, CARD_BOTTOM, CARD_BORDER);
        displayCard.setLayout(new BorderLayout(0, 14));
        displayCard.setBorder(new EmptyBorder(20, 22, 22, 22));

        JPanel topRow = new JPanel(new BorderLayout());
        topRow.setOpaque(false);

        JLabel modeLabel = new JLabel("BASIC MODE");
        modeLabel.setForeground(ACCENT);
        modeLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));

        topRow.add(historyLabel, BorderLayout.WEST);
        topRow.add(modeLabel, BorderLayout.EAST);

        JLabel helperLabel = new JLabel("Keyboard enabled: numbers, operators, Enter, Backspace, Esc");
        helperLabel.setForeground(TEXT_MUTED);
        helperLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        helperLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        displayCard.add(topRow, BorderLayout.NORTH);
        displayCard.add(buildDisplayScreen(), BorderLayout.CENTER);
        displayCard.add(helperLabel, BorderLayout.SOUTH);
        return displayCard;
    }

    private JPanel buildDisplayScreen() {
        DisplayPanel displayScreen = new DisplayPanel();
        displayScreen.setLayout(new BorderLayout());
        displayScreen.setBorder(new EmptyBorder(18, 20, 18, 20));
        displayScreen.add(displayLabel, BorderLayout.CENTER);
        return displayScreen;
    }

    private JPanel buildKeypadCard() {
        CardPanel keypadCard = new CardPanel(
            new Color(11, 24, 43, 230),
            new Color(7, 15, 27, 230),
            new Color(88, 171, 214, 105)
        );
        keypadCard.setLayout(new BorderLayout(0, 16));
        keypadCard.setBorder(new EmptyBorder(18, 18, 18, 18));

        JLabel sectionLabel = new JLabel("CONTROL GRID");
        sectionLabel.setForeground(TEXT_MUTED);
        sectionLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));

        JPanel grid = new JPanel(new GridLayout(5, 4, 12, 12));
        grid.setOpaque(false);
        grid.add(CE);
        grid.add(C);
        grid.add(CLEAR);
        grid.add(DIV);
        grid.add(B7);
        grid.add(B8);
        grid.add(B9);
        grid.add(MULT);
        grid.add(B4);
        grid.add(B5);
        grid.add(B6);
        grid.add(MEN);
        grid.add(B1);
        grid.add(B2);
        grid.add(B3);
        grid.add(MAS);
        grid.add(jButton25);
        grid.add(B0);
        grid.add(jButton27);
        grid.add(IGUAL);

        keypadCard.add(sectionLabel, BorderLayout.NORTH);
        keypadCard.add(grid, BorderLayout.CENTER);
        return keypadCard;
    }

    private JPanel createBadge(String text) {
        CardPanel badgePanel = new CardPanel(
            new Color(8, 54, 64, 210),
            new Color(4, 30, 40, 210),
            new Color(54, 229, 208, 150)
        );
        badgePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 18, 8));

        JLabel badgeLabel = new JLabel(text);
        badgeLabel.setForeground(ACCENT);
        badgeLabel.setFont(new Font("Consolas", Font.BOLD, 14));
        badgePanel.add(badgeLabel);
        return badgePanel;
    }

    private JButton createButton(String text, ButtonStyle style) {
        return switch (style) {
            case DIGIT -> new CalculatorButton(
                text,
                DIGIT_BASE,
                shiftColor(DIGIT_BASE, 18),
                new Color(98, 141, 176),
                TEXT_PRIMARY
            );
            case UTILITY -> new CalculatorButton(
                text,
                UTILITY_BASE,
                shiftColor(UTILITY_BASE, 18),
                new Color(99, 166, 208),
                TEXT_PRIMARY
            );
            case OPERATOR -> new CalculatorButton(
                text,
                OPERATOR_BASE,
                shiftColor(OPERATOR_BASE, 18),
                ACCENT_STRONG,
                TEXT_PRIMARY
            );
            case ACCENT -> new CalculatorButton(
                text,
                EQUALS_BASE,
                shiftColor(EQUALS_BASE, 20),
                ACCENT,
                new Color(7, 20, 32)
            );
        };
    }

    private void wireEvents() {
        digitButtons.forEach((button, digit) -> button.addActionListener(evt -> {
            engine.inputDigit(digit);
            refreshDisplay();
        }));

        jButton27.addActionListener(evt -> {
            engine.inputDecimalPoint();
            refreshDisplay();
        });

        jButton25.addActionListener(evt -> {
            engine.toggleSign();
            refreshDisplay();
        });

        MAS.addActionListener(evt -> applyOperator(CalculatorEngine.Operator.ADD));
        MEN.addActionListener(evt -> applyOperator(CalculatorEngine.Operator.SUBTRACT));
        MULT.addActionListener(evt -> applyOperator(CalculatorEngine.Operator.MULTIPLY));
        DIV.addActionListener(evt -> applyOperator(CalculatorEngine.Operator.DIVIDE));

        CE.addActionListener(evt -> {
            engine.clearEntry();
            refreshDisplay();
        });

        C.addActionListener(evt -> {
            engine.clearAll();
            refreshDisplay();
        });

        CLEAR.addActionListener(evt -> {
            engine.backspace();
            refreshDisplay();
        });

        IGUAL.addActionListener(evt -> {
            engine.evaluate();
            refreshDisplay();
        });
    }

    private void setupKeyboardShortcuts() {
        for (String digit : digitButtons.values()) {
            bindShortcut(KeyStroke.getKeyStroke(digit.charAt(0)), () -> {
                engine.inputDigit(digit);
                refreshDisplay();
            });
        }

        bindShortcut(KeyStroke.getKeyStroke('.'), () -> {
            engine.inputDecimalPoint();
            refreshDisplay();
        });
        bindShortcut(KeyStroke.getKeyStroke(','), () -> {
            engine.inputDecimalPoint();
            refreshDisplay();
        });
        bindShortcut(KeyStroke.getKeyStroke('+'), () -> applyOperator(CalculatorEngine.Operator.ADD));
        bindShortcut(KeyStroke.getKeyStroke('-'), () -> applyOperator(CalculatorEngine.Operator.SUBTRACT));
        bindShortcut(KeyStroke.getKeyStroke('*'), () -> applyOperator(CalculatorEngine.Operator.MULTIPLY));
        bindShortcut(KeyStroke.getKeyStroke('/'), () -> applyOperator(CalculatorEngine.Operator.DIVIDE));
        bindShortcut(KeyStroke.getKeyStroke("ENTER"), () -> {
            engine.evaluate();
            refreshDisplay();
        });
        bindShortcut(KeyStroke.getKeyStroke("BACK_SPACE"), () -> {
            engine.backspace();
            refreshDisplay();
        });
        bindShortcut(KeyStroke.getKeyStroke("DELETE"), () -> {
            engine.clearEntry();
            refreshDisplay();
        });
        bindShortcut(KeyStroke.getKeyStroke("ESCAPE"), () -> {
            engine.clearAll();
            refreshDisplay();
        });
    }

    private void bindShortcut(KeyStroke keyStroke, Runnable action) {
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getRootPane().getActionMap();
        String actionKey = "shortcut." + shortcutIndex++;
        inputMap.put(keyStroke, actionKey);
        actionMap.put(actionKey, new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                action.run();
            }
        });
    }

    private void applyOperator(CalculatorEngine.Operator operator) {
        engine.applyOperator(operator);
        refreshDisplay();
    }

    private void refreshDisplay() {
        displayLabel.setText(engine.getDisplayText());
        String historyText = engine.getHistoryText();
        historyLabel.setText(historyText.isBlank() ? READY_TEXT : historyText);
    }

    private static Color shiftColor(Color color, int amount) {
        int red = Math.max(0, Math.min(255, color.getRed() + amount));
        int green = Math.max(0, Math.min(255, color.getGreen() + amount));
        int blue = Math.max(0, Math.min(255, color.getBlue() + amount));
        return new Color(red, green, blue, color.getAlpha());
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(Calculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new Calculator().setVisible(true));
    }

    private enum ButtonStyle {
        DIGIT,
        UTILITY,
        OPERATOR,
        ACCENT
    }

    private static final class FuturisticPanel extends JPanel {

        @Override
        protected void paintComponent(Graphics graphics) {
            Graphics2D g2 = (Graphics2D) graphics.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            GradientPaint background = new GradientPaint(0, 0, CANVAS_TOP, 0, getHeight(), CANVAS_BOTTOM);
            g2.setPaint(background);
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setColor(new Color(39, 244, 222, 38));
            g2.fillOval(-70, -50, 250, 250);

            g2.setColor(new Color(0, 194, 255, 30));
            g2.fillOval(getWidth() - 220, getHeight() - 270, 310, 310);

            g2.setColor(new Color(255, 255, 255, 12));
            for (int x = 32; x < getWidth(); x += 52) {
                g2.drawLine(x, 0, x - 120, getHeight());
            }

            g2.setColor(new Color(255, 255, 255, 24));
            g2.drawRoundRect(12, 12, getWidth() - 25, getHeight() - 25, 28, 28);
            g2.dispose();
        }
    }

    private static final class CardPanel extends JPanel {

        private final Color startColor;
        private final Color endColor;
        private final Color borderColor;

        private CardPanel(Color startColor, Color endColor, Color borderColor) {
            this.startColor = startColor;
            this.endColor = endColor;
            this.borderColor = borderColor;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            Graphics2D g2 = (Graphics2D) graphics.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth() - 1;
            int height = getHeight() - 1;
            int arc = 28;

            g2.setPaint(new GradientPaint(0, 0, startColor, 0, getHeight(), endColor));
            g2.fillRoundRect(0, 0, width, height, arc, arc);

            g2.setColor(new Color(255, 255, 255, 18));
            g2.drawRoundRect(1, 1, width - 2, height - 2, arc, arc);

            g2.setColor(borderColor);
            g2.drawRoundRect(0, 0, width, height, arc, arc);

            g2.setColor(new Color(255, 255, 255, 24));
            g2.fillRoundRect(20, 18, Math.max(60, Math.min(104, width - 40)), 4, 4, 4);
            g2.dispose();
        }
    }

    private static final class DisplayPanel extends JPanel {

        private DisplayPanel() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            Graphics2D g2 = (Graphics2D) graphics.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth() - 1;
            int height = getHeight() - 1;

            g2.setPaint(new GradientPaint(0, 0, DISPLAY_TOP, 0, getHeight(), DISPLAY_BOTTOM));
            g2.fillRoundRect(0, 0, width, height, 24, 24);

            g2.setColor(new Color(39, 244, 222, 28));
            g2.fillRoundRect(12, 10, Math.max(60, width - 24), 18, 18, 18);

            g2.setColor(new Color(255, 255, 255, 22));
            g2.drawRoundRect(1, 1, width - 2, height - 2, 24, 24);

            g2.setColor(DISPLAY_BORDER);
            g2.drawRoundRect(0, 0, width, height, 24, 24);

            g2.dispose();
        }
    }

    private static final class CalculatorButton extends JButton {

        private final Color baseColor;
        private final Color hoverColor;
        private final Color borderColor;
        private boolean hover;

        private CalculatorButton(String text, Color baseColor, Color hoverColor, Color borderColor, Color textColor) {
            super(text);
            this.baseColor = baseColor;
            this.hoverColor = hoverColor;
            this.borderColor = borderColor;

            setForeground(textColor);
            setFont(new Font("Segoe UI", Font.BOLD, 18));
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);
            setHorizontalAlignment(SwingConstants.CENTER);
            setPreferredSize(new Dimension(78, 62));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent evt) {
                    hover = true;
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent evt) {
                    hover = false;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            Graphics2D g2 = (Graphics2D) graphics.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Color fillColor = getModel().isArmed() ? shiftColor(baseColor, -16) : hover ? hoverColor : baseColor;
            g2.setPaint(new GradientPaint(0, 0, shiftColor(fillColor, 12), 0, getHeight(), shiftColor(fillColor, -10)));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 22, 22);

            g2.setColor(new Color(255, 255, 255, 24));
            g2.drawLine(14, 12, getWidth() - 15, 12);

            g2.dispose();
            super.paintComponent(graphics);
        }

        @Override
        protected void paintBorder(Graphics graphics) {
            Graphics2D g2 = (Graphics2D) graphics.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(borderColor);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 22, 22);
            g2.dispose();
        }
    }
}
