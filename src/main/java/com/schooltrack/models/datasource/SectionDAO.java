package com.schooltrack.models.datasource;

import com.schooltrack.exceptions.DAOException;
import com.schooltrack.exceptions.DBHandlingException;
import com.schooltrack.jdbc.DBManager;
import com.schooltrack.models.Section;
import javafx.collections.FXCollections;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SectionDAO implements DAO<Section>{
    @Override
    public void create(Section section) throws DAOException {
    
    }
    
    @Override
    public Section read(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM section WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Section section = new Section(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        FXCollections.observableArrayList(new ClasseDAO().readAll(resultSet.getInt("id")))
                );
            }
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return null;
    }
    
    @Override
    public void update(Section section) throws DAOException {
    
    }
    
    @Override
    public void delete(int id) throws DAOException {
    
    }
    
    @Override
    public List<Section> readAll() throws DAOException {
        List<Section> sections = FXCollections.observableArrayList();
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM section";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Section section = new Section(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        FXCollections.observableArrayList(new ClasseDAO().readAll(resultSet.getInt("id")))
                );
                sections.add(section);
            }
        } catch (DBHandlingException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return sections;
    }
}
