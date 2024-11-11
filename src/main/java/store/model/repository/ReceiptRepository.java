package store.model.repository;

import java.util.Optional;
import store.model.Receipt;

public interface ReceiptRepository {
    void save(Receipt receipt);

    Optional<Receipt> findRecentReceipt();
}
