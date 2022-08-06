package ass.cafeburp.dine.domain.modals

data class Category(val id: Int, val category_name: String, val image: String) {
    constructor() : this(0, "", "")
}