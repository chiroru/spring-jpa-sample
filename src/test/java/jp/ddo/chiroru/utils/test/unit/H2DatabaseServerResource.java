package jp.ddo.chiroru.utils.test.unit;

import java.io.IOException;
//import java.sql.Connection;
import java.util.Properties;

import org.h2.tools.Server;
//import org.h2.util.JdbcUtils;
import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * データベースサーバにアクセスするモジュールのユニットテスト実行時に、
 * H2 Database Server に対する事前処理、事後処理を行うための
 * {@code JUnit 4.x}系の{@link ExternalResource}です.
 * 
 * @author chiroru_0130@yahoo.co.jp
 * @since 1.0.0
 */
public class H2DatabaseServerResource
extends ExternalResource {

    private static final Logger L = LoggerFactory.getLogger(H2DatabaseServerResource.class);

    private Server server;
    private String baseDir;
    private String dbName;
    private String schemaName;
    private String userName;
    private String userPassword;

    public H2DatabaseServerResource() {
        DatabaseProperties p;
        try {
            p = new DatabaseProperties();
            baseDir = p.getBaseDir();
            dbName = p.getDBName();
            schemaName = p.getSchemaName();
            userName = p.getUserName();
            userPassword = p.getUserPassword();
            L.info("Initialize H2DatabaseServerResource.");
            L.info("[Loaded Properties] --------------------------------------------------");
            L.info(" Base Directory : " + baseDir);
            L.info(" Database Name  : " + dbName);
            L.info(" SCHEMA NAME    : " + schemaName);
            L.info(" USER NAME      : " + userName);
            L.info(" USER PASSWORD  : " + userPassword);
            L.info("----------------------------------------------------------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void before()
            throws Throwable {
        L.info("Execute H2DatabaseServerResource before method.Start up H2 Database Server...");
        server = Server.createTcpServer("-baseDir", baseDir);
        server.start();
        L.info("H2 Database Server started.");
/*        Properties info = new Properties();
        info.setProperty("user", userName);
        info.setProperty("password", userPassword);
        Connection conn = org.h2.Driver.load().connect(createUrl(), info);
        try {
            conn.createStatement()
            .execute("CREATE SCHEMA IF NOT EXISTS " + schemaName);
        } finally {
            JdbcUtils.closeSilently(conn);
        }*/
    }

    protected void after() {
        server.stop();
    }

/*    private String createUrl() {
        return "jdbc:h2:" + server.getURL() + "/" + dbName;
    }*/

    private static class DatabaseProperties {
        private final Properties props;
        private static final String PROPERTY_FILE_PATH = "/h2.properties";
        private static final String BASE_DIR_KEY = "base.dir";
        private static final String DB_NAME_KEY = "db.name";
        private static final String SCHEMA_NAME_KEY = "schema.name";
        private static final String USER_NAME_KEY = "user.name";
        private static final String USER_PASSWORD_KEY = "user.password";

        private DatabaseProperties() throws IOException {
            props = new Properties();
            props.load(this.getClass().getResourceAsStream(PROPERTY_FILE_PATH));
        }

        String getBaseDir() {
            return props.getProperty(BASE_DIR_KEY);
        }

        String getDBName() {
            return props.getProperty(DB_NAME_KEY);
        }

        String getSchemaName() {
            return props.getProperty(SCHEMA_NAME_KEY);
        }

        String getUserName() {
            return props.getProperty(USER_NAME_KEY);
        }

        String getUserPassword() {
            return props.getProperty(USER_PASSWORD_KEY);
        }
    }
}
