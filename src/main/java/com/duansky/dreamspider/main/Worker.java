/**
 * @author DuanSky
 * @date 2015年11月17日 下午4:17:40
 * @content 
 */
package com.duansky.dreamspider.main;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.ParserException;

import com.duansky.dreamspider.bean.ErrorPage;
import com.duansky.dreamspider.bean.ParseResult;
import com.duansky.dreamspider.bean.SuccessPage;
import com.duansky.dreamspider.bean.UrlWapper;
import com.duansky.dreamspider.html.HtmlParser;
import com.duansky.dreamspider.html.InputStreamWapper;

public class Worker implements Runnable {

	private Manager manager = null;
	private ParseResult pr = null;
	private DreamSpiderConfig dsc = null;
	CloseableHttpClient httpclient = null;

	public Worker(Manager manager) {
		this.manager = manager;
		this.pr = manager.getPr();
		this.dsc = manager.getDsc();
		this.httpclient = HttpClients.createDefault();
	}

	public void run() {
		while (manager.isAlive()) {
			UrlWapper urlWapper = pr.fetchWaitingUrl();
			if (urlWapper != null) { // the queue is not empty which means there
										// are url waiting for parsing.
				String url = urlWapper.getUrl();
				// this url must below the most deep.
				if (urlWapper.getDeep() > manager.getDsc().getDeep()) 
					continue;
				CloseableHttpResponse response = getResponseByConfig(url);
				if (response != null) {
					StatusLine statusLine = response.getStatusLine();
					int statusCode = statusLine.getStatusCode();
					if (statusCode >= 200 && statusCode < 300)
						successParse(urlWapper, response);
					else
						failureParse(urlWapper, statusLine);
				}
			}
		}
	}

	private void failureParse(UrlWapper urlWapper, StatusLine statusLine) {
		System.out.println("parse " + urlWapper.getUrl() + " failed!====");
		ErrorPage page = new ErrorPage();
		page.setContent(statusLine.getReasonPhrase());
		page.setErrorCode(statusLine.getStatusCode());
		page.setUrlWapper(urlWapper);
		pr.addFailedUrl(page);
	}

	private void successParse(UrlWapper urlWapper,
			CloseableHttpResponse response) {
		try {
			System.out.println("parse " + urlWapper.getUrl() + " success!====");
			SuccessPage page = new SuccessPage();
			HttpEntity httpEntity = response.getEntity();
			
			String encoding = "";
			String content="";
			
			String temp = null;
			if (httpEntity != null) {
				if ((temp = getCharSetByHead(httpEntity)) != null){ //If we can get the charset by the header.
					encoding = temp;
					content=EntityUtils.toString(httpEntity,encoding);
				}
				else{
					byte[] in=EntityUtils.toByteArray(httpEntity);
					InputStreamWapper isw=new InputStreamWapper(in);
					encoding=isw.getCharSet();
					content=isw.getContent();
				}
//				System.out.println(content);
				
				// init parser.
				Parser parser = new Parser();
				parser.setInputHTML(content);
				parser.setEncoding(encoding);
				
				// success parse this page.
				page.setUrlWapper(urlWapper);
				page.setContent(content);
				page.setEncoding(encoding);
				pr.addParseUrl(page);
				// find all urls and add them to waiting urls.
				List<String> urls = new ArrayList<String>();
				for (NodeIterator iter = parser.elements(); iter.hasMoreNodes();) {
					urls.addAll(HtmlParser.getInstance().getUrlList(
							iter.nextNode()));
				}
				List<UrlWapper> urlWappers = new ArrayList<UrlWapper>();
				for (String newUrl : urls) {
					UrlWapper newUrlWapper = new UrlWapper();
					newUrlWapper.setUrl(newUrl);
					newUrlWapper.setDeep(urlWapper.getDeep() + 1);
					urlWappers.add(newUrlWapper);
				}
				pr.addWaitingUrls(urlWappers);
			}
		} catch (ParserException e) {
			e.printStackTrace();
			return;
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		} catch (UnsupportedOperationException e1) {
			e1.printStackTrace();
			return;
		}
	}

	

	private CloseableHttpResponse getResponseByConfig(String url) {
		CloseableHttpResponse response = null;
		if (dsc.isNeedAuthority()) {// This web need login?
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("username", dsc.getUsername()));
			nvps.add(new BasicNameValuePair("password", dsc.getPassword()));
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(nvps));
				response = httpclient.execute(httpPost);
				return response;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return null;
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			try {
				HttpGet httpGet = new HttpGet(url);
				response = httpclient.execute(httpGet);
				return response;
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	// The charset may not exists in header.
	private String getCharSetByHead(final HttpEntity entity) throws ParseException {
		if (entity == null) {
			return null;
		}
		String charset = null;
		if (entity.getContentType() != null) {
			HeaderElement values[] = entity.getContentType().getElements();
			if (values.length > 0) {
				NameValuePair param = values[0].getParameterByName("charset");
				if (param != null) {
					charset = param.getValue();
				}
			}
		}
		return charset;
	}

}
