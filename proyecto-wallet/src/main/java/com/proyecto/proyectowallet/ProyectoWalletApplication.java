package com.proyecto.proyectowallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.codec.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;


@SpringBootApplication

public class ProyectoWalletApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoWalletApplication.class, args);



	}

}
