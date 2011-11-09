import org.asterope.healpix.LongRangeSet;
import org.asterope.healpix.PixToolsNested;
import org.asterope.healpix.Vector3D;


/** 
 * Example howto use nested scheme
 */
public class NestedExample {

	public static void main(String[] args) {
		
		/*
		 * Everything related to nested scheme is contained in separate class
		 * which extends PixTools 
		 */
		PixToolsNested t = new PixToolsNested();
		
		Vector3D northPole = t.Ang2Vec(0, 0);
		/*
		 * all nested related methods have _nested suffix 
		 */
		t.ang2pix_nest(512, 0, 0);
		LongRangeSet northDisc = t.query_disc_nested(510, northPole, Math.toRadians(10), true);		
		

	}

}
