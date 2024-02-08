package com.demo.recibos.demorecibos.repository;

import com.demo.recibos.demorecibos.model.Recibo;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReciboRepositoryTest {

    @Test
    void findBySuministroOrderByFechaEmisionDescTest_WithInvalidSuministro() {
        ReciboRepository reciboRepositoryMock = mock(ReciboRepository.class);
        Pageable pageable = PageRequest.of(0, 10);
        when(reciboRepositoryMock.findBySuministroOrderByFechaEmisionDesc("Invalid Suministro", pageable)).thenReturn(new ArrayList<>());

        List<Recibo> recibos = reciboRepositoryMock.findBySuministroOrderByFechaEmisionDesc("Invalid Suministro", pageable);

        assertEquals(0, recibos.size());
    }

    @Test
    void findBySuministroOrderByFechaEmisionDescTest_WithNullPageable() {
        ReciboRepository reciboRepositoryMock = mock(ReciboRepository.class);
        List<Recibo> recibosMock = new ArrayList<>();
        when(reciboRepositoryMock.findBySuministroOrderByFechaEmisionDesc("123456", null)).thenReturn(recibosMock);

        List<Recibo> recibos = reciboRepositoryMock.findBySuministroOrderByFechaEmisionDesc("123456", null);

        assertEquals(recibosMock, recibos);
    }
}