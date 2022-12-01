package com.epam.esm.row_mapper;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * @author Hasanboy Makhmudov
 * @project Rest api basics
 * Gift Certificate Row Mapper
 */

@Component
public class GiftCertificateRowMapper implements RowMapper<GiftCertificate> {
    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        GiftCertificate certificate = new GiftCertificate();

        certificate.setId(UUID.fromString(rs.getString("id")));
        certificate.setName(rs.getString("name"));
        certificate.setPrice(rs.getDouble("price"));
        certificate.setDuration(rs.getInt("duration"));
        certificate.setDescription(rs.getString("description"));
        certificate.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime());
        certificate.setLastUpdateDate(rs.getTimestamp("last_update_date").toLocalDateTime());

        return certificate;
    }
}
