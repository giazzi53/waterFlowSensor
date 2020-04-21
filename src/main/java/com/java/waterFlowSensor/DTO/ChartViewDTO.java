package com.java.waterFlowSensor.DTO;

import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChartViewDTO {

	@NotBlank
	private String username;
	@NotBlank
	private String title;
	@NotBlank
	private String type;
	@NotBlank
	private List<DataPointDTO> dataPoints;

}
