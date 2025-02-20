package com.dbbenchmark.dtos;

public class BenchmarkResult {
    private final double minTime;
    private final double maxTime;
    private final double avgTime;

    public BenchmarkResult(double minTime, double maxTime, double avgTime) {
        this.minTime = minTime;
        this.maxTime = maxTime;
        this.avgTime = avgTime;
    }

    public double getMinTime() {
        return minTime;
    }

    public double getMaxTime() {
        return maxTime;
    }

    public double getAvgTime() {
        return avgTime;
    }
}