package com.alura.literalura;

import com.alura.literalura.model.Datos;
import com.alura.literalura.principal.Principal;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvertirDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(LiteraluraApplication.class, args);
    }

    @Autowired
    private LibroRepository repository;
    @Autowired
    private AutorRepository autorRepository;

    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal(repository, autorRepository);
        principal.muestraElMenu();
    }
}


