package Algorithm;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;

public class CFGtoCNF extends JFrame implements ActionListener {

    private Border thinBorder = BorderFactory.createLineBorder(Color.BLACK);
    private SpringLayout springLayout = new SpringLayout();
    private CNF cnf;
    private JLabel inputLabel;
    private JLabel outputLabel;
    private JLabel stringLabel;
    private JLabel outputCYKLabel;
    private JTextArea inputTextArea;
    private JTextArea inputStringArea;
    private JTextArea outputCNFTextArea;
    private JTextArea outputCYKTextArea;
    private JButton submit;
    private JButton convertButton;


    public CFGtoCNF(){
        super("Convert CFG to CNF");
        //setSize(1360, 768);
        setSize(875,725);
        setVisible(true);
        setLayout(springLayout);

        // program will launch at center
        setLocationRelativeTo(null);

        cnf = new CNF();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setInputLabel();
        setInputTextArea();
        setCNFOutputTextArea();
        setSubmitButton();
        setOutputLabel();


        /**
         * Comment to remove CYK conversion in this program
         */
        //setNextButton();
        //setStringLabel();
        //setString();
        //setCYKOutputLabel();
        //setCYKOutputTextArea();

    }

    public void setStringLabel(){
        stringLabel = new JLabel();
        stringLabel.setText("Insert String here: ");
        add(stringLabel);

        springLayout.putConstraint(SpringLayout.WEST, stringLabel, 2, SpringLayout.WEST, inputLabel);
        springLayout.putConstraint(SpringLayout.NORTH, stringLabel, 380, SpringLayout.NORTH, this);

    }

    public void setString() {
        inputStringArea = new JTextArea(1, 15);
        inputStringArea.setBorder(thinBorder);
        inputStringArea.setFont(inputStringArea.getFont().deriveFont(20f));
        inputStringArea.setVisible(true);
        add(inputStringArea);

        springLayout.putConstraint(SpringLayout.WEST, inputStringArea, 2, SpringLayout.SOUTH, inputLabel);
        springLayout.putConstraint(SpringLayout.NORTH, inputStringArea, 420, SpringLayout.NORTH, this);
    }

    public void setSubmitButton() {
        submit = new JButton("Convert");
        setVisible(true);
        submit.addActionListener(this);
        add(submit);

        springLayout.putConstraint(SpringLayout.WEST, submit, 240, SpringLayout.WEST, inputLabel);
        springLayout.putConstraint(SpringLayout.NORTH, submit, 20, SpringLayout.NORTH, this);

    }

    public void setNextButton(){
        convertButton = new JButton("Convert CNF to CYK");
        convertButton.setVisible(true);
        convertButton.addActionListener(this);
        add(convertButton);

        springLayout.putConstraint(SpringLayout.WEST, convertButton, 240, SpringLayout.WEST, outputLabel);
        springLayout.putConstraint(SpringLayout.NORTH, convertButton, 20, SpringLayout.NORTH, this);

    }

    public void setInputTextArea() {
        // 20 rows 30 columns
        inputTextArea = new JTextArea(15, 20);
        inputTextArea.setFont(inputTextArea.getFont().deriveFont(17f));
        inputTextArea.setBorder(thinBorder);
        inputTextArea.setVisible(true);
        add(inputTextArea);

        springLayout.putConstraint(SpringLayout.WEST, inputTextArea, 20, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.NORTH, inputTextArea, 30, SpringLayout.NORTH, inputLabel);
    }

    public void setCNFOutputTextArea() {
        // 20 rows 30 columns
        outputCNFTextArea = new JTextArea(40, 40);
        outputCNFTextArea.setBorder(thinBorder);
        outputCNFTextArea.setEditable(false);
        outputCNFTextArea.setVisible(true);
        add(outputCNFTextArea);
        cnf.setOutputTextArea(outputCNFTextArea);

        springLayout.putConstraint(SpringLayout.WEST, outputCNFTextArea, 375, SpringLayout.WEST, inputTextArea);
        springLayout.putConstraint(SpringLayout.NORTH, outputCNFTextArea, 60, SpringLayout.NORTH, this);
    }

    public void setCYKOutputTextArea() {
        // 20 rows 30 columns
        outputCYKTextArea = new JTextArea(40, 40);
        outputCYKTextArea.setBorder(thinBorder);
        outputCYKTextArea.setEditable(false);
        outputCYKTextArea.setVisible(true);
        add(outputCYKTextArea);
        cnf.setOutputCYKTextArea(outputCYKTextArea);

        springLayout.putConstraint(SpringLayout.WEST, outputCYKTextArea, 475, SpringLayout.WEST, outputCNFTextArea);
        springLayout.putConstraint(SpringLayout.NORTH, outputCYKTextArea, 60, SpringLayout.NORTH, this);
    }

    public void setCYKOutputLabel() {

        outputCYKLabel = new JLabel();
        outputCYKLabel.setText("Cocke–Younger–Kasami output: ");
        add(outputCYKLabel);

        springLayout.putConstraint(SpringLayout.WEST, outputCYKLabel, 475, SpringLayout.WEST, outputLabel);
        springLayout.putConstraint(SpringLayout.NORTH, outputCYKLabel, 30, SpringLayout.NORTH, this);


    }

    public void setOutputLabel() {

        outputLabel = new JLabel();
        outputLabel.setText("Chomsky Normal Form output: ");
        add(outputLabel);

        springLayout.putConstraint(SpringLayout.WEST, outputLabel, 400, SpringLayout.EAST, this);
        springLayout.putConstraint(SpringLayout.NORTH, outputLabel, 30, SpringLayout.NORTH, this);


    }


    public void setInputLabel() {

        inputLabel = new JLabel();
        inputLabel.setText("Input Context Free Grammar:");
        add(inputLabel);

        springLayout.putConstraint(SpringLayout.WEST, inputLabel, 20, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.NORTH, inputLabel, 30, SpringLayout.NORTH, this);

    }

    public void convertCFGtoCNF(){
        cnf.setInputandLineCount(inputTextArea.getText(), inputTextArea.getLineCount());
        cnf.convertCFGtoCNF();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String button = e.getActionCommand();

        if(button.equals("Convert") ){
            convertCFGtoCNF();
        }


    }



}
