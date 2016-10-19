/**
 * 
 */
package com.tys.dto.spi.req;

import javax.validation.constraints.Pattern;

/**
 * @author Administrator
 *
 */
public class ReqSettingClassModeDTO {

	private Integer onoff;

	@Pattern(regexp = "\\d{2}-\\d{2},\\d{2}-\\d{2}") // 格式HH:mm,HH:mm
	private String morning1;
	@Pattern(regexp = "\\d{2}-\\d{2},\\d{2}-\\d{2}") // 格式HH:mm,HH:mm
	private String morning2;
	@Pattern(regexp = "\\d{2}-\\d{2},\\d{2}-\\d{2}") // 格式HH:mm,HH:mm
	private String morning3;
	@Pattern(regexp = "\\d{2}-\\d{2},\\d{2}-\\d{2}") // 格式HH:mm,HH:mm
	private String morning4;
	@Pattern(regexp = "\\d{2}-\\d{2},\\d{2}-\\d{2}") // 格式HH:mm,HH:mm
	private String morning5;
	@Pattern(regexp = "\\d{2}-\\d{2},\\d{2}-\\d{2}") // 格式HH:mm,HH:mm
	private String morning6;
	@Pattern(regexp = "\\d{2}-\\d{2},\\d{2}-\\d{2}") // 格式HH:mm,HH:mm
	private String morning7;

	@Pattern(regexp = "\\d{2}-\\d{2},\\d{2}-\\d{2}") // 格式HH:mm,HH:mm
	private String afternoon1;
	@Pattern(regexp = "\\d{2}-\\d{2},\\d{2}-\\d{2}") // 格式HH:mm,HH:mm
	private String afternoon2;
	@Pattern(regexp = "\\d{2}-\\d{2},\\d{2}-\\d{2}") // 格式HH:mm,HH:mm
	private String afternoon3;
	@Pattern(regexp = "\\d{2}-\\d{2},\\d{2}-\\d{2}") // 格式HH:mm,HH:mm
	private String afternoon4;
	@Pattern(regexp = "\\d{2}-\\d{2},\\d{2}-\\d{2}") // 格式HH:mm,HH:mm
	private String afternoon5;
	@Pattern(regexp = "\\d{2}-\\d{2},\\d{2}-\\d{2}") // 格式HH:mm,HH:mm
	private String afternoon6;
	@Pattern(regexp = "\\d{2}-\\d{2},\\d{2}-\\d{2}") // 格式HH:mm,HH:mm
	private String afternoon7;

	@Pattern(regexp = "\\d{2}-\\d{2},\\d{2}-\\d{2}") // 格式HH:mm,HH:mm
	private String night1;
	@Pattern(regexp = "\\d{2}-\\d{2},\\d{2}-\\d{2}") // 格式HH:mm,HH:mm
	private String night2;
	@Pattern(regexp = "\\d{2}-\\d{2},\\d{2}-\\d{2}") // 格式HH:mm,HH:mm
	private String night3;
	@Pattern(regexp = "\\d{2}-\\d{2},\\d{2}-\\d{2}") // 格式HH:mm,HH:mm
	private String night4;
	@Pattern(regexp = "\\d{2}-\\d{2},\\d{2}-\\d{2}") // 格式HH:mm,HH:mm
	private String night5;
	@Pattern(regexp = "\\d{2}-\\d{2},\\d{2}-\\d{2}") // 格式HH:mm,HH:mm
	private String night6;
	@Pattern(regexp = "\\d{2}-\\d{2},\\d{2}-\\d{2}") // 格式HH:mm,HH:mm
	private String night7;

	public Integer getOnoff() {
		return onoff;
	}

	public void setOnoff(Integer onoff) {
		this.onoff = onoff;
	}

	public String getMorning1() {
		return morning1;
	}

	public void setMorning1(String morning1) {
		this.morning1 = morning1;
	}

	public String getMorning2() {
		return morning2;
	}

	public void setMorning2(String morning2) {
		this.morning2 = morning2;
	}

	public String getMorning3() {
		return morning3;
	}

	public void setMorning3(String morning3) {
		this.morning3 = morning3;
	}

	public String getMorning4() {
		return morning4;
	}

	public void setMorning4(String morning4) {
		this.morning4 = morning4;
	}

	public String getMorning5() {
		return morning5;
	}

	public void setMorning5(String morning5) {
		this.morning5 = morning5;
	}

	public String getMorning6() {
		return morning6;
	}

	public void setMorning6(String morning6) {
		this.morning6 = morning6;
	}

	public String getMorning7() {
		return morning7;
	}

	public void setMorning7(String morning7) {
		this.morning7 = morning7;
	}

	public String getAfternoon1() {
		return afternoon1;
	}

	public void setAfternoon1(String afternoon1) {
		this.afternoon1 = afternoon1;
	}

	public String getAfternoon2() {
		return afternoon2;
	}

	public void setAfternoon2(String afternoon2) {
		this.afternoon2 = afternoon2;
	}

	public String getAfternoon3() {
		return afternoon3;
	}

	public void setAfternoon3(String afternoon3) {
		this.afternoon3 = afternoon3;
	}

	public String getAfternoon4() {
		return afternoon4;
	}

	public void setAfternoon4(String afternoon4) {
		this.afternoon4 = afternoon4;
	}

	public String getAfternoon5() {
		return afternoon5;
	}

	public void setAfternoon5(String afternoon5) {
		this.afternoon5 = afternoon5;
	}

	public String getAfternoon6() {
		return afternoon6;
	}

	public void setAfternoon6(String afternoon6) {
		this.afternoon6 = afternoon6;
	}

	public String getAfternoon7() {
		return afternoon7;
	}

	public void setAfternoon7(String afternoon7) {
		this.afternoon7 = afternoon7;
	}

	public String getNight1() {
		return night1;
	}

	public void setNight1(String night1) {
		this.night1 = night1;
	}

	public String getNight2() {
		return night2;
	}

	public void setNight2(String night2) {
		this.night2 = night2;
	}

	public String getNight3() {
		return night3;
	}

	public void setNight3(String night3) {
		this.night3 = night3;
	}

	public String getNight4() {
		return night4;
	}

	public void setNight4(String night4) {
		this.night4 = night4;
	}

	public String getNight5() {
		return night5;
	}

	public void setNight5(String night5) {
		this.night5 = night5;
	}

	public String getNight6() {
		return night6;
	}

	public void setNight6(String night6) {
		this.night6 = night6;
	}

	public String getNight7() {
		return night7;
	}

	public void setNight7(String night7) {
		this.night7 = night7;
	}

}
