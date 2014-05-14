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
        HttpPost post = new HttpPost("http://www.money.pl/gielda/archiwum/spolki/");

        // add header
        post.setHeader("User-Agent", USER_AGENT);
        post.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        post.setHeader("Accept-Language", "pl,en-us;q=0.7,en;q=0.3");
        post.setHeader("Accept-Encoding", "gzip, deflate");
        post.setHeader("Referer", "http://www.money.pl/gielda/archiwum/spolki/");
        post.setHeader(
                "Cookie",
                "usertrack=3240d63c963a18317a487092b77787aa; node_id=3; __utma=148686129.556853546.1400070503.1400070503.1400070503.1; __utmb=148686129.8.10.1400070503; __utmc=148686129; __utmz=148686129.1400070503.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __utma=126045522.1970658251.1400070503.1400070503.1400070503.1; __utmb=126045522.8.10.1400070503; __utmc=126045522; __utmz=126045522.1400070503.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __gfp_64b=rfqKdgPQJ.V4MRI49nOEybK49fx_Dz4rIUJeXofbhT3.k7; leadu2=%7B%22u%22%3A%22809c9018eabf319b8b7f510146372fb4%22%2C%22d%22%3A1400071603%2C%22c%22%3A0%7D");
        post.setHeader("Connection", "keep-alive");

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("symbol", share.getId()));
        urlParameters.add(new BasicNameValuePair("od", "1914-05-14"));
        urlParameters.add(new BasicNameValuePair("do", "2014-05-14"));
        urlParameters.add(new BasicNameValuePair("period", "-100+year"));
        urlParameters.add(new BasicNameValuePair("format", "csv"));
        urlParameters.add(new BasicNameValuePair("show", "Poka%BF"));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);

        log.info("Sending 'POST' request to URL : {}", share.evaluatePricingURL());
        log.info("Post parameters : {}", urlParameters);
        log.info("Response Code : {}", response.getStatusLine().getStatusCode());

        return response.getEntity().getContent();
    }
}
