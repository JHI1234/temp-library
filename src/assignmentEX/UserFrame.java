package assignmentEX;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.table.*;
import java.sql.*;

public class UserFrame extends JFrame {

	private JPanel contentPane;

	Operator o = null;
	String m_id; 
	private JTextField checkoutBookNum;
	private JTable table;
	
	UserFrame(Operator _o, String _id) {
		o = _o;
		m_id = _id;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 603, 518);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane((Component) null);
		contentPane.add(scrollPane);
		
		ResultSet rs = o.db.selectMemberCheckoutStatus(m_id);
		
		try {
			table = new JTable(buildTableModel(rs));
			table.setBorder(new EmptyBorder(5, 0, 5, 0));
			table.setFillsViewportHeight(true);
			scrollPane.setViewportView(table);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("도서 대여 목록 로딩 실패 > " + e.toString());
		}
		
		// 테이블 열 모델 가져오기
		TableColumnModel columnModel = table.getColumnModel();
		
		// 세 번째 열 크기 조절
		TableColumn thirdColumn = columnModel.getColumn(2);
		thirdColumn.setPreferredWidth(300);
		
		JPanel returnPanel = new JPanel();
		contentPane.add(returnPanel, BorderLayout.SOUTH);
		returnPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		returnPanel.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_2 = new JLabel("도서 반납");
		panel_1.add(lblNewLabel_2);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		returnPanel.add(panel);
		
		JLabel lblNewLabel_1 = new JLabel("도서 번호");
		panel.add(lblNewLabel_1);
		
		checkoutBookNum = new JTextField();
		panel.add(checkoutBookNum);
		checkoutBookNum.setColumns(30);
		
		JButton btnNewButton_1 = new JButton("반납하기");
		panel.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int cobn = Integer.parseInt(checkoutBookNum.getText().trim());
				if((o.db.checkoutBookMembercheck(m_id, cobn) == false)) {
					o.db.returnBook(m_id, cobn);
					o.db.bookStockIncrease(cobn);
					
					JOptionPane.showMessageDialog(null, "도서 반납이 완료되었습니다.");
					refreshTable();
				}
				else {
					JOptionPane.showMessageDialog(null, "해당 도서의 대여 내역이 존재하지 않습니다. 도서 번호를 다시 확인하세요.");
				}
				checkoutBookNum.setText("");
			}
		});
			
		JPanel labelPanel = new JPanel();
		contentPane.add(labelPanel, BorderLayout.NORTH);
		labelPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("나의 도서 대여 목록");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		labelPanel.add(lblNewLabel);
	}
	
	public void refreshTable() {
	    ResultSet rs = o.db.selectMemberCheckoutStatus(m_id);

	    try {
	        DefaultTableModel tableModel = buildTableModel(rs);
	        table.setModel(tableModel);
	        
			// 테이블 열 모델 가져오기
			TableColumnModel columnModel = table.getColumnModel();
			
			// 세 번째 열 크기 조절
			TableColumn thirdColumn = columnModel.getColumn(2);
			thirdColumn.setPreferredWidth(250); // 두 번째 열의 크기를 200으로 설정
	    } catch (SQLException e) {
	        System.out.println("도서 대여 목록 로딩 실패 > " + e.toString());
	    }
	}
//	
//	public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
//	    if (rs == null) {
//	        return new DefaultTableModel();
//	    }
//
//	    ResultSetMetaData metaData = rs.getMetaData();
//
//	    // 테이블 열 정보 가져오기
//	    int columnCount = metaData.getColumnCount();
//	    String[] columnNames = new String[columnCount];
//	    for (int i = 1; i <= columnCount; i++) {
//	        columnNames[i - 1] = metaData.getColumnLabel(i);
//	    }
//
//	    // 테이블 데이터 가져오기
//	    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
//	    while (rs.next()) {
//	        Object[] rowData = new Object[columnCount];
//	        for (int i = 1; i <= columnCount; i++) {
//	            rowData[i - 1] = rs.getObject(i);
//	        }
//	        tableModel.addRow(rowData);
//	    }
//
//	    return tableModel;
//	}
	
	public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
	    if (rs == null) {
	        return new DefaultTableModel();
	    }

	    ResultSetMetaData metaData = rs.getMetaData();

	    // 테이블 열 정보 가져오기
	    int columnCount = metaData.getColumnCount();
	    String[] columnNames = new String[columnCount];
	    columnNames[0] = "순번"; // 첫 번째 열의 제목을 "순번"으로 설정
	    for (int i = 2; i <= columnCount; i++) { // 첫 번째 열은 이미 설정했으므로 2부터 시작
	        columnNames[i - 1] = metaData.getColumnLabel(i);
	    }

	    // 테이블 데이터 가져오기
	    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
	    int rowNum = 1; // 데이터 행의 순번을 초기화
	    while (rs.next()) {
	        Object[] rowData = new Object[columnCount];
	        rowData[0] = rowNum++; // 데이터 행의 순번으로 값 설정 후 순번 증가
	        for (int i = 2; i <= columnCount; i++) { // 첫 번째 열은 이미 설정했으므로 2부터 시작
	            rowData[i - 1] = rs.getObject(i);
	        }
	        tableModel.addRow(rowData);
	    }

	    return tableModel;
	}

}
