package com.epam.esm.dao.gift_certificate;

import com.epam.esm.dao.tag.TagDAO;
import com.epam.esm.configuration.AppTestConfig;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Hasanboy Makhmudov
 * @project Rest api basics
 * Gift Certificate DAO Implement Test
 */


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
class GiftCertificateDAOImplTest {

    @Autowired
    public GiftCertificateDAO giftCertificateDAO;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private TagDAO tagDAO;

    private GiftCertificate giftCertificate;
    private Tag tag;

    @BeforeEach
    void setUp() {
        tag = new Tag(UUID.randomUUID(), "Test tag");
        giftCertificate = new GiftCertificate(UUID.randomUUID(), "TestGiftCertificate", "Description for test",
                100.123, 30, LocalDateTime.now(), LocalDateTime.now(), Collections.singletonList(tag));
        giftCertificateDAO.save(giftCertificate);
        tagDAO.save(tag);
    }


    @Test
    void saveShouldWork() {
        GiftCertificate certificate = new GiftCertificate(UUID.randomUUID(), "GiftCertificate", "Description for GiftCertificate",
                189.637, 33, LocalDateTime.now(), LocalDateTime.now(), Collections.singletonList(tag));
        assertTrue(giftCertificateDAO.save(certificate));
    }


    @Test
    void updateShouldWork() {
        GiftCertificate newGiftCertificate = new GiftCertificate(giftCertificate.getId(),
                "NewGiftCertificate", "Description for new gift certificate",
                392.734, 28, LocalDateTime.now(), LocalDateTime.now(), null);
        assertTrue(giftCertificateDAO.update(newGiftCertificate));
    }


    @Test
    void getByIdShouldWork() {
        GiftCertificate getCertificate = giftCertificateDAO.getById(giftCertificate.getId());
        assertNotNull(getCertificate);
        assertEquals(getCertificate.getName(), giftCertificate.getName());
    }

    @Test
    void getByIdShouldNotWork() {
        GiftCertificate getCertificate = giftCertificateDAO.getById(UUID.randomUUID());
        assertNull(getCertificate);
    }

    @Test
    void findAll() {
        List<GiftCertificate> giftCertificateList = giftCertificateDAO.findAll();
        assertNotNull(giftCertificateList);
    }

    @Test
    void deleteByIdShouldWork() {
        assertTrue(giftCertificateDAO.deleteById(giftCertificate.getId()));
    }

    @Test
    void deleteByIdShouldNotWork() {
        assertTrue(giftCertificateDAO.deleteById(giftCertificate.getId()));
    }



    @Test
    void existsByIdShouldWork() {
        assertTrue(giftCertificateDAO.existsById(giftCertificate.getId()));
    }

    @Test
    void existsByIdShouldNotWork() {
        assertFalse(giftCertificateDAO.existsById(UUID.randomUUID()));
    }


    @Test
    void existByNameShouldWork() {
        assertTrue(giftCertificateDAO.existByName(giftCertificate.getName()));
    }

    @Test
    void existByNameShouldNotWork() {
        assertFalse(giftCertificateDAO.existByName("testName"));
    }

    @Test
    void getByNameShouldWork() {
        GiftCertificate certificate = giftCertificateDAO.getByName(giftCertificate.getName());
        assertNotNull(certificate);
        assertEquals(certificate.getName(), giftCertificate.getName());
    }

    @Test
    void getByNameShouldWorkNot() {
        GiftCertificate certificate = giftCertificateDAO.getByName("name");
        assertNull(certificate);
    }

    @Test
    void connectWithTagShouldWork() {
        assertTrue(giftCertificateDAO.connectWithTag(giftCertificate.getId(), tag.getId()));
    }

    @Test
    void connectWithTagShouldNotWork() {
        assertFalse(giftCertificateDAO.connectWithTag(UUID.randomUUID(), tag.getId()));
    }

    @Test
    void existByGiftCertificateIdAndTagIdShouldWork() {
        giftCertificateDAO.connectWithTag(giftCertificate.getId(), tag.getId());
        assertTrue(giftCertificateDAO.existByGiftCertificateIdAndTagId(giftCertificate.getId(), tag.getId()));
    }
    @Test
    void existByGiftCertificateIdAndTagIdShouldNotWork() {
        assertFalse(giftCertificateDAO.existByGiftCertificateIdAndTagId(UUID.randomUUID(), tag.getId()));
        assertFalse(giftCertificateDAO.existByGiftCertificateIdAndTagId(giftCertificate.getId(), UUID.randomUUID()));
    }

    @Test
    void deleteConnectionShouldWork() {
        giftCertificateDAO.connectWithTag(giftCertificate.getId(), tag.getId());
        giftCertificateDAO.deleteConnection(giftCertificate.getId());
        assertTrue(tagDAO.getByGiftCertificateId(giftCertificate.getId()).isEmpty());
    }

    @Test
    void deleteConnectionShouldNotWork() {
        giftCertificateDAO.connectWithTag(giftCertificate.getId(), tag.getId());
        giftCertificateDAO.deleteConnection(UUID.randomUUID());
        assertFalse(tagDAO.getByGiftCertificateId(giftCertificate.getId()).isEmpty());
    }

    @Test
    void existByNameAndIdNotEqualsShouldWork() {
        assertTrue(giftCertificateDAO.existByNameAndIdNotEquals(UUID.randomUUID(), giftCertificate.getName()));
    }
    @Test
    void existByNameAndIdNotEqualsShouldNotWork() {
        assertFalse(giftCertificateDAO.existByNameAndIdNotEquals(giftCertificate.getId(),"name"));
    }


    @Test
    void searchByFiltersShouldWork() {
        List<GiftCertificate> certificateList;
        certificateList = giftCertificateDAO.searchByFilters("Certificate", null, null, null);
        assertNotNull(certificateList);

        certificateList = giftCertificateDAO.searchByFilters(null, "for test", null, null);
        assertNotNull(certificateList);

        giftCertificateDAO.connectWithTag(giftCertificate.getId(), tag.getId());
        certificateList = giftCertificateDAO.searchByFilters(null, null, "Test tag", null);
        assertNotNull(certificateList);

        certificateList = giftCertificateDAO.searchByFilters(null, null, null, "mane");
        assertNotNull(certificateList);

        certificateList = giftCertificateDAO.searchByFilters("Certificate", "for test", "Test tag", "name/create_date/desc");
        assertNotNull(certificateList);

        certificateList = giftCertificateDAO.searchByFilters(null, null, null, null);
        assertNotNull(certificateList);

    }



}