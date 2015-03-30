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

	private static int NUMBER_OF_ASSET = 41;
	private static String BASE_LOCATION = "https://s3-eu-west-1.amazonaws.com/lavisgrafix/";
	
	public AssetHelper() {
	
	}

	public static List<Asset> getAssets() {

		List<Asset> assetList = new ArrayList<Asset>();
							

		Asset asset = null;
		String link = null;
		String thumb_link = null;
		for (int i=1;i < NUMBER_OF_ASSET+1;i++) {

			link = BASE_LOCATION + "images/" + i + ".jpg";
		    thumb_link = BASE_LOCATION + "thumbs/" + i + ".jpg";
		        	
		    asset = new Asset();
		    String name = ("asset_" + i);
		    asset.setName(name);
		    asset.setDescription("Description of " + name);
		    asset.setLocation(link);
		    asset.setThumbnailLocation(thumb_link);
		    		
		    assetList.add(asset);
		    		
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

		
		List<Asset> assetList = getAssets();
		int n = storeToDB(assetList);
		System.out.println("Number of new assets:" + n);
		
		List<Asset> all = assetRepository.getAll();
		System.out.println("Number of asset found: " + all.size());
		
	}

}
