package javaweb;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javadao.CommentDao;
import javamodel.Comment;
import javamodel.Home;
import javamodel.PageBean;
import javautil.DbUtil;
import javautil.NavUtil;
import javautil.PageUtil;
import javautil.PropertiesUtil;
import javautil.StringUtil;

public class CommentServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	DbUtil dbUtil=new DbUtil();
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
		if("save".equals(action)){
			commentSave(request,response);
		}
		
	}
	
	private void commentSave(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String homeId=request.getParameter("homeId");
		String content=request.getParameter("content");
		String userIP=request.getRemoteAddr();
		Comment comment=new Comment(Integer.parseInt(homeId), content, userIP);
		Connection con=null;
		try{
			con=dbUtil.getCon();
			commentDao.commentAdd(con, comment);
			request.getRequestDispatcher("home?action=show&homeId="+homeId).forward(request, response);
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