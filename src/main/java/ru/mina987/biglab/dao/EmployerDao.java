package ru.mina987.biglab.dao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mina987.biglab.MainApplication;
import ru.mina987.biglab.domains.Employer;
import ru.mina987.biglab.utils.DBHelper;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;

/**
 * Класс EmployerDao со свойством property.
 * <p>
 *     Данный класс разработан как реализация интерфейса Dao для сущности Сотрудник.
 * </p>
 * @author Ермилов Михаил
 * @version 1.0
 */
public class EmployerDao implements Dao<Employer, Long>{
    /** Поле свойство*/
    private Properties property;
    private ResourceBundle bundle = ResourceBundle.getBundle("administrator", Locale.getDefault());
    private static final Logger logger = LoggerFactory.getLogger(MainApplication.class);
    /**
     * Конструктор – создание нового экземпляра
     * @see EmployerDao#EmployerDao()
     */
    public EmployerDao() throws Exception {
        URL url = this.getClass()
                .getResource("/ru/mina987/biglab/employer.properties");
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
     * Функция получения коллекции {@link Employer} из БД
     * @return возвращает коллекцию {@link Employer}
     */
    @Override
    public Collection<Employer> findALl() {
        List<Employer> list = null;
        ResultSet rs = null;
        try(PreparedStatement statement = DBHelper.getConnection().prepareStatement(property.getProperty("sql.select"))){
            rs = statement.executeQuery();
            list = mapper(rs);
            for (Employer emp: list
                 ) {
                if (emp.getAppartment()==0)
                    emp.setNullAppartment();
            }
        }
        catch (SQLException e){
            logger.error(bundle.getString("querryerror"),e);
            System.out.println(e.getMessage());
        }
        logger.debug(bundle.getString("searchemployer"));
        return list;
    }
    /**
     * Функция преобразования результирующего набора(выборки) в коллекцию {@link Employer}
     * @param rs результирующий набор
     * @return возвращает коллекцию {@link Employer}
     */
    protected List<Employer> mapper(ResultSet rs){
        List<Employer> list = new ArrayList<>();
        try {
            while (rs.next()){
                list.add(new Employer(
                        rs.getInt("id"),
                        rs.getString("surname"),
                        rs.getString("name"),
                        rs.getString("patronym"),
                        rs.getString("phone"),
                        rs.getString("city"),
                        rs.getString("street"),
                        rs.getInt("house"),
                        rs.getInt("appartment")
                        ));
            }
        }
        catch (SQLException e){
            logger.error(bundle.getString("querryerror"),e);
            System.out.println(e.getMessage());
        }
        logger.debug(bundle.getString("rsparseemployer"));
        return list;
    }
    /**
     * Функция добавления {@link Employer} в БД
     * @param employer сущность {@link Employer}
     * @return возвращает сущность {@link Employer}
     */
    @Override
    public Employer save(Employer employer) {
        try(PreparedStatement statement = DBHelper.getConnection().prepareStatement(property.getProperty("sql.insert"))){
            statement.setString(1,employer.getSurName());
            statement.setString(2,employer.getName());
            statement.setString(3,employer.getPatronym());
            statement.setString(4,employer.getPhone());
            statement.setString(5,employer.getCity());
            statement.setString(6,employer.getStreet());
            statement.setInt(7,employer.getHouse());
            if(employer.getAppartment()!=null&&employer.getAppartment()>0)
                statement.setInt(8,employer.getAppartment());
            else
                statement.setNull(8,Types.INTEGER);
            statement.executeUpdate();
        }
        catch (SQLException e){
            logger.error(bundle.getString("querryerror"),e);
            System.out.println(e.getMessage());
        }
        logger.debug(bundle.getString("addquerryemployer"));
        return employer;
    }
    /**
     * Функция изменения {@link Employer} в БД
     * @param employer сущность {@link Employer}
     * @return возвращает сущность {@link Employer}
     */
    @Override
    public Employer update(Employer employer) {
        try(PreparedStatement statement = DBHelper.getConnection().prepareStatement(property.getProperty("sql.update"))){
            statement.setString(1,employer.getSurName());
            statement.setString(2,employer.getName());
            statement.setString(3,employer.getPatronym());
            statement.setString(4,employer.getPhone());
            statement.setString(5,employer.getCity());
            statement.setString(6,employer.getStreet());
            statement.setInt(7,employer.getHouse());
            if(employer.getAppartment()!=null&&employer.getAppartment()>0)
                statement.setInt(8,employer.getAppartment());
            else
                statement.setNull(8, Types.INTEGER);
            statement.setLong(9,employer.getEmployerID());
            statement.executeUpdate();
        }
        catch (SQLException e){
            logger.error(bundle.getString("querryerror"),e);
            System.out.println(e.getMessage());
        }
        logger.debug(bundle.getString("editquerryemployer"));
        return employer;
    }
    /**
     * Функция удаления записи о Сотруднике из БД
     * @param aLong идентификатор Сотрудника
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
        logger.debug(bundle.getString("deleteemployer"));
    }
    /**
     * Функция поиска экземпляра {@link Employer} в БД
     * @param aLong идентификатор Сотрудника
     * @return возвращает сущность {@link Employer}
     */
    @Override
    public Employer findById(Long aLong) {
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
     * Функция получения экземпляра {@link Employer} из БД
     * @param rs результирующий набор
     * @return возвращает сущность {@link Employer}
     */
    protected Employer getEntity(ResultSet rs){
        Employer employer = null;
        try {
            while (rs.next()){
                employer = new Employer(
                        rs.getInt("id"),
                        rs.getString("surname"),
                        rs.getString("name"),
                        rs.getString("patronym"),
                        rs.getString("phone"),
                        rs.getString("city"),
                        rs.getString("street"),
                        rs.getInt("house"),
                        rs.getInt("appartment")
                );
            }
        }
        catch (SQLException e){
            logger.error(bundle.getString("querryerror"),e);
            System.out.println(e.getMessage());
        }
//        logger.debug(bundle.getString("searchsingleemployer"));
        return employer;
    }
}
