package org.schabi.newpipe.extractor.exceptions;

/**
 * A logged-in YouTube player request was explicitly rejected with LOGIN_REQUIRED.
 */
public class YoutubeSessionRejectedException extends ParsingException {

    public YoutubeSessionRejectedException(final String message) {
        super(message);
    }
}
