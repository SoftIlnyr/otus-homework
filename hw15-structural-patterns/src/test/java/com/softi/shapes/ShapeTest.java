package com.softi.shapes;

import com.softi.bridge.colors.CustomColor;
import com.softi.bridge.colors.Red;
import com.softi.bridge.colors.Yellow;
import com.softi.bridge.shapes.CustomShape;
import com.softi.bridge.shapes.Rectanble;
import com.softi.bridge.shapes.Shape;
import com.softi.bridge.shapes.Triangle;
import org.junit.jupiter.api.Test;

class ShapeTest {
    
    @Test
    public void testShape() {
        Shape triangle = new Triangle();
        triangle.setColor(new Red());

        System.out.println(triangle.getDescription());
        
        Shape rectangle = new Rectanble();
        rectangle.setColor(new Yellow());

        System.out.println(rectangle.getDescription());

        CustomShape customShape = new CustomShape();
        customShape.setDescription("Кракозябра");

        CustomColor customColor = new CustomColor();
        customColor.setDescription("Серо-буро-малиновый");
        
        customShape.setColor(customColor);

        System.out.println(customShape.getDescription());
    }

}