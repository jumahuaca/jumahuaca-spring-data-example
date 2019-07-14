package org.jumahuaca.examples.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jumahuaca.assertions.HttpResponseStatusAssert.assertResponse200;
import static org.jumahuaca.assertions.HttpResponseStatusAssert.assertResponse404;
import static org.jumahuaca.assertions.HttpResponseStatusAssert.assertResponse500;
import static org.jumahuaca.examples.controller.PathConstants.RESOURCE_VERSION;
import static org.jumahuaca.examples.controller.PathConstants.UVA_EXCHANGE_GET_ALL_PATH;
import static org.jumahuaca.examples.controller.PathConstants.UVA_EXCHANGE_GET_ONE_PARAMS;
import static org.jumahuaca.examples.controller.PathConstants.UVA_EXCHANGE_GET_ONE_PATH;
import static org.jumahuaca.examples.controller.PathConstants.UVA_EXCHANGE_POST_PATH;
import static org.jumahuaca.examples.controller.PathConstants.UVA_EXCHANGE_PUT_PATH;
import static org.jumahuaca.examples.controller.PathConstants.UVA_EXCHANGE_REMOVE_PATH;
import static org.jumahuaca.examples.controller.PathConstants.UVA_EXCHANGE_ROOT_PATH;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.jumahuaca.examples.controller.UVAExchangeController;
import org.jumahuaca.examples.entity.UVAExchange;
import org.jumahuaca.examples.repository.UvaExchangeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UVAExchangeController.class)
public class SpringExchangeRestControllerTests {

	private static final String PREFIX = RESOURCE_VERSION + UVA_EXCHANGE_ROOT_PATH;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UvaExchangeRepository uvaExchangeRepository;

	@Autowired
	private ObjectMapper objectMapper;

	private String testDay1 = "2016-03-31";

	private String testDay1YearParam = "2016";

	private String testDay1MonthParam = "03";

	private String testDay1DayParam = "31";

	private String testDay2 = "2016-02-04";

	private String testDay3 = "2016-04-08";

	private Double testRate1 = 14.05;

	private Double testRate2 = 14.06;

	private Double testRate3 = 14.14;

	@Test
	public void requestAllUVAExchangeShouldWork() throws Exception {
		List<UVAExchange> mockedResult = mockSelectAllResult();
		stubRepositoryFindAllOk(mockedResult);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(PREFIX + "/" + UVA_EXCHANGE_GET_ALL_PATH)
				.accept(MediaType.APPLICATION_JSON_UTF8)).andReturn();
		List<UVAExchange> requestResult = objectMapper.readValue(result.getResponse().getContentAsString(),
				new TypeReference<List<UVAExchange>>() {
				});
		HttpServletResponse response = result.getResponse();
		assertResponse200(response);
		assertThat(requestResult).isEqualTo(mockedResult);
	}

	@Test
	public void requestAllUVAExchangeShouldNotReturn() throws Exception {
		stubRepositoryFindAllNotFound();
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(PREFIX + "/" + UVA_EXCHANGE_GET_ALL_PATH)
				.accept(MediaType.APPLICATION_JSON_UTF8)).andReturn();
		HttpServletResponse response = result.getResponse();
		assertResponse404(response);
	}

	@Test
	public void requestAllUVAExchangeShouldFail() throws Exception {
		stubRepositoryFindAllError();
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(PREFIX + "/" + UVA_EXCHANGE_GET_ALL_PATH)
				.accept(MediaType.APPLICATION_JSON_UTF8)).andReturn();
		HttpServletResponse response = result.getResponse();
		assertResponse500(response);
	}

	@Test
	public void requestOneUVAExchangeShouldWork() throws Exception {
		UVAExchange mockedResult = mockOneUVAExchange();
		stubRepositoryFindByIdOk(mockedResult);
		MvcResult result = mockMvc
				.perform(
						MockMvcRequestBuilders
								.get(PREFIX + UVA_EXCHANGE_GET_ONE_PATH + UVA_EXCHANGE_GET_ONE_PARAMS,
										testDay1YearParam, testDay1MonthParam, testDay1DayParam)
								.accept(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();
		UVAExchange requestResult = objectMapper.readValue(result.getResponse().getContentAsString(),
				UVAExchange.class);
		HttpServletResponse response = result.getResponse();
		assertResponse200(response);
		assertThat(requestResult).isEqualTo(mockedResult);
	}

	@Test
	public void requestOneUVAExchangeShouldNotFound() throws Exception {
		UVAExchange mockedResult = mockOneUVAExchange();
		stubRepositoryFindByIdNotFound(mockedResult);
		MvcResult result = mockMvc
				.perform(
						MockMvcRequestBuilders
								.get(PREFIX + UVA_EXCHANGE_GET_ONE_PATH + UVA_EXCHANGE_GET_ONE_PARAMS,
										testDay1YearParam, testDay1MonthParam, testDay1DayParam)
								.accept(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();
		HttpServletResponse response = result.getResponse();
		assertResponse404(response);
	}

	@Test
	public void postUVAExchangeShouldWork() throws Exception {
		UVAExchange mockedResult = mockOneUVAExchange();
		stubRepositoryUpdateOk(mockedResult);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(PREFIX + UVA_EXCHANGE_POST_PATH)
				.contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(mockedResult))).andReturn();
		HttpServletResponse response = result.getResponse();
		assertResponse200(response);
	}

	@Test
	public void postUVAExchangeShouldFail() throws Exception {
		UVAExchange mockedResult = mockOneUVAExchange();
		stubRepositoryUpdateError(mockedResult);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(PREFIX + UVA_EXCHANGE_POST_PATH)
				.contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(mockedResult))).andReturn();
		HttpServletResponse response = result.getResponse();
		assertResponse500(response);
	}

	@Test
	public void putUVAExchangeShouldWork() throws Exception {
		UVAExchange mockedResult = mockOneUVAExchange();
		stubRepositoryUpdateOk(mockedResult);
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.put(PREFIX + UVA_EXCHANGE_PUT_PATH).contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_JSON_UTF8).content(objectMapper.writeValueAsString(mockedResult)))
				.andReturn();
		HttpServletResponse response = result.getResponse();
		assertResponse200(response);
	}

	@Test
	public void putUVAExchangeShouldFail() throws Exception {
		UVAExchange mockedResult = mockOneUVAExchange();
		stubRepositoryUpdateError(mockedResult);
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.put(PREFIX + UVA_EXCHANGE_PUT_PATH).contentType(MediaType.APPLICATION_JSON_UTF8)
						.accept(MediaType.APPLICATION_JSON_UTF8).content(objectMapper.writeValueAsString(mockedResult)))
				.andReturn();
		HttpServletResponse response = result.getResponse();
		assertResponse500(response);
	}

	@Test
	public void deleteUVAExchangeShouldWork() throws Exception {
		UVAExchange mockedResult = mockOneUVAExchange();
		stubRepositoryDeleteOk(mockedResult);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(PREFIX + UVA_EXCHANGE_REMOVE_PATH)
				.contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(mockedResult))).andReturn();
		HttpServletResponse response = result.getResponse();
		assertResponse200(response);
	}

	@Test
	public void deleteUVAExchangeShouldFail() throws Exception {
		UVAExchange mockedResult = mockOneUVAExchange();
		stubRepositoryDeleteError(mockedResult);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(PREFIX + UVA_EXCHANGE_REMOVE_PATH)
				.contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(mockedResult))).andReturn();
		HttpServletResponse response = result.getResponse();
		assertResponse500(response);
	}

	private List<UVAExchange> mockSelectAllResult() {
		List<UVAExchange> result = new ArrayList<UVAExchange>();
		result.add(new UVAExchange(LocalDate.parse(testDay1), BigDecimal.valueOf(testRate1)));
		result.add(new UVAExchange(LocalDate.parse(testDay2), BigDecimal.valueOf(testRate2)));
		result.add(new UVAExchange(LocalDate.parse(testDay3), BigDecimal.valueOf(testRate3)));
		return result;
	}

	private void stubRepositoryFindAllOk(List<UVAExchange> mockedResult) {
		when(uvaExchangeRepository.findAll()).thenReturn(mockedResult);
	}

	private void stubRepositoryFindAllNotFound() {
		List<UVAExchange> emptyResult = new ArrayList<UVAExchange>();
		when(uvaExchangeRepository.findAll()).thenReturn(emptyResult);
	}

	private void stubRepositoryFindAllError() {
		List<UVAExchange> emptyResult = null;
		when(uvaExchangeRepository.findAll()).thenReturn(emptyResult);
	}

	private void stubRepositoryFindByIdOk(UVAExchange mockedResult) {
		when(uvaExchangeRepository.findById(mockedResult.getDate())).thenReturn(Optional.of(mockedResult));
	}

	private void stubRepositoryFindByIdNotFound(UVAExchange mockedResult) {
		Optional<UVAExchange> nullResult = Optional.ofNullable(null);
		when(uvaExchangeRepository.findById(mockedResult.getDate())).thenReturn(nullResult);
	}

	private UVAExchange mockOneUVAExchange() {
		return new UVAExchange(LocalDate.parse(testDay1), BigDecimal.valueOf(testRate1));
	}

	private void stubRepositoryUpdateOk(UVAExchange mockedResult) {
		when(uvaExchangeRepository.save(mockedResult)).thenReturn(mockedResult);
	}

	private void stubRepositoryUpdateError(UVAExchange mockedResult) {
		when(uvaExchangeRepository.save(mockedResult)).thenThrow(RuntimeException.class);
	}

	private void stubRepositoryDeleteOk(UVAExchange mockedResult) {
		doNothing().when(uvaExchangeRepository).delete(mockedResult);
	}

	private void stubRepositoryDeleteError(UVAExchange mockedResult) {
		doThrow(RuntimeException.class).when(uvaExchangeRepository).delete(mockedResult);
	}

}
