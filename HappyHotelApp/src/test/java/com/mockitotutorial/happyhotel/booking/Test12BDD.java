package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.time.LocalDate;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class Test12BDD {

	@InjectMocks
	private BookingService bookingService;

	@Mock
	private PaymentService paymentServiceMock;

	@Mock
	private RoomService roomServiceMock;

	@Spy
	private BookingDAO bookingDAOMock;

	@Mock
	private MailSender mailSenderMock;

	@Captor
	private ArgumentCaptor<Double> doubleCaptor;

	@Test
	void should_CountAvailiblePlaces_When_CalledMultipleTimes() {
		// given
		given(this.roomServiceMock.getAvailableRooms())
			.willReturn(Collections.singletonList(new Room("Room 1", 5)))
			.willReturn(Collections.emptyList());
		
		int expectedFirstCall = 5;
		int expectedSecondCall = 0;
		// when
		int actualFirst = bookingService.getAvailablePlaceCount();
		int actualSecond = bookingService.getAvailablePlaceCount();
		
		// then
		assertAll(
				() -> assertEquals(expectedFirstCall, actualFirst),
				() -> assertEquals(expectedSecondCall, actualSecond));
	}

	@Test
	void should_InvokePayment_When_Prepaid() {
		// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05),
				2, true);

		// when
		bookingService.makeBooking(bookingRequest);

		// then
		then(paymentServiceMock).should(times(1)).pay(bookingRequest, 400.0);
		verifyNoMoreInteractions(paymentServiceMock);

	}

}
