
package tr.edu.metu.ceng.sk;

import java.awt.geom.Point2D;
import java.util.Iterator;

public class EuclidDistanceMetric2D implements KMeans.PointMetric<Point2D> {

	public  double distance(Point2D a, Point2D b) {
		return a.distance(b);
	}

	public  Point2D centerOfMass(Iterator<Point2D> it) {

		double xSum = 0;
		double ySum = 0;
		int count = 0;
		for (; it.hasNext();) {
			Point2D p = it.next();
			xSum += p.getX();
			ySum += p.getY();
			count++;
		}
		double xCenter = xSum / count;
		double yCenter = ySum / count;

		return new Point2D.Double(xCenter, yCenter);
	}

	public  Point2D lowerBound(Iterator<Point2D> it) {

		double xMin = Double.MAX_VALUE;
		double yMin = Double.MAX_VALUE;
		for (; it.hasNext();) {
			Point2D p = it.next();
			xMin = Math.min(p.getX(), xMin);
			yMin = Math.min(p.getY(), yMin);
		}

		return new Point2D.Double(xMin, yMin);
	}

	public  Point2D upperBound(Iterator<Point2D> it) {
		double xMax = Double.MIN_VALUE;
		double yMax = Double.MIN_VALUE;
		for (; it.hasNext();) {
			Point2D p = it.next();
			xMax = Math.max(p.getX(), xMax);
			yMax = Math.max(p.getY(), yMax);
		}

		return new Point2D.Double(xMax, yMax);
	}

	public  Point2D move(Point2D point, double directionInRadian, double dist) {

		double x = point.getX();
		double y = point.getY();

		double newX = x + dist * Math.cos(directionInRadian);
		double newY = y + dist * Math.sin(directionInRadian);

		return new Point2D.Double(newX, newY);
	}

	public  int dimSize() {
		return 2;
	}

	public  double squareError(Iterator<Point2D> it, Point2D center) {

		double error = 0;
		for (; it.hasNext();) {
			Point2D p = it.next();
			error += Math.pow(this.distance(p, center), 2.0);
		}

		return error;
	}

}
