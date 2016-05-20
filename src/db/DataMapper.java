package db;

import java.sql.SQLException;

import model.Task;
import model.TaskParameters;

public interface DataMapper {

	public void save(Task task, TaskParameters param) throws SQLException;

	public Task read();
}
