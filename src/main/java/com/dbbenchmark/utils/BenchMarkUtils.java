package com.dbbenchmark.utils;

import com.dbbenchmark.dtos.BenchmarkResult;

import java.sql.*;
import java.util.logging.Logger;

public class BenchMarkUtils {
    private static final Logger logger = Logger.getLogger(BenchMarkUtils.class.getName());

    public static BenchmarkResult runInsertBenchmark(final Connection connection, final int numOperations, final int commitInterval)
            throws SQLException {
        final String query = "INSERT INTO benchmark_table (name) VALUES (?)";
        PreparedStatement ps = connection.prepareStatement(query);
        double totalInsertTime = 0;
        long minInsertTime = Long.MAX_VALUE;
        long maxInsertTime = Long.MIN_VALUE;

        for (int i = 1; i <= numOperations; i++) {
            long start = System.nanoTime();
            ps.setString(1, "Test " + i);
            ps.executeUpdate();
            long timeSpent = System.nanoTime() - start;

            totalInsertTime += timeSpent;
            if (timeSpent < minInsertTime) {
                minInsertTime = timeSpent;
            } else if (timeSpent > maxInsertTime) {
                maxInsertTime = timeSpent;
            }
            if (i % commitInterval == 0) {
                connection.commit();
            }
        }
        connection.commit();
        double avgInsertTime = totalInsertTime / numOperations;
        ps.close();

        // Nanoseconds to milliseconds
        return new BenchmarkResult(
                minInsertTime / 1_000_000.0,
                maxInsertTime / 1_000_000.0,
                avgInsertTime / 1_000_000.0
        );
    }

    public static BenchmarkResult runSelectBenchmark(final Connection connection, final int numOperations)
            throws SQLException {
        final String query = "SELECT * FROM benchmark_table WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        double totalSelectTime = 0;
        long minSelectTime = Long.MAX_VALUE;
        long maxSelectTime = Long.MIN_VALUE;

        for (int i = 1; i <= numOperations; i++) {
            long start = System.nanoTime();
            ps.setInt(1, i);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                rs.getString("name");
            }
            rs.close();
            long timeSpent = System.nanoTime() - start;
            totalSelectTime += timeSpent;
            if (timeSpent < minSelectTime) {
                minSelectTime = timeSpent;
            } else if (timeSpent > maxSelectTime) {
                maxSelectTime = timeSpent;
            }
        }
        ps.close();
        double avgSelectTime = totalSelectTime / numOperations;

        // Nanoseconds to milliseconds
        return new BenchmarkResult(
                minSelectTime / 1000000.0,
                maxSelectTime / 1000000.0,
                avgSelectTime / 1000000
        );
    }

    public static void saveBenchmarkResult(Connection connection, String operation, BenchmarkResult result, int numOperation)
            throws SQLException {
        String query = "INSERT INTO benchmark_results (operation, min_time, max_time, avg_time) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, operation);
        ps.setDouble(2, result.getMinTime());
        ps.setDouble(3, result.getMaxTime());
        ps.setDouble(4, result.getAvgTime());
        ps.executeUpdate();
        ps.close();
        logResults(operation, numOperation, result);
    }

    public static void logResults(String operation, int numOperation, BenchmarkResult result) {
        logger.info(operation + " Benchmark of " + numOperation + " rows:\n\t\t\t\t min: " + result.getMinTime() + " ms, max: " +
                result.getMaxTime() + " ms, avg: " + result.getAvgTime() + " ms\n");
    }
}
