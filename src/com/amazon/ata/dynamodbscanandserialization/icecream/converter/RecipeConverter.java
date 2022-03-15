package com.amazon.ata.dynamodbscanandserialization.icecream.converter;

import com.amazon.ata.dynamodbscanandserialization.icecream.model.Ingredient;
import com.amazon.ata.dynamodbscanandserialization.icecream.model.Recipe;

import java.util.LinkedList;
import java.util.Queue;

public final class RecipeConverter {
    private RecipeConverter() {
    }

    public static Queue<Ingredient> fromRecipeToIngredientQueue(Recipe recipe) {
        return new LinkedList<>(recipe.getIngredients());
    }
}
