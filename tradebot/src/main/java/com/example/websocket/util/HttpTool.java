package com.example.websocket.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.web.context.request.WebRequest;

public class HttpTool {

	private static final int TIME_OUT = 8 * 1000; // 超时时间
	private static final String CHARSET = "utf-8"; // 编码格式
	private static final String PREFIX = "--"; // 前缀
	private static final String BOUNDARY = UUID.randomUUID().toString(); // 边界标识
																			// 随机生成
	private static final String CONTENT_TYPE = "multipart/form-data"; // 内容类型
	private static final String LINE_END = "\r\n"; // 换行

	/**
	 * 发送post请求
	 * 
	 * @param params
	 *            参数
	 * @param requestUrl
	 *            请求地址
	 * @param authorization
	 *            授权书
	 * @return 返回结果
	 * @throws IOException
	 */
	public static String sendPost(String params, String requestUrl, String authorization) throws IOException {

		HttpClient httpClient = new HttpClient();// 客户端实例化
		PostMethod postMethod = new PostMethod(requestUrl);
		// 设置请求头Authorization
		if (authorization != null && !"".equals(authorization)) {
			postMethod.setRequestHeader("Authorization", authorization);
		}
		// 设置请求头 Content-Type
		postMethod.setRequestHeader("Content-Type", "application/json");

		RequestEntity se = new StringRequestEntity(params, "application/json", "UTF-8");
		postMethod.setRequestEntity(se);
		// 使用系统提供的默认的恢复策略
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		 RequestEntity requestEntity = new StringRequestEntity(params,
		 "application/json", "utf-8");
		 postMethod.setRequestEntity(requestEntity);
//		 NameValuePair message = new NameValuePair( params, authorization);//post请求必须使用
//		 postMethod.setRequestBody(new NameValuePair[]{message});

		httpClient.executeMethod(postMethod);// 执行请求
		InputStream soapResponseStream = postMethod.getResponseBodyAsStream();// 获取返回的流
		byte[] datas = null;
		try {
			datas = readInputStream(soapResponseStream);// 从输入流中读取数据
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = new String(datas, "UTF-8");// 将二进制流转为String
		// 打印返回结果
		// System.out.println(result);

		
		return result;

	}

	/**
	 * 发送post请求
	 * 
	 * @param params
	 *            参数
	 * @param requestUrl
	 *            请求地址
	 * @param authorization
	 *            授权书
	 * @return 返回结果
	 * @throws IOException
	 */
	public static String sendPatch(String params, String requestUrl, String authorization) throws IOException {

		HttpClient httpClient = new HttpClient();// 客户端实例化
		PostMethod postMethod = new PostMethod(requestUrl);
		// 设置请求头Authorization
		if (authorization != null && !"".equals(authorization)) {
			postMethod.setRequestHeader("Authorization", authorization);
		}
		// 设置请求头 Content-Type
		postMethod.setRequestHeader("Content-Type", "application/json");

		RequestEntity se = new StringRequestEntity(params, "application/json", "UTF-8");
		postMethod.setRequestEntity(se);
		// 使用系统提供的默认的恢复策略
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		// RequestEntity requestEntity = new StringRequestEntity(params,
		// "application/json", "utf-8");
		// postMethod.setRequestEntity(requestEntity);
		// NameValuePair message = new NameValuePair( params);//post请求必须使用
		// NameValuePair 类传递参数
		// postMethod.setRequestBody(new NameValuePair[]{message});

		httpClient.executeMethod(postMethod);// 执行请求
		System.out.println(postMethod.getStatusCode()+" ........"+postMethod.getStatusText() );
		InputStream soapResponseStream = postMethod.getResponseBodyAsStream();// 获取返回的流
		byte[] datas = null;
		try {
			datas = readInputStream(soapResponseStream);// 从输入流中读取数据
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = new String(datas, "UTF-8");// 将二进制流转为String
		// 打印返回结果
		System.out.println(result);

		return result;

	}

	/**
	 * 从输入流中读取数据
	 * 
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inStream.close();
		return data;
	}

	public static String sendGet(String url, String authorization) {
		// 输入流
		InputStream is = null;
		BufferedReader br = null;
		String result = null;
		// 创建httpClient实例
		HttpClient httpClient = new HttpClient();
		// 设置http连接主机服务超时时间：15000毫秒
		// 先获取连接管理器对象，再获取参数对象,再进行参数的赋值
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(15000);
		// 创建一个Get方法实例对象
		GetMethod getMethod = new GetMethod(url);
		// 设置get请求超时为60000毫秒
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);
		// 设置请求重试机制，默认重试次数：3次，参数设置为true，重试机制可用，false相反
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, true));

		if (authorization != null && !"".equals(authorization)) {
			getMethod.setRequestHeader("Authorization", authorization);
		}

		// 设置请求头 Content-Type
		getMethod.setRequestHeader("Content-Type", "application/json");
		try {
			// 执行Get方法
			int statusCode = httpClient.executeMethod(getMethod);
			// 判断返回码
			if (statusCode != HttpStatus.SC_OK) {
				// 如果状态码返回的不是ok,说明失败了,打印错误信息
				System.err.println("Method faild: " + getMethod.getStatusLine());
			} else {
				// 通过getMethod实例，获取远程的一个输入流
				is = getMethod.getResponseBodyAsStream();
				// 包装输入流
				br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

				StringBuffer sbf = new StringBuffer();
				// 读取封装的输入流
				String temp = null;
				while ((temp = br.readLine()) != null) {
					sbf.append(temp).append("\r\n");
				}

				result = sbf.toString();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭资源
			if (null != br) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != is) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			// 释放连接
			getMethod.releaseConnection();
		}
		return result;
	}

	/**
	 * post请求方法
	 */
	public static String postRequest(final String requestUrl, final Map<String, String> strParams,
			final Map<String, File> fileParams, final String authorization) {
		HttpURLConnection conn = null;
		try {
			URL url = new URL(requestUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setReadTimeout(TIME_OUT);
			conn.setConnectTimeout(TIME_OUT);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);// Post 请求不能使用缓存
			// 设置请求头参数
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
			conn.addRequestProperty("Authorization", authorization);
			/**
			 * 请求体
			 */
			// 上传参数
			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
			// getStrParams()为一个
			dos.writeBytes(getStrParams(strParams).toString());
			dos.flush();

			// 文件上传
			StringBuilder fileSb = new StringBuilder();
			for (Map.Entry<String, File> fileEntry : fileParams.entrySet()) {
				fileSb.append(PREFIX).append(BOUNDARY).append(LINE_END)
						/**
						 * 这里重点注意： name里面的值为服务端需要的key 只有这个key 才可以得到对应的文件
						 * filename是文件的名字，包含后缀名的 比如:abc.png
						 */
						.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + fileEntry.getKey() + "\""
								+ LINE_END)
						.append("Content-Type: image/jpg" + LINE_END) // 此处的ContentType不同于
																		// 请求头
																		// 中Content-Type
						.append("Content-Transfer-Encoding: 8bit" + LINE_END).append(LINE_END);// 参数头设置完以后需要两个换行，然后才是参数内容
				dos.writeBytes(fileSb.toString());
				dos.flush();
				InputStream is = new FileInputStream(fileEntry.getValue());
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					dos.write(buffer, 0, len);
				}
				is.close();
				dos.writeBytes(LINE_END);
			}
			// 请求结束标志
			dos.writeBytes(PREFIX + BOUNDARY + PREFIX + LINE_END);
			dos.flush();
			dos.close();
			System.out.println(conn.getResponseCode());
			// 读取服务器返回信息
			if (conn.getResponseCode() == 201 || conn.getResponseCode() == 200) {
				InputStream in = conn.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				String line = null;
				StringBuilder response = new StringBuilder();
				while ((line = reader.readLine()) != null) {
					response.append(line);

				}
				return response.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return "";

	}

	/**
	 * 对post参数进行编码处理
	 */
	private static StringBuilder getStrParams(Map<String, String> strParams) {
		StringBuilder strSb = new StringBuilder();
		for (Map.Entry<String, String> entry : strParams.entrySet()) {
			strSb.append(PREFIX).append(BOUNDARY).append(LINE_END)
					.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINE_END)
					.append("Content-Type: text/plain; charset=" + CHARSET + LINE_END)
					.append("Content-Transfer-Encoding: 8bit" + LINE_END).append(LINE_END)// 参数头设置完以后需要两个换行，然后才是参数内容
					.append(entry.getValue()).append(LINE_END);
		}
		return strSb;
	}

	
	
	
	
	
	public static void main(String[] args) {
		String plainCredentials = "user1234@example.com:P@ssword1!";
		String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));
		String resurl = sendGet("https://sandbox.primetrust.com/v2/users",
				"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdXRoX2lkIjoiM2E5NjA3OWEtYjUyZS00MDE4LTgxZmItNGFiZTY2YTdkOWYzIiwiZXhwIjoxNTMzMTk3NzUwfQ.niX8ik-NG_CwGkJmsiZ0H7gUOaSyWq5whyWoioyy5QU");
		System.out.println(resurl);

	}
}