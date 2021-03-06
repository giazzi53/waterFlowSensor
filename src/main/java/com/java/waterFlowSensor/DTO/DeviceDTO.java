package com.java.waterFlowSensor.DTO;

import javax.validation.constraints.NotBlank;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.java.waterFlowSensor.enums.WeekDayEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "DeviceCollection")
@JsonInclude(Include.NON_NULL)
public class DeviceDTO {
	
	private String _id;
	@NotBlank
	private String description;
	@NotBlank
	private String deviceId;
	@NotBlank
	private String username;
	@NotBlank
	private String timestamp;
	@NotBlank
	private double flowRate;
	@NotBlank
	private WeekDayEnum weekDay;

}
