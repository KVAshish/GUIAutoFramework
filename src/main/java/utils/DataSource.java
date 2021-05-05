package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import ch.qos.logback.classic.Logger;
import stepsDefinitions.NewFeatureStepDefinition;

public class DataSource {

	private final Logger logger = (Logger) LoggerFactory.getLogger(DataSource.class);
	protected InputStream input = null;
	protected Properties prop = null;
	protected Yaml yamlFile = null;
	static String testData= "";
	private static Map<String,CSVRecord> testDataRecordsMapDefault = null;
	private static CSVParser csvParser;

	/**
	 * This method is used for reading the Test data from the config file.
	 * 
	 * @param term
	 * @return
	 * @throws IOException
	 */
	public String readPropertiesFileForCMD(String term) throws IOException {
		prop = new Properties();
		FileInputStream fi = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\resources\\config.properties");
		prop.load(fi);
		if (prop.getProperty(term.toLowerCase()) == null) {
			return "";
		}else {
			return prop.getProperty(term.toLowerCase());
		}
	}
	
	/**
	 * This Method is used for reading the Test data from the yaml File.
	 * 
	 * @param filename
	 * @return
	 * @throws FileNotFoundException 
	 */
	public Map<Object, Object> getData(String filename) throws FileNotFoundException {
		yamlFile = new Yaml();
		FileInputStream yamlFilePath = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\java\\resources\\dataTemplates\\" +filename);
		System.out.println("YAML FIle Path ::" +yamlFilePath);
		System.out.println("YAML FIle Path ::" +yamlFilePath.toString());
		Map<Object, Object> map = new HashMap<Object, Object>();
		map = yamlFile.load(yamlFilePath);
		return map;
	}

	
	/**
	 * This method is for the reading the row instance from csv file
	 * 
	 * @param testDataFile
	 * @return
	 * @throws IOException
	 */
	public static Map<String, CSVRecord> getDataInstance(String testDataFile) throws IOException{
		
		if(testData.equals(testDataFile)) {
			if(testDataRecordsMapDefault != null) {
				return testDataRecordsMapDefault;
			}
		}
		
		try {
			String env = "test";
			String dataFilePath = null;
			Reader reader = null;
			
			dataFilePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + testDataFile + "_" + env + ".csv";
			
			testDataRecordsMapDefault = new HashMap<String, CSVRecord>();
			File filecheck = new File(dataFilePath);
			
			if(filecheck.exists()) {
				
				System.out.println("File exist");
				reader = new FileReader(dataFilePath);
			}else {
				String testDataFileTemp = testDataFile + "_" + env + ".csv";
				String fileName = "testdata/" + testDataFileTemp;
				File file = File.createTempFile(testDataFileTemp, ".csv");
				InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName);
				FileUtils.copyInputStreamToFile(in, file);
				reader = new FileReader(file);
				in.close();
			}
			
			BufferedReader bufferReader = new BufferedReader(reader);
			StringBuilder builder = new StringBuilder();
			String line;
			while((line = bufferReader.readLine()) != null) {
				builder.append(line.replace("~", ""));
				builder.append("\n");
			}
			
			final String csvData = builder.toString();
			csvParser = CSVParser.parse(csvData, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			//Accessing values by Header names
			for(CSVRecord csvRow : csvRecords) {
				String serviceName = csvRow.get("data_id");
				testDataRecordsMapDefault.put(serviceName, csvRow);
			}
			testData = testDataFile;
			bufferReader.close();
			reader.close();
			return testDataRecordsMapDefault;
		} catch (Exception e) {
          
			//put Logger

		}finally {
			try {
				csvParser.close();
			} catch (Exception e) {
				//put logger
			}
		}
		
		testData = testDataFile;
		
		return testDataRecordsMapDefault;
		
		
	}
	
	/**
	 * This method is for getting the Column data from Test data CSV file Row
	 * 
	 * @param data_ref
	 * @param columnNames
	 * @return
	 * @throws IOException
	 */
	public static String getData(String data_ref,String columnNames) throws IOException {
		String columnValue = DataSource.getDataInstance(data_ref.split("\\.")[0]).get(data_ref.split("\\.")[1]).get(columnNames);
		return columnValue;
	}
}
