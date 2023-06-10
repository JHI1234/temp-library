package assignmentEX;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class AddBookFrame extends JFrame {

	private JPanel contentPane;

	Operator o = null;
	private JTextField txt_title;
	private JTextField txt_author;
	private JTextField txt_genre;
	private JTextField txt_pc;
	private JTextField txt_stock;
	
	AddBookFrame(Operator _o) {
		setTitle("도서 등록");
		o = _o;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 433, 257);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel btnPanel = new JPanel();
		contentPane.add(btnPanel, BorderLayout.SOUTH);
		
		JButton btn_add = new JButton("등록하기");
		btnPanel.add(btn_add);
		
		JPanel txtPanel = new JPanel();
		txtPanel.setBorder(new EmptyBorder(5, 0, 0, 0));
		contentPane.add(txtPanel, BorderLayout.CENTER);
		GridBagLayout gbl_txtPanel = new GridBagLayout();
		gbl_txtPanel.columnWidths = new int[]{57, 200, 0};
		gbl_txtPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_txtPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_txtPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		txtPanel.setLayout(gbl_txtPanel);
		
		JLabel lbl_title = new JLabel("제목");
		GridBagConstraints gbc_lbl_title = new GridBagConstraints();
		gbc_lbl_title.anchor = GridBagConstraints.EAST;
		gbc_lbl_title.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_title.gridx = 0;
		gbc_lbl_title.gridy = 0;
		txtPanel.add(lbl_title, gbc_lbl_title);
		
		txt_title = new JTextField();
		txt_title.setColumns(17);
		GridBagConstraints gbc_txt_title = new GridBagConstraints();
		gbc_txt_title.insets = new Insets(0, 0, 5, 0);
		gbc_txt_title.gridx = 1;
		gbc_txt_title.gridy = 0;
		txtPanel.add(txt_title, gbc_txt_title);
		
		JLabel lbl_author = new JLabel("저자");
		GridBagConstraints gbc_lbl_author = new GridBagConstraints();
		gbc_lbl_author.anchor = GridBagConstraints.EAST;
		gbc_lbl_author.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_author.gridx = 0;
		gbc_lbl_author.gridy = 1;
		txtPanel.add(lbl_author, gbc_lbl_author);
		
		txt_author = new JTextField();
		txt_author.setColumns(17);
		GridBagConstraints gbc_txt_author = new GridBagConstraints();
		gbc_txt_author.insets = new Insets(0, 0, 5, 0);
		gbc_txt_author.gridx = 1;
		gbc_txt_author.gridy = 1;
		txtPanel.add(txt_author, gbc_txt_author);
		
		JLabel lbl_genre = new JLabel("분야");
		GridBagConstraints gbc_lbl_genre = new GridBagConstraints();
		gbc_lbl_genre.anchor = GridBagConstraints.EAST;
		gbc_lbl_genre.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_genre.gridx = 0;
		gbc_lbl_genre.gridy = 2;
		txtPanel.add(lbl_genre, gbc_lbl_genre);
		
		txt_genre = new JTextField();
		txt_genre.setColumns(17);
		GridBagConstraints gbc_txt_genre = new GridBagConstraints();
		gbc_txt_genre.insets = new Insets(0, 0, 5, 0);
		gbc_txt_genre.gridx = 1;
		gbc_txt_genre.gridy = 2;
		txtPanel.add(txt_genre, gbc_txt_genre);
		
		JLabel lbl_pc = new JLabel("출판사");
		GridBagConstraints gbc_lbl_pc = new GridBagConstraints();
		gbc_lbl_pc.anchor = GridBagConstraints.EAST;
		gbc_lbl_pc.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_pc.gridx = 0;
		gbc_lbl_pc.gridy = 3;
		txtPanel.add(lbl_pc, gbc_lbl_pc);
		
		txt_pc = new JTextField();
		txt_pc.setColumns(17);
		GridBagConstraints gbc_txt_pc = new GridBagConstraints();
		gbc_txt_pc.insets = new Insets(0, 0, 5, 0);
		gbc_txt_pc.gridx = 1;
		gbc_txt_pc.gridy = 3;
		txtPanel.add(txt_pc, gbc_txt_pc);
		
		JLabel lbl_stock = new JLabel("수량");
		GridBagConstraints gbc_lbl_stock = new GridBagConstraints();
		gbc_lbl_stock.anchor = GridBagConstraints.EAST;
		gbc_lbl_stock.insets = new Insets(0, 0, 0, 5);
		gbc_lbl_stock.gridx = 0;
		gbc_lbl_stock.gridy = 4;
		txtPanel.add(lbl_stock, gbc_lbl_stock);
		
		txt_stock = new JTextField();
		txt_stock.setColumns(17);

		GridBagConstraints gbc_txt_stock = new GridBagConstraints();
		gbc_txt_stock.gridx = 1;
		gbc_txt_stock.gridy = 4;
		txtPanel.add(txt_stock, gbc_txt_stock);
		
		btn_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String title = txt_title.getText().trim();
				String author = txt_author.getText().trim();
				String genre = txt_genre.getText().trim();
				String pc = txt_pc.getText().trim();
				int stock = Integer.parseInt(txt_stock.getText().trim());
				if(o.db.addBook(title, author, genre, pc, stock)) {
					JOptionPane.showMessageDialog(null, "등록되었습니다.");
					ResultSet rs = o.db.selectAllBookBW();
					o.af.refreshTable(rs);
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
		
		setSize(309, 212);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
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
