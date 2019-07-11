package com.example.websocket;



import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.common.io.BaseEncoding;


public class MercadoOEC {
	private static String MB_TAPI_ID = "818575552cd00e5025c6b7dce07d6120";
	private String MB_TAPI_SECRET = "064432727fd30384f10e8fbc2471bb0952f2b5467d5e1c876ae75fb34e964dfa";
	private static String REQUEST_HOST = "www.mercadobitcoin.net";
	private static String REQUEST_PATH = "/tapi/v3/";
	private static String SECRET = "b'064432727fd30384f10e8fbc2471bb0952f2b5467d5e1c876ae75fb34e964dfa";
	private static final String DEFAULT_ENCODING = "UTF-8";
	private static final String HMAC_SHA512 = "HmacSHA512";
	private String tapi_method;
	private String tapi_nonce;
	private String coin_pair;
	private String quantity;
	private String limit_price;
	private static  String tapi_mac1="074957d9600a5f0eeffe295b5ddf6525d24ae61819d4442a4e66e08b5eb815ea232f744c919026cf20f8957ccc24903d3967f3b927622c9da8184253f32e43d5";
	private static final String tapi_mac=tapi_mac1.trim();
	//public static final MediaType JSON= MediaType.parse("application/json; charset=utf-8");

	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, IOException, URISyntaxException {
		
		MercadoOEC m=new MercadoOEC();
		m.Buyinputs("place_buy_order", "1", "BRLBTC", "0.01", "14000.001");
		String url1="/tapi/v3/?tapi_method=place_buy_order&tapi_nonce=1&coin_pair=BRLBTC&quantity=0.01&limit_price=14000.001";
		//byte[] urlEncoded=url1.getBytes("UTF-8");
		byte[] temp=hmacSha512(url1, MercadoOEC.SECRET);
		String ss=asHex(temp);
		System.out.println("ss: "+ss);
		m.getResults();
		System.out.println("tapimac\n"+tapi_mac+" ends");

	}
	public void Buyinputs(String tapi_method, String tapi_nonce ,String coin_pair,String quantity,String limit_price) {
		 this.tapi_method=tapi_method;
		this.tapi_nonce= tapi_nonce;
		this.coin_pair= coin_pair;
		this.quantity= quantity;
		this.limit_price= limit_price;
	}

	public String hashgen(String s) {
		
		return s;
	}
	public static byte[] hmacSha512(String value, String key){
	    try {
	        SecretKeySpec keySpec = new SecretKeySpec(
	                key.getBytes(DEFAULT_ENCODING),
	                HMAC_SHA512);
	                
	 
	        Mac mac = Mac.getInstance(HMAC_SHA512);
	        mac.init(keySpec);
	        return mac.doFinal(value.getBytes(DEFAULT_ENCODING));
	 
	    } catch (NoSuchAlgorithmException e) {
	        throw new RuntimeException(e);
	    } catch (InvalidKeyException e) {
	        throw new RuntimeException(e);
	    } catch (UnsupportedEncodingException e) {
	        throw new RuntimeException(e);
	    }
	}
	public static String asHex(byte[] bytes){
	    return BaseEncoding.base16().lowerCase().encode(bytes);
	}
	private String getResults() throws IOException, URISyntaxException {
        
       CloseableHttpClient httpclient = HttpClients.createDefault();
        
        System.out.println("1");
        URI uri = new URIBuilder()
                .setScheme("https")
                .setHost(REQUEST_HOST)
                .setPath(REQUEST_PATH)
                .build();
        System.out.println("uri: "+uri.toString());
        System.out.println("2");
       
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://"+REQUEST_HOST+REQUEST_PATH);
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setHeader("TAPI-ID", MB_TAPI_ID);
        httpPost.setHeader("TAPI-MAC", tapi_mac);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tapi_method",this.tapi_method));
        params.add(new BasicNameValuePair("tapi_nonce", this.tapi_nonce));
        params.add(new BasicNameValuePair("coin_pair", this.coin_pair));
        params.add(new BasicNameValuePair("quantity", this.quantity));
        params.add(new BasicNameValuePair("limit_price", this.limit_price));
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        CloseableHttpResponse response = client.execute(httpPost);
        InputStream result= response.getEntity().getContent();
       String s= EntityUtils.toString(response.getEntity());
       System.out.println("result: "+s);
      
        return s;
    }

}

