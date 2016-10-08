/**
 * @author 			mikhail
 * Created			28.09.2016
 * Last modified  	29.09.2016
 * 
 */

import java.util.Arrays;

import edu.princeton.cs.algs4.*;

public class BruteCollinearPoints
{
	private final Stack<LineSegment> segStack;
	
	/**
	 * Finds all line segments containing 4 points
	 * @param points
	 */
	public BruteCollinearPoints(Point[] points_)
	{		
		if (points_ == null)
			throw new java.lang.NullPointerException();
	
		Point[] points = new Point[points_.length];
				
		for (int p = 0; p < points.length; p++) {
			points[p] = points_[p];
			if (points[p] == null)
				throw new java.lang.NullPointerException();
		}

		segStack = new Stack<LineSegment>();
		
		Arrays.sort(points);
		
		for (int p = 0; p < points.length; p++)
			for (int q = p+1; q < points.length; q++) {
				
				if (points[p].equals(points[q]))
					throw new java.lang.IllegalArgumentException();
				
				double pqSlope = points[p].slopeTo(points[q]);				
				for (int r = q+1; r < points.length; r++) {
					
					double prSlope = points[p].slopeTo(points[r]);					
					for (int s = r+1; s < points.length; s++) {
						
						double psSlope = points[p].slopeTo(points[s]);						
						if (pqSlope == prSlope && pqSlope == psSlope)
							segStack.push(new LineSegment(points[p], points[s]));
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
	 * The line segments
	 * @return
	 */
	public LineSegment[] segments()
	{
		LineSegment[] segments = new LineSegment[segStack.size()];
		int i = 0;
		for(LineSegment segment : segStack)
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
	    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);	        
	        segment.draw();
	        StdDraw.show();
	    }
	    StdDraw.show();
	}	
}
