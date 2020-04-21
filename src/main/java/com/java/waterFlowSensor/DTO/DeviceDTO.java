package com.java.waterFlowSensor.DTO;

import javax.validation.constraints.NotBlank;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Document(collection = "DeviceCollection")
public class DeviceDTO {
	
	private String _id;
	@NotBlank
	private String title;
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

}
