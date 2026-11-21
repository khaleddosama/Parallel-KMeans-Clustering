package algorithm;

import config.KMeansConfig;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import model.Cluster;
import model.Point;

public class KMeansSequential {
    public List<Cluster> fit(List<Point> points, KMeansConfig config) {
        validateInput(points, config);
        List<Cluster> clusters = initializeClusters(points, config);

        double maxMovement = 0.0;
        for (int iteration = 0; iteration < config.getMaxIterations(); iteration++) {
            assignPoints(points, clusters);

            maxMovement = 0.0;
            for (Cluster cluster : clusters) {
                maxMovement = Math.max(maxMovement, cluster.recomputeCentroid());
            }
            if (maxMovement < config.getTolerance()) {
                break;
            }
        }

        if (maxMovement != 0.0) {
            assignPoints(points, clusters);
        }
        return clusters;
    }

    protected List<Cluster> initializeClusters(List<Point> points, KMeansConfig config) {
        List<Point> shuffledPoints = new ArrayList<>(points);
        Collections.shuffle(shuffledPoints, new Random(config.getRandomSeed()));

        List<Cluster> clusters = new ArrayList<>(config.getK());
        for (int id = 0; id < config.getK(); id++) {
            Point seed = shuffledPoints.get(id);
            clusters.add(new Cluster(id, new Point(seed.getX(), seed.getY())));
        }
        return clusters;
    }

    protected int findNearestCluster(Point point, List<Cluster> clusters) {
        int nearestIndex = 0;
        double nearestDistance = point.squaredDistanceTo(clusters.get(0).getCentroid());
        for (int index = 1; index < clusters.size(); index++) {
            double distance = point.squaredDistanceTo(clusters.get(index).getCentroid());
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestIndex = index;
            }
        }
        return nearestIndex;
    }

    private void assignPoints(List<Point> points, List<Cluster> clusters) {
        for (Cluster cluster : clusters) {
            cluster.clearPoints();
        }
        for (Point point : points) {
            Cluster cluster = clusters.get(findNearestCluster(point, clusters));
            point.setClusterId(cluster.getId());
            cluster.addPoint(point);
        }
    }

    private void validateInput(List<Point> points, KMeansConfig config) {
        if (points == null || points.isEmpty()) {
            throw new IllegalArgumentException("points must be a non-empty list");
        }
        if (config == null) {
            throw new IllegalArgumentException("config must not be null");
        }
        if (config.getK() > points.size()) {
            throw new IllegalArgumentException("k must not exceed the number of points");
        }
        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException("points must not contain null elements");
            }
        }
    }
}
