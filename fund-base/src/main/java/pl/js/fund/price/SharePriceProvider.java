package pl.js.fund.price;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.js.fund.model.Share;
import au.com.bytecode.opencsv.CSVReader;

public class SharePriceProvider extends PriceProvider
{
    private static final Logger log        = LoggerFactory.getLogger(SharePriceProvider.class);
    private final String        USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0";
    private Share               share;

    public Share getShare()
    {
        return share;
    }

    public void setShare(Share share)
    {
        this.share = share;
    }

    @Override
    protected void load()
    {
        CSVReader reader = null;
        String[] nextLine;
        prices = new TreeMap<String, Double>();
        try
        {
            /*
             * reader = new CSVReader(new InputStreamReader(connect()), share.getPriceFileSeparator(), '\'', 2);
             * while ((nextLine = reader.readNext()) != null)
             * {
             * prices.put(nextLine[0].replace("\"", ""),
             * Double.valueOf(nextLine[share.getPricePositionIndex()].replace(',', '.')));
             * }
             */

            BufferedReader r = new BufferedReader(new InputStreamReader(connect()));
            String nl = null;
            while ((nl = r.readLine()) != null)
            {
                System.out.println(nl);
            }
        }
        catch (Exception e)
        {
            log.error("", e);
        }
    }

    private InputStream connect() throws Exception
    {
        HttpClient client = new DefaultHttpClient();
        // http://www.gpwinfostrefa.pl/GPWIS2/pl/quotes/archive/2?dateFrom=2014-06-05&dateTo=2014-06-05&instrumentType=10&isin=PLVCAOC00015
        HttpPost post = new HttpPost("http://www.gpwinfostrefa.pl/GPWIS2/pl/quotes/archive/3");
        // add header
        // post.setHeader("Host", "www.gpwinfostrefa.pl");
        // post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");
        // post.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        // post.setHeader("Accept-Language", "pl,en-us;q=0.7,en;q=0.3");
        // post.setHeader("Accept-Encoding", "gzip, deflate");
        // post.setHeader("Referrer",
        // "http://www.gpwinfostrefa.pl/GPWIS2/pl/quotes/archive/2;jsessionid=wtsphk9VK2GCtotZhwmF7hxS.undefined");
        // post.setHeader("Connection", "keep-alive");
        // post.setHeader("Content-Type", "text/plain; charset=UTF-8");
        // post.setHeader( "Cookie",
        // "JSESSIONID=wtsphk9VK2GCtotZhwmF7hxS.undefined; __gfp_64b=rfqKdgPQJ.V4MRI49nOEybK49fx_Dz4rIUJeXofbhT3.k7; __utma=83722816.256969634.1400141329.1400141329.1401369365.2; __utmz=83722816.1400141329.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __utmb=83722816.2.10.1401369365; __utmc=83722816; papcook=true");
        // post.setHeader("Pragma", "no-cache");
        // post.setHeader("Content-Length", "144");

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("dateFrom", "2014-05-01"));
        urlParameters.add(new BasicNameValuePair("dateTo", "2014-05-29"));
        urlParameters.add(new BasicNameValuePair("instrumentType", "10"));
        urlParameters.add(new BasicNameValuePair("isin", "PLVCAOC00015"));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);

        log.info("Sending 'POST' request to URL : {}", share.evaluatePricingURL());
        log.info("Post parameters : {}", urlParameters);
        log.info("Response Code : {}", response.getStatusLine().getStatusCode());

        return response.getEntity().getContent();
    }
}
