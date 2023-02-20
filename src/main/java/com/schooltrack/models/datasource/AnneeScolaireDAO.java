package com.schooltrack.models.datasource;

import com.schooltrack.exceptions.DAOException;
import com.schooltrack.exceptions.DBHandlingException;
import com.schooltrack.jdbc.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AnneeScolaireDAO {
      
      public int readLastId() throws DAOException {
          try (Connection connection = DBManager.getConnection()) {
              String sql = "SELECT MAX(id) AS id FROM anneeScolaire";
              PreparedStatement preparedStatement = connection.prepareStatement(sql);
              ResultSet resultSet = preparedStatement.executeQuery();
              if (resultSet.next()) {
                  return resultSet.getInt("id");
              }
          } catch (DBHandlingException | SQLException e) {
              throw new DAOException(e.getMessage());
          }
          return 0;
      }
}
