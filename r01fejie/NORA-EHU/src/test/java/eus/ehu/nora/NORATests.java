package eus.ehu.nora;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import org.junit.Assert;
import com.google.common.collect.Iterables;

import lombok.extern.slf4j.Slf4j;
import r01f.ejie.nora.NORAGeoIDs;
import r01f.ejie.nora.NORAService;
import r01f.ejie.nora.NORAServiceConfig;
import r01f.types.geo.GeoCountry;
import r01f.types.geo.GeoCounty;
import r01f.types.geo.GeoDistrict;
import r01f.types.geo.GeoMunicipality;
import r01f.types.geo.GeoNeighborhood;
import r01f.types.geo.GeoOIDs.GeoMunicipalityID;
import r01f.types.geo.GeoOIDs.GeoRegionID;
import r01f.types.geo.GeoOIDs.GeoStateID;
import r01f.types.geo.GeoRegion;
import r01f.types.geo.GeoState;
import r01f.types.url.Url;
import r01f.util.types.collections.CollectionUtils;

/**
 * Test the NORA WSLDL: http://svc.inter.integracion.jakina.ejiedes.net/ctxapp/t17iApiWS?wsdl
 * 
 * <pre>
 * Territory											Europe
 *   |_Country											Spain										
 *   	 |_State										Euskadi
 *   		 |_County									Bizkaia
 *   		 	|_Region								Gran Bilbao / valles alaveses
 *   				|_Municipality						Bilbao
 *   					|_District						01	
 *   						|_Neighborhood 				Abando
 *   							|_Street				General Concha
 *   								|_portal			12
 * </pre>
 */
@Slf4j
public class NORATests {
/////////////////////////////////////////////////////////////////////////////////////////
//
/////////////////////////////////////////////////////////////////////////////////////////
//	private static final NORAServiceConfig cfg = new NORAServiceConfig(Url.from("http://svc.inter.integracion.jakina.ejiedes.net/ctxapp/t17iApiWS"));
	
	private static final NORAServiceConfig cfg = new NORAServiceConfig(Url.from("http://svc.integracion.euskadi.net/ctxweb/t17iApiWS"));
	

/////////////////////////////////////////////////////////////////////////////////////////
//  COUNTRIES
/////////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void testCountries() {
		NORAService nora = new NORAService(cfg);
		
		log.info("[Countries]------------------------------------------------------------");

		// Load all countries
		log.info("Find all countries");
		Collection<GeoCountry> countries = nora.getServicesForCountries()
											   .getCountries();
		Assert.assertTrue(CollectionUtils.hasData(countries));
		for (GeoCountry country : countries) {
			log.info("\t-{}",country.debugInfo());
		}
		
		// find countries by text
		log.info("Find countries by text: {}","nia");
		Collection<GeoCountry> countriesWithText = nora.getServicesForCountries()
													   .findCountriesByText("nia");	// albania, alemania, rumania...
		Assert.assertTrue(CollectionUtils.hasData(countries));
		for (GeoCountry countryWithText : countriesWithText) {
			log.info("\t-{}",countryWithText.debugInfo());
		}
		
		// load a single country
		log.info("Find country by id={}",Iterables.getFirst(countries,null).getId());
		GeoCountry country = nora.getServicesForCountries()
								 .getCountry(Iterables.getFirst(countries,null).getId());
		Assert.assertNotNull(country);
		log.info("\t-{}",country.debugInfo());
	}
/////////////////////////////////////////////////////////////////////////////////////////
//  STATES
/////////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void testStates() {
		NORAService nora = new NORAService(cfg);
		
		log.info("[States]---------------------------------------------------------------");

		// Load all states
		log.info("Find all states");
		Collection<GeoState> states = nora.getServicesForStates()
										  .getStates();
		Assert.assertTrue(CollectionUtils.hasData(states));
		for (GeoState state : states) {
			log.info("\t-{}",state.debugInfo());
		}
		
		// find states by text
		log.info("Find states with text={}","euta");
		Collection<GeoState> statesWithText = nora.getServicesForStates()
												  .findStatesWithText("euta");	// ceuta
		Assert.assertTrue(CollectionUtils.hasData(statesWithText));
		for (GeoState stateWithText : statesWithText) {
			log.info("\t-{}",stateWithText.debugInfo());
		}
		
		// load a single state
		log.info("Find state by id={}",Iterables.getFirst(states,null).getId());
		GeoState state = nora.getServicesForStates()
							 .getState(Iterables.getFirst(states,null).getId());
		Assert.assertNotNull(state);
		log.info("\t-{}",state.debugInfo());
	}
/////////////////////////////////////////////////////////////////////////////////////////
//  COUNTIES
/////////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void testCounties() {
		NORAService nora = new NORAService(cfg);
		
		log.info("[Counties]-------------------------------------------------------------");

		// Load all counties
		log.info("Find all counties");
		Collection<GeoCounty> counties = nora.getServicesForCounties()
											 .getCounties();
		Assert.assertTrue(CollectionUtils.hasData(counties));
		for (GeoCounty county : counties) {
			log.info("\t-{}",county.debugInfo());
		}

		// load counties of a state
		log.info("Find counties of state={}",GeoStateID.forId(18));
		Collection<GeoCounty> countiesOf = nora.getServicesForCounties()
											   .getCountiesOf(GeoStateID.forId(18));	// ceuta & melilla
		Assert.assertTrue(CollectionUtils.hasData(countiesOf));
		for (GeoCounty countyOf : countiesOf) {
			log.info("\t-{}",countyOf.debugInfo());
		}
		// load counties of a state with text
		log.info("Find counties with text={}","ceu");
		Collection<GeoCounty> countiesOfWithText = nora.getServicesForCounties()
													   .findCountiesWithTextOf(GeoStateID.forId(18),
																			  "ceu");	// ceuta
		Assert.assertTrue(CollectionUtils.hasData(countiesOfWithText));
		for (GeoCounty countyWithTextOf : countiesOfWithText) {
			log.info("\t-{}",countyWithTextOf.debugInfo());
		}
		
		// load a single county
		log.info("Find single county: {}/{}",NORAGeoIDs.EUSKADI,NORAGeoIDs.ARABA);
		GeoCounty county = nora.getServicesForCounties()
							   .getCounty(NORAGeoIDs.EUSKADI,NORAGeoIDs.ARABA);
		Assert.assertNotNull(county);
		log.info("\t-{}",county.debugInfo());
	}
/////////////////////////////////////////////////////////////////////////////////////////
//  REGIONS
/////////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void testRegions() {
		NORAService nora = new NORAService(cfg);
		
		log.info("[Regions]--------------------------------------------------------------");

		// Load all regions
		log.info("Find all regions");
		Collection<GeoRegion> regions = nora.getServicesForRegions()
											.getRegions();
		Assert.assertTrue(CollectionUtils.hasData(regions));
		for (GeoRegion region : regions) {
			log.info("\t-{}",region.debugInfo());
		}

		// load regions of a state
		log.info("Find regions of state {}/{}",NORAGeoIDs.EUSKADI,NORAGeoIDs.ARABA);
		Collection<GeoRegion> regionsOf = nora.getServicesForRegions()
											  .getRegionsOf(NORAGeoIDs.EUSKADI,	// euskadi
															NORAGeoIDs.ARABA);	// araba
		Assert.assertTrue(CollectionUtils.hasData(regionsOf));
		for (GeoRegion regionOf : regionsOf) {
			log.info("\t-{}",regionOf.debugInfo());
		}
		
		// load regions of a state with text
		log.info("Find regions with text={}","valle");
		Collection<GeoRegion> regionsOfWithText = nora.getServicesForRegions()
													  .findRegionsWithTextOf(NORAGeoIDs.EUSKADI,	// euskadi
																			 NORAGeoIDs.ARABA,		// araba
																			 "valle");				// valles alaveses
		Assert.assertTrue(CollectionUtils.hasData(regionsOfWithText));
		for (GeoRegion regionWithTextOf : regionsOfWithText) {
			log.info("\t-{}",regionWithTextOf.debugInfo());
		}
		
		// load a single region
		log.info("Find region {}/{}/{}",NORAGeoIDs.EUSKADI,NORAGeoIDs.ARABA,GeoRegionID.forId(1));
		GeoRegion region = nora.getServicesForRegions()
							   .getRegion(NORAGeoIDs.EUSKADI,
										  NORAGeoIDs.ARABA,
										  GeoRegionID.forId(1));	// valles alaveses
		Assert.assertNotNull(region);
		log.info("\t-{}",region.debugInfo());
	}
/////////////////////////////////////////////////////////////////////////////////////////
//  MUNICIPALITIES
/////////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void testMunicipalities() {
		NORAService nora = new NORAService(cfg);
		
		log.info("[Municipalities]-------------------------------------------------------");

		// Load all municipalities
		log.info("Fina all municipalities");
		Collection<GeoMunicipality> muns = nora.getServicesForMunicipalities()
											   .getMunicipalities();
		Assert.assertTrue(CollectionUtils.hasData(muns));
		for (GeoMunicipality mun : muns) {
			log.info("\t-{}",mun.debugInfo());
		}

		// load muns of a state
		log.info("Find municipalities of state={}/{}",NORAGeoIDs.EUSKADI,	// euskadi
													  NORAGeoIDs.ARABA);	// araba
		Collection<GeoMunicipality> munsOf = nora.getServicesForMunicipalities()
												  .getMunicipalitiesOf(NORAGeoIDs.EUSKADI,	// euskadi
																	   NORAGeoIDs.ARABA);	// araba
		Assert.assertTrue(CollectionUtils.hasData(munsOf));
		for (GeoMunicipality munOf : munsOf) {
			log.info("\t-{}",munOf.debugInfo());
		}
		
		// load muns of a state with text
		log.info("Find municipalities with text={}","vit");
		Collection<GeoMunicipality> munsOfWithText = nora.getServicesForMunicipalities()
														  .findMunicipalitiesWithTextOf(NORAGeoIDs.EUSKADI,	// euskadi
																						NORAGeoIDs.ARABA,	// araba
																						null,				// no region
																						"vit");			// gasteiz
		Assert.assertTrue(CollectionUtils.hasData(munsOfWithText));
		for (GeoMunicipality munWithTextOf : munsOfWithText) {
			log.info("\t-{}",munWithTextOf.debugInfo());
		}
		
		// load a single mun
		log.info("Find single municipality: {}/{}/{}",NORAGeoIDs.EUSKADI,
													  NORAGeoIDs.ARABA,
													  GeoMunicipalityID.forId(59));
		GeoMunicipality mun = nora.getServicesForMunicipalities()
									 .getMunicipality(NORAGeoIDs.EUSKADI,
													  NORAGeoIDs.ARABA,
													  GeoMunicipalityID.forId(59));	// gasteiz
		Assert.assertNotNull(mun);
		log.info("\t-{}",mun.debugInfo());
	}
/////////////////////////////////////////////////////////////////////////////////////////
//  DISTRICTS
/////////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void testDistricts() {
		NORAService nora = new NORAService(cfg);
		
		log.info("[Districts]-------------------------------------------------------------");

		// Find all districts
		log.info("Find all districts");
		Collection<GeoDistrict> districts = nora.getServicesForDistricts()
												.getDistricts();
		Assert.assertTrue(CollectionUtils.hasData(districts));
		for (GeoDistrict district : districts) {
			log.info("\t-{}",district.debugInfo());
		}
		
		// Find districts of
		log.info("Find districts of state/county={}/{}",NORAGeoIDs.EUSKADI,
													  	NORAGeoIDs.ARABA);
		Collection<GeoDistrict> districtsOf = nora.getServicesForDistricts()
													.getDistrictsOf(NORAGeoIDs.EUSKADI,
																	NORAGeoIDs.ARABA);
		Assert.assertTrue(CollectionUtils.hasData(districtsOf));
		for (GeoDistrict district : districtsOf) {
			log.info("\t-{}",district.debugInfo());
		}
		
		// load a single neighborhoods
		log.info("Load district {}/{}/{}",Iterables.getFirst(districts,null).getId());
		GeoDistrict district = nora.getServicesForDistricts()
										 .getDistrict(Iterables.getFirst(districts,null).getId());
		Assert.assertNotNull(district);
		log.info("\t-{}",district.debugInfo());
	}
/////////////////////////////////////////////////////////////////////////////////////////
//  NEIGHBORHOODS
/////////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void testNeighborhoods() {
		NORAService nora = new NORAService(cfg);
		
		log.info("[Neighborhoods]-------------------------------------------------------------");

		// Load all neighborhoods
		log.info("Find all neighborhoods");
		Collection<GeoNeighborhood> neighborhoods = nora.getServicesForNeighborhoods()
														.getNeighborhoods();
		Assert.assertTrue(CollectionUtils.hasData(neighborhoods));
		for (GeoNeighborhood neighborhood : neighborhoods) {
			log.info("\t-{}",neighborhood.debugInfo());
		}
		
		// load neighborhoods in state
		log.info("Find neighborhood of state/county={}/{}",NORAGeoIDs.EUSKADI,
														   NORAGeoIDs.BIZKAIA);
		Collection<GeoNeighborhood> neighborhoodsOf = nora.getServicesForNeighborhoods()
																.getNeighborhoodsOf(NORAGeoIDs.EUSKADI,
																					NORAGeoIDs.BIZKAIA);
		Assert.assertTrue(CollectionUtils.hasData(neighborhoodsOf));
		for (GeoNeighborhood neighborhoodOf : neighborhoodsOf) {
			log.info("\t-{}",neighborhoodOf.debugInfo());
		}

		// load neighborhoods with text
		log.info("Find neighborhood with text={}","peñ");
		Collection<GeoNeighborhood> neighborhoodsWithText = nora.getServicesForNeighborhoods()
																.findNeighborhoodsByText("peñ");
		Assert.assertTrue(CollectionUtils.hasData(neighborhoodsWithText));
		for (GeoNeighborhood neighborhoodWithTextOf : neighborhoodsWithText) {
			log.info("\t-{}",neighborhoodWithTextOf.debugInfo());
		}
		
		// load a single neighborhoods
		log.info("Load neighborhood {}/{}/{}",NORAGeoIDs.BIZKAIA,
											  NORAGeoIDs.BILBAO,
											  189);					// la peña
		GeoNeighborhood neighorhood = nora.getServicesForNeighborhoods()
											 .getNeighborhood(NORAGeoIDs.BIZKAIA,
															  NORAGeoIDs.BILBAO,
															  189);				// la peña
		Assert.assertNotNull(neighorhood);
		log.info("\t-{}",neighorhood.debugInfo());
	}
}

