package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Cluster {
    private final int id;
    private Point centroid;
    private final List<Point> points = new ArrayList<>();

    public Cluster(int id, Point centroid) {
        this.id = id;
        this.centroid = Objects.requireNonNull(centroid, "centroid");
    }

    public int getId() {
        return id;
    }

    public Point getCentroid() {
        return centroid;
    }

    public List<Point> getPoints() {
        return Collections.unmodifiableList(points);
    }

    public void addPoint(Point point) {
        points.add(Objects.requireNonNull(point, "point"));
    }

    public void clearPoints() {
        points.clear();
    }

    /**
     * Replaces the centroid with the mean and returns the Euclidean movement.
     * An empty cluster keeps its centroid and returns zero.
     */
    public double recomputeCentroid() {
        if (points.isEmpty()) {
            return 0.0;
        }

        double sumX = 0.0;
        double sumY = 0.0;
        for (Point point : points) {
            sumX += point.getX();
            sumY += point.getY();
        }

        Point newCentroid = new Point(sumX / points.size(), sumY / points.size());
        double movement = centroid.distanceTo(newCentroid);
        centroid = newCentroid;
        return movement;
    }

    public double computeSSE() {
        double sse = 0.0;
        for (Point point : points) {
            sse += point.squaredDistanceTo(centroid);
        }
        return sse;
    }
}
