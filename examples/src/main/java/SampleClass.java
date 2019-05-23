
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class SampleClass {
    public static void main(String[] args) throws IOException {
        SampleClass s  = new SampleClass();
        s.fetch("http://www.wp.pl");
        s.fetch("http://www.google.com");
        s.fetch("http://www.ladbible.com/news/news-you-can-now-get-a-train-from-wigan-to-north-korea-20190523");
        s.fetch("http://www.ladbible.com/entertainment/film-and-tv-george-rr-martin-says-ending-in-books-will-be-different-from-series-20190521");
    }

    private void fetch(final String address) throws IOException {

        URL url = new URL(address);
        URLConnection conn = url.openConnection();

        try (final BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {

            final StringBuilder sb = new StringBuilder();
            in.lines().forEach(sb::append);

            System.out.println("Content size: " + sb.length());
        }
    }
}