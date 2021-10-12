package com.douzone.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SimpleConnectionProvider implements ConnectionProvider {
	private String url;
	private String user;
	private String password;

	public SimpleConnectionProvider() {
		this(null, null, null);
	}

	public SimpleConnectionProvider(String url, String user, String password) {
		this.url = url;
		this.user = user;
		this.password = password;
		
		if(this.url != null && this.user != null && this.password != null) return;
		
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(getClass().getClassLoader().getResourceAsStream("application.properties")));) {
			String property = null;
			String[] tokens = new String[2];

			while ((property = br.readLine()) != null) {
				int idx = property.indexOf('=');
				tokens[0] = property.substring(0, idx);
				tokens[1] = property.substring(idx + 1);

				switch (tokens[0]) {
				case "driver":
					try {
						System.out.println(tokens[1]);
						Class.forName(tokens[1]);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case "url":
					if (this.url != null)
						break;
					
					this.url = tokens[1];
					break;
				case "user":
					if (this.user != null)
						break;
					
					this.user = tokens[1];
					break;
				case "password":
					if (this.password != null)
						break;
					
					this.password = tokens[1];
					break;
				default:
					System.out.println("알 수 없는 프로퍼티");
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		return DriverManager.getConnection(url, user, password);
	}
}