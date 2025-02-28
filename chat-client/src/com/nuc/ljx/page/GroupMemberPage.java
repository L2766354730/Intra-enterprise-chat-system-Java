package com.nuc.ljx.page;

import java.awt.Font;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import com.nuc.ljx.util.SendMessageUtil;

public class GroupMemberPage extends JFrame{
	private static final long serialVersionUID = 1L;
	public JScrollPane jsp;
	/**
	 * ����Ⱥ��Ա�б� �������groupId
	 * @param groupId
	 */
	public void init(String groupId) {
		/**
		 * 1�������˷���һ����Ϣ ��ȡ��ǰȺ��Ӧ��Ⱥ��Ա��Ϣ
		 *    17=groupId
		 */
		String msg = "17="+groupId;
		try {
			SendMessageUtil.sendMessage(msg);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.setLayout(null);
		/**
		 * Ⱥ��Ա�б����
		 *   JScrollPaneֻ�����һ�����������������������JScrollPane�Ĺ����������
		 *   JScrollPane�����ӵ���JPanel��壬��������JPanel���ĵ�setPreferredSize�ڲ��ߴ� �������ڲ��ߴ�
		 *   �޷����ֹ�����
		 */
		JLabel jLabel = new JLabel("Ⱥ��Ա",JLabel.CENTER);
		jLabel.setBounds(2, 0, 480, 50);
		jLabel.setFont(new Font("����", Font.BOLD, 20));
		jsp = new JScrollPane();
		//���ù������ĺ����������ʧ
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setBounds(2, 50, 480, 700);
		this.add(jLabel);
		this.add(jsp);
		//0������ҳ������������
		this.setVisible(true);
		this.setSize(500,850);
		this.setResizable(false);//���ý��治������ק����
		this.setTitle("��ȺȺ��Ա�б�");
		this.setIconImage(getToolkit().getImage("images/logo.png"));
		this.setLocation(1000, 150);
	}

}