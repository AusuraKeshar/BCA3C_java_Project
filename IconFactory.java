package com.devforge;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

/** Small, hand-drawn JavaFX line icons used by the DevForge navigation and cards. */
public final class IconFactory {
    private IconFactory() {}

    public static Canvas create(String name, Color color, double size) {
        Canvas canvas = new Canvas(size, size);
        GraphicsContext g = canvas.getGraphicsContext2D();
        double scale = size / 64.0;
        g.save();
        g.scale(scale, scale);
        g.setStroke(color);
        g.setFill(color);
        g.setLineWidth(4.5);
        g.setLineCap(StrokeLineCap.ROUND);
        g.setLineJoin(StrokeLineJoin.ROUND);
        switch (name) {
            case "home" -> home(g);
            case "key" -> key(g);
            case "lock" -> lock(g);
            case "code" -> code(g);
            case "document" -> document(g);
            case "book" -> book(g);
            case "clock" -> clock(g);
            case "settings" -> settings(g);
            case "star" -> star(g);
            case "logo" -> logo(g);
            default -> code(g);
        }
        g.restore();
        canvas.setMouseTransparent(true);
        return canvas;
    }

    private static void home(GraphicsContext g) {
        path(g, 9, 30, 32, 9, 55, 30);
        g.strokeRoundRect(16, 28, 32, 27, 4, 4);
        g.strokeRoundRect(27, 39, 11, 16, 2, 2);
    }

    private static void key(GraphicsContext g) {
        g.strokeOval(7, 18, 20, 20);
        path(g, 25, 28, 55, 28);
        path(g, 45, 28, 45, 36, 39, 36);
        path(g, 53, 28, 53, 34, 49, 34);
    }

    private static void lock(GraphicsContext g) {
        g.strokeRoundRect(12, 27, 40, 30, 7, 7);
        g.beginPath();
        g.moveTo(21, 28);
        g.lineTo(21, 19);
        g.bezierCurveTo(21, 3, 43, 3, 43, 19);
        g.lineTo(43, 28);
        g.stroke();
        g.fillOval(29, 37, 7, 7);
        path(g, 32.5, 43, 32.5, 49);
    }

    private static void code(GraphicsContext g) {
        path(g, 25, 14, 9, 32, 25, 50);
        path(g, 39, 14, 55, 32, 39, 50);
        path(g, 36, 10, 28, 54);
    }

    private static void document(GraphicsContext g) {
        g.strokeRoundRect(15, 8, 34, 48, 4, 4);
        path(g, 38, 9, 38, 20, 49, 20);
        path(g, 22, 30, 42, 30);
        path(g, 22, 38, 42, 38);
        path(g, 22, 46, 36, 46);
    }

    private static void book(GraphicsContext g) {
        g.beginPath();
        g.moveTo(8, 14);
        g.quadraticCurveTo(21, 10, 32, 18);
        g.quadraticCurveTo(43, 10, 56, 14);
        g.lineTo(56, 53);
        g.quadraticCurveTo(42, 48, 32, 57);
        g.quadraticCurveTo(21, 48, 8, 53);
        g.closePath();
        g.stroke();
        path(g, 32, 18, 32, 56);
    }

    private static void clock(GraphicsContext g) {
        g.strokeOval(8, 8, 48, 48);
        path(g, 32, 18, 32, 33, 43, 40);
    }

    private static void settings(GraphicsContext g) {
        g.strokeOval(22, 22, 20, 20);
        for (int angle = 0; angle < 360; angle += 45) {
            double radians = Math.toRadians(angle);
            double x1 = 32 + Math.cos(radians) * 13;
            double y1 = 32 + Math.sin(radians) * 13;
            double x2 = 32 + Math.cos(radians) * 23;
            double y2 = 32 + Math.sin(radians) * 23;
            path(g, x1, y1, x2, y2);
        }
    }

    private static void star(GraphicsContext g) {
        double[] x = {32, 39, 55, 43, 46, 32, 18, 21, 9, 25};
        double[] y = {8, 24, 25, 35, 52, 44, 52, 35, 25, 24};
        g.strokePolygon(x, y, x.length);
    }

    private static void logo(GraphicsContext g) {
        g.setStroke(Color.web("#994cff"));
        path(g, 25, 10, 8, 32, 25, 54);
        g.setStroke(Color.web("#08cfff"));
        path(g, 39, 10, 56, 32, 39, 54);
        path(g, 37, 12, 27, 52);
    }

    private static void path(GraphicsContext g, double... points) {
        if (points.length < 4 || points.length % 2 != 0) return;
        g.beginPath();
        g.moveTo(points[0], points[1]);
        for (int i = 2; i < points.length; i += 2) g.lineTo(points[i], points[i + 1]);
        g.stroke();
    }
}
