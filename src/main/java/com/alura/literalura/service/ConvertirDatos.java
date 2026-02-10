package com.alura.literalura.service;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertirDatos implements IConvertirDatos {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json, clase);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            throw new RuntimeException("Error al deserializar el JSON", e);
        }
    }
}
