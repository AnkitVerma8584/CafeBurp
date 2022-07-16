package ass.cafeburp.dine.data.test

import ass.cafeburp.dine.domain.modals.Category

object FakeRepository {

    fun getCategories(): List<Category> {
        val list = mutableListOf<Category>()
        list.add(Category(1, "https://www.milkmaid.in/sites/default/files/2022-03/Strawberry-IceCream-590x436.jpg", "Ice Cream"))
        list.add(Category(2, "https://static.toiimg.com/thumb/83740315.cms?imgsize=361903&width=800&height=800", "Sandwich"))
        list.add(Category(3, "https://www.simplyrecipes.com/thmb/1KOEQ0SPZNXwU0pUXUDdAm6Z7xo=/2001x2001/smart/filters:no_upscale()/Simply-Recipes-Homemade-Pizza-Dough-Lead-Shot-1c-c2b1885d27d4481c9cfe6f6286a64342.jpg", "Pizza"))
        list.add(Category(4, "", "Lunch"))
        list.add(Category(5, "", "Dinner"))
        return list
    }

}