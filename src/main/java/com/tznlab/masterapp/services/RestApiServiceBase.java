package com.tznlab.masterapp.services;

import com.tznlab.masterapp.dao.RestApiRepositoryBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.MappedSuperclass;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//@Service("RestApiServiceBase")
//@Configuration
public abstract class RestApiServiceBase<T, ID extends Serializable> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestApiServiceBase.class);
    protected final RestApiRepositoryBase<T, ID> repository;
    //    protected final ISession session;
    @Autowired
    protected JdbcTemplate DB;
    @Autowired
    protected EntityManager entityManager;
    private Class<T> entityClass;
    private CriteriaQuery<Tuple> tupleQuery;
    private CriteriaQuery<Long> countQuery;
    private CriteriaQuery<T> entityQuery;
    private Boolean organizationBased;
    private Boolean locationBased;

    public RestApiServiceBase(final RestApiRepositoryBase<T, ID> repository) {
//        this.session = session;
        this.repository = repository;
        this.entityClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public Iterable<T> getAll(final Pageable pageable) {

        return this.repository.findAll(pageable);

    }
}
