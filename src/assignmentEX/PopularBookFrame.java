package assignmentEX;

import javax.swing.*;
import javax.swing.border.*;

import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.*;

import java.awt.*;
import java.sql.*;

public class PopularBookFrame extends JFrame {

	private JPanel contentPane;

	Operator o = null;

	PopularBookFrame(Operator _o) {
		o = _o;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 989, 608);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		ResultSet rs = o.db.selectCheckout();
		
		JFreeChart chart = ChartFactory.createBarChart("인기 도서 순위", "인기 도서", "대여빈도(권)", createDataset());
		ChartPanel chartpanel = new ChartPanel(chart);
		chartpanel.setMaximumDrawHeight(1080);
		chartpanel.setMaximumDrawWidth(1920);
		FlowLayout flowLayout = (FlowLayout) chartpanel.getLayout();
		flowLayout.setVgap(10);
		flowLayout.setHgap(10);
		contentPane.add(chartpanel,BorderLayout.CENTER);

		chart.getTitle().setFont(new Font("나눔바른고딕", Font.BOLD, 20));
		chart.getLegend().setItemFont(new Font("나눔바른고딕", Font.BOLD, 15));
		
		CategoryPlot p = chart.getCategoryPlot();
		// 차트의 배경색 설정입니다.
		p.setBackgroundPaint(Color.white);
		// 차트의 배경 라인 색상입니다.
		p.setRangeGridlinePaint(Color.gray);
		// X 축의 라벨 설정입니다. (보조 타이틀)
		p.getDomainAxis().setLabelFont(new Font("나눔바른고딕", Font.PLAIN, 15));
		// X 축의 도메인 설정입니다.
		p.getDomainAxis().setTickLabelFont(new Font("나눔바른고딕", Font.PLAIN, 15));
		// Y 축의 라벨 설정입니다. (보조 타이틀)
		p.getRangeAxis().setLabelFont(new Font("나눔바른고딕", Font.PLAIN, 15));
		// Y 축의 도메인 설정입니다.
		p.getRangeAxis().setTickLabelFont(new Font("나눔바른고딕", Font.PLAIN, 15));
		
		// Y 축의 도메인 설정입니다.
		NumberAxis rangeAxis = (NumberAxis) p.getRangeAxis();
		rangeAxis.setTickUnit(new NumberTickUnit(1)); // 눈금 간격을 1로 설정
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits()); // 정수 눈금 사용
		rangeAxis.setLowerBound(0); // Y 축의 최소값 설정
//		rangeAxis.setUpperBound(10); // Y 축의 최대값 설정
		rangeAxis.setAutoRangeIncludesZero(true); // 0을 항상 포함하도록 설정
		rangeAxis.setLabelFont(new Font("나눔바른고딕", Font.PLAIN, 15));
		rangeAxis.setTickLabelFont(new Font("나눔바른고딕", Font.PLAIN, 15));
	}
	
	private CategoryDataset createDataset() {
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		try {
			ResultSet rs = o.db.selectPopularBook();
			while (rs.next()) {
				System.out.println("제목 : " + rs.getString(2));
				System.out.println("대여수량 : " + rs.getInt(3));
				
				dataset.addValue(rs.getInt(3),rs.getString(2), rs.getString(2));
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		
		return dataset;
	}

}

