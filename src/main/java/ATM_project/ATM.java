package ATM_project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ATM {
    public static User validateUser(String accountNumber, int pin) throws SQLException {
        Connection conn = JDBCUtility.getConnection();
        String query = "SELECT * FROM Users WHERE account_number = ? AND pin = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, accountNumber);
        pstmt.setInt(2, pin);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return new User(rs.getInt("user_id"), rs.getString("account_number"), rs.getString("name"), rs.getInt("pin"), rs.getDouble("balance"));
        }
        return null;
    }

    public static double checkBalance(int userID) throws SQLException {
        Connection conn = JDBCUtility.getConnection();
        String query = "SELECT balance FROM Users WHERE user_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, userID);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getDouble("balance");
        }
        return 0;
    }

    public static boolean withdraw(int userID, double amount) throws SQLException {
        Connection conn = JDBCUtility.getConnection();
        double currentBalance = checkBalance(userID);
        if (currentBalance >= amount) {
            String updateQuery = "UPDATE Users SET balance = balance - ? WHERE user_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(updateQuery);
            pstmt.setDouble(1, amount);
            pstmt.setInt(2, userID);
            pstmt.executeUpdate();
            addTransaction(userID, amount, "withdraw");
            return true;
        }
        return false;
    }

    public static boolean deposit(int userID, double amount) throws SQLException {
        Connection conn = JDBCUtility.getConnection();
        String updateQuery = "UPDATE Users SET balance = balance + ? WHERE user_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(updateQuery);
        pstmt.setDouble(1, amount);
        pstmt.setInt(2, userID);
        pstmt.executeUpdate();
        addTransaction(userID, amount, "deposit");
        return true;
    }

    private static void addTransaction(int userID, double amount, String type) throws SQLException {
        Connection conn = JDBCUtility.getConnection();
        String insertQuery = "INSERT INTO Transactions (user_id, amount, type) VALUES (?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(insertQuery);
        pstmt.setInt(1, userID);
        pstmt.setDouble(2, amount);
        pstmt.setString(3, type);
        pstmt.executeUpdate();
    }
}

