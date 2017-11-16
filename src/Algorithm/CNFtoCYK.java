package Algorithm;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;


public class CNFtoCYK extends JFrame implements ActionListener {

    private Border thinBorder = BorderFactory.createLineBorder(Color.BLACK);
    private SpringLayout springLayout;
    private JLabel inputLabel;
    private JLabel outputLabel;
    private JTextArea inputTextArea;
    private JTextArea outputCNFTextArea;
    private JButton submit;
    private Map<String, List<String>> mapFromCNF = new LinkedHashMap<>();

    public CNFtoCYK(){

        super("Convert CNF to CYK");
        setSize(950, 750);
        setVisible(true);
        springLayout = new SpringLayout();
        setLayout(springLayout);

        // program will launch at center
        setLocationRelativeTo(null);


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setInputLabel();
        setInputTextArea();
        setCNFOutputTextArea();
        setSubmitButton();
        setOutputLabel();

//        Console.redirectOutput(inputTextArea);
        printMap();


    }

    private void printMap() {
        System.out.println("Printing from CNF to CYK");

        Iterator it = mapFromCNF.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            System.out.println(pair.getKey() + " -> " + pair.getValue());
        }

        System.out.println(" ");
    }


    public void setMapFromCNF(Map<String, List<String>> map){
        mapFromCNF = new LinkedHashMap<>();
        mapFromCNF.putAll(map);
    }

    public void setSubmitButton() {
        submit = new JButton("Convert");
        setVisible(true);
        submit.addActionListener(this);
        add(submit);

        springLayout.putConstraint(SpringLayout.WEST, submit, 240, SpringLayout.WEST, inputLabel);
        springLayout.putConstraint(SpringLayout.NORTH, submit, 20, SpringLayout.NORTH, this);

    }

    public void setInputTextArea() {
        // 20 rows 30 columns
        inputTextArea = new JTextArea(25, 20);
        inputTextArea.setSize(60, 30);
        inputTextArea.setFont(inputTextArea.getFont().deriveFont(20f));
        inputTextArea.setBorder(thinBorder);
        inputTextArea.setVisible(true);
        add(inputTextArea);

        springLayout.putConstraint(SpringLayout.WEST, inputTextArea, 20, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.NORTH, inputTextArea, 30, SpringLayout.NORTH, inputLabel);
    }

    public void setCNFOutputTextArea() {
        // 20 rows 30 columns
        outputCNFTextArea = new JTextArea(40, 40);
        outputCNFTextArea.setSize(100, 50);
        outputCNFTextArea.setBorder(thinBorder);
        outputCNFTextArea.setEditable(false);
        outputCNFTextArea.setVisible(true);

        add(outputCNFTextArea);

        springLayout.putConstraint(SpringLayout.WEST, outputCNFTextArea, 450, SpringLayout.WEST, inputTextArea);
        springLayout.putConstraint(SpringLayout.NORTH, outputCNFTextArea, 60, SpringLayout.NORTH, this);
    }

    public void setOutputLabel() {

        outputLabel = new JLabel();
        outputLabel.setText("Cocke–Younger–Kasami output: ");
        add(outputLabel);

        springLayout.putConstraint(SpringLayout.WEST, outputLabel, 470, SpringLayout.EAST, this);
        springLayout.putConstraint(SpringLayout.NORTH, outputLabel, 30, SpringLayout.NORTH, this);


    }

    public void setInputLabel() {

        inputLabel = new JLabel();
        inputLabel.setText("Chomskey Normal Form:");
        add(inputLabel);

        springLayout.putConstraint(SpringLayout.WEST, inputLabel, 20, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.NORTH, inputLabel, 30, SpringLayout.NORTH, this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String button = e.getActionCommand();

        if(button == "Convert"){
        }


    }
}
