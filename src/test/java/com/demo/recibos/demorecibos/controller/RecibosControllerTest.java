package com.demo.recibos.demorecibos.controller;

import com.demo.recibos.demorecibos.canonical.request.BuscarReciboRequest;
import com.demo.recibos.demorecibos.exception.GlobalExceptionHandler;
import com.demo.recibos.demorecibos.exception.ReciboNotFoundException;
import com.demo.recibos.demorecibos.model.Recibo;
import com.demo.recibos.demorecibos.services.ReciboService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class RecibosControllerTest {
    @Test
    public void buscarPorSuministroTest() {

        // Crear un mock de ReciboService
        ReciboService reciboServiceMock = mock(ReciboService.class);

        // Crear una instancia de RecibosController
        RecibosController recibosController = new RecibosController();
        recibosController.reciboService = reciboServiceMock;

        // Crear una instancia de BuscarReciboRequest
        BuscarReciboRequest buscarReciboRequest = new BuscarReciboRequest();
        buscarReciboRequest.setNumeroDeSuministro("123456");

        // Crear un Recibo de prueba
        Recibo reciboMock = new Recibo();

        // Define el comportamiento del mock cuando se llama al método buscarPorSuministro
        when(reciboServiceMock.buscarPorSuministro(buscarReciboRequest.getNumeroDeSuministro())).thenReturn(reciboMock);

        // Llama al método que se quiere probar
        ResponseEntity<Recibo> responseEntity = recibosController.buscarPorSuministro(buscarReciboRequest);

        // Comprobar que la respuesta es la esperada
        assertEquals(ResponseEntity.ok().body(reciboMock), responseEntity);

        // Se verifica que el método buscarPorSuministro en reciboServiceMock fue llamado sólo una vez
        verify(reciboServiceMock, times(1)).buscarPorSuministro(buscarReciboRequest.getNumeroDeSuministro());
    }

    @Test
    public void buscarPorSuministroNoExisteTest() {

        // Crear un mock de ReciboService
        ReciboService reciboServiceMock = mock(ReciboService.class);

        // Crear una instancia de RecibosController
        RecibosController recibosController = new RecibosController();
        recibosController.reciboService = reciboServiceMock;

        // Crear una instancia de BuscarReciboRequest
        BuscarReciboRequest buscarReciboRequest = new BuscarReciboRequest();
        buscarReciboRequest.setNumeroDeSuministro("111111");  // Supone un número de suministro no existente

        // Define el comportamiento del mock cuando se llama al método buscarPorSuministro con un suministro no existente
        when(reciboServiceMock.buscarPorSuministro(buscarReciboRequest.getNumeroDeSuministro())).thenReturn(null);

        // Llama al método que se quiere probar
        ResponseEntity<Recibo> responseEntity = recibosController.buscarPorSuministro(buscarReciboRequest);

        // Comprobar que la respuesta es la esperada
        assertEquals(ResponseEntity.ok().body(null), responseEntity);

        // Se verifica que el método buscarPorSuministro en reciboServiceMock fue llamado sólo una vez
        verify(reciboServiceMock, times(1)).buscarPorSuministro(buscarReciboRequest.getNumeroDeSuministro());
    }

    @Test
    public void buscarPorSuministroThrowsExceptionTest() {
        // Crear un mock de ReciboService
        ReciboService reciboServiceMock = mock(ReciboService.class);

        // Crear una instancia de RecibosController
        RecibosController recibosController = new RecibosController();
        recibosController.reciboService = reciboServiceMock;

        // Crear una instancia de BuscarReciboRequest con un numeroDeSuministro no existente
        BuscarReciboRequest buscarReciboRequest = new BuscarReciboRequest();
        buscarReciboRequest.setNumeroDeSuministro("99999999");

        // Configurar reciboServiceMock para lanzar ReciboNotFoundException cuando se llama a buscarPorSuministro con el numeroDeSuministro no existente
        when(reciboServiceMock.buscarPorSuministro(buscarReciboRequest.getNumeroDeSuministro())).thenThrow(new ReciboNotFoundException(buscarReciboRequest.getNumeroDeSuministro()));

        // Llama al método que se quiere probar y se espera una excepción
        assertThrows(ReciboNotFoundException.class, () -> {
            recibosController.buscarPorSuministro(buscarReciboRequest);
        });
    }

    @Test
    public void buscarPorSuministroValidationTest() throws Exception {

        // Crear una instancia de RecibosController
        RecibosController recibosController = new RecibosController();
        recibosController.reciboService = mock(ReciboService.class);

        // Configure MockMvc
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recibosController)
                .setControllerAdvice(new GlobalExceptionHandler()).build();

        // Crear una instancia de BuscarReciboRequest con un numeroDeSuministro que contiene letras
        BuscarReciboRequest buscarReciboRequest = new BuscarReciboRequest();
        buscarReciboRequest.setNumeroDeSuministro("abc123");

        // Convertir el objeto buscarReciboRequest en una cadena JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String buscarReciboRequestJson = objectMapper.writeValueAsString(buscarReciboRequest);

        // Hacer una solicitud POST y verificar que se produce un error de validación
        MvcResult result = mockMvc.perform(
                        post("/buscarPorSuministro")
                                .content(buscarReciboRequestJson)  // establecer el contenido del cuerpo de la solicitud aquí
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}