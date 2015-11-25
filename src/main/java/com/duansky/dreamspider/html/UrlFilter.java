/**
* @author DuanSky
* @date 2015年11月17日 下午5:31:55
* @content 
*/
package com.duansky.dreamspider.html;

import java.util.regex.Pattern;

public class UrlFilter {

	private String domain;
	private String urlDomainPattern;
	private String urlPattern;
	private Pattern pattern;
	private Pattern patternPost;
	

	public UrlFilter(){}
	
	public UrlFilter(String domain){
		this.domain=domain;
		urlDomainPattern="(http://[^/]*?)"+domain+"/)(.*?)";
		urlPattern="(http://[^/]*?"+domain+"/[^.]*?).(shtml|html|htm|shtm|php|asp#|cgi|jsp|aspx)";
		pattern=Pattern.compile(urlDomainPattern,Pattern.CASE_INSENSITIVE+Pattern.DOTALL);
		patternPost=Pattern.compile(urlPattern,Pattern.CASE_INSENSITIVE+Pattern.DOTALL);
	}
	
	public UrlFilter(String domain,String urlDomainPattern,String urlPattern){
		this.domain=domain;
		this.urlDomainPattern=urlDomainPattern;
		this.urlPattern=urlPattern;
		pattern=Pattern.compile(urlDomainPattern,Pattern.CASE_INSENSITIVE+Pattern.DOTALL);
		patternPost=Pattern.compile(urlPattern,Pattern.CASE_INSENSITIVE+Pattern.DOTALL);
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getUrlDomainPattern() {
		return urlDomainPattern;
	}

	public void setUrlDomainPattern(String urlDomainPattern) {
		this.urlDomainPattern = urlDomainPattern;
	}

	public String getUrlPattern() {
		return urlPattern;
	}

	public void setUrlPattern(String urlPattern) {
		this.urlPattern = urlPattern;
	}

	public Pattern getPattern() {
		return pattern;
	}

	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}

	public Pattern getPatternPost() {
		return patternPost;
	}

	public void setPatternPost(Pattern patternPost) {
		this.patternPost = patternPost;
	}
	
}
