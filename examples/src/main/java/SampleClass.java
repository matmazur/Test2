
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class SampleClass {
    public static void main(String[] args) throws IOException {
        fetch("http://www.google.com");
        fetch("https://www.gry-online.pl");
    }

    private static void fetch(final String address) throws IOException {

        final URL url = new URL(address);
        final URLConnection connection = url.openConnection();


        try (final BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()))) {

            final StringBuilder sb = new StringBuilder();
            in.lines().forEach(str -> sb.append(str));

            System.out.println("Content size: " + sb.length());
        }
    }
}