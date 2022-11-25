package com.epam.esm.DAO.tag;

import com.epam.esm.DAO.giftCertificate.GiftCertificateDAO;
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
 * Tag Certificate DAO Implement Test
 */


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
class TagDAOImplTest {

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
        Tag tag = new Tag(UUID.randomUUID(), "Gift tag");
        assertTrue(tagDAO.save(tag));
    }

    @Test
    void getById() {
        Tag getTag = tagDAO.getById(tag.getId());
        assertNotNull(getTag);
        assertEquals(getTag.getName(), tag.getName());
    }

    @Test
    void findAll() {
        List<Tag> tagList = tagDAO.findAll();
        assertNotNull(tagList);
    }

    @Test
    void deleteById() {
        assertTrue(tagDAO.deleteById(tag.getId()));
    }


    @Test
    void existsById() {
        assertTrue(tagDAO.existsById(tag.getId()));
        assertFalse(tagDAO.existsById(UUID.randomUUID()));
    }

    @Test
    void existByName() {
        assertTrue(tagDAO.existByName(tag.getName()));
        assertFalse(tagDAO.existByName("tag name"));
    }

    @Test
    void getByName() {
        Tag getTag = tagDAO.getByName(tag.getName());
        assertNotNull(getTag);
        assertEquals(getTag.getName(),tag.getName());
    }

    @Test
    void getByGiftCertificateId() {
        giftCertificateDAO.connectWithTag(giftCertificate.getId(),tag.getId());
        List<Tag> tagList = tagDAO.getByGiftCertificateId(giftCertificate.getId());
        assertNotNull(tagList);
        assertEquals(tagList.get(0).getName(),tag.getName());
    }

}