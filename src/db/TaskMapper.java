package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import model.Task;
import model.TaskParameters;

public class TaskMapper implements DataMapper {

	private final String url = "jdbc:mysql://localhost:3306/knapsak";
	private final String user = "root";
	private final String password = "npibdvjnpeov";
	private int idTask;
	private int firstCritId;
	private int firstLimitationId;
	private int firstVarId;

	@Override
	public void save(Task task, TaskParameters param) throws SQLException {
		putDataInTableTasks(param);
		putDataInTableCriterions(param, task);
		putDataInTableLimitations(param, task);
		putDataInTableVariables(param, task);
		putDataInTableCosts(task);
		putDataInTableWeights(task);
	}

	private void putDataInTableWeights(Task task) throws SQLException {
		String mainQuery = "";
		mainQuery += "insert into weights (id_limitation, id_variable, weight) values ";
		for(int row = 0; row < task.getLimitationCount(); row++){
			for(int col = 0; col < task.getVariableCount(); col++){
				mainQuery += "("+ (firstLimitationId+row) +", "+(firstVarId+col)+ ", "+task.getValue(row+task.getCriterionCount(), col)+")";
				if(row < task.getLimitationCount()-1 || col < task.getVariableCount() - 1){
					mainQuery += ", ";
				}
			}
			
		}
		putDataIntoDB(mainQuery);
	}

	private void putDataInTableCosts(Task task) throws SQLException {
		String mainQuery = "";
		mainQuery += "insert into costs (id_criterion, id_variable, cost) values ";
		for(int row = 0; row < task.getCriterionCount(); row++){
			for(int col = 0; col < task.getVariableCount(); col++){
				mainQuery += "("+ (firstCritId+row) +", "+(firstVarId+col)+ ", "+task.getValue(row, col)+")";
				if(row < task.getCriterionCount()-1 || col < task.getVariableCount() - 1){
					mainQuery += ", ";
				}
			}
			
		}
		putDataIntoDB(mainQuery);
	}

	private void putDataInTableVariables(TaskParameters param, Task task)
			throws SQLException {
		String mainQuery = "";
		if (!param.isEconom() && !param.isSolved()) {
			mainQuery = "insert into variables (id, id_task) values ";
		}
		if (param.isEconom() && !param.isSolved()) {
			mainQuery = "insert into variables (id, id_task, name) values ";
		}
		if (!param.isEconom() && param.isSolved()) {
			mainQuery = "insert into variables (id, id_task, resultValue) values ";
		}
		if (param.isEconom() && param.isSolved()) {
			mainQuery = "insert into variables (id, id_task, name, resultValue) values ";
		}
		for (int col = 0; col < task.getVariableCount(); col++) {
			String query = "select maxCurrentId from myautoincrement where tableName = \'variables\'";
			int varId = getId(query);
			if(col == 0){
				firstVarId = varId;
			}
			query = "update myautoincrement set maxCurrentId = " + varId
					+ " where tableName = \'variables\'";
			putDataIntoDB(query);

			if (!param.isEconom() && !param.isSolved()) {
				mainQuery += "(" + varId + ", " + idTask + ")";
			}
			if (param.isEconom() && !param.isSolved()) {
				mainQuery += "(" + varId + ", " + idTask + ", \'"
						+ task.getVarNames().get(col) + "\')";
			}
			if (!param.isEconom() && param.isSolved()) {
				mainQuery += "(" + varId + ", " + idTask + ", "
						+ task.getSolutionVariable(col) + ")";
			}
			if (param.isEconom() && param.isSolved()) {
				mainQuery += "(" + varId + ", " + idTask + ", \'"
						+ task.getVarNames().get(col) + "\', "
						+ task.getSolutionVariable(col) + ")";
			}

			if (col < task.getVariableCount() - 1) {
				mainQuery += ", ";
			}
		}
		putDataIntoDB(mainQuery);

	}

	private void putDataInTableLimitations(TaskParameters param, Task task)
			throws SQLException {
		String mainQuery = "";
		if (!param.isEconom()) {
			mainQuery = "insert into limitations (id, id_task, limitValue) values ";
		} else {
			mainQuery = "insert into limitations (id, id_task, limitValue, name, unit) values ";
		}
		for (int row = 0; row < task.getLimitationCount(); row++) {
			String query = "select maxCurrentId from myautoincrement where tableName = \'limitations\'";
			int limitationId = getId(query);
			if(row == 0){
				firstLimitationId = limitationId;
			}
			query = "update myautoincrement set maxCurrentId = " + limitationId
					+ " where tableName = \'limitations\'";
			putDataIntoDB(query);

			if (!param.isEconom()) {
				mainQuery += "(" + limitationId + ", " + idTask + ", "
						+ task.getLimits().get(row) + ")";
			} else {
				mainQuery += "(" + limitationId + ", " + idTask + ", "
						+ task.getLimits().get(row) + ", \'"
						+ task.getLimitNames().get(row) + "\', \'"
						+ task.getLimitUnits().get(row) + "\')";
			}
			if (row < task.getLimitationCount() - 1) {
				mainQuery += ", ";
			}
		}
		putDataIntoDB(mainQuery);
	}

	private void putDataInTableCriterions(TaskParameters param, Task task)
			throws SQLException {
		String mainQuery = "";
		if (!param.isEconom()) {
			mainQuery = "insert into criterions (id, id_task) values ";
		} else {
			mainQuery = "insert into criterions (id, id_task, name, unit) values ";
		}
		for (int row = 0; row < task.getCriterionCount(); row++) {
			String query = "select maxCurrentId from myautoincrement where tableName = \'criterions\'";
			int critId = getId(query);
			if(row == 0){
				firstCritId = critId;
			}
			query = "update myautoincrement set maxCurrentId = " + critId
					+ " where tableName = \'criterions\'";
			putDataIntoDB(query);

			if (!param.isEconom()) {
				mainQuery += "(" + critId + ", " + idTask + ")";
			} else {
				mainQuery += "(" + critId + ", " + idTask + ", \'"
						+ task.getCritNames().get(row) + "\', \'"
						+ task.getCritUnits().get(row) + "\')";
			}
			if (row < task.getCriterionCount() - 1) {
				mainQuery += ", ";
			}
		}
		putDataIntoDB(mainQuery);
	}

	private void putDataInTableTasks(TaskParameters param) throws SQLException {
		String[] initials = param.getAuthorName().split("\\s");
		String query = "select id from authors where name = \'" + initials[1]
				+ "\'" + " and surname = \'" + initials[0]
				+ "\' and futhername = \'" + initials[2] + "\'";
		int idAuthor = getIdAuthor(query);

		query = "select maxCurrentId from myautoincrement where tableName = \'tasks\'";
		idTask = getId(query);
		query = "update myautoincrement set maxCurrentId = " + idTask
				+ " where tableName = \'tasks\'";
		putDataIntoDB(query);

		if (!param.isEconom()) {
			query = "insert into tasks (id, id_author, isMax, isEconom, name, dateOfCreating, canRewrite) ";
			query += "values (" + idTask + ", " + idAuthor + ", "
					+ param.isMax() + ", " + param.isEconom() + ", \'"
					+ param.getName() + "\', \'" + param.getSqlDate() + "\', "
					+ param.isCanRewrite() + ")";
		} else {
			query = "insert into tasks (id, id_author, isMax, isEconom, name, dateOfCreating, canRewrite, economMeaning) ";
			query += "values (" + idTask + ", " + idAuthor + ", "
					+ param.isMax() + ", " + param.isEconom() + ", \'"
					+ param.getName() + "\', \'" + param.getSqlDate() + "\', "
					+ param.isCanRewrite() + ", \'" + param.getEconomMeaning()
					+ "\')";
		}
		putDataIntoDB(query);
	}

	private int getId(String query) {
		int id = 0;
		try (Connection con = DriverManager.getConnection(url, user, password);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);) {
			while (rs.next()) {
				id = rs.getInt(1) + 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	private int getIdAuthor(String query) {
		int idAuthor = 0;
		try (Connection con = DriverManager.getConnection(url, user, password);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);) {
			while (rs.next()) {
				idAuthor = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idAuthor;
	}

	@Override
	public Task read() {
		return null;
	}

	private void putDataIntoDB(String query) throws SQLException {
		try (Connection con = DriverManager.getConnection(url, user, password);
				Statement stmt = con.createStatement();) {
			stmt.executeUpdate(query);
		}
	}

	public Vector<String> getAuthorsNames() {
		Vector<String> authorsNames = new Vector<>();
		String query = "select name, surname, futhername from authors";
		try (Connection con = DriverManager.getConnection(url, user, password);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);) {
			while (rs.next()) {
				String authorName = rs.getString(2);
				authorName += " " + rs.getString(1);
				authorName += " " + rs.getString(3);
				authorsNames.add(authorName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return authorsNames;
	}

}
