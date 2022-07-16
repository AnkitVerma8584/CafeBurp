package ass.cafeburp.dine.util.states

sealed class UIState {
    object Idle : UIState()
    object Loading : UIState()
}