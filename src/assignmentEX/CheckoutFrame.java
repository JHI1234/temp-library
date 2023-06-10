package assignmentEX;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class CheckoutFrame extends JFrame {

	private JPanel contentPane;

	Operator o = null;
	private JTextField txt_memberid;
	private JTable table;
	
	CheckoutFrame(Operator _o) {
		setTitle("도서 대여 관리 프로그램 - 관리자 모드");
		o = _o;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 989, 608);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel tablePanel = new JPanel();
		contentPane.add(tablePanel, BorderLayout.CENTER);
		tablePanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		tablePanel.add(scrollPane);
		
		ResultSet rs = o.db.selectCheckout();
		
		try {
			table = new JTable(buildTableModel(rs));
			table.setFillsViewportHeight(true);
			scrollPane.setViewportView(table);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("도서 목록 로딩 실패 > " + e.toString());
		}
		
		// 테이블 열 모델 가져오기
		TableColumnModel columnModel = table.getColumnModel();

		//열 크기 조절
		TableColumn fourthColumn = columnModel.getColumn(3);
		fourthColumn.setPreferredWidth(200);
		
		//열 크기 조절
		TableColumn fifthColumn = columnModel.getColumn(4);
		fifthColumn.setPreferredWidth(200);

		JPanel searchPanel = new JPanel();
		contentPane.add(searchPanel, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		searchPanel.add(panel);
		
		JLabel lblNewLabel_1 = new JLabel("대여 상태");
		panel.add(lblNewLabel_1);
		
		JComboBox<String> cmb_status = new JComboBox<String>();
		panel.add(cmb_status);
		cmb_status.setModel(new DefaultComboBoxModel<String>(new String[] {"선택", "대여중", "반납완료"}));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(0, 10, 0, 0));
		searchPanel.add(panel_1);
		
		JLabel lblNewLabel = new JLabel("회원 아이디");
		panel_1.add(lblNewLabel);
		
		txt_memberid = new JTextField();
		panel_1.add(txt_memberid);
		txt_memberid.setColumns(30);
		
		JButton btnNewButton = new JButton("조회");
		panel_1.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        String status = (String) cmb_status.getSelectedItem();
		        if(status.equals("선택")) {
		        	status = "";
		        }
				String mid = txt_memberid.getText().trim();
				ResultSet result = o.db.selectCheckout(mid, status);
				refreshTable(result);
			}
		});
		
		
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
			TableColumn fourthColumn = columnModel.getColumn(3);
			fourthColumn.setPreferredWidth(200);
			
			//열 크기 조절
			TableColumn fifthColumn = columnModel.getColumn(4);
			fifthColumn.setPreferredWidth(200);
			
	    } catch (SQLException e) {
	        System.out.println("도서 목록 로딩 실패 > " + e.toString());
	    }
	}

}
