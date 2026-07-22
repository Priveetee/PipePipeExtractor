package org.schabi.newpipe.extractor.services.youtube.extractors;

import com.grack.nanojson.JsonObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.schabi.newpipe.extractor.ServiceList;
import org.schabi.newpipe.extractor.exceptions.AgeRestrictedContentException;
import org.schabi.newpipe.extractor.exceptions.AntiBotException;
import org.schabi.newpipe.extractor.exceptions.YoutubeSessionRejectedException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class YoutubeStreamExtractorSessionRejectionTest {

    @AfterEach
    void clearCredentials() {
        ServiceList.YouTube.setTokens(null);
    }

    @Test
    void anonymousLoginRequiredRemainsAnAntiBotChallenge() {
        assertThrows(AntiBotException.class, () -> check(false));
    }

    @Test
    void missingSessionPoTokenDoesNotRejectStoredCredentials() {
        ServiceList.YouTube.setTokens("SID=test; SAPISID=test");

        assertThrows(AntiBotException.class, () -> check(false));
    }

    @Test
    void authenticatedLoginRequiredRejectsTheSession() {
        ServiceList.YouTube.setTokens("SID=test; SAPISID=test");

        assertThrows(YoutubeSessionRejectedException.class,
                () -> check(true, "Sign in to confirm"));
    }

    @Test
    void anonymousAgeGateRemainsAgeRestricted() {
        assertThrows(AgeRestrictedContentException.class,
                () -> check(false, "Sign in to confirm your age"));
    }

    @Test
    void authenticatedAgeGateRejectsTheSession() {
        ServiceList.YouTube.setTokens("SID=test; SAPISID=test");

        assertThrows(YoutubeSessionRejectedException.class,
                () -> check(true, "Sign in to confirm your age"));
    }

    private static void check(final boolean sessionPoTokenAttached) throws Exception {
        check(sessionPoTokenAttached, "Sign in to confirm");
    }

    private static void check(final boolean sessionPoTokenAttached,
                              final String reason) throws Exception {
        final JsonObject status = JsonObject.builder()
                .value("status", "LOGIN_REQUIRED")
                .value("reason", reason)
                .done();
        YoutubeStreamExtractor.checkPlayabilityStatus(status, "video", sessionPoTokenAttached);
    }
}
