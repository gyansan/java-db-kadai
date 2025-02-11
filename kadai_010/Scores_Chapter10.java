package kadai_010;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Scores_Chapter10 {

	public static void main(String[] args) {
		
		Connection con = null;
		PreparedStatement statement = null;
		
		try {
			con = getConnection();
			
			System.out.println("データベース接続成功:" + con );
			System.out.println("レコード更新を実行します");
			//レコード更新
			String sql = "UPDATE scores SET score_math = '95' , score_english = '80' WHERE id = 5;";
			statement = con.prepareStatement(sql);
			
			int rowCnt = statement.executeUpdate(sql);
			System.out.println( rowCnt + "件のレコードが更新されました");
			
			//並べ替え
			sql = "SELECT * FROM scores ORDER BY score_math DESC,score_english DESC ";
			ResultSet result = statement.executeQuery(sql);
			System.out.println("数学・英語の点数が高い順に並べ替えました");
			while(result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                int scoreMath = result.getInt("score_math");
                int scoreEnglish = result.getInt("score_english");
                System.out.println(result.getRow() + "件目：id=" + id
                                   + "／name=" + name + "／数学=" + scoreMath + "／英語=" + scoreEnglish);
            }
			
			
		} catch(SQLException e) {
			System.out.println("エラー発生：" + e.getMessage());
		} finally {
			closeConnection(statement, con);
		}
		
	}
	
	//////////////////////////////////////////////////////////////////////////////////
	
	private static Connection getConnection() throws SQLException {
		
		return DriverManager.getConnection(
				"jdbc:mysql://localhost/challenge_java",
	            "root",
	            "Yuki8203#"
				);
	}
	
	private static void closeConnection(PreparedStatement statement, Connection con) {
		
		if (statement != null) {
            try { statement.close(); } catch (SQLException ignore) {}
        }
        if (con != null) {
            try { con.close(); } catch (SQLException ignore) {}
        }
		
	}
	
	

}
