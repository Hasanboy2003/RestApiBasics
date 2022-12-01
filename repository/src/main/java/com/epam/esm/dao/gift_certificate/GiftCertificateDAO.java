package com.epam.esm.dao.gift_certificate;

import com.epam.esm.dao.BaseDAO;
import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.UUID;

/**
 * @author Hasanboy Makhmudov
 * @project Rest api basics
 * Gift certificate DAO
 */

public interface GiftCertificateDAO extends BaseDAO<GiftCertificate, UUID> {

    boolean connectWithTag(UUID giftCertificateId,UUID tagId);

    boolean existByGiftCertificateIdAndTagId(UUID giftCertificateId,UUID tagId);

    boolean existByNameAndIdNotEquals(UUID id,String name);

    boolean update(GiftCertificate giftCertificate);

    List<GiftCertificate> searchByFilters(String name, String description, String tagName, String sortParameters);


}
