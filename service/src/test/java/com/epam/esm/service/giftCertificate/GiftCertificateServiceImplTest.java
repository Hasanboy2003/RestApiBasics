package com.epam.esm.service.giftCertificate;

import com.epam.esm.dao.gift_certificate.GiftCertificateDAO;
import com.epam.esm.dao.tag.TagDAO;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.response.ApiResponse;
import com.epam.esm.dto.response.ResponseMessage;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.AlreadyExistsException;
import com.epam.esm.exceptions.NotFoundException;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.validators.GiftCertificateValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author Hasanboy Makhmudov
 * @project Rest api basics
 * Gift Certificate Service Implement Test
 */


@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {


    private GiftCertificateService giftCertificateService;

    @Mock
    private GiftCertificateValidator giftCertificateValidator;

    @Mock
    private GiftCertificateMapper giftCertificateMapper;

    @Mock
    private TagMapper tagMapper;

    @Mock
    private GiftCertificateDAO giftCertificateDAO;

    @Mock
    private TagDAO tagDAO;


    private GiftCertificate giftCertificate;

    private GiftCertificateDTO giftCertificateDTO;


    @BeforeEach
    void setUp() {
        giftCertificateService = new GiftCertificateServiceImpl(giftCertificateValidator, giftCertificateDAO, tagDAO, giftCertificateMapper, tagMapper);

        Tag tag = new Tag(UUID.randomUUID(), "Test tag");
        giftCertificate = new GiftCertificate(UUID.randomUUID(), "TestGiftCertificate", "Description for test",
                100.123, 30, LocalDateTime.now(), LocalDateTime.now(), Collections.singletonList(tag));


        giftCertificateDTO = new GiftCertificateDTO(giftCertificate.getId(), giftCertificate.getName(), giftCertificate.getDescription(),
                giftCertificate.getPrice(), giftCertificate.getDuration(),
                LocalDateTime.now(), LocalDateTime.now(), Collections.singletonList(new TagDTO(tag.getName())));

    }

    @Test
    void create() {
        when(giftCertificateMapper.fromDTOToEntity(giftCertificateDTO)).thenReturn(giftCertificate);
        when(giftCertificateDAO.existByName(giftCertificateDTO.getName())).thenReturn(false);

        ApiResponse response = giftCertificateService.create(giftCertificateDTO);

        verify(giftCertificateDAO).existByName(giftCertificateDTO.getName());
        verify(giftCertificateDAO).save(giftCertificate);

        ArgumentCaptor<GiftCertificate> certificateArgumentCaptor = ArgumentCaptor.forClass(GiftCertificate.class);
        verify(giftCertificateDAO).save(certificateArgumentCaptor.capture());
        GiftCertificate certificate = certificateArgumentCaptor.getValue();

        assertEquals(giftCertificateDTO.getName(), certificate.getName());
        assertDoesNotThrow(() -> giftCertificateService.create(giftCertificateDTO));
        assertEquals(ResponseMessage.CREATED.getValue(), response.getMessage());
    }

    @Test
    void createSecondTest() {
        when(giftCertificateDAO.existByName(giftCertificateDTO.getName())).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> giftCertificateService.create(giftCertificateDTO));

        verify(giftCertificateDAO).existByName(giftCertificateDTO.getName());
    }

    @Test
    void update() {

        when(giftCertificateDAO.existsById(giftCertificateDTO.getId())).thenReturn(true);
        when(giftCertificateDAO.existByNameAndIdNotEquals(giftCertificateDTO.getId(), giftCertificateDTO.getName())).thenReturn(false);
        when(giftCertificateDAO.getById(giftCertificateDTO.getId())).thenReturn(giftCertificate);

        ApiResponse response = giftCertificateService.update(giftCertificateDTO);

        verify(giftCertificateDAO).existsById(giftCertificateDTO.getId());
        verify(giftCertificateDAO).existByNameAndIdNotEquals(giftCertificateDTO.getId(),giftCertificate.getName());
        verify(giftCertificateDAO).update(giftCertificate);

        ArgumentCaptor<GiftCertificate> certificateArgumentCaptor = ArgumentCaptor.forClass(GiftCertificate.class);
        verify(giftCertificateDAO).update(certificateArgumentCaptor.capture());
        GiftCertificate certificate = certificateArgumentCaptor.getValue();

        assertEquals(giftCertificateDTO.getName(), certificate.getName());
        assertDoesNotThrow(() -> giftCertificateService.update(giftCertificateDTO));
        assertEquals(ResponseMessage.UPDATED.getValue(), response.getMessage());

    }

    @Test
    public void updateSecondTest() {
        when(giftCertificateDAO.existsById(giftCertificateDTO.getId())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> giftCertificateService.update(giftCertificateDTO));

        verify(giftCertificateDAO).existsById(giftCertificateDTO.getId());
    }

    @Test
    public void updateThirdTest() {
        when(giftCertificateDAO.existsById(giftCertificateDTO.getId())).thenReturn(true);
        when(giftCertificateDAO.existByNameAndIdNotEquals(giftCertificateDTO.getId(),
                giftCertificateDTO.getName())).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> giftCertificateService.update(giftCertificateDTO));

        verify(giftCertificateDAO).existsById(giftCertificateDTO.getId());
        verify(giftCertificateDAO).existByNameAndIdNotEquals(giftCertificateDTO.getId(),giftCertificateDTO.getName());
    }


    @Test
    void delete() {
        when(giftCertificateDAO.existsById(giftCertificateDTO.getId())).thenReturn(true);

        ApiResponse response = giftCertificateService.delete(giftCertificateDTO.getId());

        verify(giftCertificateDAO).existsById(giftCertificateDTO.getId());
        verify(giftCertificateDAO).deleteById(giftCertificateDTO.getId());

        ArgumentCaptor<UUID> argumentCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(giftCertificateDAO).deleteById(argumentCaptor.capture());
        UUID captorValue = argumentCaptor.getValue();

        assertEquals(giftCertificateDTO.getId(),captorValue);
        assertDoesNotThrow(() -> giftCertificateService.delete(giftCertificateDTO.getId()));
        assertEquals(ResponseMessage.DELETED.getValue(), response.getMessage());

    }

    @Test
    void deleteSecond(){
        when(giftCertificateDAO.existsById(giftCertificateDTO.getId())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> giftCertificateService.delete(giftCertificateDTO.getId()));

        verify(giftCertificateDAO).existsById(giftCertificateDTO.getId());
    }

    @Test
    void get() {

        ApiResponse response = giftCertificateService.get();

        verify(giftCertificateDAO).findAll();

        assertDoesNotThrow(() -> giftCertificateService.get());
        assertEquals(ResponseMessage.READ.getValue(),response.getMessage());

    }

    @Test
    void getById() {
        when(giftCertificateDAO.existsById(giftCertificateDTO.getId())).thenReturn(true);
        when(giftCertificateDAO.getById(giftCertificateDTO.getId())).thenReturn(giftCertificate);

        ApiResponse response = giftCertificateService.getById(giftCertificateDTO.getId());

        verify(giftCertificateDAO).existsById(giftCertificateDTO.getId());
        verify(giftCertificateDAO).getById(giftCertificateDTO.getId());

        ArgumentCaptor<UUID> argumentCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(giftCertificateDAO).getById(argumentCaptor.capture());
        UUID captorValue = argumentCaptor.getValue();

        assertEquals(giftCertificateDTO.getId(),captorValue);
        assertDoesNotThrow(() -> giftCertificateService.getById(giftCertificateDTO.getId()));
        assertEquals(ResponseMessage.READ.getValue(), response.getMessage());
    }

    @Test
    void getByIdSecond(){
        when(giftCertificateDAO.existsById(giftCertificateDTO.getId())).thenReturn(false);

        assertThrows(NotFoundException.class,() -> giftCertificateService.getById(giftCertificateDTO.getId()));

        verify(giftCertificateDAO).existsById(giftCertificateDTO.getId());
    }


    @Test
    void getFilterResult() {

        ApiResponse response = giftCertificateService.getFilterResult(giftCertificate.getName(),null,null,null);

        verify(giftCertificateDAO).searchByFilters(giftCertificate.getName(),null,null,null);

        assertDoesNotThrow(() -> giftCertificateService.getFilterResult(anyString(),anyString(),anyString(),anyString()));
        assertEquals(ResponseMessage.READ.getValue(),response.getMessage());

    }

}