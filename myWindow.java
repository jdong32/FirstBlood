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
        p = new JPanel(); // ����һ�����
        this.getContentPane().add(p);// �������ӵ����
        
        textfield = new JTextField(10);
        p.add(textfield); // ��һ���ı�����ӵ����
        Select = new JButton("���");
        p.add(Select); // ��һ�������ť��ӵ����
        Select.addActionListener(this);
        
        btnOK = new JButton("���ɳ���Excel�ļ�");
        p.add(btnOK);        
        btnOK.addActionListener(this);
        
        button = new JButton("��������Excel�ļ�");
        p.add(button);
        button.addActionListener(this);
  
    }

public void actionPerformed(ActionEvent e) {
        // ������ѡ��ť����һ���ļ�ѡ���ı�����ʾ�ļ�·��
        if (e.getSource() == Select) {
            int intRetVal = fc.showOpenDialog(this);
            if (intRetVal == JFileChooser.APPROVE_OPTION) {
                textfield.setText(fc.getSelectedFile().getPath());
            }
        } 
        
        else if (e.getSource() == btnOK) { 
            
        	// ��һ��
        	// �����°�ť������һ��������Excel�ļ�
        	
        } 
        
        else if (e.getSource() == button) { 
            
        	// �ڶ���
        	// �����°�ť������һ�����յ�Excel�ļ�
        	
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

