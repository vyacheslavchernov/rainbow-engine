package ru.vych.render.vertex;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Color {
    private float red;
    private float green;
    private float blue;
    private float alpha;

    public static Color WHITE() {
        return new Color(1, 1, 1, 1);
    }

    public static Color RED() {
        return new Color(1, 0, 0, 1);
    }

    public static Color GREEN() {
        return new Color(0, 1, 0, 1);
    }

    public static Color BLUE() {
        return new Color(0, 0, 1, 1);
    }

    public static Color YELLOW() {
        return new Color(1, 1, 0, 1);
    }

    public List<Float> asList() {
        return List.of(red, green, blue, alpha);
    }
}
