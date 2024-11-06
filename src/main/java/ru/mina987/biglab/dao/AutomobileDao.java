package ru.mina987.biglab.dao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mina987.biglab.MainApplication;
import ru.mina987.biglab.domains.Automobile;
import ru.mina987.biglab.utils.DBHelper;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Класс AutomobileDao со свойством property.
 * <p>
 *     Данный класс разработан как реализация интерфейса Dao для сущности Автомобиль.
 * </p>
 * @author Ермилов Михаил
 * @version 1.0
 */
public class AutomobileDao implements Dao<Automobile, Long> {
    /** Поле свойство*/
    private Properties property;
    private ResourceBundle bundle = ResourceBundle.getBundle("employer", Locale.getDefault());
    private static final Logger logger = LoggerFactory.getLogger(MainApplication.class);
    /**
     * Конструктор – создание нового экземпляра
     * @see AutomobileDao#AutomobileDao()
     */
    public AutomobileDao() throws Exception {
        URL url = this.getClass()
                        .getResource("/ru/mina987/biglab/automobile.properties");
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
     * Функция получения коллекции {@link Automobile} из БД
     * @return возвращает коллекцию {@link Automobile}
     */
    @Override
    public Collection<Automobile> findALl() {
        List<Automobile> list = null;
        ResultSet rs = null;
        try(PreparedStatement statement = DBHelper.getConnection().prepareStatement(property.getProperty("sql.select"))){
            rs = statement.executeQuery();
            list = mapper(rs);
        }
        catch (SQLException e){
            logger.error(bundle.getString("querryerror"),e);
            System.out.println(e.getMessage());
        }
        logger.debug(bundle.getString("searchauto"));
        return list;
    }
    /**
     * Функция получения коллекции {@link Automobile} конкретного владельца
     * @param aLong идентификатор владельца автомобиля
     * @return возвращает коллекцию {@link Automobile}
     */
    public Collection<Automobile> findALlCarsOfOwner(Long aLong) {
        List<Automobile> list = null;
        ResultSet rs = null;
        try(PreparedStatement statement = DBHelper.getConnection().prepareStatement(property.getProperty("sql.selectownercars"))){
            statement.setLong(1,aLong);
            rs = statement.executeQuery();
            list = mapper(rs);
        }
        catch (SQLException e){
            logger.error(bundle.getString("querryerror"),e);
            System.out.println(e.getMessage());
        }
        logger.debug(bundle.getString("searchof"));
        return list;
    }
    /**
     * Функция преобразования результирующего набора(выборки) в коллекцию {@link Automobile}
     * @param rs результирующий набор
     * @return возвращает коллекцию {@link Automobile}
     */
    protected List<Automobile> mapper(ResultSet rs){
        List<Automobile> list = new ArrayList<>();
        try {
            while (rs.next()){
                list.add(new Automobile(
                        rs.getInt("id"),
                        rs.getString("mark"),
                        rs.getString("model"),
                        rs.getString("gosnumber"),
                        rs.getLong("idclient")
                ));
            }
        }
        catch (SQLException e){
            logger.error(bundle.getString("querryerror"),e);
            System.out.println(e.getMessage());
        }
        logger.debug(bundle.getString("rsparseauto"));
        return list;
    }
    /**
     * Функция добавления {@link Automobile} в БД
     * @param automobile сущность {@link Automobile}
     * @return возвращает сущность {@link Automobile}
     */
    @Override
    public Automobile save(Automobile automobile) {
//        try(PreparedStatement statement = MainApplication.getConnection().prepareStatement(property.getProperty("sql.insert"))){
            try(PreparedStatement statement = DBHelper.getConnection().prepareStatement(property.getProperty("sql.insert"))){
            statement.setString(1,automobile.getMark());
            statement.setString(2,automobile.getModel());
            statement.setString(3,automobile.getGosnumber());
            statement.setLong(4,automobile.getIdclient());
            statement.executeUpdate();
        }
        catch (SQLException e){
            logger.error(bundle.getString("querryerror"),e);
            System.out.println(e.getMessage());
        }
        logger.debug(bundle.getString("addquerryauto"));
        return automobile;
    }
    /**
     * Функция изменения {@link Automobile} в БД
     * @param automobile сущность {@link Automobile}
     * @return возвращает сущность {@link Automobile}
     */
    @Override
    public Automobile update(Automobile automobile) {
        try(PreparedStatement statement = DBHelper.getConnection().prepareStatement(property.getProperty("sql.update"))){
            statement.setString(1,automobile.getMark());
            statement.setString(2,automobile.getModel());
            statement.setString(3,automobile.getGosnumber());
            statement.setLong(4,automobile.getIdclient());
            statement.setLong(5,automobile.getAutomobileID());
            statement.executeUpdate();
        }
        catch (SQLException e){
            logger.error(bundle.getString("querryerror"),e);
            System.out.println(e.getMessage());
        }
        logger.debug(bundle.getString("editquerryauto"));
        return automobile;
    }
    /**
     * Функция удаления записи об Автомобиле из БД
     * @param aLong идентификатор Автомобиля
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
        logger.debug(bundle.getString("deleteauto"));
    }
    /**
     * Функция поиска экземпляра {@link Automobile} в БД
     * @param aLong идентификатор Автомобиля
     * @return возвращает сущность {@link Automobile}
     */
    @Override
    public Automobile findById(Long aLong) {
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
     * Функция получения экземпляра {@link Automobile} из БД
     * @param rs результирующий набор
     * @return возвращает сущность {@link Automobile}
     */
    protected Automobile getEntity(ResultSet rs){
        Automobile automobile = null;
        try {
            while (rs.next()){
                automobile = new Automobile(
                        rs.getInt("id"),
                        rs.getString("mark"),
                        rs.getString("model"),
                        rs.getString("gosnumber"),
                        rs.getLong("idclient")
                );
            }
        }
        catch (SQLException e){
            logger.error(bundle.getString("querryerror"),e);
            System.out.println(e.getMessage());
        }
//        logger.debug(bundle.getString("searchsingleauto"));
        return automobile;
    }
}
