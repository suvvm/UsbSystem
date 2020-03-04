package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;

@SuppressWarnings("serial")
public class DisplayNewRoot extends JFrame {

	private JPanel contentPane;

	public DisplayNewRoot(File file) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100,675,484);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("打开U盘");
		btnNewButton.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 17));
		btnNewButton.setBounds(382, 70, 120, 40);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ShowThisAllFile stal=new ShowThisAllFile(file);
				stal.setVisible(true);
			}
		});
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel(file+"已插入");
		lblNewLabel.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 25));
		lblNewLabel.setBounds(102, 67, 206, 46);
		contentPane.add(lblNewLabel);
	}
}
