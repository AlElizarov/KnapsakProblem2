package db;

import java.sql.Date;
import java.sql.SQLException;

import model.Task;
import model.TaskParameters;

public interface DataMapper {

	public void save(Task task, TaskParameters param) throws SQLException;

	public void read(String name, String author, Date taskDate, Task task) throws SQLException;
}
