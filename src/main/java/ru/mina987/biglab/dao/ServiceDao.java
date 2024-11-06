package ru.mina987.biglab.dao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mina987.biglab.MainApplication;
import ru.mina987.biglab.domains.Service;
import ru.mina987.biglab.utils.DBHelper;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;

/**
 * Класс ServiceDao со свойством property.
 * <p>
 *     Данный класс разработан как реализация интерфейса Dao для сущности Услуга.
 * </p>
 * @author Ермилов Михаил
 * @version 1.0
 */
public class ServiceDao implements Dao<Service, Long> {
    /** Поле свойство*/
    private Properties property;
    private ResourceBundle bundle = ResourceBundle.getBundle("administrator", Locale.getDefault());
    private static final Logger logger = LoggerFactory.getLogger(MainApplication.class);
    /**
     * Конструктор – создание нового экземпляра
     * @see ServiceDao#ServiceDao()
     */
    public ServiceDao() throws Exception {
        URL url = this.getClass()
                        .getResource("/ru/mina987/biglab/service.properties");
        this.property = new Properties();
        FileInputStream fis = null;
        if(url==null)
            throw new Exception();
        try {
            fis = new FileInputStream(url.getFile());
            property.load(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Функция получения коллекции {@link Service} из БД
     * @return возвращает коллекцию {@link Service}
     */
    @Override
    public Collection<Service> findALl() {
        List<Service> list = null;
        ResultSet rs = null;
        try(PreparedStatement statement = DBHelper.getConnection().prepareStatement(property.getProperty("sql.select"))){
            rs = statement.executeQuery();
            list = mapper(rs);
        }
        catch (SQLException e){
            logger.error(bundle.getString("querryerror"),e);
            System.out.println(e.getMessage());
        }
        logger.debug(bundle.getString("searchservice"));
        return list;
    }
    /**
     * Функция преобразования результирующего набора(выборки) в коллекцию {@link Service}
     * @param rs результирующий набор
     * @return возвращает коллекцию {@link Service}
     */
    protected List<Service> mapper(ResultSet rs){
        List<Service> list = new ArrayList<>();
        try {
            while (rs.next()){
                list.add(new Service(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("duration"),
                        rs.getInt("price"),
                        rs.getString("description")
                ));
            }
        }
        catch (SQLException e){
            logger.error(bundle.getString("querryerror"),e);
            System.out.println(e.getMessage());
        }
        logger.debug(bundle.getString("rsparseservice"));
        return list;
    }
    /**
     * Функция добавления {@link Service} в БД
     * @param service сущность {@link Service}
     * @return возвращает сущность {@link Service}
     */
    @Override
    public Service save(Service service) {
            try(PreparedStatement statement = DBHelper.getConnection().prepareStatement(property.getProperty("sql.insert"))){
            statement.setString(1,service.getName());
            statement.setInt(2,service.getDuration());
            statement.setInt(3,service.getPrice());
            if(service.getDescription()!=null&&!service.getDescription().trim().isEmpty())
                statement.setString(4,service.getDescription());
            else
                statement.setNull(4, Types.VARCHAR);
            statement.executeUpdate();
        }
        catch (SQLException e){
            logger.error(bundle.getString("querryerror"),e);
            System.out.println(e.getMessage());
        }
        logger.debug(bundle.getString("addquerryservice"));
        return service;
    }
    /**
     * Функция изменения {@link Service} в БД
     * @param service сущность {@link Service}
     * @return возвращает сущность {@link Service}
     */
    @Override
    public Service update(Service service) {
        try(PreparedStatement statement = DBHelper.getConnection().prepareStatement(property.getProperty("sql.update"))){
            statement.setString(1,service.getName());
            statement.setInt(2,service.getDuration());
            statement.setInt(3,service.getPrice());
            if(service.getDescription()!=null&&!service.getDescription().trim().isEmpty())
                statement.setString(4,service.getDescription());
            else
                statement.setNull(4, Types.VARCHAR);
            statement.setLong(5,service.getServiceID());
            statement.executeUpdate();
        }
        catch (SQLException e){
            logger.error(bundle.getString("querryerror"),e);
            System.out.println(e.getMessage());
        }
        logger.debug(bundle.getString("editquerryservice"));
        return service;
    }
    /**
     * Функция удаления записи об Услуге из БД
     * @param aLong идентификатор Услуги
     */
    @Override
    public void deleteById(Long aLong) {
        try(PreparedStatement statement = DBHelper.getConnection().prepareStatement(property.getProperty("sql.delete"))){
            statement.setLong(1,aLong);
            statement.execute();
        }
        catch (SQLException e){
            logger.error(bundle.getString("querryerror"),e);
            System.out.println(e.getMessage());
        }
        logger.debug(bundle.getString("deleteservice"));
    }
    /**
     * Функция поиска экземпляра {@link Service} в БД
     * @param aLong идентификатор Услуги
     * @return возвращает сущность {@link Service}
     */
    @Override
    public Service findById(Long aLong) {
        ResultSet rs = null;
        try(PreparedStatement statement = DBHelper.getConnection().prepareStatement(property.getProperty("sql.findbyid"))){
            statement.setLong(1,aLong);
            rs = statement.executeQuery();
            return getEntity(rs);
        }
        catch (SQLException e){
            logger.error(bundle.getString("querryerror"),e);
            System.out.println(e.getMessage());
        }
        return null;
    }
    /**
     * Функция получения экземпляра {@link Service} из БД
     * @param rs результирующий набор
     * @return возвращает сущность {@link Service}
     */
    protected Service getEntity(ResultSet rs){
        Service service = null;
        try {
            while (rs.next()){
                service = new Service(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("duration"),
                        rs.getInt("price"),
                        rs.getString("description")
                );
            }
        }
        catch (SQLException e){
            logger.error(bundle.getString("querryerror"),e);
            System.out.println(e.getMessage());
        }
//        logger.debug(bundle.getString("searchsingleservice"));
        return service;
    }
}
