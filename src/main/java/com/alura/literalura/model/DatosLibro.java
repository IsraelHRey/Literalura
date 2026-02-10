package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(@JsonAlias ("title") String titulo,
                         @JsonAlias ("authors")List<DatosAutor> autores,
                         @JsonAlias ("lenguages") List<String> idiomas,
                         @JsonAlias ("download_count") Double numeroDeDescargas
) {
}
