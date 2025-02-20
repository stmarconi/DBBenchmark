-- Test table
CREATE TABLE IF NOT EXISTS benchmark_table (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Benchmark results history table
CREATE TABLE IF NOT EXISTS benchmark_results (
    id SERIAL PRIMARY KEY,
    operation VARCHAR(20) NOT NULL,
    min_time DOUBLE PRECISION NOT NULL,
    max_time DOUBLE PRECISION NOT NULL,
    avg_time DOUBLE PRECISION NOT NULL,
    benchmark_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
