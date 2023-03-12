package src;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;
import javax.imageio.ImageIO;

public class ImageProcessing {
	public static void main(String[] args) {
		int[][] imageData = imgToTwoD("./cat.jpg");
        // Or load your own image using a URL!
		// int[][] imageData = imgToTwoD("https://upload.wikimedia.org/wikipedia/commons/3/3a/Cat03.jpg");
		viewImageData(imageData);
		int[][] trimmed = trimBorders(imageData, 300);
		twoDToImage(trimmed, "./trimmed_cat.jpg");
        int[][] negativeImage = negativeColor(imageData);
        twoDToImage(negativeImage, "./negative_cat.jpg");
        int[][] stretchedImage = stretchHorizontally(imageData);
        twoDToImage(stretchedImage, "./stretched_cat.jpg");
        int[][] shrinkedImage = shrinkVertically(imageData);
        twoDToImage(shrinkedImage, "./shrinked_cat.jpg");
        int[][] invertedImage = invertImage(imageData);
        twoDToImage(invertedImage, "./inverted_cat.jpg");
        int[][] filteredImage = colorFilter(imageData, -100, 50, -188);
        twoDToImage(filteredImage, "./filtered_cat.jpg");
		// Painting with pixels
        int[][] randCanvas = new int[500][500];
        randCanvas = paintRandomImage(randCanvas);
        twoDToImage(randCanvas, "./randCanvas.jpg");
        int[][] rectCanvas = new int[500][500];
        int[] rgb = {255, 255, 0, 255};
        rectCanvas = paintRectangle(rectCanvas, 100, 200, 80, 50, getColorIntValFromRGBA(rgb));
        twoDToImage(rectCanvas, "./rectCanvas.jpg");
        int[][] generateCanvas = new int[500][500];
        generateCanvas = generateRectangles(generateCanvas, 1000);
        twoDToImage(generateCanvas, "./generateCanvas.jpg");
	}
	
    // Image Processing Methods
	
    public static int[][] trimBorders(int[][] imageTwoD, int pixelCount) {
		if (imageTwoD.length > pixelCount * 2 && imageTwoD[0].length > pixelCount * 2) {
			int[][] trimmedImg = new int[imageTwoD.length - pixelCount * 2][imageTwoD[0].length - pixelCount * 2];
			for (int i = 0; i < trimmedImg.length; i++) {
				for (int j = 0; j < trimmedImg[i].length; j++) {
					trimmedImg[i][j] = imageTwoD[i + pixelCount][j + pixelCount];
				}
			}
			return trimmedImg;
		} else {
			System.out.println("Cannot trim that many pixels from the given image.");
			return imageTwoD;
		}
	}
	public static int[][] negativeColor(int[][] imageTwoD) {
        int[][] negativeImageTwoD = new int[imageTwoD.length][imageTwoD[0].length];
        for (int i = 0; i < imageTwoD.length; i++) {
            for (int j = 0; j < imageTwoD[0].length; j++) {
                int[] rgba = getRGBAFromPixel(imageTwoD[i][j]);
                rgba[0] = 255 - rgba[0];
                rgba[1] = 255 - rgba[1];
                rgba[2] = 255 - rgba[2];
                int negativePixel = getColorIntValFromRGBA(rgba);
                negativeImageTwoD[i][j] = negativePixel;
            }
        }
        return negativeImageTwoD;
	}
	public static int[][] stretchHorizontally(int[][] imageTwoD) {
        int[][] stretchedImageTwoD = new int[imageTwoD.length][imageTwoD[0].length * 2];
        for (int i = 0; i < imageTwoD.length; i++) {
            for (int j = 0; j < imageTwoD[0].length; j++) {
                int ij = j * 2;
                stretchedImageTwoD[i][ij] = imageTwoD[i][j];
                stretchedImageTwoD[i][ij + 1] = imageTwoD[i][j];
            }
        }
		return stretchedImageTwoD;
	}
	public static int[][] shrinkVertically(int[][] imageTwoD) {
        int[][] shrinkedImage = new int[imageTwoD.length / 2][imageTwoD[0].length];
        for (int i = 0; i < imageTwoD[0].length; i++) {
            for (int j = 0; j < imageTwoD.length - 1; j += 2) {
                shrinkedImage[j / 2][i] = imageTwoD[j][i];
            } 
        }
		return shrinkedImage;
	}
	public static int[][] invertImage(int[][] imageTwoD) {
        int[][] invertedImage = new int[imageTwoD.length][imageTwoD[0].length];
        for (int i = 0; i < imageTwoD.length; i++) {
            for (int j = 0; j < imageTwoD[0].length; j++) {
                invertedImage[i][j] = imageTwoD[imageTwoD.length - 1 - i][imageTwoD[0].length - 1 - j];
            }
        }
		return invertedImage;
	}
	public static int[][] colorFilter(int[][] imageTwoD, int redChangeValue, int greenChangeValue, int blueChangeValue) {
        int[][] filteredImage = new int[imageTwoD.length][imageTwoD[0].length];
        for (int i = 0; i < imageTwoD.length; i++) {
            for (int j = 0; j < imageTwoD.length; j++) {
                int[] rgba = getRGBAFromPixel(imageTwoD[i][j]);
                rgba[0] += redChangeValue;
                rgba[1] += greenChangeValue;
                rgba[2] += blueChangeValue;
                for(int k = 0; k < 3; k++) {
                    if (rgba[k] < 0) {
                        rgba[k] = 0;
                    } else if (rgba[k] > 255) {
                        rgba[k] = 255;
                    }
                }
                filteredImage[i][j] = getColorIntValFromRGBA(rgba);
            }
        }
		return filteredImage;
	}

	// Painting Methods
	
    public static int[][] paintRandomImage(int[][] canvas) {
	    Random rand = new Random();
        for (int i = 0; i < canvas.length; i++) {
            for (int j = 0; j < canvas[0].length; j++) {
                int r = rand.nextInt(256);
                int g = rand.nextInt(256);
                int b = rand.nextInt(256);
                int[] rgb = {r, g, b, 255};
                int randColor = getColorIntValFromRGBA(rgb);
                canvas[i][j] = randColor;
            }
        }
		return canvas;
	}
	public static int[][] paintRectangle(int[][] canvas, int width, int height, int rowPosition, int colPosition, int color) {
        for (int i = 0; i < canvas.length; i++) {
            for (int j = 0; j < canvas[0].length; j++) {
                if(i >= rowPosition && i <= rowPosition + width && j >= colPosition && j <= colPosition + height) {
                    canvas[i][j] = color;
                }
            }
        }
		return canvas;
	}
	public static int[][] generateRectangles(int[][] canvas, int numRectangles) {
        Random rand = new Random();
        for (int i = 0; i < numRectangles; i++) {
            int width = rand.nextInt(canvas[0].length);
            int height = rand.nextInt(canvas.length);
            int rowPosition = rand.nextInt(canvas.length - height);
            int colPosition = rand.nextInt(canvas[0].length - width);
            int r = rand.nextInt(256);
            int g = rand.nextInt(256);
            int b = rand.nextInt(256);
            int[] rgb = {r, g, b, 255};
            int color = getColorIntValFromRGBA(rgb);
            canvas = paintRectangle(canvas, width, height, rowPosition, colPosition, color);
        }
		return canvas;
	}

	// Utility Methods
	
    public static int[][] imgToTwoD(String inputFileOrLink) {
		try {
			BufferedImage image = null;
			if (inputFileOrLink.substring(0, 4).toLowerCase().equals("http")) {
				URL imageUrl = new URL(inputFileOrLink);
				image = ImageIO.read(imageUrl);
				if (image == null) {
					System.out.println("Failed to get image from provided URL.");
				}
			} else {
				image = ImageIO.read(new File(inputFileOrLink));
			}
			int imgRows = image.getHeight();
			int imgCols = image.getWidth();
			int[][] pixelData = new int[imgRows][imgCols];
			for (int i = 0; i < imgRows; i++) {
				for (int j = 0; j < imgCols; j++) {
					pixelData[i][j] = image.getRGB(j, i);
				}
			}
			return pixelData;
		} catch (Exception e) {
			System.out.println("Failed to load image: " + e.getLocalizedMessage());
			return null;
		}
	}
	public static void twoDToImage(int[][] imgData, String fileName) {
		try {
			int imgRows = imgData.length;
			int imgCols = imgData[0].length;
			BufferedImage result = new BufferedImage(imgCols, imgRows, BufferedImage.TYPE_INT_RGB);
			for (int i = 0; i < imgRows; i++) {
				for (int j = 0; j < imgCols; j++) {
					result.setRGB(j, i, imgData[i][j]);
				}
			}
			File output = new File(fileName);
			ImageIO.write(result, "jpg", output);
		} catch (Exception e) {
			System.out.println("Failed to save image: " + e.getLocalizedMessage());
		}
	}
	public static int[] getRGBAFromPixel(int pixelColorValue) {
		Color pixelColor = new Color(pixelColorValue);
		return new int[] { pixelColor.getRed(), pixelColor.getGreen(), pixelColor.getBlue(), pixelColor.getAlpha() };
	}
	public static int getColorIntValFromRGBA(int[] colorData) {
		if (colorData.length == 4) {
			Color color = new Color(colorData[0], colorData[1], colorData[2], colorData[3]);
			return color.getRGB();
		} else {
			System.out.println("Incorrect number of elements in RGBA array.");
			return -1;
		}
	}
	public static void viewImageData(int[][] imageTwoD) {
		if (imageTwoD.length > 3 && imageTwoD[0].length > 3) {
			int[][] rawPixels = new int[3][3];
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					rawPixels[i][j] = imageTwoD[i][j];
				}
			}
			System.out.println("Raw pixel data from the top left corner.");
			System.out.print(Arrays.deepToString(rawPixels).replace("],", "],\n") + "\n");
			int[][][] rgbPixels = new int[3][3][4];
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					rgbPixels[i][j] = getRGBAFromPixel(imageTwoD[i][j]);
				}
			}
			System.out.println();
			System.out.println("Extracted RGBA pixel data from top the left corner.");
			for (int[][] row : rgbPixels) {
				System.out.print(Arrays.deepToString(row) + System.lineSeparator());
			}
		} else {
			System.out.println("The image is not large enough to extract 9 pixels from the top left corner");
		}
	}
}