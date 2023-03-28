package cz.vutbr.feec.dbconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import cz.vutbr.feec.dbconnection.crud.DeleteQueries;
import cz.vutbr.feec.dbconnection.crud.InsertQueries;
import cz.vutbr.feec.dbconnection.crud.SelectQueries;
import cz.vutbr.feec.dbconnection.crud.UpdateQueries;
import cz.vutbr.feec.dbconnection.dbconn.DBConnection;

/**
 * POZN. V �kolech je �ast� doimplementovat p��kaz nebo celou metodu, proto pokud se z t�to t��dy
 * chcete dostat k implementaci metody zm��kn�te tla��tko Control a prav�m naje�te na metodu, kterou
 * m�te implementovat a klikn�te na Open Implementation.
 * 
 * @author Pavel �eda (154208)
 *
 */
public class RunApp {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int choice = 0;
    boolean run = true;
    int iteration = 0;

    while (run) {
      if (iteration == 0)
        iteration++;
      else
        System.out.println(System.lineSeparator());
      System.out.println("Vyberte po�adovanou �innost:");
      System.out.println("1 .. vlo�en� u�ivatele s emailem myname123@stud.feec.vutbr.cz");
      System.out.println("2 .. vlo�en� libovoln�ho zadan�ho u�ivatele");
      System.out.println("3 .. v�pis emailu, jm�na a p��jmen� o v�ech u�ivatel�ch");
      System.out.println("4 .. v�pis v�ech u�ivatel� s rol� USER");
      System.out.println("5 .. zv��en� platu u�ivatele s emailem: myname@stud.feec.vutbr.cz o 20%");
      System.out.println("6 .. smaz�n� u�ivatele");
      System.out.println("7 .. v�pis v�ech rol� v syst�mu");
      System.out.println("8 .. ukonceni aplikace");
      choice = ConsoleInput.readNumberInputFromConsole(sc);
      switch (choice) {
        case 1:
          // tento p��klad zn�zor�uje vlo�en� u�ivatele s emailem: myname123@stud.feec.vutbr.cz,
          // jm�nem Jon, p�ijmen�m Doe, v�kem 30 let a v��� platu 10 000
          InsertQueries i = new InsertQueries();
          i.performInsertQuery("INSERT INTO user " + "(email,name,surname,age,salary)"
              + "VALUES('myname123@stud.feec.vutbr.cz', 'Jon','Doe', 30, 10000)");
          break;
        case 2:
          System.out.println("Zadejte email u�ivatele");
          String email = sc.next();
          SelectQueries testUserExistence = new SelectQueries();
          if (testUserExistence.testIfUserExists(email)) {
            String userEmailToCreate = "";
            do {
              System.out.println(
                  "U�ivatel s takov�mto emailem ji� existuje pros�m zadejte email znovu: ");
              userEmailToCreate = sc.next();
            } while (testUserExistence.testIfUserExists(userEmailToCreate));
          }

          System.out.println("Zadejte jm�no u�ivatele");
          String name = sc.next();
          System.out.println("Zadejte p��jmen� u�ivatele");
          String surname = sc.next();

          InsertQueries i2 = new InsertQueries();
          i2.insertNewUser(email, name, surname);
          break;
        case 3:
          SelectQueries se = new SelectQueries();
          // dopl�t� tuto metodu dle zad�n� v metod�
          se.getAllUserEmailAndNameAndSurname();
          break;
        case 4:
          SelectQueries selectUserRoles = new SelectQueries();
          // prostudujte si tuto metodu a zjist�te, jak funguje JOIN tabulek
          selectUserRoles.getAllUsersWithRoleUser();
          break;
        case 5:
          SelectQueries selectUser = new SelectQueries();
          System.out.println("Email a plat u�ivatele p�ed zv��en�m platu o 20%");
          selectUser.printUserEmailAndSalary("myname@stud.feec.vutbr.cz");
          UpdateQueries updateQuery = new UpdateQueries();

          // dopl�te tuto metodu, tak abyste tomuto u�ivateli zv��ili plat o 20%
          updateQuery.increase20PercentOfSalary("myname@stud.feec.vutbr.cz");

          System.out.println("Email a plat u�ivatele po zv��en�m platu o 20%");
          selectUser.printUserEmailAndSalary("myname@stud.feec.vutbr.cz");
          break;
        case 6:
          System.out.println(
              "Zadejte email u�ivatele, kter�ho chcete vymazat z datab�ze (nap�. myname123@stud.feec.vutbr.cz)");
          String userName = sc.next();
          SelectQueries s = new SelectQueries();
          if (s.testIfUserExists(userName)) {
            DeleteQueries d = new DeleteQueries();

            // dopl�te metodu na vymaz�n� u�ivatele dle emailu
            d.deleteUserByEmail(userName);
          } else {
            System.out
                .println("U�ivatel se zadan�m emailem neexistuje, zkuste zadat email spr�vn�.");
            break;
          }
          break;
        case 7:
          SelectQueries roles = new SelectQueries();
          // implementuje celou metodu printAllRolesInDB, tak aby vypsala v�echny role v DB
          roles.printAllRolesInDB();
          break;
        case 8:
          run = false;
          DBConnection.closeConnection();
          break;
        default:
          run = false;
          DBConnection.closeConnection();
          break;
      }
    }
  }
}
