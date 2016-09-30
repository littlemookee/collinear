/**
 * @author 	mikhail
 * Created 	29.09.2016
 * Last		modified 29.09.2016
 *
 */

import java.util.Arrays;
import edu.princeton.cs.algs4.*;

public class FastCollinearPoints
{
	private final Stack<LineSegment> segStack;
	
	/**
	 * Finds all line segments containing 4 or more points
	 * @param points
	 */
	public FastCollinearPoints(Point[] points)
	{
		if (points == null)
			throw new java.lang.NullPointerException();

		for (int p = 0; p < points.length; p++) {
			if (points[p] == null)
				throw new java.lang.NullPointerException();
			for (int q = p+1; q < points.length; q++)
				if (points[p] == points[q])
					throw new java.lang.IllegalArgumentException();
			
		}
		
		segStack = new Stack<LineSegment>();
		
		Point[] origins = new Point[points.length];
		for (int i = 0; i < points.length; i++)
			origins[i] = points[i];
		Arrays.sort(origins);
		
		for (int o = 0; o < points.length; o++) {
			Arrays.sort(points, origins[o].slopeOrder());
			double prevSlope = origins[o].slopeTo(points[0]);
			int segSize = 2;
			for (int c = 1; c < points.length; c++) {
				double curSlope = origins[o].slopeTo(points[c]);
				if (curSlope == prevSlope) segSize++;
				else {
					if (segSize > 3) {
						Arrays.sort(points, c-segSize+1, c);
						Point start = points[c-segSize+1];
						if (origins[o].compareTo(start) < 0)
							start = origins[o];
						Point end = points[c-1];
						if (origins[o].compareTo(end) > 0)
							end = origins[o];
						boolean add = true;
						LineSegment newSegment = new LineSegment(start, end);
						for (LineSegment segment : segStack)
							if (segment.toString().equals(newSegment.toString())) {
								add = false;
								break;
							}
						if (add)
							segStack.push(newSegment);
					}
					prevSlope = curSlope;
					segSize = 2;
				}
			}
		}
	}
	
	/**
	 * The number of line segments
	 * @return
	 */
	public int numberOfSegments()
	{
		return segStack.size();
	}
	
	/**
	 * // the line segments
	 * @return
	 */
	public LineSegment[] segments()
	{
		LineSegment[] segments = new LineSegment[segStack.size()];
		int i = 0;
		for (LineSegment segment : segStack)
			segments[i++] = segment;
		return segments;
	}
	
	public static void main(String[] args) {

	    // read the n points from a file
	    In in = new In(args[0]);
	    int n = in.readInt();
	    Point[] points = new Point[n];
	    for (int i = 0; i < n; i++) {
	        int x = in.readInt();
	        int y = in.readInt();
	        points[i] = new Point(x, y);
	    }

	    // draw the points
	    StdDraw.enableDoubleBuffering();
	    StdDraw.setXscale(0, 32768);
	    StdDraw.setYscale(0, 32768);
	    for (Point p : points) {
	        p.draw();
	    }
	    StdDraw.show();

	    // print and draw the line segments
	    FastCollinearPoints collinear = new FastCollinearPoints(points);
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	    }
	    StdDraw.show();
	}	
}