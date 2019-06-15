package com.bms.bean;

public class Book {
	public Book() {}
	
	//图书的属性：编号（自动生成，不允许重复）、书名、出版社、作者、价格、库存量
	private String bID;
	private String bName;
	private String bPress;
	private String bAuthor;
	private double bPrice;
	private int bNum;
	public String getbID() {
		return bID;
	}
	public void setbID(String bID) {
		this.bID = bID;
	}
	public String getbName() {
		return bName;
	}
	public void setbName(String bName) {
		this.bName = bName;
	}
	public String getbPress() {
		return bPress;
	}
	public void setbPress(String bPress) {
		this.bPress = bPress;
	}
	public String getbAuthor() {
		return bAuthor;
	}
	public void setbAuthor(String bAuthor) {
		this.bAuthor = bAuthor;
	}
	public double getbPrice() {
		return bPrice;
	}
	public void setbPrice(double bPrice) {
		this.bPrice = bPrice;
	}
	public int getbNum() {
		return bNum;
	}
	public void setbNum(int bNum) {
		this.bNum = bNum;
	}
}