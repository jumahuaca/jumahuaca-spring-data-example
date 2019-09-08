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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.jumahuaca.examples.controller.UVAExchangeController;
import org.jumahuaca.examples.entity.UVAExchange;
import org.jumahuaca.examples.repository.UvaExchangeRepository;
import org.jumahuaca.extensions.HttpWebServiceTestDoubleHelper;
import org.jumahuaca.extensions.SpringControllerCrudExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UVAExchangeController.class)
public class SpringExchangeRestControllerTests implements HttpWebServiceTestDoubleHelper<UVAExchange>{

	private static final String PREFIX = RESOURCE_VERSION + UVA_EXCHANGE_ROOT_PATH;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UvaExchangeRepository exchangeRepository;

	@Autowired
	private ObjectMapper objectMapper;
	
	@RegisterExtension
	public final SpringControllerCrudExtension<UVAExchange> extension = new SpringControllerCrudExtension<UVAExchange>();

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
		MvcResult result  = extension.requestAllShouldWork(this, mockMvc, PREFIX + "/" + UVA_EXCHANGE_GET_ALL_PATH);
		List<UVAExchange> requestResult = objectMapper.readValue(result.getResponse().getContentAsString(),
		new TypeReference<List<UVAExchange>>() {
		});
		HttpServletResponse response = result.getResponse();
		assertResponse200(response);
		assertThat(requestResult).isEqualTo(mockedResult);
	}
	
	@Test
	public void requestAllUVAExchangeShouldNotReturn() throws Exception {
		MvcResult result  = extension.requestAllShouldNotReturn(this, mockMvc, PREFIX + "/" + UVA_EXCHANGE_GET_ALL_PATH);
		HttpServletResponse response = result.getResponse();
		assertResponse404(response);
	}
	
	@Test
	public void requestAllUVAExchangeShouldFail() throws Exception {
		MvcResult result  = extension.requestAllShouldFail(this, mockMvc, PREFIX + "/" + UVA_EXCHANGE_GET_ALL_PATH);
		HttpServletResponse response = result.getResponse();
		assertResponse500(response);
	}
	
	@Test
	public void requestOneUVAExchangeShouldWork() throws Exception {
		UVAExchange mockedResult = mockOne();
		MvcResult result  = extension.requestOneShouldWork(this, mockMvc, PREFIX + UVA_EXCHANGE_GET_ONE_PATH + UVA_EXCHANGE_GET_ONE_PARAMS,
				testDay1YearParam, testDay1MonthParam, testDay1DayParam);
				
		UVAExchange requestResult = objectMapper.readValue(result.getResponse().getContentAsString(),
				UVAExchange.class);
		HttpServletResponse response = result.getResponse();
		assertResponse200(response);
		assertThat(requestResult).isEqualTo(mockedResult);
		
	}

	@Test
	public void requestOneUVAExchangeShouldNotFound() throws Exception {
		MvcResult result  = extension.requestOneShouldNotFound(this, mockMvc, PREFIX + UVA_EXCHANGE_GET_ONE_PATH + UVA_EXCHANGE_GET_ONE_PARAMS,
				testDay1YearParam, testDay1MonthParam, testDay1DayParam);
		HttpServletResponse response = result.getResponse();
		assertResponse404(response);
	}
	
	@Test
	public void postUVAExchangeShouldWork() throws Exception {
		UVAExchange mockedResult = mockOne();
		MvcResult result = extension.postShouldWork(this, mockMvc, PREFIX + UVA_EXCHANGE_POST_PATH, objectMapper.writeValueAsString(mockedResult));
		HttpServletResponse response = result.getResponse();
		assertResponse200(response);
	}
	
	@Test
	public void postShouldFail() throws Exception {
		UVAExchange mockedResult = mockOne();
		MvcResult result = extension.postShouldFail(this, mockMvc, PREFIX + UVA_EXCHANGE_POST_PATH, objectMapper.writeValueAsString(mockedResult));
		HttpServletResponse response = result.getResponse();
		assertResponse500(response);
	}
	
	@Test
	public void putUVAExchangeShouldWork() throws Exception {
		UVAExchange mockedResult = mockOne();
		MvcResult result = extension.putShouldWork(this, mockMvc, PREFIX + UVA_EXCHANGE_PUT_PATH, objectMapper.writeValueAsString(mockedResult));
		HttpServletResponse response = result.getResponse();
		assertResponse200(response);
	}
	
	@Test
	public void putUVAExchangeShouldFail() throws Exception {
		UVAExchange mockedResult = mockOne();
		MvcResult result = extension.putShouldFail(this, mockMvc, PREFIX + UVA_EXCHANGE_PUT_PATH, objectMapper.writeValueAsString(mockedResult));
		HttpServletResponse response = result.getResponse();
		assertResponse500(response);
	}
	
	@Test
	public void deleteUVAExchangeShouldWork() throws Exception {
		UVAExchange mockedResult = mockOne();
		MvcResult result = extension.putShouldWork(this, mockMvc, PREFIX + UVA_EXCHANGE_REMOVE_PATH, objectMapper.writeValueAsString(mockedResult));
		HttpServletResponse response = result.getResponse();
		assertResponse200(response);
	}
	
	@Test
	public void deleteUVAExchangeShouldFail() throws Exception {
		UVAExchange mockedResult = mockOne();
		MvcResult result = extension.putShouldFail(this, mockMvc, PREFIX + UVA_EXCHANGE_REMOVE_PATH, objectMapper.writeValueAsString(mockedResult));
		HttpServletResponse response = result.getResponse();
		assertResponse500(response);
	}

	@Override
	public List<UVAExchange> mockSelectAllResult() {
		List<UVAExchange> result = new ArrayList<UVAExchange>();
		result.add(new UVAExchange(LocalDate.parse(testDay1), BigDecimal.valueOf(testRate1)));
		result.add(new UVAExchange(LocalDate.parse(testDay2), BigDecimal.valueOf(testRate2)));
		result.add(new UVAExchange(LocalDate.parse(testDay3), BigDecimal.valueOf(testRate3)));
		return result;
	}

	@Override
	public void mockRepositoryFindAllOk(List<UVAExchange> mockedResult) {
		when(exchangeRepository.findAll()).thenReturn(mockedResult);
	}

	@Override
	public void mockRepositoryFindAllNotFound() {
		List<UVAExchange> emptyResult = new ArrayList<UVAExchange>();
		when(exchangeRepository.findAll()).thenReturn(emptyResult);
	}

	@Override
	public void mockRepositoryFindAllError() {
		List<UVAExchange> emptyResult = null;
		when(exchangeRepository.findAll()).thenReturn(emptyResult);
	}

	@Override
	public void mockRepositoryFindByIdOk(UVAExchange mockedResult) {
		when(exchangeRepository.findById(mockedResult.getDate())).thenReturn(Optional.of(mockedResult));
	}

	@Override
	public void mockRepositoryFindByIdNotFound(UVAExchange mockedResult) {
		Optional<UVAExchange> nullResult = Optional.ofNullable(null);
		when(exchangeRepository.findById(mockedResult.getDate())).thenReturn(nullResult);
	}

	@Override
	public UVAExchange mockOne() {
		return new UVAExchange(LocalDate.parse(testDay1), BigDecimal.valueOf(testRate1));
	}

	@Override
	public void mockRepositoryUpdateOk(UVAExchange mockedResult) {
		when(exchangeRepository.save(mockedResult)).thenReturn(mockedResult);
	}

	@Override
	public void mockRepositoryUpdateError(UVAExchange mockedResult) {
		when(exchangeRepository.save(mockedResult)).thenThrow(RuntimeException.class);
	}

	@Override
	public void mockRepositoryDeleteOk(UVAExchange mockedResult) {
		doNothing().when(exchangeRepository).delete(mockedResult);
	}

	@Override
	public void mockRepositoryDeleteError(UVAExchange mockedResult) {
		doThrow(RuntimeException.class).when(exchangeRepository).delete(mockedResult);
	}

	@Override
	public void mockRepositoryFindByIdError(UVAExchange mockedResult) {
		throw new UnsupportedOperationException();		
	}

	@Override
	public void mockRepositoryCreateOk(UVAExchange mockedResult) {
		mockRepositoryUpdateOk(mockedResult);
	}

	@Override
	public void mockRepositoryCreateServerError(UVAExchange mockedResult) {
		mockRepositoryUpdateError(mockedResult);
		
	}

	@Override
	public void mockRepositoryCreateUknownError(UVAExchange mockedResult) {
		mockRepositoryUpdateUknownError(mockedResult);
	}

	@Override
	public void mockRepositoryUpdateUknownError(UVAExchange mockedResult) {
		mockRepositoryUpdateError(mockedResult);
		
	}

	@Override
	public void mockRepositoryDeleteUknownError(UVAExchange mockedResult) {
		mockRepositoryDeleteError(mockedResult);
		
	}

	@Override
	public void mockRepositoryDeleteNotFoundError(UVAExchange mockedResult) {
		throw new UnsupportedOperationException();		
	}

}
