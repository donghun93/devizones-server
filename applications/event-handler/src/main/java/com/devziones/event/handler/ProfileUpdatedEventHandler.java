package com.devziones.event.handler;

import com.devizones.application.member.port.MemberProfileImagePort;
import com.devizones.domain.member.event.ProfileUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class ProfileUpdatedEventHandler {

    private final MemberProfileImagePort memberProfileImagePort;


    @Async(value = "removeImageTaskExecutor")
    @TransactionalEventListener(
            value = ProfileUpdatedEvent.class,
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(ProfileUpdatedEvent event) {
        String defaultFileName = event.getDeleteProfile().getFileName();

        if (!StringUtils.hasText(defaultFileName) || defaultFileName.contains("default")) {
            return;
        }

        if (memberProfileImagePort.existImage(defaultFileName)) {
            memberProfileImagePort.remove(event.getDeleteProfile().getFileName());
        }
    }
}
