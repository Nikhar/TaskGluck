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
	
	public static BijliBillResponse getBillAmount(BijliBillRequest request) {

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
		BijliBillRequest request = new BijliBillRequest(0, 300, true, "single");
		ObjectMapper mapper = new ObjectMapper();
		
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(getBillAmount(request)));
	}

}
