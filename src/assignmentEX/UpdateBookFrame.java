package assignmentEX;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class UpdateBookFrame extends JFrame {

	private JPanel contentPane;

	Operator o = null;
	private JTextField txt_title;
	private JTextField txt_author;
	private JTextField txt_genre;
	private JTextField txt_pc;
	private JTextField txt_stock;
	private JTextField txt_num;
	private JRadioButton rdbtn_ok;
	private JRadioButton rdbtn_not;
	
	UpdateBookFrame(Operator _o) {
		setTitle("도서 정보 수정");
		o = _o;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 433, 257);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(5, 5));
		
		JPanel btnPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) btnPanel.getLayout();
		flowLayout.setVgap(10);
		flowLayout.setHgap(10);
		contentPane.add(btnPanel, BorderLayout.SOUTH);
		
		JButton btn_update = new JButton("수정하기");
		btnPanel.add(btn_update);
		
		JPanel txtPanel = new JPanel();
		txtPanel.setBorder(new EmptyBorder(5, 0, 0, 0));
		contentPane.add(txtPanel, BorderLayout.CENTER);
		GridBagLayout gbl_txtPanel = new GridBagLayout();
		gbl_txtPanel.columnWidths = new int[]{57, 200, 0};
		gbl_txtPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_txtPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_txtPanel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		txtPanel.setLayout(gbl_txtPanel);
		
		JLabel lbl_num = new JLabel("도서 번호");
		GridBagConstraints gbc_lbl_num = new GridBagConstraints();
		gbc_lbl_num.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_num.gridx = 0;
		gbc_lbl_num.gridy = 0;
		txtPanel.add(lbl_num, gbc_lbl_num);
		
		JPanel panel = new JPanel();
		panel.setForeground(Color.BLACK);
		panel.setBorder(new LineBorder(Color.GRAY));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 0;
		txtPanel.add(panel, gbc_panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		txt_num = new JTextField();
		txt_num.setHorizontalAlignment(SwingConstants.LEFT);
		txt_num.setColumns(10);
		panel.add(txt_num);
		
		JButton btn_select = new JButton("조회");
		panel.add(btn_select);
		
		JLabel lbl_title = new JLabel("제목");
		GridBagConstraints gbc_lbl_title = new GridBagConstraints();
		gbc_lbl_title.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_title.gridx = 0;
		gbc_lbl_title.gridy = 1;
		txtPanel.add(lbl_title, gbc_lbl_title);
		
		txt_title = new JTextField();
		txt_title.setColumns(17);
		GridBagConstraints gbc_txt_title = new GridBagConstraints();
		gbc_txt_title.fill = GridBagConstraints.BOTH;
		gbc_txt_title.insets = new Insets(0, 0, 5, 0);
		gbc_txt_title.gridx = 1;
		gbc_txt_title.gridy = 1;
		txtPanel.add(txt_title, gbc_txt_title);
		
		JLabel lbl_author = new JLabel("저자");
		GridBagConstraints gbc_lbl_author = new GridBagConstraints();
		gbc_lbl_author.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_author.gridx = 0;
		gbc_lbl_author.gridy = 2;
		txtPanel.add(lbl_author, gbc_lbl_author);
		
		txt_author = new JTextField();
		txt_author.setColumns(17);
		GridBagConstraints gbc_txt_author = new GridBagConstraints();
		gbc_txt_author.fill = GridBagConstraints.BOTH;
		gbc_txt_author.insets = new Insets(0, 0, 5, 0);
		gbc_txt_author.gridx = 1;
		gbc_txt_author.gridy = 2;
		txtPanel.add(txt_author, gbc_txt_author);
		
		JLabel lbl_genre = new JLabel("분야");
		GridBagConstraints gbc_lbl_genre = new GridBagConstraints();
		gbc_lbl_genre.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_genre.gridx = 0;
		gbc_lbl_genre.gridy = 3;
		txtPanel.add(lbl_genre, gbc_lbl_genre);
		
		txt_genre = new JTextField();
		txt_genre.setColumns(17);
		GridBagConstraints gbc_txt_genre = new GridBagConstraints();
		gbc_txt_genre.fill = GridBagConstraints.BOTH;
		gbc_txt_genre.insets = new Insets(0, 0, 5, 0);
		gbc_txt_genre.gridx = 1;
		gbc_txt_genre.gridy = 3;
		txtPanel.add(txt_genre, gbc_txt_genre);
		
		JLabel lbl_pc = new JLabel("출판사");
		GridBagConstraints gbc_lbl_pc = new GridBagConstraints();
		gbc_lbl_pc.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_pc.gridx = 0;
		gbc_lbl_pc.gridy = 4;
		txtPanel.add(lbl_pc, gbc_lbl_pc);
		
		txt_pc = new JTextField();
		txt_pc.setColumns(17);
		GridBagConstraints gbc_txt_pc = new GridBagConstraints();
		gbc_txt_pc.fill = GridBagConstraints.BOTH;
		gbc_txt_pc.insets = new Insets(0, 0, 5, 0);
		gbc_txt_pc.gridx = 1;
		gbc_txt_pc.gridy = 4;
		txtPanel.add(txt_pc, gbc_txt_pc);
		
		JLabel lbl_stock = new JLabel("수량");
		GridBagConstraints gbc_lbl_stock = new GridBagConstraints();
		gbc_lbl_stock.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_stock.gridx = 0;
		gbc_lbl_stock.gridy = 5;
		txtPanel.add(lbl_stock, gbc_lbl_stock);
		
		txt_stock = new JTextField();
		txt_stock.setColumns(17);

		GridBagConstraints gbc_txt_stock = new GridBagConstraints();
		gbc_txt_stock.fill = GridBagConstraints.BOTH;
		gbc_txt_stock.insets = new Insets(0, 0, 5, 0);
		gbc_txt_stock.gridx = 1;
		gbc_txt_stock.gridy = 5;
		txtPanel.add(txt_stock, gbc_txt_stock);
		
		JLabel lblNewLabel = new JLabel("대여가능 여부");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 6;
		txtPanel.add(lblNewLabel, gbc_lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(Color.GRAY));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 6;
		txtPanel.add(panel_1, gbc_panel_1);
		
		JRadioButton rdbtn_ok = new JRadioButton("대여 가능");
		rdbtn_ok.setSelected(true);
		panel_1.add(rdbtn_ok);
		
		JRadioButton rdbtn_not = new JRadioButton("대여 불가");
		rdbtn_not.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(rdbtn_not);
		
		// 라디오 버튼을 그룹으로 묶기
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(rdbtn_ok);
		buttonGroup.add(rdbtn_not);
		
		btn_select.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int num = Integer.parseInt(txt_num.getText().trim());
				ResultSet result = o.db.selectBookinfo(num);
				if(result != null) {
					try {
						if(result.next()) {
							txt_title.setText(result.getString(1));
							txt_author.setText(result.getString(2));
							txt_genre.setText(result.getString(3));
							txt_pc.setText(result.getString(4));
							txt_stock.setText(""+result.getInt(5));
							String status = result.getString(6);
							if(status.equals("대여가능")) {
		                        rdbtn_ok.setSelected(true);
							}else {
		                        rdbtn_not.setSelected(true);
							}
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						System.out.println("도서 정보 조회 실패 > " + e.toString());
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "해당 번호의 도서가 존재하지 않습니다. 도서 번호를 다시 확인해주세요.");
				}
			}
		});
		
		btn_update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField[] jf = {txt_title, txt_author, txt_genre, txt_pc, txt_stock, txt_num};
				if(isValidInput(jf)) {
					String title = txt_title.getText().trim();
					String author = txt_author.getText().trim();
					String genre = txt_genre.getText().trim();
					String pc = txt_pc.getText().trim();
					int stock = Integer.parseInt(txt_stock.getText().trim());
					int num = Integer.parseInt(txt_num.getText().trim());
					String status;
					if(rdbtn_ok.isSelected()) {
						status = "대여가능";
					}
					else {
						status = "대여불가";
					}
					if(o.db.updateBook(title, author, genre, pc, stock, status, num)) {
						JOptionPane.showMessageDialog(null, "수정되었습니다.");
						ResultSet rs = o.db.selectAllBookBW();
						o.af.refreshTable(rs);
					}
					else {
						JOptionPane.showMessageDialog(null, "도서 정보 수정에 실패했습니다.");
					}
				} else {
					JOptionPane.showMessageDialog(null, "값을 입력해주세요!", "경고", JOptionPane.WARNING_MESSAGE);
				}
	
			}
		});
		
		txt_stock.addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyTyped(KeyEvent e) {
	            char inputChar = e.getKeyChar();
	            if (!Character.isDigit(inputChar)) {
	                e.consume(); // 입력된 문자를 무시하도록 함
	            }
	        }
	    });
		
		setSize(340, 323);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	}
	
	private static boolean isValidInput(JTextField[] jf) {
	    boolean flag = false; // flag 변수 초기화
	    for (int i = 0; i < jf.length; i++) { // while 루프를 for 루프로 수정
	        if (jf[i].getText() == null || jf[i].getText().trim().isEmpty()) {
	        	jf[i].requestFocus();
	            return false;
	        } else {
	            flag = true;
	        }
	    }
	    return flag;
	}

	
	public void change(JFrame frame) {
		this.getContentPane().removeAll();
	    setContentPane(frame.getContentPane());
	    revalidate();
	    repaint();
	}
	
	public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
	    if (rs == null) {
	        return new DefaultTableModel();
	    }

	    ResultSetMetaData metaData = rs.getMetaData();

	    // 테이블 열 정보 가져오기
	    int columnCount = metaData.getColumnCount();
	    String[] columnNames = new String[columnCount];
	    for (int i = 1; i <= columnCount; i++) {
	        columnNames[i - 1] = metaData.getColumnLabel(i);
	    }

	    // 테이블 데이터 가져오기
	    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
	    while (rs.next()) {
	        Object[] rowData = new Object[columnCount];
	        for (int i = 1; i <= columnCount; i++) {
	            rowData[i - 1] = rs.getObject(i);
	        }
	        tableModel.addRow(rowData);
	    }

	    return tableModel;
	}

}
