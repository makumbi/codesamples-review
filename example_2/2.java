Connection con = DBConnection.getConnection();
PreparedStatement stmt = null;
ResultSet rs = null;

// Implements Valation Controls
String id = validationControls(id);
String pwd = validationControls(pwd);

String name = "";
String country = "";
try{

	// Implements Prepared Statements
    stmt = con.prepareStatement("select name, country, password from Users where email = ? and password= ? ");  
	stmt.setInt(1, id); 
	stmt.setString(2, pwd);   
	rs = stmt.executeUpdate(); 
	
	while (rs.next()) {
		// Implements Valation Controls
		name = validationControls(rs.getString("name"));
		country = validationControls(rs.getString("country"));

		System.out.println("Name="+name+",country="+country);
	}

} catch (SQLExecption ex) {
        // Exception handling stuff

}finally{
		if(rs != null) rs.close();
		if(stmt != null) stmt.close();
		if(con != null) con.close();
}
