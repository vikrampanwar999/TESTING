package com.example.websocket;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.google.common.base.Charsets;

public class TestFun {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s="/tapi/v3/?tapi_method=place_buy_order&tapi_nonce=1&coin_pair=BRLBTC&quantity=0.01&limit_price=14000.001";
		//
		//byte[] byteArray=s.getBytes(Charsets.UTF_8);
		//String snew= new String(byteArray, StandardCharsets.US_ASCII);
		//System.out.println("snew: "+byteArray.toString());
		 String SECRET = "064432727fd30384f10e8fbc2471bb0952f2b5467d5e1c876ae75fb34e964dfa";
	  String tapi_mac1="074957d9600a5f0eeffe295b5ddf6525d24ae61819d4442a4e66e08b5eb815ea232f744c919026cf20f8957ccc24903d3967f3b927622c9da8184253f32e43d5";

		 String res=get_SHA_512_SecurePassword(s, SECRET);
		 System.out.println(res);
		 if(res.equals(tapi_mac1)) {
			 System.out.println("SUCCESS");
		 }
		 else {
			 System.out.println("UNSUCCESS");
		 }
		 if(res.length()==tapi_mac1.length()) {
			 System.out.println("length is same");
		 }
		 else {
			 System.out.println("length is also not same");

		 }
	}
	public static String get_SHA_512_SecurePassword(String passwordToHash, String   salt){
		String generatedPassword = null;
		    try {
		         MessageDigest md = MessageDigest.getInstance("SHA-512");
		         md.update(salt.getBytes(StandardCharsets.UTF_8));
		         byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
		         StringBuilder sb = new StringBuilder();
		         for(int i=0; i< bytes.length ;i++){
		            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		         }
		         generatedPassword = sb.toString();
		        } 
		       catch (NoSuchAlgorithmException e){
		        e.printStackTrace();
		       }
		    return generatedPassword;
		}
}
