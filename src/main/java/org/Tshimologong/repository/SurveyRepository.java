package org.Tshimologong.repository;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SurveyRepository {
    private static final String DB_URL = "jdbc:sqlite:survey.db";

    public SurveyRepository() {
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS survey_data (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "full_names TEXT," +
                "email TEXT," +
                "dob TEXT," +
                "contact_number TEXT," +
                "favorite_food TEXT," +
                "movies INTEGER," +
                "radio INTEGER," +
                "eat_out INTEGER," +
                "watch_tv INTEGER" +
                ")";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveToDatabase(String fullNames, String email, String dob, String contactNumb, String[] foods, int movies, int radio, int eat_out, int watch_tv) {
        String sql = "INSERT INTO survey_data (full_names, email, dob, contact_number, favorite_food, movies, radio, eat_out, watch_tv) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String favoriteFoods = String.join(",", foods);

            pstmt.setString(1, fullNames);
            pstmt.setString(2, email);
            pstmt.setString(3, dob);
            pstmt.setString(4, contactNumb);
            pstmt.setString(5, favoriteFoods);
            pstmt.setInt(6, movies);
            pstmt.setInt(7, radio);
            pstmt.setInt(8, eat_out);
            pstmt.setInt(9, watch_tv);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<SurveyData> getSurveyData() {
        List<SurveyData> surveyData = new ArrayList<>();
        String sql = "SELECT * FROM survey_data";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String dobString = rs.getString("dob");
                int age = getAge(dobString);

                surveyData.add(new SurveyData(
                        rs.getString("full_names"),
                        rs.getString("email"),
                        age,
                        rs.getString("contact_number"),
                        rs.getString("favorite_food"),
                        rs.getInt("movies"),
                        rs.getInt("radio"),
                        rs.getInt("eat_out"),
                        rs.getInt("watch_tv")
                ));
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return surveyData;
    }

    private int getAge(String dobString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date dobDate = dateFormat.parse(dobString);
        Date dob = new Date(dobDate.getTime());

        Calendar dobCal = Calendar.getInstance();
        dobCal.setTime(dob);
        int yearOfBirth = dobCal.get(Calendar.YEAR);

        Calendar now = Calendar.getInstance();
        int currentYear = now.get(Calendar.YEAR);

        return currentYear - yearOfBirth;
    }
}
