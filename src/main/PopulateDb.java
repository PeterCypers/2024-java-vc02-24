package main;

import java.util.Arrays;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import domein.*;
import service.gebruiker.GebruikerService;
import service.gebruiker.GebruikerServiceDbImpl;
import service.bedrijf.BedrijfService;
import service.bedrijf.BedrijfServiceDbImpl;

public class PopulateDb {
	
	public static void main(String[] args) {
		vulDatabase();
    }
	
	private static void vulDatabase() {
		List<Bedrijf> bedrijfData = maakBedrijfData();
		List<Gebruiker> adminData = maakAdministratorData();
		
		bedrijfData.forEach((b) -> {
			// Controle op data
			System.out.println(b.getNaam());
			System.out.println("- " + b.getKlant().getNaam() + "- " + b.getKlantGebruiker().getNaam() + "- " + b.getLeverancierGebruiker().getNaam());
			b.getLeverancierGebruiker().getBestellingen().forEach(bes -> {
				System.out.println(" -> " + bes.getOrderId());
			});
			
			if (b.getKlant().getBestellingen() == null) {
				System.out.println(" <- Geen bestellinglijst");
			} else {
				b.getKlant().getBestellingen().forEach(bes -> {
					System.out.println(" <- " + bes.getOrderId());
				});
			}
		});
		
		BedrijfService bs = new BedrijfServiceDbImpl();
		GebruikerService gs = new GebruikerServiceDbImpl();
		
		long startTime = System.currentTimeMillis();
		
		System.out.println("Seeding van de database is bezig...");
		try {
			bedrijfData.forEach(b -> bs.voegBedrijfToe(b));
			adminData.forEach(a -> gs.voegGebruikerToe(a));
			
			float elapsedTime = (System.currentTimeMillis() - startTime) / 1000F;
			System.out.printf("Seeding werd succesvol afgerond (%.2f seconden)", elapsedTime);
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	private static List<Gebruiker> maakAdministratorData() {
		return List.of(
				new Gebruiker(
					Rol.ADMINISTRATOR,
					"joris@outlook.com",
					"1234",
					"Joris",
					true,
					new Adres("Belgium", "Ghent", "9000", "Sint-Pietersplein", "59A")
				)
		);
	}
	
	private static List<Bedrijf> maakBedrijfData() {
		Adres[] adressen = {
				new Adres("Belgium", 		"Ghent", 	 "9000",  	"Sint-Pietersplein", 		"59A"),
				new Adres("Belgium", 		"Brussels",  "1000",  	"Kerkstraat",	 			"1"),
				new Adres("United States",	"New York",  "10001", 	"Broadway",	 				"20"),
				new Adres("Germany", 		"Frankfurt", "60311", 	"Bendergasse",				"3"),
				new Adres("Netherlands", 	"Rotterdam", "3011",  	"Wolfshoek",	 			"7"),
				new Adres("France", 		"Toulouse",  "31000", 	"Rte de Narbonne", 			"209"),
				new Adres("Sweden",			"Stockholm", "111 53", 	"Svartensgatan",			"102"),
				new Adres("Italy",			"Milan",	 "20121",	"Piazza Luigi di Savoia",   "24"),
		};
		
		List<Bedrijf> bedrijven = List.of(
				new Bedrijf("Stella Artois", "https://logodix.com/logo/2066282.png", "Brewers",
						adressen[1], "TODO", "mark@outlook.be", "+32974178174", "BE197248342B38", true),
				new Bedrijf("Hewlett-Packard", "https://logodix.com/logo/4934.png", "Technology Hardware, Storage & Peripherals",
						adressen[2], "TODO", "mike@gmail.com", "(212)912-0384", "749196976", true),
				new Bedrijf("Bosch", "https://logodix.com/logo/9541.png", "Household Appliances",
						adressen[3], "TODO", "julia@web.de", "+496591799946", "DE41280756350", true),
				new Bedrijf("Ahold Delhaize", "https://logodix.com/logo/420832.png", "Food Retail",
						adressen[4], "TODO", "kim@gmail.com", "+31659267802", "NL463774784B52", true),
				new Bedrijf("Peugeot", "https://logodix.com/logo/9436.png", "Automobile Manufacturers",
						adressen[5], "TODO", "christophe@outlook.com", "+33124311738", "FR4579135426784", true),
				new Bedrijf("Ikea", "https://logodix.com/logo/470339.png", "Home Furnishings",
						adressen[6], "TODO", "matilda@outlook.com", "+462252869831", "SW6544167324132", true),
				new Bedrijf("Giorgio Armani", "https://logodix.com/logo/7528.jpg", "Apparel Retail",
						adressen[7], "TODO", "francesca@outlook.com", "+393713432912", "IT7410491107419", true)
		);
		
		List<Gebruiker> gebruikers = Arrays.asList(
				new Gebruiker(Rol.ADMINISTRATOR, "joris@outlook.com", "1234", "Joris", 			true, adressen[0]),
				new Gebruiker(Rol.LEVERANCIER, 	"mark@outlook.be", "1234", "Mark", 				true, adressen[1]),
				new Gebruiker(Rol.KLANT, 		"michel@outlook.be", "1234", "Michel", 			true, adressen[1]),
				new Gebruiker(Rol.LEVERANCIER, 	"mike@gmail.com", "1234", "Mike", 				true, adressen[2]),
				new Gebruiker(Rol.KLANT, 		"jake@gmail.com", "1234", "Jake", 				true, adressen[2]),
				new Gebruiker(Rol.LEVERANCIER, 	"julia@web.de", "1234", "Julia", 				true, adressen[3]),
				new Gebruiker(Rol.KLANT, 		"kurt@web.de", "1234", "Kurt", 					true, adressen[3]),
				new Gebruiker(Rol.LEVERANCIER, 	"kim@gmail.com", "1234", "Kim", 				true, adressen[4]),
				new Gebruiker(Rol.KLANT, 		"laura@gmail.com", "1234", "Laura", 			true, adressen[4]),
				new Gebruiker(Rol.LEVERANCIER, 	"christophe@outlook.com", "1234", "Christophe", true, adressen[5]),
				new Gebruiker(Rol.KLANT, 		"jean@outlook.com", "1234", "Jean",				true, adressen[5]),
				new Gebruiker(Rol.LEVERANCIER, 	"matilda@outlook.com", "1234", "Matilda", 		true, adressen[6]),
				new Gebruiker(Rol.KLANT, 		"erik@outlook.com", "1234", "Erik", 			true, adressen[6]),
				new Gebruiker(Rol.LEVERANCIER, 	"francesca@outlook.com", "1234", "Francesca", 	true, adressen[7]),
				new Gebruiker(Rol.KLANT, 		"sandra@outlook.com", "1234", "Sandra", 		true, adressen[7])
		);
		
		List<Klant> klanten = Arrays.asList(
				new Klant("Stella Artois", "https://logodix.com/logo/2066282.png", 		"+32974178174", "mark@outlook.be", adressen[1]),
				new Klant("Hewlett-Packard", "https://logodix.com/logo/4934.png",			"(212)912-0384", "mike@gmail.com", adressen[2]),
				new Klant("Bosch", "https://logodix.com/logo/9541.png",	 		"+496591799946", "julia@web.de", adressen[3]),
				new Klant("Ahold Delhaize", "https://logodix.com/logo/420832.png", 		"+31659267802", "kim@gmail.com", adressen[4]),
				new Klant("Peugeot", "https://logodix.com/logo/9436.png", 	"+33124311738", "christophe@outlook.com", adressen[5]),
				new Klant("Ikea", "https://logodix.com/logo/470339.png", 	"+462252869831", "matilda@outlook.com", adressen[6]),
				new Klant("Giorgio Armani", "https://logodix.com/logo/7528.jpg", 	"+393713432912", "francesca@outlook.com", adressen[7])
		);
		
		List<List<Bestelling>> bestellingen = Arrays.asList(
				List.of(
						new Bestelling(
								49001, LocalDate.now(), 
								OrderStatus.GEREGISTREERD, BetalingsStatus.NIET_BETAALD, 
								klanten.get(3),
								Arrays.asList(
										new Product("Stella Artois Lager, 12 Pack 11.2 fl. oz. Bottles", 			80, Stock.STOCK, 39.99),
										new Product("Stella Artois Lager, 24 Pack 11.2 fl. oz. Cans",				140, Stock.STOCK, 59.99),
										new Product("Stella Artois Premium Lager Beer, 24-11.2 fl. oz. Bottles", 	20, Stock.STOCK, 52.49),
										new Product("Stella Artois Premium Lager Beer, 3 Pack 25 fl. oz. Cans", 	30, Stock.STOCK, 18.99)
								)
						),
						new Bestelling(
								49002, LocalDate.now(), 
								OrderStatus.GEREGISTREERD, BetalingsStatus.NIET_BETAALD, 
								klanten.get(4),
								Arrays.asList(
										new Product("Stella Artois Lager, 12 Pack 11.2 fl. oz. Bottles", 	100, Stock.STOCK, 39.99),
										new Product("Stella Artois Lager, 24 Pack 11.2 fl. oz. Cans",		220, Stock.STOCK, 59.99)
								)
						)
				),
				List.of(
						new Bestelling(
								50001, LocalDate.now().plusDays(1), 
								OrderStatus.ONDERWEG, BetalingsStatus.BETAALD, 
								klanten.get(2),
								Arrays.asList(
										new Product("Business Laptop - HP Chrome Enterprise", 	300, Stock.STOCK, 469.00)
								)
						),
						new Bestelling(
								50002, LocalDate.now().plusDays(5), 
								OrderStatus.ONDERWEG, BetalingsStatus.BETAALD, 
								klanten.get(3),
								Arrays.asList(
										new Product("Business Laptop - HP Elite", 				2, Stock.STOCK, 1379.00),
										new Product("Business Desktop - HP Z2 Workstation", 	10, Stock.STOCK, 1669.00)
								)
						),
						new Bestelling(
								50003, LocalDate.now().minusDays(3),
								OrderStatus.GELEVERD, BetalingsStatus.BETAALD,
								klanten.get(5),
								Arrays.asList(
										new Product("Business Laptop - HP Chrome Enterprise", 	120, Stock.STOCK, 469.00)
								)
						)
				),
				List.of(
						new Bestelling(
								51001, LocalDate.now(), 
								OrderStatus.ONDERWEG, BetalingsStatus.BETAALD, 
								klanten.get(5),
								Arrays.asList(
										new Product("WGG1440KFG Serie 6 Wasmachine 9kg", 10, Stock.ORDER, 899.99),
										new Product("WGB244A4FG Serie 8 Wasmachine 9kg", 2, Stock.ORDER, 1239.99)
								)
						),
						new Bestelling(
								51002, LocalDate.now(), 
								OrderStatus.ONDERWEG, BetalingsStatus.BETAALD, 
								klanten.get(6),
								Arrays.asList(
										new Product("WGB244A4FG Serie 8 Wasmachine 9kg", 8, Stock.ORDER, 1239.99),
										new Product("WTN85205FG Serie 4 Luchtcondensatie droogkast 8kg", 3, Stock.ORDER, 659.99)
								)
						)
				),
				List.of(
						new Bestelling(
								52001, LocalDate.now(),
								OrderStatus.GELEVERD, BetalingsStatus.BETAALD,
								klanten.get(0),
								Arrays.asList(
										new Product("Bronwater | Romy | Niet bruisend | PET 6x2l", 8, Stock.STOCK, 2.35),
										new Product("Cola | Original taste | Blik | Soda 15x33cl", 10, Stock.STOCK, 12.09),
										new Product("Cola | Zonder suiker | Blik | Frisdrank 15x33cl", 10, Stock.STOCK, 12.69),
										new Product("Bruisende Ijsthee | Original | Caloriearm | PET", 4, Stock.STOCK, 5.45),
										new Product("Limonade | Sinaasappel | Blik | Sleek 8x33cl", 6, Stock.STOCK, 4.99),
										new Product("Crackers | Original | Toastjes | Zout | 2x75gr", 10, Stock.STOCK, 1.85),
										new Product("Koekje | Comté 100gr", 10, Stock.STOCK, 3.69),
										new Product("Soep | Room | Witloof 600gr", 5, Stock.STOCK, 3.99),
										new Product("Tomatensoep | Bio 250gr", 5, Stock.STOCK, 2.39),
										new Product("Pompoensoep | Bio 250gr", 5, Stock.STOCK, 2.39),
										new Product("Naturel Zout | Regular | Chips | 120G", 10, Stock.STOCK, 1.55),
										new Product("Paprika | Regular | Chips | 120G", 10, Stock.STOCK, 1.65),
										new Product("Gerookt | Snacks | Chips | 125G", 10, Stock.STOCK, 1.52),
										new Product("Snack | Droge worst | Gerookt | Snack Pack 60gr", 10, Stock.STOCK, 2.99)
								)
						),
						new Bestelling(
								52002, LocalDate.now(),
								OrderStatus.GELEVERD, BetalingsStatus.BETAALD,
								klanten.get(0),
								Arrays.asList(
										new Product("Vierkant brood | Wit 800gr", 4, Stock.STOCK, 2.49),
										new Product("Brood | Bruin | Vierkant 800gr", 4, Stock.STOCK, 2.49),
										new Product("Brood | Volkoren | Vierkant 800gr", 4, Stock.STOCK, 3.19),
										new Product("Gouda | Jong | Sneden", 4, Stock.STOCK, 3.15),
										new Product("Limonade | Sinaasappel | Blik | Sleek 8x33cl", 6, Stock.STOCK, 4.99),
										new Product("Crackers | Original | Toastjes | Zout | 2x75gr", 10, Stock.STOCK, 1.85),
										new Product("Koekje | Comté 100gr", 10, Stock.STOCK, 3.69),
										new Product("Soep | Room | Witloof 600gr", 5, Stock.STOCK, 3.99),
										new Product("Tomatensoep | Bio 250gr", 5, Stock.STOCK, 2.39),
										new Product("Pompoensoep | Bio 250gr", 5, Stock.STOCK, 2.39)
								)
						)
				),
				List.of(
						new Bestelling(
								53001, LocalDate.now(),
								OrderStatus.GELEVERD, BetalingsStatus.BETAALD,
								klanten.get(2),
								Arrays.asList(
										new Product("New E-208 E-Style Electric 50kWh 136 Agueda Yellow", 1, Stock.ORDER, 32650.00),
										new Product("New E-208 E-Style Electric 50kWh 136 Nera Black", 1, Stock.ORDER, 32650.00)
								)
						),
						new Bestelling(
								53002, LocalDate.now(),
								OrderStatus.ONDERWEG, BetalingsStatus.BETAALD,
								klanten.get(6),
								Arrays.asList(
										new Product("Traveller Business VIP Standard Electric 50KWh 136 Cumulus Grey", 2, Stock.ORDER, 49495.00)
								)
						),
						new Bestelling(
								53003, LocalDate.now(),
								OrderStatus.GEREGISTREERD, BetalingsStatus.BETAALD,
								klanten.get(2),
								Arrays.asList(
										new Product("408 ALLURE 1.2L PureTech 130 EAT8 S&S Elixir Red", 1, Stock.ORDER, 32805.00)
								)
						),
						new Bestelling(
								53004, LocalDate.now(),
								OrderStatus.GEREGISTREERD, BetalingsStatus.NIET_BETAALD,
								klanten.get(0),
								Arrays.asList(
										new Product("408 ALLURE PLUG-IN HYBRID 180 e-EAT8 Okenite White", 1, Stock.ORDER, 41100.00)
								)
						)
				),
				List.of(
						new Bestelling(
								54001, LocalDate.now(),
								OrderStatus.GELEVERD, BetalingsStatus.BETAALD,
								klanten.get(0),
								Arrays.asList(
										new Product("OMAR 1 section shelving unit, 36 1/4x14 1/8x71 1/4 \"", 30, Stock.STOCK, 79.98)
								)
						),
						new Bestelling(
								54002, LocalDate.now(),
								OrderStatus.AAN_HET_VERWERKEN, BetalingsStatus.BETAALD,
								klanten.get(6),
								Arrays.asList(
										new Product("IVAR 2 section shelving unit, 68 1/2x19 5/8x89 \"", 12, Stock.ORDER, 275.00),
										new Product("BUMERANG Hanger 8 pack", 80, Stock.STOCK, 4.99),
										new Product("RIGGA Clothes rack", 20, Stock.STOCK, 19.99)
								)
						),
						new Bestelling(
								54003, LocalDate.now(),
								OrderStatus.GEREGISTREERD, BetalingsStatus.NIET_BETAALD,
								klanten.get(1),
								Arrays.asList(
										new Product("OMAR 1 section shelving unit, 36 1/4x14 1/8x71 1/4 \"", 8, Stock.STOCK, 79.98),
										new Product("INGO Table, 47 1/4x29 1/2 \"", 4, Stock.STOCK, 99.99)
								)
						)
				),
				List.of(
						
				)
		);
		
		// voeg klanten toe aan de bedrijven
		for (int i = 0; i < bedrijven.size(); i++) {
			bedrijven.get(i).setKlant(klanten.get(i));
		}
		
		// leg de bidirectionale link tussen leverancier en bestelling(en)
		int klantNr = 0;
		int leverancierNr = 0;
		for (int i = 0; i < gebruikers.size(); i++) {
			int gebruikerNr = i;
			
			// we voegen enkel bestellingen toe aan leveranciers
			if (gebruikers.get(gebruikerNr).getRol() == Rol.LEVERANCIER) {
				// leg de link naar bestellingen voor een leverancier
				gebruikers.get(gebruikerNr).setBestellingen(bestellingen.get(leverancierNr));
				
				// voor elk van deze bestellingen, leg een link terug naar de leverancier
				bestellingen.get(leverancierNr).forEach(bestelling -> {
					bestelling.setLeverancier(gebruikers.get(gebruikerNr));
				});
				
				// voeg de leverancierGebruiker toe aan hun bedrijf
				bedrijven.get(leverancierNr).setLeverancierGebruiker(gebruikers.get(gebruikerNr));
				
				leverancierNr++;
			} else if (gebruikers.get(gebruikerNr).getRol() == Rol.KLANT) {
				// voeg de klantGebruiker toe aan hun bedrijf
				bedrijven.get(klantNr).setKlantGebruiker(gebruikers.get(gebruikerNr));
				
				klantNr++;
			}
		}
		
		// leg de link van klant naar hun bestellingen
		List<Bestelling> alleBestellingen = bestellingen.stream().flatMap(list -> list.stream()).collect(Collectors.toList());
		klanten.forEach(klant -> {
			List<Bestelling> bestellingenPerKlant = alleBestellingen.stream().filter(b -> b.getKlant() == klant).collect(Collectors.toList());
			klant.setBestellingen(bestellingenPerKlant);
		});
		
		return bedrijven;
	}
}