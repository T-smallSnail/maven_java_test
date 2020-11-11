package com.bmcc.vgop.service.crmsc;

import java.util.Random;

public class TestInMain {

	public static void main(String[] args) throws Exception {
//		CrmScServiceImpl s = new CrmScServiceImpl();
//		ServiceResponse r= s.doCrmService("15811346765", "CXYL", "1", null, null);
//		System.out.println(r.getServiceBody().getRESULT());
		for(int i =0;i<10;i++) {
			System.out.println(new Random().nextInt(1) + 1);
		}
	}
}
