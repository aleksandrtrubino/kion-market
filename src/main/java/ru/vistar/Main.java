package ru.vistar;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void databaseUpdate() throws Exception {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/kion-market","postgres","postgres")) {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            try (Liquibase liquibase = new liquibase.Liquibase("config/liquibase/master.xml", new ClassLoaderResourceAccessor(), database);) {
                liquibase.update(new Contexts(), new LabelExpression());
            }
        } catch (DatabaseException e) {
            throw e;
        } catch (SQLException | LiquibaseException e) {
            throw e;
        }

    }

    public static void main(String[] args) throws SQLException {
        //startDatabase();

        try {
            databaseUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("br.com.fredericci.pu");
        final EntityManager entityManager = entityManagerFactory.createEntityManager();

        User user = new User();
        user.setEmail("string@mail.com");
        user.setPassword("1234");
        user.setEnabled(true);

        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();

    }

//    private static void startDatabase() throws SQLException {
//        new Server().runTool("-tcp", "-web", "-ifNotExists");
//    }
}