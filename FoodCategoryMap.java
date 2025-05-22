import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class FoodCategoryMap {
    public static Map<String, List<MainPageUI.FoodItem>> getCategoryMap() {
        Map<String, List<MainPageUI.FoodItem>> categoryMap = new HashMap<>();
        categoryMap.put("Breakfast", List.of(
            new MainPageUI.FoodItem("Lugaw", 69, "rotationpanelads\\\\brk fast\\\\Lugaw (Filipino Rice Porridge) _ Iankewks.jpg"),
            new MainPageUI.FoodItem("Beef Pares Mami Noodles", 69, "rotationpanelads\\brk fast\\Beef Pares Mami Noodles Recipe - Panlasang Pinoy.jpg"),
            new MainPageUI.FoodItem("champorado", 69, "rotationpanelads\\\\brk fast\\\\Pinoy champorado recipe and tutorial with a little….jpg"),
            new MainPageUI.FoodItem("Pork Tocino", 69, "rotationpanelads\\brk fast\\Pork Tocino.jpg"),
            new MainPageUI.FoodItem("Beef Tapa", 69, "rotationpanelads\\brk fast\\Filipino Beef Tapa - FeliceMadeThis.jpg"),
            new MainPageUI.FoodItem("LongSiLog", 69, "rotationpanelads\\brk fast\\LongSiLog (Longganisa, Sinangag at Itlog) - The Peach Kitchen.jpg"),
            new MainPageUI.FoodItem("Tortang Talong", 69, "rotationpanelads\\brk fast\\Tortang Talong (Eggplant Omelette) with UFC Banana Catsup.jpg"),
            new MainPageUI.FoodItem("Tuyo tas dilis", 69, "rotationpanelads\\brk fast\\Tuyo tas dilis .jpg"),
            new MainPageUI.FoodItem("Bangus", 69, "rotationpanelads\\brk fast\\Fried Milk Fish with Garlic Rice.jpg"),
            new MainPageUI.FoodItem("coffee latte", 69, "rotationpanelads\\brk fast\\coffee latte cup (1).jpg"),
            new MainPageUI.FoodItem("Taho", 69, "rotationpanelads\\brk fast\\Taho_ Filipino Silken Tofu with Sago.jpg"),
            new MainPageUI.FoodItem("banana pancakes", 69, "rotationpanelads\\brk fast\\banana pancakes.jpg")
        ));
        categoryMap.put("Lunch", List.of(
            new MainPageUI.FoodItem("Pork Giniling", 69, "rotationpanelads\\Lunch\\Pork Giniling.jpg"),
            new MainPageUI.FoodItem("sisig",69, "rotationpanelads\\Lunch\\sisig.jpg"),
            new MainPageUI.FoodItem("Chicken Curry", 69, "rotationpanelads\\Lunch\\Filipino-style Chicken Curry.jpg"),
            new MainPageUI.FoodItem("Salad", 69, "rotationpanelads\\Lunch\\Pork and Green Beans in Black Bean Sauce (Tausi).jpg"),
            new MainPageUI.FoodItem("Beef Afritada", 69, "rotationpanelads\\Lunch\\Beef Afritada.jpg"),
            new MainPageUI.FoodItem("Ginataang Kalabasa at Sitaw", 69, "rotationpanelads\\Lunch\\Ginataang Kalabasa at Sitaw.jpg"),
            new MainPageUI.FoodItem("Laing", 69, "rotationpanelads\\Lunch\\Lola’s Authentic Laing (Taro Leaves in Coconut Milk) - PinoyBites.jpg"),
            new MainPageUI.FoodItem("Pork Dinuguan", 69, "rotationpanelads\\Lunch\\Pork Dinuguan.jpg"),
            new MainPageUI.FoodItem("Chicken Adobo", 69, "rotationpanelads\\Lunch\\BEST Classic Filipino Chicken Adobo with Step By Step Pictures.jpg"),
            new MainPageUI.FoodItem("ube milkshake", 69, "rotationpanelads\\Lunch\\ube milkshake.jpg"),
            new MainPageUI.FoodItem("Halo-halo", 69, "rotationpanelads\\Lunch\\10 Filipino Street Food Recipes You Can Totally Recreate at Home.jpg"),
            new MainPageUI.FoodItem("Cendol Drink ", 69, "rotationpanelads\\Lunch\\Cendol Drink Recipe.jpg")
        ));
        categoryMap.put("Dinner", List.of(
            new MainPageUI.FoodItem("Pork-Sinigang", 69, "rotationpanelads\\dinner\\dinner\\R02988-Pork-Sinigang.jpg"),
            new MainPageUI.FoodItem("Pares", 69, "rotationpanelads\\dinner\\dinner\\pares-known-beef-term-serving-600nw-2353666089.jpg"),
            new MainPageUI.FoodItem("lechon paksiw", 69, "rotationpanelads\\dinner\\dinner\\lechon-paksiw-recipe-1.png"),
            new MainPageUI.FoodItem("Kilawin", 69, "rotationpanelads\\dinner\\dinner\\Kilawin.jpg"),
            new MainPageUI.FoodItem("inihaw na bangus grilled boneless", 69, "rotationpanelads\\dinner\\dinner\\inihaw-na-bangus-grilled-boneless-bangus-preview.jpg"),
            new MainPageUI.FoodItem("ginataanga limasag", 69, "rotationpanelads\\dinner\\dinner\\ginataangalimasagrecipe-1.jpg"),
            new MainPageUI.FoodItem("grilled squid", 69, "rotationpanelads\\dinner\\dinner\\images.jpeg"),
            new MainPageUI.FoodItem("LECHON", 69, "img_of_food\\LECHON.jpg"),
            new MainPageUI.FoodItem("crispy pata", 69, "rotationpanelads\\dinner\\dinner\\freshly-cooked-filipino-food-called-crispy-pata.jpg"),
            new MainPageUI.FoodItem("Spaghetti", 69, "rotationpanelads\\dinner\\dinner\\Filipino-Spaghetti-1-of-3.jpg"),
            new MainPageUI.FoodItem("bbq pork", 69, "rotationpanelads\\dinner\\dinner\\filipino-bbq-pork-skewers.jpg"),
            new MainPageUI.FoodItem("Chia Pudding", 69, "rotationpanelads\\dinner\\dinner\\243019.jpg")
        ));
        return categoryMap;
    }
}
