package com.connectfour.object.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BijliBillResponse {

	double total;
	double fixed;
	double energy;
	double fc;
	double duty;
}
