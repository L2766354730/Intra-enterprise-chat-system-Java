package com.nuc.ljx.page;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.nuc.ljx.util.SendMessageUtil;

/**
 * 注册界面 注册用户的时候 userid addtime都不需要用户输入 这个是后端生成的 username 账号，注册的时候系统随机生成的 
 * 填写 昵称 密码 重复密码 签名 性别 头像
 * 
 * @author 11018
 *
 */
public class RegisterPage extends JFrame {
	private static final long serialVersionUID = 1L;

	public void init() {
		RegisterPage registerPage = this;
		// 1、设置页面的布局 GridLayout null--绝对布局
		this.setLayout(new GridLayout(7, 2));
		// 昵称和文本输入框
		JLabel jl1 = new JLabel("昵称",JLabel.CENTER);
		jl1.setFont(new Font("楷体", Font.BOLD, 20));
		JTextField jtf = new JTextField("请输入你的昵称");
		jtf.setFont(new Font("楷体", Font.BOLD, 14));
		this.add(jl1);this.add(jtf);
		jtf.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				jtf.setText("");
			}
		});
		// 密码和密码输入框
		JLabel jl2 = new JLabel("密码",JLabel.CENTER);
		jl2.setFont(new Font("楷体", Font.BOLD, 20));
		JPasswordField jpf = new JPasswordField("");
		this.add(jl2);this.add(jpf);
		
		// 再次输入密码和密码输入框
		JLabel jl3 = new JLabel("请确认密码",JLabel.CENTER);
		jl3.setFont(new Font("楷体", Font.BOLD, 20));
		JPasswordField jpf1 = new JPasswordField("");
		this.add(jl3);this.add(jpf1);
		
		// 个性签名和文本输入框
		JLabel jl4 = new JLabel("个性签名",JLabel.CENTER);
		jl4.setFont(new Font("楷体", Font.BOLD, 20));
		JTextField jtf1 = new JTextField("请输入你的个性签名");
		jtf1.setFont(new Font("楷体", Font.BOLD, 14));
		this.add(jl4);this.add(jtf1);
		jtf1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				jtf1.setText("");
			}
		});
		//性别-----单选按钮 必须将单选按钮加到一个按钮组中才能实现单选效果
		JLabel jl5 = new JLabel("性别",JLabel.CENTER);
		jl5.setFont(new Font("楷体", Font.BOLD, 20));
		ButtonGroup bg = new ButtonGroup();//不是组件
		JRadioButton  jrb = new JRadioButton("男");
		jrb.setSelected(true);
		JRadioButton  jrb1 = new JRadioButton("女");
		bg.add(jrb);
		bg.add(jrb1);
	
		JPanel jp = new JPanel();
		jp.add(jrb);
		jp.add(jrb1);
		jp.setBorder(BorderFactory.createEtchedBorder());
		this.add(jl5);this.add(jp);;
		
		//头像
		JLabel jl6 = new JLabel("头像",JLabel.CENTER);
		jl6.setFont(new Font("楷体", Font.BOLD, 20));
		JLabel jl7 = new JLabel("请选择你的头像");//文本输入框
		//双击jl7开启一个文件选择器 选择文件
		jl7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int clickCount = e.getClickCount();
				if (clickCount == 2) {
					/**
					 * 创建一个文件选择器
					 */
					JFileChooser jfc = new JFileChooser();
					//设置一个文件选择器可以选择什么东西
					jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
					int result = jfc.showOpenDialog(null);
					//如果用户在文件选择器选择了东西 result!=1  
					if (result != 1) {
						//file是我们在文件选择器中选择的文件 a.png
						File file = jfc.getSelectedFile();
						String fileName = file.getName();
						//1、判断一下文件是不是图片文件 jpg JPEG png gif
						String[] array = fileName.split("\\.");
						String suffixName = array[array.length-1];
						List<String> names = new ArrayList<String>();
						names.add("jpg");names.add("JPEG");names.add("png");names.add("gif");
						//判断一下你上传的是不是图片
						if (names.contains(suffixName)) {
							//上传到本地的文件路径  images/12312312313.png
							String path = "images/"+System.currentTimeMillis()+"."+suffixName;
							//IO流的传输  字节流  文件复制
							try {
								FileInputStream fis = new FileInputStream(file);
								FileOutputStream fos = new FileOutputStream(new File(path));
								int read =0;
								while((read = fis.read()) != -1) {
									fos.write(read);
								}
								//将上传成功的文件路径path赋值给jl7这个JLbel文本组件
								jl7.setText(path);
							} catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}else {
							JOptionPane.showMessageDialog(null, "选择的不是图片文件");
						}
					}else {
						JOptionPane.showMessageDialog(null, "没有选择头像");
						
					}
				}
			}
		});
		jl7.setBorder(BorderFactory.createEtchedBorder());
		 
		jl7.setFont(new Font("楷体", Font.BOLD, 15));
		this.add(jl6);this.add(jl7);

		
		JButton jb1 = new JButton("去登录");
		jb1.setFont(new Font("楷体", Font.BOLD, 20));
		jb1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new LoginPage().init("");
				registerPage.dispose();
			}
		});
		JButton jb2 = new JButton("注册");
		jb2.setFont(new Font("楷体", Font.BOLD, 20));
		jb2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//1、获取到用户输入的所有注册信息 包括头像信息
				String nickname = jtf.getText();
				String pass = jpf.getText();
				String repass = jpf1.getText();
				String sign = jtf1.getText();
				String sex="";
				if (jrb.isSelected()) {
					sex = "男";
				}else {
					sex = "女";
				}
				//头像路径两种情况  images/xxxxx.jpg   请选择你的头像
				String headerPath = jl7.getText();
				//如果用户没有选择头像，那么根据性别赋值一个性别的默认头像
				if (headerPath.equals("请选择你的头像")) {
					if (sex.equals("男")) {
						headerPath = "images/man.png";
					}else {
						headerPath = "images/woman.png";
					}
				}
				//2、校验两次输入的密码是否一致
				if (pass == null || pass.equals("") || repass == null || repass.equals("")) {
					JOptionPane.showMessageDialog(null, "密码未输入！");
					return;
				}
				if (!pass.equals(repass)) {
					JOptionPane.showMessageDialog(null, "两次输入密码不一致！");
					return;
				}
				//3、准备一个字符串传递给后端 
				//0=nickname=password=sign=sex=headerPath
				String msg = "0="+nickname+"="+pass+"="+sign+"="+sex+"="+headerPath;
				//4、发送数据给服务端完成注册逻辑
				try {
					SendMessageUtil.sendMessage(msg);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		this.add(jb1);this.add(jb2);
		// 0、设置页面的相关配置项
		this.setVisible(true);
		this.setSize(500, 400);
		this.setResizable(false);// 设置界面不可拖拉拽变形
		this.setTitle("注册界面");
		// 设置页面的LOGO
		this.setIconImage(getToolkit().getImage("images/logo.png"));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
