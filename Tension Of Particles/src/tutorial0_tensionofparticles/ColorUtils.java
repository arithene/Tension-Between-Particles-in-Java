package tutorial0_tensionofparticles;

import java.awt.Color;

public class ColorUtils {
    /**
     * <It's ChatGPT code.>
     * Interpolates between two colors (Blue and Red) based on a given fraction.
     * It's Linear Interpolation aka known LERP.
     *
     * @param colorStart The start color (e.g., Color.BLUE).
     * @param colorEnd   The end color (e.g., Color.RED).
     * @param fraction   The interpolation fraction (0.0 to 1.0).
     * @return The interpolated color.
     */
    public static Color interpolateColor(Color colorStart, Color colorEnd, double fraction) {
        // Clamp fraction between 0 and 1
        fraction = Math.max(0, Math.min(fraction, 1));

        // Extract ARGB components
        int startR = colorStart.getRed();
        int startG = colorStart.getGreen();
        int startB = colorStart.getBlue();

        int endR = colorEnd.getRed();
        int endG = colorEnd.getGreen();
        int endB = colorEnd.getBlue();

        // Interpolate each component. Lerp formula: c = a * fraction + b * (1 -
        // fraction)
        int r = (int) (startR + (endR - startR) * fraction);
        int g = (int) (startG + (endG - startG) * fraction);
        int b = (int) (startB + (endB - startB) * fraction);

        // Combine components back into a color
        return new Color(r, g, b);
    }

    /**
     * <It's ChatGPT code.>
     * Interpolates between three colors (Blue, Black, and Red) based on a given
     * fraction.
     * Supports fractions from -1.0 (Blue) to 1.0 (Red), with 0.0 corresponding to
     * Black.
     *
     * @param fraction The interpolation fraction (-1.0 to 1.0).
     * @return The interpolated color.
     */
    public static Color interpolateColor(double fraction) {
        // Clamp fraction between -1 and 1
        fraction = Math.max(-1, Math.min(fraction, 1));

        // Define the colors
        Color blue = new Color(0, 0, 255); // Blue
        Color black = new Color(0, 0, 0); // Black
        Color red = new Color(255, 0, 0); // Red

        // Determine which range to interpolate
        if (fraction < 0) {
            // Interpolate between Blue (-1) and Black (0)
            double localFraction = (fraction + 1); // Map [-1, 0] to [0, 1]
            int r = (int) (blue.getRed() + (black.getRed() - blue.getRed()) * localFraction);
            int g = (int) (blue.getGreen() + (black.getGreen() - blue.getGreen()) * localFraction);
            int b = (int) (blue.getBlue() + (black.getBlue() - blue.getBlue()) * localFraction);
            return new Color(r, g, b);
        } else {
            // Interpolate between Black (0) and Red (1)
            double localFraction = fraction; // Map [0, 1] to [0, 1]
            int r = (int) (black.getRed() + (red.getRed() - black.getRed()) * localFraction);
            int g = (int) (black.getGreen() + (red.getGreen() - black.getGreen()) * localFraction);
            int b = (int) (black.getBlue() + (red.getBlue() - black.getBlue()) * localFraction);
            return new Color(r, g, b);
        }
    }

}
