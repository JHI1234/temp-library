package assignmentEX;

import java.sql.*;

public class Database {
	Connection conn = null;
	Statement stmt = null;
	String url = "jdbc:mysql://localhost:3306/b_project?autoReconnect=true";
	String user = "root";
	String passwd = "1234";
	Database() {
		try {	//데이터베이스 연결은 try-catch문으로 예외를 잡아준다.
			//데이터베이스와 연결한다.
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, passwd);
			stmt = conn.createStatement();
			System.out.println("[Server] MySQL 서버 연동 성공");	//데이터베이스 연결에 성공하면 성공을 콘솔로 알린다.
		} catch(Exception e) {	//데이터베이스 연결에 예외가 발생했을 때 실패를 콘솔로 알린다.
			System.out.println("[Server] MySQL 서버 연동 실패> " + e.toString());
		}
	}
		
	boolean logincheck(String _i, String _p) {
		boolean flag = false;
		
		String id = _i;
		String pw = _p;
		
		try {
			String query = "SELECT m_passwd FROM membertbl WHERE m_id=?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			int count = 0;
			while(rs.next()) {
				if(pw.equals(rs.getString("m_passwd"))) {
					flag = true;
					System.out.println("로그인 성공");
				}
				
				else {
					flag = false;
					System.out.println("로그인 실패");
				}
				count++;
			}
		} catch(Exception e) {
			flag = false;
			System.out.println("로그인 실패 > " + e.toString());
		}
		
		return flag;
	}
	
	boolean joinCheck(String _i, String _p, String _n) {
		boolean flag = false;
		
		String id = _i;
		String name = _n;
		String pw = _p;
			
		try {
			String query = "INSERT INTO membertbl (m_id, m_passwd, m_name) VALUES('" + id + "', '" + pw + "', '" + name + "')";
			stmt.executeUpdate(query);
				
			flag = true;
			System.out.println("회원가입 성공");
		} catch(Exception e) {
			flag = false;
			System.out.println("회원가입 실패 > " + e.toString());
		}
			
		return flag;
	}
	
	// 사용자 - 대여가능한 전체 도서 내역 조회
	ResultSet selectAllBook() {
        try {
            String query = "SELECT b_num as '도서 번호', b_title as '제목', b_author as '저자', b_genre as '장르', b_pc as '출판사', b_stock as '대여가능수량' FROM booktbl"
            			 + " where b_status = '대여가능' ";
            ResultSet rs = stmt.executeQuery(query);
			System.out.println("도서 목록 조회 성공");
            return rs;
        } catch (SQLException e) {
			System.out.println("도서 목록 조회 실패 > " + e.toString());
    		return null;
        }
    }
	
	// 관리자 - 대여가능한 전체 도서 내역 조회
	ResultSet selectAllBookBW() {
        try {
            String query = " SELECT b_num as '도서 번호', b_title as '제목', b_author as '저자', b_genre as '장르', b_pc as '출판사', b_stock as '대여가능수량', b_status as '대여가능여부' FROM booktbl order by b_num desc";  // 테이블 이름 수정 필요
            ResultSet rs = stmt.executeQuery(query);
			System.out.println("도서 목록 조회 성공");
            return rs;
        } catch (SQLException e) {
			System.out.println("도서 목록 조회 실패 > " + e.toString());
    		return null;
        }
    }
	
	// 관리자 - 대여가능한 전체 도서 내역 조회
	ResultSet selectAllBookBW(int booknum) {
        try {
            String query = " SELECT b_num as '도서 번호', b_title as '제목', b_author as '저자', b_genre as '장르', b_pc as '출판사', b_stock as '대여가능수량', b_status as '대여가능여부' FROM booktbl "
            			 + " where b_num like ? "
            			 + " order by b_num desc ";  // 테이블 이름 수정 필요
	        PreparedStatement pstmt = conn.prepareStatement(query);
	        pstmt.setInt(1, booknum);
            ResultSet rs = pstmt.executeQuery();
			System.out.println("[관리자모드]도서번호 검색 - 목록 조회 성공");
            return rs;
        } catch (SQLException e) {
			System.out.println("[관리자모드]도서번호 검색 - 도서 목록 조회 실패 > " + e.toString());
    		return null;
        }
    }
	
	// 관리자 - 대여가능한 전체 도서 내역 조회
	ResultSet selectAllBookBW(String booktitle) {
        try {
            String query = " SELECT b_num as '도서 번호', b_title as '제목', b_author as '저자', b_genre as '장르', b_pc as '출판사', b_stock as '대여가능수량', b_status as '대여가능여부' FROM booktbl "
            			 + " where b_title like ? "
            			 + " order by b_num desc ";  // 테이블 이름 수정 필요
	        PreparedStatement pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, "%" + booktitle + "%");
            ResultSet rs = pstmt.executeQuery();
			System.out.println("[관리자모드]도서제목 검색 - 도서 목록 조회 성공");
            return rs;
        } catch (SQLException e) {
			System.out.println("[관리자모드]도서제목 검색 - 도서 목록 조회 실패 > " + e.toString());
    		return null;
        }
    }
	
	// 사용자 - 특정 도서 제목 검색 - 조회
	ResultSet selectBookTitle(String booktitle) {
	    try {
	        String query = "SELECT b_num as '도서 번호', b_title as '제목', b_author as '저자', b_genre as '장르', b_pc as '출판사', b_stock as '대여가능수량' FROM booktbl WHERE b_title LIKE ?";
	        PreparedStatement pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, "%" + booktitle + "%");
	        ResultSet rs = pstmt.executeQuery();
	        System.out.println("도서 검색 성공");
	        return rs;
	    } catch (SQLException e) {
	        System.out.println("도서 검색 실패 > " + e.toString());
	        return null;
	    }
	}
	
	// 사용자 - 대여 내역 조회
	ResultSet selectMemberCheckoutStatus(String memberid) {
	    try {
	        String query = " SELECT checkouttbl.co_num as '대여번호', checkouttbl.co_bnum as '도서번호', booktbl.b_title as '제목', booktbl.b_author as '저자', booktbl.b_pc as '출판사', checkouttbl.co_status as '반납여부' "
	        			 + " FROM checkouttbl "
	        			 + " left join booktbl on checkouttbl.co_bnum = booktbl.b_num "
	        			 + " WHERE co_mid = ? ";
	        PreparedStatement pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, memberid);
	        ResultSet rs = pstmt.executeQuery();
	        System.out.println("회원 대여 목록 조회 성공");
	        return rs;
	    } catch (SQLException e) {
	        System.out.println("회원 대여 목록 조회 실패 > " + e.toString());
	        return null;
	    }
	}
	
	// 사용자 - 도서 대여
	boolean checkoutBook(String memberid, int booknum) {
	    boolean flag = false;
	    try {
	        String query = "INSERT INTO checkouttbl (co_mid, co_bnum) VALUES (?, ?)";
	        PreparedStatement pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, memberid);
	        pstmt.setInt(2, booknum);
	        pstmt.executeUpdate();
	        
	        System.out.println("도서 대여 성공");
	        flag = true;
	    } catch (SQLException e) {
	        System.out.println("도서 대여 실패 > " + e.toString());
	        flag = false;
	    }
	    return flag;
	}

	// 사용자 - 도서 반납
	boolean returnBook(String memberid, int booknum) {
	    boolean flag = false;
	    try {
	        String query = "update checkouttbl set co_status = '반납완료' where co_mid = ? and co_bnum = ? and co_status = '대여중'" ;
	        PreparedStatement pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, memberid);
	        pstmt.setInt(2, booknum);
	        pstmt.executeUpdate();
	        
	        System.out.println("도서 반납 성공");
			flag = true;
	    } catch (SQLException e) {
	        System.out.println("도서 반납 실패 > " + e.toString());
	        flag = false;
	    }
	    return flag;
	}
	
	// 사용자 - 대여중인 동일한 도서가 있는지 확인
	boolean checkoutBookMembercheck(String memberid, int booknum) {
	    boolean flag = false;
	    
	    try {
	        String checkingStr = "SELECT count(*) FROM checkouttbl WHERE co_mid = ? and co_bnum = ? and co_status = '대여중'";
	        PreparedStatement pstmt = conn.prepareStatement(checkingStr);
	        pstmt.setString(1, memberid);
	        pstmt.setInt(2, booknum);
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            int count = rs.getInt(1);
	            if (count == 0) {
	                flag = true;
	                System.out.println("대여중인 동일한 도서 없음 - 대여 가능");
	            } else {
	                flag = false;
	                System.out.println("대여 중인 동일한 도서가 존재하므로 대여 불가");
	            }
	        }
	    } catch (Exception e) {
	        flag = false;
	        System.out.println("로그인 실패 > " + e.toString());
	    }
	    return flag;
	}
	
	// 도서 수량 확인 - 대여 가능 여부 판단
	boolean bookStockCheck(int booknum) {
	    boolean flag = false;
	    try {
	        String query = "SELECT b_stock FROM booktbl where b_num = ?" ;
	        PreparedStatement pstmt = conn.prepareStatement(query);
	        pstmt.setInt(1, booknum);
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            int count = rs.getInt(1);
	            if (count > 0) {
	                flag = true;
	    	        System.out.println("도서 수량 충분 - 대여 가능");
	            } else {
	                flag = false;
	                System.out.println("도서 수량 부족 - 대여 불가능");
	            }
	        }
	    } catch (SQLException e) {
	        flag = false;
	        System.out.println("도서 수량 확인 불가 > " + e.toString());
	    }
	    return flag;
	}
	
	// 도서 - 수량 증가
	boolean bookStockIncrease(int booknum) {
	    boolean flag = false;
	    try {
	        String query = "update booktbl set b_stock = b_stock + 1  where b_num = ?" ;
	        PreparedStatement pstmt = conn.prepareStatement(query);
	        pstmt.setInt(1, booknum);
	        pstmt.executeUpdate();
	        
	        System.out.println("도서 수량 증가 성공");
			flag = true;
	    } catch (SQLException e) {
	        System.out.println("도서 수량 증가 실패 > " + e.toString());
	        flag = false;
	    }
	    return flag;
	}	
	
	// 도서 - 수량 감소
	boolean bookStockReduce(int booknum) {
	    boolean flag = false;
	    try {
	        String query = "update booktbl set b_stock = b_stock - 1  where b_num = ?" ;
	        PreparedStatement pstmt = conn.prepareStatement(query);
	        pstmt.setInt(1, booknum);
	        pstmt.executeUpdate();
	        
	        System.out.println("도서 수량 감소 성공");
			flag = true;
	    } catch (SQLException e) {
	        System.out.println("도서 수량 감소 실패 > " + e.toString());
	        flag = false;
	    }
	    return flag;
	}
	
	// 관리자 - 도서 추가
	boolean addBook(String title, String author, String genre, String pc, int stock) {
	    boolean flag = false;
	    try {
	        String query = "insert into booktbl (b_title, b_author, b_genre, b_pc, b_stock) values (?, ?, ?, ?, ?)" ;
	        PreparedStatement pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, title);
	        pstmt.setString(2, author);
	        pstmt.setString(3, genre);
	        pstmt.setString(4, pc);
	        pstmt.setInt(5, stock);
	        pstmt.executeUpdate();
	        
	        System.out.println("도서 추가 성공");
			flag = true;
	    } catch (SQLException e) {
	        System.out.println("도서 추가 실패 > " + e.toString());
	        flag = false;
	    }
	    return flag;
	}
	
	// 관리자 - 수정할 도서 정보 조회
	ResultSet selectBookinfo(int booknum) {
	    try {
	        String query = " SELECT b_title, b_author, b_genre, b_pc, b_stock, b_status from booktbl WHERE b_num = ? ";
	        PreparedStatement pstmt = conn.prepareStatement(query);
	        pstmt.setInt(1, booknum);
	        ResultSet rs = pstmt.executeQuery();
	        System.out.println("특정 도서 정보 조회 성공");
	        return rs;
	    } catch (SQLException e) {
	        System.out.println("특정 도서 정보 조회 실패 > " + e.toString());
	        return null;
	    }
	}
	
	// 관리자 - 도서 정보 수정
	boolean updateBook(String title, String author, String genre, String pc, int stock, String status, int num) {
	    boolean flag = false;
	    try {
	        String query = "update booktbl set b_title=?, b_author=?, b_genre=?, b_pc=?, b_stock=?, b_status=? where b_num = ?" ;
	        PreparedStatement pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, title);
	        pstmt.setString(2, author);
	        pstmt.setString(3, genre);
	        pstmt.setString(4, pc);
	        pstmt.setInt(5, stock);
	        pstmt.setString(6, status);
	        pstmt.setInt(7, num);
	        pstmt.executeUpdate();
	        
	        System.out.println("도서 정보 수정 성공");
			flag = true;
	    } catch (SQLException e) {
	        System.out.println("도서 정보 수정 실패 > " + e.toString());
	        flag = false;
	    }
	    return flag;
	}
	
	// 관리자 - 전체 대여 내역 조회
	ResultSet selectCheckout() {
	    try {
	        String query = " SELECT checkouttbl.co_num as '대여번호', checkouttbl.co_mid as '대여자', checkouttbl.co_bnum as '도서번호', booktbl.b_title as '제목', booktbl.b_author as '저자', booktbl.b_pc as '출판사', checkouttbl.co_status as '반납여부' "
	        			 + " FROM checkouttbl "
	        			 + " left join booktbl on checkouttbl.co_bnum = booktbl.b_num "
	        			 + " order by co_num desc ";
	        PreparedStatement pstmt = conn.prepareStatement(query);
	        ResultSet rs = pstmt.executeQuery();
	        System.out.println("회원 대여 목록 조회 성공");
	        return rs;
	    } catch (SQLException e) {
	        System.out.println("회원 대여 목록 조회 실패 > " + e.toString());
	        return null;
	    }
	}
	
	// 관리자 - 특정 사용자 + 상태 대여 내역 조회
	ResultSet selectCheckout(String memberid, String status) {
	    try {
	        String query = " SELECT checkouttbl.co_num as '대여번호', checkouttbl.co_mid as '대여자', checkouttbl.co_bnum as '도서번호', booktbl.b_title as '제목', booktbl.b_author as '저자', booktbl.b_pc as '출판사', checkouttbl.co_status as '반납여부' "
	        			 + " FROM checkouttbl "
	        			 + " left join booktbl on checkouttbl.co_bnum = booktbl.b_num "
	        			 + " WHERE co_mid like ? and co_status like ? "
	        			 + " order by co_num desc ";
	        PreparedStatement pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, '%'+memberid+'%');
	        pstmt.setString(2, '%'+status+'%');
	        ResultSet rs = pstmt.executeQuery();
	        System.out.println("회원 대여 목록 조회 성공");
	        return rs;
	    } catch (SQLException e) {
	        System.out.println("회원 대여 목록 조회 실패 > " + e.toString());
	        return null;
	    }
	}
	
	// 관리자 - 인기 도서 순위
	ResultSet selectPopularBook() {
	    try {
	        String query = " SELECT checkouttbl.co_bnum, booktbl.b_title AS '제목', COUNT(*) "
	        			+ " FROM checkouttbl "
	        			+ " LEFT JOIN booktbl ON checkouttbl.co_bnum = booktbl.b_num "
	        			+ " GROUP BY checkouttbl.co_bnum, booktbl.b_title "
	        			+ " ORDER BY COUNT(*) DESC "
	        			+ " LIMIT 5; ";
	        PreparedStatement pstmt = conn.prepareStatement(query);
	        ResultSet rs = pstmt.executeQuery();
	        System.out.println("인기 도서 순위 조회 성공");
	        return rs;
	    } catch (SQLException e) {
	        System.out.println("인기 도서 순위 조회 실패 > " + e.toString());
	        return null;
	    }
	}
}

