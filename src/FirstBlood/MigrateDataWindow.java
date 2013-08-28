package FirstBlood;

import java.awt.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MigrateDataWindow implements ActionListener {
	JDialog dialog;
	Font myFont = new Font("黑体", Font.PLAIN, 13);
	JButton submit = new JButton("确定");
	JButton cancel = new JButton("取消");
	JLabel label0 = new JLabel("机构名称:");
	JTextField field1 = new JTextField();
	JLabel label5 = new JLabel("原支行名称:");
	JTextField field2 = new JTextField();
	JLabel label6 = new JLabel("新支行名称:");
	JTextField field3 = new JTextField();
    
	public MigrateDataWindow(JFrame f) {
		dialog = new JDialog(f, "迁移数据", true);
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
			
		label6.setFont(myFont);
		label6.setBounds(10, 90, 130, 30);
		dialogPane.add(label6);
	
		field3.setFont(myFont);
		field3.setSize(150, 30);
		field3.setLocation(110, 90);
		dialogPane.add(field3);
		
		submit.setFont(myFont);
		submit.setBackground(Color.WHITE);
		submit.setBounds(40, 140, 70, 30);
		dialogPane.add(submit);
		submit.addActionListener(this);
		
		cancel.setFont(myFont);
		cancel.setBackground(Color.WHITE);
		cancel.setBounds(168, 140, 70, 30);
		dialogPane.add(cancel);
		cancel.addActionListener(this);
		dialog.setBounds(550, 220, 300, 230);
		dialog.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submit) {
			try {
				String company = field1.getText();
				String oldbranch = field2.getText();
				String newbranch = field3.getText();
				DataMigration dm = new DataMigration();
				dm.run(company, oldbranch, newbranch);
				dialog.dispose();
			} catch (Exception ee) {
				System.err.println(ee.getMessage());
				JOptionPane.showMessageDialog(null, "操作失败", "提示", JOptionPane.INFORMATION_MESSAGE);
			}
		} else if (e.getSource() == cancel) {
			//System.out.println("cancel");
			dialog.dispose();
		}
	}

}
