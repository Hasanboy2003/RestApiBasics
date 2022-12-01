package com.epam.esm.dao.gift_certificate;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.row_mapper.GiftCertificateRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @author Hasanboy Makhmudov
 * @project Rest api basics
 * Gift certificate DAO Implement
 */

@Repository
public class GiftCertificateDAOImpl implements GiftCertificateDAO {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final MapSqlParameterSource parameterSource;

    private final GiftCertificateRowMapper giftCertificateRowMapper;

    public GiftCertificateDAOImpl(NamedParameterJdbcTemplate jdbcTemplate, MapSqlParameterSource parameterSource, GiftCertificateRowMapper giftCertificateRowMapper) {

        this.jdbcTemplate = jdbcTemplate;
        this.parameterSource = parameterSource;
        this.giftCertificateRowMapper = giftCertificateRowMapper;

    }


    private static final String SELECT_GIFT_CERTIFICATE_BY_ID = "SELECT * FROM gift_certificate WHERE id=:id";
    private static final String SELECT_GIFT_CERTIFICATE = "SELECT * FROM gift_certificate";
    private static final String DELETE_GIFT_CERTIFICATE = "DELETE FROM gift_certificate WHERE id=:id";

    private static final String INSERT_GIFT_CERTIFICATE = "INSERT INTO gift_certificate(id,name,description,price,duration,create_date,last_update_date) " +
            "VALUES(:id,:name,:description,:price,:duration,:createDate,:lastUpdateDate)";

    private static final String EXIST_GIFT_CERTIFICATE_BY_ID = "select case when exists(select * from gift_certificate gc where gc.id =:id ) then true else false end as exist";

    private static final String UPDATE_GIFT_CERTIFICATE = "UPDATE gift_certificate SET name = :name, description = :description, price = :price, " +
            "duration = :duration, last_update_date = :last_update_date WHERE id = :id";
    private static final String EXIST_GIFT_CERTIFICATE_BY_NAME = "select case when exists( select * from gift_certificate gc where gc.name =:name ) then true else false end ";
    private static final String SELECT_GIFT_CERTIFICATE_BY_NAME = "SELECT * FROM gift_certificate WHERE name=:name";
    private static final String INSERT_GIT_CERTIFICATE_TAG = "INSERT INTO gift_certificate_tag(gift_certificate_id,tag_id) VALUES(:giftCertificateId,:tagId)";


    @Override
    public GiftCertificate getById(UUID id) {

        parameterSource.addValue("id", id);
        return jdbcTemplate.query(SELECT_GIFT_CERTIFICATE_BY_ID, parameterSource, giftCertificateRowMapper).get(0);

    }


    @Override
    public List<GiftCertificate> findAll() {

        return jdbcTemplate.query(SELECT_GIFT_CERTIFICATE, giftCertificateRowMapper);

    }


    @Override
    public boolean deleteById(UUID id) {

        parameterSource.addValue("id", id);
        int delete = jdbcTemplate.update(DELETE_GIFT_CERTIFICATE, parameterSource);

        return delete == 1;

    }



    @Override
    public boolean save(GiftCertificate entity) {

        parameterSource
                .addValue("id", entity.getId()).addValue("name", entity.getName())
                .addValue("description", entity.getDescription()).addValue("price", entity.getPrice())
                .addValue("duration", entity.getDuration()).addValue("createDate", entity.getCreateDate())
                .addValue("lastUpdateDate", entity.getLastUpdateDate());

        int save = jdbcTemplate.update(INSERT_GIFT_CERTIFICATE, parameterSource);
        return save == 1;
    }


    @Override
    public boolean existsById(UUID id) {

        parameterSource.addValue("id", id);
        return (Boolean) jdbcTemplate.query(EXIST_GIFT_CERTIFICATE_BY_ID, parameterSource, (ResultSetExtractor<Object>) rs -> {
            if (rs.next()) {
                return rs.getBoolean(1);
            }
            return false;
        });
    }




    @Override
    public boolean update(GiftCertificate entity) {
        parameterSource.addValue("name", entity.getName())
                .addValue("description", entity.getDescription())
                .addValue("price", entity.getPrice())
                .addValue("duration", entity.getDuration())
                .addValue("last_update_date", entity.getLastUpdateDate())
                .addValue("id", entity.getId());
        int update = jdbcTemplate.update(UPDATE_GIFT_CERTIFICATE, parameterSource);
        System.out.println(update);
        return update == 1;
    }



    @Override
    public boolean existByName(String name) {

        parameterSource.addValue("name", name);
        return (Boolean) jdbcTemplate.query(EXIST_GIFT_CERTIFICATE_BY_NAME, parameterSource, (ResultSetExtractor<Object>) rs -> {
            if (rs.next()) {
                return rs.getBoolean(1);
            }
            return false;
        });
    }



    @Override
    public GiftCertificate getByName(String name) {

        parameterSource.addValue("name", name);
        return jdbcTemplate.query(SELECT_GIFT_CERTIFICATE_BY_NAME, parameterSource, giftCertificateRowMapper).get(0);

    }


    @Override
    public boolean connectWithTag(UUID giftCertificateId, UUID tagId) {
        parameterSource.addValue("giftCertificateId", giftCertificateId).addValue("tagId", tagId);
        int connect = jdbcTemplate.update(INSERT_GIT_CERTIFICATE_TAG, parameterSource);
        return connect == 1;
    }

    private static final String EXIST_BY_GIFT_CERTIFICATE_AND_TAG = "select case when  exists( select * from gift_certificate_tag gct where gct.gift_certificate_id = :giftCertificateId and gct.tag_id = :tagId ) then true else false end ";

    @Override
    public boolean existByGiftCertificateIdAndTagId(UUID giftCertificateId, UUID tagId) {
        parameterSource.addValue("giftCertificateId", giftCertificateId).addValue("tagId", tagId);
        return (Boolean) jdbcTemplate.query(EXIST_BY_GIFT_CERTIFICATE_AND_TAG, parameterSource, (ResultSetExtractor<Object>) rs -> {
            if (rs.next()) {
                return rs.getBoolean(1);
            }
            return false;
        });
    }



    private static final String DELETE_GIFT_CERTIFICATE_TAG = "DELETE FROM gift_certificate_tag WHERE gift_certificate_id=:giftCertificateId";

    @Override
    public void deleteConnection(UUID giftCertificateId) {
        parameterSource.addValue("giftCertificateId", giftCertificateId);
        jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_TAG, parameterSource);
    }

    private static final String EXIST_BY_NAME_AND_ID_NOT_EQUAL = "select case when exists( select * from gift_certificate gc where gc.id != :id and gc.name = :name ) then true else false end ";

    @Override
    public boolean existByNameAndIdNotEquals(UUID id, String name) {
        parameterSource.addValue("id", id).addValue("name", name);
        return (Boolean) jdbcTemplate.query(EXIST_BY_NAME_AND_ID_NOT_EQUAL, parameterSource, (ResultSetExtractor<Object>) rs -> {
            if (rs.next()) {
                return rs.getBoolean(1);
            }
            return false;
        });
    }


    @Override
    public List<GiftCertificate> searchByFilters(String name, String description, String tagName, String sortParameters) {
        String SEARCH_QUERY_GIFT_CERTIFICATE_WITH_FILTER =
                "SELECT gc.* FROM gift_certificate gc\n" +
                        "         LEFT JOIN gift_certificate_tag gct on gc.id = gct.gift_certificate_id\n" +
                        "         LEFT JOIN tag t on t.id = gct.tag_id ";

        if (name != null) {
            SEARCH_QUERY_GIFT_CERTIFICATE_WITH_FILTER += " WHERE lower(gc.name) LIKE concat('%',lower(:name),'%')";
            parameterSource.addValue("name", name);
        }

        if (description != null) {
            if (SEARCH_QUERY_GIFT_CERTIFICATE_WITH_FILTER.contains("WHERE"))
                SEARCH_QUERY_GIFT_CERTIFICATE_WITH_FILTER += " AND lower(gc.description) LIKE concat('%',lower(:description),'%')";
            else
                SEARCH_QUERY_GIFT_CERTIFICATE_WITH_FILTER += " WHERE lower(gc.description) LIKE  concat('%',lower(:description),'%')  ";
            parameterSource.addValue("description", description);
        }

        if (tagName != null) {
            if (SEARCH_QUERY_GIFT_CERTIFICATE_WITH_FILTER.contains("WHERE"))
                SEARCH_QUERY_GIFT_CERTIFICATE_WITH_FILTER += " AND lower(t.name) = lower(:tagName) ";
            else
                SEARCH_QUERY_GIFT_CERTIFICATE_WITH_FILTER += " WHERE lower(t.name) = lower(:tagName) ";
            parameterSource.addValue("tagName", tagName);
        }

        if (sortParameters != null) {
            if (sortParameters.contains("name/create_date")) {
                SEARCH_QUERY_GIFT_CERTIFICATE_WITH_FILTER += " ORDER BY gc.name, gc.create_date ";
            } else if (sortParameters.contains("create_date")) {
                SEARCH_QUERY_GIFT_CERTIFICATE_WITH_FILTER += " ORDER BY gc.create_date ";
            } else {
                SEARCH_QUERY_GIFT_CERTIFICATE_WITH_FILTER += " ORDER BY gc.name ";
            }
            if (sortParameters.contains("desc"))
                SEARCH_QUERY_GIFT_CERTIFICATE_WITH_FILTER += " DESC ";
        }


        return jdbcTemplate.query(SEARCH_QUERY_GIFT_CERTIFICATE_WITH_FILTER, parameterSource, giftCertificateRowMapper);
    }
}
