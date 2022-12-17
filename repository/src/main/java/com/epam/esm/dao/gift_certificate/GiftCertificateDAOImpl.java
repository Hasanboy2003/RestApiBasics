package com.epam.esm.dao.gift_certificate;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.enums.Parameter;
import com.epam.esm.row_mapper.GiftCertificateRowMapper;
import com.epam.esm.rule.Rule;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

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

    private final Map<String, String> sortMap = new HashMap<>();

    {
        sortMap.put("name/create_date/desc", " ORDER BY gc.name, gc.create_date desc");
        sortMap.put("name/create_date", " ORDER BY gc.name, gc.create_date ");
        sortMap.put("create_date/desc", " ORDER BY gc.create_date DESC");
        sortMap.put("create_date", " ORDER BY gc.create_date ");
        sortMap.put("name/desc", " ORDER BY gc.name DESC");
        sortMap.put("name", " ORDER BY gc.name ");
    }


    private static final String FIELD_ID = "id";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_PRICE = "price";
    private static final String FIELD_DURATION = "duration";
    private static final String FIELD_CREATE_DATE = "createDate";
    private static final String FIELD_LAST_UPDATE = "lastUpdateDate";
    private static final String GIFT_CERTIFICATE_ID = "giftCertificateId";
    private static final String TAG_ID = "tagId";

    private static final String SELECT_GIFT_CERTIFICATE_BY_ID = "SELECT * FROM gift_certificate WHERE id=:id";
    private static final String SELECT_GIFT_CERTIFICATE = "SELECT * FROM gift_certificate";
    private static final String DELETE_GIFT_CERTIFICATE = "DELETE FROM gift_certificate WHERE id=:id";
    private static final String INSERT_GIFT_CERTIFICATE = "INSERT INTO gift_certificate(id,name,description,price,duration,create_date,last_update_date) " +
            "VALUES(:id,:name,:description,:price,:duration,:createDate,:lastUpdateDate)";
    private static final String EXIST_GIFT_CERTIFICATE_BY_ID = "select case when exists(select * from gift_certificate gc where gc.id =:id ) then true else false end as exist";
    private static final String UPDATE_GIFT_CERTIFICATE = "UPDATE gift_certificate SET name = :name, description = :description, price = :price, " +
            "duration = :duration, last_update_date = :lastUpdateDate WHERE id = :id";
    private static final String EXIST_GIFT_CERTIFICATE_BY_NAME = "select case when exists( select * from gift_certificate gc where lower(gc.name) = lower(:name) ) then true else false end ";
    private static final String SELECT_GIFT_CERTIFICATE_BY_NAME = "SELECT * FROM gift_certificate WHERE lower(name) = lower(:name)";
    private static final String INSERT_GIT_CERTIFICATE_TAG = "INSERT INTO gift_certificate_tag(gift_certificate_id,tag_id) VALUES(:giftCertificateId,:tagId)";


    public GiftCertificateDAOImpl(NamedParameterJdbcTemplate jdbcTemplate, MapSqlParameterSource parameterSource, GiftCertificateRowMapper giftCertificateRowMapper) {

        this.jdbcTemplate = jdbcTemplate;
        this.parameterSource = parameterSource;
        this.giftCertificateRowMapper = giftCertificateRowMapper;

    }

    @Override
    public GiftCertificate getById(UUID id) {

        parameterSource.addValue(FIELD_ID, id);

        List<GiftCertificate> certificates = jdbcTemplate.query(SELECT_GIFT_CERTIFICATE_BY_ID, parameterSource, giftCertificateRowMapper);
        return certificates.isEmpty() ? null : certificates.get(0);
    }


    @Override
    public List<GiftCertificate> findAll() {

        return jdbcTemplate.query(SELECT_GIFT_CERTIFICATE, giftCertificateRowMapper);

    }


    @Override
    public boolean deleteById(UUID id) {

        parameterSource.addValue(FIELD_ID, id);

        int delete = jdbcTemplate.update(DELETE_GIFT_CERTIFICATE, parameterSource);
        return delete == 1;

    }


    @Override
    public boolean save(GiftCertificate entity) {

        parameterSource
                .addValue(FIELD_ID, entity.getId()).addValue(FIELD_NAME, entity.getName())
                .addValue(FIELD_DESCRIPTION, entity.getDescription()).addValue(FIELD_PRICE, entity.getPrice())
                .addValue(FIELD_DURATION, entity.getDuration()).addValue(FIELD_CREATE_DATE, entity.getCreateDate())
                .addValue(FIELD_LAST_UPDATE, entity.getLastUpdateDate());

        int save = jdbcTemplate.update(INSERT_GIFT_CERTIFICATE, parameterSource);
        return save == 1;
    }


    @Override
    public boolean existsById(UUID id) {

        parameterSource.addValue(FIELD_ID, id);
        return (Boolean) jdbcTemplate.query(EXIST_GIFT_CERTIFICATE_BY_ID, parameterSource, (ResultSetExtractor<Object>) rs -> {
            if (rs.next()) {
                return rs.getBoolean(1);
            }
            return false;
        });
    }


    @Override
    public boolean update(GiftCertificate entity) {
        parameterSource.addValue(FIELD_NAME, entity.getName())
                .addValue(FIELD_DESCRIPTION, entity.getDescription())
                .addValue(FIELD_PRICE, entity.getPrice())
                .addValue(FIELD_DURATION, entity.getDuration())
                .addValue(FIELD_LAST_UPDATE, entity.getLastUpdateDate())
                .addValue(FIELD_ID, entity.getId());
        int update = jdbcTemplate.update(UPDATE_GIFT_CERTIFICATE, parameterSource);
        return update == 1;
    }


    @Override
    public boolean existByName(String name) {

        parameterSource.addValue(FIELD_NAME, name);
        return (Boolean) jdbcTemplate.query(EXIST_GIFT_CERTIFICATE_BY_NAME, parameterSource, (ResultSetExtractor<Object>) rs -> {
            if (rs.next()) {
                return rs.getBoolean(1);
            }
            return false;
        });
    }


    @Override
    public GiftCertificate getByName(String name) {

        parameterSource.addValue(FIELD_NAME, name);
        List<GiftCertificate> certificates = jdbcTemplate.query(SELECT_GIFT_CERTIFICATE_BY_NAME, parameterSource, giftCertificateRowMapper);
        return certificates.isEmpty() ? null : certificates.get(0);
    }


    @Override
    public boolean connectWithTag(UUID giftCertificateId, UUID tagId) {
        parameterSource.addValue(GIFT_CERTIFICATE_ID, giftCertificateId).addValue(TAG_ID, tagId);
        try {
            int connect = jdbcTemplate.update(INSERT_GIT_CERTIFICATE_TAG, parameterSource);
            return connect == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static final String EXIST_BY_GIFT_CERTIFICATE_AND_TAG = "select case when  exists( select * from gift_certificate_tag gct where gct.gift_certificate_id = :giftCertificateId and gct.tag_id = :tagId ) then true else false end ";

    @Override
    public boolean existByGiftCertificateIdAndTagId(UUID giftCertificateId, UUID tagId) {
        parameterSource.addValue(GIFT_CERTIFICATE_ID, giftCertificateId).addValue(TAG_ID, tagId);
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
        parameterSource.addValue(GIFT_CERTIFICATE_ID, giftCertificateId);
        jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_TAG, parameterSource);
    }

    private static final String EXIST_BY_NAME_AND_ID_NOT_EQUAL = "select case when exists( select * from gift_certificate gc where gc.id != :id and gc.name = :name ) then true else false end ";

    @Override
    public boolean existByNameAndIdNotEquals(UUID id, String name) {
        parameterSource.addValue(FIELD_ID, id).addValue(FIELD_NAME, name);
        return (Boolean) jdbcTemplate.query(EXIST_BY_NAME_AND_ID_NOT_EQUAL, parameterSource, (ResultSetExtractor<Object>) rs -> {
            if (rs.next()) {
                return rs.getBoolean(1);
            }
            return false;
        });
    }


    @Override
    public List<GiftCertificate> searchByFilters(String name, String description, String tagName, String sortParameters) {
        String searchQuery = "SELECT gc.*\n" +
                "FROM gift_certificate gc\n" +
                "         LEFT JOIN gift_certificate_tag gct on gc.id = gct.gift_certificate_id\n" +
                "         LEFT JOIN tag t on t.id = gct.tag_id\n";

        parameterSource.addValue(FIELD_NAME, name).addValue(FIELD_DESCRIPTION, description).addValue("tagName", tagName);
        String changeQuery = changeQuery(name, description, tagName,  searchQuery);

        if (sortParameters != null && sortMap.containsKey(sortParameters))
            changeQuery += sortMap.get(sortParameters);
        return jdbcTemplate.query(changeQuery, parameterSource, giftCertificateRowMapper);
    }

    Map<Parameter, Rule<String>> createRules(List<String> values, String queryProcess) {
        Map<Parameter, Rule<String>> ruleMap = new HashMap<>();
        ruleMap.put(Parameter.NAME, createRuleForName(values.get(0), queryProcess));
        ruleMap.put(Parameter.DESCRIPTION, createRuleForDescription(values.get(1), ruleMap.get(Parameter.NAME).process.get()));
        ruleMap.put(Parameter.TAG_NAME, createRuleForTagName(values.get(2), ruleMap.get(Parameter.DESCRIPTION).process.get()));
        return ruleMap;
    }


    Rule<String> createRuleForName(String value, String process) {
        return createRule(
                () -> value != null,
                () -> process + " WHERE lower(gc.name) LIKE concat('%', lower(:name), '%')"
        );
    }

    Rule<String> createRuleForDescription(String value, String process) {
        return createRule(
                () -> value != null,
                () ->
                        process.contains("WHERE")
                                ? process + " AND lower(gc.description) LIKE concat('%', lower(:description), '%')"
                                : process + " WHERE lower(gc.description) LIKE concat('%', lower(:description), '%')"
        );
    }

    Rule<String> createRuleForTagName(String value, String process) {
        return createRule(
                () -> value != null,
                () ->
                        process.contains("WHERE")
                                ? process + " AND lower(coalesce(t.name,'')) = lower(coalesce(:tagName,''))"
                                : process + " WHERE lower(coalesce(t.name,'')) = lower(coalesce(:tagName,''))"
        );
    }

    Rule<String> createRule(Supplier<Boolean> supplier, Supplier<String> process) {
        return new Rule<>(supplier, process);
    }

    String changeQuery(String name, String description, String tagName, String searchQuery) {
        List<String> values = new LinkedList<>(Arrays.asList(name, description, tagName));
        Map<Parameter, Rule<String>> rules = createRules(values, searchQuery);
        return Stream.of(Parameter.values())
                .filter(parameter -> rules.get(parameter).condition.get())
                .map(parameter -> rules.get(parameter).process.get())
                .reduce((first, second) -> second)
                .orElse(searchQuery);
    }
}
