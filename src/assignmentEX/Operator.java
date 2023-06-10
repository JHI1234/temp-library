package assignmentEX;

import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.plaf.*;

public class Operator extends JFrame{
	Database db = null;
	LoginFrame lf = null;
	JoinFrame jf = null;
	AdminFrame af = null;
	UserFrame uf = null;
	MainFrame mf = null;
	AddBookFrame abf = null;
	UpdateBookFrame ubf = null;
	CheckoutFrame cf = null;
	PopularBookFrame pf = null;
	
	
	 public static void setUIFont(FontUIResource f) {
	        Enumeration keys = UIManager.getDefaults().keys();
	        while (keys.hasMoreElements()) {
	            Object key = keys.nextElement();
	            Object value = UIManager.get(key);
	            if (value instanceof FontUIResource) {
	                FontUIResource orig = (FontUIResource) value;
	                Font font = new Font(f.getFontName(), orig.getStyle(), f.getSize());
	                UIManager.put(key, new FontUIResource(font));
	            }
	        }
	 }
	
	public static void main(String[] args) {
		setUIFont(new FontUIResource(new Font("나눔바른고딕", 0, 13)));
		
		Operator opt = new Operator();
		String memberID = null;
		
		opt.db = new Database();
		opt.lf = new LoginFrame(opt);
		opt.jf = new JoinFrame(opt);
		opt.af = new AdminFrame(opt);
		opt.uf = new UserFrame(opt, memberID);
		opt.mf = new MainFrame(opt, memberID);
		opt.abf = new AddBookFrame(opt);
		opt.ubf = new UpdateBookFrame(opt);
		opt.cf = new CheckoutFrame(opt);
		opt.pf = new PopularBookFrame(opt);
	}
}