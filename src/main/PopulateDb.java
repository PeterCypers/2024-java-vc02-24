package main;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import domein.*;
import javafx.application.Application;
import javafx.stage.Stage;
import service.GebruikerService;
import service.GebruikerServiceDbImpl;

public class PopulateDb extends Application {
	
	public static void main(String... args) {
        Application.launch(PopulateDb.class, args);
    }
	
	@Override
    public void start(Stage stage) {
		List<Gebruiker> mockData = maakMockData();
		
		mockData.forEach((g) -> {
			System.out.printf("%s, %s", g.toString(), g.getBestellingen().toString());
		});
		
		GebruikerService as = new GebruikerServiceDbImpl();
		
		mockData.forEach((g) -> {
			as.voegGebruikerToe(g);
		});
    }
	
	private List<Gebruiker> maakMockData() {
		Adres[] adressen = {
				new Adres("Belgi\u00EB","Brussels","1000","Kerkstraat","1"),
				new Adres("Verenigde Staten","New York","10001","Broadway","20"),
				new Adres("Nederland","Rotterdam","3011","Wolfshoek","7"),
				new Adres("Belgi\u00EB", "Gent", "9000", "Straat", "2"),
				new Adres("Belgi\u00EB", "Gent", "9000", "Regen", "5"),
				new Adres("Belgi\u00EB", "Gent", "9000", "Licht", "10")
		};
		
		//gebruikerId, rol, email, wachtwoord, naam, isActief, adres
		List<Gebruiker> mockGebruikers = Arrays.asList(
				new Gebruiker(1, Rol.LEVERANCIER, "eerste@hotmail.com", "1234", "Mark", true, adressen[0]),
				new Gebruiker(2, Rol.LEVERANCIER, "tweede@hotmail.com", "password", "Mike", true, adressen[1]),
				new Gebruiker(3, Rol.ADMINISTRATOR, "derde@hotmail.com", "123abc", "Joris", true, adressen[2]),
				new Gebruiker(4, Rol.KLANT, "vierde@hotmail.com", "321", "Jeff", true, adressen[1]),
				new Gebruiker(5, Rol.LEVERANCIER, "vijfde@hotmail.com", "654", "Jos", false, adressen[2])
		);
		
		List<Klant> klanten = Arrays.asList(
				new Klant("KlantA", "https://clipground.com/images/placeholder-logo-5.png", "+32123456789", "klant1@hotmail.com", adressen[4]),
				new Klant("KlantB", "https://clipground.com/images/placeholder-logo-5.png", "+32123454321", "klant2@hotmail.com", adressen[3]),
				new Klant("KlantC", "https://clipground.com/images/placeholder-logo-5.png", "+32987654321", "klant3@hotmail.com", adressen[5])
		);
		
		List<Product> producten = List.of(
				new Product("productA", 1000, Stock.STOCK, 500.0),
				new Product("productB", 50000, Stock.STOCK, 4.99),
				new Product("productC", 2100, Stock.STOCK, 19.99),
				new Product("productD", 20, Stock.STOCK, 1119.99),
				new Product("productE", 1, Stock.STOCK, 23000.0)
		);
		
		SecureRandom sr = new SecureRandom();
		
		List<List<Bestelling>> bestellingen = Arrays.asList(
				List.of(
					new Bestelling(49111, new Date(), OrderStatus.GEREGISTREERD, BetalingsStatus.NIET_BETAALD, klanten.get(0), producten.subList(0, sr.nextInt(1, producten.size()))),
					new Bestelling(49112, new Date(), OrderStatus.GEREGISTREERD, BetalingsStatus.BETAALD, klanten.get(1), producten.subList(0, sr.nextInt(1, producten.size())))
				),
				List.of(
					new Bestelling(49114, new Date(), OrderStatus.ONDERWEG, BetalingsStatus.BETAALD, klanten.get(0), producten.subList(0, sr.nextInt(1, producten.size()))),
					new Bestelling(49113, new Date(), OrderStatus.AAN_HET_VERWERKEN, BetalingsStatus.BETAALD, klanten.get(2), producten.subList(0, sr.nextInt(1, producten.size()))),
					new Bestelling(49115, new Date(), OrderStatus.GELEVERD, BetalingsStatus.BETAALD, klanten.get(2), producten.subList(0, sr.nextInt(1, producten.size())))
				),
				List.of(
					
				),
				List.of(
					
				),
				List.of(
					new Bestelling(49116, new Date(), OrderStatus.ONDERWEG, BetalingsStatus.BETAALD, klanten.get(1), producten.subList(0, sr.nextInt(1, producten.size())))
				)
		);
		
		for (int i = 0; i < bestellingen.size(); i++) {
			int gebruikerIndex = i;
			bestellingen.get(i).forEach((bestelling) -> {
				bestelling.setLeverancier(mockGebruikers.get(gebruikerIndex));
				bestelling.setProducten(producten.subList(0, sr.nextInt(1, producten.size())));
			});
		}
		
		for (int i = 0; i < mockGebruikers.size(); i++) {
			mockGebruikers.get(i).setBestellingen(bestellingen.get(i));
		}
		
		for (int i = 0; i < klanten.size(); i++) {
			int huidigeKlantIndex = i;
			
			List<Bestelling> bestellingenVoorKlant = bestellingen.stream()
					.flatMap(lijst -> lijst.stream())
					.filter(bestelling -> bestelling.getKlant() == klanten.get(huidigeKlantIndex))
					.collect(Collectors.toList());
			
			klanten.get(i).setBestellingen(bestellingenVoorKlant);
		}
		
		return mockGebruikers;
	}
}
