package org.example;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Main {
    public static void main(String[] args) throws InterruptedException {

        while (true) {
            String os_arch = System.getProperty("os.arch");
            String os_name = System.getProperty("os.name");
            String userName = System.getProperty("user.name");
            String ip_address = getIpAddress();
            String mac_address = getMacAddress();
            String hostname = getHostname();
            String dateLastSeen = lastSeenHostOnline();

            System.out.println("Connecting to server for create a POST request");

            String payload = String.format("""
                    data={
                        "os_name": %s,
                        "os_arch": %s,
                        "user_name": %s,
                        "hostname": %s,
                        "ip_address": %s,
                        "mac_address": %s,
                        "last_seen" : %s
                    }
                    """, os_name, os_arch, userName, hostname, ip_address, mac_address, dateLastSeen);

            StringEntity entity = new StringEntity(payload,
                    ContentType.APPLICATION_FORM_URLENCODED);

            try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                HttpPost request = new HttpPost("http://localhost:8081/pcs_list");
                request.setEntity(entity);

                HttpResponse response = (HttpResponse) httpClient.execute(request);
                System.out.println(response.getStatusLine().getStatusCode());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Thread.sleep(600000);
        }
    }

    private static String getIpAddress() {
        try (final DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("8.8.8.8"), 12345);
            return socket.getLocalAddress().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getMacAddress() {
        InetAddress localHost = null;
        try {
            localHost = InetAddress.getLocalHost();
            NetworkInterface ni = NetworkInterface.getByInetAddress(localHost);
            byte[] hardwareAddress = ni.getHardwareAddress();
            String[] hexadecimal = new String[hardwareAddress.length];
            for (int i = 0; i < hardwareAddress.length; i++) {
                hexadecimal[i] = String.format("%02X", hardwareAddress[i]);
            }
            return String.join(":", hexadecimal);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getHostname() {
        try {
            InetAddress id = InetAddress.getLocalHost();
            return id.getHostName();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    private static String lastSeenHostOnline() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm Z dd.MM.yyyy");
        return format.format(now);
    }
}