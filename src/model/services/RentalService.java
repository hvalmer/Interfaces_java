package model.services;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {

	private Double pricePerDay;
	private Double pricePerHour;
	
	private BrazilTaxService taxService;

	public RentalService(Double pricePerDay, Double pricePerHour, BrazilTaxService taxService) {
		this.pricePerDay = pricePerDay;
		this.pricePerHour = pricePerHour;
		this.taxService = taxService;
	}
	
	//valor básico de pagamento
	public void processInVoice(CarRental carRental) {
		long time1 = carRental.getStart().getTime();
		long time2 = carRental.getFinish().getTime();
		double hours = (double)(time2 - time1) / 1000 / 60 / 60;
				
		double basicPayment;
		if(hours <= 12.0) {
			basicPayment = Math.ceil(hours) * pricePerHour;//arredondando para cima/hora
		}
		else {
			basicPayment = Math.ceil(hours / 24) * pricePerDay;//arredondando para cima/dia
		}
		
		//cálculo do valor do imposto
		double tax = taxService.tax(basicPayment);
		
		carRental.setInvoice(new Invoice(basicPayment, tax));
	}
}
