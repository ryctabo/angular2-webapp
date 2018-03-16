package com.nativapps.arpia.model.dto;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class DismissalHistoryRequest extends DismissalHistoryData {

    public DismissalHistoryRequest() {
    }

    public DismissalHistoryRequest(Boolean dismissal, String reason,
            Long messengerId) {
        super(dismissal, reason, messengerId);
    }

}
