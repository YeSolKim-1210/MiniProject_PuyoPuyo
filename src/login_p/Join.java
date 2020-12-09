package login_p;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;

import jdbc_p.GameUserDAO;
import jdbc_p.GameUserDTO;
import jdbc_p.RankDAO;
import java.awt.Font;
import javax.swing.JComboBox;

public class Join extends JFrame {

	public ArrayList<String> data = new ArrayList<String>(); // id, pw, name, email, birth ���� ���� �ؼ� main���� �Ѱ���

	GameUserDTO dto = new GameUserDTO();
	GameUserDAO dao = new GameUserDAO();

	String[] regex = { "^[a-zA-Z0-9_]{5,12}$", // ���̵� ����� ���ڸ� 5���ڿ��� 12���� ����
			"^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$", // ��������Ư������ ���� 8�� �̻�
			"^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$", // ��������Ư������ ���� 8�� �̻�
			"^[��-�R]{2,4}$", // �̸��ѱ� 2~4��
			"^[0-9a-zA-Z_]{2,}@[a-zA-Z]{2,}.(([a-zA-Z]{2,})|([a-zA-Z]{2,}.[a-zA-Z]{2,}))" // �̸��� ��ȿ�� �˻�
	};

	public String id; // id ��Ƶ� ����
	public String pw; // pw ��Ƶ� ����
	public String pw2; // pwȮ�ζ�
	public String name; // �̸�
	public String email; // ����
	public String birth; // ����

	JTextField JoinIdText;
	JPasswordField passwordField;
	JPasswordField passwordField_1;
	JTextField JoinNameText;
	JTextField JoinEmailText;

	JButton btnCancelMake; // ���� ��ư
	JButton btnMakeMember; // ȸ�����Խ� �Ϸ� ��ư
	JButton btnIdChk; // ���̵� �ߺ� �˻�
	int monthL = 1;
	int dayL = 1;
	int yearL = 2020;
	int days = 1;
	int lastday;
	int i=0;

	boolean truechk = false;
	boolean monchk = false;

	public Join() {

		getContentPane().setForeground(Color.RED); // ȸ�� ���� guiâ

		setTitle("ȸ������");
		setIconImage(new ImageIcon("./img/logo.png").getImage());
		getContentPane().setLayout(null);
		setSize(600, 700);
		setLocationRelativeTo(null); // gui ȭ�� �� �߾����� ������
		getContentPane().setLayout(null);
		setResizable(false); // gui ������ ���� �Ұ�

		JLabel lbJoinId = new JLabel("���̵�");
		lbJoinId.setBounds(98, 95, 74, 27);
		getContentPane().add(lbJoinId);

		JLabel lbJoinPw = new JLabel("��й�ȣ");
		lbJoinPw.setBounds(98, 145, 74, 27);
		getContentPane().add(lbJoinPw);

		JLabel lbJoinPwC = new JLabel("��й�ȣ Ȯ��");
		lbJoinPwC.setBounds(98, 195, 122, 27);
		getContentPane().add(lbJoinPwC);

		JLabel lbName = new JLabel("�̸�");
		lbName.setBounds(98, 245, 74, 27);
		getContentPane().add(lbName);

		JLabel lbJoinBirth = new JLabel("�������");
		lbJoinBirth.setBounds(98, 295, 74, 27);
		getContentPane().add(lbJoinBirth);

		JLabel lbJoinEmail = new JLabel("�̸���");
		lbJoinEmail.setBounds(98, 345, 74, 27);
		getContentPane().add(lbJoinEmail);

		JoinIdText = new JTextField();

		JoinIdText.setBounds(223, 95, 187, 27);
		JoinIdText.setColumns(10);
		getContentPane().add(JoinIdText);

		passwordField = new JPasswordField();
		passwordField.setBounds(223, 145, 187, 27);
		getContentPane().add(passwordField);

		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(223, 195, 187, 27);
		getContentPane().add(passwordField_1);

		JoinNameText = new JTextField();
		JoinNameText.setColumns(10);
		JoinNameText.setBounds(223, 245, 187, 27);
		getContentPane().add(JoinNameText);

		JoinEmailText = new JTextField();
		JoinEmailText.setColumns(10);
		JoinEmailText.setBounds(223, 345, 187, 27);
		getContentPane().add(JoinEmailText);

		btnCancelMake = new JButton("����");
		btnCancelMake.setBackground(Color.LIGHT_GRAY);
		btnCancelMake.setBounds(313, 435, 129, 29);
		getContentPane().add(btnCancelMake);
		btnCancelMake.addActionListener(butAct);

		btnMakeMember = new JButton("ȸ������");
		btnMakeMember.setBackground(Color.LIGHT_GRAY);
		btnMakeMember.setBounds(142, 435, 129, 29);
		getContentPane().add(btnMakeMember);
		btnMakeMember.addActionListener(butAct);
		btnMakeMember.setEnabled(false);

		btnIdChk = new JButton("�ߺ�Ȯ��");
		btnIdChk.setBackground(Color.LIGHT_GRAY);
		btnIdChk.setBounds(424, 95, 108, 27);
		getContentPane().add(btnIdChk);

		JLabel lblNewLabel = new JLabel("���� �Ǵ� ���ڷ� 5���� �̻�");
		lblNewLabel.setFont(new Font("����", Font.PLAIN, 11));
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setBounds(223, 120, 187, 21);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("����,����,Ư������ ���� 8���� �̻�");
		lblNewLabel_1.setFont(new Font("����", Font.PLAIN, 11));
		lblNewLabel_1.setForeground(Color.BLACK);
		lblNewLabel_1.setBounds(223, 170, 187, 21);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_3 = new JLabel("�ѱ� 2~4����");
		lblNewLabel_3.setFont(new Font("����", Font.PLAIN, 11));
		lblNewLabel_3.setForeground(Color.BLACK);
		lblNewLabel_3.setBounds(223, 270, 187, 21);
		getContentPane().add(lblNewLabel_3);

		JLabel lblNewLabel_5 = new JLabel("ex) ���̵�@������ �ּ�");
		lblNewLabel_5.setFont(new Font("����", Font.PLAIN, 11));
		lblNewLabel_5.setForeground(Color.BLACK);
		lblNewLabel_5.setBounds(223, 370, 187, 21);
		getContentPane().add(lblNewLabel_5);

		Calendar calders = Calendar.getInstance();

		JComboBox year = new JComboBox(); // ��
		year.setBounds(223, 297, 74, 23);
		for (int i = 1920; i <= 2020; i++) {
			year.addItem(i);
		}

		JComboBox month = new JComboBox(); // ��
		month.setBounds(322, 297, 52, 23);
		for (int i = 1; i <= 12; i++) {
			month.addItem(i);
		}

		JComboBox<Integer> day = new JComboBox(); // ��
		day.setBounds(396, 297, 46, 23);

		for (int j = 1; j <= 31; j++) {
			day.addItem(j);
		}
		
		
		year.setSelectedItem(2020);
		getContentPane().add(year);
		getContentPane().add(month);
		getContentPane().add(day);

		
		year.addActionListener(new  ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				yearL = (int) year.getItemAt(year.getSelectedIndex());
			}
		});
		
		
		month.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			

				if(day.getItemCount()>1) {
					day.removeAllItems();
				}
				
				monthL = (int) month.getItemAt(month.getSelectedIndex());
				calders.set(yearL, monthL - 1, days);
				lastday = calders.getActualMaximum(Calendar.DAY_OF_MONTH);
				
				for (int j = 1; j <= lastday; j++) {
					day.addItem(j);
				}

			}
		});
		
	
		day.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(day.getSelectedItem()!=null) {
					dayL = (int) day.getItemAt(day.getSelectedIndex());
					System.out.println(dayL);
				}
			}
		});
		


		JLabel lblNewLabel_2 = new JLabel("��");
		lblNewLabel_2.setBounds(300, 295, 21, 27);
		getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_2_1 = new JLabel("��");
		lblNewLabel_2_1.setBounds(378, 295, 21, 27);
		getContentPane().add(lblNewLabel_2_1);

		JLabel lblNewLabel_2_1_1 = new JLabel("��");
		lblNewLabel_2_1_1.setBounds(446, 295, 21, 27);
		getContentPane().add(lblNewLabel_2_1_1);

		btnIdChk.addActionListener(butAct);

		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	ActionListener butAct = new ActionListener() { // �׼Ǹ���Ʈ

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnCancelMake) { // ���� ��ư ���� �� �α��� â���� ȭ�� ��ȯ

				Login lt = new Login();
				dispose();

			}

			if (e.getSource() == btnIdChk) { // �ߺ� �˻�
				String jt = JoinIdText.getText().trim();
				if ("".equals(jt) || !Pattern.matches(regex[0], jt)) {
					JOptionPane.showMessageDialog(null, "���̵� Ȯ�� ���ּ���");
				} else if (dao.idChk(jt) == 0) {
					JOptionPane.showMessageDialog(null, JoinIdText.getText() + " �ߺ��Դϴ�.");
				} else if (dao.idChk(jt) == 1 && Pattern.matches(regex[0], jt)) {
					JOptionPane.showMessageDialog(null, JoinIdText.getText() + " ��밡���մϴ�.");
					JoinIdText.setEditable(false);
					btnMakeMember.setEnabled(true);
				}

			}

			

			if (e.getSource() == btnMakeMember) { // ȸ�� ����
				id = JoinIdText.getText().trim();
				pw = passwordField.getText().trim();
				pw2 = passwordField_1.getText().trim();
				name = JoinNameText.getText().trim();
				birth = yearL + "��" + monthL + "��" + dayL + "��";
				email = JoinEmailText.getText().trim();
				
				
				System.out.println(birth);

				String[] joinChk = { id, pw, pw2, name, email }; // ��ȿ�� �˻縦 ���� �迭��
				String[] pppChk = { "id�� Ȯ�� ���ּ���", "��й�ȣ�� Ȯ���� �ּ���", "��й�ȣ�� Ȯ���� �ּ���", "�̸��� Ȯ���� �ּ���", "email�� Ȯ���� �ּ���" }; // ��������

				while (true) { // while ���� ������ ��ȿ�� �˻� ����
					int i = 0; // id�� ��ȿ�� �˻� �迭, ���� �˸��޼��� �迭 �ε����� ǥ�� �� ����
					if (true) {
						if (Pattern.matches(regex[i], joinChk[i])) { // ������ ��ġ�ϸ� data�� ����
							dto.setId(id);
						} else { // ��ġ ���� ���� �� idȮ�� �޼��� â
							JOptionPane.showMessageDialog(null, pppChk[i]);
							break;
						}
						i++;

						if (Pattern.matches(regex[i], joinChk[i])) { // ������ ��ġ�ϸ� data�� ����
						} else { // ��ġ ���� ���� �� idȮ�� �޼��� â
							JOptionPane.showMessageDialog(null, pppChk[i]);
							break;
						}

						i++; // id ����� i�� �������� ���� �迭 ���� ����
						if (Pattern.matches(regex[i], joinChk[i])) {
							if (pw2.equals(pw))
								dto.setPw(pw);
						} else {
							JOptionPane.showMessageDialog(null, pppChk[i]);
							break;
						}
						i++;
						if (Pattern.matches(regex[i], joinChk[i])) {
							dto.setName(name);
						} else {
							JOptionPane.showMessageDialog(null, pppChk[i]);
							break;
						}

						if (yearL==0 || monthL==0 || dayL==0) {	
							JOptionPane.showMessageDialog(null, "����������Է����ּ���");
							break;
						} else {
							dto.setBirth(birth);
							
						}
						i++;

						if (Pattern.matches(regex[i], joinChk[i])) {
							dto.setEmail(email);
						} else {
							JOptionPane.showMessageDialog(null, pppChk[i]);
							break;
						}

					}

					new GameUserDAO().insert(dto);
					JOptionPane.showMessageDialog(null, name + "�� ȸ������ �Ϸ�");

					new RankDAO().insertId(id);

					Login lt = new Login();
					dispose();
					break;

				}

			}

		}
	};
	
	public static void main(String[] args) {
		new Join();
	}
}