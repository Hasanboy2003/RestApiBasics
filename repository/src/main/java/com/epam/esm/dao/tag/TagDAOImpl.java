package com.epam.esm.dao.tag;

import com.epam.esm.entity.Tag;
import com.epam.esm.row_mapper.TagRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @author Hasanboy Makhmudov
 * @project Rest api basics
 * Tag DAO
 */

@Repository
public class TagDAOImpl implements TagDAO {


    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final TagRowMapper tagRowMapper;
    private final MapSqlParameterSource parameterSource;


    private static final String FIELD_ID = "id";
    private static final String FIELD_NAME = "name";

    private static final String SELECT_TAG_BY_ID = "SELECT * FROM tag WHERE id=:id";
    private static final String SELECT_TAG = "SELECT * FROM tag";
    private static final String DELETE_TAG = "DELETE FROM tag WHERE id=:id";
    private static final String INSERT_TAG = "INSERT INTO tag(id,name) VALUES(:id,:name)";
    private static final String DELETE_GIFT_CERTIFICATE_TAG = "DELETE FROM gift_certificate_tag WHERE tag_id=:tagId";
    private static final String EXIST_TAG_BY_ID = "select case when exists(select * from tag t where t.id =:id ) then true else false end ";
    private static final String EXIST_TAG_BY_NAME = "select case when exists(select * from tag t where lower(t.name) =lower(:name) ) then true else false end ";
    private static final String SELECT_TAG_BY_NAME = "SELECT * FROM tag t WHERE lower(t.name)=lower(:name)";

    private static final String SELECT_TAG_BY_GIFT_CERTIFICATE_ID =
            "SELECT t.*\n" +
                    "FROM tag t\n" +
                    "         JOIN gift_certificate_tag gct ON t.id = gct.tag_id\n" +
                    "         JOIN gift_certificate gc ON gc.id = gct.gift_certificate_id\n" +
                    "WHERE gc.id =:id";

    public TagDAOImpl(NamedParameterJdbcTemplate jdbcTemplate, TagRowMapper tagRowMapper, MapSqlParameterSource parameterSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagRowMapper = tagRowMapper;
        this.parameterSource = parameterSource;
    }

    @Override
    public Tag getById(UUID id) {
        parameterSource.addValue(FIELD_ID, id);
        List<Tag> tags = jdbcTemplate.query(SELECT_TAG_BY_ID, parameterSource, tagRowMapper);
        return tags.isEmpty() ? null : tags.get(0);
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(SELECT_TAG, tagRowMapper);
    }

    @Override
    public boolean deleteById(UUID id) {
        parameterSource.addValue(FIELD_ID, id);
        int delete = jdbcTemplate.update(DELETE_TAG, parameterSource);
        return delete == 1;
    }

    @Override
    public boolean save(Tag entity) {
        parameterSource.addValue(FIELD_ID, entity.getId()).addValue(FIELD_NAME, entity.getName());
        int save = jdbcTemplate.update(INSERT_TAG, parameterSource);
        return save == 1;
    }

    @Override
    public void deleteConnection(UUID tagId) {
        parameterSource.addValue("tagId", tagId);
        jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_TAG, parameterSource);
    }

    @Override
    public boolean existsById(UUID id) {
        parameterSource.addValue(FIELD_ID, id);
        return Boolean.TRUE.equals(jdbcTemplate.query(EXIST_TAG_BY_ID, parameterSource,
                rs -> {
                    if (rs.next())
                        return rs.getBoolean(1);
                    return false;
                }
        ));
    }

    @Override
    public boolean existByName(String name) {
        parameterSource.addValue(FIELD_NAME, name);
        return Boolean.TRUE.equals(jdbcTemplate.query(EXIST_TAG_BY_NAME, parameterSource,
                rs -> {
                    if (rs.next())
                        return rs.getBoolean(1);
                    return false;
                }
        ));
    }

    @Override
    public Tag getByName(String name) {
        parameterSource.addValue(FIELD_NAME, name);
        List<Tag> tags = jdbcTemplate.query(SELECT_TAG_BY_NAME, parameterSource, tagRowMapper);
        return tags.isEmpty() ? null : tags.get(0);
    }

    @Override
    public List<Tag> getByGiftCertificateId(UUID giftCertificateId) {
        parameterSource.addValue("id", giftCertificateId);
        return jdbcTemplate.query(SELECT_TAG_BY_GIFT_CERTIFICATE_ID, parameterSource, tagRowMapper);

    }
}
