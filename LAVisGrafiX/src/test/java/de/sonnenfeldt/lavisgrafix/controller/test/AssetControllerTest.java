package de.sonnenfeldt.lavisgrafix.controller.test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;






import java.util.List;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ui.ExtendedModelMap;

import de.sonnenfeldt.lavisgrafix.controller.AssetController;
import de.sonnenfeldt.lavisgrafix.dao.AssetRepository;
import de.sonnenfeldt.lavisgrafix.dao.JdbcAssetRepository;
import de.sonnenfeldt.lavisgrafix.model.Asset;


public class AssetControllerTest {

	private AssetRepository repo;
	private AssetController tested;
	
	@Before
	public void setup() {
		
		repo = mock(JdbcAssetRepository.class);
		tested = new AssetController(repo);
	}
	

	@Test
	public void testCreate() {	Asset asset = new Asset();
	asset.setName("Wooden table");
	asset.setDescription("Wooden table made from oak tree");
	asset.setLocation("http://www.sonnenfeldt.de/picture/table.jpg");
	tested.create(asset);
	Mockito.verify(repo).insert(
				Mockito.argThat(new ArgumentMatcher<Asset>() {

					@Override
					public boolean matches(Object argument) {
						return ((Asset) argument).getName().equals("Wooden table");
					}

					@Override
					public void describeTo(Description description) {
						description.appendText("expected: Wooden table");
					}
				}));		
		
	}
	
	@Test
	public void testDelete() {
		
	}
	
	@Test
	public void testUpdate() {
		
	}
	
	@Test
	public void testGetbyId() {
		
	}
	
	@Test
	public void testGetAll() {
		List<Asset> all = tested.getAllAssets();
		assertThat(all, notNullValue());
		verify(repo).getAll();		
	}
}
