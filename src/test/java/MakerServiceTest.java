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

import java.time.LocalDateTime;
import java.util.Collections;
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

        dto = new MakerRequestDTO(
                1L,
                101L,
                "user123",
                "MAKER",
                LocalDateTime.now(),
                "{\"key\":\"value\"}",
                "PENDING",
                "No comments",
                "SERVICE_01"
        );

        entity = new MakerRequestEntity();
        entity.setUniqueld(1L);
        entity.setRequestId(101L);
        entity.setUserId("user123");
        entity.setUserRole("MAKER");
        entity.setCreatedTimeStamp(dto.getCreatedTimeStamp());
        entity.setRequestPayloadJson("{\"key\":\"value\"}");
        entity.setApprovalStatus("PENDING");
        entity.setComments("No comments");
        entity.setRequestServiceld("SERVICE_01");
    }

    @Test
    void testCreateRequest() {
        // Arrange
        when(mapper.map(dto, MakerRequestEntity.class)).thenReturn(entity);
        when(makerRepository.save(any(MakerRequestEntity.class))).thenReturn(entity);
        when(mapper.map(entity, MakerRequestDTO.class)).thenReturn(dto);

        // Act
        MakerRequestDTO result = makerService.createRequest(dto);

        // Assert
        assertEquals(dto.getUniqueld(), result.getUniqueld());
        assertEquals(dto.getRequestId(), result.getRequestId());
        assertEquals(dto.getUserId(), result.getUserId());
        assertEquals(dto.getUserRole(), result.getUserRole());
        assertEquals(dto.getApprovalStatus(), result.getApprovalStatus());

        verify(mapper, times(1)).map(dto, MakerRequestEntity.class);
        verify(makerRepository, times(1)).save(entity);
        verify(mapper, times(1)).map(entity, MakerRequestDTO.class);
    }

    @Test
    void testGetAllRequest() {
        // Arrange
        when(makerRepository.findAll()).thenReturn(Collections.singletonList(entity));
        when(mapper.map(entity, MakerRequestDTO.class)).thenReturn(dto);

        // Act
        List<MakerRequestDTO> result = makerService.getAllRequest();

        // Assert
        assertEquals(1, result.size());
        assertEquals(dto.getUserId(), result.get(0).getUserId());
        assertEquals(dto.getRequestServiceld(), result.get(0).getRequestServiceld());

        verify(makerRepository, times(1)).findAll();
        verify(mapper, times(1)).map(entity, MakerRequestDTO.class);
    }
}
