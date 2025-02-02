package main;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import domein.Adres;
import domein.Bedrijf;
import domein.BesteldProduct;
import domein.Bestelling;
import domein.Betaalmethode;
import domein.Betaling;
import domein.BetalingsStatus;
import domein.LeverMethode;
import domein.OrderStatus;
import domein.Product;
import domein.gebruiker.Administrator;
import domein.gebruiker.Gebruiker;
import domein.gebruiker.Klant;
import domein.gebruiker.Leverancier;
import service.bedrijf.BedrijfService;
import service.bedrijf.BedrijfServiceDbImpl;
import service.betaling.BetalingService;
import service.betaling.BetalingServiceDbImpl;
import service.gebruiker.GebruikerService;
import service.gebruiker.GebruikerServiceDbImpl;

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
		
		List<List<Betaalmethode>> betaalmethodes = Arrays.asList(
				Arrays.asList(
						Betaalmethode.ACHTERAF_BETALEN, 
						Betaalmethode.APPLE_PAY,
						Betaalmethode.BANCONTACT,
						Betaalmethode.MAESTRO,
						Betaalmethode.MASTERCARD,
						Betaalmethode.PAYCONIQ,
						Betaalmethode.PAYPAL,
						Betaalmethode.VENMO,
						Betaalmethode.VISA
						),
				Arrays.asList(
						Betaalmethode.BANCONTACT,
						Betaalmethode.MAESTRO,
						Betaalmethode.MASTERCARD,
						Betaalmethode.ACHTERAF_BETALEN,
						Betaalmethode.VISA
						),
				Arrays.asList(
						Betaalmethode.ACHTERAF_BETALEN, 
						Betaalmethode.APPLE_PAY,
						Betaalmethode.BANCONTACT,
						Betaalmethode.MAESTRO,
						Betaalmethode.MASTERCARD
						),
				Arrays.asList(
						Betaalmethode.ACHTERAF_BETALEN, 
						Betaalmethode.APPLE_PAY,
						Betaalmethode.BANCONTACT,
						Betaalmethode.PAYCONIQ,
						Betaalmethode.PAYPAL,
						Betaalmethode.VENMO,
						Betaalmethode.VISA
						),
				Arrays.asList(
						Betaalmethode.APPLE_PAY,
						Betaalmethode.BANCONTACT,
						Betaalmethode.MAESTRO,
						Betaalmethode.MASTERCARD,
						Betaalmethode.PAYCONIQ,
						Betaalmethode.VENMO
						),
				Arrays.asList(
						Betaalmethode.BANCONTACT,
						Betaalmethode.MAESTRO,
						Betaalmethode.MASTERCARD,
						Betaalmethode.VISA
						),
				Arrays.asList(
						Betaalmethode.ACHTERAF_BETALEN, 
						Betaalmethode.APPLE_PAY,
						Betaalmethode.PAYCONIQ,
						Betaalmethode.PAYPAL,
						Betaalmethode.VENMO,
						Betaalmethode.VISA
						)
		);
		
		List<Bedrijf> bedrijven = List.of(
				new Bedrijf("Stella Artois", "https://logodix.com/logo/2066282.png", "Brewers",
						adressen[0], betaalmethodes.get(0), "BE16154215421625", "mark@outlook.be", "+32974178174", "BE197248342B38", true),
				new Bedrijf("Hewlett-Packard", "https://logodix.com/logo/4934.png", "Technology Hardware, Storage & Peripherals",
						adressen[1], betaalmethodes.get(1), "317265174 - 97135791278174529377", "mike@gmail.com", "(212)912-0384", "749196976", true),
				new Bedrijf("Bosch", "https://logodix.com/logo/9541.png", "Household Appliances",
						adressen[2], betaalmethodes.get(2), "DE51166532731153542378", "julia@web.de", "+496591799946", "DE41280756350", true),
				new Bedrijf("Ahold Delhaize", "https://logodix.com/logo/420832.png", "Food Retail",
						adressen[3], betaalmethodes.get(3), "NL5253324545874212", "kim@gmail.com", "+31659267802", "NL463774784B52", true),
				new Bedrijf("Peugeot", "https://logodix.com/logo/9436.png", "Automobile Manufacturers",
						adressen[4], betaalmethodes.get(4), "FR4565147832789145659823451", "christophe@outlook.com", "+33124311738", "FR4579135426784", true),
				new Bedrijf("Ikea", "https://logodix.com/logo/470339.png", "Home Furnishings",
						adressen[5], betaalmethodes.get(5), "SE1245617983516498734578", "matilda@outlook.com", "+462252869831", "SW6544167324132", true),
				new Bedrijf("Giorgio Armani", "https://logodix.com/logo/7528.jpg", "Apparel Retail",
						adressen[6], betaalmethodes.get(6), "IT1791517945179531495274127", "francesca@outlook.com", "+393713432912", "IT7410491107419", true)
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
						new Product("Stella Artois Lager, 12 Pack 11.2 fl. oz. Bottles", 			1000, 39.99, LeverMethode.STOCK, "https://i5.walmartimages.com/asr/477b14e6-00f3-4a53-8005-cfffabd29493.9803909dee8b1a605d94d752aecb93da.jpeg?odnWidth=1000&odnHeight=1000&odnBg=ffffff"),
						new Product("Stella Artois Lager, 24 Pack 11.2 fl. oz. Cans",				900, 59.99, LeverMethode.STOCK, "https://www.pikfly.com/images/products/171/43062.jpg"),
						new Product("Stella Artois Premium Lager Beer, 24-11.2 fl. oz. Bottles", 	500, 52.49, LeverMethode.STOCK, "https://www.kroger.com/product/images/large/front/0078615000038"),
						new Product("Stella Artois Premium Lager Beer, 3 Pack 25 fl. oz. Cans", 	500, 18.99, LeverMethode.STOCK, "https://beerhunter.co.uk/wp-content/uploads/2021/05/Stella-Artois-Premium-Belgian-Lager-Pint-Can-Gift-Set-with-Official-Pint-Glass-3-Pack-tiny-1.jpg")
				),
				List.of(
						new Product("Business Laptop - HP Chrome Enterprise", 	500, 469.00, LeverMethode.STOCK, "https://media.krefel.be/sys-master/products/9308772040734/768x768.41008662_01.webp"),
						new Product("Business Laptop - HP Elite", 				50, 1379.00, LeverMethode.STOCK, "https://www.hp.com/be-nl/shop/media/catalog/product/c/0/c08473310.png?store=be-nl&image-type=image&auto=webp&format=png&width=1280&height=1600&fit=bounds&dpr=1.25"),
						new Product("Business Desktop - HP Z2 Workstation", 	20, 1669.00, LeverMethode.STOCK, "https://cdn.centralpoint.be/objects/high_pic/3/3b7/3465438_pcs-werkstations-hp-z2-small-form-factor-g9-workstation-5f0l7eaabb.jpg")
				),
				List.of(
						new Product("WGG1440KFG Serie 6 Wasmachine 9kg", 					8, 899.99, LeverMethode.STOCK, "https://auctions.hilcoapac.com/images/lot/1325/132574_0.jpg?1604285164"),
						new Product("WGB244A4FG Serie 8 Wasmachine 9kg", 					7, 1239.99, LeverMethode.STOCK, "https://media3.bosch-home.com/Product_Shots/900x506/21352757_WGB2440MFG_STP_def.webp"),
						new Product("WTN85205FG Serie 4 Luchtcondensatie droogkast 8kg", 	4, 659.99, LeverMethode.STOCK, "https://media.s-bol.com/3JDJpXL783P4/p20ypQ/858x1200.jpg")
				),
				List.of(
						new Product("Bronwater | Romy | Niet bruisend | PET 6x2l", 100, 2.35, LeverMethode.STOCK, "https://static.delhaize.be/medias/sys_master/hc4/hea/11706880426014.jpg?buildNumber=82174e95e8034dc2242aadaed51c35b2e76a4bb2908eb0fb223ce572e154b418&imwidth=320"),
						new Product("Cola | Original taste | Blik | Soda 15x33cl", 210, 12.09, LeverMethode.STOCK, "https://static.delhaize.be/medias/sys_master/h34/hb2/11171019980830.jpg?buildNumber=82174e95e8034dc2242aadaed51c35b2e76a4bb2908eb0fb223ce572e154b418&imwidth=320"),
						new Product("Cola | Zonder suiker | Blik | Frisdrank 15x33cl", 180, 12.69, LeverMethode.STOCK, "https://static.delhaize.be/medias/sys_master/hfd/hd3/11811610132510.jpg?buildNumber=82174e95e8034dc2242aadaed51c35b2e76a4bb2908eb0fb223ce572e154b418&imwidth=320"),
						new Product("Bruisende Ijsthee | Original | Caloriearm | PET", 80, 5.45, LeverMethode.STOCK, "https://static.delhaize.be/medias/sys_master/hcf/hca/11692300959774.jpg?buildNumber=82174e95e8034dc2242aadaed51c35b2e76a4bb2908eb0fb223ce572e154b418&imwidth=320"),
						new Product("Limonade | Sinaasappel | Blik | Sleek 8x33cl", 80, 4.99, LeverMethode.STOCK, "https://assets.iceland.co.uk/i/iceland/Fanta_8x33_Orange_72978.jpg?$pdpzoom$"),
						new Product("Crackers | Original | Toastjes | Zout | 2x75gr", 80, 1.85, LeverMethode.STOCK, "https://static.delhaize.be/medias/sys_master/h6d/h8f/12008484765726.jpg?buildNumber=82174e95e8034dc2242aadaed51c35b2e76a4bb2908eb0fb223ce572e154b418&imwidth=320"),
						new Product("Koekje | Comté 100gr", 160, 3.69, LeverMethode.STOCK, "https://static.delhaize.be/medias/sys_master/h02/h00/11735135059998.jpg?buildNumber=82174e95e8034dc2242aadaed51c35b2e76a4bb2908eb0fb223ce572e154b418&imwidth=320"),
						new Product("Soep | Room | Witloof 600gr", 40, 3.99, LeverMethode.STOCK, "https://static.delhaize.be/medias/sys_master/h8a/h7e/11574169567262.jpg?buildNumber=82174e95e8034dc2242aadaed51c35b2e76a4bb2908eb0fb223ce572e154b418&imwidth=320"),
						new Product("Tomatensoep | Bio 250gr", 40, 2.39, LeverMethode.STOCK, "https://static.delhaize.be/medias/sys_master/hae/had/10627189538846.jpg?buildNumber=82174e95e8034dc2242aadaed51c35b2e76a4bb2908eb0fb223ce572e154b418&imwidth=320"),
						new Product("Pompoensoep | Bio 250gr", 40, 2.39, LeverMethode.STOCK, "https://static.delhaize.be/medias/sys_master/hd7/h4d/11235038920734.jpg?buildNumber=82174e95e8034dc2242aadaed51c35b2e76a4bb2908eb0fb223ce572e154b418&imwidth=320"),
						new Product("Naturel Zout | Regular | Chips | 120G", 80, 1.55, LeverMethode.STOCK, "https://static.delhaize.be/medias/sys_master/hc4/ha7/11709955702814.jpg?buildNumber=3007325bb863b192eb062003dbb494f1a7838987990a03b9fd021377e8c47da1"),
						new Product("Paprika | Regular | Chips | 120G", 80, 1.65, LeverMethode.STOCK, "https://static.delhaize.be/medias/sys_master/h16/h1a/11707147354142.jpg?buildNumber=82174e95e8034dc2242aadaed51c35b2e76a4bb2908eb0fb223ce572e154b418&imwidth=320"),
						new Product("Gerookt | Snacks | Chips | 125G", 60, 1.52, LeverMethode.STOCK, "https://static.delhaize.be/medias/sys_master/hf4/h0b/12420329537566.jpg?buildNumber=82174e95e8034dc2242aadaed51c35b2e76a4bb2908eb0fb223ce572e154b418&imwidth=320"),
						new Product("Snack | Droge worst | Gerookt | Snack Pack 60gr", 100, 2.99, LeverMethode.STOCK, "https://static.delhaize.be/medias/sys_master/hd9/h6b/12326871171102.jpg?buildNumber=82174e95e8034dc2242aadaed51c35b2e76a4bb2908eb0fb223ce572e154b418&imwidth=320"),
						new Product("Vierkant brood | Wit 800gr", 80, 2.49, LeverMethode.STOCK, "https://static.delhaize.be/medias/sys_master/hde/h2a/11211812929566.jpg?buildNumber=82174e95e8034dc2242aadaed51c35b2e76a4bb2908eb0fb223ce572e154b418&imwidth=320"),
						new Product("Brood | Bruin | Vierkant 800gr", 80, 2.49, LeverMethode.STOCK, "https://static.delhaize.be/medias/sys_master/h27/h2f/11211773607966.jpg?buildNumber=82174e95e8034dc2242aadaed51c35b2e76a4bb2908eb0fb223ce572e154b418&imwidth=320"),
						new Product("Brood | Volkoren | Vierkant 800gr", 80, 3.19, LeverMethode.STOCK, "https://static.delhaize.be/medias/sys_master/h64/h19/11088435511326.jpg?buildNumber=82174e95e8034dc2242aadaed51c35b2e76a4bb2908eb0fb223ce572e154b418&imwidth=320"),
						new Product("Gouda | Jong | Sneden", 120, 3.15, LeverMethode.STOCK, "https://static.delhaize.be/medias/sys_master/h5e/hfb/12097500119070.jpg?buildNumber=82174e95e8034dc2242aadaed51c35b2e76a4bb2908eb0fb223ce572e154b418&imwidth=320")
				),
				List.of(
						new Product("New E-208 E-Style Electric 50kWh 136 Agueda Yellow", 0, 32650.00, LeverMethode.ORDER, "https://ukcar.ma/wp-content/uploads/2023/02/208.jpg"),
						new Product("New E-208 E-Style Electric 50kWh 136 Nera Black", 0, 32650.00, LeverMethode.ORDER, "https://th.bing.com/th/id/OIP.4sa4iMeFLhQYc9YK3HJ-2AHaDb?rs=1&pid=ImgDetMain"),
						new Product("Traveller Business VIP Standard Electric 50KWh 136 Cumulus Grey", 0, 49495.00, LeverMethode.ORDER, "https://th.bing.com/th/id/R.ed23a9eca9fa2a390153f86a571910d4?rik=wfUqWGNMLeUqhw&pid=ImgRaw&r=0"),
						new Product("408 ALLURE 1.2L PureTech 130 EAT8 S&S Elixir Red", 0, 32805.00, LeverMethode.ORDER, "https://www.rental-center-crete.com/gallery/cars/2019/g-peugeot-2008.webp"),
						new Product("408 ALLURE PLUG-IN HYBRID 180 e-EAT8 Okenite White", 0, 41100.00, LeverMethode.ORDER, "https://www.peugeot.be/content/dam/peugeot/master/b2c/our-range/showroom/new-408/2023/desktop/408_Blanc_Okenite_1920_1080.jpg")
				),
				List.of(
						new Product("OMAR 1 section shelving unit, 36 1/4x14 1/8x71 1/4 \"", 40, 79.98, LeverMethode.STOCK, "https://www.ikea.com/us/en/images/products/omar-1-section-shelving-unit__0626066_pe692566_s5.jpg?f=xl"),
						new Product("IVAR 2 section shelving unit, 68 1/2x19 5/8x89 \"", 0, 275.00, LeverMethode.ORDER, "https://www.ikea.com/us/en/images/products/ivar-2-section-shelving-unit-pine__0138387_pe297655_s5.jpg?f=xl"),
						new Product("BUMERANG Hanger 8 pack", 500, 4.99, LeverMethode.STOCK, "https://www.ikea.com/be/en/images/products/bumerang-hanger-natural__0710666_pe727700_s5.jpg?f=xl"),
						new Product("RIGGA Clothes rack", 60, 19.99, LeverMethode.STOCK, "https://www.ikea.com/be/nl/images/products/rigga-kledingrek-wit__0710721_pe727742_s5.jpg?f=xl"),
						new Product("INGO Table, 47 1/4x29 1/2 \"", 10, 99.99, LeverMethode.STOCK, "https://www.ikea.com/us/en/images/products/ingo-table-pine__0737092_pe740877_s5.jpg?f=xl")
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
								),
								LocalDate.now().minusMonths(2)
								
						),
						new Bestelling(
								49002, LocalDate.now().minusDays(1), 
								OrderStatus.GEPLAATST, BetalingsStatus.BETAALD, 
								klanten.get(4), leveranciers.get(0),
								Arrays.asList(
										new BesteldProduct(producten.get(0).get(0), 100),
										new BesteldProduct(producten.get(0).get(1), 220)
								),
								LocalDate.now().plusDays(29)
						)
				),
				List.of(
						new Bestelling(
								50001, LocalDate.now().minusMonths(1), 
								OrderStatus.GELEVERD, BetalingsStatus.BETAALD, 
								klanten.get(2), leveranciers.get(1),
								Arrays.asList(
										new BesteldProduct(producten.get(1).get(0), 300)
								),
								LocalDate.now().plusDays(10)
						),
						new Bestelling(
								50002, LocalDate.now().minusDays(12), 
								OrderStatus.UIT_VOOR_LEVERING, BetalingsStatus.BETAALD, 
								klanten.get(3), leveranciers.get(1),
								Arrays.asList(
										new BesteldProduct(producten.get(1).get(1), 2),
										new BesteldProduct(producten.get(1).get(2), 10)
								),
								LocalDate.now().plusDays(100)
						),
						new Bestelling(
								50003, LocalDate.now(),
								OrderStatus.VERWERKT, BetalingsStatus.FACTUUR_VERZONDEN,
								klanten.get(5), leveranciers.get(1),
								Arrays.asList(
										new BesteldProduct(producten.get(1).get(0), 120)
								),
								LocalDate.now().plusDays(30)
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
								),
								LocalDate.now().plusDays(6)
						),
						new Bestelling(
								51002, LocalDate.now().minusDays(25), 
								OrderStatus.GELEVERD, BetalingsStatus.BETAALD, 
								klanten.get(6), leveranciers.get(2),
								Arrays.asList(
										new BesteldProduct(producten.get(2).get(1), 8),
										new BesteldProduct(producten.get(2).get(2), 3)
								),
								LocalDate.now().plusDays(79)
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
								),
								LocalDate.now().plusDays(66)
						),
						new Bestelling(
								52002, LocalDate.now().minusDays(3),
								OrderStatus.VERZONDEN, BetalingsStatus.BETAALD,
								klanten.get(4), leveranciers.get(3),
								Arrays.asList(
										new BesteldProduct(producten.get(3).get(14), 4),
										new BesteldProduct(producten.get(3).get(15), 4),
										new BesteldProduct(producten.get(3).get(16), 4),
										new BesteldProduct(producten.get(3).get(17), 4),
										new BesteldProduct(producten.get(3).get(4), 6),
										new BesteldProduct(producten.get(3).get(5), 10),
										new BesteldProduct(producten.get(3).get(6), 10),
										new BesteldProduct(producten.get(3).get(7), 5),
										new BesteldProduct(producten.get(3).get(8), 5),
										new BesteldProduct(producten.get(3).get(9), 5)
								),
								LocalDate.now().plusDays(94)
						),
						new Bestelling(
								52003, LocalDate.now().minusDays(1),
								OrderStatus.VERWERKT, BetalingsStatus.FACTUUR_VERZONDEN,
								klanten.get(6), leveranciers.get(3),
								Arrays.asList(
										new BesteldProduct(producten.get(3).get(14), 4),
										new BesteldProduct(producten.get(3).get(15), 4),
										new BesteldProduct(producten.get(3).get(16), 4),
										new BesteldProduct(producten.get(3).get(17), 4),
										new BesteldProduct(producten.get(3).get(4), 6),
										new BesteldProduct(producten.get(3).get(5), 10),
										new BesteldProduct(producten.get(3).get(6), 10),
										new BesteldProduct(producten.get(3).get(7), 5),
										new BesteldProduct(producten.get(3).get(8), 5),
										new BesteldProduct(producten.get(3).get(9), 5)
								),
								LocalDate.now().plusDays(31)
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
								),
								LocalDate.now().plusDays(31)
						),
						new Bestelling(
								53002, LocalDate.now().minusMonths(1),
								OrderStatus.GELEVERD, BetalingsStatus.BETAALD,
								klanten.get(6), leveranciers.get(4),
								Arrays.asList(
										new BesteldProduct(producten.get(4).get(2), 2)
								),
								LocalDate.now().plusDays(33)
						),
						new Bestelling(
								53003, LocalDate.now().minusDays(10),
								OrderStatus.VERWERKT, BetalingsStatus.BETAALD,
								klanten.get(2), leveranciers.get(4),
								Arrays.asList(
										new BesteldProduct(producten.get(4).get(3), 1)
								),
								LocalDate.now().plusDays(64)
						),
						new Bestelling(
								53004, LocalDate.now(),
								OrderStatus.GEPLAATST, BetalingsStatus.ONVERWERKT,
								klanten.get(0), leveranciers.get(4),
								Arrays.asList(
										new BesteldProduct(producten.get(4).get(4), 1)
								),
								LocalDate.now().plusDays(31)
						)
				),
				List.of(
						new Bestelling(
								54001, LocalDate.now().minusMonths(1),
								OrderStatus.GELEVERD, BetalingsStatus.BETAALD,
								klanten.get(0), leveranciers.get(5),
								Arrays.asList(
										new BesteldProduct(producten.get(5).get(0), 30)
								),
								LocalDate.now().plusDays(7)
						),
						new Bestelling(
								54002, LocalDate.now().minusDays(5),
								OrderStatus.VERZONDEN, BetalingsStatus.BETAALD,
								klanten.get(6), leveranciers.get(5),
								Arrays.asList(
										new BesteldProduct(producten.get(5).get(1), 12),
										new BesteldProduct(producten.get(5).get(2), 80),
										new BesteldProduct(producten.get(5).get(3), 20)
								),
								LocalDate.now().plusDays(43)
						),
						new Bestelling(
								54003, LocalDate.now().minusDays(1),
								OrderStatus.VERWERKT, BetalingsStatus.BETAALD,
								klanten.get(1), leveranciers.get(5),
								Arrays.asList(
										new BesteldProduct(producten.get(5).get(0), 8),
										new BesteldProduct(producten.get(5).get(4), 4)
								),
								LocalDate.now().plusDays(49)
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