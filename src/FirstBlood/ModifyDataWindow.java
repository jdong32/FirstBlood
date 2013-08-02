package FirstBlood;

import java.awt.*;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModifyDataWindow implements ActionListener {
	JDialog dialog;
	Font myFont = new Font("黑体", Font.PLAIN, 13);
	JButton submit = new JButton("确定");
	JButton cancel = new JButton("取消");
	JLabel label0 = new JLabel("支行名称:");
	JTextField field1 = new JTextField();
	JLabel label5 = new JLabel("机构名称:");
	JTextField field2 = new JTextField();
	JLabel label6 = new JLabel("金额值:");
	JTextField field3 = new JTextField();
	JLabel label4 = new JLabel("日期:");
	JTextField field4 = new JTextField();
    
	public ModifyDataWindow(JFrame f) {
		dialog = new JDialog(f, "更改数据", true);
		Container dialogPane = dialog.getContentPane();
		dialogPane.setLayout(null);
		
		label0.setFont(myFont);
		label0.setBounds(10, 10, 130, 30);	
		dialogPane.add(label0);

		field1.setFont(myFont);
		field1.setSize(150, 30);
		field1.setLocation(110, 10);
		dialogPane.add(field1);
		
		label5.setFont(myFont);
		label5.setBounds(10, 50, 130, 30);
		dialogPane.add(label5);

		field2.setFont(myFont);
		field2.setSize(150, 30);
		field2.setLocation(110, 50);
		dialogPane.add(field2);
		
		label4.setFont(myFont);
		label4.setBounds(10, 90, 130, 30);
		dialogPane.add(label4);
	
		field4.setFont(myFont);
		field4.setSize(150, 30);
		field4.setLocation(110, 90);
		dialogPane.add(field4);
			
		label6.setFont(myFont);
		label6.setBounds(10, 130, 130, 30);
		dialogPane.add(label6);
	
		field3.setFont(myFont);
		field3.setSize(150, 30);
		field3.setLocation(110, 130);
		dialogPane.add(field3);
		
		submit.setFont(myFont);
		submit.setBackground(Color.WHITE);
		submit.setBounds(40, 180, 70, 30);
		dialogPane.add(submit);
		submit.addActionListener(this);
		
		cancel.setFont(myFont);
		cancel.setBackground(Color.WHITE);
		cancel.setBounds(168, 180, 70, 30);
		dialogPane.add(cancel);
		cancel.addActionListener(this);
		dialog.setBounds(550, 220, 300, 270);
		dialog.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submit) {
			String branch = field1.getText();
			String company = field2.getText();
			String amount = field3.getText();
			String date = field4.getText();
			ModifyData md = new ModifyData();
			
			long val = 0;
			try {
				val = Long.parseLong(amount);
				md.run(branch, company, date, val);
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
