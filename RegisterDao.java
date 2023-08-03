package com.java.webapplication;
import java.sql.*;

public class RegisterDao {
	private String dburl = "jdbc:mysql://localhost:3306/project";
	private String dbuname = "root";
	private String dbpassword = "admin";
	private String dbdriver = "com.mysql.cj.jdbc.Driver";
	private int rows_affected;

	public void loadDriver(String dbDriver) {
		try {
			Class.forName(this.dbdriver);
		} catch (ClassNotFoundException e) {
			System.out.println("MySQL JDBC driver not found.");
			e.printStackTrace();
		}
	}

	public Connection getConnection()
						   {
								Connection con = null;
								try {
									con = DriverManager.getConnection(dburl, dbuname, dbpassword);
								} catch (SQLException e) {
									System.out.println("Error while connecting to the database or executing the query.");
									e.printStackTrace();
								}
							    return con;
						   }
	
	public boolean isEmailRegistered(String email) {
        loadDriver(dbdriver);
        Connection con = getConnection();
         try{
        	PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM registered_members WHERE email = ?");
            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
         finally {
 			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 		}
        // If an exception occurred or no result found, assume email is not registered
        return false;
    }
	
	

	public boolean insert(Members member) throws Exception {
		loadDriver(dbdriver);
		Connection con = getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("insert into project.registered_members(username,password,rpassword,email) values(?,?,?,?)");
			ps.setString(1, member.getUname());
			ps.setString(2, member.getPassword());
			ps.setString(3, member.getrPassword());
			ps.setString(4, member.getEmail());
			this.rows_affected=ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			con.close();
		}
	    if(rows_affected>0)
	    {
	    	return true;
	    }
	    else
	    {
	    	return false;
	    }
	}
}
