package de.sonnenfeldt.lavisgrafix.dao.test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import de.sonnenfeldt.lavisgrafix.dao.AssetRepository;
import de.sonnenfeldt.lavisgrafix.model.Asset;
import de.sonnenfeldt.lavisgrafix.helper.AssetHelper;


public class AssetRepositoryTest {

	private AssetRepository repo;
	private List<Asset>assetList;
	private Random randomGenerator = new Random();	
	private Asset asset;
	private Asset assetToFind;
	private ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring/application-config.xml");;
	
	@Before
	public void setup() {
		
		repo = ctx.getBean(AssetRepository.class);
		
		assetList = AssetHelper.getAssets();
		
		int m = randomGenerator.nextInt(assetList.size());
		int n = randomGenerator.nextInt(assetList.size());
		
		if (m == n) {
			m = (assetList.size() < m + 1) ? m-- : m++;
		}
		
		asset = assetList.get(m);
		assetToFind = assetList.get(n);
		repo.insert(assetToFind);
		
	}	
	
	@After
	public void cleanup() {
		repo.delete(assetToFind.getUuid());
	}
	
		
	@Test
	public void testGetAll() {
		List<Asset> all = repo.getAll();
		assertThat(all, notNullValue());
		assertTrue(all.size() > 0 );
	}	
	
	@Test
	public void testLCM() {
		List<Asset> all = repo.getAll();
		assertThat(all, notNullValue());

		int currentSize = all.size();
		
		//create the asset
		repo.insert(asset);
		
		all = repo.getAll();
		assertThat(all, notNullValue());
		assertTrue(all.size() == currentSize + 1);

		//find the asset
		Asset assetFound = repo.findById(asset.getUuid());
		
		assertThat(assetFound, notNullValue());
		assertTrue(assetFound.getUuid().equals(asset.getUuid()));
		assertTrue(assetFound.getName().equals(asset.getName()));
		assertTrue(assetFound.getDescription().equals(asset.getDescription()));
		assertTrue(assetFound.getLocation().equals(asset.getLocation()));
		assertTrue(assetFound.getThumbnailLocation().equals(asset.getThumbnailLocation()));
	
		//delete the asset
		repo.delete(asset.getUuid());
				
		all = repo.getAll();
		assertTrue(all.size() == currentSize);
		
		//verify asset is deleted
		assetFound = repo.findById(asset.getUuid());
		assertThat(assetFound, nullValue());
				
	}
		
	@Test
	public void testFindById() {
			
		Asset assetFound = repo.findById(assetToFind.getUuid());
			
		assertThat(assetFound, notNullValue());
		assertTrue(assetFound.getUuid().equals(assetToFind.getUuid()));	
		assertTrue(assetFound.getName().equals(assetToFind.getName()));	
		assertTrue(assetFound.getDescription().equals(assetToFind.getDescription()));
		assertTrue(assetFound.getLocation().equals(assetToFind.getLocation()));
		assertTrue(assetFound.getThumbnailLocation().equals(assetToFind.getThumbnailLocation()));
	}
	
	@Test 
	public void testUpdate() {
	
		String new_name = "name updated";
		String new_description = "description updated";
		String new_location = "location updated";
		String new_thumbnail_location = "thumbnail location updated";
		
		// create the asset
		repo.insert(asset);
		
		//find the asset
		Asset assetToUpdate = repo.findById(asset.getUuid());
		assertThat(assetToUpdate, notNullValue());
		assertTrue(assetToUpdate.getUuid().equals(asset.getUuid()));	
		
	
		// update the asset
		
		assetToUpdate.setName(new_name);
		assetToUpdate.setDescription(new_description);
		assetToUpdate.setLocation(new_location);
		assetToUpdate.setThumbnailLocation(new_thumbnail_location);
		
		repo.update(assetToUpdate);
		
		Asset assetToCompare = repo.findById(assetToUpdate.getUuid());
			
		assertThat(assetToCompare, notNullValue());
		assertTrue(assetToCompare.getUuid().equals(assetToUpdate.getUuid()));		
		assertTrue(assetToCompare.getName().equals(assetToUpdate.getName()));	
		assertTrue(assetToCompare.getDescription().equals(assetToUpdate.getDescription()));
		assertTrue(assetToCompare.getLocation().equals(assetToUpdate.getLocation()));
		assertTrue(assetToCompare.getThumbnailLocation().equals(assetToUpdate.getThumbnailLocation()));
		
		// delete the asset
		
		repo.delete(asset.getUuid());
				
		//verify asset is deleted
		assetToUpdate = repo.findById(asset.getUuid());
		assertThat(assetToUpdate, nullValue());	
		
	}
	
	
}
