
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import net.semanticmetadata.lire.builders.DocumentBuilder;
import net.semanticmetadata.lire.imageanalysis.features.global.CEDD;
import net.semanticmetadata.lire.searchers.GenericFastImageSearcher;
import net.semanticmetadata.lire.searchers.ImageSearchHits;
import net.semanticmetadata.lire.searchers.ImageSearcher;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.SimpleFSLockFactory;


public class RunLireRun {

	    public static void main(String[] args) throws IOException {
	        // Checking if arg[0] is there and if it is an image.
	        BufferedImage img = null;
	        boolean passed = false;
	        if (args.length >= 0) {
	            File f = new File("D:\\Akademik\\Thesis\\LYS\\DATA\\2\\002_003_036.jpg");
	            if (f.exists()) {
	                try {
	                    img = ImageIO.read(f);
	                    passed = true;
	                } catch (IOException e) {
	                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
	                }
	            }
	        }
	        if (!passed) {
	            System.out.println("No image given as first argument.");
	            System.out.println("Run \"Searcher <query image>\" to search for <query image>.");
	            System.exit(1);
	        }

//	        IndexReader ir = DirectoryReader.open(FSDirectory.open(new Path()));
	        Path path = Paths.get("D:\\Akademik\\Thesis\\LYS\\DATA\\1");// FileSystems.getDefault().getPath("index");

	        Directory index = FSDirectory.open(path);
	        IndexReader ir = DirectoryReader.open(index);
//	        IndexSearcher searcher = new IndexSearcher(reader);
	        
	        ImageSearcher searcher = new GenericFastImageSearcher(30, CEDD.class);

	        // searching with a image file ...
	        ImageSearchHits hits = searcher.search(img, ir);
	        // searching with a Lucene document instance ...
	        for (int i = 0; i < hits.length(); i++) {
//	            String fileName = hits.documentID(i).getValues(DocumentBuilder.FIELD_NAME_IDENTIFIER)[0];
	            System.out.println(hits.score(i) + ": \t" + hits.documentID(i));
	        }
	    }
	
}
