package com.inventory.project.Models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Entity
@Table(name = "PCModel")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PCModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "os_name")
    private String os_name;
    @Column(name = "os_architecture")
    private String os_arch;
    @Column(name = "user_name")
    private String user_name;
    @Column(name = "host_name")
    private String hostname;
    @Column(name = "ip_address")
    private String ip_address;
    @Column(name = "mac_address")
    private String mac_address;
    @Column(name = "is_online")
    private boolean is_Online;
    @Column(name = "host_last_seen")
    private String host_last_seen;

    public void init() {
        is_Online = isReachable(ip_address);
    }

    public boolean isReachable(String ip) {
        while (true) {
            try {
                InetAddress inet = InetAddress.getByName(ip);
                if (inet.isReachable(5000)) {
                    return true;
                }
                return false;
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }

    }

}
