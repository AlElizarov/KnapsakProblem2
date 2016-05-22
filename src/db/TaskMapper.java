package db;

import java.sql.Connection;
import java.sql.Date;
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
		for (int row = 0; row < task.getLimitationCount(); row++) {
			for (int col = 0; col < task.getVariableCount(); col++) {
				mainQuery += "(" + (firstLimitationId + row) + ", "
						+ (firstVarId + col) + ", "
						+ task.getValue(row + task.getCriterionCount(), col)
						+ ")";
				if (row < task.getLimitationCount() - 1
						|| col < task.getVariableCount() - 1) {
					mainQuery += ", ";
				}
			}

		}
		putDataIntoDB(mainQuery);
	}

	private void putDataInTableCosts(Task task) throws SQLException {
		String mainQuery = "";
		mainQuery += "insert into costs (id_criterion, id_variable, cost) values ";
		for (int row = 0; row < task.getCriterionCount(); row++) {
			for (int col = 0; col < task.getVariableCount(); col++) {
				mainQuery += "(" + (firstCritId + row) + ", "
						+ (firstVarId + col) + ", " + task.getValue(row, col)
						+ ")";
				if (row < task.getCriterionCount() - 1
						|| col < task.getVariableCount() - 1) {
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
			if (col == 0) {
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
			if (row == 0) {
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
			if (row == 0) {
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
		String query = "select id from authors where name = \'";
		query += checkAuthor(initials);
		int idAuthor = getIdAuthor(query);

		query = "select maxCurrentId from myautoincrement where tableName = \'tasks\'";
		idTask = getId(query);
		query = "update myautoincrement set maxCurrentId = " + idTask
				+ " where tableName = \'tasks\'";
		putDataIntoDB(query);

		if (!param.isEconom()) {
			query = "insert into tasks (id, id_author, isMax, isEconom, name, dateOfCreating, canRewrite";
			if (!param.getNote().equals("")) {
				query += ", note, isSolved)";
			} else {
				query += ", isSolved)";
			}
			query += "values (" + idTask + ", " + idAuthor + ", "
					+ param.isMax() + ", " + param.isEconom() + ", \'"
					+ param.getName() + "\', \'" + param.getSqlDate() + "\', "
					+ param.isCanRewrite();
		} else {
			query = "insert into tasks (id, id_author, isMax, isEconom, name, dateOfCreating, canRewrite, economMeaning";
			if (!param.getNote().equals("")) {
				query += ", note, isSolved)";
			} else {
				query += ", isSolved)";
			}
			query += "values (" + idTask + ", " + idAuthor + ", "
					+ param.isMax() + ", " + param.isEconom() + ", \'"
					+ param.getName() + "\', \'" + param.getSqlDate() + "\', "
					+ param.isCanRewrite() + ", \'" + param.getEconomMeaning()
					+ "\'";
		}
		if (!param.getNote().equals("")) {
			query += ", \'" + param.getNote() + "\'";
		} else {
			query += "";
		}
		query += ", " +param.isSolved()+")";
		putDataIntoDB(query);
	}

	private String checkAuthor(String[] initials) {
		String query = initials[1] + "\'" + " and surname = \'" + initials[0]
				+ "\'";
		if (initials.length == 3) {
			query += " and futhername = \'" + initials[2] + "\'";
		}
		return query;
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

	private void putDataIntoDB(String query) throws SQLException {
		try (Connection con = DriverManager.getConnection(url, user, password);
				Statement stmt = con.createStatement();) {
			stmt.executeUpdate(query);
		}
	}

	public Vector<String> getAuthorsNames() {
		Vector<String> authorsNames = new Vector<>();
		String query = "select name, surname, futhername from authors order by name, surname, futhername";
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

	public void addAuthor(String name, String surname, String futhername)
			throws SQLException {
		String query = "select maxCurrentId from myautoincrement where tableName = \'authors\'";
		int idAuthor = getId(query);
		query = "insert into authors values (" + (idAuthor + 1) + ", \'" + name
				+ "\', \'" + surname + "\', \'" + futhername + "\')";
		putDataIntoDB(query);

		query = "update myautoincrement set maxCurrentId = " + (idAuthor + 1)
				+ " where tableName = \'authors\'";
		putDataIntoDB(query);
	}

	public void deleteAuthor(String author) throws SQLException {
		String[] initials = author.split("\\s");
		String query = "delete from authors where name = \'";
		query += checkAuthor(initials);
		putDataIntoDB(query);
	}

	public Vector<String> readTasks() {
		Vector<String> tasks = new Vector<>();
		String query = "select tasks.name, surname, authors.name, futhername, dateOfCreating from tasks, authors"
				+ " where tasks.id_author = authors.id"
				+ " order by tasks.name, surname, authors.name, futhername, dateOfCreating";
		try (Connection con = DriverManager.getConnection(url, user, password);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);) {
			while (rs.next()) {
				String task = rs.getString(1);
				task += "; " + rs.getString(2);
				task += " " + rs.getString(3);
				task += " " + rs.getString(4);
				task += "; " + rs.getString(5);
				tasks.add(task);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tasks;
	}

	public Vector<String> readTasks(String author) {
		String[] initials = author.split("\\s");
		Vector<String> tasks = new Vector<>();
		String query = "select tasks.name, surname, authors.name, futhername, dateOfCreating from tasks, authors"
				+ " where tasks.id_author = authors.id and authors.name = \'";
		query += checkAuthor(initials);
		query += " order by tasks.name, surname, authors.name, futhername, dateOfCreating";
		try (Connection con = DriverManager.getConnection(url, user, password);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);) {
			while (rs.next()) {
				String task = rs.getString(1);
				task += "; " + rs.getString(2);
				task += " " + rs.getString(3);
				task += " " + rs.getString(4);
				task += "; " + rs.getString(5);
				tasks.add(task);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tasks;
	}

	public Vector<String> readTasks(String author, Date sd, Date fd) {
		String[] initials = author.split("\\s");
		Vector<String> tasks = new Vector<>();
		String query = "select tasks.name, surname, authors.name, futhername, dateOfCreating from tasks, authors"
				+ " where tasks.id_author = authors.id and authors.name = \'";

		query += checkAuthor(initials);
		query += " and dateOfCreating between \'"
				+ sd
				+ "\' and \'"
				+ fd
				+ "\'"
				+ " order by tasks.name, surname, authors.name, futhername, dateOfCreating";
		try (Connection con = DriverManager.getConnection(url, user, password);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);) {
			while (rs.next()) {
				String task = rs.getString(1);
				task += "; " + rs.getString(2);
				task += " " + rs.getString(3);
				task += " " + rs.getString(4);
				task += "; " + rs.getString(5);
				tasks.add(task);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tasks;
	}

	public Vector<String> readTasks(Date sd, Date fd) {
		Vector<String> tasks = new Vector<>();
		String query = "select tasks.name, surname, authors.name, futhername, dateOfCreating from tasks, authors"
				+ " where tasks.id_author = authors.id and dateOfCreating between \'"
				+ sd
				+ "\' and \'"
				+ fd
				+ "\'"
				+ " order by tasks.name, surname, authors.name, futhername, dateOfCreating";
		try (Connection con = DriverManager.getConnection(url, user, password);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);) {
			while (rs.next()) {
				String task = rs.getString(1);
				task += "; " + rs.getString(2);
				task += " " + rs.getString(3);
				task += " " + rs.getString(4);
				task += "; " + rs.getString(5);
				tasks.add(task);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tasks;
	}

	public boolean readCanRewrite(String name, String author, Date taskDate) {
		String[] initials = author.split("\\s");
		String query = "select canRewrite from tasks where name = \'" + name
				+ "\' and id_author = (select id from authors where name = \'";
		query += checkAuthor(initials);
		query += ") and dateOfCreating = \'" + taskDate + "\'";
		boolean canRewrite = false;
		try (Connection con = DriverManager.getConnection(url, user, password);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);) {
			while (rs.next()) {
				canRewrite = rs.getBoolean(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return canRewrite;
	}

	public String readNote(String name, String author, Date taskDate) {
		String[] initials = author.split("\\s");
		String query = "select note from tasks where name = \'" + name
				+ "\' and id_author = (select id from authors where name = \'";
		query += checkAuthor(initials);
		query += ") and dateOfCreating = \'" + taskDate + "\'";
		String note = "";
		try (Connection con = DriverManager.getConnection(url, user, password);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);) {
			while (rs.next()) {
				note = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return note;
	}

	public boolean readIsMax(String name, String author, Date taskDate) {
		String[] initials = author.split("\\s");
		String query = "select isMax from tasks where name = \'" + name + "\'"
				+ " and id_author = (select id from authors where name = \'";
		query += checkAuthor(initials);
		query += ") and dateOfCreating = \'" + taskDate + "\'";
		boolean isMax = false;
		try (Connection con = DriverManager.getConnection(url, user, password);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);) {
			while (rs.next()) {
				isMax = rs.getBoolean(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isMax;
	}

	public boolean readIsEconom(String name, String author, Date taskDate) {
		String[] initials = author.split("\\s");
		String query = "select isEconom from tasks where name = \'" + name
				+ "\' and id_author = (select id from authors where name = \'";
		query += checkAuthor(initials);
		query += ") and dateOfCreating = \'" + taskDate + "\'";
		boolean isEconom = false;
		try (Connection con = DriverManager.getConnection(url, user, password);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);) {
			while (rs.next()) {
				isEconom = rs.getBoolean(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isEconom;
	}

	public String readEconomText(String name, String author, Date taskDate) {
		String[] initials = author.split("\\s");
		String query = "select economMeaning from tasks where name = \'" + name
				+ "\' and id_author = (select id from authors where name = \'";
		query += checkAuthor(initials);
		query += ") and dateOfCreating = \'" + taskDate + "\'";
		String economMeaning = "";
		try (Connection con = DriverManager.getConnection(url, user, password);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);) {
			while (rs.next()) {
				economMeaning = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return economMeaning;
	}

	public int readVarCount(String name, String author, Date taskDate) {
		String[] initials = author.split("\\s");
		String query = "select count(id) from variables where id_task = (select id from tasks where name = \'"
				+ name
				+ "\' and id_author = (select id from authors where name = \'";
		query += checkAuthor(initials);
		query += ") and dateOfCreating = \'" + taskDate + "\')";
		int varCount = 0;
		try (Connection con = DriverManager.getConnection(url, user, password);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);) {
			while (rs.next()) {
				varCount = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return varCount;
	}

	public int readCriterionCount(String name, String author, Date taskDate) {
		String[] initials = author.split("\\s");
		String query = "select count(id) from criterions where id_task = (select id from tasks where name = \'"
				+ name
				+ "\' and id_author = (select id from authors where name = \'";
		query += checkAuthor(initials);
		query += ") and dateOfCreating = \'" + taskDate + "\')";
		int criterionCount = 0;
		try (Connection con = DriverManager.getConnection(url, user, password);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);) {
			while (rs.next()) {
				criterionCount = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return criterionCount;
	}

	public int readLimitationCount(String name, String author, Date taskDate) {
		String[] initials = author.split("\\s");
		String query = "select count(id) from limitations where id_task = (select id from tasks where name = \'"
				+ name
				+ "\' and id_author = (select id from authors where name = \'";
		query += checkAuthor(initials);
		query += ") and dateOfCreating = \'" + taskDate + "\')";
		int limitationCount = 0;
		try (Connection con = DriverManager.getConnection(url, user, password);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);) {
			while (rs.next()) {
				limitationCount = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return limitationCount;
	}

	public void readEconom(String name, String author, Date taskDate, Task task) {
		read(name, author, taskDate, task);
		readNamesAndUnits(name, author, taskDate, task);
		readVarNames(name, author, taskDate, task);
	}

	private void readVarNames(String name, String author, Date taskDate,
			Task task) {
		String[] initials = author.split("\\s");
		String query = "select name from variables"
				+ " where id_task = (select id from tasks where name = \'"
				+ name
				+ "\' and id_author = (select id from authors where name = \'";
		query += checkAuthor(initials);
		query += ") and dateOfCreating = \'" + taskDate + "\')";
		
		try (Connection con = DriverManager.getConnection(url, user, password);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);) {
			int col = 0;
			while (rs.next()) {
				task.getVarNames().set(col, rs.getString(1));
				col++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void readNamesAndUnits(String name, String author, Date taskDate,
			Task task) {
		String[] initials = author.split("\\s");
		String query = "select name, unit from criterions"
				+ " where id_task = (select id from tasks where name = \'"
				+ name
				+ "\' and id_author = (select id from authors where name = \'";
		query += checkAuthor(initials);
		query += ") and dateOfCreating = \'" + taskDate + "\') UNION ";
		query += "select name, unit from limitations"
				+ " where id_task = (select id from tasks where name = \'"
				+ name
				+ "\' and id_author = (select id from authors where name = \'";
		query += checkAuthor(initials);
		query += ") and dateOfCreating = \'" + taskDate + "\')";

		try (Connection con = DriverManager.getConnection(url, user, password);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);) {
			int row = 0;
			while (rs.next()) {

				String ecName = rs.getString(1);
				if (row < task.getCriterionCount()) {
					task.getCritNames().set(row, ecName);
				} else {
					task.getLimitNames().set(row - task.getCriterionCount(),
							ecName);
				}
				String unit = rs.getString(2);
				if (row < task.getCriterionCount()) {
					task.getCritUnits().set(row, unit);
				}
				else{
					task.getLimitUnits().set(row-task.getCriterionCount(), unit);
				}
				row++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void read(String name, String author, Date taskDate, Task task) {
		readCosts(name, author, taskDate, task);
		readWeights(name, author, taskDate, task);
		readLimits(name, author, taskDate, task);
	}

	private void readLimits(String name, String author, Date taskDate, Task task) {
		String[] initials = author.split("\\s");
		String query = "select limitValue from limitations"
				+ " where id_task = (select id from tasks where name = \'"
				+ name
				+ "\' and id_author = (select id from authors where name = \'";
		query += checkAuthor(initials);
		query += ") and dateOfCreating = \'" + taskDate + "\')";

		try (Connection con = DriverManager.getConnection(url, user, password);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);) {
			int row = 0;
			int col = task.getVariableCount();
			while (rs.next()) {

				int val = rs.getInt(1);
				task.setValue(val, row + task.getCriterionCount(), col);
				row++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void readWeights(String name, String author, Date taskDate,
			Task task) {
		String[] initials = author.split("\\s");
		String query = "select weight from weights"
				+ " where id_limitation in ( select id from limitations"
				+ " where id_task = (select id from tasks where name = \'"
				+ name
				+ "\' and id_author = (select id from authors where name = \'";
		query += checkAuthor(initials);
		query += ") and dateOfCreating = \'" + taskDate + "\'))";

		try (Connection con = DriverManager.getConnection(url, user, password);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);) {
			int row = 0;
			int col = 0;
			while (rs.next()) {

				int val = rs.getInt(1);
				task.setValue(val, row + task.getCriterionCount(), col);
				if (col < task.getVariableCount() - 1) {
					col++;
				} else {
					row++;
					col = 0;
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void readCosts(String name, String author, Date taskDate, Task task) {
		String[] initials = author.split("\\s");
		String query = "select cost from costs"
				+ " where id_criterion in ( select id from criterions"
				+ " where id_task = (select id from tasks where name = \'"
				+ name
				+ "\' and id_author = (select id from authors where name = \'";
		query += checkAuthor(initials);
		query += ") and dateOfCreating = \'" + taskDate + "\'))";

		try (Connection con = DriverManager.getConnection(url, user, password);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);) {
			int row = 0;
			int col = 0;
			while (rs.next()) {

				int val = rs.getInt(1);
				task.setValue(val, row, col);
				if (col < task.getVariableCount() - 1) {
					col++;
				} else {
					row++;
					col = 0;
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void readSolveEconom(String name, String author, Date taskDate,
			Task task) {
		readEconom(name, author, taskDate, task);
		readSolution(name, author, taskDate, task);
	}

	private void readSolution(String name, String author, Date taskDate, Task task) {
		String[] initials = author.split("\\s");
		String query = "select resultValue from variables"
				+ " where id_task = (select id from tasks where name = \'"
				+ name
				+ "\' and id_author = (select id from authors where name = \'";
		query += checkAuthor(initials);
		query += ") and dateOfCreating = \'" + taskDate + "\')";
		
		try (Connection con = DriverManager.getConnection(url, user, password);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);) {
			int col = 0;
			while (rs.next()) {
				task.setSolutionVariable(rs.getBoolean(1), col);
				col++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean readIsSolved(String name, String author, Date taskDate) {
		String[] initials = author.split("\\s");
		String query = "select isSolved from tasks where name = \'" + name
				+ "\' and id_author = (select id from authors where name = \'";
		query += checkAuthor(initials);
		query += ") and dateOfCreating = \'" + taskDate + "\'";
		boolean isSolved = false;
		try (Connection con = DriverManager.getConnection(url, user, password);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);) {
			while (rs.next()) {
				isSolved = rs.getBoolean(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isSolved;
	}

}
