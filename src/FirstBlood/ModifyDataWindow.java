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
	JRadioButton check1 = new JRadioButton("汇入",true);
	JRadioButton check2 = new JRadioButton("汇出");		
	ButtonGroup bg = new ButtonGroup();
	public ModifyDataWindow(JFrame f) {
		dialog = new JDialog(f, "更改数据", true);
		Container dialogPane = dialog.getContentPane();
		dialogPane.setLayout(null);
		
		bg.add(check1);
		bg.add(check2);
		check1.setFont(myFont);
		check1.setBounds(10,10,90,30);
		dialogPane.add(check1);
		
		check2.setFont(myFont);
		check2.setBounds(100,10,130,30);
		dialogPane.add(check2);
		
		label0.setFont(myFont);
		label0.setBounds(10, 40, 130, 30);	
		dialogPane.add(label0);

		field1.setFont(myFont);
		field1.setSize(150, 30);
		field1.setLocation(110, 40);
		dialogPane.add(field1);
		
		label5.setFont(myFont);
		label5.setBounds(10, 80, 130, 30);
		dialogPane.add(label5);

		field2.setFont(myFont);
		field2.setSize(150, 30);
		field2.setLocation(110, 80);
		dialogPane.add(field2);
		
		label4.setFont(myFont);
		label4.setBounds(10, 120, 130, 30);
		dialogPane.add(label4);
	
		field4.setFont(myFont);
		field4.setSize(150, 30);
		field4.setLocation(110, 120);
		dialogPane.add(field4);
			
		label6.setFont(myFont);
		label6.setBounds(10, 160, 130, 30);
		dialogPane.add(label6);
	
		field3.setFont(myFont);
		field3.setSize(150, 30);
		field3.setLocation(110, 160);
		dialogPane.add(field3);
		
		submit.setFont(myFont);
		submit.setBackground(Color.WHITE);
		submit.setBounds(40, 210, 70, 30);
		dialogPane.add(submit);
		submit.addActionListener(this);
		
		cancel.setFont(myFont);
		cancel.setBackground(Color.WHITE);
		cancel.setBounds(168, 210, 70, 30);
		dialogPane.add(cancel);
		cancel.addActionListener(this);
		dialog.setBounds(550, 220, 300, 300);
		dialog.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {

		        
		if (e.getSource() == submit) {
			String branch = field1.getText();
			String company = field2.getText();
			String amount = field3.getText();
			String date = field4.getText();
			String type = null; //表示汇入汇出类型
			ModifyData md = new ModifyData();
			DefaultButtonModel model = (DefaultButtonModel) check1.getModel();
			//判断汇入、汇出单选状态
			if (model.getGroup().isSelected(model)){
				type = "in";
			}				
			else {
				type = "out";
			}
				
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
