package testen;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
	private static final String GELDIGE_NAAM = "Ikea", GELDIGE_LOGO = "https://logodix.com/logo/470339.png", GELDIGE_SECTOR = "Home Furnishings", GELDIGE_REKENINGNUMMER = "BE16154215421625",
			GELDIGE_EMAIL = "matilda@outlook.com", GELDIGE_TELNR = "+462252869831", GELDIGE_BTW = "SW6544167324132";
	private static final Adres GELDIGE_ADRES = new Adres("Belgium","Brussels","1000","Kerkstraat","1");
	private static final List<Betaalmethode> GELDIGE_BETHAALMETHODES_LIJST = new ArrayList<>();
	private static final boolean IS_ACTIEF = true;
	
	@BeforeEach
	void before() {
		GELDIGE_BETHAALMETHODES_LIJST.clear();
		bedrijf = new Bedrijf(GELDIGE_NAAM, GELDIGE_LOGO, GELDIGE_SECTOR, GELDIGE_ADRES, GELDIGE_BETHAALMETHODES_LIJST,
				GELDIGE_REKENINGNUMMER, GELDIGE_EMAIL, GELDIGE_TELNR, GELDIGE_BTW, IS_ACTIEF);
	}
	
	@Test
	void maakBedrijf_geldigeWaarden_maaktBedrijf() {
		assertDoesNotThrow(() -> new Bedrijf(GELDIGE_NAAM, GELDIGE_LOGO, GELDIGE_SECTOR, GELDIGE_ADRES, GELDIGE_BETHAALMETHODES_LIJST,
				GELDIGE_REKENINGNUMMER, GELDIGE_EMAIL, GELDIGE_TELNR, GELDIGE_BTW, IS_ACTIEF));
		
	}
	
	static Stream<Arguments> ongeldigeWaarden() {
		return Stream.of(
				Arguments.of("", GELDIGE_LOGO, GELDIGE_SECTOR, GELDIGE_ADRES, GELDIGE_BETHAALMETHODES_LIJST, GELDIGE_REKENINGNUMMER, GELDIGE_EMAIL, GELDIGE_TELNR, GELDIGE_BTW, IS_ACTIEF),
				Arguments.of(null, GELDIGE_LOGO, GELDIGE_SECTOR, GELDIGE_ADRES, GELDIGE_BETHAALMETHODES_LIJST, GELDIGE_REKENINGNUMMER, GELDIGE_EMAIL, GELDIGE_TELNR, GELDIGE_BTW, IS_ACTIEF),
				Arguments.of(GELDIGE_NAAM, "", GELDIGE_SECTOR, GELDIGE_ADRES, GELDIGE_BETHAALMETHODES_LIJST, GELDIGE_REKENINGNUMMER, GELDIGE_EMAIL, GELDIGE_TELNR, GELDIGE_BTW, IS_ACTIEF),
				Arguments.of(GELDIGE_NAAM, null, GELDIGE_SECTOR, GELDIGE_ADRES, GELDIGE_BETHAALMETHODES_LIJST, GELDIGE_REKENINGNUMMER, GELDIGE_EMAIL, GELDIGE_TELNR, GELDIGE_BTW, IS_ACTIEF),
				Arguments.of(GELDIGE_NAAM, GELDIGE_LOGO, "", GELDIGE_ADRES, GELDIGE_BETHAALMETHODES_LIJST, GELDIGE_REKENINGNUMMER, GELDIGE_EMAIL, GELDIGE_TELNR, GELDIGE_BTW, IS_ACTIEF),
				Arguments.of(GELDIGE_NAAM, GELDIGE_LOGO, null, GELDIGE_ADRES, GELDIGE_BETHAALMETHODES_LIJST, GELDIGE_REKENINGNUMMER, GELDIGE_EMAIL, GELDIGE_TELNR, GELDIGE_BTW, IS_ACTIEF),
				Arguments.of(GELDIGE_NAAM, GELDIGE_LOGO, GELDIGE_SECTOR, null, GELDIGE_BETHAALMETHODES_LIJST, GELDIGE_REKENINGNUMMER, GELDIGE_EMAIL, GELDIGE_TELNR, GELDIGE_BTW, IS_ACTIEF),
				Arguments.of(GELDIGE_NAAM, GELDIGE_LOGO, GELDIGE_SECTOR, GELDIGE_ADRES, null, GELDIGE_REKENINGNUMMER, GELDIGE_EMAIL, GELDIGE_TELNR, GELDIGE_BTW, IS_ACTIEF),
				Arguments.of(GELDIGE_NAAM, GELDIGE_LOGO, GELDIGE_SECTOR, GELDIGE_ADRES, GELDIGE_BETHAALMETHODES_LIJST, "", GELDIGE_EMAIL, GELDIGE_TELNR, GELDIGE_BTW, IS_ACTIEF),
				Arguments.of(GELDIGE_NAAM, GELDIGE_LOGO, GELDIGE_SECTOR, GELDIGE_ADRES, GELDIGE_BETHAALMETHODES_LIJST, null, GELDIGE_EMAIL, GELDIGE_TELNR, GELDIGE_BTW, IS_ACTIEF),
				Arguments.of(GELDIGE_NAAM, GELDIGE_LOGO, GELDIGE_SECTOR, GELDIGE_ADRES, GELDIGE_BETHAALMETHODES_LIJST, GELDIGE_REKENINGNUMMER, "", GELDIGE_TELNR, GELDIGE_BTW, IS_ACTIEF),
				Arguments.of(GELDIGE_NAAM, GELDIGE_LOGO, GELDIGE_SECTOR, GELDIGE_ADRES, GELDIGE_BETHAALMETHODES_LIJST, GELDIGE_REKENINGNUMMER, null, GELDIGE_TELNR, GELDIGE_BTW, IS_ACTIEF),
				Arguments.of(GELDIGE_NAAM, GELDIGE_LOGO, GELDIGE_SECTOR, GELDIGE_ADRES, GELDIGE_BETHAALMETHODES_LIJST, GELDIGE_REKENINGNUMMER, GELDIGE_EMAIL, "", GELDIGE_BTW, IS_ACTIEF),
				Arguments.of(GELDIGE_NAAM, GELDIGE_LOGO, GELDIGE_SECTOR, GELDIGE_ADRES, GELDIGE_BETHAALMETHODES_LIJST, GELDIGE_REKENINGNUMMER, GELDIGE_EMAIL, null, GELDIGE_BTW, IS_ACTIEF),
				Arguments.of(GELDIGE_NAAM, GELDIGE_LOGO, GELDIGE_SECTOR, GELDIGE_ADRES, GELDIGE_BETHAALMETHODES_LIJST, GELDIGE_REKENINGNUMMER, GELDIGE_EMAIL, GELDIGE_TELNR, "", IS_ACTIEF),
				Arguments.of(GELDIGE_NAAM, GELDIGE_LOGO, GELDIGE_SECTOR, GELDIGE_ADRES, GELDIGE_BETHAALMETHODES_LIJST, GELDIGE_REKENINGNUMMER, GELDIGE_EMAIL, GELDIGE_TELNR, null, IS_ACTIEF));
	}
	
	@ParameterizedTest
	@MethodSource("ongeldigeWaarden")
	void maakBedrijf_ongeldigeWaarden_throwsException(String naam, String logo, String sector, Adres adres, List<Betaalmethode> betaalmethodes,
			String rekeningnummer, String email, String telefoon, String btwNr, boolean isActief) {
		assertThrows(IllegalArgumentException.class, () -> new Bedrijf(naam, logo, sector, adres, betaalmethodes, rekeningnummer, email, telefoon, btwNr, isActief));
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = {"123abc","!@#$%","test$naam","test_naam", "testnaam*"})
	void maakBedrijf_ongeldigeNaam_throwsException(String ongeldigeNaam) {
		assertThrows(IllegalArgumentException.class, () -> new Bedrijf(ongeldigeNaam, GELDIGE_LOGO, GELDIGE_SECTOR, GELDIGE_ADRES, GELDIGE_BETHAALMETHODES_LIJST,
				GELDIGE_REKENINGNUMMER, GELDIGE_EMAIL, GELDIGE_TELNR, GELDIGE_BTW, IS_ACTIEF));
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = {"user@domain","user@domain.","@domain.com","user@.com","user.domain@com"})
	void maakBedrijf_ongeldigeEmail_throwsException(String ongeldigeEmail) {
		assertThrows(IllegalArgumentException.class, () -> new Bedrijf(GELDIGE_NAAM, GELDIGE_LOGO, GELDIGE_SECTOR, GELDIGE_ADRES, GELDIGE_BETHAALMETHODES_LIJST,
				GELDIGE_REKENINGNUMMER, ongeldigeEmail, GELDIGE_TELNR, GELDIGE_BTW, IS_ACTIEF));
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = {/*"+1234567890",*/"(123) 456-789","123-456-7890 ext. 1234","123.45.6789","1234567","123-456-789"})
	void maakBedrijf_ongeldigeTelnr_throwsException(String ongeldigeTelNr) {
		assertThrows(IllegalArgumentException.class, () -> new Bedrijf(GELDIGE_NAAM, GELDIGE_LOGO, GELDIGE_SECTOR, GELDIGE_ADRES, GELDIGE_BETHAALMETHODES_LIJST,
				GELDIGE_REKENINGNUMMER, GELDIGE_EMAIL, ongeldigeTelNr, GELDIGE_BTW, IS_ACTIEF));
	}
	
	@Test
	void addBetaalMethodes_duplicateMethode_doesNothing() {
		bedrijf.addBetaalmethodes(Betaalmethode.APPLE_PAY);
		bedrijf.addBetaalmethodes(Betaalmethode.APPLE_PAY);
		assertEquals(1, bedrijf.getBetaalmethodes().size());
	}
	
	@Test
	void addBetaalMethodes_null_throwsException() {
		assertThrows(IllegalArgumentException.class, () -> bedrijf.addBetaalmethodes(null));
	}
	
	@Test
	void addBetaalMethodes_betaalMethodesAdded() {
		bedrijf.addBetaalmethodes(Betaalmethode.values()[0]);
		bedrijf.addBetaalmethodes(Betaalmethode.values()[1]);
		bedrijf.addBetaalmethodes(Betaalmethode.values()[2]);
		assertEquals(3, bedrijf.getBetaalmethodes().size());
	}

}