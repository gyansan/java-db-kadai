package kadai_007;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Posts_Chapter07 {

	public static void main(String[] args) {
		
		Connection con = null;
		PreparedStatement statement = null;
		
		String[][] userList = {
				{ "1003", "2023-02-08", "昨日の夜は徹夜でした・・", "13"},
				{ "1002", "2023-02-08", "お疲れ様です！", "12"},
				{ "1003", "2023-02-09", "今日も頑張ります！", "13"},
				{ "1001", "2023-02-09", "無理は禁物ですよ！", "17"},
				{ "1002", "2023-02-10", "明日から連休ですね！", "20"}
				
		};
		
		try {
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/challenge_java",
				"root",
				"Yuki8203#"
			);
			
			System.out.println("データベース接続成功:" + con );
			System.out.println("レコード追加を実行します");
			//レコード追加
			String sql = "INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES(?, ?, ?, ?);";
			statement = con.prepareStatement(sql);
			
			int rowCnt = 0;
			for( int i = 0; i < userList.length; i++) {
				statement.setString(1, userList[i][0]);
				statement.setString(2, userList[i][1]);
				statement.setString(3, userList[i][2]);
				statement.setString(4, userList[i][3]);
		
				rowCnt += statement.executeUpdate();
			}
			System.out.println(rowCnt + "件のレコードが追加されました");
			
			//レコード検索
			sql = "SELECT * FROM posts WHERE user_id= 1002;";
			
			ResultSet result = statement.executeQuery(sql);
			
			while(result.next()) {
				
				Date postedAt = result.getDate("posted_at");
				String contents = result.getString("post_content");
				int like = result.getInt("likes");
				System.out.println(result.getRow() + "件目：投稿日時=" + postedAt
											+ "／投稿内容=" + contents + "／いいね数=" + like );
				
			}
			
		} catch(SQLException e) {
			System.out.println("エラー発生：" + e.getMessage());
		} finally {
			if( statement !=null ) {
				try { statement.close(); } catch(SQLException ignore) {}
				}
			if( con != null ) {
				try { con.close(); } catch(SQLException ignore) {}
			}
		}

	}

}
