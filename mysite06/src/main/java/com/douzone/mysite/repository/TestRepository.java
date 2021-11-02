package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douzone.mysite.util.ConnectionProvider;

public class TestRepository {
	private final ConnectionProvider connectionProvider;

	public TestRepository(ConnectionProvider connectionProvider) {
		super();
		this.connectionProvider = connectionProvider;
	}

	public void test() {

		String sql = "insert into test(seq, s) values((select m from (select max(seq) + 1 as m from test) a), (select 1))";
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		try (Connection conn = connectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.executeUpdate();				

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
