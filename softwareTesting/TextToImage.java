import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.io.FileNotFoundException;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Image;
import java.io.FileReader;
import java.io.BufferedReader;
import java.lang.Integer;
import java.util.Arrays;

public class TextToImage{

	public static void main(String args[]) throws IOException{

		File input = new File("new_image.txt");
		BufferedReader reader = null;
		int stamp = 0;

		try {
    		reader = new BufferedReader(new FileReader(input));
    		String im = null;

			int c = 1;
			BufferedImage bi = new BufferedImage(1,768,BufferedImage.TYPE_BYTE_GRAY);

			while ((im = reader.readLine()) != null) {
				bi.setRGB(0,c,Integer.parseInt(im));
				c++;

        		if(c>=768){
        			File ioutput = new File("edited_line_"+(stamp++)+".jpg");
				    ImageIO.write(bi,"jpg",ioutput);
				    c=0;
        		}

    		}
		} 
		catch (FileNotFoundException e) {
    		e.printStackTrace();
		} 
		catch (IOException e) {
    		e.printStackTrace();
		} 
		finally {
    		try {
        		if (reader != null) {
            		reader.close();
        		}
    		}
    		catch (IOException e) {
    		}
		}

	}

}