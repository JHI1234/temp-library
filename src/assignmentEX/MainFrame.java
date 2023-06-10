package assignmentEX;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.sql.*;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTextField searchTitle;

	Operator o = null;
	String m_id; 
	private JTextField checkoutBookNum;
	private JTable table;

	MainFrame(Operator _o, String _id) {

		o = _o;
		m_id = _id;
		
		setTitle("도서 대여 프로그램");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 896, 641);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("프로그램 메뉴");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("메인페이지");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				o.mf = new MainFrame(o, m_id);
				change(o.mf);
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("나의 대여 목록");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				o.uf = new UserFrame(o, m_id);
				change(o.uf);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel tablePanel = new JPanel();
		tablePanel.setBorder(new EmptyBorder(5, 0, 5, 0));
		contentPane.add(tablePanel, BorderLayout.CENTER);
		tablePanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		tablePanel.add(scrollPane);
		
		ResultSet rs = o.db.selectAllBook();
		
		try {			
			table = new JTable(buildTableModel(rs));
			table.setFillsViewportHeight(true);
			scrollPane.setViewportView(table);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("전체 도서 목록 로딩 실패 > " + e.toString());
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
		
		JPanel panel_3 = new JPanel();
		searchPanel.add(panel_3, BorderLayout.NORTH);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_3 = new JLabel("도서 검색");
		panel_3.add(lblNewLabel_3);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		searchPanel.add(panel_2);
		
		JLabel lblNewLabel = new JLabel("도서 제목");
		panel_2.add(lblNewLabel);
		
		searchTitle = new JTextField();
		panel_2.add(searchTitle);
		searchTitle.setColumns(30);
	
		JButton btnNewButton = new JButton("검색");
		panel_2.add(btnNewButton);
		
		JButton btnNewButton_2 = new JButton("검색 결과 리셋");
		btnNewButton_2.setMinimumSize(new Dimension(130, 23));
		btnNewButton_2.setMaximumSize(new Dimension(130, 23));
		panel_2.add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ResultSet newResultSet = o.db.selectAllBook();
				refreshTable(newResultSet);
			}
		});
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String searchword = searchTitle.getText().trim();
				ResultSet newResultSet = o.db.selectBookTitle(searchword);
				refreshTable(newResultSet);
				
			}
		});
		
		JPanel checkoutPanel = new JPanel();
		contentPane.add(checkoutPanel, BorderLayout.SOUTH);
		checkoutPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		checkoutPanel.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_2 = new JLabel("도서 대여");
		panel_1.add(lblNewLabel_2);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		checkoutPanel.add(panel);
		
		JLabel lblNewLabel_1 = new JLabel("도서 번호");
		panel.add(lblNewLabel_1);
		
		checkoutBookNum = new JTextField();
		panel.add(checkoutBookNum);
		checkoutBookNum.setColumns(30);
		
		JButton btnNewButton_1 = new JButton("대여하기");
		panel.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isValidInput(checkoutBookNum)) {
					int cobn = Integer.parseInt(checkoutBookNum.getText().trim());
					if(o.db.bookStockCheck(cobn)) {
						if(o.db.checkoutBookMembercheck(m_id, cobn)) {
							o.db.checkoutBook(m_id, cobn);
							o.db.bookStockReduce(cobn);
							
							JOptionPane.showMessageDialog(null, "대여가 완료되었습니다.");
						}
						else {
							JOptionPane.showMessageDialog(null, "대여중인 동일한 도서가 존재하므로 대여할 수 없습니다.");
						}
					}else {
						JOptionPane.showMessageDialog(null, "해당 번호의 도서는 대여가 불가능합니다. 번호를 다시 확인해주세요.");
					}
					checkoutBookNum.setText("");
					refreshTable(o.db.selectAllBook());
				}
				else {
					JOptionPane.showMessageDialog(null, "값을 입력해주세요!", "경고", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
	    checkoutBookNum.addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyTyped(KeyEvent e) {
	            char inputChar = e.getKeyChar();
	            if (!Character.isDigit(inputChar)) {
	                e.consume(); // 입력된 문자를 무시하도록 함
	            }
	        }
	    });
	    
	}

    private static boolean isValidInput(JTextField input) {
        if (input.getText() == null || input.getText().trim().isEmpty()) {
            input.requestFocus();
            return false;
        }
        return true;
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
