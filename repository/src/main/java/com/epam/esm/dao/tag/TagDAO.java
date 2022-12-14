package com.epam.esm.dao.tag;

import com.epam.esm.dao.BaseDAO;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.UUID;

/**
 * @author Hasanboy Makhmudov
 * @project Rest api basics
 * Tag DAO
 */
public interface TagDAO extends BaseDAO<Tag, UUID> {

    List<Tag> getByGiftCertificateId(UUID giftCertificateId);


}
