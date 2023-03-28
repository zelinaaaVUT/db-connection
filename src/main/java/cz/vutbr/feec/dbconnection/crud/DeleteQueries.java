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
public class DeleteQueries {

  public DeleteQueries() {}

  /**
   * Template metoda, do kter� se d� vkl�dat libovoln� SQL DELETE p��kaz.
   * 
   * POZN: nen� vhodn� implementovat sv� metody t�mto zp�sobem, daleko vhodn�j�� je implementovat
   * konkr�tn� metody (nap�. deleteUserById(String email) pomoc� PreparedStatements, do kter�ch
   * vkl�d�me konkr�tn� parametry)
   * 
   * @param deleteQuery �et�zec p�edstavuj�c� p��kaz DELETE
   */
  public void performDeleteQuery(String deleteQuery) {
    if (deleteQuery == null) {
      throw new NullPointerException("query must not be null!");
    } else if (deleteQuery.isEmpty()) {
      throw new IllegalArgumentException("query must not be empty!");
    }
    Connection conn = DBConnection.getDBConnection();
    try (PreparedStatement prStmt = conn.prepareStatement(deleteQuery);) {
      int rowsDeleted = prStmt.executeUpdate();
      // System.out.println(rowsInserted);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  //@formatter:off
  /**
   * �kol: Dopl�te prom�nnou userToDelete p��kazem DELETE ..., kter� vyma�e u�ivatele podle emailu, kter� je p�ed�n v parametru metody
   * 
   * HINT: V t�to metod� jsou vyu�ity preparedStatements, tak�e se parametr emailu nastav� a� pozd�ji p��kazem prStmt.setString(1, email). 
   * Pro p�edstavu, jak se p�� prepared statements se pod�vejte na n�sleduj�c� odkaz: 
   * https://www.mkyong.com/jdbc/jdbc-preparestatement-example-delete-a-record/
   * 
   * @param email na z�klad�, kter�ho je vymaz�n dan� u�ivatel
   */
  //@formatter:on
  public void deleteUserByEmail(String email) {
    if (email == null) {
      throw new NullPointerException("email must not be null!");
    } else if (email.isEmpty()) {
      throw new IllegalArgumentException("email must not be empty!");
    }
    Connection conn = DBConnection.getDBConnection();

    String userToDelete = "DELETE FROM user WHERE email = ?";

    try (PreparedStatement prStmt = conn.prepareStatement(userToDelete);) {
      prStmt.setString(1, email);
      int rowsDeleted = prStmt.executeUpdate();
      System.out.println("Va��m p��kazem byl vymaz�n n�sleduj�c� po�et u�ivatel�: " + rowsDeleted);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
