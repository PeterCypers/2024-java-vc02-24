package testen;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import domein.Adres;
import domein.Bedrijf;
import domein.Betaalmethode;

public class BedrijfTest {
	
	private Bedrijf bedrijf;
	private static final String GELDIGE_NAAM = "Ikea", GELDIGE_LOGO = "https://logodix.com/logo/470339.png", GELDIGE_SECTOR = "Home Furnishings", GELDIGE_BETALINGSINFO = "" /*TODO: dit mag op het moment nog leeg zijn*/,
			GELDIGE_EMAIL = "matilda@outlook.com", GELDIGE_TELNR = "+462252869831", GELDIGE_BTW = "SW6544167324132";
	private static final Adres GELDIGE_ADRES = new Adres("Belgium","Brussels","1000","Kerkstraat","1");
	private static final List<Betaalmethode> GELDIGE_BETHAALMETHODES_LIJST = new ArrayList<>();
	private static final boolean IS_ACTIEF = true;
	
	@BeforeEach
	void before() {
		bedrijf = new Bedrijf(GELDIGE_NAAM, GELDIGE_LOGO, GELDIGE_SECTOR, GELDIGE_ADRES, GELDIGE_BETHAALMETHODES_LIJST,
				GELDIGE_BETALINGSINFO, GELDIGE_EMAIL, GELDIGE_TELNR, GELDIGE_BTW, IS_ACTIEF);
	}
	
	@Test
	void maakBedrijf_geldigeWaarden_maaktBedrijf() {
		assertDoesNotThrow(() -> new Bedrijf(GELDIGE_NAAM, GELDIGE_LOGO, GELDIGE_SECTOR, GELDIGE_ADRES, GELDIGE_BETHAALMETHODES_LIJST,
				GELDIGE_BETALINGSINFO, GELDIGE_EMAIL, GELDIGE_TELNR, GELDIGE_BTW, IS_ACTIEF));
		
	}
	/**
	 * TODO: GELDIGE_BETHAALMETHODES_LIJST,  GELDIGE_BETALINGSINFO
	 * no strong checks on these incoming params yet -> check why
	 * 
	 */
	static Stream<Arguments> ongeldigeWaarden() {
		return Stream.of(
				Arguments.of(GELDIGE_NAAM, "", GELDIGE_SECTOR, GELDIGE_ADRES, GELDIGE_BETHAALMETHODES_LIJST,GELDIGE_BETALINGSINFO, GELDIGE_EMAIL, GELDIGE_TELNR, GELDIGE_BTW, IS_ACTIEF),
				Arguments.of(GELDIGE_NAAM, GELDIGE_LOGO, "", GELDIGE_ADRES, GELDIGE_BETHAALMETHODES_LIJST,GELDIGE_BETALINGSINFO, GELDIGE_EMAIL, GELDIGE_TELNR, GELDIGE_BTW, IS_ACTIEF),
				Arguments.of(GELDIGE_NAAM, GELDIGE_LOGO, GELDIGE_SECTOR, null, GELDIGE_BETHAALMETHODES_LIJST,GELDIGE_BETALINGSINFO, GELDIGE_EMAIL, GELDIGE_TELNR, GELDIGE_BTW, IS_ACTIEF),
				Arguments.of(GELDIGE_NAAM, GELDIGE_LOGO, "", GELDIGE_ADRES, GELDIGE_BETHAALMETHODES_LIJST,GELDIGE_BETALINGSINFO, GELDIGE_EMAIL, GELDIGE_TELNR, GELDIGE_BTW, IS_ACTIEF),
				Arguments.of(GELDIGE_NAAM, GELDIGE_LOGO, GELDIGE_SECTOR, GELDIGE_ADRES, GELDIGE_BETHAALMETHODES_LIJST,GELDIGE_BETALINGSINFO, GELDIGE_EMAIL, GELDIGE_TELNR, null, IS_ACTIEF),
				Arguments.of(GELDIGE_NAAM, "", GELDIGE_SECTOR, GELDIGE_ADRES, GELDIGE_BETHAALMETHODES_LIJST,GELDIGE_BETALINGSINFO, GELDIGE_EMAIL, GELDIGE_TELNR, GELDIGE_BTW, IS_ACTIEF),
				Arguments.of(GELDIGE_NAAM, GELDIGE_LOGO, null, GELDIGE_ADRES, GELDIGE_BETHAALMETHODES_LIJST,GELDIGE_BETALINGSINFO, GELDIGE_EMAIL, GELDIGE_TELNR, GELDIGE_BTW, IS_ACTIEF),
				Arguments.of("", GELDIGE_LOGO, GELDIGE_SECTOR, GELDIGE_ADRES, GELDIGE_BETHAALMETHODES_LIJST,GELDIGE_BETALINGSINFO, GELDIGE_EMAIL, GELDIGE_TELNR, GELDIGE_BTW, IS_ACTIEF));
	}
	
	@ParameterizedTest
	@MethodSource("ongeldigeWaarden")
	void maakBedrijf_ongeldigeWaarden_throwsException(String naam, String logo, String sector, Adres adres, List<Betaalmethode> betaalmethodes,
			String betalingsInfo, String email, String telefoon, String btwNr, boolean isActief) {
		assertThrows(IllegalArgumentException.class, () -> new Bedrijf(naam, logo, sector, adres, betaalmethodes, betalingsInfo, email, telefoon, btwNr, isActief));
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = {"123abc","!@#$%","test$naam","test_naam", "testnaam*"})
	void maakBedrijf_ongeldigeNaam_throwsException(String ongeldigeNaam) {
		assertThrows(IllegalArgumentException.class, () -> new Bedrijf(ongeldigeNaam, GELDIGE_LOGO, GELDIGE_SECTOR, GELDIGE_ADRES, GELDIGE_BETHAALMETHODES_LIJST,
				GELDIGE_BETALINGSINFO, GELDIGE_EMAIL, GELDIGE_TELNR, GELDIGE_BTW, IS_ACTIEF));
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = {"user@domain","user@domain.","@domain.com","user@.com","user.domain@com"})
	void maakBedrijf_ongeldigeEmail_throwsException(String ongeldigeEmail) {
		assertThrows(IllegalArgumentException.class, () -> new Bedrijf(GELDIGE_NAAM, GELDIGE_LOGO, GELDIGE_SECTOR, GELDIGE_ADRES, GELDIGE_BETHAALMETHODES_LIJST,
				GELDIGE_BETALINGSINFO, ongeldigeEmail, GELDIGE_TELNR, GELDIGE_BTW, IS_ACTIEF));
	}
	
	//TODO: check 1st pattern: is it allowed to pass?
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = {/*"+1234567890",*/"(123) 456-789","123-456-7890 ext. 1234","123.45.6789","1234567","123-456-789"})
	void maakBedrijf_ongeldigeTelnr_throwsException(String ongeldigeTelNr) {
		assertThrows(IllegalArgumentException.class, () -> new Bedrijf(GELDIGE_NAAM, GELDIGE_LOGO, GELDIGE_SECTOR, GELDIGE_ADRES, GELDIGE_BETHAALMETHODES_LIJST,
				GELDIGE_BETALINGSINFO, GELDIGE_EMAIL, ongeldigeTelNr, GELDIGE_BTW, IS_ACTIEF));
	}

}