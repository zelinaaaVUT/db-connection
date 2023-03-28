package cz.vutbr.feec.dbconnection.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cz.vutbr.feec.dbconnection.dbconn.DBConnection;

/**
 * 
 * @author Pavel �eda (154208)
 *
 */
public class SelectQueries {

  public SelectQueries() {}

  /**
   * Template metoda, do kter� se d� vkl�dat libovoln� SQL SELECT p��kaz. Tento template je uk�zka
   * pou�it� PreparedStatements, co� je v ur�it�ch p��padech rychlej�� alternativa ke Statements,
   * proto�e se SQL p��kazy p�edkompilov�vaj� a tak� je to ochrana proti SQL Injection viz.
   * https://xacker.files.wordpress.com/2010/12/sql_injection.png
   * 
   * POZN: nen� vhodn� implementovat sv� metody t�mto zp�sobem, daleko vhodn�j�� je implementovat
   * konkr�tn� metody (nap�. getAllUsers() pomoc� PreparedStatements, do kter�ch vkl�d�me konkr�tn�
   * parametry)
   * 
   * @param selectQuery �et�zec p�edstavuj�c� p��kaz SELECT
   */
  public void performPreparedStatementSelect(String selectQuery) {
    if (selectQuery == null) {
      throw new NullPointerException("query must not be null!");
    } else if (selectQuery.isEmpty()) {
      throw new IllegalArgumentException("query must not be empty!");
    }
    Connection conn = DBConnection.getDBConnection();
    try (PreparedStatement prStmt = conn.prepareStatement(selectQuery);
        ResultSet rs = prStmt.executeQuery()) {
      while (rs.next()) {
        System.out.println(rs.getString("id_user") + " : " + rs.getString("email") + ", "
            + rs.getString("name") + ", " + rs.getString("surname") + ", " + rs.getString("age")
            + ", " + rs.getString("salary"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Template metoda, do kter� se d� vkl�dat libovoln� SQL SELECT p��kaz. Tento template je uk�zka
   * pou�it� PreparedStatements
   * 
   * POZN: nen� vhodn� implementovat sv� metody t�mto zp�sobem, daleko vhodn�j�� je implementovat
   * konkr�tn� metody (nap�. getAllUsers() pomoc� PreparedStatements, do kter�ch vkl�d�me konkr�tn�
   * parametry)
   * 
   */
  public void performStatementSelect(String selectQuery) {
    if (selectQuery == null) {
      throw new NullPointerException("query must not be null!");
    } else if (selectQuery.isEmpty()) {
      throw new IllegalArgumentException("query must not be empty!");
    }

    Connection conn = DBConnection.getDBConnection();
    try (Statement prStmt = conn.createStatement();
        ResultSet rs = prStmt.executeQuery(selectQuery)) {
      while (rs.next()) {
        System.out.println(rs.getString("id_user") + " : " + rs.getString("email") + ", "
            + rs.getString("name") + ", " + rs.getString("surname") + ", " + rs.getString("age")
            + ", " + rs.getString("salary"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public boolean testIfUserExists(String userEmail) {
    if (userEmail == null) {
      throw new NullPointerException("query must not be null!");
    } else if (userEmail.isEmpty()) {
      throw new IllegalArgumentException("query must not be empty!");
    }

    Connection conn = DBConnection.getDBConnection();
    String testUserExistence = "SELECT * FROM user WHERE email = ?";

    try (PreparedStatement prStmt = conn.prepareStatement(testUserExistence);) {
      prStmt.setString(1, userEmail);
      ResultSet rs = prStmt.executeQuery();
      if (rs.next())
        return true;
      else
        return false;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  public void getAllUsers() {
    performPreparedStatementSelect("SELECT * FROM user");
  }

  public void printUserEmailAndSalary(String email) {
    Connection conn = DBConnection.getDBConnection();
    String userEmailAndSalary = "SELECT email, salary FROM User WHERE email = ?";

    try (PreparedStatement prStmt = conn.prepareStatement(userEmailAndSalary);) {
      prStmt.setString(1, email);
      ResultSet rs = prStmt.executeQuery();
      if (rs.next())
        System.out.println("Email u�ivatele je: " + rs.getString("email") + " plat u�ivatele je: "
            + rs.getString("salary"));
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * dopl�te String selectAlluserEmailNameAndSurname, tak aby se vypsal email, jm�no a p��jmen�
   * v�ech u�ivatel� v datab�zi
   * 
   * POZN: P��kaz SELECT * FROM User (nen� spr�vn�, proto�e ten by do objektu ResultSet vr�til
   * v�echny sloupce tabulky (I kdy� by "naoko" d�ky implementovan�m v�pis�m vypsal pouze email,
   * name a surname
   */
  public void getAllUserEmailAndNameAndSurname() {
    Connection conn = DBConnection.getDBConnection();
    String selectAlluserEmailNameAndSurname = "SELECT email, name, surname FROM User";

    try (PreparedStatement prStmt = conn.prepareStatement(selectAlluserEmailNameAndSurname);
        ResultSet rs = prStmt.executeQuery()) {
      while (rs.next()) {
        System.out.println(
            rs.getString("email") + ", " + rs.getString("name") + ", " + rs.getString("surname"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  public int getUserSalaryToIncrease(String email) {
    Connection conn = DBConnection.getDBConnection();
    String selectUserSalaryToIncrease = "SELECT email, salary FROM User WHERE email=?";

    try (PreparedStatement prStmt = conn.prepareStatement(selectUserSalaryToIncrease);){
        prStmt.setString(1, email);
        ResultSet rs = prStmt.executeQuery();
        if (rs.next()){
          int a = rs.getInt("salary");
          return a;
        }

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return 0;
  }

  /**
   * prostudujte si tuto metodu nebo n�sleduj�c� metodu "getAllUsersWithRoleUserById" a v�im�te si,
   * jak pracuje JOIN tabulek
   */
  public void getAllUsersWithRoleUser() {
    Connection conn = DBConnection.getDBConnection();
    String selectAllUsersWithRoleUser =
        "SELECT id_user, email, name, surname, age, salary FROM user JOIN user_has_role ON user.id_user = user_has_role.user_id_user WHERE role_id_role=1";

    try (PreparedStatement prStmt = conn.prepareStatement(selectAllUsersWithRoleUser);
        ResultSet rs = prStmt.executeQuery();) {
      while (rs.next()) {
        System.out.println(rs.getString("id_user") + " : " + rs.getString("email") + ", "
            + rs.getString("name") + ", " + rs.getString("surname") + ", " + rs.getString("age")
            + ", " + rs.getString("salary"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void getAllUsersWithRoleUserById(int id) {
    Connection conn = DBConnection.getDBConnection();
    String selectAllUsersWithRoleUser =
        "SELECT id_user, email, name, surname, age, salary FROM user JOIN user_has_role ON user.id_user = user_has_role.user_id_user WHERE role_id_role=?";
    try (PreparedStatement prStmt = conn.prepareStatement(selectAllUsersWithRoleUser);) {
      prStmt.setInt(1, id);
      ResultSet rs = prStmt.executeQuery();

      while (rs.next()) {
        System.out.println(rs.getString("id_user") + " : " + rs.getString("email") + ", "
            + rs.getString("name") + ", " + rs.getString("surname") + ", " + rs.getString("age")
            + ", " + rs.getString("salary"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Dopl�te celou implementaci k z�sk�n� v�pisu v�ech rol� v DB.
   * 
   * HINT: pod�vejte se na strukturu jin�ch metod v t�to t��d� pop��pad� se inspirujte na internetu
   * nap�.: https://www.mkyong.com/jdbc/jdbc-preparestatement-example-select-list-of-the-records/
   */
  public void printAllRolesInDB() {
    // 1. z�skejte connection k datab�zi
    // 2. vytvo�te SQL p��kaz k z�sk�n� v�ech rol� v DB
    // 3. vlo�te tento p��kaz jako prepareStatement k z�skan� connection k datab�zi
    // 4. vyvolejte tento PreparedStatement
    // 5. ulo�te v�sledek do ResultSetu
    // 6. p�es cyklus projd�te v�sledek ResultSetu a vypi�te role v syst�mu (pro z�sk�n� spr�vn�
    // kolonky se pod�vejte na n�vrh datab�ze, abyste znali n�zev sloupce (pop��pad� lze je�t�
    // z�skat data ze sloupce po�adov�m ��slem (za��naj�c od 1)))

    // tyto operace obalte v try-catch bloku pop��pad� v try-with-resources bloku

    Connection conn = DBConnection.getDBConnection();
    String all = "SELECT * FROM role";

    try (PreparedStatement prStmt = conn.prepareStatement(all);) {
      ResultSet rs = prStmt.executeQuery();
      while (rs.next()){
        System.out.println(rs.getString(2));
      }
    }
    catch (SQLException e){
     e.printStackTrace();
    }
  }
}

