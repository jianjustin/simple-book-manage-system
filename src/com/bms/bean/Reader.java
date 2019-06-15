package com.bms.bean;

public class Reader {
	public Reader() {}
	//编号（自动生成，不允许重复）、姓名、性别、年龄、职业、借书量、罚款金额
	
	private String rID;
	private String rName;
	private String rSex;
	private int rAge;
	private String rOccupation;
	private int rBorrowNum = 0;
	private double rFinesAmount = 0.0;
	
	public String getrID() {
		return rID;
	}
	public void setrID(String rID) {
		this.rID = rID;
	}
	public String getrName() {
		return rName;
	}
	public void setrName(String rName) {
		this.rName = rName;
	}
	public String getrSex() {
		return rSex;
	}
	public void setrSex(String rSex) {
		this.rSex = rSex;
	}
	public int getrAge() {
		return rAge;
	}
	public void setrAge(int rAge) {
		this.rAge = rAge;
	}
	public String getrOccupation() {
		return rOccupation;
	}
	public void setrOccupation(String rOccupation) {
		this.rOccupation = rOccupation;
	}
	public int getrBorrowNum() {
		return rBorrowNum;
	}
	public void setrBorrowNum(int rBorrowNum) {
		this.rBorrowNum = rBorrowNum;
	}
	public double getrFinesAmount() {
		return rFinesAmount;
	}
	public void setrFinesAmount(double rFinesAmount) {
		this.rFinesAmount = rFinesAmount;
	}
}
