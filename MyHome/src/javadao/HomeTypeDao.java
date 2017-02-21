package javadao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javamodel.HomeType;

public class HomeTypeDao {

	public List<HomeType> homeTypeList(Connection con)throws Exception{
		List<HomeType> homeTypeList=new ArrayList<HomeType>();
		String sql="select * from t_homeType";
		PreparedStatement pstmt=con.prepareStatement(sql);
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()){
			HomeType homeType=new HomeType();
			homeType.setHomeTypeId(rs.getInt("homeTypeId"));
			homeType.setTypeName(rs.getString("typeName"));
			homeTypeList.add(homeType);
		}
		return homeTypeList;
	}
	
	public HomeType getHomeTypeById(Connection con,String homeTypeId)throws Exception{
		HomeType homeType=new HomeType();
		String sql="select * from t_homeType where homeTypeId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, homeTypeId);
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			homeType.setHomeTypeId(rs.getInt("homeTypeId"));
			homeType.setTypeName(rs.getString("typeName"));
		}
		return homeType;
	}
}
