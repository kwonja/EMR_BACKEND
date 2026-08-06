package com.example.demo.examroomqueue.event;

import com.example.demo.examroomqueue.domain.ExamRoomQueueEvent;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;


@Component
public class ExamRoomQueueEventListener {

    private static final String GLOBAL_DESTINATION =
            "/event/wait-queue";

    private static final String ROOM_DESTINATION_PREFIX =
            "/event/wait-queue/examination-rooms/";

    private final SimpMessagingTemplate messagingTemplate;

    public ExamRoomQueueEventListener(
            SimpMessagingTemplate messagingTemplate
    ) {
        this.messagingTemplate = messagingTemplate;
    }

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(ExamRoomQueueEvent event) {


        messagingTemplate.convertAndSend(
                GLOBAL_DESTINATION,
                event
        );

        messagingTemplate.convertAndSend(
                ROOM_DESTINATION_PREFIX
                        + event.getExaminationRoomId(),
                event
        );
    }
}