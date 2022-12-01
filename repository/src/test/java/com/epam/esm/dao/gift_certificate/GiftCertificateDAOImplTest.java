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
    void save() {
        GiftCertificate certificate = new GiftCertificate(UUID.randomUUID(), "GiftCertificate", "Description for GiftCertificate",
                189.637, 33, LocalDateTime.now(), LocalDateTime.now(), Collections.singletonList(tag));
        assertTrue(giftCertificateDAO.save(certificate));
    }


    @Test
    void update() {
        GiftCertificate newGiftCertificate = new GiftCertificate(giftCertificate.getId(),
                "NewGiftCertificate", "Description for new gift certificate",
                392.734, 28, LocalDateTime.now(), LocalDateTime.now(), null);
        assertTrue(giftCertificateDAO.update(newGiftCertificate));
    }


    @Test
    void getById() {
        GiftCertificate getCertificate = giftCertificateDAO.getById(giftCertificate.getId());
        assertNotNull(getCertificate);
        assertEquals(getCertificate.getName(), giftCertificate.getName());
    }

    @Test
    void findAll() {
        List<GiftCertificate> giftCertificateList = giftCertificateDAO.findAll();
        assertNotNull(giftCertificateList);
    }

    @Test
    void deleteById() {
        assertTrue(giftCertificateDAO.deleteById(giftCertificate.getId()));
    }


    @Test
    void existsById() {
        assertTrue(giftCertificateDAO.existsById(giftCertificate.getId()));
        assertFalse(giftCertificateDAO.existsById(UUID.randomUUID()));
    }

    @Test
    void existByName() {
        assertTrue(giftCertificateDAO.existByName(giftCertificate.getName()));
        assertFalse(giftCertificateDAO.existByName("testName"));
    }

    @Test
    void getByName() {
        GiftCertificate certificate = giftCertificateDAO.getByName(giftCertificate.getName());
        assertNotNull(certificate);
        assertEquals(certificate.getName(), giftCertificate.getName());
    }

    @Test
    void connectWithTag() {
        assertTrue(giftCertificateDAO.connectWithTag(giftCertificate.getId(), tag.getId()));
    }

    @Test
    void existByGiftCertificateIdAndTagId() {
        giftCertificateDAO.connectWithTag(giftCertificate.getId(), tag.getId());
        assertTrue(giftCertificateDAO.existByGiftCertificateIdAndTagId(giftCertificate.getId(), tag.getId()));
        assertFalse(giftCertificateDAO.existByGiftCertificateIdAndTagId(UUID.randomUUID(), tag.getId()));
        assertFalse(giftCertificateDAO.existByGiftCertificateIdAndTagId(giftCertificate.getId(), UUID.randomUUID()));
    }

    @Test
    public void deleteConnection() {
        giftCertificateDAO.deleteConnection(giftCertificate.getId());
        assertTrue(tagDAO.getByGiftCertificateId(giftCertificate.getId()).isEmpty());
    }

    @Test
    void existByNameAndIdNotEquals() {
        assertTrue(giftCertificateDAO.existByNameAndIdNotEquals(UUID.randomUUID(), giftCertificate.getName()));
    }

    @Test
    void searchByFilters() {
        List<GiftCertificate> certificateList;
        certificateList = giftCertificateDAO.searchByFilters("Certificate", null, null, null);
        assertNotNull(certificateList);

        certificateList = giftCertificateDAO.searchByFilters(null, "for test", null, null);
        assertNotNull(certificateList);

        giftCertificateDAO.connectWithTag(giftCertificate.getId(), tag.getId());
        certificateList = giftCertificateDAO.searchByFilters(null, null, "Test tag", null);
        assertNotNull(certificateList);

        certificateList = giftCertificateDAO.searchByFilters(null, null, null, "name/create_date/des");
        assertNotNull(certificateList);

        certificateList = giftCertificateDAO.searchByFilters("Certificate", "for test", "Test tag", "name/create_date/des");
        assertNotNull(certificateList);

        certificateList = giftCertificateDAO.searchByFilters(null, null, null, null);
        assertNotNull(certificateList);

    }



}