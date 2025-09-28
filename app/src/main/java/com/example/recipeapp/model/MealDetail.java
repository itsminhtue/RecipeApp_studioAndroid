package com.example.recipeapp.model;

public class MealDetail {
    private String strMeal;
    private String strInstructions;
    private String strMealThumb;

    // Ingredients (1..20)
    private String strIngredient1;
    private String strIngredient2;
    private String strIngredient3;
    private String strIngredient4;
    private String strIngredient5;
    private String strIngredient6;
    private String strIngredient7;
    private String strIngredient8;
    private String strIngredient9;
    private String strIngredient10;
    private String strIngredient11;
    private String strIngredient12;
    private String strIngredient13;
    private String strIngredient14;
    private String strIngredient15;
    private String strIngredient16;
    private String strIngredient17;
    private String strIngredient18;
    private String strIngredient19;
    private String strIngredient20;

    // Measures (1..20)
    private String strMeasure1;
    private String strMeasure2;
    private String strMeasure3;
    private String strMeasure4;
    private String strMeasure5;
    private String strMeasure6;
    private String strMeasure7;
    private String strMeasure8;
    private String strMeasure9;
    private String strMeasure10;
    private String strMeasure11;
    private String strMeasure12;
    private String strMeasure13;
    private String strMeasure14;
    private String strMeasure15;
    private String strMeasure16;
    private String strMeasure17;
    private String strMeasure18;
    private String strMeasure19;
    private String strMeasure20;

    // Getter chính
    public String getStrMeal() { return strMeal; }
    public String getStrInstructions() { return strInstructions; }
    public String getStrMealThumb() { return strMealThumb; }

    // Lấy nguyên liệu + định lượng thành danh sách gọn gàng
    public String[] getIngredientsList() {
        String[] ingredients = new String[20];
        String[] ingr = {
                strIngredient1, strIngredient2, strIngredient3, strIngredient4, strIngredient5,
                strIngredient6, strIngredient7, strIngredient8, strIngredient9, strIngredient10,
                strIngredient11, strIngredient12, strIngredient13, strIngredient14, strIngredient15,
                strIngredient16, strIngredient17, strIngredient18, strIngredient19, strIngredient20
        };
        String[] meas = {
                strMeasure1, strMeasure2, strMeasure3, strMeasure4, strMeasure5,
                strMeasure6, strMeasure7, strMeasure8, strMeasure9, strMeasure10,
                strMeasure11, strMeasure12, strMeasure13, strMeasure14, strMeasure15,
                strMeasure16, strMeasure17, strMeasure18, strMeasure19, strMeasure20
        };

        int count = 0;
        for (int i = 0; i < 20; i++) {
            if (ingr[i] != null && !ingr[i].trim().isEmpty()) {
                ingredients[count++] = "- " + ingr[i] + " : " + (meas[i] != null ? meas[i] : "");
            }
        }

        String[] result = new String[count];
        System.arraycopy(ingredients, 0, result, 0, count);
        return result;
    }
}
