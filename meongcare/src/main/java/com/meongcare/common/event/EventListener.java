package com.meongcare.common.event;

import com.meongcare.infra.image.ImageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class EventListener {

    private final ImageHandler imageHandler;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void deleteImage(String imageURL) {
        imageHandler.deleteImage(imageURL);
    }
}
