package repository;

import java.sql.SQLException;

public interface Repository<T> {
    boolean remove(T object) throws SQLException;
}
