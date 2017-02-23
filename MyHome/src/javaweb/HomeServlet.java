package javaweb;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javadao.CommentDao;
import javadao.HomeDao;
import javadao.HomeTypeDao;
import javamodel.Comment;
import javamodel.Home;
import javamodel.PageBean;
import javautil.DbUtil;
import javautil.NavUtil;
import javautil.PageUtil;
import javautil.PropertiesUtil;
import javautil.StringUtil;

public class HomeServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	DbUtil dbUtil=new DbUtil();
	HomeDao homeDao=new HomeDao();
	HomeTypeDao homeTypeDao=new HomeTypeDao();
	CommentDao commentDao=new CommentDao();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action=request.getParameter("action");
		if("list".equals(action)){
			this.homeList(request, response);
		}else if("show".equals(action)){
			this.homeShow(request, response);
		}
		
	}

	private void homeList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String typeId=request.getParameter("typeId");
		String page=request.getParameter("page");
		if(StringUtil.isEmpty(page)){
			page="1";
		}
		Connection con=null;
		Home s_home=new Home();
		if(StringUtil.isNotEmpty(typeId)){
			s_home.setTypeId(Integer.parseInt(typeId));
		}
		try{
			con=dbUtil.getCon();
			int total=homeDao.homeCount(con, s_home);
			PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(PropertiesUtil.getValue("pageSize")));
			List<Home> hometHomeListWithType=homeDao.homeList(con, s_home, pageBean);
			request.setAttribute("hometHomeListWithType", hometHomeListWithType);
			request.setAttribute("navCode", NavUtil.genHomeListNavigation(homeTypeDao.getHomeTypeById(con, typeId).getTypeName(), typeId));
			request.setAttribute("pageCode", PageUtil.getUpAndDownPagation(total, Integer.parseInt(page), Integer.parseInt(PropertiesUtil.getValue("pageSize")), typeId));
			request.setAttribute("mainPage", "home/homeList.jsp");
			request.getRequestDispatcher("foreground/homeTemp.jsp").forward(request, response);
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
	
	private void homeShow(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String homeId=request.getParameter("homeId");
		Connection con=null;
		try{
			con=dbUtil.getCon();
			homeDao.homeClick(con, homeId);
			Home home=homeDao.getHomeById(con, homeId);
			Comment s_comment=new Comment();
			s_comment.setHomeId(Integer.parseInt(homeId));
			List<Comment> commentList=commentDao.commentList(con, s_comment);
			request.setAttribute("commentList", commentList);
			request.setAttribute("home", home);
			request.setAttribute("pageCode", this.genUpAndDownPageCode(homeDao.getUpAndDownPageId(con, homeId)));
			request.setAttribute("navCode", NavUtil.genHomeNavigation(home.getTypeName(), home.getTypeId()+"",home.getTitle()));
			request.setAttribute("mainPage", "home/homeShow.jsp");
			request.getRequestDispatcher("foreground/homeTemp.jsp").forward(request, response);
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
	
	private String genUpAndDownPageCode(List<Home> upAndDownPage){
		Home upHome=upAndDownPage.get(0);
		Home downHome=upAndDownPage.get(1);
		StringBuffer pageCode=new StringBuffer();
		if(upHome.getHomeId()==-1){
			pageCode.append("<p>上一篇：没有了</p>");
		}else{
			pageCode.append("<p>上一篇：<a href='home?action=show&homeId="+upHome.getHomeId()+"'>"+upHome.getTitle()+"</a></p>");
		}
		if(downHome.getHomeId()==-1){
			pageCode.append("<p>下一篇：没有了</p>");
		}else{
			pageCode.append("<p>下一篇：<a href='home?action=show&homeId="+downHome.getHomeId()+"'>"+downHome.getTitle()+"</a></p>");
		}
		return pageCode.toString();
	}
}
