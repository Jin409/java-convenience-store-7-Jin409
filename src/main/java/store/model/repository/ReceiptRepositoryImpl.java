package store.model.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import store.model.Receipt;

public class ReceiptRepositoryImpl implements ReceiptRepository {
    private static List<Receipt> receipts;

    public ReceiptRepositoryImpl() {
        receipts = new ArrayList<>();
    }

    @Override
    public void save(Receipt receipt) {
        receipts.add(receipt);
    }

    @Override
    public Optional<Receipt> findRecentReceipt() {
        return Optional.of(receipts.getLast());
    }
}
