package com.connectfour.manager;

import java.util.Map;
import java.util.TreeMap;

import com.connectfour.object.responses.BijliBillResponse;
import com.connectfour.objects.TariffObject;
import com.connectfour.objects.requests.BijliBillRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BijliManager {

	
	static TreeMap<Integer, Integer> tariff = new TreeMap<Integer, Integer>();
	static TreeMap<Integer, TariffObject> urban = new TreeMap<Integer, TariffObject>();
	static TreeMap<Integer, TariffObject> rural = new TreeMap<Integer, TariffObject>();
	
	static {
		tariff.put(50, 385);
		tariff.put(100, 470);
		tariff.put(300, 600);
		tariff.put(Integer.MAX_VALUE, 630);
		
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
		for(Map.Entry<Integer, Integer> entry: tariff.entrySet())
		{
			int value = entry.getKey() < net? entry.getKey(): net;
			int next = value - base;
			amount += 1.0 * (next) * entry.getValue();
			base = entry.getKey();
			if(net - base < 0) break;
		}
		TreeMap<Integer, TariffObject> fixedRate = request.isUrban()? urban: rural;
		
		amount = amount/100;
		TariffObject tariffObject = fixedRate.floorEntry(net).getValue();
		double fixed = 0.0;
		if(tariffObject.isFixed()) fixed=tariffObject.getRate();
		else fixed= Math.ceil(net/75.0)*tariffObject.getRate();
		
		double fc = net * 0.16;
		
		return new BijliBillResponse(fixed+amount+fc, fixed, amount, fc, 0);
	}
	
	public static void main (String[] args) throws Exception
	{
		BijliBillRequest request = new BijliBillRequest(0, 301, true);
		ObjectMapper mapper = new ObjectMapper();
		
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(getBillAmount(request)));
	}

}
