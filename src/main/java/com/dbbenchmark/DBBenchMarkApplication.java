package com.dbbenchmark;

import com.dbbenchmark.dtos.BenchmarkResult;
import com.dbbenchmark.utils.BenchMarkUtils;
import com.dbbenchmark.utils.ApplicationUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DBBenchMarkApplication {

    private static final Logger logger = Logger.getLogger(DBBenchMarkApplication.class.getName());
    private static final String INSERT_OPERATION = "INSERT";
    private static final String SELECT_OPERATION = "SELECT";

    public static void main(String[] args) {
        try {
            // Legge le proprietà dall'application.properties
            ApplicationUtils.readApplicationProperties();
            String url = ApplicationUtils.getProperty("db.url");
            String user = ApplicationUtils.getProperty("db.username");
            String password = ApplicationUtils.getProperty("db.password");
            int commitInterval = Integer.parseInt(ApplicationUtils.getProperty("db.commit.interval"));
            int numOperation = Integer.parseInt(ApplicationUtils.getProperty("db.commit.numOperation"));

            try {
                Connection connection = DriverManager.getConnection(url, user, password);
                connection.setAutoCommit(false);
                logger.info("Connected to database\n");

                // INSERT benchmark
                BenchmarkResult insertResult = BenchMarkUtils.runInsertBenchmark(connection, numOperation, commitInterval);
                BenchMarkUtils.saveBenchmarkResult(connection, INSERT_OPERATION, insertResult,numOperation);

                // SELECT benchmark
                BenchmarkResult selectResult = BenchMarkUtils.runSelectBenchmark(connection, numOperation);
                BenchMarkUtils.saveBenchmarkResult(connection, SELECT_OPERATION, selectResult, numOperation);

                connection.commit();
            } catch (SQLException e) {
                logger.severe("Database connection error: " + e.getMessage());
            }
        } catch (IOException e) {
            logger.severe("IOException error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
