package api.transaction.manless.service;

import api.transaction.manless.dto.CardlessRequest;
import api.transaction.manless.dto.MsResponse;

public interface CardlessService {
    MsResponse create(CardlessRequest request);

}
