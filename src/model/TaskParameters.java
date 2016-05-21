package model;

import java.sql.Date;

public class TaskParameters {

	private boolean isEconom;
	private boolean isMax;
	private String name;
	private String authorName;
	private Date sqlDate;
	private boolean canRewrite;
	private String economMeaning;
	private boolean isSolved;
	private String note;

	public boolean isEconom() {
		return isEconom;
	}

	public void setEconom(boolean isEconom) {
		this.isEconom = isEconom;
	}

	public boolean isMax() {
		return isMax;
	}

	public void setMax(boolean isMax) {
		this.isMax = isMax;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public Date getSqlDate() {
		return sqlDate;
	}

	public void setSqlDate(Date sqlDate) {
		this.sqlDate = sqlDate;
	}

	public boolean isCanRewrite() {
		return canRewrite;
	}

	public void setCanRewrite(boolean canRewrite) {
		this.canRewrite = canRewrite;
	}

	public String getEconomMeaning() {
		return economMeaning;
	}

	public void setEconomMeaning(String economText) {
		economMeaning = economText;
	}

	public boolean isSolved() {
		return isSolved;
	}

	public void setIsSolved(boolean isTaskSolved) {
		isSolved = isTaskSolved;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNote() {
		return note;
	}

}
