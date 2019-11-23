package maven.arduino.waterFlowSensor.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import maven.arduino.waterFlowSensor.date.DateAndTime;
import maven.arduino.waterFlowSensor.domain.WaterFlowSensorDomain;
import maven.arduino.waterFlowSensor.mongoDB.MongoDBConnection;

@RestController
public class WaterFlowSensorController {

	@Value("${WATERFLOW_URL}")
	private String waterFlowURL;
			
	@Value("${USER_URL}")
	private String userURL;

	@Value("${DEVICEID_URL}")
	private String deviceIdURL;

	@Value("${DESCRIPTION_URL}")
	private String descriptionURL;

	@Autowired
	private WaterFlowSensorDomain domain;

	@Autowired
	private MongoDBConnection mongo;

	@Autowired
	private RestTemplate restTemplate;

	private final Logger LOGGER = LoggerFactory.getLogger(WaterFlowSensorController.class);

	@GetMapping(value = "/getData")
	public void getData() {
		this.mongo.openConnection();

		double flowRate = Double.parseDouble(sendGETRequest(waterFlowURL));
		this.domain.setFlowRate(flowRate);

		String userId = sendGETRequest(userURL);
		this.domain.setUser(userId);

		String deviceId = sendGETRequest(deviceIdURL);
		this.domain.setDeviceId(deviceId);

		String description = sendGETRequest(descriptionURL);
		this.domain.setDescription(description);

		DateAndTime time = new DateAndTime();
		String timestamp = time.getTimestamp();
		this.domain.setTimestamp(timestamp);

		try {
			LOGGER.info("!!! Inserindo " + this.domain.toString() + " no MongoDB !!!!");
			this.mongo.store(this.domain);
		} catch (Exception e) {
			LOGGER.error("Ocorreu um erro ao inserir no MongoDB", e);
		}

		this.mongo.closeConnection();
	}

	private String sendGETRequest(String URL) {
		String responseString = null;
		
		try {
			responseString = restTemplate.getForObject(URL, String.class);

			if (responseString.contains("[") && responseString.contains("]")) { // removendo caracteres [ e ]
				responseString = responseString.replace("[", "").replace("]", "");
			}

			if (responseString.contains("\"")) { // removendo aspas, pois o Mongo ja as insere, ficando duplicado
				responseString = responseString.replace("\"", "");
			}
			
		} catch (Exception e) {
			LOGGER.error("Ocorreu um erro ao mandar a requisição GET");
		}
		
		return responseString;
	}
}