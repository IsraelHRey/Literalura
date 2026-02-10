package com.alura.literalura;

import com.alura.literalura.model.Datos;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvertirDatos;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class LiteraluraApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(LiteraluraApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        ConsumoAPI consumoApi = new ConsumoAPI();
        var json = consumoApi.obtenerDatos("https://gutendex.com/books/");
        System.out.println(json);


        ConvertirDatos conversor = new ConvertirDatos();
        var datos = conversor.obtenerDatos(json, Datos.class);
        System.out.println(datos);

    }
}


