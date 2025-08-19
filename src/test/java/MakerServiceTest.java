package com.citi.adr.services.service;

import com.citi.adr.services.entity.MakerRequestEntity;
import com.citi.adr.services.model.MakerRequestDTO;
import com.citi.adr.services.repository.MakerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MakerServiceTest {

    @Mock
    private ModelMapper mapper;

    @Mock
    private MakerRepository makerRepository;

    @InjectMocks
    private MakerService makerService;

    private MakerRequestDTO dto;
    private MakerRequestEntity entity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        dto = new MakerRequestDTO();
        dto.setId(1L);
        dto.setRequestData("Test Request");

        entity = new MakerRequestEntity();
        entity.setId(1L);
        entity.setRequestData("Test Request");
    }

    @Test
    void testCreateRequest() {
        // Arrange
        when(mapper.map(dto, MakerRequestEntity.class)).thenReturn(entity);
        when(makerRepository.save(entity)).thenReturn(entity);
        when(mapper.map(entity, MakerRequestDTO.class)).thenReturn(dto);

        // Act
        MakerRequestDTO result = makerService.createRequest(dto);

        // Assert
        assertEquals(dto.getId(), result.getId());
        assertEquals(dto.getRequestData(), result.getRequestData());

        verify(mapper, times(1)).map(dto, MakerRequestEntity.class);
        verify(makerRepository, times(1)).save(entity);
        verify(mapper, times(1)).map(entity, MakerRequestDTO.class);
    }

    @Test
    void testGetAllRequest() {
        // Arrange
        List<MakerRequestEntity> entities = Arrays.asList(entity);
        when(makerRepository.findAll()).thenReturn(entities);
        when(mapper.map(entity, MakerRequestDTO.class)).thenReturn(dto);

        // Act
        List<MakerRequestDTO> result = makerService.getAllRequest();

        // Assert
        assertEquals(1, result.size());
        assertEquals(dto.getId(), result.get(0).getId());
        assertEquals(dto.getRequestData(), result.get(0).getRequestData());

        verify(makerRepository, times(1)).findAll();
        verify(mapper, times(1)).map(entity, MakerRequestDTO.class);
    }
}
