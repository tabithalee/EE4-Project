import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Image;
import java.io.FileWriter;
import java.io.BufferedWriter;

/*
* Loops through all jpeg files in a directory defined by the variable dir and creates and saves a series of images,
* each 1x384 pixels, made up of the center pixels of the images in the directory.
*/
public class ImageToText{

	public static void main(String args[]) throws IOException{


		File dir = new File("/home/timothy/workspace/TDP4/testDir");
  		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {

			int count = 5;

			int stamp = 1;
			int col=0;
			BufferedWriter output = null;
			File file = new File("image_"+(stamp++)+".txt");
			output = new BufferedWriter(new FileWriter(file));

		    for (File child : directoryListing) {
		      	if( child.getName().substring(child.getName().lastIndexOf(".")+1).equals("jpg")){
		      		System.out.println(child.getName());


		      		BufferedImage im = null;
		      		String data = "";

		      		im = ImageIO.read(child);
		      		final byte[] pixels = ((DataBufferByte) im.getRaster().getDataBuffer()).getData();
				    final int width = im.getWidth();
				    final int height = im.getHeight();
				    final boolean hasAlphaChannel = im.getAlphaRaster() != null;

				    if (hasAlphaChannel){
				    	final int pixelLength = 4;
				    	for (int pixel = 0, row=0; row < height; row++){
				    		int sum = 0;
				    		sum += (((int) pixels[pixel + ((width/2)*pixelLength + row*(width*pixelLength))] & 0xff) << 24); // alpha
				            sum += ((int) pixels[pixel  + ((width/2)*pixelLength + row*(width*pixelLength)) + 1] & 0xff); // blue
				            sum += (((int) pixels[pixel + ((width/2)*pixelLength + row*(width*pixelLength)) + 2] & 0xff) << 8); // green
				            sum += (((int) pixels[pixel + ((width/2)*pixelLength + row*(width*pixelLength)) + 3] & 0xff) << 16); // red

				            if(col>=768){
				            	data+=(sum<<16)|(sum<<8)|(sum);
				            	try {
							        output.write(data+"\n");
						        } catch ( IOException e ) {
						            e.printStackTrace();
						        }      
				            	col=0;
				            }
				            else{
				            	sum=(sum<<16)|(sum<<8)|(sum);
				            	data+=sum+",";
				            	col++;
				            }
				    	}
				    }
				    else{
				    	final int pixelLength = 3;
				        for (int pixel = 0, row=0; row < height; row++) {
				        	int sum = 0;
				            sum += ( ((int) pixels[pixel + ((width/2)*pixelLength + row*(width*pixelLength))] & 0xff)
				            	+ (((int) pixels[pixel + ((width/2)*pixelLength + row*(width*pixelLength)) + 1] & 0xff) << 8)
				            	+ (((int) pixels[pixel + ((width/2)*pixelLength + row*(width*pixelLength)) + 2] & 0xff) << 16) ) /3;

				            if(col>=768){
				            	data+=(sum/3);
				            	try {
							        output.write(data+"\n");
						        } catch ( IOException e ) {
						            e.printStackTrace();
						        }     
				            	col=0;
				            }
				            else{
				            	if(count--<0) System.out.println(sum/3);
				            	data+=(sum/3)+",";
				            	col++;
				            }
				        }
				    }	

		      	}
		    }
		    output.close();
		} 
		else {
		    System.out.println("Directory not found.");
		}
	}

}