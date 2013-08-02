package FirstBlood;

import java.awt.*;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetRateWindow implements ActionListener {
	JDialog dialog;
	Font myFont = new Font("黑体", Font.PLAIN, 13);
	JButton submit = new JButton("确定");
	JButton cancel = new JButton("取消");
	JLabel label0 = new JLabel("欧元对美元汇率:");
	JTextField ratefield1 = new JTextField(myWindow.rateE);
	JLabel label5 = new JLabel("港币对美元汇率:");
	JTextField ratefield2 = new JTextField(myWindow.rateH);
	JLabel label6 = new JLabel("日元对美元汇率:");
	JTextField ratefield3 = new JTextField(myWindow.rateJ);
    
	public SetRateWindow(JFrame f) {
		dialog = new JDialog(f, "更改汇率", true);
		Container dialogPane = dialog.getContentPane();
		dialogPane.setLayout(null);
		
		label0.setFont(myFont);
		label0.setBounds(10, 10, 130, 30);	
		dialogPane.add(label0);

		ratefield1.setFont(myFont);
		ratefield1.setSize(60, 30);
		ratefield1.setLocation(110, 10);
		dialogPane.add(ratefield1);
		
		label5.setFont(myFont);
		label5.setBounds(10, 50, 130, 30);
		dialogPane.add(label5);

		ratefield2.setFont(myFont);
		ratefield2.setSize(60, 30);
		ratefield2.setLocation(110, 50);
		dialogPane.add(ratefield2);
			
		label6.setFont(myFont);
		label6.setBounds(10, 90, 130, 30);
		dialogPane.add(label6);
	
		ratefield3.setFont(myFont);
		ratefield3.setSize(60, 30);
		ratefield3.setLocation(110, 90);
		dialogPane.add(ratefield3);
		
		submit.setFont(myFont);
		submit.setBackground(Color.WHITE);
		submit.setBounds(10, 140, 70, 30);
		dialogPane.add(submit);
		submit.addActionListener(this);
		
		cancel.setFont(myFont);
		cancel.setBackground(Color.WHITE);
		cancel.setBounds(100, 140, 70, 30);
		dialogPane.add(cancel);
		cancel.addActionListener(this);
		dialog.setBounds(550, 220, 225, 230);
		dialog.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submit) {
			try {
				Float.parseFloat(ratefield1.getText());
				Float.parseFloat(ratefield2.getText());
				Float.parseFloat(ratefield3.getText());
				myWindow.rateE = ratefield1.getText();
				myWindow.rateH = ratefield2.getText();
				myWindow.rateJ = ratefield3.getText();
				dialog.dispose();
			} catch (Exception ee) {
				JOptionPane.showMessageDialog(null, "操作失败", "提示", JOptionPane.INFORMATION_MESSAGE);
			}
			
		} else if (e.getSource() == cancel) {
			//System.out.println("cancel");
			dialog.dispose();
		}
	}

}
