package config;

public final class KMeansConfig {

    private final int k;
    private final int maxIterations;
    private final double tolerance;
    private final int parallelThreshold;
    private final long randomSeed;

    public KMeansConfig(int k, int maxIterations, double tolerance,
            int parallelThreshold, long randomSeed) {
        if (k <= 0) {
            throw new IllegalArgumentException("k must be positive");
        }
        if (maxIterations <= 0) {
            throw new IllegalArgumentException("maxIterations must be positive");
        }
        if (!Double.isFinite(tolerance) || tolerance < 0.0) {
            throw new IllegalArgumentException("tolerance must be finite and non-negative");
        }
        if (parallelThreshold <= 0) {
            throw new IllegalArgumentException("parallelThreshold must be positive");
        }

        this.k = k;
        this.maxIterations = maxIterations;
        this.tolerance = tolerance;
        this.parallelThreshold = parallelThreshold;
        this.randomSeed = randomSeed;
    }

    public int getK() {
        return k;
    }

    public int getMaxIterations() {
        return maxIterations;
    }

    public double getTolerance() {
        return tolerance;
    }

    public int getParallelThreshold() {
        return parallelThreshold;
    }

    public long getRandomSeed() {
        return randomSeed;
    }
}
