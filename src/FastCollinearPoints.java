/**
 * @author 	mikhail
 * Created 	29.09.2016
 * Last		modified 29.09.2016
 *
 */

import java.util.Arrays;

public class FastCollinearPoints
{
	/**
	 * Finds all line segments containing 4 or more points
	 * @param points
	 */
	public FastCollinearPoints(Point[] points)
	{
		Point[] origins = new Point[points.length];
		for (int i = 0; i < points.length; i++)
			origins[i] = points[i];
		Arrays.sort(origins);
		
		for (int o = 0; o < points.length; o++) {
			Arrays.sort(points, origins[o].slopeOrder());
			double prevSlope = origins[o].slopeTo(points[0]);
			int segSize = 1;
			for (int c = 1; c < points.length; c++) {
				double curSlope = origins[c].slopeTo(points[c]);
				if (curSlope == prevSlope) segSize++;
				else {
					if (segSize > 3) {
						Arrays.sort(points, c-segSize, c);
						
						
					}
					
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
		return 0;
	}
	
	/**
	 * // the line segments
	 * @return
	 */
	public LineSegment[] segments()
	{
		return null;
	}
}