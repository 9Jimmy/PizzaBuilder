package com.pizzabuilder.controller;

import com.pizzabuilder.domain.Ingredient.Type;
import com.pizzabuilder.domain.Ingredient;
import com.pizzabuilder.domain.Pizza;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
public class DesignPizzaController {

    @ModelAttribute
    public void addIngredientdToModel(Model model){
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("CH-P", "Parmesan", Type.CHEESE),
                new Ingredient("CH-M", "Mozzarella", Type.CHEESE),
                new Ingredient("DGH-Y", "Yeast", Type.DOUGH),
                new Ingredient("DGH-P", "Puff pastry", Type.DOUGH),
                new Ingredient("PR-BN", "Bacon", Type.PROTEIN),
                new Ingredient("PR-BF", "Beef", Type.PROTEIN),
                new Ingredient("S-B", "Basil", Type.SEASONING),
                new Ingredient("S-O", "Oregano", Type.SEASONING),
                new Ingredient("O-T", "Tomatoes", Type.OTHERS),
                new Ingredient("O-C", "Cucumbers", Type.OTHERS),
                new Ingredient("O-P", "Pepper", Type.OTHERS)
        );

        Type[] types = Ingredient.Type.values();
        for(Type type: types){
            model.addAttribute(type.toString().toLowerCase(),
                filterByType(ingredients, type));
        }
    }

    @GetMapping
    public String showDesignForm(Model model){
        model.addAttribute("design", new Pizza());
        return "design";
    }

    @PostMapping
    public String processDesign(@Valid @ModelAttribute("design") Pizza design, Errors errors, Model model){
        if(errors.hasErrors()){
            return "design";
        }
        log.info("Processing design: " + design);

        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(
            List<Ingredient> ingredients, Type type){
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
