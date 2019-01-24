package com.zsls.entity;

/**
 *@ClassName KxlhXh
 *@Description TODO
 *@Author zk102
 *@Date 2019/1/22 18:23
 *@Version 1.0
 */
public class KxlhXh {

	private String kxlh;
	private String studentNo;
	private String collegeCode;

	public String getKxlh() {
		return kxlh;
	}

	public void setKxlh(String kxlh) {
		this.kxlh = kxlh;
	}

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getCollegeCode() {
		return collegeCode;
	}

	public void setCollegeCode(String collegeCode) {
		this.collegeCode = collegeCode;
	}

	public KxlhXh() {
	}

	public KxlhXh(String kxlh, String studentNo, String collegeCode) {
		this.kxlh = kxlh;
		this.studentNo = studentNo;
		this.collegeCode = collegeCode;
	}

	@Override
	public String toString() {
			return "KxlhXh{" +
					"kxlh=" + kxlh +
					", studentNo='" + studentNo + '\'' +
					", collegeCode='" + collegeCode + '\'' +
					'}';
	}
}
