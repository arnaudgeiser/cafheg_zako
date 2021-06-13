package ch.hearc.cafheg.infrastructure.persistance;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Supplier;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Database {

  private static DataSource dataSource;
  private static ThreadLocal<Connection> connection = new ThreadLocal<>();

  private static final Logger logger = LoggerFactory.getLogger(Database.class);

  public Database() {
  }

  static Connection getConnection() {
    return connection.get();
  }

  public static <T> T inTransaction(Supplier<T> inTransaction) {
    try {
      connection.set(dataSource.getConnection());
      return inTransaction.get();
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      try {
        connection.get().close();
      } catch (SQLException e) {
        logger.error("Unable to createTransaction", e);
        throw new RuntimeException(e);
      }
      connection.remove();
    }
  }

  public DataSource getDataSource() {
    return dataSource;
  }

  public void start() {
    System.out.println("Initializing datasource");
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl("jdbc:h2:mem:sample");
    config.setMaximumPoolSize(20);
    config.setDriverClassName("org.h2.Driver");
    dataSource = new HikariDataSource(config);
    System.out.println("Datasource initialized");
  }
}
