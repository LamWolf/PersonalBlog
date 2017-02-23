package javadao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javamodel.Home;
import javamodel.PageBean;
import javautil.DateUtil;
import javautil.PropertiesUtil;

public class HomeDao {

	public List<Home> homeList(Connection con,String sql)throws Exception{
		List<Home> homeList=new ArrayList<Home>();
		PreparedStatement pstmt=con.prepareStatement(sql);
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()){
			Home home=new Home();
			home.setHomeId(rs.getInt("homeId"));
			home.setTitle(rs.getString("title"));
			home.setContent(rs.getString("content"));
			home.setPublishDate(DateUtil.formatString(rs.getString("publishDate"), "yyyy-MM-dd HH:mm:ss"));
			home.setAuthor(rs.getString("author"));
			home.setTypeId(rs.getInt("typeId"));
			home.setClick(rs.getInt("click"));
			home.setIsHead(rs.getInt("isHead"));
			home.setImageName(PropertiesUtil.getValue("userImage")+rs.getString("imageName"));
			home.setIsHot(rs.getInt("isHot"));
			homeList.add(home);
		}
		return homeList;
	}
	
	public List<Home> homeList(Connection con,Home s_home,PageBean pageBean)throws Exception{
		List<Home> homeList=new ArrayList<Home>();
		StringBuffer sb=new StringBuffer("select * from t_home t1,t_homeType t2 where t1.typeId=t2.homeTypeId ");
		if(s_home.getTypeId()!=-1){
			sb.append(" and t1.typeId="+s_home.getTypeId());
		}
		sb.append(" order by t1.publishDate desc ");
		if(pageBean!=null){
			sb.append(" limit "+pageBean.getStart()+","+pageBean.getPageSize());
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString());
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()){
			Home home=new Home();
			home.setHomeId(rs.getInt("homeId"));
			home.setTitle(rs.getString("title"));
			home.setContent(rs.getString("content"));
			home.setPublishDate(DateUtil.formatString(rs.getString("publishDate"), "yyyy-MM-dd HH:mm:ss"));
			home.setAuthor(rs.getString("author"));
			home.setTypeId(rs.getInt("typeId"));
			home.setClick(rs.getInt("click"));
			home.setIsHead(rs.getInt("isHead"));
			home.setImageName(PropertiesUtil.getValue("userImage")+rs.getString("imageName"));
			home.setIsHot(rs.getInt("isHot"));
			homeList.add(home);
		}
		return homeList;
	}
	
	public int homeCount(Connection con,Home s_home)throws Exception{
		StringBuffer sb=new StringBuffer("select count(*) as total from t_home");
		if(s_home.getTypeId()!=-1){
			sb.append(" and typeId="+s_home.getTypeId());
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString().replaceFirst("and", "where"));
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return rs.getInt("total");
		}else{
			return 0;
		}
	}
	
	public Home getHomeById(Connection con,String homeId)throws Exception{
		String sql="select * from t_home t1,t_homeType t2 where t1.typeId=t2.homeTypeId and t1.homeId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, homeId);
		ResultSet rs=pstmt.executeQuery();
		Home home=new Home();
		if(rs.next()){
			home.setHomeId(rs.getInt("homeId"));
			home.setTitle(rs.getString("title"));
			home.setContent(rs.getString("content"));
			home.setPublishDate(DateUtil.formatString(rs.getString("publishDate"), "yyyy-MM-dd HH:mm:ss"));
			home.setAuthor(rs.getString("author"));
			home.setTypeName(rs.getString("typeName"));
			home.setTypeId(rs.getInt("typeId"));
			home.setClick(rs.getInt("click"));
			home.setIsHead(rs.getInt("isHead"));
			home.setImageName(PropertiesUtil.getValue("userImage")+rs.getString("imageName"));
			home.setIsHot(rs.getInt("isHot"));
		}
		return home;
	}
	
	public int homeClick(Connection con,String homeId)throws Exception{
		String sql="update t_home set click=click+1 where homeId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, homeId);
		return pstmt.executeUpdate();
	}
	
	public List<Home> getUpAndDownPageId(Connection con,String homeId)throws Exception{
		List<Home> upAndDownPage=new ArrayList<Home>();
		String sql="SELECT * FROM t_home WHERE homeId<? ORDER BY homeId DESC LIMIT 1;";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, homeId);
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			upAndDownPage.add(new Home(rs.getInt("homeId"),rs.getString("title")));
		}else{
			upAndDownPage.add(new Home(-1,""));
		}
		
		sql="SELECT * FROM t_home WHERE homeId>? ORDER BY homeId ASC LIMIT 1;";
		pstmt=con.prepareStatement(sql);
		pstmt.setString(1, homeId);
		rs=pstmt.executeQuery();
		if(rs.next()){
			upAndDownPage.add(new Home(rs.getInt("homeId"),rs.getString("title")));
		}else{
			upAndDownPage.add(new Home(-1,""));
		}
		return upAndDownPage;
	}
}