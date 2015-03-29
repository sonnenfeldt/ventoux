package de.sonnenfeldt.lavisgrafix.helper;



import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import de.sonnenfeldt.lavisgrafix.dao.AssetRepository;
import de.sonnenfeldt.lavisgrafix.model.Asset;

public class AssetHelper {

	public static String DEFAULT_FILE_NAME = "./links.txt";
	
	public AssetHelper() {
	
	}

	public static List<Asset> getAssets(String filename) {

	    BufferedReader br = null;
		List<Asset> assetList = new ArrayList<Asset>();
	    try {
							
				br = new BufferedReader(new FileReader(filename));
		        String link = br.readLine();
		        Asset asset = null;


		        int i = 1;
		        while (link != null) {

		        	asset = new Asset();
		        	String name = ("asset_" + i);
		    		asset.setName(name);
		    		asset.setDescription("Description of " + name);
		    		asset.setLocation(link);
		    		asset.setThumbnailLocation(link);
		    		
		    		// System.out.println(asset.toJsonString());
		    		
		    		assetList.add(asset);
		    		
		            link = br.readLine();	        
		    		i++;
		        }

		        
			} catch (FileNotFoundException e) {	
				e.printStackTrace();	        
		    } catch (IOException e) {
				e.printStackTrace();
			} finally {
		        try {
		        	if (br!=null) {
		        		br.close();
		        	}
				} catch (IOException e) {
					e.printStackTrace();
				}
		    }					
		return assetList;
	}
	

	public static int storeToDB(List<Asset> assetList) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"spring/application-config.xml");
		AssetRepository assetRepository = ctx.getBean(AssetRepository.class);

		int n = 0;
		for (Asset asset: assetList) {
			assetRepository.insert(asset);
			n++;
		}
		return n;
	}
	
	
	public static void storeToDB(Asset asset) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"spring/application-config.xml");
		AssetRepository assetRepository = ctx.getBean(AssetRepository.class);

		assetRepository.insert(asset);
	}
	
	

	public static int deleteFromDB(List<Asset> assetList) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"spring/application-config.xml");
		AssetRepository assetRepository = ctx.getBean(AssetRepository.class);

		int n = 0;
		for (Asset asset: assetList) {
			assetRepository.delete(asset.getUuid());
			n++;
		}
		return n;
	}

	public static void deleteFromDB(Asset asset) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"spring/application-config.xml");
		AssetRepository assetRepository = ctx.getBean(AssetRepository.class);

		assetRepository.delete(asset.getUuid());
	}
	
	
	public static void main(String[] args) {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"spring/application-config.xml");
		AssetRepository assetRepository = ctx.getBean(AssetRepository.class);

		
		List<Asset> assetList = getAssets(DEFAULT_FILE_NAME);
		int n = storeToDB(assetList);
		System.out.println("Number of new assets:" + n);
		
		List<Asset> all = assetRepository.getAll();
		System.out.println("Number of asset found: " + all.size());
		
	}

}
