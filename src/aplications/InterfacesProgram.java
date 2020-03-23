package aplications;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import model.entities.CarRental;
import model.entities.Vehicle;
import model.services.BrazilTaxService;
import model.services.RentalService;

public class InterfacesProgram {

	public static void main(String[] args) throws ParseException {

		Locale.setDefault(Locale.US);
		Scanner sca = new Scanner(System.in);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:ss");
		
		System.out.println("Enter rental data");
		System.out.print("Car model: ");
		String carModel = sca.nextLine();
		System.out.print("Pickup (dd/MM/yyyy hh:ss): ");
		Date start = sdf.parse(sca.nextLine());
		System.out.print("Return (dd/MM/yyyy hh:ss): ");
		Date finish = sdf.parse(sca.nextLine());
		
		//objeto de alugel de carro
		CarRental  cr = new CarRental(start, finish, new Vehicle(carModel));
		
		//o usuário digita o preço por hora e por dia
		System.out.print("Enter price per hour: ");
		double pricePerHour = sca.nextDouble();
		System.out.print("Enter price per day: ");
		double pricePerDay = sca.nextDouble();
		
		//instanciando o serviço de aluguel
		RentalService rentalService = new RentalService(pricePerDay, pricePerHour, new BrazilTaxService());
		
		rentalService.processInVoice(cr);
		
		System.out.println("INVOICE:");
		System.out.println("Basic payment: " + String.format("%.2f", cr.getInvoice().getBasicPayment()));
		System.out.println("Tax: " + String.format("%.2f", cr.getInvoice().getTax()));
		System.out.println("Total payment: " + String.format("%.2f", cr.getInvoice().getTotalPayment()));
		
		sca.close();
	}
}
