package com.connectfour.manager;

import java.util.Map;
import java.util.TreeMap;

import com.connectfour.object.responses.BijliBillResponse;
import com.connectfour.objects.EnergyChargeObject;
import com.connectfour.objects.TariffObject;
import com.connectfour.objects.requests.BijliBillRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BijliManager {

	
	static TreeMap<Integer, EnergyChargeObject> tariff = new TreeMap<Integer, EnergyChargeObject>();
	static TreeMap<Integer, TariffObject> urban = new TreeMap<Integer, TariffObject>();
	static TreeMap<Integer, TariffObject> rural = new TreeMap<Integer, TariffObject>();
	static TreeMap<Integer, Double> duty = new TreeMap<Integer, Double>();
	
	static {
		tariff.put(50, new EnergyChargeObject(385,16,0.09)); // 385 (energy charge) + 16 (fc)
		tariff.put(100, new EnergyChargeObject(470,16,0.09)); // 470 (energy charge) + 16 (fc)
		tariff.put(300, new EnergyChargeObject(600,16,0.12)); // 600 (energy charge) + 16 (fc)
		tariff.put(Integer.MAX_VALUE, new EnergyChargeObject(630,16,0.12));
		
		duty.put(100, 0.09);
		duty.put(Integer.MAX_VALUE, 0.12);
		
		urban.put(0, new TariffObject(true, 50));
		urban.put(51, new TariffObject(true, 90));
		urban.put(101, new TariffObject(false, 100));
		urban.put(301, new TariffObject(false, 110));
		
		rural.put(0, new TariffObject(true, 35));
		rural.put(51, new TariffObject(true, 65));
		rural.put(101, new TariffObject(false, 85));
		rural.put(301, new TariffObject(false, 105));
	}
	
	public BijliManager() {

	}
	
	private static BijliBillResponse getTwoOneBillAmount(BijliBillRequest request)
	{
		System.out.println("2.1");
		int kw = request.getKilowatt();
		int minUnits = kw *20;
		if (!request.isUrban())
			minUnits = kw *15;
		
		int units = request.getCurrent() - request.getPrevious();
		if(minUnits > units)
			units = minUnits;
		
		final double energyChargeRate = 6.26;
		final int fixedChargeRate;
		
		if(kw <= 10)
		{
			if(request.isUrban())
				fixedChargeRate = 130;
			else 
				fixedChargeRate = 100;
		}
		else
		{
			if(request.isUrban())
				fixedChargeRate = 240;
			else 
				fixedChargeRate = 200;
		}
				
		double energyCharge = units * energyChargeRate;
		double fixedCharge = fixedChargeRate * kw;
		double duty = 0.11 * energyCharge;
		return new BijliBillResponse(energyCharge + fixedCharge + duty, fixedCharge, energyCharge, 0, duty, 0);
	}
	
	private static BijliBillResponse getTwoTwoBillAmount(BijliBillRequest request)
	{
		System.out.println("2.2");
		int kw = request.getKilowatt();
		
		int consumedUnits = request.getCurrent() - request.getPrevious();
		int minUnits = kw *20;
		int units = consumedUnits;
		if(minUnits > units)
			units = minUnits;
		
		final double energyChargeRate;
		final double fixedChargeRate;
		
		if(kw <= 10 && consumedUnits <=50)
		{
				energyChargeRate = 6.20;
				if(request.isUrban())
					fixedChargeRate = 70;
				else
					fixedChargeRate = 55;				
		}
		else if (kw <= 10)
		{
			energyChargeRate = 7.4;
			if(request.isUrban())
				fixedChargeRate = 115;
			else
				fixedChargeRate = 100;
		}
		else
		{
			energyChargeRate = 6.4;
			if(request.isUrban())
				fixedChargeRate = 260;
			else
				fixedChargeRate = 190;
		}
		
		double energyCharge = units * energyChargeRate;
		double fixedCharge = fixedChargeRate * kw;
		double duty = 0.11 * energyCharge;
		return new BijliBillResponse(energyCharge + fixedCharge + duty, fixedCharge, energyCharge, 0, duty, 0);
	}
	
	
	private static BijliBillResponse getCommercialBillAmount(BijliBillRequest request)
	{
		if(request.getLevel().equals("2.1"))
			return getTwoOneBillAmount(request);
		return getTwoTwoBillAmount(request);
	}
	
	public static BijliBillResponse getBillAmount(BijliBillRequest request) {
		
		if(request.isCommercial())
			return getCommercialBillAmount(request);
			
		int net = request.getCurrent() - request.getPrevious();
		double amount = 0;
	
		int temp = net;
		int base = 0;
		double dutyCharges = 0;
		for(Map.Entry<Integer, EnergyChargeObject> entry: tariff.entrySet())
		{
			int value = entry.getKey() < net? entry.getKey(): net;
			int next = value - base;
			double thisBracket = 1.0 * (next) * (entry.getValue().getEnergy() + entry.getValue().getFc());
			dutyCharges += thisBracket *entry.getValue().getDuty();
			amount += thisBracket;
			//System.out.println(next + " : " + thisBracket/100 + " : " + dutyCharges/100);
			base = entry.getKey();
			if(net - base < 0) break;
		}
		TreeMap<Integer, TariffObject> fixedRate = request.isUrban()? urban: rural;
		
		if(amount < 6000)
		{
			amount = 6000;
			dutyCharges = amount * 0.09;
		}
		
		amount = amount/100;
		dutyCharges = dutyCharges/100;
		TariffObject tariffObject = fixedRate.floorEntry(net).getValue();
		double fixed = 0.0;
		if(tariffObject.isFixed()) fixed=tariffObject.getRate();
		else fixed= Math.ceil(net/75.0)*tariffObject.getRate();
		
		double metreRent = request.getPhase().equalsIgnoreCase("single")? 10:25;

		return new BijliBillResponse(fixed+amount+dutyCharges+metreRent, fixed, amount, 0, dutyCharges, metreRent);
	}
	
	public static void main (String[] args) throws Exception
	{
		BijliBillRequest request = new BijliBillRequest(false, "2.2", 2,0, 10, false, "single");
		ObjectMapper mapper = new ObjectMapper();
		
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(getBillAmount(request)));
	}

}
