package javaweb;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javadao.HomeDao;
import javadao.HomeTypeDao;
import javadao.LinkDao;
import javamodel.Home;
import javamodel.HomeType;
import javamodel.Link;
import javautil.DbUtil;
import javautil.StringUtil;

public class IndexServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	/**
	 * ʵ����
	 */
	DbUtil dbUtil=new DbUtil();
	HomeDao homeDao=new HomeDao();
	HomeTypeDao homeTypeDao=new HomeTypeDao();
	LinkDao linkDao=new LinkDao();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		Connection con=null;
		try {
			con=dbUtil.getCon();
			
			/*����������飩�б�*/
			List<HomeType> homeTypeList=homeTypeDao.homeTypeList(con);
			
			/*ͼƬ��ת�б�*/
			String sql="select * from t_home where isImage=1 order by publishDate desc limit 0,5";
			List<Home> imageHomeList=homeDao.homeList(con, sql);
			request.setAttribute("imageHomeList", imageHomeList);
			
			/*�ö����⣨�鿴ȫ�ģ�*/
			sql="select * from t_home where isHead=1 order by publishDate desc limit 0,1 ";
			List<Home> headhomeList=homeDao.homeList(con, sql);
			Home headHome=headhomeList.get(0);
			headHome.setContent(StringUtil.Html2Text(headHome.getContent()));
			request.setAttribute("headHome", headHome);
			
			/*��������б�*/
			/*sql="select * from t_home order by publishDate desc limit 0,8 ";
			List<Home> hometHomeList=homeDao.homeList(con, sql);
			request.setAttribute("hometHomeList", hometHomeList);*/
			
			/*���������б�*/
			sql="select * from t_home where isHot=1 order by publishDate desc limit 0,8 ";
			List<Home> hotSpotHomeList=homeDao.homeList(con, sql);
			request.setAttribute("hotSpotHomeList", hotSpotHomeList);
			
			/*��Ҫ���ݰ���б�*/
			List allIndexHomeList=new ArrayList();
			if(homeTypeList!=null && homeTypeList.size()!=0){
				for(int i=0;i<homeTypeList.size();i++){
					HomeType homeType=homeTypeList.get(i);
					sql="select * from t_home,t_homeType where typeId=homeTypeId and typeId="+homeType.getHomeTypeId()+" order by publishDate desc limit 0,8";
					List<Home> oneSubList=homeDao.homeList(con, sql);
					allIndexHomeList.add(oneSubList);
				}
			}
			request.setAttribute("allIndexHomeList", allIndexHomeList);
			
			/*���������б�*/
			sql="select * from t_link order by orderNum ";
			List<Link> linkList=linkDao.linkList(con, sql);
			request.setAttribute("linkList", linkList);
			
			request.getRequestDispatcher("index.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}
}
