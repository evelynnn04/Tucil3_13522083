import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.NoSuchElementException;

public class Gui extends JFrame {
    private JTextField startTextField;
    private JTextField goalTextField;
    private JComboBox<String> algorithmChoosen;
    private JTextArea outputTextArea;
    private JButton startButton;

    public Gui() {

        // Setup Frame
        setTitle("Word Ladder");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));
        getContentPane().setBackground(Color.BLACK);
        setLocationRelativeTo(null);

        // Title, subtitle
        JLabel titleLabel = new JLabel("Welcome to Word Ladder");
        JLabel subtitleLabel = new JLabel("by Evelyn Yosiana 13522083");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        subtitleLabel.setFont(new Font("Arial", Font.BOLD, 12));
        subtitleLabel.setForeground(Color.WHITE);
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(titleLabel);
        titlePanel.add(subtitleLabel);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        titlePanel.setBackground(Color.BLACK);
        add(titlePanel);

        // Label start
        JLabel startLabel = new JLabel("Start:");
        startLabel.setFont(new Font("Arial", Font.BOLD, 14));
        startLabel.setForeground(Color.WHITE);
        startTextField = new JTextField();
        startTextField.setPreferredSize(new Dimension(300, 30));
        startTextField.setBackground(Color.BLACK);
        startTextField.setForeground(Color.WHITE);
        JPanel startPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        startPanel.add(startLabel);
        startPanel.add(startTextField);
        startPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        startPanel.setBackground(Color.BLACK);
        add(startPanel);

        // Label goal
        JLabel goalLabel = new JLabel("Goal:");
        goalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        goalLabel.setForeground(Color.WHITE);
        goalTextField = new JTextField();
        goalTextField.setPreferredSize(new Dimension(300, 30));
        goalTextField.setBackground(Color.BLACK);
        goalTextField.setForeground(Color.WHITE);
        JPanel goalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        goalPanel.add(goalLabel);
        goalPanel.add(goalTextField);
        goalPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        goalPanel.setBackground(Color.BLACK);
        add(goalPanel);

        // Dropdown algorithm choice
        JLabel algorithmLabel = new JLabel("Select Algorithm:");
        algorithmLabel.setFont(new Font("Arial", Font.BOLD, 14));
        algorithmLabel.setForeground(Color.WHITE);
        String[] algorithms = { "UCS", "Greedy", "AStar" };
        algorithmChoosen = new JComboBox<>(algorithms);
        algorithmChoosen.setFont(new Font("Arial", Font.PLAIN, 12));
        algorithmChoosen.setBackground(Color.BLACK);
        algorithmChoosen.setForeground(Color.WHITE);
        JPanel algorithmPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        algorithmPanel.add(algorithmLabel);
        algorithmPanel.add(algorithmChoosen);
        algorithmPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        algorithmPanel.setBackground(Color.BLACK);
        add(algorithmPanel);

        // Result frame
        outputTextArea = new JTextArea(10, 30); 
        outputTextArea.setEditable(false);
        outputTextArea.setBackground(Color.BLACK); 
        outputTextArea.setForeground(Color.WHITE); 
        JScrollPane scrollPane = new JScrollPane(outputTextArea);

        Border lineBorder = BorderFactory.createLineBorder(Color.WHITE);
        Border matteBorder = BorderFactory.createMatteBorder(0, 20, 5, 20, Color.BLACK);
        Border compoundBorder = BorderFactory.createCompoundBorder(matteBorder, lineBorder);
        scrollPane.setBorder(compoundBorder);
        scrollPane.setBackground(Color.BLACK);
        add(scrollPane);

        // Button
        startButton = new JButton("Find");
        startButton.setFont(new Font("Arial", Font.BOLD, 14));
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startButton.setEnabled(false);

                Method.wordExist.clear();
                outputTextArea.setText("");

                String start = startTextField.getText();
                String goal = goalTextField.getText();
                String method = (String) algorithmChoosen.getSelectedItem();
            
                if (start.equals(goal)) {
                    outputTextArea.append("You have found the answer!" + "\n");
                    return;
                }
            
                Solver solver = new Solver(start.length(), start, goal);
                Node nodeRes = new Node(start, goal);
            
                try {
                    if (method.equals("UCS")) { 
                        nodeRes = solver.solveUCS();
                    } else if (method.equals("Greedy")) { 
                        nodeRes = solver.solveGreedy();
                    } else {
                        nodeRes = solver.solveAStar();
                    }
            
                    outputTextArea.append("Result: " + "\n");
                    for (String s : nodeRes.getList()) {
                        outputTextArea.append(s);
                        outputTextArea.append("\n");
                    }
            
                    outputTextArea.append("Execution time: " + String.format("%.2f", solver.getTimeExecution()) + " ms" + "\n");
                    outputTextArea.append("Memory used: " + String.format("%.2f", solver.getMemoryUsed()) + " mb" + "\n");
                    outputTextArea.append("Node visited: " + solver.getVisitedWords() + " nodes" + "\n");

                } catch (NoSuchElementException ex) {
                    outputTextArea.append(ex.getMessage() + "\n");
                }

                startButton.setEnabled(true);
                startTextField.setText("");
                goalTextField.setText("");
                startTextField.setText(start);
                goalTextField.setText(goal);
                algorithmChoosen.setSelectedItem(method);
            }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(startButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.setBackground(Color.BLACK);
        add(buttonPanel);

        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Gui();
            }
        });
    }
}
