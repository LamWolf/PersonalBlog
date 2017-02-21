package javaweb;

import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import javadao.HomeDao;
import javadao.HomeTypeDao;
import javamodel.Home;
import javamodel.HomeType;
import javautil.DbUtil;

public class InitServlet extends HttpServlet {

private static final long serialVersionUID = 1L;
	
	DbUtil dbUtil=new DbUtil();
	HomeDao homeDao=new HomeDao();
	HomeTypeDao homeTypeDao=new HomeTypeDao();

	@Override
	public void init(ServletConfig config) throws ServletException {
		ServletContext application=config.getServletContext();
		this.refreshSystem(application);
	}
	
	private void refreshSystem(ServletContext application){
		Connection con=null;
		try{
			con=dbUtil.getCon();
			
			List<HomeType> homeTypeList=homeTypeDao.homeTypeList(con);
			application.setAttribute("homeTypeList", homeTypeList);
			
			String sql="select * from t_home order by publishDate desc limit 0,8 ";
			List<Home> hometHomeList=homeDao.homeList(con, sql);
			application.setAttribute("hometHomeList", hometHomeList);
			
			sql="select * from t_home order by click desc limit 0,8";
			List<Home> hotHomeList=homeDao.homeList(con, sql);
			application.setAttribute("hotHomeList", hotHomeList);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
