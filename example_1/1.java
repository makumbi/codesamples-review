
// Implements Valation Controls
String customerName = inputValidation(request.getParameter("customerName"));

try {
    // Implements Prepared Statements
    PreparedStatement statement = connection.prepareStatement("SELECT account_balance FROM user_data WHERE user_name = ?");  
    statement.setString(1, customerName);  
    ResultSet results = statement.executeUpdate(); 
} catch (SQLExecption ex) {
        // Exception handling stuff
} finally {
    if (rs != null) {
        try {
            results.close();
        } catch (SQLException e) { 
            /* ignored */
        }
    }
    if (ps != null) {
        try {
            statement.close();
        } catch (SQLException e) { 
            /* ignored */
        }
    }
    if (connection != null) {
        try {
            connection.close();
        } catch (SQLException e) { 
            /* ignored */
        }
    }
}

