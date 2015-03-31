package de.sonnenfeldt.lavisgrafix.controller.test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;
import org.springframework.ui.ExtendedModelMap;

import de.sonnenfeldt.lavisgrafix.controller.AssetController;
import de.sonnenfeldt.lavisgrafix.dao.AssetRepository;
import de.sonnenfeldt.lavisgrafix.dao.JdbcAssetRepository;
import de.sonnenfeldt.lavisgrafix.helper.AssetHelper;
import de.sonnenfeldt.lavisgrafix.model.Asset;


public class AssetControllerTest {

	private AssetRepository repo;
	private AssetController tested;
	private List<Asset>assetList;
	private Random randomGenerator = new Random();	
	private Asset asset;
	private Asset assetToFind;	


	
	@Before
	public void setup() {
		
		repo = mock(JdbcAssetRepository.class);
		tested = new AssetController(repo);
		assetList = AssetHelper.getAssets();
		
		int m = randomGenerator.nextInt(assetList.size());
		int n = randomGenerator.nextInt(assetList.size());
		
		if (m == n) {
			m = (assetList.size() < m + 1) ? m-- : m++;
		}
		
		asset = assetList.get(m);
		assetToFind = assetList.get(n);
		
	}	
	
	
	@Test
	public void testGetAllAssetsFromModel() {
		ExtendedModelMap model = new ExtendedModelMap();
		String view = tested.getAllAssets(model);
		assertThat(view, CoreMatchers.equalTo(AssetController.MAIN_VIEW));
		assertThat(model.get("assets"), notNullValue());
		verify(repo).getAll();		
	}
	
	@Test
	public void testGetAllAssets() {
		List<Asset> all = tested.getAllAssets();
		assertThat(all, notNullValue());
		verify(repo).getAll();				
	}
	
	@Test
	public void testGetAsset() {
		final Asset final_asset = assetToFind;
		
		tested.getAsset(assetToFind.getUuid());

		verify(repo).findById(Mockito.argThat(new ArgumentMatcher<String>() {

			@Override
			public boolean matches(Object argument) {
				if (!(argument instanceof String)) {
			        return false;
			      }				
				
				String uuid = (String) argument;				
				return uuid.equals(final_asset.getUuid());
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("expected String with uuid");
			}
				
		}));
	}
	
	@Test
	public void testCreateAsset() {
		HttpServletRequest request = mock(HttpServletRequest.class);       
		HttpServletResponse response = mock(HttpServletResponse.class);    	
		
		final Asset final_asset = asset;
		tested.createAsset(asset, request, response);
		verify(repo).insert(Mockito.argThat(new ArgumentMatcher<Asset>() {

			@Override
			public boolean matches(Object argument) {
				if (!(argument instanceof Asset)) {
			        return false;
			      }								
				
				boolean matched = true;
				Asset assetInserted = (Asset) argument;
				matched = matched && assetInserted.getUuid().equals(final_asset.getUuid());				
				matched = matched && assetInserted.getName().equals(final_asset.getName());
				matched = matched && assetInserted.getDescription().equals(final_asset.getDescription());
				matched = matched && assetInserted.getLocation().equals(final_asset.getLocation());
				matched = matched && assetInserted.getThumbnailLocation().equals(final_asset.getThumbnailLocation());
				return matched;
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("expected asset with new values");
			}
		}));	
		
		repo.delete(asset.getUuid());
		
	}
	
	@Test
	public void testUpdateAsset() {
				
		tested.updateAsset(asset.getUuid(),asset);
		
		// verification can only be done against findById, as the mock of findById does not return the asset for update
		verify(repo).findById(Mockito.argThat(new ArgumentMatcher<String>() {

			@Override
			public boolean matches(Object argument) {
				if (!(argument instanceof String)) {
			        return false;
			      }				
				
				String uuid = (String) argument;				
				return uuid.equals(asset.getUuid());
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("expected String with uuid");
			}
				
		}));				
					
	}
	
	@Test
	public void testDeleteAsset()	{
		
		tested.deleteAsset(asset.getUuid());
		
		verify(repo).delete(Mockito.argThat(new ArgumentMatcher<String>() {

			@Override
			public boolean matches(Object argument) {
				if (!(argument instanceof String)) {
			        return false;
			      }				
				
				String uuid = (String) argument;				
				return uuid.equals(asset.getUuid());
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("expected String with uuid");
			}
				
		}));
		
	}

}
