package com.devizones.domain.member.event;

import com.devizones.commons.event.DomainEvent;
import com.devizones.domain.member.model.Profile;
import lombok.Getter;

@Getter
public class ProfileUpdatedEvent extends DomainEvent {
    private final Profile deleteProfile;

    public ProfileUpdatedEvent(Profile deleteProfile) {
        this.deleteProfile = deleteProfile;
    }
}
