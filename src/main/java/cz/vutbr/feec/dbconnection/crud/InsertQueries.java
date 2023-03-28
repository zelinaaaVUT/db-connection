package cz.vutbr.feec.dbconnection.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import cz.vutbr.feec.dbconnection.dbconn.DBConnection;

/**
 * 
 * @author Pavel �eda (154208)
 *
 */
public class InsertQueries {

  public InsertQueries() {}


  /**
   * Template metoda, do kter� se d� vkl�dat libovoln� SQL INSERT p��kaz.
   * 
   * POZN: nen� vhodn� implementovat sv� metody t�mto zp�sobem, daleko vhodn�j�� je implementovat
   * konkr�tn� metody (nap�. insertNewUser(String email, char[] password) pomoc� PreparedStatements,
   * do kter�ch vkl�d�me konkr�tn� parametry)
   * 
   * @param insertQuery �et�zec p�edstavuj�c� p��kaz INSERT
   */
  public void performInsertQuery(String insertQuery) {
    if (insertQuery == null) {
      throw new NullPointerException("query must not be null!");
    } else if (insertQuery.isEmpty()) {
      throw new IllegalArgumentException("query must not be empty!");
    }
    Connection conn = DBConnection.getDBConnection();
    try (PreparedStatement prStmt = conn.prepareStatement(insertQuery);) {
      int rowsInserted = prStmt.executeUpdate();
      // System.out.println("Bylo vlo�eno u�ivatel�: " + rowsInserted);
      System.out.println("Byl vlo�en u�ivatel s emailem: " + "myname123@stud.feec.vutbr.cz");
    } catch (SQLException e) {
      System.out.println("U�ivatel s emailem: " + "myname123@stud.feec.vutbr.cz "
          + "ji� byl vlo�en nemus�te jej vkl�dat znovu");
      // e.printStackTrace();
    }
  }

  /**
   * �kol: Va��m �kolem je p�i�adit INSERT p��kaz do prom�nn� insertUser, tak aby se vytvo�il nov�
   * u�ivatel dle zadan�ch parametr�
   * 
   * HINT: V t�to metod� jsou vyu�ity preparedStatements, tak�e se parametr emailu nastav� a�
   * pozd�ji p��kazem prStmt.setString(1, email)... Pro p�edstavu, jak se p�� prepared statements
   * se pod�vejte na n�sleduj�c� odkaz:
   * https://www.mkyong.com/jdbc/jdbc-preparestatement-example-insert-a-record/
   * 
   * @param email u�ivatele
   * @param name u�ivatele
   * @param surname u�ivatele
   * @param age u�ivatele
   * @param salary u�ivatele
   */
  public void insertNewUser(String email, String name, String surname) {
    if (email == null || name == null || surname == null)
      throw new NullPointerException("Email, name and surname must be set.");

    Connection conn = DBConnection.getDBConnection();

    String insertUser = "INSERT INTO USER " + "(email, name, surname)" + " VALUES(?, ?, ?)";

    try (PreparedStatement prStmt = conn.prepareStatement(insertUser)) {
      prStmt.setString(1, email);
      prStmt.setString(2, name);
      prStmt.setString(3, surname);

      prStmt.executeUpdate();
      System.out.println("Nov� u�ivatel byl vlo�en do datab�ze!");
    } catch (SQLException e) {
      System.out.println("U�ivatel u� byl vlo�en nebo jste zadali �patn� SQL p��kaz INSERT");
      // e.printStackTrace();
    }
  }


}
