/**
 * @author 			mikhail
 * Created			28.09.2016
 * Last modified  	29.09.2016
 * 
 */

import edu.princeton.cs.algs4.*;

public class BruteCollinearPoints
{
	private final Stack<LineSegment> segStack;
	
	/**
	 * Finds all line segments containing 4 points
	 * @param points
	 */
	public BruteCollinearPoints(Point[] points)
	{
		if (points == null)
			throw new java.lang.NullPointerException();
		
		segStack = new Stack<LineSegment>();
		
		for (int p = 0; p < points.length; p++)
			if (points[p] == null)
				throw new java.lang.NullPointerException();
		
		for (int p = 0; p < points.length; p++)
			for (int q = 0; q < points.length; q++) {
				
				if (points[p] == points[q])
					throw new java.lang.IllegalArgumentException();
				
				double pqSlope = points[p].slopeTo(points[q]);				
				for (int r = 0; r < points.length; r++) {
					
					double prSlope = points[p].slopeTo(points[r]);					
					for (int s = 0; s < points.length; s++) {
						
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
}
