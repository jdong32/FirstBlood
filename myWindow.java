import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import javax.swing.JPanel;

import javax.swing.JTextField;



public class myWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
    JButton button;
    JButton Select;
    JButton btnOK;


JTextField textfield;
    JPanel p;
    JFileChooser fc = new JFileChooser();
    TextArea area;

public myWindow() {
        p = new JPanel(); // 建立一个面板
        this.getContentPane().add(p);// 把面板添加到框架
        
        textfield = new JTextField(10);
        p.add(textfield); // 把一个文本框添加到面板
        Select = new JButton("浏览");
        p.add(Select); // 把一个浏览按钮添加到面板
        Select.addActionListener(this);
        
        btnOK = new JButton("生成初步Excel文件");
        p.add(btnOK);        
        btnOK.addActionListener(this);
        
        button = new JButton("生成最终Excel文件");
        p.add(button);
        button.addActionListener(this);
  
    }

public void actionPerformed(ActionEvent e) {
        // 当按下选择按钮，打开一个文件选择，文本框显示文件路径
        if (e.getSource() == Select) {
            int intRetVal = fc.showOpenDialog(this);
            if (intRetVal == JFileChooser.APPROVE_OPTION) {
                textfield.setText(fc.getSelectedFile().getPath());
            }
        } 
        
        else if (e.getSource() == btnOK) { 
            
        	// 第一步
        	// 当按下按钮，生成一个初步的Excel文件
        	
        } 
        
        else if (e.getSource() == button) { 
            
        	// 第二步
        	// 当按下按钮，生成一个最终的Excel文件
        	
        } 
       
    }
    


public static void main(String[] args) {
        myWindow frame = new myWindow();
        frame.setSize(200, 250);
        frame.setLocation(600,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

