package com.maor.shop.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherData {
	private Long temp;
	private Long temp_min;
	private Long temp_max;
	private Long pressure;
	private Long humidity;
}