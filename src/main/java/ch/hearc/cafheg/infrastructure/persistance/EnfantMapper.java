package ch.hearc.cafheg.infrastructure.persistance;

import ch.hearc.cafheg.business.allocations.NoAVS;
import ch.hearc.cafheg.business.versements.Enfant;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnfantMapper extends Mapper {

  public Enfant findById(long id) {
    Connection connection = getConnection();
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(
          "SELECT NO_AVS, NOM, PRENOM FROM ENFANTS WHERE NUMERO=?");
      preparedStatement.setLong(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();
      resultSet.next();
      return new Enfant(new NoAVS(resultSet.getString(1)),
          resultSet.getString(2), resultSet.getString(3));
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

}
