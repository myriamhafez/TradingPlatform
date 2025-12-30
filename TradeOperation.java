package TradingPlatform.Main;

public class TradeOperation {
    public void validateTrade(Trade trade){
        ensureState(trade, TradeState.CREATED);
        trade.setInternalState(TradeState.VALIDATED);
    }

    public void acceptTrade(Trade trade){
        ensureState(trade, TradeState.VALIDATED);
        trade.setInternalState(TradeState.ACCEPTED);
    }

    public void rejectTrade(Trade trade){
        ensureState(trade, TradeState.VALIDATED);
        trade.setInternalState(TradeState.REJECTED);
    }

    public void cancelTrade(Trade trade){
        ensureState(trade, TradeState.ACCEPTED);
        trade.setInternalState(TradeState.CANCELLED);
    }

    private void ensureState(Trade trade, TradeState expected) {
        if (trade.getState() != expected) {
            throw new IllegalStateException(
                "Invalid transition from " + trade.getState()
            );
        }
    }
}
