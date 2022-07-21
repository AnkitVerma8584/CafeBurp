package ass.cafeburp.dine.data.fake_data

import ass.cafeburp.dine.domain.modals.Category
import ass.cafeburp.dine.domain.modals.Product
import java.util.*

object FakeRepository {

    fun getCategories(): List<Category> {
        val list = mutableListOf<Category>()
        list.add(
            Category(
                1,
                "https://www.milkmaid.in/sites/default/files/2022-03/Strawberry-IceCream-590x436.jpg",
                "Ice Cream"
            )
        )
        list.add(
            Category(
                2,
                "https://static.toiimg.com/thumb/83740315.cms?imgsize=361903&width=800&height=800",
                "Sandwich"
            )
        )
        list.add(
            Category(
                3,
                "https://www.simplyrecipes.com/thmb/1KOEQ0SPZNXwU0pUXUDdAm6Z7xo=/2001x2001/smart/filters:no_upscale()/Simply-Recipes-Homemade-Pizza-Dough-Lead-Shot-1c-c2b1885d27d4481c9cfe6f6286a64342.jpg",
                "Pizza"
            )
        )
        list.add(Category(4, "", "Lunch"))
        list.add(Category(5, "", "Dinner"))
        return list
    }

    fun getProducts(): List<Product> {
        val list = mutableListOf<Product>()
        list.add(
            Product(
                1,
                "Blackberry",
                "Ice Cream",
                "Delicious cool tenderness of the milky ice cream. It will melt into your mouth instantly releasing bombs of flavours",
                500.0,
                20.0,
                "https://www.milkmaid.in/sites/default/files/2022-03/Strawberry-IceCream-590x436.jpg",
            )
        )
        list.add(
            Product(
                2,
                "American swaz",
                "Sandwhich",
                "Crispy bread buns entangling the delicious meat and cheese that will explode in your mouth.",
                210.0,
                30.0,
                "https://static.toiimg.com/thumb/83740315.cms?imgsize=361903&width=800&height=800"
            )
        )
        list.add(
            Product(
                3,
                "Barbeque chicken",
                "Pizza",
                "Delicious cool tenderness of the milky ice cream. It will melt into your mouth instantly releasing bombs of flavours",
                500.0,
                0.0,
                "https://www.simplyrecipes.com/thmb/1KOEQ0SPZNXwU0pUXUDdAm6Z7xo=/2001x2001/smart/filters:no_upscale()/Simply-Recipes-Homemade-Pizza-Dough-Lead-Shot-1c-c2b1885d27d4481c9cfe6f6286a64342.jpg",
            )
        )
        list.add(
            Product(
                4,
                "Peri Peri chicken",
                "Pizza",
                "Delicious cool tenderness of the milky ice cream. It will melt into your mouth instantly releasing bombs of flavours",
                200.0,
                0.0,
                "https://th.bing.com/th/id/OIP.VJheVPW-C9sgHDbv1uOX4QHaFn?pid=ImgDet&rs=1"
            )
        )
        return list
    }

    fun placeOrder(): String {
        val a = arrayOf("No internet found", "No chefs available", "Some error occurred")
        return a[Random().nextInt(2) % 3]
    }
}