package com.epam.esm.DAO.tag;

import com.epam.esm.entity.Tag;
import com.epam.esm.rowMapper.TagRowMapper;
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

    public TagDAOImpl(NamedParameterJdbcTemplate jdbcTemplate, TagRowMapper tagRowMapper, MapSqlParameterSource parameterSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagRowMapper = tagRowMapper;
        this.parameterSource = parameterSource;
    }

    private static final String SELECT_TAG_BY_ID = "SELECT * FROM tag WHERE id=:id";

    @Override
    public Tag getById(UUID id) {
        parameterSource.addValue("id", id);
        return  jdbcTemplate.query(SELECT_TAG_BY_ID, parameterSource, tagRowMapper).get(0);
    }

    private static final String SELECT_TAG = "SELECT * FROM tag";

    @Override
    public List<Tag> findAll() {

        return jdbcTemplate.query(SELECT_TAG, tagRowMapper);

    }

    private static final String DELETE_TAG = "DELETE FROM tag WHERE id=:id";

    @Override
    public boolean deleteById(UUID id) {
        parameterSource.addValue("id", id);
        int delete = jdbcTemplate.update(DELETE_TAG, parameterSource);
        return delete==1;
    }

    private static final String INSERT_TAG = "INSERT INTO tag(id,name) VALUES(:id,:name)";



    @Override
    public boolean save(Tag entity) {
        parameterSource.addValue("id", entity.getId()).addValue("name", entity.getName());
        int save = jdbcTemplate.update(INSERT_TAG, parameterSource);
        return save==1;
    }

    private static final String DELETE_GIFT_CERTIFICATE_TAG = "DELETE FROM gift_certificate_tag WHERE tag_id=:tagId";

    @Override
    public void deleteConnection(UUID tagId) {
        parameterSource.addValue("tagId", tagId);
        jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_TAG, parameterSource);
    }

    private static final String EXIST_TAG_BY_ID = "select case when exists(select * from tag t where t.id =:id ) then true else false end ";

    @Override
    public boolean existsById(UUID id) {
        parameterSource.addValue("id", id);
        return Boolean.TRUE.equals(jdbcTemplate.query(EXIST_TAG_BY_ID, parameterSource,
                rs -> {
                    if (rs.next())
                        return rs.getBoolean(1);
                    return false;
                }
        ));
    }

    private static final String EXIST_TAG_BY_NAME = "select case when exists(select * from tag t where t.name =:name ) then true else false end ";


    @Override
    public boolean existByName(String name) {

        parameterSource.addValue("name",name);
        return Boolean.TRUE.equals(jdbcTemplate.query(EXIST_TAG_BY_NAME, parameterSource,
                rs -> {
                    if (rs.next())
                        return rs.getBoolean(1);
                    return false;
                }
        ));

    }

    private static final String SELECT_TAG_BY_NAME = "SELECT * FROM tag t WHERE t.name=:name";

    @Override
    public Tag getByName(String name) {
        parameterSource.addValue("name", name);
        return  jdbcTemplate.query(SELECT_TAG_BY_NAME, parameterSource, tagRowMapper).get(0);
    }

    private static final String SELECT_TAG_BY_GIFT_CERTIFICATE_ID =
            "SELECT t.*\n" +
            "FROM tag t\n" +
            "         JOIN gift_certificate_tag gct ON t.id = gct.tag_id\n" +
            "         JOIN gift_certificate gc ON gc.id = gct.gift_certificate_id\n" +
            "WHERE gc.id =:id";

    @Override
    public List<Tag> getByGiftCertificateId(UUID giftCertificateId) {

        parameterSource.addValue("id", giftCertificateId);
        return  jdbcTemplate.query(SELECT_TAG_BY_GIFT_CERTIFICATE_ID, parameterSource, tagRowMapper);

    }
}
