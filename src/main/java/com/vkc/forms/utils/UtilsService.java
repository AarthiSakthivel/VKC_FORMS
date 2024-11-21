package com.vkc.forms.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Service
public class UtilsService {

//	@Value("${app.invoice.path}")
//	public String InvoicePath;

	@Value("${app.url.port}")
	public String port;

	@Value("${app.url.username}")
	public String apiUserName;

	@Value("${app.url.password}")
	public String apiPassword;
//
	@Value("${spring.datasource.username}")
	public String databaseUsername;

	@Value("${spring.datasource.password}")
	public String databasePassword;

	@Value("${spring.datasource.url}")
	public String databaseConnectionUrl;

	@Description(value = "Convert XML to Json Format !!")
	public JsonNode XmlToJsonConversion(String data) throws JsonMappingException, JsonProcessingException {
		JSONObject json = XML.toJSONObject(data);
		String jsonString2 = json.toString(4);

		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(jsonString2);
		JsonNode entryNodeArray = rootNode.path("feed").path("entry"); // .path("feed")
		return entryNodeArray;
	}

	@Description(value = "Convert XML to Json Format !!")
	public JsonNode XmlToJsonConversionWhenUsingFilter(String data)
			throws JsonMappingException, JsonProcessingException {
		JsonNode entryNodeArray = null;
		;
		try {
			JSONObject json = XML.toJSONObject(data);
			String jsonString2 = json.toString(4);

			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(jsonString2);
			entryNodeArray = rootNode.path("feed").path("entry"); // .path("feed")
			return entryNodeArray;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("API Error");
		}
		return entryNodeArray;
	}

	@Description(value = "Convert XML to Json Format !!")
	public JsonNode XmlToJsonConversionTestContent(String data) throws JsonMappingException, JsonProcessingException {
		JSONObject json = XML.toJSONObject(data);
		String jsonString2 = json.toString(4);

		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(jsonString2);
		JsonNode entryNodeArray = rootNode.path("feed").path("entry"); // .path("feed")
		return entryNodeArray;
	}

//	@Description(value = "Log to Save the Entry Request of the API Call")
//	public AltrocksLog log(HttpServletRequest request, String requestTime, String requestMethod, String IntegrationType,
//			String requestBody, String responseCode, String remarks, String responseTime) {
//		AltrocksLog log = AltrocksLog.builder().integrationType(IntegrationType).method(requestMethod)
//				.requestAddress(request.getLocalAddr()).requestBody(requestBody).responseCode(responseCode)
//				.requestEndpoint(request.getRequestURI()).remarks(remarks).build();
//		return altrockslog.save(log);
//	}
//
//	@Description(value = "Log to record/update the entry response and timestamp of the API Call")
//	public String updateLog(AltrocksLog updatedLog) {
//		Optional<AltrocksLog> logFromDb = altrockslog.findById(updatedLog.getId());
//
//		if (logFromDb.isPresent()) {
//			AltrocksLog savedLog = logFromDb.get();
//			savedLog.setResponseCode(updatedLog.getResponseCode());
//			savedLog.setRequestTime(updatedLog.getRequestTime());
//			savedLog.setResponseBody(updatedLog.getResponseBody());
//			AltrocksLog UpdatedLog = altrockslog.save(savedLog);
//			return UpdatedLog.getId().toString();
//		}
//		return null;
//	}

	public String ConvertObjectToJsonFormat(String requestBody) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		HttpHeaders headers = new HttpHeaders();

		HttpEntity<String> postData = new HttpEntity(mapper.writeValueAsString(requestBody), headers);
		return postData.getBody();
	}

	public HttpResponse<String> ApiCall(String apiUrl, String username, String password) throws UnirestException {
		HttpResponse<String> response = Unirest.get(apiUrl).basicAuth(username, password).asString();
		return response;
	}

	private static String getBase64Credentials(String username, String password) {
		String credentials = username + ":" + password;
		return java.util.Base64.getEncoder().encodeToString(credentials.getBytes());
	}

	public String AppendPlusOneDayToDate(String dateStr) {
		// String dateString = "2023-09-21";

		// Parse the original string to a LocalDate
		LocalDate localDate = LocalDate.parse(dateStr);

		// Add one day to the LocalDate
		LocalDate nextDay = localDate.plusDays(1);

		// Convert the LocalDate to a Date
		Date date = Date.from(nextDay.atStartOfDay(ZoneId.systemDefault()).toInstant());

		// Format the resulting date as a string (optional)
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// String formattedDate = sdf.format(date);

		return sdf.format(date);
	}

	public String DateConversionToUIformat(String date) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = dateFormat.parse(date);

		// Format the date to the target format "dd-MM-yyyy"
		SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MM-yyyy");
		String outputDateStr = targetFormat.format(startDate);
		return outputDateStr;
	}

	@Description(value = "Date Conversion Remove T ")
	public String DateConversionToUINewformat(String date) {

		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
		String splitPostingDate = date.split("T")[0];
		// Posting date validation
		String dateStr = null;
		try {
			Date inputDate = inputFormat.parse(splitPostingDate);
			dateStr = outputFormat.format(inputDate);
		} catch (Exception e2) {
			// TODO: handle exception
			e2.printStackTrace();
		}
		return dateStr;
	}

//	
//	public List GetAPIDetailsInJsonFormat(String DAO, String url, String username, String password) throws JsonMappingException, JsonProcessingException, UnirestException {
//		
//	HttpResponse<String> response = ApiCall(APIConstants.PaymentAdvice_z.PA_Header, APIConstants.CreditNoteUserName, APIConstants.CreditNotePassword);
//	ObjectMapper mapper = new ObjectMapper();
//    JsonNode entryNodeArray = XmlToJsonConversion(response.getBody().toString());
//    
//    List<"+DAO+"> data = null;
//    
//    if (entryNodeArray.toString() !=null || !"".equals(entryNodeArray.toString())) {
//    	List<PaymentAdviceHeaderDAO> entryNodes = mapper.reader().forType(mapper.getTypeFactory().constructCollectionType(List.class, DAO.class)).readValue(entryNodeArray.toString());
//			data = entryNodes.stream().map( e -> e.getContent().getProperties()).collect(Collectors.toList());
//	}
//	return data;
//	
//	}

	// =============================================================================================================//
	String string;
	String st1[] = { "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", };
	String st2[] = { "hundred", "thousand", "lakh", "crore" };
	String st3[] = { "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen",
			"Ninteen", };
	String st4[] = { "Twenty", "Thirty", "Fourty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninty" };

	public String converbig(BigDecimal valu) {

		String AmountToWords = null;

		int value = Math.abs(Integer.valueOf(valu.intValue()));
		String wrd = convert(value);

		try {
			long integerPart = valu.longValue();
			int fractionalPart = valu.remainder(BigDecimal.ONE).movePointRight(2).abs().intValue();
			if (fractionalPart > 0) {
				AmountToWords = convert((int) integerPart).toUpperCase() + " AND " +" PAISA "
						+ convert(fractionalPart).toUpperCase() + " ONLY ";
			} else {
				AmountToWords = convert((int) integerPart).toUpperCase() + "ONLY";
			}
			// System.out.println(convert((int) integerPart) + " and " +
			// convert(fractionalPart) + " paise only") ;
		} catch (Exception e) {
			System.out.println("Amount to Words Conversion Error :" + e.getMessage());
			AmountToWords = "" + wrd.toUpperCase() + "" + " ONLY ";
		}
		// System.out.println(AmountToWords);
		return AmountToWords;
	}

	public String convert(int number) {
		int n = 1;
		int word;
		string = "";
		while (number != 0) {
			switch (n) {
			case 1:
				word = number % 100;
				pass(word);
				if (number > 100 && number % 100 != 0) {
					show("and ");
				}
				number /= 100;
				break;

			case 2:
				word = number % 10;
				if (word != 0) {
					show(" ");
					show(st2[0]);
					show(" ");
					pass(word);
				}
				number /= 10;
				break;

			case 3:
				word = number % 100;
				if (word != 0) {
					show(" ");
					show(st2[1]);
					show(" ");
					pass(word);
				}
				number /= 100;
				break;

			case 4:
				word = number % 100;
				if (word != 0) {
					show(" ");
					show(st2[2]);
					show(" ");
					pass(word);
				}
				number /= 100;
				break;

			case 5:
				word = number % 100;
				if (word != 0) {
					show(" ");
					show(st2[3]);
					show(" ");
					pass(word);
				}
				number /= 100;
				break;

			}
			n++;
		}
		return string;
	}

	public void pass(int number) {
		int word, q;
		if (number < 10) {
			show(st1[number]);
		}
		if (number > 9 && number < 20) {
			show(st3[number - 10]);
		}
		if (number > 19) {
			word = number % 10;
			if (word == 0) {
				q = number / 10;
				show(st4[q - 2]);
			} else {
				q = number / 10;
				show(st1[word]);
				show(" ");
				show(st4[q - 2]);
			}
		}
	}

	public void show(String s) {
		String st;
		st = string;
		string = s;
		string += st;
	}

	public Date dateConversionInDateDataType(String StringDateFormat) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			// Parse the string to obtain a java.util.Date object
			date = dateFormat.parse(StringDateFormat);

			// Print the result
			// System.out.println(date);
		} catch (ParseException e) {
			// Handle parsing exception if needed
			e.printStackTrace();
		}

		return date;
	}

	public String ConvertDateFromJsonNodeFormatToString(String jsonNode) {
		LocalDateTime dateTime = LocalDateTime.parse(jsonNode.replace("\"", ""), DateTimeFormatter.ISO_DATE_TIME);
		// Format the LocalDateTime to the desired format
		// String formattedDate =
		// dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

		return dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
	}

	public String GetCurrencyDetails(String currency) {

		// Create a HashMap
		HashMap<String, String> currencyMap = new HashMap<>();

		// Add currency mappings to the HashMap
		currencyMap.put("GBP", "POUND");
		currencyMap.put("EUR", "EUROS");
		currencyMap.put("AUD", "AUSTRALIAN DOLLARS");
		currencyMap.put("INR", "RUPEES");
		currencyMap.put("USD", "DOLLARS");

		String currencyValue = "";
		if (currencyMap.containsKey(currency)) {
			currencyValue = currencyMap.get(currency);
		}
		return currencyValue;
	}

	public String DateConversionToDisplayFormat(String dateStr) {
		String formattedDate = null;
		SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
		SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");

		try {
			Date date = inputFormat.parse(dateStr);
			formattedDate = outputFormat.format(date);
			System.out.println(formattedDate); // Output: 28-05-2024
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return formattedDate;
	}
	
	
	public String DateConversionToDisplayFormatchange(String dateStr) {
	    String formattedDate = null;
	    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	    SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
	    
	    try {
	        Date date = inputFormat.parse(dateStr);
	        formattedDate = outputFormat.format(date);
	        System.out.println(formattedDate); // Output: 05-08-2024
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	    return formattedDate;
	}
	
	
	//***********************Tax BreakUp Header Name***********************
	public String TaxBreakUpName(String taxCode) {
		Set<String> sgstCgstCodes = new HashSet<>();
		sgstCgstCodes.add("I0");
		sgstCgstCodes.add("I1");
		sgstCgstCodes.add("I2");
		sgstCgstCodes.add("I3");
		sgstCgstCodes.add("I4");
		Set<String> igstCodes = new HashSet<>();
		igstCodes.add("I5");
		igstCodes.add("I6");
		igstCodes.add("I7");
		igstCodes.add("I8");
		igstCodes.add("I9");
		Set<String> rcmD_SgstCgstCodes = new HashSet<>();
		rcmD_SgstCgstCodes.add("R0");
		rcmD_SgstCgstCodes.add("R1");
		rcmD_SgstCgstCodes.add("R2");
		rcmD_SgstCgstCodes.add("R3");
		rcmD_SgstCgstCodes.add("R4");
		Set<String> rcmD_IgstCodes = new HashSet<>();
		rcmD_IgstCodes.add("R5");
		rcmD_IgstCodes.add("R6");
		rcmD_IgstCodes.add("R7");
		rcmD_IgstCodes.add("R8");
		rcmD_IgstCodes.add("R9");
		Set<String> op_SgstCgstCodes = new HashSet<>();
		op_SgstCgstCodes.add("O0");
		op_SgstCgstCodes.add("O1");
		op_SgstCgstCodes.add("O2");
		op_SgstCgstCodes.add("O3");
		op_SgstCgstCodes.add("O4");
		Set<String> op_IgstCodes = new HashSet<>();
		op_IgstCodes.add("O5");
		op_IgstCodes.add("O6");
		op_IgstCodes.add("O7");
		op_IgstCodes.add("O8");
		op_IgstCodes.add("O9");
		
		 String result;
		if (sgstCgstCodes.contains(taxCode)) {
		    result = "GST@";
		} else if (igstCodes.contains(taxCode)) {
			 result = "IGST@";
		} else if (rcmD_SgstCgstCodes.contains(taxCode)) {
			 result = "RGST@";
		} else if (rcmD_IgstCodes.contains(taxCode)) {
			  result = "RIGST@";
		} else if (op_SgstCgstCodes.contains(taxCode)) {
			  result = "GST@";
		} else if (op_IgstCodes.contains(taxCode)) {
			  result = "IGST@";
		} else {
			 result = "Unknown tax code";
		}
		
		return result;
	}
	


}
