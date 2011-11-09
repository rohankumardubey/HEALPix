package org.asterope.healpix;

import junit.framework.TestCase;
import org.apache.commons.math.geometry.Vector3D;

import java.util.Vector;

/**
 * Test which runs some 'brutal force' calculations to 
 * test calculations produce consistent results.
 * <p>
 * This test is optional and may take very long time to finish. 
 */
public class CalculTest extends TestCase{
	
	final long COUNT = 1000;
	
	final long[] nsidelist = { 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048,
			4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288, 1048576 };
	

	
	public double nsideRes(long nside){
		return Math.toRadians(PixTools.PixRes(nside) / 3600d);
	}
	

	public void testInversionRing(){		
		for(long nside: nsidelist){
                        if(nside<5) continue;
                        PixTools p = new PixTools(nside);
			System.out.println(nside);
			double angle = nsideRes(nside)*2;
			for(long n = 0;n<COUNT * 10; n++){
				//convert random vector to IPIX
				Vector3D v = new Vector3D(Math.random() - 0.5, Math.random() - 0.5, Math.random() - 0.5).normalize();
				long ipix = p.vect2pix( v);
				//and convert IPIX back to vector
				Vector3D v2 = p.pix2vect( ipix);
				//make sure inversion is close enought
				assertTrue( "Vector inversion failed. Nside:"+nside+", \nvector:"+v,
                        Vector3D.angle(v2,v) < angle);
				
			}
		}
	}

	
	public void testSmallCircleRing(){
		//query small circle and check center ipix is in it
		for(long nside: nsidelist){
			if(nside<500) continue;
                        PixTools p = new PixTools(nside);
			System.out.println(nside);
			double angle = nsideRes(nside);
			for(long n = 0;n<COUNT; n++){
				//get random vector and ipix
				Vector3D v = new Vector3D(Math.random() - 0.5, Math.random() - 0.5, Math.random() - 0.5).normalize();
				long ipix = p.vect2pix( v);
				//query circle
				LongRangeSet r1 = p.query_disc(v,angle,true);
				assertTrue("Query disc failed. Nside:  "+nside+ 
						"\n angle: "+Math.toDegrees(angle)+"\n vector : "+v+
						"\n ipix: "+ipix+"\n rangeSet: "+r1,
						r1.contains(ipix));
				//test non inclusive
				LongRangeSet r2 = p.query_disc(v,angle,false);
				assertTrue("Query disc failed non inclusive. Nside:  "+nside+ 
						"\n angle: "+Math.toDegrees(angle)+"\n vector : "+v+
						"\n ipix: "+ipix+"\n rangeSet: "+r2,
						r2.contains(ipix));

				//even smaller circle
				LongRangeSet r3 = p.query_disc(v,angle/10,true);
				assertTrue("Query disc failed inclusive. Nside:  "+nside+ 
						"\n angle: "+Math.toDegrees(angle/10)+"\n vector : "+v+
						"\n ipix: "+ipix+"\n rangeSet: "+r3,
						r3.contains(ipix));
				
				//bigger circle
				LongRangeSet r4 = p.query_disc(v,angle*10,false);
				assertTrue("Query disc failed non inclusive. Nside:  "+nside+ 
						"\n angle: "+Math.toDegrees(angle*10)+"\n vector : "+v+
						"\n ipix: "+ipix+"\n rangeSet: "+r4,
						r4.contains(ipix));


			}
		}
	}
	
	

	
}
