package com.epam.esm.service.tag;

import com.epam.esm.dao.gift_certificate.GiftCertificateDAO;
import com.epam.esm.dao.tag.TagDAO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.response.ApiResponse;
import com.epam.esm.dto.response.ResponseMessage;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.AlreadyExistsException;
import com.epam.esm.exceptions.NotFoundException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.validators.TagValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Hasanboy Makhmudov
 * @project Rest api basics
 * Gift Certificate Service Implement Test
 */

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    private TagService tagService;

    @Mock
    private TagDAO tagDAO;

    @Mock
    private GiftCertificateDAO giftCertificateDAO;

    @Mock
    private TagMapper tagMapper;

    @Mock
    private TagValidator tagValidator;

    private Tag tag;

    private TagDTO tagDTO;

    @BeforeEach
    void setUp() {
        tagService = new TagServiceImpl(tagDAO,giftCertificateDAO,tagMapper,tagValidator);
        tag = new Tag(UUID.randomUUID(),"Test tag");
        tagDTO = new TagDTO(tag.getId(),tag.getName());
    }

    @Test
    void create() {
        when(tagMapper.fromDTOToEntity(tagDTO)).thenReturn(tag);
        when(tagDAO.existByName(tagDTO.getName())).thenReturn(false);

        ApiResponse response = tagService.create(tagDTO);

        verify(tagDAO).existByName(tagDTO.getName());
        verify(tagDAO).save(tag);

        ArgumentCaptor<Tag> argumentCaptor = ArgumentCaptor.forClass(Tag.class);
        verify(tagDAO).save(argumentCaptor.capture());
        Tag savedTag = argumentCaptor.getValue();

        assertEquals(savedTag.getName(),tagDTO.getName());
        assertDoesNotThrow(() -> tagService.create(tagDTO));
        assertEquals(ResponseMessage.CREATED.getValue(),response.getMessage());
    }

    @Test
    void createSecond(){
        when(tagDAO.existByName(tagDTO.getName())).thenReturn(true);

        assertThrows(AlreadyExistsException.class,() -> tagService.create(tagDTO));

        verify(tagDAO).existByName(tagDTO.getName());
    }

    @Test
    void get() {
        ApiResponse response = tagService.get();

        verify(tagDAO).findAll();

        assertDoesNotThrow(() -> tagService.get());
        assertEquals(ResponseMessage.READ.getValue(),response.getMessage());

    }

    @Test
    void getById() {
        when(tagDAO.existsById(tagDTO.getId())).thenReturn(true);
        when(tagDAO.getById(tagDTO.getId())).thenReturn(tag);

        ApiResponse response = tagService.getById(tagDTO.getId());

        verify(tagDAO).existsById(tagDTO.getId());
        verify(tagDAO).getById(tagDTO.getId());

        ArgumentCaptor<UUID> argumentCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(tagDAO).getById(argumentCaptor.capture());
        UUID tagId = argumentCaptor.getValue();

        assertEquals(tagDTO.getId(),tagId);
        assertDoesNotThrow(() -> tagService.getById(tagId));
        assertEquals(ResponseMessage.READ.getValue(),response.getMessage());

    }

    @Test
    void  getByIdSecond(){
        when(tagDAO.existsById(tagDTO.getId())).thenReturn(false);

        assertThrows(NotFoundException.class,() -> tagService.getById(tagDTO.getId()));

        verify(tagDAO).existsById(tagDTO.getId());
    }

    @Test
    void delete() {
        when(tagDAO.existsById(tagDTO.getId())).thenReturn(true);

        ApiResponse response = tagService.delete(tagDTO.getId());

        verify(tagDAO).existsById(tagDTO.getId());
        verify(tagDAO).deleteById(tagDTO.getId());

        ArgumentCaptor<UUID> argumentCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(tagDAO).deleteById(argumentCaptor.capture());
        UUID tagId = argumentCaptor.getValue();

        assertEquals(tagDTO.getId(),tagId);
        assertDoesNotThrow(() -> tagService.delete(tagId));
        assertEquals(ResponseMessage.DELETED.getValue(),response.getMessage());
    }

    @Test
    void deleteSecond(){
        when(tagDAO.existsById(tagDTO.getId())).thenReturn(false);

        assertThrows(NotFoundException.class,() -> tagService.delete(tagDTO.getId()));

        verify(tagDAO).existsById(tagDTO.getId());
    }
}