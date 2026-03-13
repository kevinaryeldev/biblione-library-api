package com.biblione.library_api.kafka.producer;

import com.biblione.library_api.kafka.event.KafkaEvent;
import com.biblione.library_api.kafka.event.CopyEventData;
import com.biblione.library_api.kafka.event.FineEventData;
import com.biblione.library_api.kafka.event.LoanEventData;
import com.biblione.library_api.kafka.event.ReservationEventData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LibraryEventProducer {

    private final KafkaTemplate<String, KafkaEvent<?>> kafkaTemplate;

    private static final String LOAN_CREATED       = "loan.created";
    private static final String LOAN_RETURNED      = "loan.returned";
    private static final String LOAN_OVERDUE       = "loan.overdue";
    private static final String LOAN_RENEWED       = "loan.renewed";
    private static final String RESERVATION_CREATED = "reservation.created";
    private static final String RESERVATION_READY  = "reservation.ready";
    private static final String RESERVATION_EXPIRED = "reservation.expired";
    private static final String FINE_CREATED       = "fine.created";
    private static final String FINE_PAID          = "fine.paid";
    private static final String COPY_ADDED         = "copy.added";
    private static final String COPY_DISCARDED     = "copy.discarded";

    public void publishLoanCreated(LoanEventData data) {
        publish(LOAN_CREATED, data);
    }

    public void publishLoanReturned(LoanEventData data) {
        publish(LOAN_RETURNED, data);
    }

    public void publishLoanOverdue(LoanEventData data) {
        publish(LOAN_OVERDUE, data);
    }

    public void publishLoanRenewed(LoanEventData data) {
        publish(LOAN_RENEWED, data);
    }

    public void publishReservationCreated(ReservationEventData data) {
        publish(RESERVATION_CREATED, data);
    }

    public void publishReservationReady(ReservationEventData data) {
        publish(RESERVATION_READY, data);
    }

    public void publishReservationExpired(ReservationEventData data) {
        publish(RESERVATION_EXPIRED, data);
    }

    public void publishFineCreated(FineEventData data) {
        publish(FINE_CREATED, data);
    }

    public void publishFinePaid(FineEventData data) {
        publish(FINE_PAID, data);
    }

    public void publishCopyAdded(CopyEventData data) {
        publish(COPY_ADDED, data);
    }

    public void publishCopyDiscarded(CopyEventData data) {
        publish(COPY_DISCARDED, data);
    }

    private void publish(String topic, Object data) {
        KafkaEvent<?> event = KafkaEvent.of(topic, data);
        kafkaTemplate.send(topic, event.getMeta().getMessageId().toString(), event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to publish event to topic {}: {}", topic, ex.getMessage());
                    } else {
                        log.debug("Event published to topic {}: messageId={}",
                                topic, event.getMeta().getMessageId());
                    }
                });
    }
}