package assignmentEX;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class AdminFrame extends JFrame {

	private JPanel contentPane;

	Operator o = null;
	private JTable table;
	private JTextField txt_searchTitle;
	private JTextField txt_searchNum;
	
	AdminFrame(Operator _o) {
		setTitle("도서 대여 관리 프로그램 - 관리자 모드");
		o = _o;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 989, 662);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("관리자 메뉴");
		menuBar.add(mnNewMenu);
		
		JMenuItem MenuItem_Main = new JMenuItem("메인페이지");
		MenuItem_Main.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				o.af = new AdminFrame(o);
				change(o.af);
			}
		});
		mnNewMenu.add(MenuItem_Main);
		
		JMenuItem MenuItem_statistics = new JMenuItem("인기 도서 순위");
		MenuItem_statistics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				o.pf = new PopularBookFrame(o);
				change(o.pf);
			}
		});
		mnNewMenu.add(MenuItem_statistics);
		
		JMenu mnNewMenu_1 = new JMenu("도서 관리");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem MenuItem_AddBook = new JMenuItem("도서 등록");
		MenuItem_AddBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				o.abf = new AddBookFrame(o);
				o.abf.setVisible(true);
			}
		});
		mnNewMenu_1.add(MenuItem_AddBook);
		
		JMenuItem MenuItem_UpdateBook = new JMenuItem("도서 정보 수정");
		MenuItem_UpdateBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				o.ubf = new UpdateBookFrame(o);
				o.ubf.setVisible(true);
			}
		});
		mnNewMenu_1.add(MenuItem_UpdateBook);
		
		JMenu mnNewMenu_2 = new JMenu("대여 관리");
		menuBar.add(mnNewMenu_2);
		
		JMenuItem MenuItem_AllCheckoutList = new JMenuItem("전체 대여 내역");
		MenuItem_AllCheckoutList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				o.cf = new CheckoutFrame(o);
				change(o.cf);
			}
		});
		mnNewMenu_2.add(MenuItem_AllCheckoutList);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel tablePanel = new JPanel();
		contentPane.add(tablePanel, BorderLayout.CENTER);
		tablePanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		tablePanel.add(scrollPane);
		
		ResultSet rs = o.db.selectAllBookBW();
		
		try {
			table = new JTable(buildTableModel(rs));
			table.setFillsViewportHeight(true);
			scrollPane.setViewportView(table);
			
			JPanel panel = new JPanel();
			scrollPane.setColumnHeaderView(panel);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("도서 목록 로딩 실패 > " + e.toString());
		}
		
		// 테이블 열 모델 가져오기
		TableColumnModel columnModel = table.getColumnModel();

		//열 크기 조절
		TableColumn firstColumn = columnModel.getColumn(0);
		firstColumn.setPreferredWidth(30);

		TableColumn secondColumn = columnModel.getColumn(1);
		secondColumn.setPreferredWidth(220); 
		
		TableColumn lastColumn = columnModel.getColumn(5);
		lastColumn.setPreferredWidth(30);
		
		JPanel searchPanel = new JPanel();
		contentPane.add(searchPanel, BorderLayout.NORTH);
		searchPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		searchPanel.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("전체 도서 목록(역순)");
		lblNewLabel.setBorder(new EmptyBorder(0, 0, 5, 0));
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(0, 0, 5, 0));
		searchPanel.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.WEST);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_8 = new JPanel();
		panel_2.add(panel_8, BorderLayout.WEST);
		
		JLabel lbl_num = new JLabel("번호");
		panel_8.add(lbl_num);
		
		txt_searchNum = new JTextField();
		panel_8.add(txt_searchNum);
		txt_searchNum.setColumns(10);
		
		JButton btn_search1 = new JButton("검색");
		btn_search1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int searchnum = Integer.parseInt(txt_searchNum.getText().trim());
				ResultSet newResultSet = o.db.selectAllBookBW(searchnum);
				refreshTable(newResultSet);
			}
		});
		panel_8.add(btn_search1);
		
		JPanel panel_5 = new JPanel();
		panel_1.add(panel_5, BorderLayout.EAST);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_7 = new JPanel();
		panel_5.add(panel_7, BorderLayout.EAST);
		
		JLabel lbl_title = new JLabel("제목");
		panel_7.add(lbl_title);
		
		txt_searchTitle = new JTextField();
		panel_7.add(txt_searchTitle);
		txt_searchTitle.setColumns(30);
		
		JButton btn_search2 = new JButton("검색");
		btn_search2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String searchword = txt_searchTitle.getText().trim();
				ResultSet newResultSet = o.db.selectAllBookBW(searchword);
				refreshTable(newResultSet);
			}
		});
		panel_7.add(btn_search2);
		
		
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
	
	public void refreshTable(ResultSet rs) {
	    try {
	        DefaultTableModel tableModel = buildTableModel(rs);
	        table.setModel(tableModel);
	        
			// 테이블 열 모델 가져오기
			TableColumnModel columnModel = table.getColumnModel();
			//열 크기 조절
			TableColumn firstColumn = columnModel.getColumn(0);
			firstColumn.setPreferredWidth(30);

			TableColumn secondColumn = columnModel.getColumn(1);
			secondColumn.setPreferredWidth(220); 
			
			TableColumn lastColumn = columnModel.getColumn(5);
			lastColumn.setPreferredWidth(30); 
			
	    } catch (SQLException e) {
	        System.out.println("도서 목록 로딩 실패 > " + e.toString());
	    }
	}

}
