package util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Created by hasee on 2016/7/21.
 */
public class RestUtil
{
	private static RestTemplate restTemplate = new RestTemplate();
	private static HttpHeaders headers = new HttpHeaders();
	public static RestTemplate getSession()
	{
		return null;
	}
	public static <T> T postForObject(String url, Object body, Class<T> clazz)
	{
		ResponseEntity re = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity(body, headers), clazz);
		HttpHeaders res = re.getHeaders();
		if(res.get("Set-Cookie") != null)
		{
			String cookie = res.get("Set-Cookie").get(0);
			cookie = cookie.substring(0, cookie.indexOf(';'));
			if(headers.containsKey("Cookie"))
			{
				headers.set("Cookie", cookie);
			}
			else
			{
				headers.add("Cookie", cookie);
			}
		}
		return (T)re.getBody();
	}

	public static <T> T getForObject(String url, Class<T> clazz)
	{
		return (T)restTemplate.exchange(
				url,
				HttpMethod.GET,
				new HttpEntity(headers),
				clazz
		).getBody();
	}
}
