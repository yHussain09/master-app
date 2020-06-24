package com.tznlab.masterapp.rest.controllers;

import com.tznlab.masterapp.services.RestApiServiceBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;

@RestController
@RequestMapping("api")
@CrossOrigin
public abstract class RestApiControllerBase<T, ID extends Serializable> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestApiControllerBase.class);

    //    @Autowired
//    @Qualifier("RestApiServiceBase")
    private final RestApiServiceBase<T, ID> service;

    public RestApiControllerBase(final RestApiServiceBase<T, ID> service) {
        this.service = service;
    }

//    @GetMapping("/all")
//    public Iterable<T> getAll(final Pageable pageable, final T entity, @RequestParam(required = false) ArrayList<String> exactSearchingFields) {
//        LOGGER.info (String.format ("RestControllerBase --> all of {0}", entity.getClass ().toString ()));
//        return service.getAll (pageable, entity, exactSearchingFields, false);
//    }

    @GetMapping("/all")
    public Iterable<T> getAll(final Pageable pageable, final T entity, @RequestParam(required = false) ArrayList<String> exactSearchingFields) {
        LOGGER.info(String.format("RestControllerBase --> all of {0}", entity.getClass().toString()));
//        return service.getAll (pageable, entity, exactSearchingFields, false);
        return service.getAll(pageable);
    }

    /*@GetMapping("/allWithoutPageable")
    public Iterable<T> getAllWithoutPageable(final Pageable pageable, final T entity, @RequestParam(required = false) ArrayList<String> exactSearchingFields) {
        LOGGER.info(String.format("RestControllerBase --> allWithoutPageable of {0}", entity.getClass().toString()));
        return service.getAll(Pageable.unpaged(), entity, exactSearchingFields, false);
    }

    @GetMapping("/allWithoutPageableAndIOrganizationBased")
    public Iterable<T> getAllWithoutPageableAndIOrganizationBased(final Pageable pageable, final T entity, @RequestParam(required = false) ArrayList<String> exactSearchingFields) {
        LOGGER.info(String.format("RestControllerBase --> allWithoutPageable of {0}", entity.getClass().toString()));
        return service.getAll(Pageable.unpaged(), entity, exactSearchingFields, true);
    }

    @GetMapping("getbyid/{id}")
    public Optional<T> getById(@PathVariable final ID id) {
        LOGGER.info (String.format ("RestControllerBase --> by Id {0}", id));
        return service.getById (id);
    }

    @GetMapping("/get")
    public Optional<T> getByIdParam(final ID id) {
        LOGGER.info (String.format ("RestControllerBase --> by Id {0}", id));
        return service.getById (id);
    }

//    @GetMapping("/getforcombo")
//    public Iterable<T> getForCombo(final T entity,@RequestParam(required = false) boolean ignoreOrga,
//                                   @RequestParam(required = false) Map<String, String> criteria) throws Exception{
//        LOGGER.info (String.format ("RestControllerBase --> all of {0}", entity.getClass ().toString ()));
//        return service.getForCombo ((Class<T>) entity.getClass(),criteria, ignoreOrga);
//    }

    @PostMapping("/save")
    public T save(@RequestBody final T entity) {
        LOGGER.info (String.format ("RestControllerBase --> save {0}", entity.toString ()));
        return service.save (entity);
    }
    @PostMapping("/saveAll")
    public List<T> saveAll(@RequestBody final List<T> entities) {
        LOGGER.info (String.format ("RestControllerBase --> save {0}", entities.get(0).toString ()));
        return service.saveAll (entities);
    }

    @PostMapping("/multiSave")
    public void multiSave(@RequestBody final TransEntity<ID> entity) throws NoSuchFieldException, IllegalAccessException {
        LOGGER.info(String.format ("RestControllerBase --> multi save {0}", entity.toString ()));;
        service.multiSave(entity);
    }


    @GetMapping("/getMultiLanguages")
    public Map<String,Object> getMultiLanguages(@RequestParam(name="rightLanguage") String leftOrRight,Pageable pageable,T entity) throws IllegalAccessException, InvocationTargetException, IntrospectionException, InstantiationException, SQLException, NoSuchMethodException, NoSuchFieldException, SQLException, IntrospectionException, InvocationTargetException {
        return service.getMultiLingualColumns(leftOrRight,pageable,entity);
    }

    @PutMapping("/update")
    public T update(@RequestBody final T entity) {
        LOGGER.info (String.format ("RestControllerBase --> update {0}", entity.toString ()));
        return service.update(entity);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestBody final T entity) {
        LOGGER.info (String.format ("RestControllerBase --> delete {0}", entity.toString ()));
        service.delete(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestBody final ID id) {
        LOGGER.info (String.format ("RestControllerBase --> delete by Id {0}", id));
        service.deleteEntityById(id);
    }
    @PostMapping("/allowablesave")
    public void allowableSave(@RequestBody final Selectable<T> selectable) {
        LOGGER.info(String.format("RestControllerBase --> allowablesave {0}", selectable.toString()));
        PersistenceUnitUtil persistenceUnitUtil = BeanUtil.getBean(EntityManager.class).
                getEntityManagerFactory().getPersistenceUnitUtil();

        Object ID = persistenceUnitUtil.getIdentifier(selectable.getModel());

        if (!selectable.selected) {
            if (ID != null) {
                *//**
     * TODO : getById change
     *//*
                Optional<T> tempEntityOpt = service.getById((ID) ID);
                if (tempEntityOpt.isPresent()) {
                    service.deleteEntityById((ID) ID);
                }
            }
        }

        if (selectable.isSelected()) {
            service.save(selectable.getModel());
        }

    }

    @GetMapping("/alllang")
    public Iterable<T> getAllMultiLang(final Pageable pageable, final T entity, ArrayList<String> exactSearchingFields) {
        LOGGER.info(String.format("RestControllerBase --> alllang {0}", entity.getClass().toString()));
        return service.getAllWithTranslations (pageable, entity, exactSearchingFields);
    }

    @GetMapping("/alllang/{id}")
    public Optional<T> getByIdMultiLang(@PathVariable final ID id) {
        LOGGER.info (String.format ("RestControllerBase --> alllang by Id {0}", id));
        return service.getByIdWithTranslations(id);
    }

    protected Pageable getMappedPageable(Pageable pageable) {
        List<Sort.Order> orders = new ArrayList<>();
        Map<String, String> mapping = service.getAvailableColumnsMapping();
        Iterator<Sort.Order> actualOrders = pageable.getSort().iterator();
        while (actualOrders.hasNext()) {
            Sort.Order actualOrder = actualOrders.next();
//            Sort.Order newOrder = new Sort.Order(actualOrder.getDirection(), mapping.get(actualOrder.getProperty()));
            Sort.Order newOrder = new Sort.Order(actualOrder.getDirection(), this.getSortingColumn(actualOrder.getProperty().toString()));
            orders.add(newOrder);
        }
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(orders));
//        return pageable;
    }

    public String getSortingColumn(String key) {
        if (key.contains("available.")) {
            String[] field = key.split("\\.");
            return field[1];
        }
        return key;
    }
    @GetMapping("/getAvailable/{attributeName}")
    public Iterable<SelectAvailable<T, ?>> getAvailable(final Pageable pageable,@PathVariable("attributeName") String attributeName, @RequestParam Map<String, Object> filters) throws InstantiationException, IllegalAccessException, IntrospectionException {
        return service.getAvailable(pageable,attributeName, filters);
    }*/

}
