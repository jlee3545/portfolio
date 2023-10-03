package dao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NeonDbLeaderboard implements Leaderboard {

    /*
     * Add your connection string below
     *
     * Example:
     * private final String NEON_CONNECTION_STRING = postgres://dencee:zzzzyyyyaaaabbbb@ep-aged-forest-55306244.us-west-2.aws.neon.tech/neondb
     */
    private final String NEON_CONNECTION_STRING = "postgres://jlee3545:EdmFC8guaJ3k@ep-winter-sun-07783447.us-east-2.aws.neon.tech/neondb";

    private JdbcTemplate template;

    public NeonDbLeaderboard(){
        this.template = new JdbcTemplate(getDbSource(false));
    }

    @Override
    public List<Player> getLeaderboard(){
        List<Player> leaderboard = new ArrayList<>();

        String sql = "SELECT player_name, score, date " +
                     "FROM leaderboard " +
                     "ORDER BY score DESC, player_name " +
                     "LIMIT 10;";

        SqlRowSet results = this.template.queryForRowSet(sql);

        while(results.next()){
            String name = results.getString("player_name");
            int score = results.getInt("score");
            LocalDate date = results.getDate("date").toLocalDate();
            leaderboard.add(new Player(name, score));
        }

        return leaderboard;
    }

    @Override
    public void addLeaderboard(Player playerToAdd){

        String sql = "INSERT INTO leaderboard " +
                     "(player_name, score) " +
                     "VALUES " +
                     "(?, ?)";

        try {
            this.template.update(sql, playerToAdd.getName(), playerToAdd.getScore());
        } catch(DataAccessException e){
            e.printStackTrace();
        }
    }






    /*
     * Private methods
     */
    private DataSource getDbSource(boolean useLocalhost) {
        BasicDataSource dbSource = new BasicDataSource();

        if( useLocalhost ) {

            final String databaseName = "Leaderboard";
            final String connectionStr = "jdbc:postgresql://localhost:5432/" + databaseName;
            dbSource.setUrl(connectionStr);
            dbSource.setUsername("postgres");
            dbSource.setPassword("postgres1");

        } else {

            try {
                // Example connection string from neon db:
                // postgres://dencee:hYBjUxIml1p6@ep-aged-forest-55306244.us-west-2.aws.neon.tech/neondb
                //           |--+---|-----+------|--------------- hostname/address --------------|---+--|
                //    username_/       password                                       database name _/

                int startUsernameIndex = NEON_CONNECTION_STRING.indexOf("//") + "//".length();
                int endUsernameIndex = NEON_CONNECTION_STRING.indexOf(":", startUsernameIndex);
                final String username = NEON_CONNECTION_STRING.substring(startUsernameIndex, endUsernameIndex);

                int endPasswordIndex = NEON_CONNECTION_STRING.indexOf("@");
                final String password = NEON_CONNECTION_STRING.substring(endUsernameIndex + 1, endPasswordIndex);

                final String address = NEON_CONNECTION_STRING.substring(endPasswordIndex + 1);

                //                           "jdbc:postgresql://<hostname/address>/<database name>
                final String connectionStr = "jdbc:postgresql://" + address;
                dbSource.setUrl(connectionStr);
                dbSource.setUsername(username);
                dbSource.setPassword(password);

            } catch(IndexOutOfBoundsException e){
                String errorMessage = "Error parsing connection string (NEON_CONNECTION_STRING).\n" +
                        "Check that it is formatted correctly at the top of this file";
                System.out.println(errorMessage);
                e.printStackTrace();
                System.exit(1);
            }
        }

        return dbSource;
    }
}
