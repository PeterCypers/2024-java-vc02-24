package main;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import domein.Adres;
import domein.Bedrijf;
import domein.BesteldProduct;
import domein.Bestelling;
import domein.Betaling;
import domein.BetalingsStatus;
import domein.OrderStatus;
import domein.Product;
import domein.Stock;
import domein.gebruiker.Administrator;
import domein.gebruiker.Gebruiker;
import domein.gebruiker.Klant;
import domein.gebruiker.Leverancier;
import service.bedrijf.BedrijfService;
import service.bedrijf.BedrijfServiceDbImpl;
import service.gebruiker.GebruikerService;
import service.gebruiker.GebruikerServiceDbImpl;
import service.betaling.BetalingService;
import service.betaling.BetalingServiceDbImpl;

public class PopulateDb {
	
	public static void main(String[] args) {
		vulDatabase();
    }
	
	private static void vulDatabase() {
		List<Bedrijf> bedrijfData = maakBedrijfData();
		List<Gebruiker> adminData = maakAdministratorData();
		List<Betaling> betalingData = maakBetalingData();
		
		bedrijfData.forEach((b) -> {
			// Controle op data
			System.out.println(b.getNaam());
			System.out.println("- " + b.getKlant().getNaam() + "- " + b.getKlant().getNaam() + "- " + b.getLeverancier().getNaam());
			b.getLeverancier().getBestellingen().forEach(bes -> {
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
		BetalingService bts = new BetalingServiceDbImpl();
		
		long startTime = System.currentTimeMillis();
		
		System.out.println("Seeding van de database is bezig...");
		try {
			bedrijfData.forEach(b -> bs.voegBedrijfToe(b));
			adminData.forEach(a -> gs.voegGebruikerToe(a));
			betalingData.forEach(b -> bts.voegBetalingToe(b));
			
			float elapsedTime = (System.currentTimeMillis() - startTime) / 1000F;
			System.out.printf("Seeding werd succesvol afgerond (%.2f seconden)", elapsedTime);
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	private static List<Gebruiker> maakAdministratorData() {
		return List.of(
				new Administrator(
					"joris@outlook.com",
					"1234",
					"Joris"
				)
		);
	}
	
	private static List<Bedrijf> maakBedrijfData() {
		Adres[] adressen = {
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
						adressen[0], "", "mark@outlook.be", "+32974178174", "BE197248342B38", true),
				new Bedrijf("Hewlett-Packard", "https://logodix.com/logo/4934.png", "Technology Hardware, Storage & Peripherals",
						adressen[1], "", "mike@gmail.com", "(212)912-0384", "749196976", true),
				new Bedrijf("Bosch", "https://logodix.com/logo/9541.png", "Household Appliances",
						adressen[2], "", "julia@web.de", "+496591799946", "DE41280756350", true),
				new Bedrijf("Ahold Delhaize", "https://logodix.com/logo/420832.png", "Food Retail",
						adressen[3], "", "kim@gmail.com", "+31659267802", "NL463774784B52", true),
				new Bedrijf("Peugeot", "https://logodix.com/logo/9436.png", "Automobile Manufacturers",
						adressen[4], "", "christophe@outlook.com", "+33124311738", "FR4579135426784", true),
				new Bedrijf("Ikea", "https://logodix.com/logo/470339.png", "Home Furnishings",
						adressen[5], "", "matilda@outlook.com", "+462252869831", "SW6544167324132", true),
				new Bedrijf("Giorgio Armani", "https://logodix.com/logo/7528.jpg", "Apparel Retail",
						adressen[6], "", "francesca@outlook.com", "+393713432912", "IT7410491107419", true)
		);
		
		List<Leverancier> leveranciers = List.of(
				new Leverancier(bedrijven.get(0), "mark@outlook.be", "1234", "Mark", true),
				new Leverancier(bedrijven.get(1), "mike@gmail.com", "1234", "Mike", true),
				new Leverancier(bedrijven.get(2), "julia@web.de", "1234", "Julia", true),
				new Leverancier(bedrijven.get(3), "kim@gmail.com", "1234", "Kim", true),
				new Leverancier(bedrijven.get(4), "christophe@outlook.com", "1234", "Christophe", true),
				new Leverancier(bedrijven.get(5), "matilda@outlook.com", "1234", "Matilda", true),
				new Leverancier(bedrijven.get(6), "francesca@outlook.com", "1234", "Francesca", true)
		);
		
		List<Klant> klanten = List.of(
				new Klant(bedrijven.get(0), "michel@outlook.be", "1234", "Michel", true, adressen[0], "+32974178174"),
				new Klant(bedrijven.get(1), "jake@gmail.com", "1234", "Jake", true, adressen[1], "(212)912-0384"),
				new Klant(bedrijven.get(2), "kurt@web.de", "1234", "Kurt", true, adressen[2], "+496591799946"),
				new Klant(bedrijven.get(3), "laura@gmail.com", "1234", "Laura", true, adressen[3], "+31659267802"),
				new Klant(bedrijven.get(4), "jean@outlook.com", "1234", "Jean", true, adressen[4], "+33124311738"),
				new Klant(bedrijven.get(5), "erik@outlook.com", "1234", "Erik", true, adressen[5], "+462252869831"),
				new Klant(bedrijven.get(6), "sandra@outlook.com", "1234", "Sandra", true, adressen[6], "+393713432912")
		);
		
		List<List<Product>> producten = Arrays.asList(
				List.of(
						new Product("Stella Artois Lager, 12 Pack 11.2 fl. oz. Bottles", 			Stock.STOCK, 39.99),
						new Product("Stella Artois Lager, 24 Pack 11.2 fl. oz. Cans",				Stock.STOCK, 59.99),
						new Product("Stella Artois Premium Lager Beer, 24-11.2 fl. oz. Bottles", 	Stock.STOCK, 52.49),
						new Product("Stella Artois Premium Lager Beer, 3 Pack 25 fl. oz. Cans", 	Stock.STOCK, 18.99)
				),
				List.of(
						new Product("Business Laptop - HP Chrome Enterprise", 	Stock.STOCK, 469.00),
						new Product("Business Laptop - HP Elite", 				Stock.STOCK, 1379.00),
						new Product("Business Desktop - HP Z2 Workstation", 	Stock.STOCK, 1669.00)
				),
				List.of(
						new Product("WGG1440KFG Serie 6 Wasmachine 9kg", 					Stock.ORDER, 899.99),
						new Product("WGB244A4FG Serie 8 Wasmachine 9kg", 					Stock.ORDER, 1239.99),
						new Product("WTN85205FG Serie 4 Luchtcondensatie droogkast 8kg", 	Stock.ORDER, 659.99)
				),
				List.of(
						new Product("Bronwater | Romy | Niet bruisend | PET 6x2l", Stock.STOCK, 2.35),
						new Product("Cola | Original taste | Blik | Soda 15x33cl", Stock.STOCK, 12.09),
						new Product("Cola | Zonder suiker | Blik | Frisdrank 15x33cl", Stock.STOCK, 12.69),
						new Product("Bruisende Ijsthee | Original | Caloriearm | PET", Stock.STOCK, 5.45),
						new Product("Limonade | Sinaasappel | Blik | Sleek 8x33cl", Stock.STOCK, 4.99),
						new Product("Crackers | Original | Toastjes | Zout | 2x75gr", Stock.STOCK, 1.85),
						new Product("Koekje | Comté 100gr", Stock.STOCK, 3.69),
						new Product("Soep | Room | Witloof 600gr", Stock.STOCK, 3.99),
						new Product("Tomatensoep | Bio 250gr", Stock.STOCK, 2.39),
						new Product("Pompoensoep | Bio 250gr", Stock.STOCK, 2.39),
						new Product("Naturel Zout | Regular | Chips | 120G", Stock.STOCK, 1.55),
						new Product("Paprika | Regular | Chips | 120G", Stock.STOCK, 1.65),
						new Product("Gerookt | Snacks | Chips | 125G", Stock.STOCK, 1.52),
						new Product("Snack | Droge worst | Gerookt | Snack Pack 60gr", Stock.STOCK, 2.99),
						new Product("Vierkant brood | Wit 800gr", Stock.STOCK, 2.49),
						new Product("Brood | Bruin | Vierkant 800gr", Stock.STOCK, 2.49),
						new Product("Brood | Volkoren | Vierkant 800gr", Stock.STOCK, 3.19),
						new Product("Gouda | Jong | Sneden", Stock.STOCK, 3.15),
						new Product("Limonade | Sinaasappel | Blik | Sleek 8x33cl", Stock.STOCK, 4.99),
						new Product("Crackers | Original | Toastjes | Zout | 2x75gr", Stock.STOCK, 1.85),
						new Product("Koekje | Comté 100gr", Stock.STOCK, 3.69),
						new Product("Soep | Room | Witloof 600gr", Stock.STOCK, 3.99),
						new Product("Tomatensoep | Bio 250gr", Stock.STOCK, 2.39),
						new Product("Pompoensoep | Bio 250gr", Stock.STOCK, 2.39)
				),
				List.of(
						new Product("New E-208 E-Style Electric 50kWh 136 Agueda Yellow", Stock.ORDER, 32650.00),
						new Product("New E-208 E-Style Electric 50kWh 136 Nera Black", Stock.ORDER, 32650.00),
						new Product("Traveller Business VIP Standard Electric 50KWh 136 Cumulus Grey", Stock.ORDER, 49495.00),
						new Product("408 ALLURE 1.2L PureTech 130 EAT8 S&S Elixir Red", Stock.ORDER, 32805.00),
						new Product("408 ALLURE PLUG-IN HYBRID 180 e-EAT8 Okenite White", Stock.ORDER, 41100.00)
				),
				List.of(
						new Product("OMAR 1 section shelving unit, 36 1/4x14 1/8x71 1/4 \"", Stock.STOCK, 79.98),
						new Product("IVAR 2 section shelving unit, 68 1/2x19 5/8x89 \"", Stock.ORDER, 275.00),
						new Product("BUMERANG Hanger 8 pack", Stock.STOCK, 4.99),
						new Product("RIGGA Clothes rack", Stock.STOCK, 19.99),
						new Product("INGO Table, 47 1/4x29 1/2 \"", Stock.STOCK, 99.99)
				),
				List.of(
						
				)
		);
		
		List<List<Bestelling>> bestellingen = Arrays.asList(
				List.of(
						new Bestelling(
								49001, LocalDate.now().minusMonths(3),
								OrderStatus.GELEVERD, BetalingsStatus.BETAALD, 
								klanten.get(4), leveranciers.get(0), 
								Arrays.asList(
										new BesteldProduct(producten.get(0).get(0), 80),
										new BesteldProduct(producten.get(0).get(1), 140),
										new BesteldProduct(producten.get(0).get(2), 20),
										new BesteldProduct(producten.get(0).get(3), 30)
								)
						),
						new Bestelling(
								49002, LocalDate.now().minusDays(1), 
								OrderStatus.GEREGISTREERD, BetalingsStatus.BETAALD, 
								klanten.get(4), leveranciers.get(0),
								Arrays.asList(
										new BesteldProduct(producten.get(0).get(0), 100),
										new BesteldProduct(producten.get(0).get(1), 220)
								)
						)
				),
				List.of(
						new Bestelling(
								50001, LocalDate.now().minusMonths(1), 
								OrderStatus.GELEVERD, BetalingsStatus.BETAALD, 
								klanten.get(2), leveranciers.get(1),
								Arrays.asList(
										new BesteldProduct(producten.get(1).get(0), 300)
								)
						),
						new Bestelling(
								50002, LocalDate.now().minusDays(12), 
								OrderStatus.ONDERWEG, BetalingsStatus.BETAALD, 
								klanten.get(3), leveranciers.get(1),
								Arrays.asList(
										new BesteldProduct(producten.get(1).get(1), 2),
										new BesteldProduct(producten.get(1).get(2), 10)
								)
						),
						new Bestelling(
								50003, LocalDate.now(),
								OrderStatus.GEREGISTREERD, BetalingsStatus.NIET_BETAALD,
								klanten.get(5), leveranciers.get(1),
								Arrays.asList(
										new BesteldProduct(producten.get(1).get(0), 120)
								)
						)
				),
				List.of(
						new Bestelling(
								51001, LocalDate.now().minusMonths(3), 
								OrderStatus.GELEVERD, BetalingsStatus.BETAALD, 
								klanten.get(5), leveranciers.get(2),
								Arrays.asList(
										new BesteldProduct(producten.get(2).get(0), 10),
										new BesteldProduct(producten.get(2).get(1), 2)
								)
						),
						new Bestelling(
								51002, LocalDate.now().minusDays(25), 
								OrderStatus.GELEVERD, BetalingsStatus.BETAALD, 
								klanten.get(6), leveranciers.get(2),
								Arrays.asList(
										new BesteldProduct(producten.get(2).get(1), 8),
										new BesteldProduct(producten.get(2).get(2), 3)
								)
						)
				),
				List.of(
						new Bestelling(
								52001, LocalDate.now().minusMonths(1),
								OrderStatus.GELEVERD, BetalingsStatus.BETAALD,
								klanten.get(6), leveranciers.get(3),
								Arrays.asList(
										new BesteldProduct(producten.get(3).get(0), 8),
										new BesteldProduct(producten.get(3).get(1), 10),
										new BesteldProduct(producten.get(3).get(2), 10),
										new BesteldProduct(producten.get(3).get(3), 4),
										new BesteldProduct(producten.get(3).get(4), 6),
										new BesteldProduct(producten.get(3).get(5), 10),
										new BesteldProduct(producten.get(3).get(6), 10),
										new BesteldProduct(producten.get(3).get(7), 5),
										new BesteldProduct(producten.get(3).get(8), 5),
										new BesteldProduct(producten.get(3).get(9), 5),
										new BesteldProduct(producten.get(3).get(10), 10),
										new BesteldProduct(producten.get(3).get(11), 10),
										new BesteldProduct(producten.get(3).get(12), 10),
										new BesteldProduct(producten.get(3).get(13), 10)
								)
						),
						new Bestelling(
								52002, LocalDate.now().minusDays(3),
								OrderStatus.ONDERWEG, BetalingsStatus.BETAALD,
								klanten.get(4), leveranciers.get(3),
								Arrays.asList(
										new BesteldProduct(producten.get(3).get(14), 4),
										new BesteldProduct(producten.get(3).get(15), 4),
										new BesteldProduct(producten.get(3).get(16), 4),
										new BesteldProduct(producten.get(3).get(17), 4),
										new BesteldProduct(producten.get(3).get(18), 6),
										new BesteldProduct(producten.get(3).get(19), 10),
										new BesteldProduct(producten.get(3).get(20), 10),
										new BesteldProduct(producten.get(3).get(21), 5),
										new BesteldProduct(producten.get(3).get(22), 5),
										new BesteldProduct(producten.get(3).get(23), 5)
								)
						),
						new Bestelling(
								52003, LocalDate.now().minusDays(1),
								OrderStatus.GEREGISTREERD, BetalingsStatus.NIET_BETAALD,
								klanten.get(6), leveranciers.get(3),
								Arrays.asList(
										new BesteldProduct(producten.get(3).get(14), 4),
										new BesteldProduct(producten.get(3).get(15), 4),
										new BesteldProduct(producten.get(3).get(16), 4),
										new BesteldProduct(producten.get(3).get(17), 4),
										new BesteldProduct(producten.get(3).get(18), 6),
										new BesteldProduct(producten.get(3).get(19), 10),
										new BesteldProduct(producten.get(3).get(20), 10),
										new BesteldProduct(producten.get(3).get(21), 5),
										new BesteldProduct(producten.get(3).get(22), 5),
										new BesteldProduct(producten.get(3).get(23), 5)
								)
						)
				),
				List.of(
						new Bestelling(
								53001, LocalDate.now().minusMonths(2),
								OrderStatus.GELEVERD, BetalingsStatus.BETAALD,
								klanten.get(2), leveranciers.get(4),
								Arrays.asList(
										new BesteldProduct(producten.get(4).get(0), 1),
										new BesteldProduct(producten.get(4).get(1), 1)
								)
						),
						new Bestelling(
								53002, LocalDate.now().minusMonths(1),
								OrderStatus.ONDERWEG, BetalingsStatus.BETAALD,
								klanten.get(6), leveranciers.get(4),
								Arrays.asList(
										new BesteldProduct(producten.get(4).get(2), 2)
								)
						),
						new Bestelling(
								53003, LocalDate.now().minusDays(10),
								OrderStatus.AAN_HET_VERWERKEN, BetalingsStatus.BETAALD,
								klanten.get(2), leveranciers.get(4),
								Arrays.asList(
										new BesteldProduct(producten.get(4).get(3), 1)
								)
						),
						new Bestelling(
								53004, LocalDate.now(),
								OrderStatus.GEREGISTREERD, BetalingsStatus.NIET_BETAALD,
								klanten.get(0), leveranciers.get(4),
								Arrays.asList(
										new BesteldProduct(producten.get(4).get(4), 1)
								)
						)
				),
				List.of(
						new Bestelling(
								54001, LocalDate.now().minusMonths(1),
								OrderStatus.GELEVERD, BetalingsStatus.BETAALD,
								klanten.get(0), leveranciers.get(5),
								Arrays.asList(
										new BesteldProduct(producten.get(5).get(0), 30)
								)
						),
						new Bestelling(
								54002, LocalDate.now().minusDays(5),
								OrderStatus.ONDERWEG, BetalingsStatus.BETAALD,
								klanten.get(6), leveranciers.get(5),
								Arrays.asList(
										new BesteldProduct(producten.get(5).get(1), 12),
										new BesteldProduct(producten.get(5).get(2), 80),
										new BesteldProduct(producten.get(5).get(3), 20)
								)
						),
						new Bestelling(
								54003, LocalDate.now().minusDays(1),
								OrderStatus.AAN_HET_VERWERKEN, BetalingsStatus.BETAALD,
								klanten.get(1), leveranciers.get(5),
								Arrays.asList(
										new BesteldProduct(producten.get(5).get(0), 8),
										new BesteldProduct(producten.get(5).get(4), 4)
								)
						)
				),
				List.of(
						
				)
		);
		
		// voeg de gebruikers toe aan de bedrijven
		for (int i = 0; i < bedrijven.size(); i++) {
			bedrijven.get(i).setLeverancier(leveranciers.get(i));
			bedrijven.get(i).setKlant(klanten.get(i));
		}
		
		// leg de link van leverancier naar hun bestellingen en producten
		for (int i = 0; i < leveranciers.size(); i++) {
			leveranciers.get(i).setBestellingen(bestellingen.get(i));
			leveranciers.get(i).setProducten(producten.get(i));
		}
		
		// leg de link van klant naar hun bestellingen
		List<Bestelling> alleBestellingen = bestellingen.stream().flatMap(list -> list.stream()).collect(Collectors.toList());
		klanten.forEach(klant -> {
			List<Bestelling> bestellingenPerKlant = alleBestellingen.stream().filter(b -> b.getKlant() == klant).collect(Collectors.toList());
			klant.setBestellingen(bestellingenPerKlant);
		});
		
		return bedrijven;
	}
	
	private static List<Betaling> maakBetalingData() {
		return List.of(
				new Betaling(49001, true),
				new Betaling(49002, true),
				
				new Betaling(50001, true),
				new Betaling(50002, true),
				new Betaling(50003, false),
				
				new Betaling(51001, true),
				new Betaling(51002, true),
				
				new Betaling(52001, true),
				new Betaling(52002, true),
				new Betaling(52003, false),
				
				new Betaling(53001, true),
				new Betaling(53002, true),
				new Betaling(53003, true),
				new Betaling(53004, false),
				
				new Betaling(54001, true),
				new Betaling(54002, true),
				new Betaling(54003, true)
		);
	}
}