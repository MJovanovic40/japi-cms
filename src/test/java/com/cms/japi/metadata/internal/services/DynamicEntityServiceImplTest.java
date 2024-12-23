package com.cms.japi.metadata.internal.services;

import com.cms.japi.metadata.DynamicEntityService;
import com.cms.japi.metadata.internal.constants.DynamicEntityConstants;
import com.cms.japi.metadata.DynamicEntityDto;
import com.cms.japi.metadata.internal.entities.DynamicEntity;
import com.cms.japi.metadata.internal.exceptions.DynamicEntityNotFoundException;
import com.cms.japi.metadata.internal.repositories.DynamicEntityRepository;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DynamicEntityServiceImplTest {

    @Mock
    private DynamicEntityRepository dynamicEntityRepository;
    @Mock
    private Mapper mapper;

    private DynamicEntityService dynamicEntityService;

    @BeforeEach
    public void init() {
        dynamicEntityService = new DynamicEntityServiceImpl(dynamicEntityRepository, mapper);
    }

    @Nested
    class CreateDynamicEntityTests {
        @Test
        void shouldCreateDynamicEntity() {
            //Arrange
            String name = "Test";
            String data = DynamicEntityConstants.EMPTY_DATA;

            DynamicEntity dynamicEntity = new DynamicEntity();
            dynamicEntity.setId(null);
            dynamicEntity.setName(name);
            dynamicEntity.setData(data);

            DynamicEntityDto dto = new DynamicEntityDto();
            dto.setId(null);
            dto.setName(name);
            dto.setData(data);

            when(dynamicEntityRepository.save(any())).thenReturn(dynamicEntity);
            when(mapper.map(any(DynamicEntity.class), any(Class.class))).thenReturn(dto);

            //Act
            DynamicEntityDto result = dynamicEntityService.createDynamicEntity(name);

            //Assert
            assertEquals(dto, result);
            verify(dynamicEntityRepository).save(any(DynamicEntity.class));
            verify(mapper).map(eq(dynamicEntity), eq(DynamicEntityDto.class));
        }
    }

    @Nested
    class GetAllDynamicEntitiesTests {

        @Test
        void shouldReturnAllDynamicEntities() {
            //Arrange
            DynamicEntity dynamicEntity1 = new DynamicEntity();
            dynamicEntity1.setId(null);
            dynamicEntity1.setName("Entity1");
            dynamicEntity1.setData("Data1");

            DynamicEntity dynamicEntity2 = new DynamicEntity();
            dynamicEntity2.setId(null);
            dynamicEntity2.setName("Entity2");
            dynamicEntity2.setData("Data2");

            DynamicEntityDto dto1 = new DynamicEntityDto();
            dto1.setId(null);
            dto1.setName("Entity1");
            dto1.setData("Data1");

            DynamicEntityDto dto2 = new DynamicEntityDto();
            dto2.setId(null);
            dto2.setName("Entity2");
            dto2.setData("Data2");

            List<DynamicEntity> dynamicEntities = List.of(dynamicEntity1, dynamicEntity2);
            List<DynamicEntityDto> expectedDtos = List.of(dto1, dto2);

            when(dynamicEntityRepository.findAll()).thenReturn(dynamicEntities);
            when(mapper.map(dynamicEntity1, DynamicEntityDto.class)).thenReturn(dto1);
            when(mapper.map(dynamicEntity2, DynamicEntityDto.class)).thenReturn(dto2);

            //Act
            List<DynamicEntityDto> result = dynamicEntityService.getAll();

            //Assert
            assertEquals(expectedDtos, result);
            verify(dynamicEntityRepository).findAll();
            verify(mapper).map(dynamicEntity1, DynamicEntityDto.class);
            verify(mapper).map(dynamicEntity2, DynamicEntityDto.class);
        }
    }

    @Nested
    class GetDynamicEntityTests {

        @Test
        void shouldReturnDynamicEntityDtoWhenEntityExists() {
            //Arrange
            Integer dynamicEntityId = 1;
            DynamicEntity dynamicEntity = new DynamicEntity();
            dynamicEntity.setId(dynamicEntityId);
            dynamicEntity.setName("Test Entity");
            dynamicEntity.setData("Test Data");

            DynamicEntityDto expectedDto = new DynamicEntityDto();
            expectedDto.setId(dynamicEntityId);
            expectedDto.setName("Test Entity");
            expectedDto.setData("Test Data");

            when(dynamicEntityRepository.findById(dynamicEntityId))
                    .thenReturn(Optional.of(dynamicEntity));
            when(mapper.map(dynamicEntity, DynamicEntityDto.class))
                    .thenReturn(expectedDto);

            //Act
            DynamicEntityDto result = dynamicEntityService.getDynamicEntity(dynamicEntityId);

            //Assert
            assertEquals(expectedDto, result);
            verify(dynamicEntityRepository).findById(dynamicEntityId);
            verify(mapper).map(dynamicEntity, DynamicEntityDto.class);
        }

        @Test
        void shouldThrowDynamicEntityNotFoundExceptionWhenEntityNotFound() {
            //Arrange
            Integer dynamicEntityId = 1;

            when(dynamicEntityRepository.findById(dynamicEntityId))
                    .thenReturn(Optional.empty());

            //Act & Assert
            assertThrows(DynamicEntityNotFoundException.class, () -> {
                dynamicEntityService.getDynamicEntity(dynamicEntityId);
            });

            verify(dynamicEntityRepository).findById(dynamicEntityId);
        }
    }

    @Nested
    class UpdateDynamicEntityDataTests {

        @Test
        void shouldUpdateDynamicEntityDataSuccessfully() {
            //Arrange
            Integer dynamicEntityId = 1;
            String name = "Updated Entity";
            String dynamicEntityJsonString = "{\"key\":\"value\"}";

            when(dynamicEntityRepository.updateDynamicEntityById(dynamicEntityId, name, dynamicEntityJsonString))
                    .thenReturn(1);

            //Act
            dynamicEntityService.updateDynamicEntityData(dynamicEntityId, name, dynamicEntityJsonString);

            //Assert
            verify(dynamicEntityRepository).updateDynamicEntityById(dynamicEntityId, name, dynamicEntityJsonString);
        }

        @Test
        void shouldThrowDynamicEntityNotFoundExceptionWhenNoRowsAreAffected() {
            //Arrange
            Integer dynamicEntityId = 1;
            String name = "Updated Entity";
            String dynamicEntityJsonString = "{\"key\":\"value\"}";

            when(dynamicEntityRepository.updateDynamicEntityById(dynamicEntityId, name, dynamicEntityJsonString))
                    .thenReturn(0);

            //Act & Assert
            assertThrows(DynamicEntityNotFoundException.class, () -> {
                dynamicEntityService.updateDynamicEntityData(dynamicEntityId, name, dynamicEntityJsonString);
            });

            verify(dynamicEntityRepository).updateDynamicEntityById(dynamicEntityId, name, dynamicEntityJsonString);
        }
    }

    @Nested
    class DeleteDynamicEntityTests {

        @Test
        void shouldDeleteDynamicEntitySuccessfully() {
            //Arrange
            Integer dynamicEntityId = 1;

            when(dynamicEntityRepository.deleteDynamicEntityById(dynamicEntityId))
                    .thenReturn(1);

            //Act
            dynamicEntityService.deleteDynamicEntity(dynamicEntityId);

            //Assert
            verify(dynamicEntityRepository).deleteDynamicEntityById(dynamicEntityId);
        }

        @Test
        void shouldThrowDynamicEntityNotFoundExceptionWhenNoRowsAreAffected() {
            //Arrange
            Integer dynamicEntityId = 1;

            when(dynamicEntityRepository.deleteDynamicEntityById(dynamicEntityId))
                    .thenReturn(0);

            //Act & Assert
            assertThrows(DynamicEntityNotFoundException.class, () -> {
                dynamicEntityService.deleteDynamicEntity(dynamicEntityId);
            });

            verify(dynamicEntityRepository).deleteDynamicEntityById(dynamicEntityId);
        }
    }

}