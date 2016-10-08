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
	private int N;
	private Point[] starts;
	private Point[] ends;
	private LineSegment[] segments;
	
	private void resize()
	{
		Point[] newStarts = new Point[2*N];
		Point[] newEnds = new Point[2*N];
		LineSegment[] newSegments = new LineSegment[2*N];
		for (int i = 0; i < N; i++) {
			newStarts[i] = starts[i];
			newEnds[i] = ends[i];
			newSegments[i] = segments[i];
		}
		starts = newStarts;
		ends = newEnds;
		segments = newSegments;
	}	
	
	private void addSegment(Point[] points, Point origin, int base, int offSet)
	{ 
		Arrays.sort(points, base, base+offSet);
		Point start = points[base];
		if (origin.compareTo(start) < 0)
			start = origin;
		Point end = points[base+offSet-1];
		if (origin.compareTo(end) > 0)
			end = origin;
		boolean add = true;		
		for (int i = 0; i < N; i++)
			if (starts[i] == start && ends[i] == end) {
				add = false;
				break;
			}
		if (add) {
			if (starts.length == N) resize();
			starts[N] = start;
			ends[N] = end;
			segments[N++] = new LineSegment(start, end);
		}
	}
	
	/**
	 * Finds all line segments containing 4 or more points
	 * @param points
	 */
	public FastCollinearPoints(Point[] points_)
	{
		if (points_ == null)
			throw new java.lang.NullPointerException();
		
		for (int p = 0; p < points_.length; p++) {
			if (points_[p] == null)
				throw new java.lang.NullPointerException();
			for (int q = p+1; q < points_.length; q++)
				if (points_[p].equals(points_[q]))
					throw new java.lang.IllegalArgumentException();			
		}

		Point[] points = new Point[points_.length];
		
		for (int p = 0; p < points.length; p++) points[p] = points_[p];
		
		N = 0;
		starts = new Point[1];
		ends = new Point[1];
		segments = new LineSegment[1];
		
		Point[] origins = new Point[points.length];
		for (int i = 0; i < points.length; i++) origins[i] = points[i];
		
		for (int o = 0; o < points.length; o++) {
			Arrays.sort(points, origins[o].slopeOrder());
			double prevSlope = origins[o].slopeTo(points[0]);
			int segSize = 1;
			int c;
			for (c = 1; c < points.length; c++) {
				double curSlope = origins[o].slopeTo(points[c]);
				if (curSlope == prevSlope) segSize++;
				else {
					if (segSize > 3)
						addSegment(points, origins[o], c-segSize+1, segSize-1);
					prevSlope = curSlope;
					segSize = 2;
				}
			}
			if (segSize > 3)
				addSegment(points, origins[o], c-segSize+1, segSize-1);
		}
	}
	
	/**
	 * The number of line segments
	 * @return
	 */
	public int numberOfSegments()
	{
		return N;
	}
	
	/**
	 * // the line segments
	 * @return
	 */
	public LineSegment[] segments()
	{		
		LineSegment[] out = new LineSegment[N];
		for (int i = 0; i < N; i++) out[i] = segments[i];
		return out;
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