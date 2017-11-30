package com.codecamp.libs;

//Rest Client Manager
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream.PutField;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.util.Log;

public class RestClient {

	private ArrayList<NameValuePair> params;
	private ArrayList<NameValuePair> headers;
	private HashMap<String, FileParam> files;
	private String url;

	private String finalurl;

	private HashMap<String, String> responseheaders;

	private int responseCode;
	private String message;

	private String response = null;

	public enum RequestMethod {
		GET, POST, MultiPartPOST, PUT, DELETE,PUT2
	}

	public String getResponse() {
		return response;
	}

	public String getErrorMessage() {
		return message;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public String getHeader(String key) {
		return this.responseheaders.get(key);
	}

	public String getFinalUrl() {
		return this.finalurl;
	}

	public RestClient(String url) {
		this.url = url;
		params = new ArrayList<NameValuePair>();
		headers = new ArrayList<NameValuePair>();
		this.responseheaders = new HashMap<String, String>();
		this.files = new HashMap<String, FileParam>();
	}

	public void updateURL(String key, String value) {
		this.url = getUrl().replace(key, value);
	}

	public String getUrl() {
		return this.url;
	}

	public void AddParam(String name, String value) {
		params.add(new BasicNameValuePair(name, value));
	}

	public void AddHeader(String name, String value) {
		headers.add(new BasicNameValuePair(name, value));
	}

	public void AddFile(String key, File file, String type) {
		files.put(key, new FileParam(file, type));
	}

	public void AddFile(String key, String filepath, String type) {
		files.put(key, new FileParam(filepath, type));
	}

	public void Execute(RequestMethod method) throws Exception {
		switch (method) {
			case GET: {
				// add parameters
				String combinedParams = "";
				if (!params.isEmpty()) {
					combinedParams += "?";
					for (NameValuePair p : params) {
						String paramString = p.getName() + "="
								+ URLEncoder.encode(p.getValue(), "UTF-8");
						if (combinedParams.length() > 1) {
							combinedParams += "&" + paramString;
						} else {
							combinedParams += paramString;
						}
					}
				}
	
				HttpGet request = new HttpGet(url + combinedParams);
	
				// add headers
				for (NameValuePair h : headers) {
					request.addHeader(h.getName(), h.getValue());
				}
	
				executeRequest(request, url);
				break;
			}
			case POST: {
				HttpPost request = new HttpPost(url);
	
				// add headers
				for (NameValuePair h : headers) {
					request.addHeader(h.getName(), h.getValue());
				}
	
				 if (!params.isEmpty()) {
				 request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
				 }
	
				executeRequest(request, url);
	
				break;
			}
			/**
			 * MultiPartPost is used for posting files and text data to a web server
			 */
			case MultiPartPOST: {
				HttpPost request = new HttpPost(url);
	
				// add headers
				for (NameValuePair h : headers) {
					request.addHeader(h.getName(), h.getValue());
				}
				MultipartEntityBuilder builder = MultipartEntityBuilder.create();
				builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
				if (!params.isEmpty()) {
					for (NameValuePair p : params) {
						builder.addTextBody(p.getName(), p.getValue(),
								ContentType.MULTIPART_FORM_DATA);
					}
				}
				Set<String> keys = files.keySet();
				if (!files.isEmpty())
					for (String key : keys) {
						builder.addBinaryBody(key, files.get(key).getFile(),
								ContentType.create(files.get(key).getContenType()),
								files.get(key).getName());
					}
				;
				final HttpEntity entity = builder.build();
				request.setEntity(entity);
				executeRequest(request, url);
	
				break;
	
			}
			/**
			 * put is used for updating files and text data to a web server
			 */
			case PUT: {
				HttpPut request = new HttpPut(url);
	
				// add headers
				for (NameValuePair h : headers) {
					request.addHeader(h.getName(), h.getValue());
				}
				MultipartEntityBuilder builder = MultipartEntityBuilder.create();
				builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
				if (!params.isEmpty()) {
					for (NameValuePair p : params) {
						builder.addTextBody(p.getName(), p.getValue(),
								ContentType.MULTIPART_FORM_DATA);
					}
				}
				Set<String> keys = files.keySet();
				if (!files.isEmpty())
					for (String key : keys) {
						builder.addBinaryBody(key, files.get(key).getFile(),
								ContentType.create(files.get(key).getContenType()),
								files.get(key).getName());
					}
				;
				final HttpEntity entity = builder.build();
				request.setEntity(entity);
				
				executeRequest(request, url);
	
				break;
	
			}
			case DELETE: {
				// add parameters
				String combinedParams = "";
				if (!params.isEmpty()) {
					combinedParams += "?";
					for (NameValuePair p : params) {
						String paramString = p.getName() + "="
								+ URLEncoder.encode(p.getValue(), "UTF-8");
						if (combinedParams.length() > 1) {
							combinedParams += "&" + paramString;
						} else {
							combinedParams += paramString;
						}
					}
				}
	
				HttpDelete request = new HttpDelete(url + combinedParams);
	
				// add headers
				for (NameValuePair h : headers) {
					request.addHeader(h.getName(), h.getValue());
				}
	
				executeRequest(request, url);
				break;
			}
			case PUT2:
				HttpPut request = new HttpPut(url);
				
				// add headers
				for (NameValuePair h : headers) {
					request.addHeader(h.getName(), h.getValue());
				}
	
				 if (!params.isEmpty()) {
				 request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
				 }
	
				executeRequest(request, url);
	
				break;
			
		}
	}

	private void executeRequest(HttpUriRequest request, String url) {
		HttpClient client = new DefaultHttpClient();

		HttpResponse httpResponse;

		try {
			httpResponse = client.execute(request);
			this.finalurl = request.getURI().toURL().toString();
			responseCode = httpResponse.getStatusLine().getStatusCode();
			message = httpResponse.getStatusLine().getReasonPhrase();
			Header[] head = httpResponse.getAllHeaders();

			for (Header h : head) {
				this.responseheaders.put(h.getName(), h.getValue());
			}

			HttpEntity entity = httpResponse.getEntity();

			if (entity != null) {

				InputStream instream = entity.getContent();
				response = convertStreamToString(instream);

				// Closing the input stream will trigger connection release
				instream.close();
			}

		} catch (ClientProtocolException e) {
			client.getConnectionManager().shutdown();
			e.printStackTrace();
		} catch (IOException e) {
			client.getConnectionManager().shutdown();
			e.printStackTrace();
		} catch (Exception e) {
			client.getConnectionManager().shutdown();
			e.printStackTrace();
		}
	}

	private static String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	private class FileParam {
		private File file;
		private String contentType;
		private String name;

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name
		 *            the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		private void setName() {
			this.name = this.file.getName();
		}

		FileParam(String filepath, String contentType) {
			setContentType(contentType);
			setFile(filepath);
			setName();
		}

		FileParam(File file, String contentType) {
			setContentType(contentType);
			setFile(file);
			setName();

		}

		public File getFile() {
			return file;
		}

		private void setFile(String filepath) {
			this.file = new File(filepath);
		}

		private void setFile(File newfile) {
			this.file = newfile;
		}

		public String getContenType() {
			return contentType;
		}

		private void setContentType(String contentType) {
			this.contentType = contentType;
		}

	}

}
