package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.food.model.Adiacenza;
import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDao {
	public List<Food> listAllFoods(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_code"),
							res.getString("display_name")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiments(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"),
							res.getDouble("condiment_calories"), 
							res.getDouble("condiment_saturated_fats")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Portion> listAllPortions(){
		String sql = "SELECT * FROM portion" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Portion> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Portion(res.getInt("portion_id"),
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"), 
							res.getDouble("calories"),
							res.getDouble("saturated_fats"),
							res.getInt("food_code")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Food> getFoodPortions(int portions){
		String sql = "SELECT f.food_code, f.display_name "
				+ "FROM food f, portion p "
				+ "WHERE f.food_code= p.food_code "
				+ "group by f.food_code, f.display_name "
				+ "having count(*) > ?";
		List<Food> list = new ArrayList<>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, portions);
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_code"),
							res.getString("display_name")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}


	}
	
	public List<Adiacenza> getAdiacenze(Map<Integer, Food> mFood){
		String sql = "SELECT f1.food_code as f1, f2.food_code as f2,  AVG(c.condiment_calories) as calorie "
				+ "FROM food f1, food f2, food_condiment fd1, food_condiment fd2, condiment c "
				+ "WHERE f1.food_code > f2.food_code "
				+ "	AND f1.food_code = fd1.food_code AND f2.food_code = fd2.food_code "
				+ "	AND fd1.condiment_code = fd2.condiment_code "
				+ "	AND fd1.condiment_code = c.condiment_code "
				+ "GROUP BY f1.food_code , f2.food_code ";
		List<Adiacenza> result = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
		
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					
					//aggiungere adiacenze 
					if(mFood.containsKey(res.getInt("f1")) && mFood.containsKey(res.getInt("f2"))) {
						Food f1 = mFood.get(res.getInt("f1"));
						Food f2 = mFood.get(res.getInt("f2"));
						double calorie = res.getDouble("calorie");
						Adiacenza a  = new Adiacenza (f1,f2,calorie);
						result.add(a);
					}
					
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
				
	}
	
	public List<Adiacenza> getAdiacenzeCalorieCongiunte(Food f, Map<Integer, Food> mFood){
		String sql = "SELECT f1.food_code as f1, f2.food_code as f2,  AVG(c.condiment_calories) as calorie "
				+ "FROM food f1, food f2, food_condiment fd1, food_condiment fd2, condiment c "
				+ "WHERE f1.food_code > f2.food_code "
				+ "		AND f1.food_code = fd1.food_code AND f2.food_code = fd2.food_code "
				+ "		AND fd1.condiment_code = fd2.condiment_code "
				+ "		AND fd1.condiment_code = c.condiment_code "
				+ "		AND f1.food_code = ? "
				+ "GROUP BY f1.food_code , f2.food_code ";
	List<Adiacenza> result = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, f.getFood_code());
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					
					//aggiungere adiacenze 
					if(mFood.containsKey(res.getInt("f1")) && mFood.containsKey(res.getInt("f2"))) {
						Food f1 = mFood.get(res.getInt("f1"));
						Food f2 = mFood.get(res.getInt("f2"));
						double calorie = res.getDouble("calorie");
						Adiacenza a  = new Adiacenza (f1,f2,calorie);
						result.add(a);
					}
					
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	
	}
	
	
	
	
}
