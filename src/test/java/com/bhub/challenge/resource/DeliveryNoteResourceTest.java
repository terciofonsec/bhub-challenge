package com.bhub.challenge.resource;


import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bhub.challenge.message.DeliveryNotePublisher;
import com.bhub.challenge.model.DeliveryNoteDocument;
import com.bhub.challenge.repository.DeliveryNoteRepository;
import com.bhub.challenge.resource.apiobjects.DeliveryNoteRequest;
import com.bhub.challenge.stub.DeliveryNoteStub;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class DeliveryNoteResourceTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;
  @MockBean
  private DeliveryNoteRepository repository;

  @MockBean
  private DeliveryNotePublisher publisher;

  @Test
  public void givenNewDeliveryNoteRequest_whenEndpointIsCalled_thenResponseShouldBe201()
      throws Exception {

    //given
    DeliveryNoteRequest deliveryNoteRequest = DeliveryNoteStub.gimmeDeliveryNoteRequest();

    //when
    mockMvc.perform(MockMvcRequestBuilders.post("/delivery-notes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(deliveryNoteRequest)))
        //then
        .andExpect(status().isCreated());
    verify(repository).save(any());
    verify(publisher).sendEvent(any());


  }

  @Test
  public void givenNewDeliveryNoteRequest_whenIsMissingRequiredFields_thenResponseShouldBe400()
      throws Exception {
    DeliveryNoteRequest deliveryNoteRequest = DeliveryNoteStub.gimmeDeliveryNoteRequestMissingRequiredFields();

    //when
    mockMvc.perform(MockMvcRequestBuilders.post("/delivery-notes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(deliveryNoteRequest)))
        //then
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.messages",
            containsInAnyOrder(
                "department - must not be blank",
                "checkoutOrderId - must not be null",
                "userId - must not be null")));

  }

  @Test
  public void givenNewDeliveryNoteRequest_whenErrorOccurToSaveOnDatabase_thenResponseShouldBe500()
      throws Exception {

    //given
    DeliveryNoteRequest deliveryNoteRequest = DeliveryNoteStub.gimmeDeliveryNoteRequest();

    when(repository.save(any())).thenThrow(new RuntimeException("Error to save on database"));

    // when
    mockMvc.perform(MockMvcRequestBuilders.post("/delivery-notes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(deliveryNoteRequest)))
        //then
        .andExpect(status().isInternalServerError());


  }

  @Test
  public void givenNewDeliveryNoteRequest_whenCalledWithAlreadyCreatedDeliveryNoteObject_thenResponseShouldBeCreated202()
      throws Exception {
    //given
    DeliveryNoteRequest deliveryNoteRequest = DeliveryNoteStub.gimmeDeliveryNoteRequest();
    when(repository.findByUserIdAndCheckoutOrderIdAndDepartment(any(), any(), any())).thenReturn(
        List.of(new DeliveryNoteDocument()));

    //when
    mockMvc.perform(MockMvcRequestBuilders.post("/delivery-notes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(deliveryNoteRequest)))
        //then
        .andExpect(status().isAccepted());
    verify(repository, times(0)).save(any());
    verify(publisher, times(0)).sendEvent(any());
  }
}
